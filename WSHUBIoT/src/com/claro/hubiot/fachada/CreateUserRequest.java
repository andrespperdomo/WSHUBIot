package com.claro.hubiot.fachada;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CreateUserRequest")
public class CreateUserRequest {

	@XmlElement(name = "createUserRequest")
	private com.claro.hubiot.dto.CreateUserRequest request;

	public com.claro.hubiot.dto.CreateUserRequest getRequest() {
		return request;
	}

	public void setRequest(com.claro.hubiot.dto.CreateUserRequest request) {
		this.request = request;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<CreateUserRequest><");
		if (request != null) {
			builder.append("request>");
			builder.append(request);
			builder.append("</request");
		}
		builder.append("></CreateUserRequest>");
		return builder.toString();
	}
}
