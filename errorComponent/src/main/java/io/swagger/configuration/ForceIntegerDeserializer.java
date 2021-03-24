package io.swagger.configuration;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ForceIntegerDeserializer extends JsonDeserializer<Integer> {

	    @SuppressWarnings("deprecation")
		@Override
	    public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
	        if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING) {
	            throw deserializationContext.wrongTokenException(jsonParser, JsonToken.VALUE_NUMBER_INT, "Attempted to parse String to Integer but this is forbidden");
	        }
	        return jsonParser.getValueAsInt();
	    }

}
 