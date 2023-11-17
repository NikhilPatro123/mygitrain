package com.ywc.notification.gst.service;

 
public interface GstService {
	
   public boolean checkValidGst(String gstInNumber);
   
   public String getGstDetails(String gstInNumber);
}
