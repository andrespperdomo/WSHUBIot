package com.claro.hubiot.fachada;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreateUserResponse {
	@XmlElement(name = "createUserResponse")
	private com.claro.hubiot.dto.CreateUserResponse response;

	public com.claro.hubiot.dto.CreateUserResponse getResponse() {
		return response;
	}

	public void setResponse(com.claro.hubiot.dto.CreateUserResponse response) {
		this.response = response;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<CreateUserResponse><");
		if (response != null) {
			builder.append("response>");
			builder.append(response);
			builder.append("</response");
		}
		builder.append("></CreateUserResponse>");
		return builder.toString();
	}
	
}
