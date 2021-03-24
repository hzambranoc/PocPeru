package io.swagger.configuration;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


	public class ForceStringDeserializer extends JsonDeserializer<String> {

	    @SuppressWarnings("deprecation")
		@Override
	    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
	    	try {
	            if (jsonParser.getCurrentToken() == JsonToken.VALUE_NUMBER_INT) {
		            throw deserializationContext.wrongTokenException(jsonParser, JsonToken.VALUE_STRING, "Attempted to parse int to string but this is forbidden");
		        }
	            if (jsonParser.getCurrentToken() == JsonToken.VALUE_NUMBER_FLOAT) {
		            throw deserializationContext.wrongTokenException(jsonParser, JsonToken.VALUE_STRING, "Attempted to parse int to string but this is forbidden");
		        }
		        return jsonParser.getValueAsString();
			} catch (Exception e) {
				e.printStackTrace();
				 throw deserializationContext.wrongTokenException(jsonParser, JsonToken.VALUE_STRING, "Attempted to parse int to string but this is forbidden");
			
			}
	
	    }

}
 
 