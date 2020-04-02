package com.claro.hubiot.servicios;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.claro.hubiot.database.ServiciosBD;
import com.claro.hubiot.util.Constantes;

import co.com.globalhitss.util.configuracion.Propiedades;

public class ActualizaPaseThread extends Thread {

	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);
	private static Propiedades prop = Propiedades.getInstance();
	private String msisdn;
	
	public ActualizaPaseThread(String msisdn) {
		super();
		this.msisdn = msisdn;
	}

	@Override
	public void run() {

		logger.info("Inicia el hilo para actualziar en COMCORP la fecha de finalización del pase  ");
		String result = "";
		for(int i = 0; i < Integer.parseInt(prop.obtenerPropiedad(Constantes.REINTENTOS_ACTUALIZA_HILO_COMCORP)) ; i++) {
			try {
				result = ServiciosBD.actualizaPaseFechacomcorp(this.msisdn);
				if(prop.obtenerPropiedad(Constantes.ACTUALIZA_PASE_COMCORP_OK).contains(result)) {
					break;
				}
				ActualizaPaseThread.sleep(Long.parseLong(prop.obtenerPropiedad(Constantes.SLEEP_HILO_ACTUALIZA_PASE)));
			}catch(Exception ex) {
				logger.info("Ocurrio una excpcion en la actualizacion del pase en COMCORP." , ex);
				try {
					ActualizaPaseThread.sleep(Long.parseLong(prop.obtenerPropiedad(Constantes.SLEEP_HILO_ACTUALIZA_PASE)));
				} catch (NumberFormatException | InterruptedException e) {
					logger.info("Ocurrio una excepcion en la actualizacion del pase en COMCORP." , e);
				}
			}
		}
		logger.info("Finaliza el hilo para actualziar en COMCORP la fecha de finalización del pase  ");
	}
	
}
