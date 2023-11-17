package com.ywc.notification.email.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ywc.notification.email.model.DefaultEmail;
import com.ywc.notification.email.model.Email;
import com.ywc.notification.email.model.EmailTemplate;
import com.ywc.notification.email.model.User;
import com.ywc.notification.email.service.MailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/notification")
@Tag(name = "Email Notification" , description = "The Email Notification API")
public class EmailNotificationController {
	
	@Autowired
	private MailService mailService;
	
	@Operation(summary = " This API shoots an email without template to recipient(s)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Email Without Templating" ,
					content = {@Content(mediaType  = "application/json")}),
			@ApiResponse(responseCode = "404",
			description = "Not available",
			content = @Content)
	})
	@PostMapping(value = "/textemail", consumes = "application/json", produces = "application/json")
	public void sendEmailWithoutTemplating(@RequestBody EmailTemplate emailObj) throws UnsupportedEncodingException {
		
		
		final Email email = DefaultEmail.builder()
				 	.from(new InternetAddress(emailObj.getFrom().getUserEmailId(), emailObj.getFrom().getUserName()))
			        .to(prepareRecipientList(emailObj.getTo()))
			        .subject(emailObj.getSubject())
			        .body(emailObj.getBody())
			        .encoding("UTF-8").build();
		
		try {
			mailService.sendMail(email, emailObj.getMailServiceProvider());
			
			
		} catch (MailException e) {
			log.error(e.getMessage());
		}
	}
	
	
	@Operation(summary = " This API shoots an email without template to recipient(s)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Email Without Templating" ,
					content = {@Content(mediaType  = "application/json")}),
			@ApiResponse(responseCode = "404",
			description = "Not available",
			content = @Content)
	})
	@PostMapping(value = "/htmlemail", consumes = "application/json", produces = "application/json")
	public void sendEmailWithoutHmtl(@RequestBody EmailTemplate emailObj) throws UnsupportedEncodingException {
		
		log.info("sendEmailWithoutHmtl  ---> htmlemail");
		final Email email = DefaultEmail.builder()
				 	.from(new InternetAddress(emailObj.getFrom().getUserEmailId(), emailObj.getFrom().getUserName()))
			        .to(prepareRecipientList(emailObj.getTo()))
			        .subject(emailObj.getSubject())
			        .body(emailObj.getBody())
			        .encoding("UTF-8").build();
		
		try {
			log.info("sendMail  ---> htmlemail");
			log.info("sendMail  --->FROM---> htmlemail -->"+email.getFrom().getAddress());
			
			email.getTo().forEach(action ->{
				log.info("sendMail  --->TO---> htmlemail --> "+action.getAddress());
			});
			
			log.info("sendMail  --->BODY---> htmlemail --> "+			email.getBody());
			
			mailService.sendMail(email, emailObj.getMailServiceProvider());
			
			
		} catch (MailException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	
	
	
	/**
	 * 
	 * @param listOfUsers
	 * @return List<InternetAddress>
	 */
	private List<InternetAddress> prepareRecipientList(List<User> listOfUsers){
		
		List<InternetAddress> addresses = new ArrayList<>();
		InternetAddress userAddress = null;
		
		for(User user : listOfUsers) {
			
			try {
				userAddress = new InternetAddress(user.getUserEmailId(), user.getUserName());
				addresses.add(userAddress);
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage());
			}
		}
		
		return addresses;
	}
}
