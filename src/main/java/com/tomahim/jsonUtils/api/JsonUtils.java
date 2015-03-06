package com.tomahim.jsonUtils.api;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
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
import com.tomahim.jsonUtils.configuration.JsonUtilsSettings;
import com.tomahim.jsonUtils.configuration.SettingsEnum;

/* API Definition */
public class JsonUtils {
	
	/*
	 * Getting all fields with getter methods 
	 */
	
	public static JsonObject toJson(Object object, int maxDepth) {
		try {
			return JsonCompute.getJsonObjectBuilderFromJavaObject(object, maxDepth).build();
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JsonArray toJsonArray(Collection<?> collection, int maxDepth) {
	    return JsonCompute.getJsonArrayBuilderFomJavaList(collection, maxDepth).build();
	}

	public static JsonObject toJson(Object object) {
		return toJson(object, (int) JsonUtilsSettings.value(SettingsEnum.DEPTH_LEVEL));
	}
	
	public static JsonArray toJsonArray(Collection<?> collection) {
	    return JsonCompute.getJsonArrayBuilderFomJavaList(collection, (int) JsonUtilsSettings.value(SettingsEnum.DEPTH_LEVEL)).build();
	}
	
	/*
	 * Using map selection
	 */
	
	private static JsonArray toJsonArrayFromMap(Collection<?> collection, Map<String, String> map) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
	    for(Object o : collection) {	        
			jsonArrayBuilder.add(toJsonFromMap(o, map));
	    }
	    return jsonArrayBuilder.build();
	}
	
	private static JsonObject toJsonFromMap(Object o, Map<String, String> selection) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		JsonNode rootNode = new JsonNode();
		return JsonCompute.getJsonObjectFromTree(null, o, JsonTreeBuilder.constructTreeFromMap(rootNode, selection)).build();
	}
	
	public static JsonObject toJson(Object o, Map<String, String> selection) {
		try {
			return toJsonFromMap(o, selection);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JsonArray toJsonArray(Collection<?> collection, Map<String, String> selection) {
		try {
		return toJsonArrayFromMap(collection, selection);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
	
	/*
	 * Using Set
	 */
	
	private static Map<String, String> transformSetToMap(Set<String> set) {
		Map<String, String> map  = new HashMap<String, String>();
		for(String el : set) {
			map.put(el, el);
		}
		return map;
	}
	
	public static JsonObject toJson(Object o, Set<String> attributes) {
		Map<String, String> map  = transformSetToMap(attributes);
		try {
			return toJsonFromMap(o, map);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static JsonArray toJsonArray(Collection<?> collection, Set<String> attributes) {
		Map<String, String> map  = transformSetToMap(attributes);
		try {
			return toJsonArrayFromMap(collection, map);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}		
	
	/*
	 * Using varargs
	 */
	
	private static Map<String, String> transformVarargsToMap(String[] values) {
		if (values == null || values.length == 0) {
		   throw new IllegalArgumentException("No values supplied.");
	    }

		Map<String, String> map  = new HashMap<String, String>();
	    for (String el : values) {
	    	map.put(el, el);
	    }
	    return map;
	}
	
	public static JsonObject toJson(Object o, String... attributes) {
		Map<String, String> map = transformVarargsToMap(attributes);
		try {
			return toJsonFromMap(o, map);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static JsonArray toJsonArray(Collection<?> collection, String... attributes) {
		Map<String, String> map  = transformVarargsToMap(attributes);
		try {
			return toJsonArrayFromMap(collection, map);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}		
}
