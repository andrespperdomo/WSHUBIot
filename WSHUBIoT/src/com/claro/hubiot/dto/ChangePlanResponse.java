package com.claro.hubiot.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChangePlanResponse {

	private int resultCode;
	private String resultMessage;
	private String correlatorId;
	private List<NamedParameter> extensionInfo;


	public ChangePlanResponse() {
	}


	public int getResultCode() {
		return resultCode;
	}


	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}


	public String getResultMessage() {
		return resultMessage;
	}


	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}


    public String getCorrelatorId() {
		return correlatorId;
	}


	public void setCorrelatorId(String correlatorId) {
		this.correlatorId = correlatorId;
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
		builder.append("<ChangePlanResponse><resultCode>");
		builder.append(resultCode);
		builder.append("</resultCode><");
		if (resultMessage != null) {
			builder.append("resultMessage>");
			builder.append(resultMessage);
			builder.append("</resultMessage><");
		}
		if (correlatorId != null) {
			builder.append("correlatorId>");
			builder.append(correlatorId);
			builder.append("</correlatorId><");
		}
		if (extensionInfo != null) {
			builder.append("extensionInfo>");
			builder.append(extensionInfo);
			builder.append("</extensionInfo");
		}
		builder.append("></ChangePlanResponse>");
		return builder.toString();
	}
	

}
