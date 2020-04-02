package com.claro.hubiot.fachada;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GetDownloadProfileStatusResponse {
	@XmlElement(name = "getDownloadProfileStatusResponse")
	private com.claro.hubiot.dto.GetDownloadProfileStatusResponse response;

	public com.claro.hubiot.dto.GetDownloadProfileStatusResponse getResponse() {
		return response;
	}

	public void setResponse(com.claro.hubiot.dto.GetDownloadProfileStatusResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<GetDownloadProfileStatusResponse><");
		if (response != null) {
			builder.append("response>");
			builder.append(response);
			builder.append("</response");
		}
		builder.append("></GetDownloadProfileStatusResponse>");
		return builder.toString();
	}


}
