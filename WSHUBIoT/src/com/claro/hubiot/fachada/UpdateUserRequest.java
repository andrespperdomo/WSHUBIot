package com.claro.hubiot.fachada;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="UpdateUserRequest")
public class UpdateUserRequest {

	  @XmlElement(name = "updateUserRequest")
	  private com.claro.hubiot.dto.UpdateUserRequest request;

	public com.claro.hubiot.dto.UpdateUserRequest getRequest() {
		return request;
	}

	public void setRequest(com.claro.hubiot.dto.UpdateUserRequest request) {
		this.request = request;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<UpdateUserRequest><");
		if (request != null) {
			builder.append("request>");
			builder.append(request);
			builder.append("</request");
		}
		builder.append("></UpdateUserRequest>");
		return builder.toString();
	}
	  
	
}
