package com.tomahim.jsonUtils.configuration;

import static org.junit.Assert.*;
import org.junit.Test;

import com.tomahim.jsonUtils.configuration.JsonUtilsSettings;

public class ConfigTest {

	@Test
	public void defaultsAreSet() {
		assertNotNull(JsonUtilsSettings.value(SettingsEnum.DATE_FORMAT));
	}	
}
