package com.everis.archivado.config;

import io.swagger.api.erroradvisor.ErrorException;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.util.HashMap;

public class Util {
	
    /**
     * validate dates obtain in String formats
     * @param mapStringDates
     * @return datetime
     * @throws ErrorException
     */
    public static HashMap<String, DateTime> validateDatesAndGetDates(HashMap<String, String> mapStringDates) throws ErrorException {
    	
    	HashMap<String, DateTime> mapDateTimeDates = new HashMap<String, DateTime>();
    	
    	if(!mapStringDates.isEmpty()) {
        	DateTimeFormatter sdf1 = new DateTimeFormatterBuilder()
        		    .appendOptional(org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").getParser())
        		    .appendOptional(org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'").getParser())
        		    .appendOptional(org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ").getParser())
					.appendOptional(org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd").getParser())
        		    .toFormatter();   	
        	
    	    	for (String key : mapStringDates.keySet()) {
    	    		LocalDate.parse( mapStringDates.get(key), sdf1).toDateTimeAtStartOfDay().toLocalDateTime();
    	    	}
    	    	
    	    	for (String key : mapStringDates.keySet()) {
    	    		LocalDate.parse( mapStringDates.get(key), sdf1).toDateTimeAtStartOfDay().toLocalDateTime();
    	    		mapDateTimeDates.put(key, sdf1.parseDateTime( mapStringDates.get(key)));
    	    	}
    	    	
    	}
	    
		return mapDateTimeDates;
	}

}
