package com.ywc.notification.twilio.client;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ywc.notification.twilio.configuration.TwilioConfiguration;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;

@Configuration
public class CustomFeignClientConfiguration {
 
	@Autowired
	private TwilioConfiguration twilioConfiguration;  	

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Accept", "application/json");
            requestTemplate.header("Content-Type", "application/json");
            
            String authKey =  twilioConfiguration.getAuthKey();
    		String authToken =  twilioConfiguration.getSmsCountryAuthToken();
    		String fstr3 =authKey+":"+authToken;
    		String encoding = Base64.getEncoder().encodeToString((fstr3).getBytes(StandardCharsets.UTF_8));
    		String authHeader = "Basic " + encoding;
            requestTemplate.header("Authorization", authHeader);
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignCustomErrorDecoder();
    }
}
