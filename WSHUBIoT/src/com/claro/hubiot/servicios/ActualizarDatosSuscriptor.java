package com.claro.hubiot.servicios;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.claro.cap.servicios.NamedParameter;
import com.claro.cap.servicios.NotifyUpdateUserResultRequest;
import com.claro.cap.servicios.NotifyUserResultResponse;
import com.claro.cap.servicios.WSNotificacionHUB;
import com.claro.cap.servicios.WSNotificacionHUBProxy;
import com.claro.hubiot.database.ServiciosBD;
import com.claro.hubiot.dto.UpdateUserRequest;
import com.claro.hubiot.dto.UpdateUserResponse;
import com.claro.hubiot.exceptions.BusinessException;
import com.claro.hubiot.util.Constantes;
import com.claro.hubiot.util.GeneradorResponses;

import co.com.globalhitss.util.configuracion.Configurador;
import co.com.globalhitss.util.configuracion.ParametrosIniciales;
import co.com.globalhitss.util.configuracion.Propiedades;

public class ActualizarDatosSuscriptor extends Thread {
	UpdateUserRequest request;
	ParametrosIniciales ini;
	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);
	String callback;
	private static Propiedades prop = Propiedades.getInstance();

	ActualizarDatosSuscriptor(UpdateUserRequest request, ParametrosIniciales ini,String callback) {
		this.request = request;
		this.ini = ini;
		this.callback=callback;
	}

	@Override
	public void run() {
		ThreadContext.put("UUID", Long.toString(ini.getUuid()));
		UpdateUserResponse response = new UpdateUserResponse();
		String rtaPL = "";
		try {
			rtaPL = ServiciosBD.updateUser(request);
			
			if(!rtaPL.equalsIgnoreCase(prop.obtenerPropiedad(Constantes.OK_PL_UPDATE_USER)) && !rtaPL.equalsIgnoreCase(prop.obtenerPropiedad(Constantes.OK_PL_UPDATE_USER_1))) {
				GeneradorResponses.generarRespuesta(response,  Constantes.ERROR_UPDATE_USER);
			}else if (rtaPL.equalsIgnoreCase(prop.obtenerPropiedad(Constantes.OK_PL_UPDATE_USER_1))){
				GeneradorResponses.generarRespuesta(response, Constantes.UPDATE_USER_NO_FOUND);
			}
			
			if (rtaPL.equalsIgnoreCase(prop.obtenerPropiedad(Constantes.OK_PL_UPDATE_USER))){
				GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
			}
			
		} catch (BusinessException e) {
			logger.error("Error desconocido en el sistema", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_UPDATE_USER);
		} catch (Throwable tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_UPDATE_USER);
		}  finally {
			try {
				notifyUpdateUser(response);
			}catch (Exception e) {
				logger.error("Error desconocido en el sistema", e);
			}
			
			ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
					response.getResultCode() != null ? response.getResultCode().toString() : "",
							response.getResultMessage(),Propiedades.getInstance().obtenerPropiedad(Constantes.METODO_UPDATE_USER));
			logger.info("<< Respuesta de updateUser " + response.toString());
			Configurador.cerrarTransaccion(ini, logger);
		}

	}

	private void notifyUpdateUser(UpdateUserResponse response) {
		try {
			NamedParameter[] array= null;
			if (request.getExtensionInfo() != null){
				array = new NamedParameter[request.getExtensionInfo().size()];

				int i = 0;
				for(com.claro.hubiot.dto.NamedParameter named : request.getExtensionInfo()){
					array[i++] = new NamedParameter(named.getLlave(),named.getValor());
				}
			}
			WSNotificacionHUB notif = new WSNotificacionHUBProxy(Propiedades.getInstance().obtenerPropiedad(Constantes.END_POINT_WS_NOTIFICACION));
			NotifyUpdateUserResultRequest requestWSClient = new NotifyUpdateUserResultRequest(); // extensionInfo
			requestWSClient.setProviderId(request.getProviderId());
			requestWSClient.setResultCode(response.getResultCode().toString());
			requestWSClient.setResultMessage(response.getResultMessage());
			requestWSClient.setServiceName(request.getServiceName());
			requestWSClient.setCorrelatorId(request.getCorrelatorId());
			requestWSClient.setCountry(request.getCountry());
			requestWSClient.setExtensionInfo(array);
			requestWSClient.setCallback(callback);
			logger.info("Request NotificacionHUB: \n" + requestWSClient.toString());
			NotifyUserResultResponse respuestaWSClient = notif.notifyUpdateUserResult(requestWSClient);
			logger.info("Response NotificacionHUB: \n" + respuestaWSClient.toString());
		} catch (Throwable tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_UPDATE_USER);
		} 
	}

}
