package com.ywc.notification.email.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerOpenAPIConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		
		Contact contact = new Contact().name("Lohith Kumar")
				                       .email("lohith.kumar@stockxbid.com")
				                       .url("https://stockxbid.com");
				                       
		 return new OpenAPI()
	                .components(new Components())
	                .info(new Info().title("YWC Email Notification Service API")
	                .description("Email notification services")
	                .contact(contact)
	                );

	}

}
