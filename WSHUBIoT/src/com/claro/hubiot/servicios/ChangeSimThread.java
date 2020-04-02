package com.claro.hubiot.servicios;

import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.claro.cap.servicios.NotificacionCAPRequest;
import com.claro.cap.servicios.NotificacionCAPResponse;
import com.claro.cap.servicios.WSNotificacionHUB;
import com.claro.cap.servicios.WSNotificacionHUBProxy;
import com.claro.hubiot.database.ServiciosBD;
import com.claro.hubiot.dto.ChangeSimRequest;
import com.claro.hubiot.dto.ChangeSimResponse;
import com.claro.hubiot.dto.ProvisioningRequest;
import com.claro.hubiot.exceptions.BusinessException;
import com.claro.hubiot.util.Constantes;
import com.claro.hubiot.util.GeneradorResponses;

import co.com.globalhitss.util.configuracion.Configurador;
import co.com.globalhitss.util.configuracion.ParametrosIniciales;
import co.com.globalhitss.util.configuracion.Propiedades;

public class ChangeSimThread extends Thread{
	private ChangeSimResponse response = new ChangeSimResponse();
	private ChangeSimRequest request;
	private static Propiedades prop = Propiedades.getInstance();
	private ParametrosIniciales ini;
	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);
	private String callback;
	
	public ChangeSimThread(ChangeSimResponse response, ChangeSimRequest request, ParametrosIniciales ini,
			String callback) {
		super();
		this.response = response;
		this.request = request;
		this.ini = ini;
		this.callback = callback;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		String tmcode = null;
		try {
			ThreadContext.put("UUID", Long.toString(ini.getUuid()));
			this.sleep(Long.parseLong(prop.obtenerPropiedad(Constantes.SLEEP_THREAD)));
			logger.info("Inicia el hilo de changeSim");
			ServiciosBD.cambioSIM(request, response);
			if(prop.obtenerPropiedad(Constantes.CAMBIO_SIM_EXITOSO).equals(""+response.getResultCode())) {
				ServiciosBD.insertaRegistro( request.getIccid2(), request.getMsisdn(), request.getCorrelatorId(),request.getProviderId(), "", callback, ini.getUuid(), prop.obtenerPropiedad(Constantes.METODO_CHANGE_SIM ));
				GeneradorResponses.generarRespuesta( response, ""+response.getResultCode() );
			} else {
				ServiciosBD.insertaRegistro( request.getIccid1(), request.getMsisdn(), request.getCorrelatorId(),request.getProviderId(), "", callback, ini.getUuid(), prop.obtenerPropiedad(Constantes.METODO_CHANGE_SIM ));
				GeneradorResponses.generarRespuesta( response, ""+response.getResultCode() );
				logger.info("Consultando datos para enviar la notificacion:");
				ProvisioningRequest request_prov = new ProvisioningRequest();
				request_prov.setIccid(request.getIccid1());
				ServiciosBD.consultaDatosPlan(request_prov); 
				tmcode = request_prov.getNMtmcode();
				
				if(tmcode == null || tmcode.isEmpty() ) {
					logger.info("TMCODE nulo o vacio, se envia TMCODE por default : " + prop.obtenerPropiedad(Constantes.TMCODE_DEFAULT));
					tmcode = prop.obtenerPropiedad(Constantes.TMCODE_DEFAULT);
				}	
			} 
		} catch (BusinessException e) {
			logger.error("Bussiness Exception ", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_CHANGESIM);
		} catch (Exception tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_CHANGESIM);
		} finally {
			ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
					""+ response.getResultCode(),
					response.getResultMessage(),Propiedades.getInstance().obtenerPropiedad( Constantes.METODO_CHANGE_SIM ));
			if(!prop.obtenerPropiedad(Constantes.PRC_CHANGESIM_EXITOSO).equalsIgnoreCase(""+response.getResultCode())) {
				GeneradorResponses.generarRespuesta(response, Constantes.ERROR_CHANGESIM);
				notifyChangeSim(response, request, request.getIccid1(), tmcode);
			}
			logger.info("<< Respuesta de changeSim " + response.toString());
			Configurador.cerrarTransaccion(ini, logger);
		}
	}

	private void notifyChangeSim(ChangeSimResponse response, ChangeSimRequest request, String iccid, String tmcode){
		try {
			NotificacionCAPRequest requestNotificacion = new NotificacionCAPRequest();
			requestNotificacion.setTipoTransaccion(prop.obtenerPropiedad(Constantes.NOTIFICACION_CHANGESIM));
			requestNotificacion.setSistema(prop.obtenerPropiedad(Constantes.NOTIFICACION_CHANGESIM_SISTEMA));
			requestNotificacion.setMsisdn(request.getMsisdn().length() < 12 ? prop.obtenerPropiedad(Constantes.VALOR_57) + request.getMsisdn() : request.getMsisdn());
			requestNotificacion.setImsi(request.getImsi() == null ? prop.obtenerPropiedad(Constantes.IMSI_DEFAULT):request.getImsi() );
			requestNotificacion.setIccid(iccid);
			requestNotificacion.setTmCode(tmcode);
			requestNotificacion.setEstado(prop.obtenerPropiedad(Constantes.NOTIFICACION_CHANGESIM_ESTADO));
			requestNotificacion.setCodigoResultado(String.valueOf(response.getResultCode()));
			requestNotificacion.setMensajeResutado(response.getResultMessage());
			WSNotificacionHUBProxy proxy = new WSNotificacionHUBProxy(prop.obtenerPropiedad(Constantes.END_POINT_WS_NOTIFICACION));
			WSNotificacionHUB port = proxy.getWSNotificacionHUB();
			Stub stub = (Stub) port;
			stub.setTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_NOTIFICACIONHUB)));
			logger.info("Request NotificacionHUB: \n" + requestNotificacion.toString());
			NotificacionCAPResponse responseNotificacion = port.notifyResult(requestNotificacion);
			logger.info("Response NotificacionHUB: \n" + responseNotificacion.toString());
		} catch (RemoteException e) {
			logger.error("Ocurrio una excepcion", e);
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion", e);
		}		
	}
}
