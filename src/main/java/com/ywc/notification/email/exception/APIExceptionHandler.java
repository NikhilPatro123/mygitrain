package com.ywc.notification.email.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class APIExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest req) {
		ExceptionDetail exceptDetail = new ExceptionDetail(new Date(), ex.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(exceptDetail, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
		ExceptionDetail errorDetail = new ExceptionDetail(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}