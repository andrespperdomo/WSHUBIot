package com.claro.hubiot.dto;

import java.util.List;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.claro.hubiot.exceptions.InvalidParameterException;
import com.claro.hubiot.exceptions.ParameterEmptyException;
import com.claro.hubiot.exceptions.ParameterException;
import com.claro.hubiot.servicios.Memoria;


/**
 * DTO que almacena ...
 * @author AUTOR
 *
 */
@XmlRootElement(name="queryPlanListRequest")
public class QueryPlanListRequest{

	@XmlElement(name="serviceName",required = true)
	@Size(min = 0 , max = 10, message = "No mas que diez")
	private String serviceName;
	@XmlElement(name="enterpriseId",required = true)
	private String enterpriseId;
	@XmlElement(name="providerId",required = true)
	private String providerId;
	@XmlElement(name="correlatorId",required = true)
	private String correlatorId;
	@XmlElement(name="country",required = true)
	private String country;
	@XmlElement(name="channel",required = true)
	private String channel;
	@XmlElement(name="requestSource",required = true)
	private String requestSource;
	@XmlElement(required = false)
	private List<NamedParameter> extensionInfo;

	public QueryPlanListRequest() {
	}

	/**
	 * Valida que el request contenga los campos necesarios para el servicio
	 * @return
	 * @throws InvalidParameterException 
	 * @throws ParameterException 
	 * @throws ParameterEmptyException 
	 */
	public Boolean esValido(String countryProp, String serviceNameProp) throws InvalidParameterException, ParameterException, ParameterEmptyException{

		if (serviceName==null ||enterpriseId == null ||  providerId == null || correlatorId == null || country == null || channel == null || requestSource == null){
			throw new ParameterException();
		}

		if( serviceName.isEmpty() || tieneEspacios(serviceName) || enterpriseId.isEmpty() || tieneEspacios(enterpriseId) || providerId.isEmpty() || tieneEspacios(providerId) 
				|| correlatorId.isEmpty() || tieneEspacios(correlatorId) || country.isEmpty() || tieneEspacios(country)) {
			throw new ParameterEmptyException();
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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<QueryPlanListRequest><");
		if (serviceName != null) {
			builder.append("serviceName>");
			builder.append(serviceName);
			builder.append("</serviceName><");
		}
		if (enterpriseId != null) {
			builder.append("enterpriseId>");
			builder.append(enterpriseId);
			builder.append("</enterpriseId><");
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
		if (requestSource != null) {
			builder.append("requestSource>");
			builder.append(requestSource);
			builder.append("</requestSource><");
		}
		if (extensionInfo != null) {
			builder.append("extensionInfo>");
			for (NamedParameter namedParameter : extensionInfo) {
				builder.append(namedParameter.toString());
			}
			builder.append("</extensionInfo");
		}
		builder.append("></QueryPlanListRequest>");
		return builder.toString();
	}

	

}
