package com.ywc.notification.email.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.ywc.notification.gst.client" ,"com.ywc.notification.hsn.client" ,"com.ywc.notification.twilio.client"})
@OpenAPIDefinition
@ComponentScan("com.ywc.notification")
@Slf4j
public class YwcEmailNotificationServiceApplication  extends SpringBootServletInitializer{

	public static void main(String[] args) {
		//SpringApplication.run(YwcEmailNotificationServiceApplication.class, args);
		SpringApplication app = new SpringApplication(YwcEmailNotificationServiceApplication.class);
		Environment env = app.run(args).getEnvironment();
 	  log.info("\n----------------------------------------------------------\n\t" +
 						"Application '{}' is running! Access URLs:\n\t" +
 						"Local: \t\thttp://localhost:{}\n" +
 						"----------------------------------------------------------",
 				env.getProperty("spring.application.name"),
 				env.getProperty("server.port"));

	}
}
