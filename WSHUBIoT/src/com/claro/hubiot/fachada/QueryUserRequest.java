package com.claro.hubiot.fachada;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="QueryUserRequest")
public class QueryUserRequest {
	@XmlElement(name = "queryUserRequest")
	private com.claro.hubiot.dto.QueryUserRequest request;

	public com.claro.hubiot.dto.QueryUserRequest getRequest() {
		return request;
	}

	public void setRequest(com.claro.hubiot.dto.QueryUserRequest request) {
		this.request = request;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<QueryUserRequest><");
		if (request != null) {
			builder.append("request>");
			builder.append(request);
			builder.append("</request");
		}
		builder.append("></QueryUserRequest>");
		return builder.toString();
	}
}
