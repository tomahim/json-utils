package com.tomahim.jsonUtils.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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

public class JsonUtilFullObjectBuilderTest {
	
	public PersonGenerator personGenerator;
	
	Person p;
	
	Person mother;
	
	Date birthDate;
	
	List<Person> personsList;
	
	Set<Person> personsSet;
	
	@Before
	public void init() {
		
		JsonUtilsSettings.resetSettings();
		
		birthDate = new Date();
		personGenerator = new PersonGenerator();
		mother = personGenerator.createPerson(2, "Mama", false, birthDate, personGenerator.createPerson(), 0, 0, 1);
		p = personGenerator.createPerson(1, "Toto", true, birthDate, mother, 0, 0, 2);
		
		personsList = new ArrayList<Person>();
		personsList.add(p);
		personsList.add(p);
		personsList.add(p);
		
		personsSet = new HashSet<Person>();
		personsSet.add(p);
		personsSet.add((Person) p.clone());
	}
	
	private void testFullObjectContent(JsonObject jsonObject) {
		assertNotNull(jsonObject);
		
		/* Key verification */
		assertTrue(CommonTestMethods.jsonObjectContainKeys(jsonObject, "id", "name", "isMale", "birthDate", "mother"));	
		assertFalse(jsonObject.containsKey("privateInfo"));
		
		/* Values verifications */
		assertEquals(p.getId().intValue(), jsonObject.getInt("id"));
		assertEquals(2, jsonObject.getJsonObject("mother").getInt("id"));
		assertEquals(jsonObject.getString("name"), p.getName());
		assertTrue(jsonObject.getBoolean("isMale"));
		assertEquals(jsonObject.getJsonNumber("birthDate").longValue(), birthDate.getTime());
		assertEquals(p.getNbSisters(), jsonObject.getInt("nbSisters"));
	}

	@Test
	public void testFullJavaObjectToJson() {		
		JsonObject jsonObject = JsonUtils.toJson(p);
		testFullObjectContent(jsonObject);		
	}
	
	@Test
	public void testListOfFullObjectToJson() {
		JsonArray jsonArray = JsonUtils.toJsonArray(personsList);
		assertTrue(jsonArray.size() == 3);
		for(int i = 0; i < jsonArray.size(); i++) {
			testFullObjectContent(jsonArray.getJsonObject(i));			
		}
	}
	
	@Test
	public void testSetOfFullObjectToJson() {
		JsonArray jsonArray = JsonUtils.toJsonArray(personsSet);
		assertTrue(jsonArray.size() == 2);
		for(int i = 0; i < jsonArray.size(); i++) {
			testFullObjectContent(jsonArray.getJsonObject(i));			
		}
	}
}
