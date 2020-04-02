package com.claro.hubiot.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NamedParameter {

	@XmlElement(name = "key")
	private String llave;
	@XmlElement(name = "value")
	private String valor;
	
	
	public String getLlave() {
		return llave;
	}
	public void setLlave(String llave) {
		this.llave = llave;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
	@Override
	public String toString() {
		return "<NamedParameter><" + (llave != null ? "llave>" + llave + "</llave><" : "")
				+ (valor != null ? "valor>" + valor + "</valor" : "") + "></NamedParameter>";
	}
	
	
	

}
