package com.everis.archivado.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.TimeZone;

public class TestJodaDateTimeSerializer extends JsonSerializer<DateTime> {

    //private static DateTimeFormatter formatter = ISODateTimeFormat.dateTime();

    private String timeZone;
	
    @Autowired
	public TestJodaDateTimeSerializer(String timeZone) {
		this.timeZone = timeZone;
	}

    @Override
    public void serialize(DateTime value, JsonGenerator gen, SerializerProvider arg2)
            throws IOException, JsonProcessingException {
    	try {
//    		System.out.println("---------Without Format:" + value.toString() );
//    		LocalDateTime ldt = value.toLocalDateTime();
    		
    		
    		org.joda.time.format.DateTimeFormatter elasticDateFormat = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZZ").
    				withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone(timeZone)));
    		
//    		System.out.println("-----SERIALIZER------"+elasticDateFormat.print(value));
    		
    		 gen.writeString(elasticDateFormat.print(value));
    	}catch (Exception e) {
			e.printStackTrace();
		}
       
    }

}
 