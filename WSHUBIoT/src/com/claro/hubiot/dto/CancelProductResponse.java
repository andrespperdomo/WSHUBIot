
package com.claro.hubiot.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para cancelProductResponseType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="cancelProductResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resultCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resultMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="correlatorId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cancelProductList" type="{http://schema.ib.sdp.huawei.com}CancelledProductType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="iccid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="imsi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="msisdn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "cancelProductResponse", namespace = "http://response.cr004.iothub.ib.sdp.huawei.com", propOrder = {
    "resultCode",
    "resultMessage",
    "correlatorId",
    "cancelProductList",
    "iccid",
    "imsi",
    "msisdn",
    "country",
    "providerId",
    "orderId",
    "extensionInfo"
})
public class CancelProductResponse {

    @XmlElement(required = true)
    protected String resultCode;
    protected String resultMessage;
    @XmlElement(required = true)
    protected String correlatorId;
    protected List<CancelledProductType> cancelProductList;
    protected String iccid;
    protected String imsi;
    protected String msisdn;
    protected String country;
    protected String providerId;
    protected String orderId;
    protected List<NamedParameter> extensionInfo;

    /**
     * Obtiene el valor de la propiedad resultCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultCode() {
        return resultCode;
    }

    public void setCancelProductList(List<CancelledProductType> cancelProductList) {
		this.cancelProductList = cancelProductList;
	}

	public void setExtensionInfo(List<NamedParameter> extensionInfo) {
		this.extensionInfo = extensionInfo;
	}

	/**
     * Define el valor de la propiedad resultCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultCode(String value) {
        this.resultCode = value;
    }

    /**
     * Obtiene el valor de la propiedad resultMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * Define el valor de la propiedad resultMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultMessage(String value) {
        this.resultMessage = value;
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
     * Gets the value of the cancelProductList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cancelProductList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCancelProductList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CancelledProductType }
     * 
     * 
     */
    public List<CancelledProductType> getCancelProductList() {
        if (cancelProductList == null) {
            cancelProductList = new ArrayList<CancelledProductType>();
        }
        return this.cancelProductList;
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
		builder.append("<CancelProductResponse><");
		if (resultCode != null) {
			builder.append("resultCode>");
			builder.append(resultCode);
			builder.append("</resultCode><");
		}
		if (resultMessage != null) {
			builder.append("resultMessage>");
			builder.append(resultMessage);
			builder.append("</resultMessage><");
		}
		if (correlatorId != null) {
			builder.append("correlatorId>");
			builder.append(correlatorId);
			builder.append("</correlatorId><");
		}
		if (cancelProductList != null) {
			builder.append("cancelProductList>");
			builder.append(cancelProductList);
			builder.append("</cancelProductList><");
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
		if (orderId != null) {
			builder.append("orderId>");
			builder.append(orderId);
			builder.append("</orderId><");
		}
		if (extensionInfo != null) {
			builder.append("extensionInfo>");
			builder.append(extensionInfo);
			builder.append("</extensionInfo");
		}
		builder.append("></CancelProductResponse>");
		return builder.toString();
	}
    
}
