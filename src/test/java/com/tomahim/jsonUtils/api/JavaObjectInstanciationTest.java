package com.tomahim.jsonUtils.api;

import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tomahim.jsonUtils.entities.Person;

public class JavaObjectInstanciationTest {
	
	JsonObject p1;
	JsonObject p2;
	Integer id;
	String name;
	String motherName;
	Boolean isMale;
	int nbSisters;
	
	Date birthDate;
	
	@Before
	public void initData() {		
		name = "Toto";
		motherName = "Mama";
		id = 1;
		birthDate = new Date();
		isMale = true;
		nbSisters = 2;
		
		JsonObjectBuilder jsonBuilderP1 = Json.createObjectBuilder();
		JsonObjectBuilder jsonBuilderP2 = Json.createObjectBuilder();

		jsonBuilderP2.add("name", motherName);
		
		p2 = jsonBuilderP2.build();
		
		jsonBuilderP1.add("id", id);
		jsonBuilderP1.add("name", name);
		jsonBuilderP1.add("isMale", isMale);
		jsonBuilderP1.add("birthDate", birthDate.getTime());
		jsonBuilderP1.add("nbSisters", nbSisters);		
		jsonBuilderP1.add("mother", p2);		
		
		p1 = jsonBuilderP1.build();
	}
	
	@Test
	public void simpleJavaObjectFromJson() {
		Person person = (Person) JsonUtils.create(Person.class, p1);
		
		assertNotNull(person);
		assertEquals(id, person.getId());
		assertEquals(name, person.getName());
		assertEquals(isMale, person.getIsMale());
		assertEquals(birthDate, person.getBirthDate());
		assertEquals(nbSisters, person.getNbSisters());
		
		assertNotNull(person.getMother());
		assertEquals(motherName, person.getMother().getName());
	}

}
