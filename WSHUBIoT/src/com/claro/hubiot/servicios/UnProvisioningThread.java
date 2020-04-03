package com.claro.hubiot.servicios;

import java.rmi.RemoteException;
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
import com.claro.hubiot.dto.GestionPaquetesIOTRequest;
import com.claro.hubiot.dto.LlavePlanes;
import com.claro.hubiot.dto.PlanInfo;
import com.claro.hubiot.dto.UnProvisioningRequest;
import com.claro.hubiot.dto.UnProvisioningResponse;
import com.claro.hubiot.exceptions.BusinessException;
import com.claro.hubiot.util.Constantes;
import com.claro.hubiot.util.GeneradorResponses;

import co.com.globalhitss.util.configuracion.Configurador;
import co.com.globalhitss.util.configuracion.ParametrosIniciales;
import co.com.globalhitss.util.configuracion.Propiedades;

public class UnProvisioningThread extends Thread {

	private UnProvisioningResponse response = new UnProvisioningResponse();
	private UnProvisioningRequest request;
	private static Propiedades prop = Propiedades.getInstance();
	private ParametrosIniciales ini;
	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);
	private String callback;
	
	public UnProvisioningThread(ParametrosIniciales ini, UnProvisioningRequest request, String callback) {
		this.request = request;
		this.ini = ini;
		this.callback = callback;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		String tmcodeAMX = "";
		try {
			ThreadContext.put("UUID", Long.toString(ini.getUuid()));
			this.sleep(Long.parseLong(prop.obtenerPropiedad(Constantes.SLEEP_THREAD)));
			logger.info("Inicia el hilo de UnProvisioning");
			ServiciosBD.unProvisioning(request, response);
			

			/* Brief 31072 - Paquetes y Planes */
			tmcodeAMX = consultaTmcode(request.getPlanId(), request.getProviderId(), request.getEnterpriseId());
			/* Brief 31072 - Paquetes y Planes */

			//if (Integer.parseInt(prop.obtenerPropiedad(Constantes.PRC_DESAAPROVISIONA_EXITOSO)) == response.getResultCode()) {

			ServiciosBD.insertaRegistro(request.getIccid(), request.getMsisdn(), request.getCorrelatorId(),request.getProviderId(),request.getEnterpriseId(), callback, ini.getUuid(), prop.obtenerPropiedad(Constantes.METODO_UNPROVISIONING));

			//}
			GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
			
		} catch (BusinessException e) {
			logger.error("Error de Negocio: en el procesamiento de la solicitud", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_DESACT);
		} catch (Throwable tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_DESACT);
		} finally {
			try {
				/* Brief 31072 - Paquetes y Planes */
				gestionProvisioningIoTReq(request, tmcodeAMX);
				/* Brief 31072 - Paquetes y Planes */
				
				if(!prop.obtenerPropiedad(Constantes.PRC_DESAPROVISIONA_EXITOSO_07).equalsIgnoreCase(""+response.getResultCode()) 
						&& !prop.obtenerPropiedad(Constantes.PRC_DESAPROVISIONA_EXITOSO).equalsIgnoreCase(""+response.getResultCode())) {
					GeneradorResponses.generarRespuesta(response, Constantes.ERROR_DESACT);
					notifyUnProvisioning(response, request);
				} else if(prop.obtenerPropiedad(Constantes.PRC_DESAPROVISIONA_EXITOSO_07).equalsIgnoreCase(""+response.getResultCode())){
					GeneradorResponses.generarRespuesta(response, Constantes.LINE_IS_ALREADY_CANCELLED);
					notifyUnProvisioning(response, request);
				}
				
			} catch (BusinessException e) {
				logger.error("Error de Negocio: en el procesamiento de la solicitud", e);
				GeneradorResponses.generarRespuesta(response, Constantes.ERROR_DESACT);
			}
			logger.info("<< Respuesta de unProvisioning " + response.toString());
			Configurador.cerrarTransaccion(ini, logger);
		}
	}

	private void notifyUnProvisioning(UnProvisioningResponse response, UnProvisioningRequest request) throws BusinessException {
		try {
			NotificacionCAPRequest requestNotificacion = new NotificacionCAPRequest();
			requestNotificacion.setTipoTransaccion(prop.obtenerPropiedad(Constantes.NOTIFICACION_DESACTIVACION));
			requestNotificacion.setSistema(prop.obtenerPropiedad(Constantes.NOTIFICACION_DESACTIVACION_SISTEMA));
			requestNotificacion.setMsisdn(request.getMsisdn().length() < 12 ? prop.obtenerPropiedad(Constantes.VALOR_57) + request.getMsisdn() : request.getMsisdn());
			requestNotificacion.setImsi(request.getImsi());
			requestNotificacion.setIccid(request.getIccid());
			requestNotificacion.setTmCode(request.getPlanId());
			requestNotificacion.setEstado(prop.obtenerPropiedad(Constantes.NOTIFICACION_DESACTIVACION_ESTADO));
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
			throw new BusinessException(Constantes._102, e);
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion", e);
			throw new BusinessException(Constantes._102, e);
		}	
	}
	
	/**
	 * Brief 31072 - Paquetes y Planes
	 * 
	 * @param planID
	 * @param proveedor
	 * @param empresa
	 * @return
	 */
	private String consultaTmcode(String planID, String proveedor, String empresa) {
		logger.info("Ingresa a consultaTmcode");
		List<PlanInfo> planestmp;
		String tmcode = null;
		LlavePlanes llave = new LlavePlanes(proveedor, empresa);
		if (Memoria.existeOferta(llave)) {
			planestmp = Memoria.getInstance().obtenerPlanes(llave);
			if (planestmp != null) {
				for (PlanInfo planInfo : planestmp) {
					if (planInfo.getPlanId().equalsIgnoreCase(planID)) {
						tmcode = planInfo.getNmtmcode();
					}
				}
			}
		}
		return tmcode;
	}
	
	/**
	 * Brief 31072 - Paquetes y Planes
	 * 
	 * @param request
	 * @param tmcodeAMX
	 */
	private void gestionProvisioningIoTReq(UnProvisioningRequest request, String tmcodeAMX) throws BusinessException {
		GestionPaquetesIOTRequest gestionIoTReq = new GestionPaquetesIOTRequest();
		gestionIoTReq.setMin(request.getMsisdn());
		gestionIoTReq.setImsi(request.getImsi());
		gestionIoTReq.setCoid(Double.parseDouble(request.getCorrelatorId()));
		gestionIoTReq.setTmCodeNew(Integer.valueOf(tmcodeAMX));
		gestionIoTReq.setAccion(Constantes.PAQ_IOT_V_ACCION_DL);

		ServiciosBD.gestionPaQIoT(gestionIoTReq);
	}
}
