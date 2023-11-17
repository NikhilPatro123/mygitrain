package com.ywc.notification.twilio.exceptions;

import java.io.Serializable;

public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = -1482756656649175068L;
	private String message;
	private String uri;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
