package com.ywc.notification.twilio.service;

import static java.util.Objects.isNull;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.naming.ServiceUnavailableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import com.ywc.notification.twilio.client.SmsClient;
import com.ywc.notification.twilio.configuration.TwilioConfiguration;
import com.ywc.notification.twilio.dto.OTPModel;
import com.ywc.notification.twilio.dto.SmsRequest;
import com.ywc.notification.twilio.exceptions.InvalidOTPException;
import com.ywc.notification.twilio.exceptions.OTPTimeExpiredException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SmsSenderServiceImpl implements SmsSenderService {

	private final static Logger LOGGER = LoggerFactory.getLogger(SmsSenderServiceImpl.class);

	private final TwilioConfiguration twilioConfiguration;
	private final static  String STOCKXBID= "STOCKXBID";
	private final static  String SERVICEPROVIDER= "STOCKXBID";

	@Autowired
	private SmsClient smsClient;
	
//	private Map<String, OTPModel> otpData = new HashMap<>();

	private LoadingCache<String, OTPModel> otpCache;

	@Autowired
	public SmsSenderServiceImpl(TwilioConfiguration twilioConfiguration) {
		this.twilioConfiguration = twilioConfiguration;
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS)
				.build(new CacheLoader<String, OTPModel>() {
					@Override
					public OTPModel load(String key) throws Exception {
						return null;
					}
				});
	}
 
	@Override
	public void sendSms(SmsRequest smsRequest) throws ServiceUnavailableException {
		PhoneNumber to = new PhoneNumber(new StringBuilder("+91").append(smsRequest.getPhoneNumber()).toString());
		PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
		ObjectMapper mapper = new ObjectMapper(); 
		try {

			if (!isNull(smsRequest.getServiceProvider()) && !smsRequest.getServiceProvider().equalsIgnoreCase("string") && !"".equals(smsRequest.getServiceProvider())) {
				String smsMessage = populateSmsFDCountryObject(smsRequest);
				String smsResponse = smsClient.sendSMS(smsMessage);
				log.info(" SMS Message Resonse from SMS Country  "+smsResponse);
				if(smsResponse != null  && !"".equals(smsResponse)) {
					JsonNode treeNode = mapper.readTree(smsResponse);
				}else {
				    // This is has to be called from the Twilio
					MessageCreator creator = Message.creator(to, from, smsRequest.getMessage());
					creator.create();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			if( e instanceof java.lang.reflect.UndeclaredThrowableException) {
				java.lang.reflect.UndeclaredThrowableException ex = (java.lang.reflect.UndeclaredThrowableException)e;
				if(ex.getUndeclaredThrowable().getMessage().contains("403")) {
					MessageCreator creator = Message.creator(to, from, smsRequest.getMessage());
					creator.create();
				}else {
					throw new ServiceUnavailableException("SOMETHING RELATED TO TWILIO SERVICE :-" + e.getMessage());
				}
			}
			
			
		}
		LOGGER.info("Twilio message service used to send the message to phone number " + smsRequest.getPhoneNumber());

	}
	

//    @Bean
//    public ErrorDecoder errorDecoder() {
//        return new FeignCustomErrorDecoder();
//    }
 
	private String populateSmsFDCountryObject(SmsRequest smsRequest) throws JsonProcessingException {
		//String toPhoneNumber= new StringBuilder("91").append(smsRequest.getPhoneNumber()).toString();
		String message =  smsRequest.getMessage();
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode parentNode = objectMapper.createObjectNode();
		parentNode.put("Text", message);
		parentNode.put("Number", smsRequest.getPhoneNumber());
		parentNode.put("SenderId", twilioConfiguration.getSenderId());
		parentNode.put("DRNotifyUrl", twilioConfiguration.getdRNotifyUrl());
		parentNode.put("DRNotifyHttpMethod", twilioConfiguration.getdRNotifyHttpMethod());
		parentNode.put("Tool", twilioConfiguration.getTool());
		return  objectMapper.writeValueAsString(parentNode);
	}

	@Override
	public OTPModel generateOtp(String phoneNumber) throws ServiceUnavailableException {
		//PhoneNumber to = new PhoneNumber(new StringBuilder("+91").append(phoneNumber).toString());
		//PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
		//if(!phoneNumber.contains("+"))
			//phoneNumber = new StringBuilder("+91").append(phoneNumber).toString();
		ObjectMapper mapper = new ObjectMapper(); 
		OTPModel otpModel = new OTPModel();
		try {
			
			otpModel.setPhoneNumber(phoneNumber);
			otpModel.setOtp(genrateRandomOtp());
			otpModel.setExpiryTime(setExpireTime());
			otpCache.put(phoneNumber, otpModel);
			SmsRequest smsRequest= this.populateSmsRequest(phoneNumber,otpModel);
			String smsMessage = this.populateSmsFDCountryObject(smsRequest);
			String smsResponse = smsClient.sendSMS(smsMessage);
			log.info(" SMS Message Resonse from SMS Country  "+smsResponse);
			if(smsResponse != null  && !"".equals(smsResponse)) {
				JsonNode treeNode = mapper.readTree(smsResponse);
				if(treeNode  != null && treeNode.get("Success") != null ) {
					treeNode.get("Success").asText().equalsIgnoreCase("True");
					LOGGER.info("OTP Generated and Sent to Number "+phoneNumber);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceUnavailableException("TWILIO SERVICE ERROR DUE TO " + e.getMessage());
		}
		return otpModel;
	}
	
	private SmsRequest populateSmsRequest(String phoneNumber,OTPModel otpModel) {
		SmsRequest smsRequest  = new SmsRequest();
		
		smsRequest.setPhoneNumber(phoneNumber);
		smsRequest.setMessage("Hello, your OutXtock verification OTP is " + otpModel.getOtp()+". - "+STOCKXBID);
		smsRequest.setServiceProvider(SERVICEPROVIDER);
		
		return smsRequest;
	}

	private long setExpireTime() {
		long time = System.currentTimeMillis() + 90000;
//		LOGGER.info("the expiry time is " + time);
		return time;
	}

	private String genrateRandomOtp() {
		String otp = String.valueOf((int) (Math.random() * (10000 - 1000)) + 1000);
//		LOGGER.info("the generated otp is " + otp);
		return otp;
	}

	@Override
	public String verifyOtp(String phoneNumber, String otp)
			throws ServiceUnavailableException, OTPTimeExpiredException, InvalidOTPException {
		OTPModel otpModelData = null;
		try {
			otpModelData = otpCache.get(phoneNumber);
		} catch (ExecutionException e) {
			e.printStackTrace();
			throw new ServiceUnavailableException("SOMETHING HAPPENED SO PLEASE TRY ONCE AGAIN FOR OTP.");

		}
		if (Objects.nonNull(otpModelData)) {
			OTPModel otpModel = otpModelData;
			if (Objects.isNull(otpModel)) {
				throw new ServiceUnavailableException("SOMETHING HAPPENED SO PLEASE TRY ONCE AGAIN FOR OTP.");
			}

			if (otpModel.getExpiryTime() < System.currentTimeMillis()) {
				otpCache.invalidate(phoneNumber);
				throw new OTPTimeExpiredException("OTP TIME IS EXPIRED");
			} else {
				if (otpModel.getOtp().equals(otp)) {
					otpCache.invalidate(phoneNumber);
					return "Otp varification is success";
				} else {
					throw new InvalidOTPException("INVALID OTP");
				}
			}
		} else {
			throw new ServiceUnavailableException("Phone number is not existed to validated");
		}
	}

}
