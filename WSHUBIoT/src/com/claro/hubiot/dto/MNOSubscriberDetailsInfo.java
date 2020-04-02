package com.claro.hubiot.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class MNOSubscriberDetailsInfo {

	@XmlElement(name = "dateOfBirth")
	private String dateOfBirth;
	@XmlElement(name = "placeOfBirth")
	private String placeOfBirth;
	@XmlElement(name = "idType")
	private String idType;
	@XmlElement(name = "idValue")
	private String idValue;
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdValue() {
		return idValue;
	}
	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<MNOSubscriberDetailsInfo><");
		if (dateOfBirth != null) {
			builder.append("dateOfBirth>");
			builder.append(dateOfBirth);
			builder.append("</dateOfBirth><");
		}
		if (placeOfBirth != null) {
			builder.append("placeOfBirth>");
			builder.append(placeOfBirth);
			builder.append("</placeOfBirth><");
		}
		if (idType != null) {
			builder.append("idType>");
			builder.append(idType);
			builder.append("</idType><");
		}
		if (idValue != null) {
			builder.append("idValue>");
			builder.append(idValue);
			builder.append("</idValue");
		}
		builder.append("></MNOSubscriberDetailsInfo>");
		return builder.toString();
	}
	
	
}
