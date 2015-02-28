package com.tomahim.jsonUtils.configuration;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Test;

import com.tomahim.jsonUtils.api.JsonUtils;
import com.tomahim.jsonUtils.entities.Person;
import com.tomahim.jsonUtils.entities.PersonGenerator;

public class DateFormatSettingsTest {
	
	public PersonGenerator personGenerator;
	public Person p1;	
	public Date birthDate;
	
	@Before
	public void init() {
		JsonUtilsSettings.resetSettings();
		personGenerator = new PersonGenerator();
		birthDate = new Date();
		p1 = personGenerator.createPerson(birthDate);
	}	
	
	@Test
	public void testDateFormatSetting() {
		String dateFormat = "yyyy-MM-dd";
		JsonUtilsSettings.setValue(SettingsEnum.DATE_FORMAT, dateFormat);
		
		JsonObject jsonObject = JsonUtils.toJson(p1, "birthDate");

		SimpleDateFormat dt = new SimpleDateFormat(dateFormat);
		String expected = dt.format(birthDate);
		assertEquals(expected, jsonObject.getString("birthDate"));
	}
}
