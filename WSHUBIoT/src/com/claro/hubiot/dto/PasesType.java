
package com.claro.hubiot.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.claro.hubiot.util.Constantes;

import co.com.globalhitss.util.configuracion.Propiedades;

public class PasesType {
	private String codigoPL;
	private String msisdn;
	private String productId;
	private Integer secuencia;
	private String fechaActivacion;
	private String fechaexpiracion;
	
	
	
	public PasesType(String codigoPL, String msisdn, String productId, Integer secuencia, java.sql.Timestamp fechaActivacion,
			java.sql.Timestamp fechaexpiracion) {
		this.codigoPL = codigoPL;
		this.msisdn = msisdn;
		this.productId = productId;
		this.secuencia = secuencia;
		this.fechaActivacion = getFechaStr(fechaActivacion);
		this.fechaexpiracion = getFechaStr(fechaexpiracion);
	}
	
	public String getCodigoPL() {
		return codigoPL;
	}
	public void setCodigoPL(String codigoPL) {
		this.codigoPL = codigoPL;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}
	public String getFechaActivacion() {
		return fechaActivacion;
	}
	public void setFechaActivacion(String fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}
	public String getFechaexpiracion() {
		return fechaexpiracion;
	}
	public void setFechaexpiracion(String fechaexpiracion) {
		this.fechaexpiracion = fechaexpiracion;
	}
	
	private static String getFechaStr(java.sql.Timestamp fecha) {
		Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);
		try {
			DateFormat sd = new SimpleDateFormat(Propiedades.getInstance().obtenerPropiedad( Constantes.FORMATO_FECHA_COMCORP));
			sd.setTimeZone(TimeZone.getTimeZone(Propiedades.getInstance().obtenerPropiedad(Constantes.ZONA_HORARIA_PASES_TYPE)));
			return sd.format(fecha);
		}catch(Exception ex) {
			logger.error("Error parseando la fecha ",ex);
			return "";
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<PasesType><");
		if (codigoPL != null) {
			builder.append("codigoPL>");
			builder.append(codigoPL);
			builder.append("</codigoPL><");
		}
		if (msisdn != null) {
			builder.append("msisdn>");
			builder.append(msisdn);
			builder.append("</msisdn><");
		}
		if (productId != null) {
			builder.append("productId>");
			builder.append(productId);
			builder.append("</productId><");
		}
		if (secuencia != null) {
			builder.append("secuencia>");
			builder.append(secuencia);
			builder.append("</secuencia><");
		}
		if (fechaActivacion != null) {
			builder.append("fechaActivacion>");
			builder.append(fechaActivacion);
			builder.append("</fechaActivacion><");
		}
		if (fechaexpiracion != null) {
			builder.append("fechaexpiracion>");
			builder.append(fechaexpiracion);
			builder.append("</fechaexpiracion");
		}
		builder.append("></PasesType>");
		return builder.toString();
	}
	
}
