package com.claro.hubiot.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SubscriberDetails {

	@XmlElement(name = "title")
	private String title;
	@XmlElement(name = "firstName")
	private String firstName;
	@XmlElement(name = "lastName")
	private String lastName;
	@XmlElement(required = true)
	private Address address;
	@XmlElement(required = true)
	private Communication communication;
	@XmlElement(name = "preferredLanguageCode")
	private String preferredLanguageCode;
	
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Communication getCommunication() {
		return communication;
	}

	public void setCommunication(Communication communication) {
		this.communication = communication;
	}

	public String getPreferredLanguageCode() {
		return preferredLanguageCode;
	}

	public void setPreferredLanguageCode(String preferredLanguageCode) {
		this.preferredLanguageCode = preferredLanguageCode;
	}

	/**
	 * @return el valor de lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "<SubscriberDetails><" + (title != null ? "title>" + title + "</title><" : "")
				+ (firstName != null ? "firstName>" + firstName + "</firstName><" : "")
				+ (address  != null ? "address>" + address + "</address ><" : "")
				+ (communication != null ? "communication>" + communication + "</communication><" : "")
				+ (preferredLanguageCode != null
						? "preferredLanguageCode>" + preferredLanguageCode + "</preferredLanguageCode" : "")
				+ "></SubscriberDetails>";
	}
	
	
}
