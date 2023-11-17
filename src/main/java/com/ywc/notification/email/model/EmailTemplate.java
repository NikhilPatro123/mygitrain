package com.ywc.notification.email.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class EmailTemplate {


	@NotBlank
	private List<User> to;
	
	@NonNull
	private User from;
	
	@NonNull
	private String subject;
	private String body;
	
	@NonNull
	private String mailServiceProvider;
}
