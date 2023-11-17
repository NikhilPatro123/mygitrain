package com.ywc.notification.pincodes.dto;

public class PincodeResult {

	private String zone;
	private String city;
	private String state;
	private String pincode;

	public PincodeResult() {
		// TODO Auto-generated constructor stub
	}

	public PincodeResult(String zone, String city, String state, String pincode) {
		super();
		this.zone = zone;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

}
