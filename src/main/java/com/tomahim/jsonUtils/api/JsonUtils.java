package com.tomahim.jsonUtils.api;

import java.awt.List;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import com.tomahim.jsonUtils.builders.JsonCompute;
import com.tomahim.jsonUtils.builders.JsonNode;
import com.tomahim.jsonUtils.builders.JsonTreeBuilder;
import com.tomahim.jsonUtils.common.ReflectUtil;
import com.tomahim.jsonUtils.configuration.JsonUtilsSettings;
import com.tomahim.jsonUtils.configuration.SettingsEnum;

/* API Definition */
public final class JsonUtils {

	private JsonUtils() {

	}

	/*
	 * Getting all fields with getter methods
	 */

	public static JsonObject toJson(Object object, int maxDepth) {
		try {
			return JsonCompute.getJsonObjectBuilderFromJavaObject(object,
					maxDepth).build();
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JsonArray toJsonArray(Collection<?> collection, int maxDepth) {
		return JsonCompute.getJsonArrayBuilderFomJavaList(collection, maxDepth)
				.build();
	}

	public static JsonObject toJson(Object object) {
		return toJson(object,
				(int) JsonUtilsSettings.value(SettingsEnum.DEPTH_LEVEL));
	}

	public static JsonArray toJsonArray(Collection<?> collection) {
		return JsonCompute.getJsonArrayBuilderFomJavaList(collection,
				(int) JsonUtilsSettings.value(SettingsEnum.DEPTH_LEVEL))
				.build();
	}

	/*
	 * Using map selection
	 */

	private static JsonArray toJsonArrayFromMap(Collection<?> collection, Map<String, String> map) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		for (Object o : collection) {
			jsonArrayBuilder.add(toJsonFromMap(o, map));
		}
		return jsonArrayBuilder.build();
	}

	private static JsonObject toJsonFromMap(Object o, Map<String, String> selection) {
		JsonNode rootNode = new JsonNode();
		return JsonCompute.getJsonObjectFromTree(null, o, 
				JsonTreeBuilder.constructTreeFromMap(rootNode, selection))
				.build();
	}

	public static JsonObject toJson(Object o, Map<String, String> selection) {
		return toJsonFromMap(o, selection);
	}

	public static JsonArray toJsonArray(Collection<?> collection, Map<String, String> selection) {
		return toJsonArrayFromMap(collection, selection);
	}

	/*
	 * Using Set
	 */

	private static Map<String, String> transformSetToMap(Set<String> set) {
		Map<String, String> map = new HashMap<String, String>();
		for (String el : set) {
			map.put(el, el);
		}
		return map;
	}

	public static JsonObject toJson(Object o, Set<String> attributes) {
		Map<String, String> map = transformSetToMap(attributes);
		return toJsonFromMap(o, map);
	}

	public static JsonArray toJsonArray(Collection<?> collection,
			Set<String> attributes) {
		Map<String, String> map = transformSetToMap(attributes);
		return toJsonArrayFromMap(collection, map);
	}

	/*
	 * Using varargs
	 */

	private static Map<String, String> transformVarargsToMap(String[] values) {
		if (values == null || values.length == 0) {
			throw new IllegalArgumentException("No values supplied.");
		}

		Map<String, String> map = new HashMap<String, String>();
		for (String el : values) {
			map.put(el, el);
		}
		return map;
	}

	public static JsonObject toJson(Object o, String... attributes) {
		Map<String, String> map = transformVarargsToMap(attributes);
		return toJsonFromMap(o, map);
	}

	public static JsonArray toJsonArray(Collection<?> collection,
			String... attributes) {
		Map<String, String> map = transformVarargsToMap(attributes);
		return toJsonArrayFromMap(collection, map);
	}
		
	private static Object getJavaObjectValueFromJsonValue(JsonObject jsonObject, String key, Class memberClass) {
		Object value = null;
		switch (memberClass.getSimpleName()) {
			case "Integer":
			case "int":
				value = jsonObject.getInt(key);
			break;
			case "String":
				value = jsonObject.getString(key);
			break;	
			case "Boolean":
				value = jsonObject.getBoolean(key);
			break;	
			case "Date":
				long timestamp = jsonObject.getJsonNumber(key).longValue();
				value = new Date(timestamp);
			break;	
			default:
				value = jsonObject.getString(key);
			break;
		}
		return value;
	}

	public static Object create(Class classType, JsonObject jsonObject) {
		try {
			Object obj = classType.newInstance();
			for(String key : jsonObject.keySet()) {
				Method setter = null;
				Method getter = null;
				Class classTypeMember = null;				
				try {
					getter = ReflectUtil.getGetterByMemberName(classType, key);
					classTypeMember = getter.getReturnType();
					setter = ReflectUtil.getSetterByMemberName(classType, key, classTypeMember);
				} catch (NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(setter != null && classTypeMember != null) {
					Object objectParam = null;
					if(!ReflectUtil.isPrimiveObject(classTypeMember)) {
						if(!ReflectUtil.multipleObjectsReturned(getter)) {
							objectParam = create(classTypeMember, jsonObject.getJsonObject(key));
						} else {
							Collection collection = instanciateArrayOrSet(classTypeMember);
							collection.add(create(classTypeMember, jsonObject));
							objectParam = collection;
						}
					} else {
						objectParam = getJavaObjectValueFromJsonValue(jsonObject, key, classTypeMember);
					}
					try {
						setter.invoke(obj, objectParam);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return obj;			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static Collection instanciateArrayOrSet(Class classTypeMember) {
		Type[] types = classTypeMember.getGenericInterfaces();		
		Collection collection = null;
		Class genericType = types[0].getClass();
		if(classTypeMember.getSimpleName().equals("List")) {
			collection = new ArrayList();
		}
		return collection;
	}
}
