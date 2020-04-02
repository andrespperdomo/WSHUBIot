
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
 * <p>Clase Java para changeSimRequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="changeSimRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="msisdn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="iccid1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="iccid2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eid1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="eid2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="correlatorId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serviceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "changeSimRequest", namespace = "http://request.cr004.iothub.ib.sdp.huawei.com", propOrder = {
		"msisdn",
		"iccid1",
		"iccid2",
		"eid1",
		"eid2",
		"correlatorId",
		"country",
		"providerId",
		"serviceName",
		"userId",
		"extensionInfo"
})
public class ChangeSimRequest {

	@XmlElement(required = true)
	protected String msisdn;
	@XmlElement(required = true)
	protected String iccid1;
	protected String iccid2;
	@XmlElement(required = true)
	protected String eid1;
	protected String eid2;
	@XmlElement(required = true)
	protected String correlatorId;
	@XmlElement(required = true)
	protected String country;
	@XmlElement(required = true)
	protected String providerId;
	@XmlElement(required = true)
	protected String serviceName;
	@XmlElement(required = true)
	protected String userId;
	protected List<NamedParameter> extensionInfo;
	protected String imsi;

	/**
	 * Obtiene el valor de la propiedad msisdn.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getMsisdn() {
		return msisdn;
	}

	/**
	 * Define el valor de la propiedad msisdn.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setMsisdn(String value) {
		this.msisdn = value;
	}

	/**
	 * Obtiene el valor de la propiedad iccid1.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getIccid1() {
		return iccid1;
	}

	/**
	 * Define el valor de la propiedad iccid1.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setIccid1(String value) {
		this.iccid1 = value;
	}

	/**
	 * Obtiene el valor de la propiedad iccid2.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getIccid2() {
		return iccid2;
	}

	/**
	 * Define el valor de la propiedad iccid2.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setIccid2(String value) {
		this.iccid2 = value;
	}

	/**
	 * Obtiene el valor de la propiedad eid1.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getEid1() {
		return eid1;
	}

	/**
	 * Define el valor de la propiedad eid1.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setEid1(String value) {
		this.eid1 = value;
	}

	/**
	 * Obtiene el valor de la propiedad eid2.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getEid2() {
		return eid2;
	}

	/**
	 * Define el valor de la propiedad eid2.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setEid2(String value) {
		this.eid2 = value;
	}

	/**
	 * Obtiene el valor de la propiedad correlatorId.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getCorrelatorId() {
		return correlatorId;
	}

	/**
	 * Define el valor de la propiedad correlatorId.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setCorrelatorId(String value) {
		this.correlatorId = value;
	}

	/**
	 * Obtiene el valor de la propiedad country.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Define el valor de la propiedad country.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setCountry(String value) {
		this.country = value;
	}

	/**
	 * Obtiene el valor de la propiedad providerId.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * Define el valor de la propiedad providerId.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setProviderId(String value) {
		this.providerId = value;
	}

	/**
	 * Obtiene el valor de la propiedad serviceName.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Define el valor de la propiedad serviceName.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setServiceName(String value) {
		this.serviceName = value;
	}

	/**
	 * Obtiene el valor de la propiedad userId.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Define el valor de la propiedad userId.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setUserId(String value) {
		this.userId = value;
	}

	/**
	 * Gets the value of the extensionInfo property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the extensionInfo property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getExtensionInfo().add(newItem);
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


	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public boolean esValido(String countryProp, String serviceNameProp, String providerIdsProp) throws InvalidParameterException, ParameterEmptyException, ParameterException{
		if( msisdn == null ||  iccid1 == null ||  eid1  == null || correlatorId == null || country == null || providerId == null || 
				serviceName == null || userId == null)
			throw new ParameterException();
		
		if(msisdn.isEmpty() || tieneEspacios(msisdn) || iccid1.isEmpty() || tieneEspacios(iccid1) || iccid2.isEmpty() || tieneEspacios(iccid2)
				|| eid1.isEmpty() || correlatorId.isEmpty() || tieneEspacios(correlatorId) || country.isEmpty() || tieneEspacios(country) 
				|| providerId.isEmpty() || tieneEspacios(providerId) || serviceName.isEmpty() || tieneEspacios(serviceName) || userId.isEmpty() || serviceName.isEmpty()) {
			throw new ParameterEmptyException();
		}
		
		if(this.msisdn!=null) {
			this.msisdn = this.msisdn.length() > 10 ? this.msisdn.substring(this.msisdn.length() - 10) : this.msisdn;
		}
		
		if(!countryProp.contains("|" + country + "|") || !serviceNameProp.contains("|" + serviceName + "|") || !providerIdsProp.contains("|"+ providerId +"|")) {
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
	public String toString(){
		StringBuilder sb = new StringBuilder();	
		sb.append( "<ChangeSimRequest>" );
		if( msisdn != null ){
			sb.append( "<msisdn>" );
			sb.append( msisdn );
			sb.append( "</msisdn>" );
		}	
		if( iccid1 != null ){
			sb.append( "<iccid1>" );
			sb.append( iccid1 );
			sb.append( "</iccid1>" );
		}
		if( eid1 != null ){
			sb.append( "<eid1>" );
			sb.append( eid1 );
			sb.append( "</eid1>" );
		}
		if( correlatorId != null ){
			sb.append( "<correlatorId>" );
			sb.append( correlatorId );
			sb.append( "</correlatorId>" );
		}
		if( country != null ){
			sb.append( "<country>" );
			sb.append( country );
			sb.append( "</country>" );
		}
		if( providerId != null ){
			sb.append( "<providerId>" );
			sb.append( providerId );
			sb.append( "</providerId>" );
		}
		if( providerId != null ){
			sb.append( "<serviceName>" );
			sb.append( serviceName );
			sb.append( "</serviceName>" );
		}
		if( providerId != null ){
			sb.append( "<userId>" );
			sb.append( userId );
			sb.append( "</userId>" );
		}
		sb.append( "</ChangeSimRequest>" );
		return sb.toString();
	}

}
