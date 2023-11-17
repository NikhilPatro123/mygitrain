package com.ywc.notification.gst.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "gstVa", url = "https://appyflow.in/api/verifyGST"  )
public interface GstCheckClient {
	
	
//	@GetMapping(value = "/verifyGST", 
//			produces =  MediaType.APPLICATION_JSON_VALUE ,consumes = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody String getAllOrder(@RequestParam(name = "key_secret")String key_secret,
//			@RequestParam("gstNo") String gstNo);

	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody String getAllOrder(@RequestParam(name = "key_secret")String keySecret,
			@RequestParam("gstNo") String gstNo);
	  
	
	
//	@GetMapping(value = "/check/",  produces =  MediaType.APPLICATION_JSON_VALUE ,consumes = MediaType.ALL_VALUE)
//	ResponseEntity<GstData> checkGstIn(@RequestParam(name = "api-eky") String apikey ,
//					@RequestParam(name = "gstin-number") String gstinNumber);
//	
//	
//	@GetMapping(value = "/check-return/",  produces =  MediaType.APPLICATION_JSON_VALUE ,consumes = MediaType.ALL_VALUE)
//	ResponseEntity<String> checkGstInStatus(@RequestParam(name = "api-eky") String apikey ,
//					@RequestParam(name = "gstin-number") String gstinNumber);
	
//	 @RequestLine("GET /check/{apikey}/{gstinNumber}")
//	 ResponseEntity<GstData>  checkGstIn(@Param("apikey") String apikey,@Param("gstinNumber") String  gstinNumber);
//

}
