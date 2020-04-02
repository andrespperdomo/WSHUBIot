package com.claro.hubiot.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.claro.hubiot.exceptions.InvalidParameterException;
import com.claro.hubiot.exceptions.ParameterEmptyException;
import com.claro.hubiot.exceptions.ParameterException;

@XmlRootElement(name="QueryUserRequest")
public class QueryUserRequest {
	
	@XmlElement(name="providerId",required = true)
	private String providerId;
	@XmlElement(name="serviceName",required = true)
	private String serviceName;
	@XmlElement(name="msisdn",required = true)
	private String msisdn;
	@XmlElement(name="correlatorId",required = true)
	private String correlatorId;
	@XmlElement(name="country",required = true)
	private String country;
	@XmlElement(name="billingAccountNumber",required = true)
	private String billingAccountNumber;
	@XmlElement(required = false)
	private List<NamedParameter> extensionInfo;	
	
 	public QueryUserRequest() {
	}
	
	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getCorrelatorId() {
		return correlatorId;
	}

	public void setCorrelatorId(String correlatorId) {
		this.correlatorId = correlatorId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBillingAccountNumber() {
		return billingAccountNumber;
	}

	public void setBillingAccountNumber(String billingAccountNumber) {
		this.billingAccountNumber = billingAccountNumber;
	}

	public List<NamedParameter> getExtensionInfo() {
		return extensionInfo;
	}

	public void setExtensionInfo(List<NamedParameter> extensionInfo) {
		this.extensionInfo = extensionInfo;
	}

	public Boolean esValido(String countryProp, String serviceNameProp, String providerIdProp) throws ParameterException, ParameterEmptyException, InvalidParameterException{
		
		if ( providerId == null || correlatorId == null || serviceName==null || country==null || msisdn==null || 
				billingAccountNumber==null) {
			throw new ParameterException();
		}

		if (providerId.isEmpty() || tieneEspacios(providerId) || correlatorId.isEmpty() || tieneEspacios(correlatorId) || serviceName.isEmpty() || tieneEspacios(serviceName) ||
				country.isEmpty() || tieneEspacios(country) || msisdn.isEmpty() || tieneEspacios(msisdn) || 
			 billingAccountNumber.isEmpty() || tieneEspacios(billingAccountNumber)) {
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
		builder.append("<QueryUserRequest><");
		if (providerId != null) {
			builder.append("providerId>");
			builder.append(providerId);
			builder.append("</providerId><");
		}
		if (serviceName != null) {
			builder.append("serviceName>");
			builder.append(serviceName);
			builder.append("</serviceName><");
		}
		if (msisdn != null) {
			builder.append("msisdn>");
			builder.append(msisdn);
			builder.append("</msisdn><");
		}
		if (correlatorId != null) {
			builder.append("correlatorId>");
			builder.append(correlatorId);
			builder.append("</correlatorId><");
		}
		if (country != null) {
			builder.append("country>");
			builder.append(country);
			builder.append("</country><");
		}
		if (billingAccountNumber != null) {
			builder.append("billingAccountNumber>");
			builder.append(billingAccountNumber);
			builder.append("</billingAccountNumber><");
		}
		if (extensionInfo != null) {
			builder.append("extensionInfo>");
			builder.append(extensionInfo);
			builder.append("</extensionInfo");
		}
		builder.append("></QueryUserRequest>");
		return builder.toString();
	}
}
