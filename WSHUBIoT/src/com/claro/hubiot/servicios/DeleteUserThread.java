package com.claro.hubiot.servicios;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.claro.cap.servicios.NamedParameter;
import com.claro.cap.servicios.NotifyDeleteUserResultRequest;
import com.claro.cap.servicios.NotifyUserResultResponse;
import com.claro.cap.servicios.WSNotificacionHUB;
import com.claro.cap.servicios.WSNotificacionHUBProxy;
import com.claro.hubiot.database.ServiciosBD;
import com.claro.hubiot.dto.DeleteUserRequest;
import com.claro.hubiot.dto.DeleteUserResponse;
import com.claro.hubiot.exceptions.BusinessException;
import com.claro.hubiot.util.Constantes;
import com.claro.hubiot.util.GeneradorResponses;

import co.com.globalhitss.util.configuracion.Configurador;
import co.com.globalhitss.util.configuracion.ParametrosIniciales;
import co.com.globalhitss.util.configuracion.Propiedades;

public class DeleteUserThread extends Thread {

	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);
	private static Propiedades prop = Propiedades.getInstance();

	private ParametrosIniciales ini;
	private DeleteUserRequest request;
	private String callback;

	public ParametrosIniciales getIni() {
		return ini;
	}

	public void setIni(ParametrosIniciales ini) {
		this.ini = ini;
	}

	public DeleteUserRequest getRequest() {
		return request;
	}

	public void setRequest(DeleteUserRequest request) {
		this.request = request;
	}

	public DeleteUserThread() {
	}

	public DeleteUserThread(ParametrosIniciales ini, DeleteUserRequest request,String callback) {
		this.ini = ini;
		this.request = request;
		this.callback=callback;
	}

	@SuppressWarnings("static-access")
	public void run() {
		DeleteUserResponse response = new DeleteUserResponse();
		try {
			ThreadContext.put("UUID", Long.toString(ini.getUuid()));
			this.sleep(Long.parseLong(prop.obtenerPropiedad(Constantes.SLEEP_THREAD)));
			ServiciosBD.deleteUser(request, response);
			
			if(!response.getResultCode().toString().equalsIgnoreCase(prop.obtenerPropiedad(Constantes.OK_PL_DELETE_USER)) 
					&& !response.getResultCode().toString().equalsIgnoreCase(prop.obtenerPropiedad(Constantes.OK_PL_DELETE_USER_1))) {
				GeneradorResponses.generarRespuesta(response,  Constantes.ERROR_DELETE_USER);
			}else if (response.getResultCode().toString().equalsIgnoreCase(prop.obtenerPropiedad(Constantes.OK_PL_DELETE_USER_1))){
				GeneradorResponses.generarRespuesta(response, Constantes.DELETE_USER_NO_FOUND);
			}
			
			if (response.getResultCode().toString().equalsIgnoreCase(prop.obtenerPropiedad(Constantes.OK_PL_UPDATE_USER))){
				GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
			}
			
		} catch (BusinessException e) {
			logger.error("Error de Negocio: en el procesamiento de la solicitud", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_DELETE_USER);
		} catch (Throwable tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_DELETE_USER);
		} finally {
			// consumir el WS
			notifyDeleteUser(response);

			ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
					response.getResultCode() != null ? response.getResultCode().toString() : "",
							response.getResultMessage(),prop.obtenerPropiedad(Constantes.METODO_DELETE_USER));
			logger.info("<< Respuesta de deleteUser " + response.toString());
			Configurador.cerrarTransaccion(ini, logger);
		}

	}

	/**
	 * <b>Nombre: </b> notifyDeleteUser </br>
	 * <b>Descripción:</b> Consume el Web Service de respuesta del servicio de
	 * delete </br>
	 * <b>Fecha Creación:</b> 2/04/2018 </br>
	 * <b>Autor:</b> HITSS Fabio Orjuela Diaz</br>
	 * <b>Fecha de Última Modificación: </b></br>
	 * <b>Modificado por: </b></br>
	 * <b>Brief: 02072</b></br>
	 */
	private void notifyDeleteUser(DeleteUserResponse response) {
		try {
			WSNotificacionHUB notif = new WSNotificacionHUBProxy(Propiedades.getInstance().obtenerPropiedad(Constantes.END_POINT_WS_NOTIFICACION));
			NamedParameter[] array= null; 
			if (request.getExtensionInfo() != null){
				array = new NamedParameter[request.getExtensionInfo().size()];
				for (int i = 0; i < request.getExtensionInfo().size(); i++) {

					com.claro.cap.servicios.NamedParameter named = new com.claro.cap.servicios.NamedParameter(request.getExtensionInfo().get(i).getLlave(), request.getExtensionInfo().get(i).getValor());

					array[i]=named;
				}
			}

			NotifyDeleteUserResultRequest requestWSClient = new NotifyDeleteUserResultRequest(request.getServiceName(),
					request.getProviderId(),
					request.getCountry(),
					request.getCorrelatorId(), 
					response.getResultCode().toString(), 
					response.getResultMessage(),
					array,callback);

			logger.info("Request NotificacionHUB: \n" + requestWSClient.toString());
			NotifyUserResultResponse respuestaWSClient = notif.notifyDeleteUserResult(requestWSClient);
			logger.info("Response NotificacionHUB: \n" + respuestaWSClient.toString());
		} catch (Throwable tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_INESPERADO);
		}
	}

}
