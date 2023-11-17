package com.ywc.notification.gst.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GstAddr {

	
	private String dst;

    private String city;

    private String bnm;

    private String lt;

    private String loc;

    private String st;

    private String bno;

    private String lg;

    private String stcd;

    private String pncd;

    private String flno;
}
