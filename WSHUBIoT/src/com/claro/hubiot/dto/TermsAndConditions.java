package com.claro.hubiot.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TermsAndConditions {

	@XmlElement(name = "consent")
	private Boolean consent;
	@XmlElement(name = "consentTimestamp")
	private String consentTimestamp;
	@XmlElement(name = "documentCode")
	private String documentCode;
	@XmlElement(name = "documentVersion")
	private String documentVersion;	
	@XmlElement(name = "documentLanguage")
	private String documentLanguage;
	@XmlElement(name = "validationIdType")
	private String validationIDType;
	
	public Boolean getConsent() {
		return consent;
	}
	public void setConsent(Boolean consent) {
		this.consent = consent;
	}
	public String getConsentTimestamp() {
		return consentTimestamp;
	}
	public void setConsentTimestamp(String consentTimestamp) {
		this.consentTimestamp = consentTimestamp;
	}
	public String getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	public String getDocumentVersion() {
		return documentVersion;
	}
	public void setDocumentVersion(String documentVersion) {
		this.documentVersion = documentVersion;
	}
	public String getDocumentLanguage() {
		return documentLanguage;
	}
	public void setDocumentLanguage(String documentLanguage) {
		this.documentLanguage = documentLanguage;
	}
	public String getValidationIDType() {
		return validationIDType;
	}
	public void setValidationIDType(String validationIDType) {
		this.validationIDType = validationIDType;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<TermsAndConditions><");
		if (consent != null) {
			builder.append("consent>");
			builder.append(consent);
			builder.append("</consent><");
		}
		if (consentTimestamp != null) {
			builder.append("consentTimestamp>");
			builder.append(consentTimestamp);
			builder.append("</consentTimestamp><");
		}
		if (documentCode != null) {
			builder.append("documentCode>");
			builder.append(documentCode);
			builder.append("</documentCode><");
		}
		if (documentVersion != null) {
			builder.append("documentVersion>");
			builder.append(documentVersion);
			builder.append("</documentVersion><");
		}
		if (documentLanguage != null) {
			builder.append("documentLanguage>");
			builder.append(documentLanguage);
			builder.append("</documentLanguage><");
		}
		if (validationIDType != null) {
			builder.append("validationIDType>");
			builder.append(validationIDType);
			builder.append("</validationIDType");
		}
		builder.append("></TermsAndConditions>");
		return builder.toString();
	}
}
