package com.tomahim.jsonUtils.api;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue.ValueType;

import org.junit.Before;
import org.junit.Test;

import com.tomahim.jsonUtils.api.JsonUtils;
import com.tomahim.jsonUtils.entities.Person;
import com.tomahim.jsonUtils.entities.PersonGenerator;

public class JsonUtilDepthTest {
	
	public PersonGenerator personGenerator;

	public Person depth0;
	
	public Person depth1;
	
	public Person depth2;	
	
	public Person depth3;	
	
	public List<Person> personsDepth3;	
	
	@Before
	public void initData() {
		personGenerator = new PersonGenerator();
		
		depth1 = personGenerator.createPerson(1, 2);
		
		depth2 = personGenerator.createPerson(1, 2);
		
		Person uncleDeep1 = personGenerator.createPerson();
		Person uncleDeep2 = personGenerator.createPerson();
		uncleDeep1.addUncle(uncleDeep2);
		depth2.addUncle(uncleDeep1);				

		depth3 = (Person) depth2.clone();
		Person friendDeep1 = personGenerator.createPerson();
		Person friendDeep2 = personGenerator.createPerson();
		Person friendDeep3 = personGenerator.createPerson();
		friendDeep2.addFriend(friendDeep3);
		friendDeep1.addFriend(friendDeep2);
		depth3.addFriend(friendDeep1);
		
		personsDepth3 = new ArrayList<Person>();
		personsDepth3.add(depth3);
		personsDepth3.add(depth3);
		personsDepth3.add(depth3);
	}
	
	@Test
	public void testJsonContructionDepth0() {
		JsonObject jsonObject = JsonUtils.toJson(depth1, 0);
		
		//It should contain deep level 0 data
		assertTrue(jsonObject.containsKey("name"));
		assertTrue(jsonObject.containsKey("birthDate"));
		assertTrue(jsonObject.containsKey("id"));
		assertTrue(jsonObject.containsKey("isMale"));	
		
		//It shouldn't contain deep level 1 data
		assertFalse(jsonObject.containsKey("friends"));
		assertFalse(jsonObject.containsKey("uncles"));
		
	}
	
	@Test
	public void testJsonContructionDepth1() {
		
		JsonObject jsonObject = JsonUtils.toJson(depth1, 1);

		//It should get deep level 1 data
		assertTrue(jsonObject.containsKey("friends"));		
		assertTrue(jsonObject.get("friends").getValueType().equals(ValueType.ARRAY));
		assertTrue(jsonObject.get("uncles").getValueType().equals(ValueType.ARRAY));
		
		assertTrue(jsonObject.getJsonArray("friends").size() == 1);
		assertTrue(jsonObject.getJsonArray("uncles").size() == 2);

		//It shouldn't get deep level 2 data
		JsonObject jsonObject2 = JsonUtils.toJson(depth2, 1);		
		JsonArray uncles = jsonObject2.getJsonArray("uncles");
		
		assertTrue(uncles.size() == 3);
		
		JsonObject thirdUncle = uncles.getJsonObject(2);	
		
		assertNotNull(thirdUncle);			
		assertTrue(thirdUncle.getValueType().equals(ValueType.OBJECT));	
		assertNull(thirdUncle.getJsonArray("uncles"));
 	}	
	
	private void testDepth2(JsonObject jsonObject) {
		//It should get deep level 1 data 
		assertTrue(jsonObject.containsKey("friends"));
		
		JsonArray friendsDeep1 = jsonObject.getJsonArray("friends");
		
		assertTrue(friendsDeep1.size() == 2);
		
		JsonObject friendDeep1 = friendsDeep1.getJsonObject(1);

		//It should get deep level 2 data 
		assertTrue(friendDeep1.containsKey("friends"));
		
		JsonArray friendsDeep2 = friendDeep1.getJsonArray("friends");
		
		assertTrue(friendsDeep2.size() == 1);

		JsonObject friendDeep2 = friendsDeep2.getJsonObject(0);

		//It shouldn't get deep level 2 data 
		assertFalse(friendDeep2.containsKey("friends"));
	}

	@Test
	public void testJsonContructionDepth2() {
		JsonObject jsonObject = JsonUtils.toJson(depth3, 2);
		testDepth2(jsonObject); 
	}
	
	@Test
	public void testArrayConstructionDepth2() {
		JsonArray jsonArray = JsonUtils.toJsonArray(personsDepth3, 2);
		assertTrue(jsonArray.size() == 3);
		for(int i = 0; i < jsonArray.size(); i++) {
			testDepth2(jsonArray.getJsonObject(i));			
		}
	}
	
}
