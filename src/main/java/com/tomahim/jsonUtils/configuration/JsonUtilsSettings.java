package com.tomahim.jsonUtils.configuration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JsonUtilsSettings {
		
	public static Map<String, String> config;
	static {
	    config = new HashMap<String, String>();
	    config.put(SettingsEnum.DATE_FORMAT.name(), "timestamp");	    
	 }
	
	public static String value(SettingsEnum enumValue) {
    	return config.get(enumValue.name());
    }
}
