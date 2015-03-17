package com.tomahim.jsonUtils.api;

import java.util.List;

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
		jsonBuilder.add("name", name);
		JsonObject jsonObject = jsonBuilder.build();
		Person person = (Person) JsonUtils.create(Person.class, jsonObject);
		assertNotNull(person);
		assertEquals(name, person.getName());
	}

}
