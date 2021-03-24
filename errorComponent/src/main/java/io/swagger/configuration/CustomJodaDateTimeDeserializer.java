package io.swagger.configuration;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomJodaDateTimeDeserializer extends JsonDeserializer<DateTime>{
	  private static DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
	  
	    @Override
	    public DateTime deserialize(JsonParser jsonparser,
	            DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {
	    	
	        String date = jsonparser.getText();
	        return formatter.parseDateTime(date);
	    }
	}
