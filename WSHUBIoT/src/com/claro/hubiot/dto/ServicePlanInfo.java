package com.claro.hubiot.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ServicePlanInfo {

	@XmlElement(name = "planId")
	private String planId;

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<ServicePlanInfo><");
		if (planId != null) {
			builder.append("planId>");
			builder.append(planId);
			builder.append("</planId");
		}
		builder.append("></ServicePlanInfo>");
		return builder.toString();
	}
		
}
