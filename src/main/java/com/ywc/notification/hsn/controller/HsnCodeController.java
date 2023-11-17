package com.ywc.notification.hsn.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.benmanes.caffeine.cache.Cache;
import com.ywc.notification.hsn.model.HsnCodeModel;
import com.ywc.notification.hsn.service.HsnCodeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/hsnCode")
@Tag(name = "HSNCODE Validation " , description = "The HSNCODE API")
public class HsnCodeController {


	@Autowired
	HsnCodeService hsnCodeService;
	
	@Autowired
	CacheManager cacheManager;


//	@RequestMapping(value = "/hsnCodeGstRate/{hsnCode}",
//			method = RequestMethod.GET, 
//			produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody String getGstDetails(@PathVariable(value="hsnCode") String  hsnCode) throws Exception {
//		try {
//			HsnCodeModel  hsnCodeModel = hsnCodeService.getHSNCodeGSTRateInfo(hsnCode);
//			System.out.println(hsnCodeModel);
//			hsnCodeService.getHsnCodeGstFrCache();
//			return "";
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception(e);
//		}    
//	}
//	
//
//	@GetMapping(value = "/postValue/{hsnCode}")
//	public HsnCodeModel GetHSnCode(@PathVariable String hsnCode ) {
//	
//		CaffeineCache caffeineCache = (CaffeineCache) cacheManager.getCache("HSNCODEMODEL");
//		Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
//		for (Map.Entry<Object, Object> entry : nativeCache.asMap().entrySet()) {
//			System.out.println("Key = " + entry.getKey());
//			System.out.println("Value = " + entry.getValue());
//		}
//		
//     return hsnCodeService.getHsnCodeModelByCode(hsnCode);
//	}
 
}
