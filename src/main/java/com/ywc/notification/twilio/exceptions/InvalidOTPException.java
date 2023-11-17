package com.ywc.notification.twilio.exceptions;

public class InvalidOTPException extends Exception {

	private static final long serialVersionUID = 5650160564071677628L;

	public InvalidOTPException() {

		super();
	}

	public InvalidOTPException(final String message) {
		super(message);
	}
}
