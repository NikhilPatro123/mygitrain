package com.ywc.notification.pincodes.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ywc.notification.pincodes.services.PincodesService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
public class PinCodeApi {

	@Autowired
	private PincodesService pincodesService;
	
	@GetMapping(value = "/pincode/{city}", produces = MediaType.APPLICATION_JSON_VALUE, headers = "X-API-VERSION=v1")
	public @ResponseBody ResponseEntity<Set<String>> getAllPincodeByCity(@PathVariable(name = "city",required = true ) String city) throws Exception {
		//ResponseEntity<List<String>>(shippingBaseService.getListCitByState(state), HttpStatus.OK);
		Set<String> stateList = pincodesService.getAllPincodeByCity(city);
		return new ResponseEntity<Set<String>>(stateList, HttpStatus.OK); 
	}

}
