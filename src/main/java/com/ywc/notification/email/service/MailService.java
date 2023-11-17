package com.ywc.notification.email.service;

import com.ywc.notification.email.model.Email;

public interface MailService {

	void sendMail(Email email, String serviceProvider);

}