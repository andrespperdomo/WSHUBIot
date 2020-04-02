package com.claro.hubiot.fachada;

import javax.xml.bind.annotation.XmlElement;

public class GetProductsRequest {
	@XmlElement(name = "getProductsRequest")
	private com.claro.hubiot.dto.GetProductsRequest request;

	public com.claro.hubiot.dto.GetProductsRequest getRequest() {
		return request;
	}

	public void setRequest(com.claro.hubiot.dto.GetProductsRequest request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "<GetProductsRequest><request>" 
	+ request + "</request></GetProductsRequest>";
	}
	
	

}
