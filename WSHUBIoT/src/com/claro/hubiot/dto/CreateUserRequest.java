package com.claro.hubiot.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.claro.hubiot.exceptions.InvalidParameterException;
import com.claro.hubiot.exceptions.ParameterEmptyException;
import com.claro.hubiot.exceptions.ParameterException;

@XmlRootElement(name="CreateUserRequest")
public class CreateUserRequest {

	@XmlElement(name="providerId",required = true)
	private String providerId;
	@XmlElement(name="operatorUserId",required = false)
	private String operatorUserId;
	@XmlElement(name="correlatorId",required = true)
	private String correlatorId;
	@XmlElement(name="serviceName",required = true)
	private String serviceName;
	@XmlElement(name="country",required = true)
	private String country;
	@XmlElement(name="iccid",required = true)
	private String iccid;
	@XmlElement(name="imsi",required = true)
	private String imsi;
	@XmlElement(name="msisdn",required = true)
	private String msisdn;
	@XmlElement(required = false)
	private SubscriberDetails subscriberDetails;
	@XmlElement(required = false)
	private TermsAndConditions termsAndConditions;
	@XmlElement(name="MNOSubscriberDetails", required = false)
	private MNOSubscriberDetailsInfo mnoSubscriberDetails;
	@XmlElement(required = false)
	private ServicePlanInfo servicePlan;
	@XmlElement(required = false)
	private List<NamedParameter> extensionInfo;	
	
 	public CreateUserRequest() {
	}
 	
	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getOperatorUserId() {
		return operatorUserId;
	}

	public void setOperatorUserId(String operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public String getCorrelatorId() {
		return correlatorId;
	}

	public void setCorrelatorId(String correlatorId) {
		this.correlatorId = correlatorId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
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

	public SubscriberDetails getSubscriberDetails() {
		return subscriberDetails;
	}

	public void setSubscriberDetails(SubscriberDetails subscriberDetails) {
		this.subscriberDetails = subscriberDetails;
	}

	public TermsAndConditions getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(TermsAndConditions termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	public MNOSubscriberDetailsInfo getMnoSubscriberDetails() {
		return mnoSubscriberDetails;
	}

	public void setMnoSubscriberDetails(MNOSubscriberDetailsInfo mnoSubscriberDetails) {
		this.mnoSubscriberDetails = mnoSubscriberDetails;
	}

	public ServicePlanInfo getServicePlan() {
		return servicePlan;
	}

	public void setServicePlan(ServicePlanInfo servicePlan) {
		this.servicePlan = servicePlan;
	}

	public List<NamedParameter> getExtensionInfo() {
		return extensionInfo;
	}

	public void setExtensionInfo(List<NamedParameter> extensionInfo) {
		this.extensionInfo = extensionInfo;
	}

	/**
	 * Valida que el request contenga los campos necesarios para el servicio
	 * @return
	 */
	public Boolean esValido(String countryProp, String serviceNameProp, String providerIdProp) throws ParameterException, ParameterEmptyException, InvalidParameterException{

		if ( providerId == null || correlatorId == null || serviceName==null || country==null || iccid==null || imsi==null || msisdn==null) {
			throw new ParameterException();
		}
		
		if(providerId.isEmpty()  || tieneEspacios(correlatorId) || tieneEspacios(providerId) || correlatorId.isEmpty() || serviceName.isEmpty() || tieneEspacios(serviceName) 
				|| tieneEspacios(country) || country.isEmpty() || iccid.isEmpty() || tieneEspacios(iccid) || imsi.isEmpty() || tieneEspacios(imsi) || msisdn.isEmpty() || tieneEspacios(msisdn)) {
			throw new ParameterEmptyException();
		}
		
		this.msisdn = this.msisdn.length() > 10 ? this.msisdn.substring(this.msisdn.length() - 10) : this.msisdn;
		
		if(!countryProp.contains("|" + country + "|") || !serviceNameProp.contains("|" + serviceName + "|") || !providerIdProp.contains("|"+ providerId +"|") ) {
			throw new InvalidParameterException();
		}
		
		return true;
	}
	
	public boolean tieneEspacios (String cadena) {
		int longitudInicial = 0;
		int longitudFinal = 0;
		longitudInicial = cadena.length();
		longitudFinal= cadena.replaceAll(" ", "").length();
		if(longitudInicial != longitudFinal) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<CreateUserRequest><");
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
		if (correlatorId != null) {
			builder.append("correlatorId>");
			builder.append(correlatorId);
			builder.append("</correlatorId><");
		}
		if (serviceName != null) {
			builder.append("serviceName>");
			builder.append(serviceName);
			builder.append("</serviceName><");
		}
		if (country != null) {
			builder.append("country>");
			builder.append(country);
			builder.append("</country><");
		}
		if (iccid != null) {
			builder.append("iccid>");
			builder.append(iccid);
			builder.append("</iccid><");
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
		if (subscriberDetails != null) {
			builder.append("subscriberDetails>");
			builder.append(subscriberDetails);
			builder.append("</subscriberDetails><");
		}
		if (termsAndConditions != null) {
			builder.append("termsAndConditions>");
			builder.append(termsAndConditions);
			builder.append("</termsAndConditions><");
		}
		if (mnoSubscriberDetails != null) {
			builder.append("mnoSubscriberDetails>");
			builder.append(mnoSubscriberDetails);
			builder.append("</mnoSubscriberDetails><");
		}
		if (servicePlan != null) {
			builder.append("servicePlan>");
			builder.append(servicePlan);
			builder.append("</servicePlan><");
		}
		if (extensionInfo != null) {
			builder.append("extensionInfo>");
			builder.append(extensionInfo);
			builder.append("</extensionInfo");
		}
		builder.append("></CreateUserRequest>");
		return builder.toString();
	}
}
