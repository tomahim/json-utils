package com.tomahim.jsonUtils.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Test;

import com.tomahim.jsonUtils.entities.Person;
import com.tomahim.jsonUtils.entities.PersonGenerator;

public class JsonUtilFullObjectBuilder {
	
	public PersonGenerator personGenerator;
	
	@Before
	public void init() {
		personGenerator = new PersonGenerator();
	}

	@Test
	public void testFullJavaObjectToJson() {
		Date birthDate = new Date();
		Person p = personGenerator.createPerson(1, "Toto", true, birthDate, 0, 0);
		
		JsonObject jsonObject = JsonUtils.toJson(p);
		
		/* Key verification */
		assertTrue(jsonObject.containsKey("id"));
		assertTrue(jsonObject.containsKey("name"));
		assertTrue(jsonObject.containsKey("isMale"));
	
		assertFalse(jsonObject.containsKey("privateInfo"));
		
		/* Values verifications */
		assertEquals(jsonObject.getInt("id"), p.getId().intValue());
		assertEquals(jsonObject.getString("name"), p.getName());
		assertTrue(jsonObject.getBoolean("isMale"));
		assertEquals(jsonObject.getJsonNumber("birthDate").longValue(), birthDate.getTime());
		
		assertNotNull(jsonObject);
	}
}
