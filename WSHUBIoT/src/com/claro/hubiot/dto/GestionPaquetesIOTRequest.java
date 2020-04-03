package com.claro.hubiot.dto;

public class GestionPaquetesIOTRequest {

	/* Brief 31072 - Paquetes y Planes */
	private String min;
	private Double coid;
	private String imsi;
	private Integer tmCodeNew;
	private Integer tmCodeOld;
	private String accion;

	public GestionPaquetesIOTRequest() {
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public Double getCoid() {
		return coid;
	}

	public void setCoid(Double coid) {
		this.coid = coid;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public Integer getTmCodeNew() {
		return tmCodeNew;
	}

	public void setTmCodeNew(Integer tmCodeNew) {
		this.tmCodeNew = tmCodeNew;
	}

	public Integer getTmCodeOld() {
		return tmCodeOld;
	}

	public void setTmCodeOld(Integer tmCodeOld) {
		this.tmCodeOld = tmCodeOld;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<GestionPaquetesIOTRequest>");
		if (min != null) {
			sb.append("<min>");
			sb.append(min);
			sb.append("</min>");
		}
		if (coid != null) {
			sb.append("<coid>");
			sb.append(coid);
			sb.append("</coid>");
		}
		if (imsi != null) {
			sb.append("<imsi>");
			sb.append(imsi);
			sb.append("</imsi>");
		}
		if (tmCodeNew != null) {
			sb.append("<tmCodeNew>");
			sb.append(tmCodeNew);
			sb.append("</tmCodeNew>");
		}
		if (tmCodeOld != null) {
			sb.append("<tmCodeOld>");
			sb.append(tmCodeOld);
			sb.append("</tmCodeOld>");
		}
		if (accion != null) {
			sb.append("<accion>");
			sb.append(accion);
			sb.append("</accion>");
		}
		sb.append("</GestionPaquetesIOTRequest>");
		return sb.toString();
	}
}
