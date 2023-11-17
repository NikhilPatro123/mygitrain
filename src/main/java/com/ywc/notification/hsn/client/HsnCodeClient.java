package com.ywc.notification.hsn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "hsnCode", url = "https://appyflow.in/api/hsn_code_details"  )
public interface HsnCodeClient {

	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody String getHSNCodeGSTRateInfo(@RequestParam(name = "key_secret")String keySecret,
			@RequestParam("hsn_code") String hsnCode);
	
}
