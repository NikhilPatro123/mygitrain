/*
 * package com.ywc.notification.email.service.impl;
 * 
 * import java.util.Date;
 * 
 * import javax.mail.internet.MimeMessage; import
 * javax.validation.constraints.NotNull;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.mail.MailException; import
 * org.springframework.mail.javamail.JavaMailSender; import
 * org.springframework.stereotype.Service;
 * 
 * import com.ywc.notification.email.model.Email; import
 * com.ywc.notification.email.service.EmailService;
 * 
 * import lombok.NonNull;
 * 
 * @Service public class EmailServiceImpl implements EmailService {
 * 
 * @Autowired private JavaMailSender javaMailSender;
 * 
 * @Autowired private EmailToMimeMessage emailToMimeMessage;
 * 
 * @Override public MimeMessage send(final @NonNull Email email) throws
 * MailException { email.setSentAt(new Date()); final MimeMessage mimeMessage =
 * toMimeMessage(email); javaMailSender.send(mimeMessage); //
 * emailLogRenderer.info("Sent email {}.", email); return mimeMessage; }
 * 
 * private MimeMessage toMimeMessage(@NotNull Email email) { return
 * emailToMimeMessage.apply(email); }
 * 
 * 
 * }
 */