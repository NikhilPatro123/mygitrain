package com.ywc.notification.email.service;

//import it.ozimov.springboot.mail.model.InlinePicture;
//import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

import javax.mail.internet.MimeMessage;

import com.ywc.notification.email.model.Email;

import java.util.Map;

public interface EmailService {

    /**
     * Send an email message.
     * <p>
     * The send date is set or overridden if any is present.
     *
     * @param mimeEmail an email to be send
     */
    MimeMessage send(Email mimeEmail);

    /**
     * Send an email message.
     * <p>
     * The body is ignored if present.
     * The send date is set or overridden if any is present.
     *
     * @param mimeEmail      an email to be send
     * @param template       the reference to the template file
     * @param modelObject    the model object to be used for the template engine, it may be null
     * @param inlinePictures list of pictures to be rendered inline in the template
     */
	/*
	 * MimeMessage send(Email mimeEmail, String template, Map<String, Object>
	 * modelObject, InlinePicture... inlinePictures) throws
	 * CannotSendEmailException;
	 */

}
