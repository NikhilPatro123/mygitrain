package com.ywc.notification.twilio.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OTPModel implements Serializable {

	private static final long serialVersionUID = 7539768019027572340L;

	private String phoneNumber;
	@JsonIgnore
	private String otp;
	@JsonIgnore
	private long expiryTime;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public long getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}

}
