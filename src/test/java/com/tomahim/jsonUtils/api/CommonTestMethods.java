package com.tomahim.jsonUtils.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import javax.json.JsonObject;
import javax.json.JsonValue;

public class CommonTestMethods {

	public static boolean jsonObjectContainKeys(JsonObject jsonObject, String... keys) {
		
		Set<Entry<String, JsonValue>> entrySet = jsonObject.entrySet();
		Set<String> objectKeys = new HashSet<String>();
		for(Entry<String, JsonValue> entry : entrySet) {
			objectKeys.add(entry.getKey());
		}
		return objectKeys.containsAll(Arrays.asList(keys));
		
	}
	
}
