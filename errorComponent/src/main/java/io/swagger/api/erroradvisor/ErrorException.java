package io.swagger.api.erroradvisor;

public class ErrorException extends Exception{

	private static final long serialVersionUID = 1L;

	String errorCode;

	public String getErrorCode() {
		return errorCode;
	}

	public ErrorException(String errorCode, String errorDetail) {
		super(errorDetail);
		this.errorCode=errorCode;
	}

}
