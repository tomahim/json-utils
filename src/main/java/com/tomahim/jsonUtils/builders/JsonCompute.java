package com.tomahim.jsonUtils.builders;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import com.tomahim.jsonUtils.common.ReflectUtil;
import com.tomahim.jsonUtils.common.StringUtil;
import com.tomahim.jsonUtils.configuration.JsonUtilsSettings;
import com.tomahim.jsonUtils.configuration.SettingsEnum;

public final class JsonCompute {	
	
	final static String DOT = ".";
    final static String DOT_SPLIT_REGEX = "\\.";
	
	private JsonCompute() {
		
	}
	
	private static String computeAttributeNameFromMethod(Method m) {
		return ReflectUtil.getPropertyFromMethod(m);
	}
	
	private static void addKeyValue(JsonObjectBuilder jsonObjectBuilder, Object o, String name, Method method) throws IllegalAccessException, InvocationTargetException {
		String returnTypeName = method.getReturnType().getSimpleName();
		switch (returnTypeName) {
			case "boolean" :
			case "Boolean" :
				jsonObjectBuilder.add(name, (Boolean) method.invoke(o));
			break;
			case "BigDecimal" :
				jsonObjectBuilder.add(name, (BigDecimal) method.invoke(o));
			break;
			case "BigInteger" :
				jsonObjectBuilder.add(name, (BigInteger) method.invoke(o));
			break;
			case "float" :
			case "Float" :
				jsonObjectBuilder.add(name, (Float) method.invoke(o));
			break;
			case "char" :
			case "Character" :
				jsonObjectBuilder.add(name, (Character) method.invoke(o));
			break;
			case "byte" :
			case "Byte" :
				jsonObjectBuilder.add(name, (Byte) method.invoke(o));
			break;
			case "short" :
			case "Short" :
				jsonObjectBuilder.add(name, (Short) method.invoke(o));
			break;
			case "double" :
			case "Double" :
				jsonObjectBuilder.add(name, (Double) method.invoke(o));
			break;
			case "long" :
			case "Long" :
				jsonObjectBuilder.add(name, (Long) method.invoke(o));
			break;
			case "int" :
			case "Integer" :
				jsonObjectBuilder.add(name, (Integer) method.invoke(o));
			break;
			case "Date" :
				String dateFormat = (String) JsonUtilsSettings.value(SettingsEnum.DATE_FORMAT);
				if(dateFormat == null) {
					//TODO : throw custom exception if settings value is incorrect
				}
				Date date = (Date) method.invoke(o);
				if(dateFormat.equals(JsonUtilsSettings.DEFAULT_DATE_FORMAT)) { //We expect timestamp for default
					jsonObjectBuilder.add(name, date.getTime());	
				} else {
					SimpleDateFormat dt = new SimpleDateFormat(dateFormat);
					jsonObjectBuilder.add(name, dt.format(date));					
				}
			break;
			case "String":
			default:
				jsonObjectBuilder.add(name, String.valueOf(method.invoke(o)));
			break;
		}
	}
	
	private static void addToJsonBuilderMethod(JsonObjectBuilder jsonBuilder, Object o, Method method, int maxDepth, String propertyName) throws IllegalAccessException, InvocationTargetException {		
		String property = propertyName != null ? propertyName : computeAttributeNameFromMethod(method);
		if(!ReflectUtil.needRecusivity(method)) {
			addKeyValue(jsonBuilder, o, property, method);
		} else if(maxDepth > 0) {
			//Avoiding insecure StackOverlow!
			if(ReflectUtil.multipleObjectsReturned(method)) {
				jsonBuilder.add(property, getJsonArrayBuilderFomJavaList((Collection<?>) method.invoke(o), maxDepth - 1));
			} else {
				jsonBuilder.add(property, getJsonObjectBuilderFromJavaObject(method.invoke(o), maxDepth - 1));					
			}				
		}
	}
	
	private static void addToJsonBuilderMethod(JsonObjectBuilder jsonBuilder, Object o, Method method, int maxDepth) throws IllegalAccessException, InvocationTargetException {
		addToJsonBuilderMethod(jsonBuilder, o, method, maxDepth, null);
	}
		
	public static JsonObjectBuilder getJsonObjectBuilderFromJavaObject(Object object, int maxDepth) throws IllegalAccessException, InvocationTargetException {
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		List<Method> methods = ReflectUtil.getAccessibleGettersMethods(object);
		for (Method method : methods) {			
			addToJsonBuilderMethod(jsonBuilder, object, method, maxDepth);
		}
		return jsonBuilder;
	}
	
	public static JsonArrayBuilder getJsonArrayBuilderFomJavaList(Collection<?> collection, int maxDepth) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
	    for(Object o : collection) {
	        try {
				jsonArrayBuilder.add(getJsonObjectBuilderFromJavaObject(o, maxDepth));
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
	    }
	    return jsonArrayBuilder;
	}
	
	private static JsonArrayBuilder resolveArrayValuePath(JsonArrayBuilder jsonArrayBuilder, Collection<?> collection, String key, String valuePath) throws IllegalAccessException, InvocationTargetException {
		JsonArrayBuilder jsonB = (jsonArrayBuilder != null) ? jsonArrayBuilder : Json.createArrayBuilder();
	    for(Object o : collection) {
	        try {
	        	jsonB.add(resolveValuePath(Json.createObjectBuilder(), o, key, valuePath));
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
	    }
	    return jsonB;
	}
	
	private static JsonObjectBuilder resolveValuePath(JsonObjectBuilder jsonBuilder, Object object, String key, String valuePath) throws IllegalAccessException, InvocationTargetException {
		List<Method> methods = ReflectUtil.getAccessibleGettersMethods(object);
		for(Method method : methods) {
			String propertyName = computeAttributeNameFromMethod(method);
			if(valuePath.contains(DOT)) {
				String[] values = valuePath.split(DOT_SPLIT_REGEX);
				if(!propertyName.equals(values[0])) {
					continue;
				}
				String nextValue = StringUtil.getNextValue(valuePath);
				if(!ReflectUtil.multipleObjectsReturned(method)) { //Simple object to parse
					resolveValuePath(jsonBuilder, method.invoke(object), key, nextValue);
				} else {  //Collection of objects
					jsonBuilder.add(key, resolveArrayValuePath(null, (Collection<?>) method.invoke(object), key, nextValue));
				}
			} else {
				if(propertyName.equals(valuePath)) {
					addToJsonBuilderMethod(jsonBuilder, object, method, ReflectUtil.multipleObjectsReturned(method) || !ReflectUtil.isPrimiveObject(method.getReturnType()) ? 1 : 0, key);					
				}				
			}			
		}
		return jsonBuilder;
	}
	
	
	public static JsonObjectBuilder getJsonObjectFromTree(JsonObjectBuilder jsonBuilder, Object object, JsonNode jsonNode) {
		JsonObjectBuilder jsonB = (jsonBuilder != null) ? jsonBuilder : Json.createObjectBuilder();
		if(jsonNode.isLeaf()) {
			//add attribute to jsonB + calculate value of valuePath
			try {
				resolveValuePath(jsonB, object, jsonNode.getKey(), jsonNode.getValuePath());
			} catch (IllegalAccessException e) {
				
			} catch (InvocationTargetException e) {
				
			}
		} else {
			for(JsonNode node : jsonNode.getNodes()) {
				if(node.isLeaf()) {
					//add attribute to jsonB + calculate value of valuePath	
					try {
						resolveValuePath(jsonB, object, node.getKey(), node.getValuePath());
					} catch (IllegalAccessException e) {

					} catch (InvocationTargetException e) {
						
					}			
				} else {
					jsonB.add(node.getKey(), getJsonObjectFromTree(null, object, node));
				}
			}
		}
		return jsonB;
	}	

}
