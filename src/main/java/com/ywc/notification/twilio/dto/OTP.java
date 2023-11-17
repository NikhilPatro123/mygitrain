package com.ywc.notification.twilio.dto;

import java.io.Serializable;

public class OTP implements Serializable {

	private static final long serialVersionUID = -1892101994914087085L;

	private String otp;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

}
