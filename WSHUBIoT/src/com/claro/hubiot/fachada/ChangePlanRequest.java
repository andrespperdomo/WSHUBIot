package com.claro.hubiot.fachada;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="changePlanRequest")
public class ChangePlanRequest {
	@XmlElement(name = "changePlanRequest")
	private com.claro.hubiot.dto.ChangePlanRequest request;

	public com.claro.hubiot.dto.ChangePlanRequest getRequest() {
		return request;
	}

	public void setRequest(com.claro.hubiot.dto.ChangePlanRequest request) {
		this.request = request;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<ChangePlanRequest><");
		if (request != null) {
			builder.append("request>");
			builder.append(request);
			builder.append("</request");
		}
		builder.append("></ChangePlanRequest>");
		return builder.toString();
	}
}
