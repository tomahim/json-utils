
package com.tomahim.jsonUtils.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Test;

import com.tomahim.jsonUtils.entities.Person;
import com.tomahim.jsonUtils.entities.PersonGenerator;

import static org.junit.Assert.*;

public class JsonUtilVarargsTest {

	public PersonGenerator personGenerator;
	
	Date birthDate;
	
	Person p1;
	
	List<Person> persons;

	@Before
	public void init() {		
		birthDate = new Date();
		personGenerator = new PersonGenerator();
		p1 = personGenerator.createPerson(1, "Tata", false, birthDate, 2, 1);
		persons = new ArrayList<Person>();
		persons.add(p1);
		persons.add(p1);
		persons.add(p1);
	}
	
	private void testCompleteJsonObject(JsonObject jsonObject) {

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
	public void buildingCompleteJsonFromVarargs() {		
		JsonObject jsonObject = JsonUtils.toJson(p1, "id", "name", "isMale", "birthDate", "friends", "uncles");
		testCompleteJsonObject(jsonObject);		
	}
	
	@Test
	public void buildingArrayFromCompleteJsonVarargs() {
		JsonArray jsonArray = JsonUtils.toJsonArray(persons, "id", "name", "isMale", "birthDate", "friends", "uncles");
		assertTrue(jsonArray.size() == 3);
		for(int i = 0; i < jsonArray.size(); i++) {
			testCompleteJsonObject(jsonArray.getJsonObject(i));
		}
	}
	
	private void testPartOfJsonObject(JsonObject jsonObject) {

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
	
	@Test
	public void buildingJsonPartOfObjectFromVarags() {	
		JsonObject jsonObject = JsonUtils.toJson(p1, "name", "birthDate", "friends");
		testPartOfJsonObject(jsonObject);
		
	}
	
	@Test
	public void buildingArrayPartOfObjectFromJsonVarargs() {
		JsonArray jsonArray = JsonUtils.toJsonArray(persons, "name", "birthDate", "friends");
		assertTrue(jsonArray.size() == 3);
		for(int i = 0; i < jsonArray.size(); i++) {
			testPartOfJsonObject(jsonArray.getJsonObject(i));
		}
	}
	
	
}