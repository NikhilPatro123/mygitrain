package com.ywc.notification.twilio.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public @ResponseBody ExceptionResponse resourceNotFoundExceptionHandler(
			final ResourceNotFoundException exception, final HttpServletRequest request) {
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage(exception.getMessage());
		response.setUri(request.getRequestURI());
		return response;
	}
	
	@ExceptionHandler(InvalidOTPException.class)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public @ResponseBody ExceptionResponse otpInvalidExceptionHandler(
			final InvalidOTPException exception, final HttpServletRequest request) {
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage(exception.getMessage());
		response.setUri(request.getRequestURI());
		return response;
	}
	
	@ExceptionHandler(OTPTimeExpiredException.class)
	@ResponseStatus(code = HttpStatus.GATEWAY_TIMEOUT)
	public @ResponseBody ExceptionResponse otpTimeOutExceptionHandler(
			final OTPTimeExpiredException exception, final HttpServletRequest request) {
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage(exception.getMessage());
		response.setUri(request.getRequestURI());
		return response;
	}

}
