package com.everis.archivado.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.IOException;
import java.util.TimeZone;

public class TestJodaDateTimeDeSerializer extends JsonDeserializer<DateTime> {

	@Override
	public DateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
    		org.joda.time.format.DateTimeFormatter elasticDateFormat = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZZ").
    				withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT-5")));
			String date = p.getText();
			System.out.println("-----DESERIALIZER------"+date);
			return elasticDateFormat.parseDateTime(date);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
 