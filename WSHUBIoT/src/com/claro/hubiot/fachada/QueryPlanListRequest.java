package com.claro.hubiot.fachada;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO que almacena ...
 * @author AUTOR
 *
 */
@XmlRootElement()
public class QueryPlanListRequest{
	@XmlElement(name = "queryPlanListRequest")
	private com.claro.hubiot.dto.QueryPlanListRequest request;

	public com.claro.hubiot.dto.QueryPlanListRequest getRequest() {
		return request;
	}

	public void setRequest(com.claro.hubiot.dto.QueryPlanListRequest request) {
		this.request = request;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<QueryPlanListRequest><");
		if (request != null) {
			builder.append("request>");
			builder.append(request);
			builder.append("</request");
		}
		builder.append("></QueryPlanListRequest>");
		return builder.toString();
	}
	
		
}
