package com.claro.hubiot.fachada;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement()
public class GetDownloadProfileStatusRequest {
	@XmlElement(name = "getDownloadProfileStatusRequest")
	private com.claro.hubiot.dto.GetDownloadProfileStatusRequest request;

	public com.claro.hubiot.dto.GetDownloadProfileStatusRequest getGetDownloadProfileStatusRequest() {
		return request;
	}

	public void setGetDownloadProfileStatusRequest(
			com.claro.hubiot.dto.GetDownloadProfileStatusRequest request) {
		this.request= request;
	}
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<GetDownloadProfileStatusRequest><");
		if (request != null) {
			builder.append("request>");
			builder.append(request);
			builder.append("</request");
		}
		builder.append("></QueryPlanListRequest>");
		return builder.toString();
	}

}
