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
		return createPerson(randomInt(0, 999), randomName(), randomBoolean(), randomDate(), createPersonWithNoMother(), 0, 0, randomInt(0, 10));
	}
	
	public Person createPerson(String name) {
		return createPerson(randomInt(0, 999), name, randomBoolean(), randomDate(), createPersonWithNoMother(), 0, 0, randomInt(0, 10));
	}

	public Person createPerson(Date birthDate) {
		return createPerson(randomInt(0, 999), randomName(), randomBoolean(), birthDate, createPersonWithNoMother(), 0, 0, randomInt(0, 10));
	}

	public Person createPersonWithNoMother() {
		return createPerson(randomInt(0, 999), randomName(), randomBoolean(), randomDate(), null, 0, 0, randomInt(0, 10));
	}
	
	public Person createPerson(int nbFriends, int nbUncles) {
		return createPerson(randomInt(0, 999), randomName(), randomBoolean(), randomDate(), createPerson(), nbFriends, nbUncles, randomInt(0, 10));
	}
	
	public Person createPerson(Integer id, String name, Boolean isMale, Date birthDate, Person mother, int nbFriends, int nbUncles, int nbSisters) {		
		Person person = new Person();
		person.setId(id);
		person.setIsMale(isMale);
		person.setName(name);
		person.setBirthDate(birthDate);
		if(mother != null) {
			person.setMother(mother);
		}
		for(int i = 0; i < nbFriends; i++) {
			person.addFriend(createPerson());
		}
		for(int i = 0; i < nbUncles; i++) {
			person.addUncle(createPerson());
		}
		person.setNbSisters(nbSisters);
		return person;
	}
}
