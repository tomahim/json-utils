
package com.tomahim.jsonUtils.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Test;

import com.tomahim.jsonUtils.configuration.JsonUtilsSettings;
import com.tomahim.jsonUtils.entities.Person;
import com.tomahim.jsonUtils.entities.PersonGenerator;

import static org.junit.Assert.*;

public class JsonSelectionFromSetTest {

	public PersonGenerator personGenerator;
	
	Date birthDate;
	
	Person p1;
	
	Person mother;
	
	List<Person> persons;

	@Before
	public void init() {		
		JsonUtilsSettings.resetSettings();
		birthDate = new Date();
		personGenerator = new PersonGenerator();
		mother = personGenerator.createPerson(2, "Mama", false, birthDate, personGenerator.createPerson(), 0, 0, 1);
		p1 = personGenerator.createPerson(1, "Tata", false, birthDate, mother, 2, 1, 2);
		persons = new ArrayList<Person>();
		persons.add(p1);
		persons.add(p1);
		persons.add(p1);
	}
	
	private void testCompleteJsonObject(JsonObject jsonObject) {

		//It should contain all these keys
		assertTrue(CommonTestMethods.jsonObjectContainKeys(jsonObject, "id", "name", "isMale", "birthDate", "mother", "friends", "uncles", "nbSisters"));	
	
		//It should be the good values
		assertTrue(jsonObject.getInt("id") == 1);
		assertEquals("Tata", jsonObject.getString("name"));
		assertEquals(false, jsonObject.getBoolean("isMale"));
		assertEquals(birthDate.getTime(), jsonObject.getJsonNumber("birthDate").longValue());
		assertEquals(2, jsonObject.getJsonObject("mother").getInt("id"));
		assertTrue(jsonObject.getJsonArray("friends").size() == 2);
		assertTrue(jsonObject.getJsonArray("uncles").size() == 1);
		assertTrue(jsonObject.getInt("nbSisters") == 2);
		
		//It shouldn't contain other properties from java object
		assertFalse(jsonObject.containsKey("class"));
	}
	
	@Test
	public void buildingCompleteJsonFromSet() {		
		Set<String> selection = new HashSet<String>();		
		String[] fields = {"id", "name", "isMale", "birthDate", "mother", "friends", "uncles", "nbSisters"};
		selection.addAll(Arrays.asList(fields));
		JsonObject jsonObject = JsonUtils.toJson(p1, selection);
		testCompleteJsonObject(jsonObject);		
	}
	
	@Test
	public void buildingArrayFromCompleteSet() {
		Set<String> selection = new HashSet<String>();		
		String[] fields = {"id", "name", "isMale", "birthDate", "mother", "friends", "uncles", "nbSisters"};
		selection.addAll(Arrays.asList(fields));
		
		JsonArray jsonArray = JsonUtils.toJsonArray(persons, selection);
		assertTrue(jsonArray.size() == 3);
		for(int i = 0; i < jsonArray.size(); i++) {
			testCompleteJsonObject(jsonArray.getJsonObject(i));
		}
	}
	
	private void testPartOfJsonObject(JsonObject jsonObject) {

		//It should contain all these keys
		assertTrue(CommonTestMethods.jsonObjectContainKeys(jsonObject, "name", "birthDate", "friends"));	

		//It should be the good values
		assertEquals("Tata", jsonObject.getString("name"));
		assertEquals(birthDate.getTime(), jsonObject.getJsonNumber("birthDate").longValue());
		assertTrue(jsonObject.getJsonArray("friends").size() == 2);
		
		//It shouldn't contain other properties from java object
		assertFalse(CommonTestMethods.jsonObjectContainKeys(jsonObject, "id", "isMale", "uncles"));	
	}
	
	@Test
	public void buildingJsonPartOfObjectFromSet() {	
		Set<String> selection = new HashSet<String>();		
		String[] fields = {"name", "birthDate", "friends"};		
		selection.addAll(Arrays.asList(fields));
		
		JsonObject jsonObject = JsonUtils.toJson(p1, selection);
		
		testPartOfJsonObject(jsonObject);
		
	}
	
	@Test
	public void buildingArrayPartOfObjectFromSet() {
		Set<String> selection = new HashSet<String>();		
		String[] fields = {"name", "birthDate", "friends"};
		selection.addAll(Arrays.asList(fields));
		
		JsonArray jsonArray = JsonUtils.toJsonArray(persons, selection);
		
		assertTrue(jsonArray.size() == 3);
		for(int i = 0; i < jsonArray.size(); i++) {
			testPartOfJsonObject(jsonArray.getJsonObject(i));
		}
	}
	
	
}
