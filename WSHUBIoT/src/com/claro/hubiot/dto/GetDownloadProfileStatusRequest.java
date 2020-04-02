package com.claro.hubiot.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.claro.hubiot.exceptions.InvalidParameterException;
import com.claro.hubiot.exceptions.ParameterEmptyException;
import com.claro.hubiot.exceptions.ParameterException;

@XmlRootElement(name="getDownloadProfileStatusRequest ")
public class GetDownloadProfileStatusRequest {
	@XmlElement(name="eid",required = true)
	private String eid;
	@XmlElement(name="iccid",required = true)
	private String iccid;
	@XmlElement(name="correlatorId",required = false)
	private String correlatorId;
	@XmlElement(name="providerId",required = true)
	private String providerId;	
	@XmlElement(name="country",required = true)
	private String country;
	@XmlElement(name="serviceName",required = true)
	private String serviceName;
	@XmlElement(required = false)
	private List<NamedParameter> extensionInfo;
	
	public GetDownloadProfileStatusRequest() {
	}

	/**
	 * Valida que el request contenga los campos necesarios para el servicio
	 * @return
	 * @throws ParameterException 
	 * @throws ParameterEmptyException 
	 * @throws InvalidParameterException 
	 */
	public Boolean esValido(String countryProp, String serviceNameProp, String providerIdProp) throws ParameterException, ParameterEmptyException, InvalidParameterException{

		if (correlatorId == null || providerId == null || country==null || serviceName==null ){				 
			throw new ParameterException();
		}
		
		if (correlatorId.isEmpty() || tieneEspacios(correlatorId) || providerId.isEmpty() || tieneEspacios(providerId) || country.isEmpty() || tieneEspacios(country) 
				|| serviceName.isEmpty() || tieneEspacios(serviceName)) {
			throw new ParameterEmptyException();
		}
		
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
	
	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getCorrelatorId() {
		return correlatorId;
	}

	public void setCorrelatorId(String correlatorId) {
		this.correlatorId = correlatorId;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<GetDownloadProfileStatusRequest><");
		if (eid != null) {
			builder.append("eid>");
			builder.append(eid);
			builder.append("</eid><");
		}
		
		if (iccid != null) {
			builder.append("iccid>");
			builder.append(iccid);
			builder.append("</iccid><");
		}
		
		if (correlatorId != null) {
			builder.append("correlatorId>");
			builder.append(correlatorId);
			builder.append("</correlatorId><");
		}
		
	//---------------------------------------------
		if (country != null) {
			builder.append("country>");
			builder.append(country);
			builder.append("</country><");
		}				
		
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
		
		if (extensionInfo != null) {
			builder.append("extensionInfo>");
			builder.append(extensionInfo);
			builder.append("</extensionInfo");
		}
		builder.append("></GetDownloadProfileStatusRequest>");
		return builder.toString();
	
	
	}
	

}
