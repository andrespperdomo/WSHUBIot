package com.claro.hubiot.dto;

public class LlaveMensajes implements Comparable<LlaveMensajes>{

	private String nombrePl;
	private String valorPl;


	public LlaveMensajes(String nombrePl, String valorPl) {
		this.nombrePl = nombrePl;
		this.valorPl = valorPl;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombrePl == null) ? 0 : nombrePl.hashCode());
		result = prime * result + ((valorPl == null) ? 0 : valorPl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == null)
			return false;
		if (this == obj)
			return true;		
		if (getClass() != obj.getClass())
			return false;
		LlaveMensajes other = (LlaveMensajes) obj;
		if (nombrePl == null) {
			if (other.nombrePl != null)
				return false;
		} else if (!nombrePl.equals(other.nombrePl))
			return false;
		if (valorPl == null) {
			if (other.valorPl != null)
				return false;
		} else if (!valorPl.equals(other.valorPl))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(LlaveMensajes llave) {
		String temp = this.nombrePl + this.valorPl;
		String temp2 = llave.getNombrePl() + llave.getValorPl();
		return temp.compareTo(temp2);
	}
}
