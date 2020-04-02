package com.claro.hubiot.servicios;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.claro.cap.servicios.NamedParameter;
import com.claro.cap.servicios.NotifyCreateUserResultRequest;
import com.claro.cap.servicios.NotifyUserResultResponse;
import com.claro.cap.servicios.WSNotificacionHUB;
import com.claro.cap.servicios.WSNotificacionHUBProxy;
import com.claro.hubiot.database.ServiciosBD;
import com.claro.hubiot.dto.CreateUserRequest;
import com.claro.hubiot.dto.CreateUserResponse;
import com.claro.hubiot.dto.CreateUserResponsePL;
import com.claro.hubiot.exceptions.BusinessException;
import com.claro.hubiot.util.Constantes;
import com.claro.hubiot.util.GeneradorResponses;

import co.com.globalhitss.util.configuracion.Configurador;
import co.com.globalhitss.util.configuracion.ParametrosIniciales;
import co.com.globalhitss.util.configuracion.Propiedades;

public class CreateUserThread extends Thread {

	private CreateUserRequest request;
	private CreateUserResponse response = new CreateUserResponse();
	private CreateUserResponsePL responsePL = new CreateUserResponsePL();
	private ParametrosIniciales ini;
	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);
	private String callback;
	private static Propiedades prop = Propiedades.getInstance();

	public CreateUserThread(ParametrosIniciales ini, CreateUserRequest request,String callback) {
		this.request = request;
		this.ini = ini;
		this.callback=callback;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try {
			ThreadContext.put("UUID", Long.toString(ini.getUuid()));
			this.sleep(Long.parseLong(prop.obtenerPropiedad(Constantes.SLEEP_THREAD)));
			logger.info("Inicia el hilo createUser");
			// consumir el PL
			ServiciosBD.crearSuscriptor(request, responsePL);
			// Mapear la respuesta
			if(responsePL.getResultCode().toString().equalsIgnoreCase(prop.obtenerPropiedad(Constantes.OK_PL_CREATE_USER))) {
				GeneradorResponses.generarRespuesta(response,  Constantes.RESPUESTA_ACK);
			}else {
				GeneradorResponses.generarRespuesta(response, Constantes.ERROR_CREATE_USER);
			}
		} catch (BusinessException e) {
			logger.error("Error de Negocio: en el procesamiento de la solicitud", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_CREATE_USER);
		} catch (Throwable tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_CREATE_USER);
		} finally {
			// consumir el WS
			notifyCreateUser();
			ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
					response.getResultCode() != null ? response.getResultCode().toString() : "",
							response.getResultMessage(),Propiedades.getInstance().obtenerPropiedad(Constantes.METODO_CREATE_USER));
			logger.info("<< Respuesta de createUser " + response.toString());
			Configurador.cerrarTransaccion(ini, logger);
		}
	}

	/**	<b>Nombre: </b> notifyCreateUser </br>
	 * <b>Descripción:</b> Consume el Web Service   </br>
	 * <b>Fecha Creación:</b> 2/04/2018 </br>
	 * <b>Autor:</b> HITSS  Andrea Daza </br>
	 * <b>Fecha de Última Modificación: </b></br>
	 * <b>Modificado por: </b></br>
	 * <b>Brief: 02072</b></br>
	 */
	private void notifyCreateUser() {
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
			NotifyCreateUserResultRequest requestWSClient = new NotifyCreateUserResultRequest();
			requestWSClient.setProviderId(request.getProviderId());
			requestWSClient.setResultCode(response.getResultCode().toString());
			requestWSClient.setResultMessage(response.getResultMessage());
			requestWSClient.setServiceName(request.getServiceName());
			requestWSClient.setCorrelatorId(request.getCorrelatorId());
			requestWSClient.setCountry(request.getCountry());
			requestWSClient.setBillingAccountNumber(responsePL.getCursor()!=null?responsePL.getCursor().getAccountUserId():null);
			requestWSClient.setIccid(responsePL.getCursor()!=null? responsePL.getCursor().getIccId():null);
			requestWSClient.setImsi(responsePL.getCursor()!=null? responsePL.getCursor().getImsi():null);
			String msisdn=null;
			if(responsePL.getCursor()!=null && responsePL.getCursor().getMsisdn()!=null ){
				msisdn=responsePL.getCursor().getMsisdn().length() < 12 ? prop.obtenerPropiedad(Constantes.VALOR_57) + responsePL.getCursor().getMsisdn() : responsePL.getCursor().getMsisdn();
			}
			requestWSClient.setMsisdn(msisdn);
			requestWSClient.setExtensionInfo(array);
			requestWSClient.setCallback(callback);
			logger.info("Request NotificacionHUB: \n" + requestWSClient.toString());
			NotifyUserResultResponse respuestaWSClient = notif.notifyCreateUserResult(requestWSClient);
			logger.info("Response NotificacionHUB: \n" + respuestaWSClient.toString());
		} catch (Throwable tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_INESPERADO);
		} 
	}

}
