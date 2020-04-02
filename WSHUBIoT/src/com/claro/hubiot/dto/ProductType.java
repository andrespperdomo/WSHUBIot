
package com.claro.hubiot.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ProductType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ProductType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vat" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="shortName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="group" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="includedHours" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="includedBytes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="roaming" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="altNameOne" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="altNameTwo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="attribute1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attribute2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attribute3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="value1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="value2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="value3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "ProductType", propOrder = {
		"sku",
		"name",
		"price",
		"currency",
		"vat",
		"duration",
		"shortName",
		"group",
		"type",
		"includedHours",
		"includedBytes",
		"roaming",
		"altNameOne",
		"altNameTwo",
		"attribute1",
		"attribute2",
		"attribute3",
		"value1",
		"value2",
		"value3",
		"extensionInfo"
})
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductType {
	@XmlElement(required = true)
	protected String sku;
	@XmlElement(required = true)
	protected String name;
	protected double price;
	protected String currency;
	protected Double vat;
	protected Integer duration;
	@XmlElement(required = true)
	protected String shortName;
	@XmlElement(required = true)
	protected String group;
	@XmlElement(required = true)
	protected String type;
	@XmlElement(required = true)
	protected String includedHours;
	@XmlElement(required = true)
	protected String includedBytes;
	@XmlElement(required = true)
	protected String roaming;
	@XmlElement(required = true)
	protected String altNameOne;
	@XmlElement(required = true)
	protected String altNameTwo;
	protected String attribute1;
	protected String attribute2;
	protected String attribute3;
	protected String value1;
	protected String value2;
	protected String value3;
	protected List<NamedParameter> extensionInfo;

	@XmlTransient
	protected String coid;
	@XmlTransient
	protected java.sql.Timestamp fechaExpiracion;
	@XmlTransient
	protected String prioridad;
	
	/**
	 * Obtiene el valor de la propiedad sku.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getSku() {
		return sku;
	}

	/**
	 * Define el valor de la propiedad sku.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setSku(String value) {
		this.sku = value;
	}

	/**
	 * Obtiene el valor de la propiedad name.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getName() {
		return name;
	}

	/**
	 * Define el valor de la propiedad name.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Obtiene el valor de la propiedad price.
	 * 
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Define el valor de la propiedad price.
	 * 
	 */
	public void setPrice(double value) {
		this.price = value;
	}

	/**
	 * Obtiene el valor de la propiedad currency.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Define el valor de la propiedad currency.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setCurrency(String value) {
		this.currency = value;
	}

	/**
	 * Obtiene el valor de la propiedad vat.
	 * 
	 * @return
	 *     possible object is
	 *     {@link Double }
	 *     
	 */
	public Double getVat() {
		return vat;
	}

	/**
	 * Define el valor de la propiedad vat.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link Double }
	 *     
	 */
	public void setVat(Double value) {
		this.vat = value;
	}

	/**
	 * Obtiene el valor de la propiedad duration.
	 * 
	 * @return
	 *     possible object is
	 *     {@link Integer }
	 *     
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * Define el valor de la propiedad duration.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link Integer }
	 *     
	 */
	public void setDuration(Integer value) {
		this.duration = value;
	}

	/**
	 * Obtiene el valor de la propiedad shortName.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * Define el valor de la propiedad shortName.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setShortName(String value) {
		this.shortName = value;
	}

	/**
	 * Obtiene el valor de la propiedad group.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * Define el valor de la propiedad group.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setGroup(String value) {
		this.group = value;
	}

	/**
	 * Obtiene el valor de la propiedad type.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getType() {
		return type;
	}

	/**
	 * Define el valor de la propiedad type.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setType(String value) {
		this.type = value;
	}

	/**
	 * Obtiene el valor de la propiedad includedHours.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getIncludedHours() {
		return includedHours;
	}

	/**
	 * Define el valor de la propiedad includedHours.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setIncludedHours(String value) {
		this.includedHours = value;
	}

	/**
	 * Obtiene el valor de la propiedad includedBytes.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getIncludedBytes() {
		return includedBytes;
	}

	/**
	 * Define el valor de la propiedad includedBytes.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setIncludedBytes(String value) {
		this.includedBytes = value;
	}

	/**
	 * Obtiene el valor de la propiedad roaming.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getRoaming() {
		return roaming;
	}

	/**
	 * Define el valor de la propiedad roaming.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setRoaming(String value) {
		this.roaming = value;
	}

	/**
	 * Obtiene el valor de la propiedad altNameOne.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getAltNameOne() {
		return altNameOne;
	}

	/**
	 * Define el valor de la propiedad altNameOne.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setAltNameOne(String value) {
		this.altNameOne = value;
	}

	/**
	 * Obtiene el valor de la propiedad altNameTwo.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getAltNameTwo() {
		return altNameTwo;
	}

	/**
	 * Define el valor de la propiedad altNameTwo.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setAltNameTwo(String value) {
		this.altNameTwo = value;
	}

	/**
	 * Obtiene el valor de la propiedad attribute1.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getAttribute1() {
		return attribute1;
	}

	/**
	 * Define el valor de la propiedad attribute1.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setAttribute1(String value) {
		this.attribute1 = value;
	}

	/**
	 * Obtiene el valor de la propiedad attribute2.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getAttribute2() {
		return attribute2;
	}

	/**
	 * Define el valor de la propiedad attribute2.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setAttribute2(String value) {
		this.attribute2 = value;
	}

	/**
	 * Obtiene el valor de la propiedad attribute3.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getAttribute3() {
		return attribute3;
	}

	/**
	 * Define el valor de la propiedad attribute3.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setAttribute3(String value) {
		this.attribute3 = value;
	}

	/**
	 * Obtiene el valor de la propiedad value1.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getValue1() {
		return value1;
	}

	/**
	 * Define el valor de la propiedad value1.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setValue1(String value) {
		this.value1 = value;
	}

	/**
	 * Obtiene el valor de la propiedad value2.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getValue2() {
		return value2;
	}

	/**
	 * Define el valor de la propiedad value2.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setValue2(String value) {
		this.value2 = value;
	}

	/**
	 * Obtiene el valor de la propiedad value3.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getValue3() {
		return value3;
	}

	/**
	 * Define el valor de la propiedad value3.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setValue3(String value) {
		this.value3 = value;
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



	public String getCoid() {
		return coid;
	}

	public void setCoid(String coid) {
		this.coid = coid;
	}

	

	public java.sql.Timestamp getFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(java.sql.Timestamp fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<ProductType><sku>");
		builder.append(sku);
		builder.append("</sku><name>");
		builder.append(name);
		builder.append("</name><price>");
		builder.append(price);
		builder.append("</price><currency>");
		builder.append(currency);
		builder.append("</currency><vat>");
		builder.append(vat);
		builder.append("</vat><duration>");
		builder.append(duration);
		builder.append("</duration><shortName>");
		builder.append(shortName);
		builder.append("</shortName><group>");
		builder.append(group);
		builder.append("</group><type>");
		builder.append(type);
		builder.append("</type><includedHours>");
		builder.append(includedHours);
		builder.append("</includedHours><includedBytes>");
		builder.append(includedBytes);
		builder.append("</includedBytes><roaming>");
		builder.append(roaming);
		builder.append("</roaming><altNameOne>");
		builder.append(altNameOne);
		builder.append("</altNameOne><altNameTwo>");
		builder.append(altNameTwo);
		builder.append("</altNameTwo><attribute1>");
		builder.append(attribute1);
		builder.append("</attribute1><attribute2>");
		builder.append(attribute2);
		builder.append("</attribute2><attribute3>");
		builder.append(attribute3);
		builder.append("</attribute3><value1>");
		builder.append(value1);
		builder.append("</value1><value2>");
		builder.append(value2);
		builder.append("</value2><value3>");
		builder.append(value3);
		builder.append("</value3><extensionInfo>");
		builder.append(extensionInfo);
		builder.append("</extensionInfo><coid>");
		builder.append(coid);
		builder.append("</coid><fechaExpiracion>");
		builder.append(fechaExpiracion);
		builder.append("</fechaExpiracion><prioridad>");
		builder.append(prioridad);
		builder.append("</prioridad></ProductType>");
		return builder.toString();
	}
}
