package com.ywc.notification.gst.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GstData {

	private String gstin;

	private List<String> adadr;

	private String errorMsg;

	private String stjCd;

	private String cxdt;

	private String dty;

	private String lstupdt;

	private GstPradr pradr;

	private String frequencyType;

	private String rgdt;

	private String ctb;

	private String ctj;

	private String ctjCd;

	private String sts;

	private String tradeNam;

	private String lgnm;


	private List<String> nba;

	private String stj;


}
