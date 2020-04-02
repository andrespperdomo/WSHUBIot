package com.claro.hubiot.servicios;

import java.util.List;

import org.apache.axis.client.Stub;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.claro.cap.servicios.NotificacionCAPRequest;
import com.claro.cap.servicios.NotificacionCAPResponse;
import com.claro.cap.servicios.WSNotificacionHUB;
import com.claro.cap.servicios.WSNotificacionHUBProxy;
import com.claro.hubiot.database.ServiciosBD;
import com.claro.hubiot.dto.ChangePlanRequest;
import com.claro.hubiot.dto.ChangePlanResponse;
import com.claro.hubiot.dto.LlavePlanes;
import com.claro.hubiot.dto.PlanInfo;
import com.claro.hubiot.exceptions.BusinessException;
import com.claro.hubiot.util.Constantes;
import com.claro.hubiot.util.GeneradorResponses;

import co.com.globalhitss.util.configuracion.Configurador;
import co.com.globalhitss.util.configuracion.ParametrosIniciales;
import co.com.globalhitss.util.configuracion.Propiedades;

public class ChangePlanThread extends Thread{
	
	private ChangePlanResponse response = new ChangePlanResponse();
	private ChangePlanRequest request;
	private static Propiedades prop = Propiedades.getInstance();
	private ParametrosIniciales ini;
	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);
	private String callback;
	
	public ChangePlanThread(ChangePlanResponse response, ChangePlanRequest request, ParametrosIniciales ini,
			String callback) {
		this.response = response;
		this.request = request;
		this.ini = ini;
		this.callback = callback;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		
		try {	
			ThreadContext.put("UUID", Long.toString(ini.getUuid()));
			List<PlanInfo> planestmp;
			boolean existePlan=false;
			this.sleep(Long.parseLong(prop.obtenerPropiedad(Constantes.SLEEP_THREAD)));
			logger.info("Inicia el hilo de changePlan");
			
			
			LlavePlanes llave = new LlavePlanes(request.getProviderId(), request.getEnterpriseId());
			if (Memoria.existeOferta(llave)) {
				planestmp = Memoria.getInstance().obtenerPlanes(llave);
				if (planestmp != null) {
					logger.info("Inicia homologacion del plan "+ request.getPlanId());
					for (PlanInfo planInfo : planestmp) {
						if (planInfo.getPlanId().equalsIgnoreCase(request.getPlanId())){
							existePlan=true;
							request.setNMtmcode(planInfo.getNmtmcode());
							ServiciosBD.cambioPlan(request, response);
							GeneradorResponses.generarRespuesta(response, String.valueOf(response.getResultCode()));
							ServiciosBD.insertaRegistro(prop.obtenerPropiedad(Constantes.ICCID_DEFAULT), request.getMsisdn(), request.getCorrelatorId(),request.getProviderId(),request.getEnterpriseId(), callback, ini.getUuid(), prop.obtenerPropiedad(Constantes.METODO_CHANGE_PLAN));
						}
					}
					if(!existePlan){
						logger.error("No se encuentra el plan ingresado ");
						throw new BusinessException(Constantes.ERROR_CHANGE_PLAN);
					}
				} else {
					GeneradorResponses.generarRespuesta(response, Constantes.ERROR_CHANGE_PLAN);
				}
			}else{
				logger.error("No se encuentra oferta comercial");
				throw new BusinessException(Constantes.ERROR_CHANGE_PLAN);
			}
		} catch (BusinessException e) {
			logger.error("Error de Negocio: en el procesamiento de la solicitud", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_CHANGE_PLAN);
		} catch (Throwable tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_CHANGE_PLAN);
		} finally {
			try {
				
				String imsi = prop.obtenerPropiedad(Constantes.IMSI_DEFAULT);	
				String iccid = prop.obtenerPropiedad(Constantes.ICCID_DEFAULT);
				
				if(!prop.obtenerPropiedad(Constantes.PRC_CHANGEPLAN_EXITOSO).equalsIgnoreCase(""+response.getResultCode()) 
						&& !prop.obtenerPropiedad(Constantes.PRC_CHANGEPLAN_EXITOSO).equalsIgnoreCase(""+response.getResultCode())) {
					GeneradorResponses.generarRespuesta(response, Constantes.ERROR_CHANGEPLAN);
					notifyChangePlan(response, request, imsi, request.getNMtmcode(), iccid);
				} else if(prop.obtenerPropiedad(Constantes.PRC_CHANGEPLAN_EXITOSO_16).equalsIgnoreCase(""+response.getResultCode())){
					GeneradorResponses.generarRespuesta(response, Constantes.ALREADY_HAS_THE_PLANID);
					notifyChangePlan(response, request, imsi, request.getNMtmcode(), iccid);
				}
				
			} catch (BusinessException e) {
				logger.error("Error de Negocio: Generando la notificacion ", e);
				GeneradorResponses.generarRespuesta(response, Constantes.ERROR_CHANGE_PLAN);
			}
			logger.info("<< Respuesta de changePlan " + response.toString());
			Configurador.cerrarTransaccion(ini, logger);
		}
	}

	private void notifyChangePlan(ChangePlanResponse response, ChangePlanRequest request,String imsi, String tmcode, String iccid) throws BusinessException {

		try {
			NotificacionCAPRequest requestNotificacion = new NotificacionCAPRequest();
			requestNotificacion.setTipoTransaccion(prop.obtenerPropiedad(Constantes.NOTIFICACION_CHANGEPLAN));
			requestNotificacion.setSistema(prop.obtenerPropiedad(Constantes.NOTIFICACION_CHANGEPLAN_SISTEMA));
			requestNotificacion.setMsisdn(request.getMsisdn().length() < 12 ? prop.obtenerPropiedad(Constantes.VALOR_57) + request.getMsisdn() : request.getMsisdn());
			requestNotificacion.setImsi(imsi);
			requestNotificacion.setIccid(iccid);
			requestNotificacion.setTmCode(tmcode);
			requestNotificacion.setEstado(prop.obtenerPropiedad(Constantes.NOTIFICACION_CHANGEPLAN_ESTADO));
			requestNotificacion.setCodigoResultado(String.valueOf(response.getResultCode()));
			requestNotificacion.setMensajeResutado(response.getResultMessage());
			WSNotificacionHUBProxy proxy = new WSNotificacionHUBProxy(prop.obtenerPropiedad(Constantes.END_POINT_WS_NOTIFICACION));
			WSNotificacionHUB port = proxy.getWSNotificacionHUB();
			Stub stub = (Stub) port;
			stub.setTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_NOTIFICACIONHUB)));
			logger.info("Request NotificacionHUB: \n" + requestNotificacion.toString());
			NotificacionCAPResponse responseNotificacion = port.notifyResult(requestNotificacion);
			logger.info("Response NotificacionHUB: \n" + responseNotificacion.toString());
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion", e);
			throw new BusinessException(Constantes._102, e);
		}
		
	}
}
