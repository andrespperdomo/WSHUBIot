package com.claro.hubiot.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.claro.hubiot.exceptions.InvalidParameterException;
import com.claro.hubiot.exceptions.ParameterEmptyException;
import com.claro.hubiot.exceptions.ParameterException;
import com.claro.hubiot.servicios.Memoria;

@XmlRootElement(name="provisioningRequest")
public class ProvisioningRequest {
	@XmlElement(name="eid",required = true)
	private String eid;
	@XmlElement(name="imei",required = false)
	private String imei;
	@XmlElement(name="iccid",required = true)
	private String iccid;
	@XmlElement(name="location",required = false)
	private String location;
	@XmlElement(name="enterpriseId",required = true)
	private String enterpriseId;	
	@XmlElement(name="correlatorId",required = true)
	private String correlatorId;
	@XmlElement(name="version",required = true)
	private String version;
	@XmlElement(name="channel",required = true)
	private String channel;
	@XmlElement(name="country",required = false)
	private String country;
	@XmlElement(name="region",required = true)
	private String region;
	@XmlElement(name="requestSource",required = true)
	private String requestSource;
	@XmlElement(name="providerId",required = false)
	private String providerId;	
	@XmlElement(name="serviceName",required = false)
	private String serviceName;
	@XmlElement(name="simManufacturer",required = false)
	private String simManufacturer;
	@XmlElement(name="planId",required = false)
	private String planId;
 	@XmlElement(required = true)
	private List<NamedParameter> extensionInfo;
 	
 	@XmlTransient
 	private String msisdn;
 	@XmlTransient
 	private String imsi;
 	@XmlTransient
 	private String NMtmcode;


 	public ProvisioningRequest() {
	}

	/**
	 * Valida que el request contenga los campos necesarios para el servicio
	 * @return
	 * @throws ParameterException 
	 * @throws ParameterEmptyException 
	 * @throws InvalidParameterException 
	 */
	public Boolean esValido(String countryProp, String serviceNameProp) throws ParameterException, ParameterEmptyException, InvalidParameterException{

		if (eid == null || iccid == null || enterpriseId == null || correlatorId == null || version == null || country == null || providerId == null || serviceName == null ||
				simManufacturer == null || planId == null){
			throw new ParameterException();
		}

		if(eid.isEmpty() || iccid.isEmpty() || enterpriseId.isEmpty() || tieneEspacios(enterpriseId) || correlatorId.isEmpty() || tieneEspacios(correlatorId) ||
				version.isEmpty() || country.isEmpty() || tieneEspacios(country) || providerId.isEmpty() || tieneEspacios(providerId) || 
				serviceName.isEmpty() || tieneEspacios(serviceName) || simManufacturer.isEmpty() || planId.isEmpty() ) {
			throw new ParameterEmptyException();
		}
		
		if(eid.length() > 32 || imei == null ? false : imei.length() > 20 || iccid.length() > 20 || location == null ? false : location.length() > 30 || enterpriseId.length() > 30 || correlatorId.length() > 40 ||
				version.length() > 20 || channel == null ? false : channel.length() > 10 || country.length() > 10 || region == null ? false : region.length() > 3 || requestSource == null ? false : requestSource.length() > 30 || providerId.length() > 30 ||
				serviceName.length() > 60 || simManufacturer.length() > 50 || planId.length() > 30) {
			throw new InvalidParameterException();
		}
		
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

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getCorrelatorId() {
		return correlatorId;
	}

	public void setCorrelatorId(String correlatorId) {
		this.correlatorId = correlatorId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRequestSource() {
		return requestSource;
	}

	public void setRequestSource(String requestSource) {
		this.requestSource = requestSource;
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

	public String getSimManufacturer() {
		return simManufacturer;
	}

	public void setSimManufacturer(String simManufacturer) {
		this.simManufacturer = simManufacturer;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public List<NamedParameter> getExtensionInfo() {
		return extensionInfo;
	}

	public void setExtensionInfo(List<NamedParameter> extensionInfo) {
		this.extensionInfo = extensionInfo;
	}

	
	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
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
		builder.append("<ProvisioningRequest><");
		if (eid != null) {
			builder.append("eid>");
			builder.append(eid);
			builder.append("</eid><");
		}
		if (imei != null) {
			builder.append("imei>");
			builder.append(imei);
			builder.append("</imei><");
		}
		if (iccid != null) {
			builder.append("iccid>");
			builder.append(iccid);
			builder.append("</iccid><");
		}
		if (location != null) {
			builder.append("location>");
			builder.append(location);
			builder.append("</location><");
		}
		if (enterpriseId != null) {
			builder.append("enterpriseId>");
			builder.append(enterpriseId);
			builder.append("</enterpriseId><");
		}		
		if (correlatorId != null) {
			builder.append("correlatorId>");
			builder.append(correlatorId);
			builder.append("</correlatorId><");
		}
		if (version != null) {
			builder.append("version>");
			builder.append(version);
			builder.append("</version><");
		}
		if (channel != null) {
			builder.append("channel>");
			builder.append(channel);
			builder.append("</channel><");
		}
	//---------------------------------------------
		if (country != null) {
			builder.append("country>");
			builder.append(country);
			builder.append("</country><");
		}				
		if (region != null) {
			builder.append("region>");
			builder.append(region);
			builder.append("</region><");
		}
		if (requestSource != null) {
			builder.append("requestSource>");
			builder.append(requestSource);
			builder.append("</requestSource><");
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
		if (simManufacturer != null) {
			builder.append("simManufacturer>");
			builder.append(simManufacturer);
			builder.append("</simManufacturer><");
		}
		if (planId != null) {
			builder.append("planId>");
			builder.append(planId);
			builder.append("</planId><");
		}
		if (extensionInfo != null) {
			builder.append("extensionInfo>");
			builder.append(extensionInfo);
			builder.append("</extensionInfo");
		}
		builder.append("></ProvisioningRequest>");
		return builder.toString();
	}

}
