package com.claro.hubiot.util;

import java.util.Calendar;
import java.util.Timer;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.claro.hubiot.servicios.Memoria;

import co.com.globalhitss.util.configuracion.Propiedades;

public class TimerHUBIoT {

	private static Propiedades prop = Propiedades.getInstance();
	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);
	private static TaskMemoria task;

	public static void iniciarTimer() {
		logger.info("Comienzan a Cargar Memoria");
		Memoria.getInstance().configurarMemoria();
		logger.info("Termina de cargar Memoria");
		Calendar calTimer = Calendar.getInstance();

		calTimer.set(Calendar.HOUR_OF_DAY,Integer.parseInt(prop.obtenerPropiedad(Constantes.HORA_INICIO)));
		calTimer.set(Calendar.MINUTE, Integer.parseInt(prop.obtenerPropiedad(Constantes.MINUTOS_INICIO)));
		calTimer.set(Calendar.SECOND, 0);
		calTimer.set(Calendar.MILLISECOND, 0);

		Long retrasoMin =Long.parseLong(prop.obtenerPropiedad(Constantes.RETRASO));
		Long retraso = 1000 * 60 * retrasoMin;	

		Calendar ahora = Calendar.getInstance();
		while (ahora.after(calTimer)){
			calTimer.setTimeInMillis(calTimer.getTimeInMillis() + retraso);
		}

		task = new TaskMemoria(UUID.randomUUID().getMostSignificantBits());
		Timer timer = new Timer();	
		timer.scheduleAtFixedRate(task, calTimer.getTime(), retraso);	
	}
	
	public static void detenerTimer() {
		task.cancel();
	}
}
