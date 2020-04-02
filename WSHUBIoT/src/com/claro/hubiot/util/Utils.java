package com.claro.hubiot.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.globalhitss.util.configuracion.Propiedades;

public abstract class Utils {


	private static Propiedades prop = Propiedades.getInstance();
	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);


	public static Boolean esNumerico(String parametro) {

		boolean numerico = false;
		try {
			Long.parseLong(parametro);
			numerico = true;
		} catch (Exception e) {
			logger.error("Error intentando parsear " + parametro);
		}
		return numerico;
	}


	public static String getEncryptedPass(String passLimpio)   {  	    
		try {
			MessageDigest md = MessageDigest.getInstance(prop.obtenerPropiedad(Constantes.TIPO_ENCRIPTACION));//"SHA-256"
			byte[] result = md.digest(passLimpio.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error encriptando el pass", e);
		}
		return "";
	}

}
