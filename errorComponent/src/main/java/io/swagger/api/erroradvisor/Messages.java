package io.swagger.api.erroradvisor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class Messages {

    @Autowired
    private MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource);
    }

    public String get(String codeMessage) {
    	String strRetorno = "Message not defined in properties";
        try {
        	strRetorno = accessor.getMessage(codeMessage);
		} catch (NoSuchMessageException e) {
		}
        return strRetorno;
    }

}