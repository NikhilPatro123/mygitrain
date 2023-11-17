package com.ywc.notification.gst.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GstPradr {

	private String ntr;

	private GstAddr addr;
}
