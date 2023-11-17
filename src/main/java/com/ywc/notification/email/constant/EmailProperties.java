package com.ywc.notification.email.constant;

public enum EmailProperties {

	TRANSPORT_PROTOCOL("mail.transport.protocol"),
	AUTH("mail.smtp.auth"),
	START_TLS_ENABLE("mail.smtp.starttls.enable"),
	CONNECTION_TIMEOUT("mail.smtp.connectiontimeout"),
	TIMEOUT("mail.smtp.timeout"),
	WRITE_TIMEOUT(""),
	DEBUG("mail.debug")
	;

	private String property;
	
	EmailProperties(String property) {
		this.property = property;
	}
	
	public String getProperty() {
		return property;
	}
}
