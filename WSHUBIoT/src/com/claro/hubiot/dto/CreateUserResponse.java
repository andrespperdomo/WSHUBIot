package com.claro.hubiot.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreateUserResponse {

	private Integer resultCode;
	private String resultMessage;
	private String correlatorId;
	private List<NamedParameter> extensionInfo;
	
	public CreateUserResponse() {
	}
	
	public CreateUserResponse(String correlator, List<NamedParameter> list) {
		this.correlatorId = correlator;
		this.extensionInfo = list;
	}


	/**
	 * @return el valor de resultCode
	 */
	public Integer getResultCode() {
		return resultCode;
	}


	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(Integer resultCode) {
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
		builder.append("<CreateUserResponse><resultCode>");
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
		builder.append("></CreateUserResponse>");
		return builder.toString();
	}
	
	
}
