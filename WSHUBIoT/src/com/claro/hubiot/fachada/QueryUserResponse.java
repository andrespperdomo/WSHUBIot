package com.claro.hubiot.fachada;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QueryUserResponse {
	@XmlElement(name = "queryUserResponse")
	private com.claro.hubiot.dto.QueryUserResponse response;

	public com.claro.hubiot.dto.QueryUserResponse getResponse() {
		return response;
	}

	public void setResponse(com.claro.hubiot.dto.QueryUserResponse response) {
		this.response = response;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<QueryUserResponse><");
		if (response != null) {
			builder.append("response>");
			builder.append(response);
			builder.append("</response");
		}
		builder.append("></QueryUserResponse>");
		return builder.toString();
	}
	
	
}
