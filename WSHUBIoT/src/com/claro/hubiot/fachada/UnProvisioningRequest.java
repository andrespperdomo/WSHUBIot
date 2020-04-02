package com.claro.hubiot.fachada;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement()
public class UnProvisioningRequest {
	@XmlElement(name = "unProvisioningRequest")
   private  com.claro.hubiot.dto.UnProvisioningRequest request;

	public com.claro.hubiot.dto.UnProvisioningRequest getRequest() {
		return request;
	}

	public void setRequest(com.claro.hubiot.dto.UnProvisioningRequest request) {
		this.request = request;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<UnProvisioningRequest><");
		if (request != null) {
			builder.append("request>");
			builder.append(request);
			builder.append("</request");
		}
		builder.append("></UnProvisioningRequest>");
		return builder.toString();
	}
}
