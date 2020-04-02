package com.claro.hubiot.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Address {

	@XmlElement(name = "addressLine1")
	private String addressLine1;
	@XmlElement(name = "addressLine2")
	private String addressLine2;
	@XmlElement(name = "city")
	private String city;
	@XmlElement(name = "state")
	private String state;	
	@XmlElement(name = "postalCode")
	private String postalCode;
	@XmlElement(name = "countryM2M")
	private String countryM2M;
	
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
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
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountryM2M() {
		return countryM2M;
	}
	public void setCountryM2M(String countryM2M) {
		this.countryM2M = countryM2M;
	}
	
	@Override
	public String toString() {
		return "<Address><" + (addressLine1 != null ? "addressLine1>" + addressLine1 + "</addressLine1><" : "")
				+ (addressLine2 != null ? "addressLine2>" + addressLine2 + "</addressLine2><" : "")
				+ (city != null ? "city>" + city + "</city><" : "")
				+ (state != null ? "state>" + state + "</state><" : "")
				+ (postalCode != null ? "postalCode>" + postalCode + "</postalCode><" : "")
				+ (countryM2M != null ? "countryM2M>" + countryM2M + "</countryM2M" : "") + "></Address>";
	}
	
	

}
