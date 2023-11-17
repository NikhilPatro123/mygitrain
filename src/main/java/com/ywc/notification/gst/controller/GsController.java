package com.ywc.notification.gst.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ywc.notification.gst.service.GstService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/gst")
@Tag(name = "GST Validation " , description = "The GST API")
public class GsController {

 	@Autowired
 	GstService gstService; 
	
	@GetMapping(value = "/validadteGst/{gstIn}")
	public ResponseEntity<String> validadteGst(@PathVariable(value="gstIn") String  gstIn) throws UnsupportedEncodingException {
		log.info("validadteGst  --> validadteGst started");
 		boolean exist = gstService.checkValidGst(gstIn) ;
		if(exist) {
			log.info("validadteGst  --> validadteGst Found");
			return  new ResponseEntity<String>("GSTIN  found",HttpStatus.OK);
		}
		else {
			log.info("validadteGst  --> validadteGst NOT Found");
			return  new ResponseEntity<String>("GSTIN not found",HttpStatus.EXPECTATION_FAILED);
		}	
	}
	
	
	@Cacheable
	@RequestMapping(value = "/gstDetails/{gstIn}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getGstDetails(@PathVariable(value="gstIn") String  gstIn) throws UnsupportedEncodingException {
		  return gstService.getGstDetails(gstIn);
	}
	
	
//	
//	@RequestMapping(value = "/gstDetailsh/{gstIn}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody String getGstDetailsHardCode(@PathVariable(value="gstIn") String  gstIn) throws UnsupportedEncodingException {
//		  return gstService.getGstDetails(gstIn);
//	}
}
