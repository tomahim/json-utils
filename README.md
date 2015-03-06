# json-utils
Json utility java classes for Json-binding.

Experimental work, no stable realease yet.

#### Todo

##### In progress

- Be sure to keep initial primary type of value when converting to Json
 
- Unit test and better handle of deep field selection with Collections. Four cases :
  - "list.name" : it should return an array like ["Toto", "Tata", "Another name"]
  - "list" : it should return the entire array
  - "list[id, name]" : it should return an array like [{id : 1, name : "Toto"}, {id : 2, name : "Tata"}]

- Unit test for exception cases
- Optimize method finding by constructing a Map<String, Method> (example : <"person.name", methodObject>) or Map<Class, Entry<String, Method>> ?
- Create interface for JsonUtils

##### New features / Ideas

- Create java object from Json object

#### Usefull resources 

##### Maven

http://www.mkyong.com/maven/how-to-include-library-manully-into-maven-local-repository/
http://www.eclemma.org/jacoco/trunk/doc/maven.html

##### Code coverage

http://www.eclemma.org/
