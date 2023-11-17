package com.ywc.notification.email.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class MailerProperties {
	
	@NonNull
	private String host;
	
	@NonNull
	private Integer port;
	
	@NonNull
	private String userName;
	
	@NonNull
	private String password;
	
	private boolean auth;
	private Integer connectionTimeout;
	private Integer timeout;
	private Integer writetimeout;
	private boolean starttls;

}
