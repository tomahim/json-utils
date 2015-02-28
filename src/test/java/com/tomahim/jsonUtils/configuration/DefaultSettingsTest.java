package com.tomahim.jsonUtils.configuration;

import static org.junit.Assert.*;

import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Test;

import com.tomahim.jsonUtils.api.JsonUtils;
import com.tomahim.jsonUtils.configuration.JsonUtilsSettings;
import com.tomahim.jsonUtils.entities.Person;
import com.tomahim.jsonUtils.entities.PersonGenerator;

public class DefaultSettingsTest {

	PersonGenerator personGenerator;
	
	public Person depth2;	
	
	@Before
	public void init() {
		JsonUtilsSettings.resetSettings();
		personGenerator = new PersonGenerator();
		
		depth2 = personGenerator.createPerson(1, 2);
		
		Person uncleDeep1 = personGenerator.createPerson();
		Person uncleDeep2 = personGenerator.createPerson();
		uncleDeep1.addUncle(uncleDeep2);
		depth2.addUncle(uncleDeep1);
	}
	
	@Test
	public void defaultAreSet() {
		assertNotNull(JsonUtilsSettings.value(SettingsEnum.DATE_FORMAT));
		assertTrue(JsonUtilsSettings.value(SettingsEnum.DATE_FORMAT).equals(JsonUtilsSettings.DEFAULT_DATE_FORMAT));
		
		assertNotNull(JsonUtilsSettings.value(SettingsEnum.DEPTH_LEVEL));
		assertTrue(JsonUtilsSettings.value(SettingsEnum.DEPTH_LEVEL).equals(new Integer(JsonUtilsSettings.DEFAULT_MAX_DEPTH)));
	}
	
	@Test
	public void defaultDateFormatIsUsed() {
		Person p = personGenerator.createPerson();
		JsonObject jsonObj = JsonUtils.toJson(p);

		assertNotNull(jsonObj.getInt("birthDate"));
		//Check if is int number because we espect timestamp as default 
		assertTrue(jsonObj.getInt("birthDate") == (int)jsonObj.getInt("birthDate"));		
	}
	
	@Test
	public void defaulDepthLevelIsUsed() {
		
		JsonObject jsonObj = JsonUtils.toJson(depth2);

		assertNotNull(jsonObj.getJsonArray("friends"));
		assertNotNull(jsonObj.getJsonArray("uncles"));	
		
		assertNotNull(jsonObj.getJsonArray("uncles").getJsonObject(2));
		
		assertFalse(jsonObj.getJsonArray("uncles").getJsonObject(2).containsKey("uncles"));
	}
}
