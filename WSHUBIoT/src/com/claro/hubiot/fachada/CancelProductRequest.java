package com.claro.hubiot.fachada;

import javax.xml.bind.annotation.XmlElement;

public class CancelProductRequest {

	@XmlElement(name = "cancelProductRequest")
	private com.claro.hubiot.dto.CancelProductRequest request;

	public com.claro.hubiot.dto.CancelProductRequest getRequest() {
		return request;
	}

	public void setRequest(com.claro.hubiot.dto.CancelProductRequest request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "<CancelProductRequest><request>" + request + "</request></CancelProductRequest>";
	}
	
}
