package com.ywc.notification.hsn.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@Document
@Builder
public class HsnCodeModel {

	
	@Id private String id; 
	@Indexed(unique = true) 
	private  String hsn_code;

//	public HsnCodeModel(String hsn_code) {
//		this.hsn_code = hsn_code;
//	}
	private String cess;
	private String chapter_name;
	private String chapter_number;
	private String description;
	private String effective_date;
	private String error;
	private String rate;
	private String rate_revision;
	private String type;
	
}
