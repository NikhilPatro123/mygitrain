package com.ywc.notification.twilio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "personclient",  url="${fdsms.ribbon.smsSend}" , configuration = CustomFeignClientConfiguration.class)
public interface SmsClient {
	
	 @RequestMapping(method = RequestMethod.POST, value = "/SMSes/",consumes = "application/json")
	   String sendSMS(@RequestBody String str);

}
