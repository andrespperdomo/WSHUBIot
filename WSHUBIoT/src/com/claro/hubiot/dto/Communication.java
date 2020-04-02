package com.claro.hubiot.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Communication {

	@XmlElement(name = "emailAddress")
	private String emailAddress;
	@XmlElement(name = "mobilePhone")
	private String mobilePhone;
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<Communication><");
		if (emailAddress != null) {
			builder.append("emailAddress>");
			builder.append(emailAddress);
			builder.append("</emailAddress><");
		}
		if (mobilePhone != null) {
			builder.append("mobilePhone>");
			builder.append(mobilePhone);
			builder.append("</mobilePhone");
		}
		builder.append("></Communication>");
		return builder.toString();
	}
	
}
