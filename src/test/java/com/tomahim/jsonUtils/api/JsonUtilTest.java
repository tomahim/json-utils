package com.tomahim.jsonUtils.api;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.tomahim.jsonUtils.api.JsonUtils;
import com.tomahim.jsonUtils.entities.Person;
import com.tomahim.jsonUtils.entities.PersonGenerator;

public class JsonUtilTest {
	
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
		assertFalse(jsonObject.containsKey("name2"));
		
		/* Values verifications */
		assertEquals(jsonObject.getInt("id"), p.getId().intValue());
		assertEquals(jsonObject.getString("name"), p.getName());
		assertTrue(jsonObject.getBoolean("isMale"));
		assertEquals(jsonObject.getJsonNumber("birthDate").longValue(), birthDate.getTime());
		
		assertNotNull(jsonObject);
	}
	
	@Test
	public void testJsonContructionDepth1() {
		Person p1 = personGenerator.createPerson(1, 2);
		
		JsonObject jsonObject = JsonUtils.toJson(p1, 1);
		
		assertTrue(jsonObject.containsKey("friends"));		
		assertTrue(jsonObject.get("friends").getValueType().equals(ValueType.ARRAY));
		assertTrue(jsonObject.get("uncles").getValueType().equals(ValueType.ARRAY));
	}
	
}
