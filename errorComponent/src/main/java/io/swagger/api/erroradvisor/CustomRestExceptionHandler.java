package io.swagger.api.erroradvisor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * load messages properties
	 */
	@Autowired
	Messages messages;

	HashMap<String, String> mapDetail = new HashMap<String, String>();
	
	MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
	
   	@Autowired 
   	HttpServletRequest request;
	

	
	private final static Logger LOGGER = LoggerFactory.getLogger(CustomRestExceptionHandler.class.getName());

	private static Map<String, String> mapApiName;
	
	static{
		Map<String, String> mapApiNameTmp = new HashMap<String, String>();
		mapApiNameTmp.put("invoice", "/.../invoice../v1");
		mapApiNameTmp.put("customer", "/../customer/v1");

		mapApiName = Collections.unmodifiableMap(mapApiNameTmp);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));
		mapDetail.put("detail", builder.substring(0, builder.length() - 2));
//		mapDetail.put("serviceName", request.getDescription(true));
		mapDetail.put("serviceName", getServiceName(request.getContextPath(), request));
		mapDetail.put("apiName", getApiName(request.getContextPath()));
		mapDetail.put("appUser", request.getRemoteUser());
		mapDetail.put("ejecId", getEjecId(request));
		LOGGER.error("Error in Class: " + this.getClass().getName() + " produced by Exeption type of: "  + ex.getClass().getCanonicalName());
		LOGGER.error("Error trace:", ex);
		ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, getCodeError(HttpStatus.UNSUPPORTED_MEDIA_TYPE), getMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE), mapDetail,"POL0011");
		mapDetail.clear();
		return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}


	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		mapDetail.put("detail", ex.getMessage());
//		mapDetail.put("serviceName", request.getDescription(true));
		mapDetail.put("serviceName", getServiceName(request.getContextPath(), request));
		mapDetail.put("apiName", getApiName(request.getContextPath()));
		mapDetail.put("appUser", request.getRemoteUser());
		mapDetail.put("ejecId", getEjecId(request));
		ApiError apiError =new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.BAD_REQUEST), getMessage(HttpStatus.BAD_REQUEST), mapDetail,"SVC1000");
		LOGGER.error("Error in Class: " + this.getClass().getName() + " produced by Exeption type of: "  + ex.getClass().getCanonicalName());
		LOGGER.error("Error trace:", ex);
		mapDetail.clear();
		return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		mapDetail.put("detail", ex.getMessage());
//		mapDetail.put("serviceName", request.getDescription(true));
		mapDetail.put("serviceName", getServiceName(request.getContextPath(), request));
		mapDetail.put("apiName", getApiName(request.getContextPath()));
		mapDetail.put("appUser", request.getRemoteUser());
		mapDetail.put("ejecId", getEjecId(request));
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, getCodeError(HttpStatus.INTERNAL_SERVER_ERROR), getMessage(HttpStatus.INTERNAL_SERVER_ERROR), mapDetail,"SVR1000");
		LOGGER.error("Error in Class: " + this.getClass().getName() + " produced by Exeption type of: "  + ex.getClass().getCanonicalName());
		LOGGER.error("Error trace:", ex);
		mapDetail.clear();
		return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		mapDetail.put("detail", ex.getMessage());
		mapDetail.put("ejecId", getEjecId(request));
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, getCodeError(HttpStatus.INTERNAL_SERVER_ERROR), getMessage(HttpStatus.INTERNAL_SERVER_ERROR), mapDetail,"SVR1000");
		LOGGER.error("Error in Class: " + this.getClass().getName() + " produced by Exeption type of: "  + ex.getClass().getCanonicalName());
		LOGGER.error("Error trace:", ex);
		mapDetail.clear();
		return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

//		String error = extractErrorMessageFromSubSTR(ex,"nested ");
//		mapDetail.put("serviceName", request.getDescription(true));
		mapDetail.put("serviceName", getServiceName(request.getContextPath(), request));
		mapDetail.put("apiName", getApiName(request.getContextPath()));
		mapDetail.put("appUser", request.getRemoteUser());
		mapDetail.put("ejecId", getEjecId(request));
		LOGGER.error("Error in Class: " + this.getClass().getName() + " produced by Exeption type of: "  + ex.getClass().getCanonicalName());
		LOGGER.error("Error trace:", ex);

		if(ex.getLocalizedMessage().contains("ignorable")) {
			mapDetail.put("detail", "Invalid parameter.");
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.BAD_REQUEST), getMessage(HttpStatus.BAD_REQUEST), mapDetail,"SVC1001");
			mapDetail.clear();
			return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.BAD_REQUEST);
		}else if (ex.getLocalizedMessage().contains("Unexpected token")) {
			mapDetail.put("detail", "Invalid parameter.");
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.BAD_REQUEST), getMessage(HttpStatus.BAD_REQUEST), mapDetail,"SVC0002");
			mapDetail.clear();
			return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.BAD_REQUEST);
		}else if (ex.getLocalizedMessage().contains("has no corresponding value")) {
			mapDetail.put("detail", "Invalid parameter.");
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.BAD_REQUEST), getMessage(HttpStatus.BAD_REQUEST), mapDetail,"SVC0003");
			mapDetail.clear();
			return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.BAD_REQUEST);
		}else if (ex.getLocalizedMessage().contains("Can not construct instance of")) {
			mapDetail.put("detail", "Content not well-formed");
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.BAD_REQUEST), getMessage(HttpStatus.BAD_REQUEST), mapDetail,"SVC1023");
			mapDetail.clear();
			return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.BAD_REQUEST);
		}else if (ex.getLocalizedMessage().contains("Can not deserialize")){
			mapDetail.put("detail", "Invalid parameter value.");
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.BAD_REQUEST), getMessage(HttpStatus.BAD_REQUEST), mapDetail,"SVC0002");
			mapDetail.clear();
			return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.BAD_REQUEST);
		}else if (ex.getLocalizedMessage().contains("Unrecognized token")){
			mapDetail.put("detail", "Invalid parameter value.");
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.BAD_REQUEST), getMessage(HttpStatus.BAD_REQUEST), mapDetail,"SVC0002");
			mapDetail.clear();
			return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.BAD_REQUEST);
		}else {
			mapDetail.put("detail", "T-Open API Generic wildcard fault response");
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.INTERNAL_SERVER_ERROR), getMessage(HttpStatus.INTERNAL_SERVER_ERROR), mapDetail,"SVR1000");
			mapDetail.clear();
			return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.BAD_REQUEST);
		}
	}

//	private String extractErrorMessageFromSubSTR(HttpMessageNotReadableException ex, String str2Search) {
//		int pos=ex.getMessage().indexOf(str2Search);
//		String error=ex.getMessage().substring(0, pos);
//		return error;
//	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		mapDetail.put("detail", "Missing mandatory parameter: %1");
//		mapDetail.put("serviceName", request.getDescription(true));
		mapDetail.put("serviceName", getServiceName(request.getContextPath(), request));
		mapDetail.put("apiName", getApiName(request.getContextPath()));
		mapDetail.put("appUser", request.getRemoteUser());
		mapDetail.put("ejecId", getEjecId(request));
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.BAD_REQUEST), getMessage(HttpStatus.BAD_REQUEST), mapDetail,"SVC1000");
		LOGGER.error("Error in Class: " + this.getClass().getName() + " produced by Exeption type of: "  + ex.getClass().getCanonicalName());
		LOGGER.error("Error trace:", ex);
		mapDetail.clear();
		return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		mapDetail.put("detail", "Missing mandatory parameter: %1");
//		mapDetail.put("serviceName", request.getDescription(true));
		mapDetail.put("serviceName", getServiceName(request.getContextPath(), request));
		mapDetail.put("apiName", getApiName(request.getContextPath()));
		mapDetail.put("appUser", request.getRemoteUser());
		mapDetail.put("ejecId", getEjecId(request));
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.BAD_REQUEST), getMessage(HttpStatus.BAD_REQUEST), mapDetail,"SVC1000");
		LOGGER.error("Error in Class: " + this.getClass().getName() + " produced by Exeption type of: "  + ex.getClass().getCanonicalName());
		LOGGER.error("Error trace:", ex);
		mapDetail.clear();
		return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,	HttpHeaders headers, HttpStatus status, WebRequest request) {
		mapDetail.put("detail", "Missing mandatory parameter: %1");
		mapDetail.put("ejecId", getEjecId(request));
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.BAD_REQUEST), getMessage(HttpStatus.BAD_REQUEST), mapDetail,"SVC1000");
		LOGGER.error("Error in Class: " + this.getClass().getName() + " produced by Exeption type of: "  + ex.getClass().getCanonicalName());
		LOGGER.error("Error trace:", ex);
		mapDetail.clear();
		return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		mapDetail.put("detail", "Missing mandatory parameter: %1");
//		mapDetail.put("serviceName", request.getDescription(true));
		mapDetail.put("serviceName", getServiceName(request.getContextPath(), request));
		mapDetail.put("apiName", getApiName(request.getContextPath()));
		mapDetail.put("appUser", request.getRemoteUser());
		mapDetail.put("ejecId", getEjecId(request));
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.BAD_REQUEST), getMessage(HttpStatus.BAD_REQUEST), mapDetail,"SVC1000");
		LOGGER.error("Error in Class: " + this.getClass().getName() + " produced by Exeption type of: "  + ex.getClass().getCanonicalName());
		LOGGER.error("Error trace:", ex);
		mapDetail.clear();
		return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String errors = "";
		@SuppressWarnings("unused")
		String det="";
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.concat(error.getField() + ": " + error.getDefaultMessage());
			det=error.getField();
			errors.concat("\n");
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.concat(error.getObjectName() + ": " + error.getDefaultMessage());
			errors.concat("\n");
		}
		mapDetail.put("detail", "Missing Mandatory Parameter");
//		mapDetail.put("serviceName", request.getDescription(true));
		mapDetail.put("serviceName", getServiceName(request.getContextPath(), request));
		mapDetail.put("apiName", getApiName(request.getContextPath()));
		mapDetail.put("appUser", request.getRemoteUser());
		mapDetail.put("ejecId", getEjecId(request));
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.BAD_REQUEST), getMessage(HttpStatus.BAD_REQUEST), mapDetail,"SVC1000");
		LOGGER.error("Error in Class: " + this.getClass().getName() + " produced by Exeption type of: "  + ex.getClass().getCanonicalName());
		LOGGER.error("Error trace:", ex);
		mapDetail.clear();
		return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";
		mapDetail.put("detail", error);
		mapDetail.put("apiName", getApiName(request.getContextPath()));
//		mapDetail.put("serviceName", request.getDescription(true));
		mapDetail.put("serviceName", getServiceName(request.getContextPath(), request));
		mapDetail.put("appUser", request.getRemoteUser());
		mapDetail.put("ejecId", getEjecId(request));
//		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.BAD_REQUEST), getMessage(HttpStatus.BAD_REQUEST), mapDetail,"SVC1001");
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "INVALID_ARGUMENT", getMessage("INVALID_ARGUMENT"), mapDetail,"SVC1001");
		LOGGER.error("Error in Class: " + this.getClass().getName() + " produced by Exeption type of: "  + ex.getClass().getCanonicalName());
		LOGGER.error("Error trace:", ex);
		mapDetail.clear();
		return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		String errors = "";
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.concat(violation.getRootBeanClass().getName() + " " +
					violation.getPropertyPath() + ": " + violation.getMessage());
			errors.concat("\n");
		}

		mapDetail.put("detail", errors);
//		mapDetail.put("serviceName", request.getDescription(true));
		mapDetail.put("serviceName", getServiceName(request.getContextPath(), request));
		mapDetail.put("apiName", getApiName(request.getContextPath()));
		mapDetail.put("appUser", request.getRemoteUser());
		mapDetail.put("ejecId", getEjecId(request));
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.BAD_REQUEST), getMessage(HttpStatus.BAD_REQUEST), mapDetail,"SVC1023");
		LOGGER.error("Error in Class: " + this.getClass().getName() + " produced by Exeption type of: "  + ex.getClass().getCanonicalName());
		LOGGER.error("Error trace:", ex);
		mapDetail.clear();
		return new ResponseEntity<ApiError>(apiError, addXCorrelatoHeader(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<ApiError> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
		String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		mapDetail.put("detail", error);
//		mapDetail.put("serviceName", request.getDescription(true));
		mapDetail.put("serviceName", getServiceName(request.getContextPath(), request));
		mapDetail.put("apiName", getApiName(request.getContextPath()));
		mapDetail.put("appUser", request.getRemoteUser());
		mapDetail.put("ejecId", getEjecId(request));
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.BAD_REQUEST), getMessage(HttpStatus.BAD_REQUEST), mapDetail,"SVC0003");
		LOGGER.error("Error in Class: " + this.getClass().getName() + " produced by Exeption type of: "  + ex.getClass().getCanonicalName());
		LOGGER.error("Error trace:", ex);
		mapDetail.clear();
		return new ResponseEntity<ApiError>(apiError, addXCorrelatoHeader(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
		mapDetail.put("detail", error);
//		mapDetail.put("serviceName", request.getDescription(true));
		mapDetail.put("serviceName", getServiceName(request.getContextPath(), request));
		mapDetail.put("apiName", getApiName(request.getContextPath()));
		mapDetail.put("appUser", request.getRemoteUser());
		mapDetail.put("ejecId", getEjecId(request));
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, getCodeError(HttpStatus.NOT_FOUND), getMessage(HttpStatus.NOT_FOUND), mapDetail,"SVC1006");
		mapDetail.clear();
		return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		mapDetail.put("detail", builder.toString());
//		mapDetail.put("serviceName", request.getDescription(true));
		mapDetail.put("serviceName", getServiceName(request.getContextPath(), request));
		mapDetail.put("apiName", getApiName(request.getContextPath()));
		mapDetail.put("appUser", request.getRemoteUser());
		mapDetail.put("ejecId", getEjecId(request));
		ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, getCodeError(HttpStatus.METHOD_NOT_ALLOWED), getMessage(HttpStatus.METHOD_NOT_ALLOWED), mapDetail,"SVC 1003");
		LOGGER.error("Error in Class: " + this.getClass().getName() + " produced by Exeption type of: "  + ex.getClass().getCanonicalName());
		LOGGER.error("Error trace:", ex);
		mapDetail.clear();
		return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

		mapDetail.put("detail", builder.substring(0, builder.length() - 2));
//		mapDetail.put("serviceName", request.getDescription(true));
		mapDetail.put("serviceName", getServiceName(request.getContextPath(), request));
		mapDetail.put("apiName", getApiName(request.getContextPath()));
		mapDetail.put("appUser", request.getRemoteUser());
		mapDetail.put("ejecId", getEjecId(request));
		ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, getCodeError(HttpStatus.UNSUPPORTED_MEDIA_TYPE), getMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE), mapDetail,"POL0011");
		LOGGER.error("Error in Class: " + this.getClass().getName() + " produced by Exeption type of: "  + ex.getClass().getCanonicalName());
		LOGGER.error("Error trace:", ex);
		mapDetail.clear();
		return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	//HZC: Management of Business's Layer
	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {

//		mapDetail.put("serviceName", request.getDescription(true));
		mapDetail.put("serviceName", getServiceName(request.getContextPath(), request));
		mapDetail.put("apiName", getApiName(request.getContextPath()));
		mapDetail.put("appUser", request.getRemoteUser());
		mapDetail.put("ejecId", getEjecId(request));
		LOGGER.info("Error in Class: " + this.getClass().getName() + " produced by Exeption type of: "  + ex.getClass().getCanonicalName());
		LOGGER.error("Error trace:", ex);
		
		if (ex instanceof ErrorException) {
			//Error Code defined by application
			String strErrorCode = ((ErrorException) ex).getErrorCode();
			String strErrorMessage = ((ErrorException) ex).getMessage();
			
			if (strErrorCode.startsWith("INVALID_")) {
				mapDetail.put("detail", strErrorMessage);
				
				if("INVALID_X_TOKEN_INFO".equals(strErrorCode)){
					ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, strErrorCode, strErrorMessage, mapDetail, strErrorCode);
					mapDetail.clear();
					return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.FORBIDDEN);
				}else if("INVALID_UNAUTHORIZED".equals(strErrorCode)){
					ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, strErrorCode, strErrorMessage, mapDetail, strErrorCode);
					mapDetail.clear();
					return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.FORBIDDEN);
				}else if("INVALID_NOT_FOUND".equals(strErrorCode) || "INVALID_USERID".equals(strErrorCode)){
					ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, getCodeError(HttpStatus.NOT_FOUND), getMessage(HttpStatus.NOT_FOUND), mapDetail, getCodeError(HttpStatus.NOT_FOUND));
					mapDetail.clear();
					return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.NOT_FOUND);
				}else {
					ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, strErrorCode, strErrorMessage, mapDetail, strErrorCode);
					mapDetail.clear();
					return new ResponseEntity<Object>(apiError,addXCorrelatoHeader(), HttpStatus.BAD_REQUEST);
				}
			}else {
//				String strErrorCode = strErrorCode;
				String strMessage = getMessage(strErrorCode);
				mapDetail.put("detail", strMessage);
				ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getCodeError(HttpStatus.BAD_REQUEST), strMessage, mapDetail, strErrorCode);
				mapDetail.clear();
				return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.BAD_REQUEST);
			}
		}else {
			//Error Code not defined 
			mapDetail.put("detail",  ex.getLocalizedMessage());
			String strExctId=ex.getMessage()==null?"":ex.getMessage();
			mapDetail.put("detail", strExctId);
			ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, getCodeError(HttpStatus.INTERNAL_SERVER_ERROR), getMessage(HttpStatus.INTERNAL_SERVER_ERROR), mapDetail, strExctId);	    
			mapDetail.clear();
			return new ResponseEntity<Object>(apiError, addXCorrelatoHeader(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	/**
	 *
	 * @param codMessage
	 * @return code defined in properties
	 */
	private String getMessage(String codMessage) {
		return messages.get(codMessage);
	}

	
	/**
	 *
	 * @param httpStatus
	 * @return code defined in properties
	 */
	private String getMessage(HttpStatus httpStatus) {
		return messages.get(httpStatus.value()+".message");
	}

	/**
	 *
	 * @param httpStatus
	 * @return description defined in properties
	 */
	private String getCodeError(HttpStatus httpStatus) {
		return messages.get(httpStatus.value()+".code");
	}
	
	
	/**
	 * Obtain the path of the context 
	 * @param context
	 * @param description
	 * @return
	 */
	private static String getUrl(String context, String description) {
		String[] bbb= description.split(";");
		String uri=bbb[0];
		String find="uri="+context;
		String url=uri.substring(find.length());
		return url;
	}
	
	
	/**
	 * Obtain the url of the service for the validation 
	 * @param path
	 * @param request
	 * @return
	 */
	private String getServiceName(String path, WebRequest request) {
		path=getUrl(path, request.getDescription(true));
		String strReturn="";
		String strQueryString="";
		Map<String, String[]>  map = request.getParameterMap();
		
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
		    String key = entry.getKey();
		    String[] value = entry.getValue();
		    strQueryString += key + "=" + value[0]+ "&";
		}
		
		if(strQueryString.length()>0) {
			strQueryString = strQueryString.substring(0, strQueryString.lastIndexOf("&"));
			strReturn = path.concat("?").concat(strQueryString);
		}else {
			strReturn = path;
		}

		return strReturn;
	}
	
   	private MultiValueMap<String, String> addXCorrelatoHeader(){
		if ((request.getHeader("x-correlator")!=null)) {			
			if (responseHeaders.containsKey("x-correlator")) {
				responseHeaders.remove("x-correlator");
			}
			responseHeaders.add("x-correlator",request.getHeader("x-correlator"));
		}
		return responseHeaders;
	}
   	
	private String getEjecId(WebRequest request) {
		if(request.getHeader("x-correlator")!=null) {
			return request.getHeader("x-correlator");
		}else {
			return UUID.randomUUID().toString();
		}
	}
	
	/**
	 * Get name of the apiName form contextPath
	 * @param contextPath
	 * @return
	 */
	private String getApiName(String contextPath) {
		String result = null;

		for (Map.Entry<String, String> entry : mapApiName.entrySet()) {
			if (contextPath.equals(entry.getValue())) {
				result = entry.getKey();
			}
		}

		result = (result == null) ? contextPath : result;
		return result;
	}
	
}