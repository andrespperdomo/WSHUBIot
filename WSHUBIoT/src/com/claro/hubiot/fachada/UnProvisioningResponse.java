
package com.claro.hubiot.fachada;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class UnProvisioningResponse {
	@XmlElement(name = "unProvisioningResponse")
	   private com.claro.hubiot.dto.UnProvisioningResponse response;

	public com.claro.hubiot.dto.UnProvisioningResponse getResponse() {
		return response;
	}

	public void setResponse(com.claro.hubiot.dto.UnProvisioningResponse response) {
		this.response = response;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<UnProvisioningResponse><");
		if (response != null) {
			builder.append("response>");
			builder.append(response);
			builder.append("</response");
		}
		builder.append("></UnProvisioningResponse>");
		return builder.toString();
	}   
   
}
