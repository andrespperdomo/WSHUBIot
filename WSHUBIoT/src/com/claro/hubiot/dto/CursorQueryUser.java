package com.claro.hubiot.dto;

public class CursorQueryUser {
	private String providerId;
	private String operatorUserId;

	/*** BillingAccountInfo ****/
	private String billingAccountNumber;
	private String billingAccountType; // vacio
	private String billingAccountStatus; // vacio

	private String iccId;
	private String imsi;
	private String msisdn;
	private String country;

	/** subscriberDetails **/
	private String title;
	private String firstName;
	private String lastName;
	/** subscriberDetails.Address **/
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String postalCode;
	private String countryM2M;

	/** subscriberDetails.Communication **/
	private String emailAddress;
	private String mobilePhone;

	/*** subscriberDetails **/
	private String preferredLanguageCode;

	/*** TermsAndConditions ***/
	private String consent;
	private String consentTimestamp;
	private String documentCode;
	private String documentVersioivate;
	private String documentLanguage;
	private String validationIDType;

	/**** MNOSubscriberDetailsInfo ***/
	private String dateOfBirth;
	private String placeOfBirth;
	private String idType;
	private String idValue;

	/*** ServicePlanInfo ***/
	private String planId;
	
	private String estado;
    
	
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getOperatorUserId() {
		return operatorUserId;
	}

	public void setOperatorUserId(String operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public String getBillingAccountNumber() {
		return billingAccountNumber;
	}

	public void setBillingAccountNumber(String billingAccountNumber) {
		this.billingAccountNumber = billingAccountNumber;
	}

	public String getBillingAccountType() {
		return billingAccountType;
	}

	public void setBillingAccountType(String billingAccountType) {
		this.billingAccountType = billingAccountType;
	}

	public String getBillingAccountStatus() {
		return billingAccountStatus;
	}

	public void setBillingAccountStatus(String billingAccountStatus) {
		this.billingAccountStatus = billingAccountStatus;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	public String getPreferredLanguageCode() {
		return preferredLanguageCode;
	}

	public void setPreferredLanguageCode(String preferredLanguageCode) {
		this.preferredLanguageCode = preferredLanguageCode;
	}

	public String getConsent() {
		return consent;
	}

	public void setConsent(String consent) {
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

	public String getDocumentVersioivate() {
		return documentVersioivate;
	}

	public void setDocumentVersioivate(String documentVersioivate) {
		this.documentVersioivate = documentVersioivate;
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

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getIccId() {
		return iccId;
	}

	public void setIccId(String iccId) {
		this.iccId = iccId;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**	<b>Nombre: </b> toString </br>
	 * <b>Descripción:</b>   </br>
	 * <b>Fecha Creación:</b> 4/04/2018 </br>
	 * <b>Autor:</b> HITSS  Andrea Daza </br>
	 * <b>Fecha de Última Modificación: </b></br>
	 * <b>Modificado por: </b></br>
	 * <b>Brief: 02072</b></br>
	 * @return
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<cursor><");
		if (providerId != null) {
			builder.append("providerId>");
			builder.append(providerId);
			builder.append("</providerId><");
		}
		if (operatorUserId != null) {
			builder.append("operatorUserId>");
			builder.append(operatorUserId);
			builder.append("</operatorUserId><");
		}
		if (billingAccountNumber != null) {
			builder.append("billingAccountNumber>");
			builder.append(billingAccountNumber);
			builder.append("</billingAccountNumber><");
		}
		if (billingAccountType != null) {
			builder.append("billingAccountType>");
			builder.append(billingAccountType);
			builder.append("</billingAccountType><");
		}
		if (billingAccountStatus != null) {
			builder.append("billingAccountStatus>");
			builder.append(billingAccountStatus);
			builder.append("</billingAccountStatus><");
		}
		if (iccId != null) {
			builder.append("iccId>");
			builder.append(iccId);
			builder.append("</iccId><");
		}
		if (imsi != null) {
			builder.append("imsi>");
			builder.append(imsi);
			builder.append("</imsi><");
		}
		if (msisdn != null) {
			builder.append("msisdn>");
			builder.append(msisdn);
			builder.append("</msisdn><");
		}
		if (country != null) {
			builder.append("country>");
			builder.append(country);
			builder.append("</country><");
		}
		if (title != null) {
			builder.append("title>");
			builder.append(title);
			builder.append("</title><");
		}
		if (firstName != null) {
			builder.append("firstName>");
			builder.append(firstName);
			builder.append("</firstName><");
		}
		if (lastName != null) {
			builder.append("lastName>");
			builder.append(lastName);
			builder.append("</lastName><");
		}
		if (addressLine1 != null) {
			builder.append("addressLine1>");
			builder.append(addressLine1);
			builder.append("</addressLine1><");
		}
		if (addressLine2 != null) {
			builder.append("addressLine2>");
			builder.append(addressLine2);
			builder.append("</addressLine2><");
		}
		if (city != null) {
			builder.append("city>");
			builder.append(city);
			builder.append("</city><");
		}
		if (state != null) {
			builder.append("state>");
			builder.append(state);
			builder.append("</state><");
		}
		if (postalCode != null) {
			builder.append("postalCode>");
			builder.append(postalCode);
			builder.append("</postalCode><");
		}
		if (countryM2M != null) {
			builder.append("countryM2M>");
			builder.append(countryM2M);
			builder.append("</countryM2M><");
		}
		if (emailAddress != null) {
			builder.append("emailAddress>");
			builder.append(emailAddress);
			builder.append("</emailAddress><");
		}
		if (mobilePhone != null) {
			builder.append("mobilePhone>");
			builder.append(mobilePhone);
			builder.append("</mobilePhone><");
		}
		if (preferredLanguageCode != null) {
			builder.append("preferredLanguageCode>");
			builder.append(preferredLanguageCode);
			builder.append("</preferredLanguageCode><");
		}
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
		if (documentVersioivate != null) {
			builder.append("documentVersioivate>");
			builder.append(documentVersioivate);
			builder.append("</documentVersioivate><");
		}
		if (documentLanguage != null) {
			builder.append("documentLanguage>");
			builder.append(documentLanguage);
			builder.append("</documentLanguage><");
		}
		if (validationIDType != null) {
			builder.append("validationIDType>");
			builder.append(validationIDType);
			builder.append("</validationIDType><");
		}
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
			builder.append("</idValue><");
		}
		if (planId != null) {
			builder.append("planId>");
			builder.append(planId);
			builder.append("</planId><");
		}
		if (estado != null) {
			builder.append("estado>");
			builder.append(estado);
			builder.append("</estado");
		}
		builder.append("></cursor>");
		return builder.toString();
	}

}
