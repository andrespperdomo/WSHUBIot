package com.claro.hubiot.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.Scanner;
import java.util.TimerTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.claro.hubiot.servicios.Memoria;

import co.com.globalhitss.util.configuracion.Propiedades;

	
public class TaskMemoria extends TimerTask {

	private static Long uuid;
	
	public TaskMemoria(long uuid) {
		TaskMemoria.uuid = uuid;
		almacenarUUID(uuid);
	}
	
	private static Propiedades prop = Propiedades.getInstance();
	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);


	@Override
	public void run() {
		Long uuidArch = obtenerUUIDArchivo();
		if (uuid.equals(uuidArch)) {
			logger.info("Coincidencia de UUID para el día" + new Date(System.currentTimeMillis()) + ". UUID: " + uuid);		
			Memoria.getInstance().recargarMemoria();			
		}else {
			this.cancel();
			logger.info("Finaliza Carga Programado en Memoria");
		}	
	}
	
	private Long obtenerUUIDArchivo() {
		String serverName = System.getProperty("weblogic.Name");
		String linea = null;
		Long resultado = null;
		try (Scanner scanner = new Scanner(new File(prop.obtenerPropiedad(Constantes.RUTA_ARCHIVO_UUID, serverName)))) {
			linea = scanner.nextLine();
			resultado= Long.parseLong(linea);
		}
		catch(Exception e) {
			logger.info("Archivo no existe o no es posible acceder a el.");
		}
		return resultado;
	}

	private void almacenarUUID(Long uuid2) {
		String serverName = System.getProperty("weblogic.Name");
		logger.info("Almacenar UUID en archivo" );
		FileWriter flwriter = null;

		try {

			File file = new File(prop.obtenerPropiedad(Constantes.RUTA_ARCHIVO_UUID, serverName));

			if (!file.exists()) {
				file.createNewFile();
				logger.info("Se crea archivo nuevo con exito ");	
			}

			flwriter = new FileWriter(prop.obtenerPropiedad(Constantes.RUTA_ARCHIVO_UUID, serverName));
			BufferedWriter bfwriter = new BufferedWriter(flwriter);
			bfwriter.write(uuid2.toString());			
			bfwriter.close();
			logger.info("Archivo creado con exito " + "UUID=="+ uuid2);	

		} catch (Exception e) {
			logger.info("Archivo no  pudo ser creado con exito ");	
		} 
	}
}
