package com.tomahim.jsonUtils.configuration;

public enum SettingsEnum {

	DATE_FORMAT("date_format"), //Global format to use for date (default : "timestamp")
	DEPTH_LEVEL("depth_level"); //Depth level for full object construction (default : 1)

	public String name;	

	private SettingsEnum(String name) {
		this.name = name;
	}

}	

