package com.claro.hubiot.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.claro.hubiot.exceptions.InvalidParameterException;
import com.claro.hubiot.exceptions.ParameterEmptyException;
import com.claro.hubiot.exceptions.ParameterException;
import com.claro.hubiot.servicios.Memoria;

@XmlRootElement(name="changePlanRequest")
public class ChangePlanRequest {

	@XmlElement(name="msisdn",required = false)
	private String msisdn;
	@XmlElement(name="planId",required = false)
	private String planId;
	@XmlElement(name="enterpriseId",required = false)
	private String enterpriseId;
	@XmlElement(name="country",required = false)
	private String country;
	@XmlElement(name="channel",required = true)
	private String channel;
	@XmlElement(name="providerId",required = false)
	private String providerId;
	@XmlElement(name="correlatorId",required = false)
	private String correlatorId;
	@XmlElement(name="requestSource",required = true)
	private String requestSource;
	@XmlElement(name="serviceName",required = false)
	private String serviceName;
	@XmlElement(required = true)
	private List<NamedParameter> extensionInfo;
	
	@XmlTransient
	private String NMtmcode;
	
 	public ChangePlanRequest() {
	}

	/**
	 * Valida que el request contenga los campos necesarios para el servicio
	 * @return
	 * @throws ParameterException 
	 * @throws ParameterEmptyException 
	 * @throws InvalidParameterException 
	 */
	public Boolean esValido(String countryProp, String serviceNameProp) throws ParameterException, ParameterEmptyException, InvalidParameterException{

		if ( msisdn == null || planId == null || enterpriseId==null ||  country==null || providerId==null || correlatorId==null ||  serviceName==null) {
			throw new ParameterException();
		}
		
		if(msisdn.isEmpty() || tieneEspacios(msisdn) || planId.isEmpty() || tieneEspacios(planId) || enterpriseId.isEmpty() || tieneEspacios(enterpriseId) 
				|| country.isEmpty() || tieneEspacios(country) || providerId.isEmpty() || tieneEspacios(providerId) || correlatorId.isEmpty() || tieneEspacios(correlatorId) 
				||serviceName.isEmpty() || tieneEspacios(serviceName)) {
			throw new ParameterEmptyException();
		}
		
		if (this.msisdn.length() < 10){
			return false;
		}
		this.msisdn = this.msisdn.length() > 10 ? this.msisdn.substring(this.msisdn.length() - 10) : this.msisdn;

		if(!Memoria.existeOferta(new LlavePlanes(providerId, enterpriseId))) {
			throw new InvalidParameterException();
		}
		
		if(!countryProp.contains("|" + country + "|") || !serviceNameProp.contains("|" + serviceName + "|")) {
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getRequestSource() {
		return requestSource;
	}

	public void setRequestSource(String requestSource) {
		this.requestSource = requestSource;
	}

	public List<NamedParameter> getExtensionInfo() {
		return extensionInfo;
	}

	public void setExtensionInfo(List<NamedParameter> extensionInfo) {
		this.extensionInfo = extensionInfo;
	}
   
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getNMtmcode() {
		return NMtmcode;
	}

	public void setNMtmcode(String nMtmcode) {
		NMtmcode = nMtmcode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<ChangePlanRequest><");
		if (msisdn != null) {
			builder.append("msisdn>");
			builder.append(msisdn);
			builder.append("</msisdn><");
		}
		if (planId != null) {
			builder.append("planId>");
			builder.append(planId);
			builder.append("</planId><");
		}
		if (enterpriseId != null) {
			builder.append("enterpriseId>");
			builder.append(enterpriseId);
			builder.append("</enterpriseId><");
		}
		if (country != null) {
			builder.append("country>");
			builder.append(country);
			builder.append("</country><");
		}
		if (channel != null) {
			builder.append("channel>");
			builder.append(channel);
			builder.append("</channel><");
		}
		if (providerId != null) {
			builder.append("providerId>");
			builder.append(providerId);
			builder.append("</providerId><");
		}
		if (correlatorId != null) {
			builder.append("correlatorId>");
			builder.append(correlatorId);
			builder.append("</correlatorId><");
		}
		if (requestSource != null) {
			builder.append("requestSource>");
			builder.append(requestSource);
			builder.append("</requestSource><");
		}
		if (serviceName != null) {
			builder.append("serviceName>");
			builder.append(serviceName);
			builder.append("</serviceName><");
		}
		if (extensionInfo != null) {
			builder.append("extensionInfo>");
			builder.append(extensionInfo);
			builder.append("</extensionInfo><");
		}
		if (NMtmcode != null) {
			builder.append("NMtmcode>");
			builder.append(NMtmcode);
			builder.append("</NMtmcode");
		}
		builder.append("></ChangePlanRequest>");
		return builder.toString();
	}

	

}
