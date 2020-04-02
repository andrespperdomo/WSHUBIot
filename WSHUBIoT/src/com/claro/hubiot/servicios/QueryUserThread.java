package com.claro.hubiot.servicios;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import co.com.globalhitss.util.configuracion.Configurador;
import co.com.globalhitss.util.configuracion.ParametrosIniciales;
import co.com.globalhitss.util.configuracion.Propiedades;



import com.claro.cap.servicios.Address;
import com.claro.cap.servicios.BillingAccountInfo;
import com.claro.cap.servicios.Communication;
import com.claro.cap.servicios.MnoSubscriberDetailsInfo;
import com.claro.cap.servicios.NamedParameter;
import com.claro.cap.servicios.NotifyQueryUserResultRequest;
import com.claro.cap.servicios.NotifyUserResultResponse;
import com.claro.cap.servicios.ServicePlanInfo;
import com.claro.cap.servicios.SubscriberDetails;
import com.claro.cap.servicios.TermsAndConditions;
import com.claro.cap.servicios.WSNotificacionHUB;
import com.claro.cap.servicios.WSNotificacionHUBProxy;
import com.claro.hubiot.database.ServiciosBD;
import com.claro.hubiot.dto.QueryUserRequest;
import com.claro.hubiot.dto.QueryUserResponse;
import com.claro.hubiot.dto.QueryUserResponsePL;
import com.claro.hubiot.exceptions.BusinessException;
import com.claro.hubiot.util.Constantes;
import com.claro.hubiot.util.GeneradorResponses;


public class QueryUserThread extends Thread{

	private ParametrosIniciales ini;
	private QueryUserRequest request;
	private QueryUserResponse response;
	private QueryUserResponsePL responsePL;
	private String callback;
	private static Propiedades prop = Propiedades.getInstance();

	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);
	public QueryUserThread (ParametrosIniciales ini, QueryUserRequest request,String callback) {
		this.request = request;
		this.ini = ini;
		this.callback=callback;
	}
	@SuppressWarnings("static-access")
	public void run() {
		try {
			ThreadContext.put("UUID", Long.toString(ini.getUuid()));
			this.sleep(Long.parseLong(prop.obtenerPropiedad(Constantes.SLEEP_THREAD)));
			// consumir el PL
			responsePL = new QueryUserResponsePL();
			response = new QueryUserResponse();
			ServiciosBD.queryUserPro(request.getBillingAccountNumber(), responsePL);
			// Mapear la respuesta
			
			if(responsePL.getCursor().getBillingAccountNumber() == null ||  responsePL.getCursor().getBillingAccountNumber().isEmpty()) {
				
				GeneradorResponses.generarRespuesta(response, Constantes.QUERY_USER_NO_FOUND);
				
			} else	if(!responsePL.getResultCode().toString().equalsIgnoreCase(prop.obtenerPropiedad(Constantes.OK_PL_QUERY_USER)) 
					&& !responsePL.getResultCode().toString().equalsIgnoreCase(prop.obtenerPropiedad(Constantes.OK_PL_QUERY_USER_1))) {
				
				GeneradorResponses.generarRespuesta(response,  Constantes.ERROR_QUERY_USER);
				
			}else if (responsePL.getResultCode().toString().equalsIgnoreCase(prop.obtenerPropiedad(Constantes.OK_PL_QUERY_USER_1))){
				
				GeneradorResponses.generarRespuesta(response, Constantes.QUERY_USER_NO_FOUND);
				
			} else if (responsePL.getResultCode().toString().equalsIgnoreCase(prop.obtenerPropiedad(Constantes.OK_PL_UPDATE_USER))){
				
				GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
			}
			
		} catch (BusinessException e) {
			logger.error("Error de Negocio: en el procesamiento de la solicitud", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_QUERY_USER);
		} catch (Throwable tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_QUERY_USER);
		} finally {
			// consumir el WS
			notifyQueryUser();
			ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
					response.getResultCode()!= null ? response.getResultCode().toString() : "",
							response.getResultMessage(),Propiedades.getInstance().obtenerPropiedad(Constantes.METODO_QUERY_USER));
			logger.info("<< Respuesta de queryUser " + response);
			Configurador.cerrarTransaccion(ini, logger);	
		}
	}

	private void notifyQueryUser() {
		try {

			NamedParameter[] array= null; 
			if (request.getExtensionInfo() != null){
				array = new NamedParameter[request.getExtensionInfo().size()];
				int i = 0;
				for(com.claro.hubiot.dto.NamedParameter named : request.getExtensionInfo()){
					array[i++] = new NamedParameter(named.getLlave(),named.getValor());
				}
			}
			BillingAccountInfo bai = new BillingAccountInfo();
			SubscriberDetails sd = new SubscriberDetails();	
			Communication com = new Communication();
			Address  a = new Address();
			TermsAndConditions tac = new TermsAndConditions();
			MnoSubscriberDetailsInfo msd = new MnoSubscriberDetailsInfo();
			ServicePlanInfo spi = new ServicePlanInfo();
			if(responsePL.getCursor().getBillingAccountNumber() != null) {

				bai.setBillingAccountNumber(request.getBillingAccountNumber());
				bai.setBillingAccountType(responsePL.getCursor().getBillingAccountType());
				bai.setBillingAccountStatus(responsePL.getCursor().getBillingAccountStatus());

				com.setEmailAddress(responsePL.getCursor().getEmailAddress());
				com.setMobilePhone(responsePL.getCursor().getMobilePhone());			
				sd.setCommunication(com);			

				a.setAddressLine1(responsePL.getCursor().getAddressLine1());
				a.setAddressLine2(responsePL.getCursor().getAddressLine2());
				a.setCity(responsePL.getCursor().getCity());
				a.setCountryM2M(responsePL.getCursor().getCountryM2M());
				a.setPostalCode(responsePL.getCursor().getPostalCode());
				a.setState(responsePL.getCursor().getState());
				sd.setAddress(a);
				sd.setFirstName(responsePL.getCursor().getFirstName());
				sd.setTitle(responsePL.getCursor().getTitle());	
				sd.setPreferredLanguageCode(responsePL.getCursor().getPreferredLanguageCode());

				tac.setConsentTimestamp(responsePL.getCursor().getConsentTimestamp());
				tac.setDocumentCode(responsePL.getCursor().getDocumentCode());
				tac.setDocumentLanguage(responsePL.getCursor().getDocumentLanguage());
				tac.setDocumentVersion(responsePL.getCursor().getDocumentVersioivate());
				tac.setValidationIDType(responsePL.getCursor().getValidationIDType());

				msd.setDateOfBirth(responsePL.getCursor().getDateOfBirth());
				msd.setIdType(responsePL.getCursor().getIdType());
				msd.setIdValue(responsePL.getCursor().getIdValue());
				msd.setPlaceOfBirth(responsePL.getCursor().getPlaceOfBirth());

				spi.setPlanId(responsePL.getCursor().getPlanId());
			} else {
				sd.setAddress(a);
				sd.setCommunication(com);	
				logger.info("Cursor con la infomracion del usuario vacio");
			}
			
			String minEnviado = responsePL.getCursor().getMsisdn() == null ? request.getMsisdn() : responsePL.getCursor().getMsisdn();
			minEnviado = minEnviado.length() < 12 ? prop.obtenerPropiedad(Constantes.VALOR_57) + minEnviado : minEnviado;
			WSNotificacionHUB notif = new WSNotificacionHUBProxy(Propiedades.getInstance().obtenerPropiedad(Constantes.END_POINT_WS_NOTIFICACION));
			NotifyQueryUserResultRequest requestWSClient = new NotifyQueryUserResultRequest(
					request.getServiceName(),
					request.getProviderId(),
					responsePL.getCursor().getOperatorUserId() == null ? prop.obtenerPropiedad(Constantes.QUERY_USER_OPERATOR_ID_DEFAULT) : responsePL.getCursor().getOperatorUserId() , 
					request.getCorrelatorId(),
					response.getResultCode().toString(),
					response.getResultMessage(), 
					bai,
					responsePL.getCursor().getIccId() == null ? prop.obtenerPropiedad(Constantes.QUERY_USER_ICCID_DEFAULT) : responsePL.getCursor().getIccId(),
					responsePL.getCursor().getImsi() == null ? prop.obtenerPropiedad(Constantes.QUERY_USER_IMSI_DEFAULT) : responsePL.getCursor().getImsi(),
					minEnviado,					
					request.getCountry(),
					sd,
					tac,
					msd,
					spi,					
					array,
					callback); 
			logger.info("Request NotificacionHUB: \n" + requestWSClient.toString());
			NotifyUserResultResponse respuestaWSClient = notif.notifyQueryUserResult(requestWSClient);
			logger.info("Response NotificacionHUB: \n" + respuestaWSClient.toString());
		} catch (Throwable tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_QUERY_USER);
		} 
	}


}
