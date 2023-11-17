package com.ywc.notification.email.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionDetail {
	
	private Date timeStamp;
	private String errorMesssage;
	private String errorDetails;

}
