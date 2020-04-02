
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
 * <p>Clase Java para cancelProductRequestType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="cancelProductRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="iccid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="imsi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="msisdn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="productId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="refundType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="correlatorId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serviceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "cancelProductRequest", namespace = "http://request.cr004.iothub.ib.sdp.huawei.com", propOrder = {
    "iccid",
    "imsi",
    "msisdn",
    "productId",
    "refundType",
    "correlatorId",
    "country",
    "providerId",
    "serviceName",
    "orderId",
    "extensionInfo"
})
public class CancelProductRequest {

    protected String iccid;
    protected String imsi;
    @XmlElement(required = true)
    protected String msisdn;
    protected String productId;
    protected String refundType;
    @XmlElement(required = true)
    protected String correlatorId;
    @XmlElement(required = true)
    protected String country;
    @XmlElement(required = true)
    protected String providerId;
    @XmlElement(required = true)
    protected String serviceName;
    protected String orderId;
    protected List<NamedParameter> extensionInfo;


    
	public boolean esValido(String countryProp, String serviceNameProp, String providerIdProp) throws ParameterException, ParameterEmptyException, InvalidParameterException{

		if(msisdn==null || correlatorId==null || country==null || providerId==null || serviceName==null){
			throw new ParameterException();
		}
		
		if (this.msisdn.length() < 10){
			return false;
		}
		
		this.msisdn = this.msisdn.length() > 10 ? this.msisdn.substring(this.msisdn.length() - 10) : this.msisdn;

		if ( providerId.isEmpty() || tieneEspacios(providerId) || correlatorId.isEmpty() || tieneEspacios(correlatorId) || serviceName.isEmpty() || tieneEspacios(serviceName)
				|| country.isEmpty() || tieneEspacios(country)) {
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
	
    /**
     * Obtiene el valor de la propiedad iccid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIccid() {
        return iccid;
    }

    /**
     * Define el valor de la propiedad iccid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIccid(String value) {
        this.iccid = value;
    }

    /**
     * Obtiene el valor de la propiedad imsi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * Define el valor de la propiedad imsi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImsi(String value) {
        this.imsi = value;
    }

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
     * Obtiene el valor de la propiedad productId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Define el valor de la propiedad productId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductId(String value) {
        this.productId = value;
    }

    /**
     * Obtiene el valor de la propiedad refundType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefundType() {
        return refundType;
    }

    /**
     * Define el valor de la propiedad refundType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefundType(String value) {
        this.refundType = value;
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
     * Obtiene el valor de la propiedad orderId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Define el valor de la propiedad orderId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderId(String value) {
        this.orderId = value;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<CancelProductRequest><");
		if (msisdn != null) {
			builder.append("msisdn>");
			builder.append(msisdn);
			builder.append("</msisdn><");
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
		if (country != null) {
			builder.append("country>");
			builder.append(country);
			builder.append("</country><");
		}
		if (productId != null) {
			builder.append("productId>");
			builder.append(productId);
			builder.append("</productId><");
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
		if (refundType != null) {
			builder.append("refundType>");
			builder.append(refundType);
			builder.append("</refundType><");
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
		if (orderId != null) {
			builder.append("orderId>");
			builder.append(orderId);
			builder.append("</orderId");
		}
		builder.append("></CancelProductRequest>");
		return builder.toString();
	}
    
    
}
