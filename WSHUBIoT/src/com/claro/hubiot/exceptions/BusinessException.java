package com.claro.hubiot.exceptions;

import com.claro.hubiot.util.Constantes;

import co.com.globalhitss.util.configuracion.Propiedades;

/**
 * Clase que genera la excepcion a nivel general de la aplicacion
 * @author AUTOR
 *
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 4751539105768366612L;
	private static Propiedades prop = Propiedades.getInstance();	

	public BusinessException(String cod, Throwable  e){		
		super("[ERROR_APP_" + cod + " " + prop.obtenerPropiedad(Constantes.EXCEPTION + cod) + "]", e);		
	}

	public BusinessException(String cod){		
		super("[ERROR_APP_" + cod + " " + prop.obtenerPropiedad(Constantes.EXCEPTION + cod) + "]");		
	}	
}
