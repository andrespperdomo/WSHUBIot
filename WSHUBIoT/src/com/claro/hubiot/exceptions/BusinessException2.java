package com.claro.hubiot.exceptions;

/**
 * Clase que genera la excepcion a nivel general de la aplicacion
 * @author AUTOR
 *
 */
public class BusinessException2 extends Exception {

	private static final long serialVersionUID = 2592220159805079552L;

	public BusinessException2(String msg, Throwable  e){		
		super(msg , e);		
	}

	public BusinessException2(String msg){		
		super(msg);		
	}	
}
