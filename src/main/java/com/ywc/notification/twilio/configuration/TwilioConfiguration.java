package com.ywc.notification.twilio.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:twilio.properties")
public class TwilioConfiguration {
	
	@Value("${account_sid}")
	private String accountSid;
	@Value("${auth_token}")
	private String authToken;
	@Value("${trial_number}")
	private String trialNumber;
	
	
	@Value("${senderId}")
	private String senderId;
	
	@Value("${DRNotifyUrl}")
	private String dRNotifyUrl;
	
	@Value("${DRNotifyHttpMethod}")
	private String dRNotifyHttpMethod;
	
	@Value("${Tool}")
	private String tool;
	

	@Value("${authKey}")
	private String authKey;
	
	@Value("${sms_country_auth_Token}")
	private String smsCountryAuthToken;

	
	public TwilioConfiguration() {

	}

	public String getAccountSid() {
		return accountSid;
	}

	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getTrialNumber() {
		return trialNumber;
	}

	public void setTrialNumber(String trialNumber) {
		this.trialNumber = trialNumber;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getdRNotifyUrl() {
		return dRNotifyUrl;
	}

	public void setdRNotifyUrl(String dRNotifyUrl) {
		this.dRNotifyUrl = dRNotifyUrl;
	}

	public String getdRNotifyHttpMethod() {
		return dRNotifyHttpMethod;
	}

	public void setdRNotifyHttpMethod(String dRNotifyHttpMethod) {
		this.dRNotifyHttpMethod = dRNotifyHttpMethod;
	}

	public String getTool() {
		return tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getSmsCountryAuthToken() {
		return smsCountryAuthToken;
	}

	public void setSmsCountryAuthToken(String smsCountryAuthToken) {
		this.smsCountryAuthToken = smsCountryAuthToken;
	}

	
	
	
	
}
