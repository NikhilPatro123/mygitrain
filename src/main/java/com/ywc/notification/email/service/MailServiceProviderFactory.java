package com.ywc.notification.email.service;

import org.springframework.mail.javamail.JavaMailSender;

public interface MailServiceProviderFactory {

	JavaMailSender getServiceProvider(String serviceProvider);
	
}
