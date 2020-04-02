package com.claro.hubiot.dto;

public class Mensaje {


	private String nombrePl;
	private String valorPl;
	private String codFinal;
	private String descFinal;
	
	
	public String getNombrePl() {
		return nombrePl;
	}
	public void setNombrePl(String nombrePl) {
		this.nombrePl = nombrePl;
	}
	public String getValorPl() {
		return valorPl;
	}
	public void setValorPl(String valorPl) {
		this.valorPl = valorPl;
	}
	public String getCodFinal() {
		return codFinal;
	}
	public void setCodFinal(String codFinal) {
		this.codFinal = codFinal;
	}
	public String getDescFinal() {
		return descFinal;
	}
	public void setDescFinal(String descFinal) {
		this.descFinal = descFinal;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<Mensaje><");
		if (nombrePl != null) {
			builder.append("nombrePl>");
			builder.append(nombrePl);
			builder.append("</nombrePl><");
		}
		if (valorPl != null) {
			builder.append("valorPl>");
			builder.append(valorPl);
			builder.append("</valorPl><");
		}
		if (codFinal != null) {
			builder.append("codFinal>");
			builder.append(codFinal);
			builder.append("</codFinal><");
		}
		if (descFinal != null) {
			builder.append("descFinal>");
			builder.append(descFinal);
			builder.append("</descFinal");
		}
		builder.append("></Mensaje>");
		return builder.toString();
	}
	
}
