package com.claro.hubiot.fachada;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UpdateUserResponse {
	
	@XmlElement(name = "updateUserResponse")
	private com.claro.hubiot.dto.UpdateUserResponse response;

	public com.claro.hubiot.dto.UpdateUserResponse getResponse() {
		return response;
	}

	public void setResponse(com.claro.hubiot.dto.UpdateUserResponse response) {
		this.response = response;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<UpdateUserResponse><");
		if (response != null) {
			builder.append("response>");
			builder.append(response);
			builder.append("</response");
		}
		builder.append("></UpdateUserResponse>");
		return builder.toString();
	}
}
