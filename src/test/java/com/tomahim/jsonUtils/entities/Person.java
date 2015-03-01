package com.tomahim.jsonUtils.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Person implements Cloneable {
	
		private Integer id;
		
		private String name;
		
		private Date birthDate;
		
		private String privateInfo = "It shouldn't be read! Private stuff!";
		
		private Boolean isMale;
		
		private Person mother;
		
		private List<Person> friends;
		
		private Set<Person> uncles;
		
		private int nbSisters;

		public Person() {
			this.friends = new ArrayList<Person>();
			this.uncles = new HashSet<Person>();
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getBirthDate() {
			return birthDate;
		}

		public void setBirthDate(Date birthDate) {
			this.birthDate = birthDate;
		}

		public Boolean getIsMale() {
			return isMale;
		}

		public void setIsMale(Boolean isMale) {
			this.isMale = isMale;
		}

		public Person getMother() {
			return mother;
		}

		public void setMother(Person mother) {
			this.mother = mother;
		}

		public List<Person> getFriends() {
			return friends;
		}

		public void setFriends(List<Person> friends) {
			this.friends = friends;
		}		
		
		public void addFriend(Person friend) {
			this.friends.add(friend);
		}
		
		public Set<Person> getUncles() {
			return uncles;
		}

		public void setUncles(Set<Person> uncles) {
			this.uncles = uncles;
		}
		
		public void addUncle(Person uncle) {
			this.uncles.add(uncle);
		}
		
		public int getNbSisters() {
			return nbSisters;
		}

		public void setNbSisters(int nbSisters) {
			this.nbSisters = nbSisters;
		}

		public Object clone() {
			Object o = null;
			try {
				o = super.clone();
			} catch(CloneNotSupportedException cnse) {
				cnse.printStackTrace(System.err);
			}
			return o;
		}
	}