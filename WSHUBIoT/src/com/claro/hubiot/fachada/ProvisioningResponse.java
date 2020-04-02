package com.claro.hubiot.fachada;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProvisioningResponse {
	@XmlElement(name = "provisioningResponse")
	private com.claro.hubiot.dto.ProvisioningResponse response;

	public com.claro.hubiot.dto.ProvisioningResponse getResponse() {
		return response;
	}

	
	public void setResponse(com.claro.hubiot.dto.ProvisioningResponse response) {
		this.response = response;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<ProvisioningResponse><");
		if (response != null) {
			builder.append("response>");
			builder.append(response);
			builder.append("</response");
		}
		builder.append("></ProvisioningResponse>");
		return builder.toString();
	}
	

}
