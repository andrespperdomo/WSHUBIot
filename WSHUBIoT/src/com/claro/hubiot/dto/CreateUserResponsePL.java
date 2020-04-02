package com.claro.hubiot.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreateUserResponsePL  {
	
	private Integer resultCode;
	private String resultMessage;
	private CursorCreateUser cursor;
	
	
	/**
	 * @return el valor de cursor
	 */
	public CursorCreateUser getCursor() {
		return cursor;
	}
	/**
	 * @param cursor the cursor to set
	 */
	public void setCursor(CursorCreateUser cursor) {
		this.cursor = cursor;
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
	/**
	 * @return el valor de resultMessage
	 */
	public String getResultMessage() {
		return resultMessage;
	}
	/**
	 * @param resultMessage the resultMessage to set
	 */
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
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
		builder.append("<CreateUserResponsePL><");
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
		builder.append("></CreateUserResponsePL>");
		return builder.toString();
	}
}
