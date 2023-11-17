package com.ywc.notification.twilio.exceptions;

public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 3268284292430576293L;

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(final String message) {
		super(message);
	}
}
