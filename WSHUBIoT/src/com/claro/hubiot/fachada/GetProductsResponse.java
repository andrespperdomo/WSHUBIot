package com.claro.hubiot.fachada;

import javax.xml.bind.annotation.XmlElement;

public class GetProductsResponse {
	@XmlElement(name = "getProductsResponse")
	private com.claro.hubiot.dto.GetProductsResponse response;

	public com.claro.hubiot.dto.GetProductsResponse getResponse() {
		return response;
	}

	public void setResponse(com.claro.hubiot.dto.GetProductsResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "<GetProductsResponse><response>" + response + "</response></GetProductsResponse>";
	}

}
