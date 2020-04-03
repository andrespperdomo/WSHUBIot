package com.claro.hubiot.servicios;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.axis.client.Stub;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.claro.cap.servicios.*;
import com.claro.hubiot.database.ServiciosBD;
import com.claro.hubiot.dto.ChangePlanRequest;
import com.claro.hubiot.dto.ChangePlanResponse;
import com.claro.hubiot.dto.GestionPaquetesIOTRequest;
import com.claro.hubiot.dto.LlavePlanes;
import com.claro.hubiot.dto.PlanInfo;
import com.claro.hubiot.dto.ProvisioningRequest;
import com.claro.hubiot.dto.ProvisioningResponse;
import com.claro.hubiot.exceptions.BusinessException;
import com.claro.hubiot.exceptions.PlanException;
import com.claro.hubiot.util.Constantes;
import com.claro.hubiot.util.GeneradorResponses;

import co.com.globalhitss.util.configuracion.Configurador;
import co.com.globalhitss.util.configuracion.ParametrosIniciales;
import co.com.globalhitss.util.configuracion.Propiedades;

public class ProvisioningThread extends Thread {
	
	private ProvisioningRequest request;
	private ProvisioningResponse response = new ProvisioningResponse();
	private static Propiedades prop = Propiedades.getInstance();
	private ParametrosIniciales ini;
	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);
	private String callback;

	public ProvisioningThread(ParametrosIniciales ini, ProvisioningRequest request, String callback) {
		this.request = request;
		this.ini = ini;
		this.callback = callback;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		String imsi = "";
		String tmcodeAMX ="";
		try {
			ThreadContext.put("UUID", Long.toString(ini.getUuid()));
			this.sleep(Long.parseLong(prop.obtenerPropiedad(Constantes.SLEEP_THREAD)));
			logger.info("Inicia el hilo provisioning");
			//consulta MSISDN y TMCODE
			ServiciosBD.consultaDatosPlan(request);
			
			tmcodeAMX=consultaTmcode(request.getPlanId(), request.getProviderId(), request.getEnterpriseId());
			if(tmcodeAMX==null){
				throw new PlanException();
			}
			logger.info("Respuesta de Tmcode:"+tmcodeAMX); 

			if(!tmcodeAMX.equalsIgnoreCase(request.getNMtmcode())){
				ChangePlanRequest cambioPlanReq= new ChangePlanRequest();
				ChangePlanResponse cambioPlanRes= new ChangePlanResponse();
				cambioPlanReq.setProviderId(request.getProviderId());
				cambioPlanReq.setEnterpriseId(request.getEnterpriseId());
				cambioPlanReq.setMsisdn(request.getMsisdn() == null ? "" : request.getMsisdn().length() > 10 ? request.getMsisdn().substring(request.getMsisdn().length() - 10) : request.getMsisdn());
				cambioPlanReq.setNMtmcode(tmcodeAMX);
				ServiciosBD.provisioning(request, response);
				if(prop.obtenerPropiedad(Constantes.PRC_APROVISIONA_EXITOSO).equalsIgnoreCase(""+response.getResultCode())){
					ServiciosBD.cambioPlan(cambioPlanReq, cambioPlanRes);
					//rollback si el cambio falla
					if(!prop.obtenerPropiedad(Constantes.PRC_APROVISIONA_EXITOSO).equalsIgnoreCase(""+cambioPlanRes.getResultCode())){
						throw new BusinessException(Constantes.ERROR_PROCEDIMIENTO);
					}
				}
			}else{
				ServiciosBD.provisioning(request, response);
			}
			
			GeneradorResponses.generarRespuesta(response, String.valueOf(response.getResultCode()));

     		ServiciosBD.insertaRegistro(request.getIccid(), request.getMsisdn(), request.getCorrelatorId(),request.getProviderId(),request.getEnterpriseId(), callback, ini.getUuid(), prop.obtenerPropiedad(Constantes.METODO_PROVISIONING));
			
			imsi = ServiciosBD.consultarImsi (request.getIccid());
			
			if(imsi == null || imsi.isEmpty()) {
				logger.info("Imsi nulo o vacio, se envia imsi por defecto: " +prop.obtenerPropiedad(Constantes.IMSI_DEFAULT));
				imsi = prop.obtenerPropiedad(Constantes.IMSI_DEFAULT);
			}
			
		} catch (PlanException e) {
			logger.error("Parametro nulo en la consulta de tmcode",e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_ACT);
		} catch (BusinessException e) {
			logger.error("Error de Negocio: en el procesamiento de la solicitud", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_ACT);
		} catch (Throwable tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_ACT);
		} finally {
			try {
				if(prop.obtenerPropiedad(Constantes.PRC_APROVISIONA_EXITOSO).equalsIgnoreCase(""+response.getResultCode())) {
					GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
				} else {
					if(!prop.obtenerPropiedad(Constantes.PRC_APROVISIONA_EXITOSO_27).equalsIgnoreCase(""+response.getResultCode()) 
							&& !prop.obtenerPropiedad(Constantes.PRC_APROVISIONA_EXITOSO).equalsIgnoreCase(""+response.getResultCode())) {
						GeneradorResponses.generarRespuesta(response, Constantes.ERROR_ACT);	
					} else if(prop.obtenerPropiedad(Constantes.PRC_APROVISIONA_EXITOSO_27).equalsIgnoreCase(""+response.getResultCode())){
						GeneradorResponses.generarRespuesta(response, Constantes.ICCID_ALREADY_IN_USE);
					}
				}
				
				//Brief 31072 - Paquetes y Planes 
				gestionProvisioningIoTReq(request, tmcodeAMX);
				//Brief 31072 - Paquetes y Planes
				
				notifyProvisioning(response, imsi, request.getMsisdn());
			} catch (BusinessException e) {
				logger.error("Error de Negocio: fallo en la notificacion", e);
				GeneradorResponses.generarRespuesta(response, Constantes.ERROR_ACT);
			}
			ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
					response.getResultCode() != null ? response.getResultCode().toString() : "",
							response.getResultMessage(),Propiedades.getInstance().obtenerPropiedad(Constantes.METODO_PROVISIONING));
			logger.info("<< Respuesta de provisioning " + response.toString());
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
	 * @param response 
	 * @param request 
	 * @throws BusinessException 
	 */
	private void notifyProvisioning(ProvisioningResponse response, String imsi, String msisdn) throws BusinessException {
		try {
			NotifyProvisioningResultRequest requestNotificacion = new NotifyProvisioningResultRequest();
			requestNotificacion.setCorrelatorId(request.getCorrelatorId());
			requestNotificacion.setCallback(callback);
			requestNotificacion.setSimStatus(prop.obtenerPropiedad(Constantes.NOTIFICACION_ACTIVACION_ESTADO));
			requestNotificacion.setIccid(request.getIccid());
			requestNotificacion.setPlanId(request.getPlanId());
			requestNotificacion.setServiceName(prop.obtenerPropiedad(Constantes.NOTIFICACION_ACTIVACION));
			requestNotificacion.setProviderId(request.getProviderId());
			requestNotificacion.setMsisdn(msisdn.length() < 12 ? prop.obtenerPropiedad(Constantes.VALOR_57) + msisdn : msisdn);
			requestNotificacion.setImsi(imsi);
			requestNotificacion.setResultCode(String.valueOf(response.getResultCode()));
			requestNotificacion.setResultMessage(response.getResultMessage());
			WSNotificacionHUBProxy proxy = new WSNotificacionHUBProxy(prop.obtenerPropiedad(Constantes.END_POINT_WS_NOTIFICACION));
			WSNotificacionHUB port = proxy.getWSNotificacionHUB();
			Stub stub = (Stub) port;
			stub.setTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_NOTIFICACIONHUB)));
			logger.info("Request NotificacionHUB: \n" + requestNotificacion.toString());
			NotifyUserResultResponse responseNotificacion = port.notifyProvisioningResult(requestNotificacion);
			logger.info("Response NotificacionHUB: \n" + responseNotificacion.toString());
		} catch (RemoteException e) {
			logger.error("Ocurrio una excepcion", e);
			throw new BusinessException(Constantes._102, e);
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion", e);
			throw new BusinessException(Constantes._102, e);
		}	
	}
	
	private String consultaTmcode(String planID,String proveedor,String empresa){
		logger.info("Ingresa a consultar tmcode");
		List<PlanInfo> planestmp;
		String tmcode=null;
		LlavePlanes llave = new LlavePlanes(proveedor, empresa);
		if (Memoria.existeOferta(llave)) {
			planestmp = Memoria.getInstance().obtenerPlanes(llave);
			if (planestmp != null) {
				for (PlanInfo planInfo : planestmp) {
					if (planInfo.getPlanId().equalsIgnoreCase(planID)){
						tmcode=planInfo.getNmtmcode();
					}
				}
			} 
		}
		return tmcode;
	}
	
	/**
	 * Brief 31072 - Paquetes y Planes
	 * @param request
	 * @param tmcodeAMX
	 */
	private void gestionProvisioningIoTReq(ProvisioningRequest request,String tmcodeAMX) throws BusinessException{
		GestionPaquetesIOTRequest gestionIoTReq = new GestionPaquetesIOTRequest();
		gestionIoTReq.setMin(request.getMsisdn());
		gestionIoTReq.setImsi(request.getImsi());
		gestionIoTReq.setCoid(Double.parseDouble(request.getCorrelatorId()));
		gestionIoTReq.setTmCodeNew(Integer.valueOf(tmcodeAMX));
		gestionIoTReq.setAccion(Constantes.PAQ_IOT_V_ACCION_AL);
		
		ServiciosBD.gestionPaQIoT(gestionIoTReq);
    }
}
