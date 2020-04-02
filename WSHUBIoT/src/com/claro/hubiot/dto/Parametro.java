package com.claro.hubiot.dto;

public class Parametro {


	private String parametroPl;
	private String valorParametro;
	private String valorFinal;
	public String getParametroPl() {
		return parametroPl;
	}
	public void setParametroPl(String parametroPl) {
		this.parametroPl = parametroPl;
	}
	public String getValorParametro() {
		return valorParametro;
	}
	public void setValorParametro(String valorParametro) {
		this.valorParametro = valorParametro;
	}
	public String getValorFinal() {
		return valorFinal;
	}
	public void setValorFinal(String valorFinal) {
		this.valorFinal = valorFinal;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<Parametro><");
		if (parametroPl != null) {
			builder.append("parametroPl>");
			builder.append(parametroPl);
			builder.append("</parametroPl><");
		}
		if (valorParametro != null) {
			builder.append("valorParametro>");
			builder.append(valorParametro);
			builder.append("</valorParametro><");
		}
		if (valorFinal != null) {
			builder.append("valorFinal>");
			builder.append(valorFinal);
			builder.append("</valorFinal");
		}
		builder.append("></Parametro>");
		return builder.toString();
	}



}
