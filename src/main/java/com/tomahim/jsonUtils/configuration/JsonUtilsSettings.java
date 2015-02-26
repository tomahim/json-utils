package com.tomahim.jsonUtils.configuration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JsonUtilsSettings {

	public final static String DEFAULT_DATE_FORMAT = "timestamp";
	public final static int DEFAULT_MAX_DEPTH = 1;
		
	public static Map<String, Object> config;
	static {
		
		//Initializing defaults settings
	    config = new HashMap<String, Object>();

	    config.put(SettingsEnum.DATE_FORMAT.name(), DEFAULT_DATE_FORMAT);	
	    config.put(SettingsEnum.DEPTH_LEVEL.name(), DEFAULT_MAX_DEPTH);	    
	 }
	
	public static Object value(SettingsEnum enumValue) {
    	return config.get(enumValue.name());
    }
}
