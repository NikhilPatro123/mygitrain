package com.ywc.notification.gst.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GstRoot {
	
	
	private boolean flag;
	private String message;
	private GstData data;
}
