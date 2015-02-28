package com.tomahim.jsonUtils.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Test;

import com.tomahim.jsonUtils.entities.Person;
import com.tomahim.jsonUtils.entities.PersonGenerator;

public class JsonUtilFullObjectBuilderTest {
	
	public PersonGenerator personGenerator;
	
	Person p;
	
	Date birthDate;
	
	List<Person> persons;
	
	@Before
	public void init() {
		birthDate = new Date();
		personGenerator = new PersonGenerator();
		p = personGenerator.createPerson(1, "Toto", true, birthDate, 0, 0);
		persons = new ArrayList<Person>();
		persons.add(p);
		persons.add(p);
		persons.add(p);
	}
	
	private void testFullObjectContent(JsonObject jsonObject) {
		assertNotNull(jsonObject);
		
		/* Key verification */
		assertTrue(jsonObject.containsKey("id"));
		assertTrue(jsonObject.containsKey("name"));
		assertTrue(jsonObject.containsKey("isMale"));
		assertTrue(jsonObject.containsKey("birthDate"));
	
		assertFalse(jsonObject.containsKey("privateInfo"));
		
		/* Values verifications */
		assertEquals(jsonObject.getInt("id"), p.getId().intValue());
		assertEquals(jsonObject.getString("name"), p.getName());
		assertTrue(jsonObject.getBoolean("isMale"));
		assertEquals(jsonObject.getJsonNumber("birthDate").longValue(), birthDate.getTime());
	}

	@Test
	public void testFullJavaObjectToJson() {		
		JsonObject jsonObject = JsonUtils.toJson(p);
		testFullObjectContent(jsonObject);		
	}
	
	@Test
	public void testArrayFullObjectToJson() {
		JsonArray jsonArray = JsonUtils.toJsonArray(persons);
		assertTrue(jsonArray.size() == 3);
		for(int i = 0; i < jsonArray.size(); i++) {
			testFullObjectContent(jsonArray.getJsonObject(i));			
		}
	}
}
