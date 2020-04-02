package com.claro.hubiot.fachada;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChangePlanResponse {
	@XmlElement(name = "changePlanResponse")
	private com.claro.hubiot.dto.ChangePlanResponse response;

	public com.claro.hubiot.dto.ChangePlanResponse getResponse() {
		return response;
	}

	public void setResponse(com.claro.hubiot.dto.ChangePlanResponse response) {
		this.response = response;
	}@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<ChangePlanResponse><");
		if (response != null) {
			builder.append("response>");
			builder.append(response);
			builder.append("</response");
		}
		builder.append("></ChangePlanResponse>");
		return builder.toString();
	}
	
	
	
}
