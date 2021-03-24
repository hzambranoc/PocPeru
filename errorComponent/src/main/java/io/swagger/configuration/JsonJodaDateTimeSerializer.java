package io.swagger.configuration;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonJodaDateTimeSerializer extends JsonSerializer<DateTime> {

    private static DateTimeFormatter formatter = ISODateTimeFormat.dateTime();

    @Override
    public void serialize(DateTime value, JsonGenerator gen, SerializerProvider arg2)
            throws IOException, JsonProcessingException {
    	try {
    		System.out.println("-----------"+formatter.print(value));
    		 gen.writeString(formatter.print(value));
    	}catch (Exception e) {
			e.printStackTrace();
		}
       
    }

}
 