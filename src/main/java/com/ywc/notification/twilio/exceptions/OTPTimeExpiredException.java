package com.ywc.notification.twilio.exceptions;

public class OTPTimeExpiredException extends Exception {

	private static final long serialVersionUID = 3720316007833155055L;

	public OTPTimeExpiredException() {
		super();
	}

	public OTPTimeExpiredException(final String message) {
		super(message);
	}

}
