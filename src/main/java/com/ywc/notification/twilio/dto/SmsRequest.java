package com.ywc.notification.twilio.dto;

import java.io.Serializable;

public class SmsRequest implements Serializable{

	private static final long serialVersionUID = -6192624960941964932L;
	private String phoneNumber;
	private String message;
	private String serviceProvider;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	
	
}
