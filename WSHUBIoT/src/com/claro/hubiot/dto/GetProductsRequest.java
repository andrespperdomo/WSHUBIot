
package com.claro.hubiot.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.claro.hubiot.exceptions.InvalidParameterException;
import com.claro.hubiot.exceptions.ParameterEmptyException;
import com.claro.hubiot.exceptions.ParameterException;

/**
 * <p>
 * Clase Java para GetProductsRequestType complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="GetProductsRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="enterpriseId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="msisdn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="channel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="roaming" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="specialOffer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serviceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operatorId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="correlatorId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="extensionInfo" type="{http://schema.ib.sdp.huawei.com}NamedParameter" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetProductsRequest", namespace = "http://request.cr006.iothub.ib.sdp.huawei.com", propOrder = {
		"country", "enterpriseId", "msisdn", "channel", "roaming", "specialOffer", "region", "providerId",
		"serviceName", "operatorId", "correlatorId", "extensionInfo" })
public class GetProductsRequest {

	@XmlElement(required = true)
	protected String country;
	protected String enterpriseId;
	protected String msisdn;
	@XmlElement(required = true)
	protected String channel;
	protected Integer roaming;
	protected String specialOffer;
	protected String region;
	@XmlElement(required = true)
	protected String providerId;
	@XmlElement(required = true)
	protected String serviceName;
	@XmlElement(required = true)
	protected String operatorId;
	@XmlElement(required = true)
	protected String correlatorId;
	protected List<NamedParameter> extensionInfo;

	public boolean esValido(String countryProp, String serviceNameProp, String providerIdProp, String enterpriseIdProp) throws ParameterException, ParameterEmptyException, InvalidParameterException{

		if (country == null || providerId == null || serviceName == null || correlatorId == null || enterpriseId == null || operatorId == null || channel == null) {
			throw new ParameterException();
		}

		if(country.isEmpty() || tieneEspacios(country) || enterpriseId.isEmpty() || tieneEspacios(enterpriseId) || providerId.isEmpty() || tieneEspacios(providerId) || 
				serviceName.isEmpty() || tieneEspacios(serviceName) || correlatorId.isEmpty() || tieneEspacios(correlatorId)) {
			throw new ParameterEmptyException();
		}
		if (this.msisdn == null) {
			this.msisdn = "";
		}
		
		this.msisdn = this.msisdn.length() > 10 ? this.msisdn.substring(this.msisdn.length() - 10) : this.msisdn;
		
		if(!countryProp.contains("|" + country + "|") || !serviceNameProp.contains("|" + serviceName + "|") || !providerIdProp.contains("|"+ providerId +"|") 
				|| !enterpriseIdProp.contains("|" + enterpriseId + "|")) {
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
	
	/**
	 * Obtiene el valor de la propiedad country.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Define el valor de la propiedad country.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCountry(String value) {
		this.country = value;
	}

	/**
	 * Obtiene el valor de la propiedad enterpriseId.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEnterpriseId() {
		return enterpriseId;
	}

	/**
	 * Define el valor de la propiedad enterpriseId.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEnterpriseId(String value) {
		this.enterpriseId = value;
	}

	/**
	 * Obtiene el valor de la propiedad msisdn.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMsisdn() {
		return msisdn;
	}

	/**
	 * Define el valor de la propiedad msisdn.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMsisdn(String value) {
		this.msisdn = value;
	}

	/**
	 * Obtiene el valor de la propiedad channel.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * Define el valor de la propiedad channel.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setChannel(String value) {
		this.channel = value;
	}

	/**
	 * Obtiene el valor de la propiedad roaming.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getRoaming() {
		return roaming;
	}

	/**
	 * Define el valor de la propiedad roaming.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setRoaming(Integer value) {
		this.roaming = value;
	}

	/**
	 * Obtiene el valor de la propiedad specialOffer.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSpecialOffer() {
		return specialOffer;
	}

	/**
	 * Define el valor de la propiedad specialOffer.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSpecialOffer(String value) {
		this.specialOffer = value;
	}

	/**
	 * Obtiene el valor de la propiedad region.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Define el valor de la propiedad region.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRegion(String value) {
		this.region = value;
	}

	/**
	 * Obtiene el valor de la propiedad providerId.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * Define el valor de la propiedad providerId.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setProviderId(String value) {
		this.providerId = value;
	}

	/**
	 * Obtiene el valor de la propiedad serviceName.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Define el valor de la propiedad serviceName.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setServiceName(String value) {
		this.serviceName = value;
	}

	/**
	 * Obtiene el valor de la propiedad operatorId.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOperatorId() {
		return operatorId;
	}

	/**
	 * Define el valor de la propiedad operatorId.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOperatorId(String value) {
		this.operatorId = value;
	}

	/**
	 * Obtiene el valor de la propiedad correlatorId.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCorrelatorId() {
		return correlatorId;
	}

	/**
	 * Define el valor de la propiedad correlatorId.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCorrelatorId(String value) {
		this.correlatorId = value;
	}

	/**
	 * Gets the value of the extensionInfo property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the extensionInfo property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getExtensionInfo().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link NamedParameter }
	 * 
	 * 
	 */
	public List<NamedParameter> getExtensionInfo() {
		if (extensionInfo == null) {
			extensionInfo = new ArrayList<NamedParameter>();
		}
		return this.extensionInfo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<GetProductsRequest><");
		if (msisdn != null) {
			builder.append("msisdn>");
			builder.append(msisdn);
			builder.append("</msisdn><");
		}
		if (specialOffer != null) {
			builder.append("specialOffer>");
			builder.append(specialOffer);
			builder.append("</specialOffer><");
		}
		if (region != null) {
			builder.append("region>");
			builder.append(region);
			builder.append("</region><");
		}
		if (country != null) {
			builder.append("country>");
			builder.append(country);
			builder.append("</country><");
		}
		if (roaming != null) {
			builder.append("roaming>");
			builder.append(roaming);
			builder.append("</roaming><");
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
		if (channel != null) {
			builder.append("channel>");
			builder.append(channel);
			builder.append("</channel><");
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
		if (enterpriseId != null) {
			builder.append("enterpriseId>");
			builder.append(enterpriseId);
			builder.append("</enterpriseId><");
		}
		if (operatorId != null) {
			builder.append("operatorId>");
			builder.append(operatorId);
			builder.append("</operatorId");
		}
		builder.append("></GetProductsRequest>");
		return builder.toString();
	}
	
	
}
