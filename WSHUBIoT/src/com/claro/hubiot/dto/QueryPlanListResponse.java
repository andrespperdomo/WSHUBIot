package com.claro.hubiot.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO que almacena ...
 * @author AUTOR
 *
 */
@XmlRootElement
public class QueryPlanListResponse {

	private String resultCode;
	private String resultMessage;
	private String country;
	private String correlatorId;
	private String providerId;
	private List<PlanInfo> plans;
	private List<NamedParameter> extensionInfo;


	public QueryPlanListResponse() {
	}


	public String getResultCode() {
		return resultCode;
	}


	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}


	public String getResultMessage() {
		return resultMessage;
	}


	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getCorrelatorId() {
		return correlatorId;
	}


	public void setCorrelatorId(String correlatorId) {
		this.correlatorId = correlatorId;
	}


	public String getProviderId() {
		return providerId;
	}


	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}


	public List<PlanInfo> getPlans() {
		return plans;
	}


	public void setPlans(List<PlanInfo> plans) {
		this.plans = plans;
	}


	public List<NamedParameter> getExtensionInfo() {
		return extensionInfo;
	}


	public void setExtensionInfo(List<NamedParameter> extensionInfo) {
		this.extensionInfo = extensionInfo;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<QueryPlanListResponse><");
		if (resultCode != null) {
			builder.append("resultCode>");
			builder.append(resultCode);
			builder.append("</resultCode><");
		}
		if (resultMessage != null) {
			builder.append("resultMessage>");
			builder.append(resultMessage);
			builder.append("</resultMessage><");
		}
		if (country != null) {
			builder.append("country>");
			builder.append(country);
			builder.append("</country><");
		}
		if (correlatorId != null) {
			builder.append("correlatorId>");
			builder.append(correlatorId);
			builder.append("</correlatorId><");
		}
		if (providerId != null) {
			builder.append("providerId>");
			builder.append(providerId);
			builder.append("</providerId><");
		}
		if (plans != null) {
			builder.append("plans>");
			builder.append(plans);
			builder.append("</plans><");
		}
		if (extensionInfo != null) {
			builder.append("extensionInfo>");
			builder.append(extensionInfo);
			builder.append("</extensionInfo");
		}
		builder.append("></QueryPlanListResponse>");
		return builder.toString();
	}
	
}
