package org.oneicy.exception;

public class ExampleException extends RuntimeException {
	private String exceptionMessage;

	public ExampleException(String message) {
		super(message);
		this.exceptionMessage = message;
	}

	public ExampleException() {
		this("");
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
}
