package io.swagger.api.erroradvisor;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

public class ApiError implements Serializable{

    private final static Logger LOGGER = LoggerFactory.getLogger(ApiError.class.getName());

    private static final long serialVersionUID = 1L;

    private String code;
    private String message;
    private InetAddress localHost = null;
    @SuppressWarnings("unused")
	private HashMap<String,String> details;
    @SuppressWarnings("unused")
	private HttpStatus httpCode;

    public ApiError(HttpStatus status, String idCode , String descriptionCode, HashMap<String,String > details, String exctId)  {
        super();
        this.httpCode = status;
        this.code = idCode;
        this.message = descriptionCode;
        this.details = details;
        try {
            localHost = InetAddress.getLocalHost();
            if(MDC.get("nameServer").isEmpty());
        } catch (Exception e) {
            MDC.put("nameServer", localHost.getHostName());
            MDC.put("ip", localHost.getHostAddress());
            MDC.put("originSystem", "4P");
            MDC.put("appUser", "ErrorAdvisor");
            MDC.put("apiName", details.get("apiName"));
//            MDC.put("serviceName", getUrl(details.get("apiName"), details.get("serviceName")));
            MDC.put("serviceName", details.get("serviceName"));
//            MDC.put("serviceName", details.get("apiName"));
            MDC.put("timeServiceResponse", "0");
            MDC.put("timeBackendResponse", "0");
            MDC.put("ejecId", details.get("ejecId"));
        } finally {
            MDC.put("errorCode", idCode);
            MDC.put("errorDesc", descriptionCode.concat(": ").concat(details.get("detail")).concat(": ".concat(exctId)));
            MDC.put("resultCode", String.valueOf(status.value()));
            MDC.put("result", "KO");
            LOGGER.trace("Registro Traza Ejecucion");
            LOGGER.error("ERROR ".concat(String.valueOf(status.value()).concat(" ").concat(details.get("detail"))));
            MDC.clear();
        }


    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    
}