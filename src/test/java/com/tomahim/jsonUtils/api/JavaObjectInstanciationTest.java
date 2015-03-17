package com.tomahim.jsonUtils.api;

import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tomahim.jsonUtils.entities.Person;

public class JavaObjectInstanciationTest {
	
	@Test
	public void simpleJavaObjectFromJson() {
		
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		String name = "Toto";
		Integer id = 1;
		Date birthDate = new Date();
		Boolean isMale = true;
		jsonBuilder.add("id", id);
		jsonBuilder.add("name", name);
		jsonBuilder.add("isMale", isMale);
		jsonBuilder.add("birthDate", birthDate.getTime());
		JsonObject jsonObject = jsonBuilder.build();
		Person person = (Person) JsonUtils.create(Person.class, jsonObject);
		assertNotNull(person);
		assertEquals(name, person.getName());
		assertEquals(id, person.getId());
		assertEquals(isMale, person.getIsMale());
		assertEquals(birthDate, person.getBirthDate());
	}

}
