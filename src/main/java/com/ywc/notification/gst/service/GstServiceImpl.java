package com.ywc.notification.gst.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import static java.util.Objects.isNull;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ywc.notification.gst.client.GstCheckClient;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
@PropertySource(value = "classpath:application.yml")
public class GstServiceImpl  implements GstService {

	@Autowired
	GstCheckClient gstClient; 

	@Value("${gst.validationApi.key}")
	private String apiKey;

	public boolean checkValidGst(String gstInNumber) {
		log.info("GstServiceImpl --> GSTNumber  "+gstInNumber );
		String responseEntity= gstClient.getAllOrder(apiKey, gstInNumber);
		log.info("GstServiceImpl --> GSTNumer ---> responseEntity "+responseEntity );
		return createGstData(responseEntity);
	}
	
	public String getGstDetails(String gstInNumber) {
		log.info("GstServiceImpl --> getGstDetails  "+gstInNumber );
		String responseEntity= gstClient.getAllOrder(apiKey, gstInNumber);
		log.info("GstServiceImpl --> getGstDetails --->responseEntity "+responseEntity);
		return responseEntity;
	}
	
	
	private boolean createGstData(String jsonString) {
		log.info("createGstjData ---> createGstData "+jsonString);
		//boolean exist = false;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode root = objectMapper.readTree(jsonString);
			JsonNode taxPayerInfo =  root.get("taxpayerInfo");
			if(!isNull(taxPayerInfo)){
			String status =  taxPayerInfo.get("sts").asText();
				if(status.equalsIgnoreCase("Active")){
					return true;
				}else{
					return false;
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		log.info("createGstjData ---> createGstData "+jsonString);
		return false;
	}

}
