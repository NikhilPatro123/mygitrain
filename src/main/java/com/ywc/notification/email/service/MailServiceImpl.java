package com.ywc.notification.email.service;

import java.util.Date;

import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ywc.notification.email.model.Email;
import com.ywc.notification.email.service.impl.EmailToMimeMessage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

	private final MailServiceProviderFactory mailServiceProviderFactory;
	
	@Autowired
	public MailServiceImpl(MailServiceProviderFactory mailServiceProviderFactory) {
		this.mailServiceProviderFactory = mailServiceProviderFactory;
	}
	
	@Autowired
	private EmailToMimeMessage emailToMimeMessage;
	 
	@Override
		public void sendMail(Email email, String serviceProvider) {
		
		 log.info(" MailServiceImpl  ---->serviceProvider " +serviceProvider);
		
			JavaMailSender mailSender = mailServiceProviderFactory.getServiceProvider(serviceProvider);
			
			email.setSentAt(new Date());
	        final MimeMessage mimeMessage = toMimeMessage(email);
	        
	        mailSender.send(mimeMessage);
	        log.info(" MailServiceImpl  Ends---->serviceProvider " +serviceProvider);
		}
	
	 private MimeMessage toMimeMessage(@NotNull Email email) {
	        return emailToMimeMessage.apply(email);
	    }
}
