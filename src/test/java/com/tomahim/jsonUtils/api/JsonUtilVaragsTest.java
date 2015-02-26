
package com.tomahim.jsonUtils.api;

import java.util.Date;

import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Test;

import com.tomahim.jsonUtils.entities.Person;
import com.tomahim.jsonUtils.entities.PersonGenerator;

import static org.junit.Assert.*;

public class JsonUtilVaragsTest {

	public PersonGenerator personGenerator;

	@Before
	public void init() {
		personGenerator = new PersonGenerator();
	}
	
	@Test
	public void buildingCompleteJsonFromVarags() {
		
		Date birthDate = new Date();
		Person p1 = personGenerator.createPerson(1, "Tata", false, birthDate, 2, 1);
		
		JsonObject jsonObject = JsonUtils.toJson(p1, "id", "name", "isMale", "birthDate", "friends", "uncles");
		
		//It should contain all these keys
		assertTrue(jsonObject.containsKey("id"));
		assertTrue(jsonObject.containsKey("name"));
		assertTrue(jsonObject.containsKey("isMale"));
		assertTrue(jsonObject.containsKey("birthDate"));
		assertTrue(jsonObject.containsKey("friends"));
		assertTrue(jsonObject.containsKey("uncles"));
	
		//It should be the good values
		assertTrue(jsonObject.getInt("id") == 1);
		assertEquals("Tata", jsonObject.getString("name"));
		assertEquals(false, jsonObject.getBoolean("isMale"));
		assertEquals(birthDate.getTime(), jsonObject.getJsonNumber("birthDate").longValue());
		assertTrue(jsonObject.getJsonArray("friends").size() == 2);
		assertTrue(jsonObject.getJsonArray("uncles").size() == 1);
		
		//It shouldn't contain other properties from java object
		assertFalse(jsonObject.containsKey("class"));
		
	}
	
	@Test
	public void buildingJsonPartOfObjectFromVarags() {

		Date birthDate = new Date();
		Person p1 = personGenerator.createPerson(2, "Tata", false, birthDate, 2, 1);		

		JsonObject jsonObject = JsonUtils.toJson(p1, "name", "birthDate", "friends");
		
		//It should contain all these keys
		assertTrue(jsonObject.containsKey("name"));
		assertTrue(jsonObject.containsKey("birthDate"));
		assertTrue(jsonObject.containsKey("friends"));

		//It should be the good values
		assertEquals("Tata", jsonObject.getString("name"));
		assertEquals(birthDate.getTime(), jsonObject.getJsonNumber("birthDate").longValue());
		assertTrue(jsonObject.getJsonArray("friends").size() == 2);
		
		//It shouldn't contain other properties from java object
		assertFalse(jsonObject.containsKey("id"));
		assertFalse(jsonObject.containsKey("isMale"));
		assertFalse(jsonObject.containsKey("uncles"));
	}
	
	
}
