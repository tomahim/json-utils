package com.tomahim.jsonUtils.configuration;

import static org.junit.Assert.*;

import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Test;

import com.tomahim.jsonUtils.api.JsonUtils;
import com.tomahim.jsonUtils.configuration.JsonUtilsSettings;
import com.tomahim.jsonUtils.entities.Person;
import com.tomahim.jsonUtils.entities.PersonGenerator;

public class ConfigTest {

	PersonGenerator personGenerator;
	
	@Before
	public void init() {
		personGenerator = new PersonGenerator();
	}
	
	@Test
	public void defaultsDateFormateisSet() {
		assertNotNull(JsonUtilsSettings.value(SettingsEnum.DATE_FORMAT));
	}
	
	@Test
	public void defaultDateFormatIsUsed() {
		Person p = personGenerator.createPerson();
		
		JsonObject jsonObj = JsonUtils.toJson(p);

		assertNotNull(jsonObj.getInt("birthDate"));
		//Check if is int number because we espect timestamp as default 
		assertTrue(jsonObj.getInt("birthDate") == (int)jsonObj.getInt("birthDate"));		
	}
}
