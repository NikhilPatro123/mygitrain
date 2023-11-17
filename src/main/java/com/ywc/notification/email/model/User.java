package com.ywc.notification.email.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1660093987723128855L;

	private String userEmailId;
	private String userName;
}
