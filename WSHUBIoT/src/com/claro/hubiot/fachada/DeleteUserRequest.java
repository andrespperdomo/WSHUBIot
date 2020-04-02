package com.claro.hubiot.fachada;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DeleteUserRequest")
public class DeleteUserRequest {

	@XmlElement(name = "deleteUserRequest")
	private com.claro.hubiot.dto.DeleteUserRequest request;

	public com.claro.hubiot.dto.DeleteUserRequest getRequest() {
		return request;
	}

	public void setRequest(com.claro.hubiot.dto.DeleteUserRequest request) {
		this.request = request;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<DeleteUserRequest><");
		if (request != null) {
			builder.append("request>");
			builder.append(request);
			builder.append("</request");
		}
		builder.append("></DeleteUserRequest>");
		return builder.toString();
	}
	
}
