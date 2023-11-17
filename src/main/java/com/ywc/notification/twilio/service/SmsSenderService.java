package com.ywc.notification.twilio.service;

import javax.naming.ServiceUnavailableException;

import com.ywc.notification.twilio.dto.OTPModel;
import com.ywc.notification.twilio.dto.SmsRequest;
import com.ywc.notification.twilio.exceptions.InvalidOTPException;
import com.ywc.notification.twilio.exceptions.OTPTimeExpiredException;

public interface SmsSenderService {
	
	void sendSms(SmsRequest smsRequest) throws ServiceUnavailableException;
	
	OTPModel generateOtp(String phoneNumber) throws ServiceUnavailableException;
	
	String verifyOtp(String phoneNumber, String otp) throws ServiceUnavailableException, OTPTimeExpiredException, InvalidOTPException;

}
