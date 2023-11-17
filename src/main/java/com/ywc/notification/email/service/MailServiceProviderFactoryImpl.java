package com.ywc.notification.email.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.ywc.notification.email.constant.EmailProperties;
import com.ywc.notification.email.model.MailerProperties;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MailServiceProviderFactoryImpl implements MailServiceProviderFactory {

	/*
	 * private final MailerProperties mailerProps;
	 * 
	 * @Autowired public MailServiceProviderFactoryImpl(MailerProperties
	 * mailerProps) { this.mailerProps = mailerProps; }
	 */

	@Override
	public JavaMailSender getServiceProvider(String serviceProvider) {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		MailerProperties properties = readMailerProperties(serviceProvider);
		
	    mailSender.setHost(properties.getHost());
		mailSender.setPort(properties.getPort());
		mailSender.setUsername(properties.getUserName());
		mailSender.setPassword(properties.getPassword());
		 
		mailSender.setJavaMailProperties(mailProperties(properties));
		return mailSender;
	}

	private Properties mailProperties(MailerProperties props) {

		Properties properties = new Properties();
		properties.put(EmailProperties.AUTH.getProperty(), props.isAuth());
		properties.put(EmailProperties.TRANSPORT_PROTOCOL.getProperty(), "smtp");
		properties.put(EmailProperties.START_TLS_ENABLE.getProperty(), props.isStarttls());
		properties.put(EmailProperties.CONNECTION_TIMEOUT.getProperty(), props.getConnectionTimeout());
		properties.put(EmailProperties.TIMEOUT.getProperty(), props.getTimeout());
		properties.put(EmailProperties.WRITE_TIMEOUT.getProperty(), props.getWritetimeout());
		properties.put(EmailProperties.DEBUG.getProperty(), true);

		return properties;
	}

	
	private MailerProperties readMailerProperties(String serviceProvideName) {

		MailerProperties mailerProperties = new MailerProperties();
		
		String fileName = serviceProvideName + ".properties";

		try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {

			Properties properties = new Properties();

			if (input == null) {
				log.info("Sorry, Unable to find " + serviceProvideName + ".properties file");
				return null;
			}

			// load properties files from the class path
			properties.load(input);

			String host = properties.getProperty("mail.host") != null ? properties.getProperty("mail.host")  : "";
			String port = properties.getProperty("mail.port")!= null ? properties.getProperty("mail.port")  : "";
			String username = properties.getProperty("mail.username") != null ? properties.getProperty("mail.username")  : "";
			String password = properties.getProperty("mail.password") != null ? properties.getProperty("mail.password")  : "";
			String auth = properties.getProperty("mail.smtp.auth") != null ? properties.getProperty("mail.auth")  : "false";
			String connectiontimeout = properties.getProperty("mail.smtp.connectiontimeout") != null ? properties.getProperty("mail.smtp.connectiontimeout")  : "0";
			String timeout = properties.getProperty("mail.smtp.timeout") != null ? properties.getProperty("mail.smtp.timeout")  : "0";
			String writetimeout = properties.getProperty("mail.smtp.writetimeout") != null ? properties.getProperty("mail.smtp.writetimeout")  : "0";;
			String starttls = properties.getProperty("mail.smtp.starttls.enable")  != null ? properties.getProperty("mail.smtp.enable")  : "false";

			mailerProperties.setHost(host);
			mailerProperties.setPort(Integer.valueOf(port));
			mailerProperties.setUserName(username);
			mailerProperties.setPassword(password);
			mailerProperties.setAuth(Boolean.valueOf(auth));
			mailerProperties.setConnectionTimeout(Integer.valueOf(connectiontimeout));
			mailerProperties.setTimeout(Integer.valueOf(timeout));
			mailerProperties.setWritetimeout(Integer.valueOf(writetimeout));
			mailerProperties.setStarttls(Boolean.valueOf(starttls));

		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return mailerProperties;
	}
}
