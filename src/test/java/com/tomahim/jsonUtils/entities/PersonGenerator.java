package com.tomahim.jsonUtils.entities;

import java.util.Date;
import java.util.Random;

public class PersonGenerator {

	public String[] names = {
	  "Aaron", "Abbey", "Abbie", "Abby", "Abdul", "Abe", "Abel", "Abigail", "Abraham", "Abram", "Ada", 
	  "Adah", "Adalberto","Adaline", "Adan", "Addie", "Adela", "Adelaida", "Adelaide", "Adele", "Adelia",
	  "Adelina", "Adeline", "Adell"
	};
	
	public String randomName() {
		int count = names.length;
		return names[new Random().nextInt(count - 1)];
	}
	
	public int randomInt(int min, int max){
	  return new Random().nextInt(max - min + 1) + min;
	}
	
	public boolean randomBoolean() {
		return randomInt(0, 1) == 0 ? false : true;
	}
	
	public Date randomDate() {
		return new Date();
	}
	
	public Person createPerson() {
		return createPerson(randomInt(0, 999), randomName(), randomBoolean(), randomDate(), 0, 0);
	}
	
	public Person createPerson(String name) {
		return createPerson(randomInt(0, 999), name, randomBoolean(), randomDate(), 0, 0);
	}

	public Person createPerson(Date birthDate) {
		return createPerson(randomInt(0, 999), randomName(), randomBoolean(), birthDate, 0, 0);
	}
	
	public Person createPerson(int nbFriends, int nbUncles) {
		return createPerson(randomInt(0, 999), randomName(), randomBoolean(), randomDate(), nbFriends, nbUncles);
	}
	
	public Person createPerson(Integer id, String name, Boolean isMale, Date birthDate, int nbFriends, int nbUncles) {		
		Person person = new Person();
		person.setId(id);
		person.setIsMale(isMale);
		person.setName(name);
		person.setBirthDate(birthDate);
		for(int i = 0; i < nbFriends; i++) {
			person.addFriend(createPerson());
		}
		for(int i = 0; i < nbUncles; i++) {
			person.addUncle(createPerson());
		}
		return person;
	}
}
