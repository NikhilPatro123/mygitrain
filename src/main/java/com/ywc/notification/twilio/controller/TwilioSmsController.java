package com.ywc.notification.twilio.controller;

import java.util.Objects;

import javax.naming.ServiceUnavailableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ywc.notification.twilio.dto.OTP;
import com.ywc.notification.twilio.dto.OTPModel;
import com.ywc.notification.twilio.dto.SmsRequest;
import com.ywc.notification.twilio.exceptions.InvalidOTPException;
import com.ywc.notification.twilio.exceptions.OTPTimeExpiredException;
import com.ywc.notification.twilio.service.SmsSenderService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
public class TwilioSmsController {

	private final static Logger LOGGER = LoggerFactory.getLogger(TwilioSmsController.class);

	@Autowired
	private SmsSenderService smsSenderService;
	

	@PostMapping("/sms/send")
	@ApiOperation(httpMethod = "POST", value = "Send sms for PhoneNumber", notes = "The phone number should start with 91")
	public ResponseEntity<Object> sendSms(@RequestBody SmsRequest smsRequest) throws ServiceUnavailableException {
		if (Objects.isNull(smsRequest)) {
			return new ResponseEntity<>("SMS REQUEST BODY IS NULL", HttpStatus.BAD_REQUEST);
		}

		if (StringUtils.isEmpty(smsRequest.getPhoneNumber())) {
			return new ResponseEntity<>("PHONE NUMBER IS INSERTED", HttpStatus.BAD_REQUEST);
		}

		if (StringUtils.isEmpty(smsRequest.getMessage())) {
			return new ResponseEntity<>("MESSAGE IS NOT INSERTED", HttpStatus.BAD_REQUEST);
		}

		try {
			smsSenderService.sendSms(smsRequest);

		} catch (Exception e) {
			throw new ServiceUnavailableException("ERROR CAUSED DUE TO TWILIO SERVICE :-" + e.getMessage());
		}
		return new ResponseEntity<>("SMS SENT SUCCESSFULLY", HttpStatus.OK);
	}

	@PostMapping("/generate/otp/{phoneNumber}")
	@ApiOperation(httpMethod = "POST", value = "Generate Otp for PhoneNumber", notes = "The phone number should start with 91")
	public ResponseEntity<Object> generateOtp(@PathVariable(value = "phoneNumber", required = true) String phoneNumber)
			throws ServiceUnavailableException {

		if (StringUtils.isEmpty(phoneNumber)) {
			return new ResponseEntity<>("PHONE NUMBER IS NOt INSERTED", HttpStatus.BAD_REQUEST);
		}
		OTPModel otpModel = null;
		try {
			otpModel = smsSenderService.generateOtp(phoneNumber);
			if (Objects.isNull(otpModel)) {
				return new ResponseEntity<>("OTP GENRATION ERROR CAUSED", HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			throw new ServiceUnavailableException("ERROR CAUSED DUE TO TWILIO SERVICE :-" + e.getMessage());
		}
		return new ResponseEntity<>(otpModel, HttpStatus.OK);
	}

	@PostMapping("/verify/otp/{phoneNumber}")
	@ApiOperation(httpMethod = "POST", value = "Varify Otp for PhoneNumber", notes = "The phone number should start with 91")
	public ResponseEntity<Object> varifyOtp(@PathVariable(value = "phoneNumber", required = true) String phoneNumber,
			@RequestBody OTP otp) throws ServiceUnavailableException, OTPTimeExpiredException, InvalidOTPException {

		if (StringUtils.isEmpty(phoneNumber)) {
			return new ResponseEntity<>("PHONE NUMBER IS NOT INSERTED", HttpStatus.BAD_REQUEST);
		}
		if (StringUtils.isEmpty(otp.getOtp()) || Objects.isNull(otp)) {
			return new ResponseEntity<>("OTP is not provided to varification", HttpStatus.BAD_REQUEST);
		}
		String response = null;

		response = smsSenderService.verifyOtp(phoneNumber, otp.getOtp());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	


}
