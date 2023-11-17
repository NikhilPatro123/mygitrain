package com.ywc.notification.hsn.service;

import com.ywc.notification.hsn.model.HsnCodeModel;

public interface HsnCodeService {

	public HsnCodeModel getHSNCodeGSTRateInfo(String hsnCode) throws Exception;
	
	public HsnCodeModel getHsnCodeGstFrCache();

	public HsnCodeModel getHsnCodeModelByCode(String hsnCode);
	
}
 