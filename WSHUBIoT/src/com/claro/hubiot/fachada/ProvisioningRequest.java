package com.claro.hubiot.fachada;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ProvisioningRequest")
public class ProvisioningRequest {
	@XmlElement(name = "provisioningRequest")
	private com.claro.hubiot.dto.ProvisioningRequest request;

	public com.claro.hubiot.dto.ProvisioningRequest getRequest() {
		return request;
	}

	public void setRequest(com.claro.hubiot.dto.ProvisioningRequest request) {
		this.request = request;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<ProvisioningRequest><");
		if (request != null) {
			builder.append("request>");
			builder.append(request);
			builder.append("</request");
		}
		builder.append("></ProvisioningRequest>");
		return builder.toString();
	}
	
}
