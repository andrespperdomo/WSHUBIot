package com.claro.hubiot.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QueryUserResponsePL {
	private Integer resultCode;
	private String resultMessage;
	private CursorQueryUser cursor;
	public Integer getResultCode() {
		return resultCode;
	}
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public CursorQueryUser getCursor() {
		return cursor;
	}
	public void setCursor(CursorQueryUser cursor) {
		this.cursor = cursor;
	}
	/**	<b>Nombre: </b> toString </br>
	 * <b>Descripción:</b>   </br>
	 * <b>Fecha Creación:</b> 4/04/2018 </br>
	 * <b>Autor:</b> HITSS  Andrea Daza </br>
	 * <b>Fecha de Última Modificación: </b></br>
	 * <b>Modificado por: </b></br>
	 * <b>Brief: 02072</b></br>
	 * @return
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<QueryUserResponsePL><");
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
		if (cursor != null) {
			builder.append(cursor);
		}
		builder.append("></QueryUserResponsePL>");
		return builder.toString();
	}

	
}
