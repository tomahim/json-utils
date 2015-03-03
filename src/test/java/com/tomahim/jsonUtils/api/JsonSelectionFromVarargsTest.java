
package com.tomahim.jsonUtils.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Test;

import com.tomahim.jsonUtils.configuration.JsonUtilsSettings;
import com.tomahim.jsonUtils.entities.Person;
import com.tomahim.jsonUtils.entities.PersonGenerator;

import static org.junit.Assert.*;

public class JsonSelectionFromVarargsTest {

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
	public void buildingCompleteJsonFromVarargs() {		
		JsonObject jsonObject = JsonUtils.toJson(p1, "id", "name", "isMale", "birthDate", "mother", "friends", "uncles", "nbSisters");
		testCompleteJsonObject(jsonObject);		
	}
	
	@Test
	public void buildingArrayFromCompleteJsonVarargs() {
		JsonArray jsonArray = JsonUtils.toJsonArray(persons, "id", "name", "isMale", "birthDate", "mother", "friends", "uncles", "nbSisters");
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

	private void testDeepObjectSelection(JsonObject jsonObject) {
		assertTrue(jsonObject.containsKey("name"));
		assertTrue(jsonObject.containsKey("mother"));
		assertTrue(jsonObject.containsKey("friends"));
		assertEquals("Mama", jsonObject.getJsonObject("mother").getString("name"));
	}
	
	@Test
	public void deepObjectSelection() {
		JsonObject jsonObject = JsonUtils.toJson(p1, "name", "mother.name", "friends.name");
		testDeepObjectSelection(jsonObject);
	}
	
	@Test
	public void deepArraySelection() {
		JsonArray jsonArray = JsonUtils.toJsonArray(persons, "name", "mother.name", "friends.name");
		assertTrue(jsonArray.size() == 3);
		for(int i = 0; i < jsonArray.size(); i++) {
			testDeepObjectSelection(jsonArray.getJsonObject(i));
		}		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullVarargsShouldThrowIllegalArgumentException() {
		String[] varargs = null;
		JsonArray jsonArray = JsonUtils.toJsonArray(persons, varargs);
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void emptyVarargsSouldThrowIllegalArgumentException() {
		String[] varargs = {};
		JsonArray jsonArray = JsonUtils.toJsonArray(persons, varargs);
	}
}
