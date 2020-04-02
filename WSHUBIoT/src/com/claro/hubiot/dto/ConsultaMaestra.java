package com.claro.hubiot.dto;

public class ConsultaMaestra {
	
	private String custcode;
	private String tipoDoc;
	private String numDoc;
	private String codigoSalida;
	private String descripcionSalida;
	
	public String getCustcode() {
		return custcode;
	}
	
	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}
	
	public String getTipoDoc() {
		return tipoDoc;
	}
	
	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	
	public String getNumDoc() {
		return numDoc;
	}
	
	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}
	
	public String getCodigoSalida() {
		return codigoSalida;
	}
	
	public void setCodigoSalida(String codigoSalida) {
		this.codigoSalida = codigoSalida;
	}
	
	public String getDescripcionSalida() {
		return descripcionSalida;
	}
	
	public void setDescripcionSalida(String descripcionSalida) {
		this.descripcionSalida = descripcionSalida;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<ConsultaMaestra><");
		if (custcode != null) {
			builder.append("custcode>");
			builder.append(custcode);
			builder.append("</custcode><");
		}
		if (tipoDoc != null) {
			builder.append("tipoDoc>");
			builder.append(tipoDoc);
			builder.append("</tipoDoc><");
		}
		if (numDoc != null) {
			builder.append("numDoc>");
			builder.append(numDoc);
			builder.append("</numDoc><");
		}
		if (codigoSalida != null) {
			builder.append("codigoSalida>");
			builder.append(codigoSalida);
			builder.append("</codigoSalida><");
		}
		if (descripcionSalida != null) {
			builder.append("descripcionSalida>");
			builder.append(descripcionSalida);
			builder.append("</descripcionSalida");
		}
		builder.append("></ConsultaMaestra>");
		return builder.toString();
	}
}
