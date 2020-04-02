
package com.claro.hubiot.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseType", propOrder = {
    "resultCode",
    "resultMessage",
    "correlatorId",
    "extensionInfo"
})
public class UnProvisioningResponse {

    private Integer resultCode;
    private String resultMessage;
    @XmlElement(required = true)
    private String correlatorId;
    private List<NamedParameter> extensionInfo;

    /**
     * Obtiene el valor de la propiedad resultCode.
     * 
     */
    public Integer getResultCode() {
        return resultCode;
    }

    /**
     * Define el valor de la propiedad resultCode.
     * 
     */
    public void setResultCode(Integer value) {
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
		builder.append("<UnProvisioningResponse><resultCode>");
		builder.append(resultCode);
		builder.append("</resultCode><");
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
		if (extensionInfo != null) {
			builder.append("extensionInfo>");
			builder.append(extensionInfo);
			builder.append("</extensionInfo");
		}
		builder.append("></UnProvisioningResponse>");
		return builder.toString();
	}
    
    

}
