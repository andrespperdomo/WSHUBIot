package com.claro.hubiot.dto;

public class CursorCreateUser {

	private String providerId;
	private String accountUserId;
	private String iccId;
	private String imsi;
	private String msisdn;
	
	/**
	 * @return el valor de providerId
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * @param providerId the providerId to set
	 */
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	/**
	 * @return el valor de accountUserId
	 */
	public String getAccountUserId() {
		return accountUserId;
	}

	/**
	 * @param accountUserId the accountUserId to set
	 */
	public void setAccountUserId(String accountUserId) {
		this.accountUserId = accountUserId;
	}

	/**
	 * @return el valor de iccId
	 */
	public String getIccId() {
		return iccId;
	}

	/**
	 * @param iccId the iccId to set
	 */
	public void setIccId(String iccId) {
		this.iccId = iccId;
	}

	/**
	 * @return el valor de imsi
	 */
	public String getImsi() {
		return imsi;
	}

	/**
	 * @param imsi the imsi to set
	 */
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	/**
	 * @return el valor de msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}

	/**
	 * @param msisdn the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**	<b>Nombre: </b> toString </br>
	 * <b>Descripción:</b>   </br>
	 * <b>Fecha Creación:</b> 2/04/2018 </br>
	 * <b>Autor:</b> HITSS  Andrea Daza </br>
	 * <b>Fecha de Última Modificación: </b></br>
	 * <b>Modificado por: </b></br>
	 * <b>Brief: 02072</b></br>
	 * @return
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<cursor><");
		if (providerId != null) {
			builder.append("providerId>");
			builder.append(providerId);
			builder.append("</providerId><");
		}
		if (accountUserId != null) {
			builder.append("accountUserId>");
			builder.append(accountUserId);
			builder.append("</accountUserId><");
		}
		if (iccId != null) {
			builder.append("iccId>");
			builder.append(iccId);
			builder.append("</iccId><");
		}
		if (imsi != null) {
			builder.append("imsi>");
			builder.append(imsi);
			builder.append("</imsi><");
		}
		if (msisdn != null) {
			builder.append("msisdn>");
			builder.append(msisdn);
			builder.append("</msisdn");
		}
		builder.append("></cursor>");
		return builder.toString();
	}
}
