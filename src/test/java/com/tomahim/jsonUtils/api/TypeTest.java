package com.tomahim.jsonUtils.api;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Test;

import com.tomahim.jsonUtils.entities.AllTypes;

public class TypeTest {
	
	AllTypes types;
	
	public static Boolean typeBoolean = true;
	public static Character typeCharacter = new Character('c');
	public static Byte typeByte = (byte) 0xe0;
	public static Date typeDate = new Date();
	public static Short typeShort = 1;
	public static Integer typeInteger = 1;
	public static Long typeLong = new Long(1);
	public static Float typeFloat = new Float(1.2);

	public static Double typeDouble = new Double(2);
	public static BigDecimal typeBigDecimal = new BigDecimal(2);
	public static String typeString = "string";

	public static int primitiveInt = 1;
	public static boolean primitiveBoolean = true;
	public static byte primitiveByte = (byte) 0xe0;
	public static long primitiveLong = 1;
	public static float primitiveFloat = (float) 1.2;
	public static double primitiveDouble = 2;
	public static char primitiveChar = 'c';
	
	public JsonObject jsonObject;
	
	@Before
	public void init() {
		types = new AllTypes(typeBoolean, typeCharacter, typeByte, typeDate, typeShort, typeInteger, typeLong, 
							typeFloat, typeDouble, typeBigDecimal, typeString, primitiveInt, primitiveBoolean, 
							primitiveByte, primitiveLong, primitiveFloat, primitiveDouble, primitiveChar);
		jsonObject = JsonUtils.toJson(types);		
	}
	
	@Test
	public void notNull() {
		assertNotNull(jsonObject);
		String[] keys = {"typeBoolean", "typeCharacter", "typeByte", "typeDate", "typeShort", 
				"typeInteger", "typeLong", "typeFloat", "typeDouble", "typeBigDecimal", 
				"typeString", "primitiveInt", "primitiveBoolean", "primitiveByte", 
				"primitiveLong", "primitiveFloat", "primitiveDouble", "primitiveChar"};
		for(String key : keys) {
			assertTrue(key + " not supported", jsonObject.containsKey(key));
		}
	}
	
	//TODO : one method per type checking values of each
}
