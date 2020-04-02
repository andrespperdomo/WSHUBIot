package com.claro.hubiot.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.claro.hubiot.util.Constantes;

import co.com.globalhitss.util.configuracion.Propiedades;

@XmlRootElement
public class GetDownloadProfileStatusResponse {

	private static Propiedades prop = Propiedades.getInstance();
	
	private String resultCode;
	private String resultMessage;	
	private String correlatorId;
	private String msisdn;	
	private String imsi;
	private String simStatus;	
	private String serviceName;
	private String iccid; 
	private String planId;
	private String providerId;	
	private List<NamedParameter> extensionInfo;


	public GetDownloadProfileStatusResponse() {
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


	public String getMsisdn() {
		return msisdn;
	}


	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn.length() == 10 ? prop.obtenerPropiedad(Constantes.CODIGO_PAIS)+msisdn : msisdn;
	}


	public String getImsi() {
		return imsi;
	}


	public void setImsi(String imsi) {
		this.imsi = imsi;
	}


	public String getSimStatus() {
		return simStatus;
	}


	public void setSimStatus(String simStatus) {
		this.simStatus = simStatus;
	}


	public String getServiceName() {
		return serviceName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	public String getIccid() {
		return iccid;
	}


	public void setIccid(String iccid) {
		this.iccid = iccid;
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


	public String getPlanId() {
		return planId;
	}


	public void setPlanId(String planId) {
		this.planId = planId;
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
		builder.append("<GetDownloadProfileStatusResponse><");
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
		if (correlatorId != null) {
			builder.append("correlatorId>");
			builder.append(correlatorId);
			builder.append("</correlatorId><");
		}
		if (msisdn != null) {
			builder.append("msisdn>");
			builder.append(msisdn);
			builder.append("</msisdn><");
		}
		if (imsi != null) {
			builder.append("imsi>");
			builder.append(imsi);
			builder.append("</imsi><");
		}
		if (simStatus != null) {
			builder.append("simStatus>");
			builder.append(simStatus);
			builder.append("</simStatus><");
		}
		if (serviceName != null) {
			builder.append("serviceName>");
			builder.append(serviceName);
			builder.append("</serviceName><");
		}
		if (iccid != null) {
			builder.append("iccid>");
			builder.append(iccid);
			builder.append("</iccid><");
		}
		if (planId != null) {
			builder.append("planId>");
			builder.append(planId);
			builder.append("</planId><");
		}
		if (providerId != null) {
			builder.append("providerId>");
			builder.append(providerId);
			builder.append("</providerId><");
		}
		if (extensionInfo != null) {
			builder.append("extensionInfo>");
			builder.append(extensionInfo);
			builder.append("</extensionInfo");
		}
		builder.append("></GetDownloadProfileStatusResponse>");
		return builder.toString();
	}
	
}
