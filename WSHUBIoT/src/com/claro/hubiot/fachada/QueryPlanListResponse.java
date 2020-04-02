package com.claro.hubiot.fachada;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



/**
 * DTO que almacena ...
 * @author AUTOR
 *
 */
@XmlRootElement
public class QueryPlanListResponse {
	@XmlElement(name = "queryPlanListResponse")
	private com.claro.hubiot.dto.QueryPlanListResponse response;

	public com.claro.hubiot.dto.QueryPlanListResponse getResponse() {
		return response;
	}

	public void setResponse(com.claro.hubiot.dto.QueryPlanListResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<QueryPlanListResponse><");
		if (response != null) {
			builder.append("response>");
			builder.append(response);
			builder.append("</response");
		}
		builder.append("></QueryPlanListResponse>");
		return builder.toString();
	}
	
	
	

}
