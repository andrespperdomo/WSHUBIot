package com.claro.hubiot.dto;

public class LlaveParametros implements Comparable<LlaveParametros>{

	private String parametroPl;
	private String valorParametro;


	public LlaveParametros(String parametroPl, String valorParametro) {
		this.parametroPl = parametroPl;
		this.valorParametro = valorParametro;
	}


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
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parametroPl == null) ? 0 : parametroPl.hashCode());
		result = prime * result + ((valorParametro == null) ? 0 : valorParametro.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LlaveParametros other = (LlaveParametros) obj;
		if (parametroPl == null) {
			if (other.parametroPl != null)
				return false;
		} else if (!parametroPl.equals(other.parametroPl))
			return false;
		if (valorParametro == null) {
			if (other.valorParametro != null)
				return false;
		} else if (!valorParametro.equals(other.valorParametro))
			return false;
		return true;
	}
	@Override
	public int compareTo(LlaveParametros llave) {
		String temp = this.parametroPl + this.valorParametro;
		String temp2 = llave.getParametroPl() + llave.getValorParametro();
		return temp.compareTo(temp2);
	}
}
