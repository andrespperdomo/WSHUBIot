package com.claro.hubiot.fachada;

import javax.xml.bind.annotation.XmlElement;

public class CancelProductResponse {

	@XmlElement(name = "cancelProductResponse")
	private com.claro.hubiot.dto.CancelProductResponse response;

	public com.claro.hubiot.dto.CancelProductResponse getResponse() {
		return response;
	}

	public void setResponse(com.claro.hubiot.dto.CancelProductResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "<CancelProductResponse><response>" + response + "</response></CancelProductResponse>";
	}
	
}
