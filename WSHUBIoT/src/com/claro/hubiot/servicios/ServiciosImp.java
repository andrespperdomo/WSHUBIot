package com.claro.hubiot.servicios;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeMap;

import com.claro.hubiot.database.ServiciosBD;
import com.claro.hubiot.dto.BuyProductRequest;
import com.claro.hubiot.dto.BuyProductResponse;
import com.claro.hubiot.dto.CancelProductRequest;
import com.claro.hubiot.dto.CancelProductResponse;
import com.claro.hubiot.dto.CancelledProductType;
import com.claro.hubiot.dto.ChangePlanRequest;
import com.claro.hubiot.dto.ChangePlanResponse;
import com.claro.hubiot.dto.ChangeSimRequest;
import com.claro.hubiot.dto.ChangeSimResponse;
import com.claro.hubiot.dto.CreateUserRequest;
import com.claro.hubiot.dto.CreateUserResponse;
import com.claro.hubiot.dto.DeleteUserRequest;
import com.claro.hubiot.dto.DeleteUserResponse;
import com.claro.hubiot.dto.GetDownloadProfileStatusRequest;
import com.claro.hubiot.dto.GetDownloadProfileStatusResponse;
import com.claro.hubiot.dto.GetProductsRequest;
import com.claro.hubiot.dto.GetProductsResponse;
import com.claro.hubiot.dto.LlavePlanes;
import com.claro.hubiot.dto.NamedParameter;
import com.claro.hubiot.dto.PasesType;
import com.claro.hubiot.dto.PlanInfo;
import com.claro.hubiot.dto.ProductType;
import com.claro.hubiot.dto.ProvisioningRequest;
import com.claro.hubiot.dto.ProvisioningResponse;
import com.claro.hubiot.dto.QueryPlanListRequest;
import com.claro.hubiot.dto.QueryPlanListResponse;
import com.claro.hubiot.dto.QueryUserRequest;
import com.claro.hubiot.dto.QueryUserResponse;
import com.claro.hubiot.dto.UnProvisioningRequest;
import com.claro.hubiot.dto.UnProvisioningResponse;
import com.claro.hubiot.dto.UpdateUserRequest;
import com.claro.hubiot.dto.UpdateUserResponse;
import com.claro.hubiot.exceptions.AutenticationException;
import com.claro.hubiot.exceptions.BusinessException;
import com.claro.hubiot.exceptions.InvalidParameterException;
import com.claro.hubiot.exceptions.ParameterEmptyException;
import com.claro.hubiot.exceptions.ParameterException;
import com.claro.hubiot.exceptions.PaseException;
import com.claro.hubiot.exceptions.ProductException;
import com.claro.hubiot.util.Constantes;
import com.claro.hubiot.util.GeneradorResponses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.globalhitss.util.configuracion.Configurador;
import co.com.globalhitss.util.configuracion.ParametrosIniciales;
import co.com.globalhitss.util.configuracion.Propiedades;

/**
 * Clase que implementa los servicios necesarios para ....
 * @author AUTOR
 *
 */
public class ServiciosImp implements IServicios {


	private static Propiedades prop = Propiedades.getInstance();
	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);

	@SuppressWarnings("static-access")
	public QueryPlanListResponse consultaPlanes(String username, String password, QueryPlanListRequest request) {

		ParametrosIniciales ini = Configurador.iniciarTransaccion();
		logger.info(">>Solicitud de consultaPlanes " + request.toString());
		QueryPlanListResponse response = new QueryPlanListResponse();
		List<PlanInfo> planestmp;

		try {
			response.setCorrelatorId(request.getCorrelatorId());
			if (username == null || password == null || !ServiciosBD.autentica(username, password, prop.obtenerPropiedad(Constantes.METODO_QUERYPLANLIST)))
				throw new AutenticationException();

			request.esValido(prop.obtenerPropiedad(Constantes.COUNTRIES_QUERYPLANLIST), prop.obtenerPropiedad(Constantes.SERVICE_NAMES_QUERYPLANLIST));

			response.setCountry(request.getCountry());
			response.setProviderId(request.getProviderId());
			
			LlavePlanes llave = new LlavePlanes(request.getProviderId(), request.getEnterpriseId());
			if (Memoria.existeOferta(llave)) {
				planestmp = Memoria.getInstance().obtenerPlanes(llave);
				if (planestmp != null) {
					response.setPlans(planestmp);
					GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
				} else {
					GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PLAN_LIST);
				}

			} else {
				planestmp = ServiciosBD.consultaPlanes(llave);
				if (planestmp != null && !planestmp.isEmpty()) {
					for (PlanInfo planInfo : planestmp) {
						Memoria.modifcaParametros(planInfo);
					}
					response.setPlans(planestmp);
					GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
					Memoria.getInstance().guardaPlanes(llave, planestmp);
				} else {
					GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PLAN_LIST);
				}
			}
			
			
			List<NamedParameter> parametros = new ArrayList<>();
			
			/*Se extrae el valor de profileType en BD por su relacion con el valor de providerId */
			String profileType = ServiciosBD.consultaProfileType(request.getProviderId());
			
			if(profileType != null) {
				NamedParameter paramNombrePromo = new NamedParameter();
				paramNombrePromo.setLlave(prop.obtenerPropiedad(Constantes.PROFILE_TYPE)); /*profileType*/
				paramNombrePromo.setValor(profileType);
				parametros.add(paramNombrePromo);
			}
			
			if(request.getExtensionInfo() != null) {
				String keyEsType = prop.obtenerPropiedad(Constantes.VALOR_TYPE_ES); /*esType*/
				
				/* Se extrae el valor de la llave esType evaluando su presencia en arreglo de extensionInfo */
				String valueEsType = "";
				boolean containEsType = false;
				
				for (NamedParameter namedParam : request.getExtensionInfo()) {
					if(namedParam.getLlave().equals(keyEsType)) {
						valueEsType = namedParam.getValor();
						containEsType = true;
						break;
					}
				}
				
				if(containEsType) {
					/* Se extrae el valor de offerMessage en BD por su relacion con el valor de esType */
					String offerMessage = ServiciosBD.consultaOfferMessage(valueEsType);
					
					if(offerMessage != null) {
						NamedParameter paramNombreProfileType = new NamedParameter();
						paramNombreProfileType.setLlave(prop.obtenerPropiedad(Constantes.LLAVE_PROMOTION)); /*promotion*/
						paramNombreProfileType.setValor(offerMessage);
						parametros.add(paramNombreProfileType);
					}
					
				}
			}
						
			if(!parametros.isEmpty()) {
				response.setExtensionInfo(parametros);
			}
			
		} catch (ParameterException e) {
			logger.error("Los parametros no son correctos algunos estan nulos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCOMPLETOS);
			Configurador.cerrarTransaccion(ini, logger);
		} catch (ParameterEmptyException e) {
			logger.error("Los parametros no son correctos algunos estan vacios");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_VACIOS);
			Configurador.cerrarTransaccion(ini, logger);
		} catch (InvalidParameterException e) {
			logger.error("enterpriceId, providerId, country o serviceName no son correctos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCORRECTOS);
			Configurador.cerrarTransaccion(ini, logger);
		} catch (AutenticationException ex) {
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_AUTENTICA);
			Configurador.cerrarTransaccion(ini, logger);
		} catch (BusinessException e) {
			logger.error("Bussiness Exception ", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PLAN_LIST);
			Configurador.cerrarTransaccion(ini, logger);
		} catch (Exception tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PLAN_LIST);
		} finally {
			response.setResultMessage(MessageFormat.format(response.getResultMessage(), request.getEnterpriseId(), request.getProviderId()));
			ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
					response.getResultCode(), response.getResultMessage(), "consultaPlanes");
			logger.info("<< Respuesta de consultaPlanes " + response.toString());
			Configurador.cerrarTransaccion(ini, logger);
		}
		return response;
	}

	@Override
	public ProvisioningResponse provisioning(String callback,String username, String password, ProvisioningRequest request) {
		ParametrosIniciales ini = Configurador.iniciarTransaccion();
		logger.info(">>Solicitud de provisioning " + request.toString());
		ProvisioningResponse response = new ProvisioningResponse();
		boolean fallido = true;
		try {
			response.setCorrelatorId(request.getCorrelatorId());
			
			if (username == null || password == null
					|| !ServiciosBD.autentica(username, password, prop.obtenerPropiedad(Constantes.METODO_PROVISIONING)))
				throw new AutenticationException();

			request.esValido(prop.obtenerPropiedad(Constantes.COUNTRIES_PROVISIONING), prop.obtenerPropiedad(Constantes.SERVICE_NAMES_PROVISIONING));
			
			new ProvisioningThread(ini, request,callback).start();
			
			GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
			
			fallido = false;
		} catch (ParameterException e) {
			logger.error("Los parametros no son correctos algunos estan nulos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCOMPLETOS);
		} catch (ParameterEmptyException e) {
			logger.error("Los parametros no son correctos algunos estan vacios");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_VACIOS);
		} catch (InvalidParameterException e) {
			logger.error("enterpriceId, providerId, country o serviceName no son correctos / Revise la longitud de los cambios");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCORRECTOS);
		} catch (AutenticationException ex) {
			logger.error("Usuario o contraseña errados");
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_AUTENTICA);
		} catch (BusinessException e) {
			logger.error("Bussiness Exception ", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PROCEDIMIENTO);
		} catch (Exception tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_INESPERADO);
		} finally {
			logger.info("<< Respuesta de provisioning ACK" + response.toString());	
			if(fallido) {
				ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
						String.valueOf(response.getResultCode()), response.getResultMessage(),
						prop.obtenerPropiedad(Constantes.METODO_PROVISIONING));
				Configurador.cerrarTransaccion(ini, logger);
			}
		}
		return response;
	}

	@Override
	public ChangePlanResponse changePlan(String callback,String username, String password, ChangePlanRequest request) {
		ParametrosIniciales ini = Configurador.iniciarTransaccion();
		logger.info(">>Solicitud de changePlan " + request.toString());
		ChangePlanResponse response = new ChangePlanResponse();
		boolean fallido = true;
		
		try {
			response.setCorrelatorId(request.getCorrelatorId());
			
			if (username == null || password == null
					|| !ServiciosBD.autentica(username, password, prop.obtenerPropiedad(Constantes.METODO_CHANGE_PLAN)))
				throw new AutenticationException();

			request.esValido(prop.obtenerPropiedad(Constantes.COUNTRIES_CHANGEPLAN), prop.obtenerPropiedad(Constantes.SERVICE_NAMES_CHANGEPLAN));
			
			new ChangePlanThread(response, request, ini, callback).start();
			
			GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
			
			fallido = false;
			
		} catch (ParameterException e) {
			logger.error("Los parametros no son correctos algunos estan nulos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCOMPLETOS);
		} catch (ParameterEmptyException e) {
			logger.error("Los parametros no son correctos algunos estan vacios");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_VACIOS);
		} catch (InvalidParameterException e) {
			logger.error("enterpriceId, providerId, country o serviceName no son correctos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCORRECTOS);
		} catch (AutenticationException ex) {
			logger.error("Usuario o contraseña errados");
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_AUTENTICA);
		} catch (BusinessException e) {
			logger.error("Bussiness Exception ", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PROCEDIMIENTO);
		} catch (Exception tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_INESPERADO);
		} finally {
			if(fallido) {
				ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
						String.valueOf(response.getResultCode()), response.getResultMessage(),
						prop.obtenerPropiedad(Constantes.METODO_CHANGE_PLAN));

				Configurador.cerrarTransaccion(ini, logger);
			}
			logger.info("<< Respuesta de changePlan ACK" + response.toString());
		}
		return response;
	}

	@Override
	public GetDownloadProfileStatusResponse getDownloadProfileStatus(String callback,String username, String password,
			GetDownloadProfileStatusRequest request) {
		ParametrosIniciales ini = Configurador.iniciarTransaccion();
		logger.info(">>Consulta estado de la SIMCARD " + request.toString());
		GetDownloadProfileStatusResponse response = new GetDownloadProfileStatusResponse();

		try {
			response.setCorrelatorId(request.getCorrelatorId() == null ? "" : request.getCorrelatorId());
			
			if (!ServiciosBD.autentica(username, password, prop.obtenerPropiedad(Constantes.METODO_PROFILE))) {
				throw new AutenticationException();
			}
			
			request.esValido(prop.obtenerPropiedad(Constantes.COUNTRIES_GETCOWNLOADPROFILESTATUS), prop.obtenerPropiedad(Constantes.SERVICE_NAMES_GETCOWNLOADPROFILESTATUS)
					, prop.obtenerPropiedad(Constantes.PROVIDERID_GETCOWNLOADPROFILESTATUS));

			response.setServiceName(request.getServiceName() == null ? "" : request.getServiceName());
			response.setProviderId(request.getProviderId() == null ? "" : request.getProviderId());
			
			ServiciosBD.consultaIccid(request);
			if (request.getIccid() != null)
				ServiciosBD.getDownLoadProfile(request, response);

			response.setPlanId(Memoria.getInstance().obtenerPlanId(response.getPlanId()));
			response.setMsisdn(response.getMsisdn()== null ? "": response.getMsisdn().length() < 12 ? prop.obtenerPropiedad(Constantes.VALOR_57) + response.getMsisdn() : response.getMsisdn());
			
			if(!prop.obtenerPropiedad(Constantes.PRC_GETCOWNLOADPROFILESTATUS_EXITOSO_24).equalsIgnoreCase(""+response.getResultCode())
					&& !prop.obtenerPropiedad(Constantes.PRC_GETCOWNLOADPROFILESTATUS_EXITOSO).equalsIgnoreCase(""+response.getResultCode())) {
				
				GeneradorResponses.generarRespuesta(response, Constantes.ERROR_GETTING_THE_SUBSCRIBER_DATA);
				
			} else if(prop.obtenerPropiedad(Constantes.PRC_GETCOWNLOADPROFILESTATUS_EXITOSO_24).equalsIgnoreCase(""+response.getResultCode())){
				
				GeneradorResponses.generarRespuesta(response, Constantes.CREATION_FAILED);
				
			} else if (prop.obtenerPropiedad(Constantes.PRC_GETCOWNLOADPROFILESTATUS_EXITOSO).equalsIgnoreCase(""+response.getResultCode())) {
				
				GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
			}
		} catch (ParameterException e) {
			logger.error("Los parametros no son correctos algunos estan nulos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCOMPLETOS);
		} catch (ParameterEmptyException e) {
			logger.error("Los parametros no son correctos algunos estan vacios");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_VACIOS);
		} catch (InvalidParameterException e) {
			logger.error("enterpriceId, providerId, country o serviceName no son correctos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCORRECTOS);
		} catch (AutenticationException ex) {
			logger.error("Usuario o contraseña errados");
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_AUTENTICA);
		} catch (BusinessException e) {
			logger.error("Bussiness Exception ", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PROCEDIMIENTO);
		} catch (Exception e) {
			logger.error("Error desconocido en el sistema", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_INESPERADO);
		} finally {
			ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
					String.valueOf(response.getResultCode()), response.getResultMessage(),
					prop.obtenerPropiedad(Constantes.METODO_PROFILE));
			logger.info("<< Respuesta de consulta estado de la SIMCARD" + response.toString());
			Configurador.cerrarTransaccion(ini, logger);
		}
		return response;

	}

	public UnProvisioningResponse unProvisioning(String callback,String username, String password, UnProvisioningRequest request) {
		ParametrosIniciales ini = Configurador.iniciarTransaccion();
		logger.info(">>Solicitud de unProvisioning " + request.toString());
		UnProvisioningResponse response = new UnProvisioningResponse();
		boolean fallido = true;
		try {
			response.setCorrelatorId(request.getCorrelatorId());
			
			if (username == null || password == null || !ServiciosBD.autentica(username, password,
					prop.obtenerPropiedad(Constantes.METODO_UNPROVISIONING)))
				throw new AutenticationException();

			request.esValido(prop.obtenerPropiedad(Constantes.COUNTRIES_UNPROVISIONING), prop.obtenerPropiedad(Constantes.SERVICE_NAMES_UNPROVISIONING));
			
			new UnProvisioningThread(ini, request, callback).start();
			
			GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);

			fallido = false;
			
		} catch (ParameterException e) {
			logger.error("Los parametros no son correctos algunos estan nulos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCOMPLETOS);
		} catch (ParameterEmptyException e) {
			logger.error("Los parametros no son correctos algunos estan vacios");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_VACIOS);
		} catch (InvalidParameterException e) {
			logger.error("enterpriceId, providerId, country o serviceName no son correctos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCORRECTOS);
		} catch (AutenticationException ex) {
			logger.error("Usuario o contraseña errados");
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_AUTENTICA);
		} catch (BusinessException e) {
			logger.error("Bussiness Exception ", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PROCEDIMIENTO);
		} catch (Exception tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_INESPERADO);
		} finally {
			if(fallido) {
				ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
						String.valueOf(response.getResultCode()), response.getResultMessage(),
						prop.obtenerPropiedad(Constantes.METODO_UNPROVISIONING));
				
				Configurador.cerrarTransaccion(ini, logger);
			}
			logger.info("<< Respuesta de unProvisioning ACK " + response.toString());
		}
		return response;
	}

	/**	<b>Nombre: </b> createUser </br>
	 * <b>Descripción:</b>  Método que permite la creación de un usuario. 
	 * Genera un hilo para la consulta a base de datos y consume un 
	 * Web Service para enviar la respuesta </br>
	 * <b>Fecha Creación:</b> 2/04/2018 </br>
	 * <b>Autor:</b> HITSS  Andrea Daza </br>
	 * <b>Fecha de Última Modificación: </b></br>
	 * <b>Modificado por: </b></br>
	 * <b>Brief: 02072</b></br>
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	@Override
	public CreateUserResponse createUser(String callback,String username, String password, CreateUserRequest request) {
		ParametrosIniciales ini = Configurador.iniciarTransaccion();	
		logger.info(">> Solicitud de createUser " + request);
		CreateUserResponse response = new CreateUserResponse(request.getCorrelatorId(), request.getExtensionInfo());
		boolean fallido=true;
		try {
			response.setCorrelatorId(request.getCorrelatorId());
			// Valida autenticación
			if (username == null || password == null
					|| !ServiciosBD.autentica(username, password, Propiedades.getInstance().obtenerPropiedad(Constantes.METODO_CREATE_USER)))
				throw new AutenticationException();
			// Valida los parámetros
			request.esValido(prop.obtenerPropiedad(Constantes.COUNTRIES_CREATE_USER), prop.obtenerPropiedad(Constantes.SERVICE_NAMES_CREATE_USER)
					, prop.obtenerPropiedad(Constantes.PROVIDERID_CREATE_USER));
			
			// Inicializa el hilo
			new CreateUserThread(ini, request,callback).start();
			// Genera respuesta 
			GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
			fallido=false;
			
		} catch (ParameterException e) {
			logger.error("Los parametros no son correctos algunos estan nulos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCOMPLETOS);
		} catch (ParameterEmptyException e) {
			logger.error("Los parametros no son correctos algunos estan vacios");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_VACIOS);
		} catch (InvalidParameterException e) {
			logger.error("enterpriceId, providerId, country o serviceName no son correctos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCORRECTOS);
		} catch (AutenticationException ex){
			logger.error("Usuario y/o Contraseña inválidos");
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_AUTENTICA);
		} catch (BusinessException e) {
			logger.error("Error de Negocio: en el procesamiento de la solicitud", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PROCEDIMIENTO);
		} catch (Exception tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_INESPERADO);
		} finally {
			if (fallido) {
				ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
						response.getResultCode() != null ? response.getResultCode().toString() : "",
								response.getResultMessage(),Propiedades.getInstance().obtenerPropiedad(Constantes.METODO_CREATE_USER));
				Configurador.cerrarTransaccion(ini, logger);
			}
			logger.info("<< Respuesta de createUser ACK " + response.toString());
		}
		return response;
	}


	@Override
	public UpdateUserResponse updateUser(String callback,String username, String password, UpdateUserRequest request) {

		ParametrosIniciales ini = Configurador.iniciarTransaccion();	
		logger.info(">>Solicitud de updateUser " + request.toString());
		UpdateUserResponse response = new UpdateUserResponse(request.getCorrelatorId(), request.getExtensionInfo());
		boolean fallido=true;
		try {
			response.setCorrelatorId(request.getCorrelatorId());
			
			if(username==null || password==null ||!ServiciosBD.autentica(username,password,prop.obtenerPropiedad(Constantes.METODO_UPDATE_USER)))
				throw new AutenticationException();

			request.esValido(prop.obtenerPropiedad(Constantes.COUNTRIES_UPDATE_USER), prop.obtenerPropiedad(Constantes.SERVICE_NAMES_UPDATE_USER)
					, prop.obtenerPropiedad(Constantes.PROVIDERID_UPDATE_USER));
			
			new ActualizarDatosSuscriptor(request, ini,callback).start();
			
			GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
			fallido = false;
		} catch (ParameterException e) {	
			logger.error("Los parametros no son correctos algunos estan nulos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCOMPLETOS);
		} catch (ParameterEmptyException e) {
			logger.error("Los parametros no son correctos algunos estan vacios");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_VACIOS);
		} catch (InvalidParameterException e) {
			logger.error("enterpriceId, providerId, country o serviceName no son correctos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCORRECTOS);
		} catch (AutenticationException ex){
			logger.error("Usuario y/o Contraseña inválidos");
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_AUTENTICA);
		} catch (BusinessException e) {
			logger.error("Error de Negocio: en el procesamiento de la solicitud", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PROCEDIMIENTO);
		} catch (Exception tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_INESPERADO);
		} finally {
			if (fallido) {
				ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
						response.getResultCode() != null ? response.getResultCode().toString() : "",
								response.getResultMessage(),Propiedades.getInstance().obtenerPropiedad(Constantes.METODO_UPDATE_USER));
				Configurador.cerrarTransaccion(ini, logger);
			}
			logger.info("<< Respuesta de updateUser " + response.toString());
		}
		return response;
	}

	/**	<b>Nombre: </b> deleteUser </br>
	 * <b>Descripción:</b>  Método que permite la eliminacion de un usuario. 
	 * Genera un hilo para la consulta a base de datos y consume un 
	 * Web Service para enviar la respuesta </br>
	 * <b>Fecha Creación:</b> 2/04/2018 </br>
	 * <b>Autor:</b> HITSS  Fabio Orjuela Diaz</br>
	 * <b>Fecha de Última Modificación: </b></br>
	 * <b>Modificado por: </b></br>
	 * <b>Brief: 02072</b></br>
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	@Override
	public DeleteUserResponse deleteUser(String callback,String username, String Password, DeleteUserRequest request) {
		ParametrosIniciales ini = Configurador.iniciarTransaccion();
		logger.info(">>Solicitud de deleteUser " + request.toString());
		DeleteUserResponse response = new DeleteUserResponse(request.getCorrelatorId(), request.getExtensionInfo());
		boolean fallido=true;
		try {
			response.setCorrelatorId(request.getCorrelatorId());
			
			if (username == null || Password == null
					|| !ServiciosBD.autentica(username, Password, prop.obtenerPropiedad(Constantes.METODO_DELETE_USER)))
				throw new AutenticationException();

			request.esValido(prop.obtenerPropiedad(Constantes.COUNTRIES_DELETE_USER), prop.obtenerPropiedad(Constantes.SERVICE_NAMES_DELETE_USER)
					, prop.obtenerPropiedad(Constantes.PROVIDERID_DELETE_USER));
			
			new DeleteUserThread(ini, request,callback).start();
			// Genera respuesta 
			GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
			fallido=false;
		} catch (ParameterException e) {
			logger.error("Los parametros no son correctos algunos estan nulos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCOMPLETOS);
		} catch (ParameterEmptyException e) {
			logger.error("Los parametros no son correctos algunos estan vacios");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_VACIOS);
		} catch (InvalidParameterException e) {
			logger.error("enterpriceId, providerId, country o serviceName no son correctos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCORRECTOS);
		} catch (AutenticationException ex){
			logger.error("Usuario y/o Contraseña inválidos");
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_AUTENTICA);
		} catch (BusinessException e) {
			logger.error("Error de Negocio: en el procesamiento de la solicitud", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PROCEDIMIENTO);
		} catch (Exception tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_INESPERADO);
		} finally {	
			if(fallido) {
				ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
						response.getResultCode() != null ? response.getResultCode().toString() : "",
								response.getResultMessage(),prop.obtenerPropiedad(Constantes.METODO_DELETE_USER));

				Configurador.cerrarTransaccion(ini, logger);
			}
			logger.info("<< Respuesta de deleteUser ACK " + response.toString());
		}
		return response;
	}

	@Override
	public QueryUserResponse queryUser(String callback,String username, String password, QueryUserRequest request) {
		ParametrosIniciales ini = Configurador.iniciarTransaccion();
		logger.info(">> Solicitud de queryUser " + request.toString());
		QueryUserResponse response = new QueryUserResponse(request.getCorrelatorId(), request.getExtensionInfo());
		boolean fallido = true;
		try {		
			response.setCorrelatorId(request.getCorrelatorId());
			
			if (username == null || password == null || !ServiciosBD.autentica(username, password,
					Propiedades.getInstance().obtenerPropiedad(Constantes.METODO_QUERY_USER)))
				throw new AutenticationException();
			
			request.esValido(prop.obtenerPropiedad(Constantes.COUNTRIES_QUERY_USER), prop.obtenerPropiedad(Constantes.SERVICE_NAMES_QUERY_USER)
					, prop.obtenerPropiedad(Constantes.PROVIDERID_QUERY_USER));
			
			new QueryUserThread(ini, request,callback).start();
			
			GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
			
			fallido=false;
			
		} catch (ParameterException e) {
			logger.error("Los parametros no son correctos algunos estan nulos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCOMPLETOS);
		} catch (ParameterEmptyException e) {
			logger.error("Los parametros no son correctos algunos estan vacios");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_VACIOS);
		} catch (InvalidParameterException e) {
			logger.error("enterpriceId, providerId, country o serviceName no son correctos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCORRECTOS);
		} catch (AutenticationException ex){
			logger.error("Usuario y/o Contraseña inválidos");
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_AUTENTICA);
		} catch (BusinessException e) {
			logger.error("Error de Negocio: en el procesamiento de la solicitud", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PROCEDIMIENTO);
		} catch (Exception tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_INESPERADO);
		} finally {	
			if(fallido) {
				ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
						response.getResultCode()!= null ? response.getResultCode().toString() : "",
								response.getResultMessage(),Propiedades.getInstance().obtenerPropiedad(Constantes.METODO_QUERY_USER));
				Configurador.cerrarTransaccion(ini, logger);
			}
			logger.info("<< Respuesta de queryUser ACK " + response);
		}
		return response;
	}



	@Override
	public BuyProductResponse buyProduct(String callback,String username, String password, BuyProductRequest request) {
		ParametrosIniciales ini = Configurador.iniciarTransaccion();
		logger.info(">>Solicitud de buyProduct " + request.toString());
		BuyProductResponse response = new BuyProductResponse();

		TreeMap<String,ProductType> productos=null;
		PasesType infopase=null;
		try{
			response.setCorrelatorId(request.getCorrelatorId());
			
			if (username == null || password == null || !ServiciosBD.autentica( username, password, Propiedades.getInstance().obtenerPropiedad(Constantes.METODO_BUY_PRODUCT ) ) )
				throw new AutenticationException();
			
			request.esValido(prop.obtenerPropiedad(Constantes.COUNTRIES_BUY_PRODUCT), prop.obtenerPropiedad(Constantes.SERVICE_NAMES_BUY_PRODUCT)
					, prop.obtenerPropiedad(Constantes.PROVIDERID_BUY_PRODUCT));

			Calendar calendar = Calendar.getInstance();
			DateFormat sdf = new SimpleDateFormat(prop.obtenerPropiedad(Constantes.FORMATO_FECHA_EFECTIVEDATE));
			sdf.setTimeZone(TimeZone.getTimeZone(prop.obtenerPropiedad(Constantes.ZONA_HORARIA_GMT)));
			Date date = calendar.getTime();
			
			response.setEffectiveDate(sdf.format(date));
			response.setOrderId(request.getOrderId() == null || request.getOrderId().isEmpty()? generarOrderId() : request.getOrderId());
			
			productos = ServiciosBD.consultaProductos(null, request.getProviderId(), request.getMsisdn());
			
			if(!productos.containsKey(request.getProductId()))
				throw new ProductException();
			
			//comcorp respuesta
			ArrayList<String>consultaPase=ServiciosBD.consultaPase(request.getMsisdn(), request.getProductId());
			
			if (!esActivable(consultaPase.get(0))) {
				GeneradorResponses.generarRespuesta(response, Constantes.ERROR_BUY_PRODUCT_COMCORP);
				return response;
			}
			
			infopase=ServiciosBD.activaPaquete(productos.get(request.getProductId()), request.getMsisdn());
			
			if(infopase.getCodigoPL().equalsIgnoreCase(prop.obtenerPropiedad(Constantes.PL_BUY_PRODUCT_2)))
			{
				GeneradorResponses.generarRespuesta(response, Constantes.ERROR_BUY_PRODUCT_INVALID_PRODUCT);
				return response;
			}

			String respuestaPase =ServiciosBD.procesaPase(infopase, esInsertaPase(consultaPase.get(0)));
			
			if(!prop.obtenerPropiedad(Constantes.PASES_EXITOSO).contains("|" + respuestaPase + "|")) {
				throw new PaseException();
			}
			
			GeneradorResponses.generarRespuesta(response,Constantes.RESPUESTA_ACK);

			ServiciosBD.instalaTickler(request.getMsisdn(), request.getProductId(), prop.obtenerPropiedad(Constantes.METODO_BUY_PRODUCT));

			//Lanza hilo para insertar en inh_smo.TBL_CTRL_PASE
			ServiciosGetInternetBalance hilo = new ServiciosGetInternetBalance();
			hilo.setIni(ini);
			hilo.setMetodo(1);
			hilo.setRequest(request);
			hilo.setResponse(response);
			hilo.setInfopase(infopase);
			hilo.setProductos(productos);
			hilo.start();

		}catch (PaseException e){
			logger.error("Error activando el paquete");
			CancelProductResponse cancelacion = new CancelProductResponse();
			try {
				ServiciosBD.cancelProduct(""+infopase.getSecuencia(), cancelacion);
			} catch (BusinessException e1) {
				logger.error("Error cancelando paquete "+e1);
			}
			GeneradorResponses.generarRespuesta( response, Constantes.ERROR_PROCEDIMIENTO );
		}catch (ProductException e) {
			logger.error("No existe el producto en la oferta", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_BUY_PRODUCT_NO_PRODUCTS);
		} catch (ParameterException e) {
			logger.error("Los parametros no son correctos algunos estan nulos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCOMPLETOS);
		} catch (ParameterEmptyException e) {
			logger.error("Los parametros no son correctos algunos estan vacios");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_VACIOS);
		} catch (InvalidParameterException e) {
			logger.error("enterpriceId, providerId, country o serviceName no son correctos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCORRECTOS);
		} catch (AutenticationException ex){
			logger.error("Usuario y/o Contraseña inválidos");
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_AUTENTICA);
		} catch (BusinessException e) {
			logger.error("Error de Negocio: en el procesamiento de la solicitud", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PROCEDIMIENTO);
		} catch (Exception tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_INESPERADO);
		}finally {
			ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
					response.getResultCode()!= null ? response.getResultCode().toString() : "",
							response.getResultMessage(),Propiedades.getInstance().obtenerPropiedad( Constantes.METODO_BUY_PRODUCT ));
			logger.info("<< Respuesta de buyProduct " + response);
			Configurador.cerrarTransaccion(ini, logger);
		}

		return response;	
	}

	@Override
	public ChangeSimResponse changeSim(String callback, String username, String password, ChangeSimRequest request) {
		ChangeSimResponse response = new ChangeSimResponse();
		ParametrosIniciales ini = Configurador.iniciarTransaccion();
		logger.info(">>Solicitud de changeSim " + request.toString());
		boolean fallido = true;
		try{
			response.setCorrelatorId(request.getCorrelatorId());
			
			if (username == null || password == null || !ServiciosBD.autentica( username, password, Propiedades.getInstance().obtenerPropiedad(Constantes.METODO_CHANGE_SIM)))
				throw new AutenticationException(); 
			
			request.esValido(prop.obtenerPropiedad(Constantes.COUNTRIES_CHANGESIM), prop.obtenerPropiedad(Constantes.SERVICE_NAMES_CHANGESIM),  prop.obtenerPropiedad(Constantes.PROVIDERID_CHANGESIM));
			
			new ChangeSimThread(response, request, ini, callback).start();
			
			GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
			
			fallido = false;
			
		} catch (ParameterException e) {
			logger.error("Los parametros no son correctos algunos estan nulos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCOMPLETOS);
		} catch (ParameterEmptyException e) {
			logger.error("Los parametros no son correctos algunos estan vacios");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_VACIOS);
		} catch (InvalidParameterException e) {
			logger.error("enterpriceId, providerId, country o serviceName no son correctos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCORRECTOS);
		} catch (AutenticationException ex) {
			logger.error("Usuario o contraseña errados");
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_AUTENTICA);
		} catch (BusinessException e) {
			logger.error("Bussiness Exception ", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PROCEDIMIENTO);
		} catch (Exception tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_INESPERADO);
		} 
		finally {
			if(fallido) {
				ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
						""+ response.getResultCode(),
						response.getResultMessage(),Propiedades.getInstance().obtenerPropiedad( Constantes.METODO_CHANGE_SIM ));
				Configurador.cerrarTransaccion(ini, logger);
			}
			logger.info("<< Respuesta de changeSim ACK " + response.toString());
		}
		return response;
	}

	@Override
	public GetProductsResponse getProduct(String callback, String username, String password,
			GetProductsRequest request) {
		ParametrosIniciales ini = Configurador.iniciarTransaccion();
		logger.info(">>Solicitud de obtener producto " + request.toString());
		GetProductsResponse response = new GetProductsResponse();
		TreeMap<String,ProductType> productos=null;
		try {
			response.setCorrelatorId(request.getCorrelatorId());
			
			if (username == null || password == null || !ServiciosBD.autentica(username, password,
					Propiedades.getInstance().obtenerPropiedad(Constantes.METODO_GET_PRODUCT))) {
				throw new AutenticationException();
			} 
			
			request.esValido(prop.obtenerPropiedad(Constantes.COUNTRIES_GET_PRODUCT), prop.obtenerPropiedad(Constantes.SERVICE_NAMES_GET_PRODUCT)
					, prop.obtenerPropiedad(Constantes.PROVIDERID_GET_PRODUCT), prop.obtenerPropiedad(Constantes.ENTERPRISEID_GET_PRODUCT));
			
			productos =ServiciosBD.consultaProductos(request.getEnterpriseId(), request.getProviderId(), request.getMsisdn());
			
			if(productos.isEmpty()) {
				GeneradorResponses.generarRespuesta(response, Constantes.NO_PRODUCTS_AVAILABLE);
				return response;
			}
			
			response.setResultCode(prop.obtenerPropiedad(Constantes.RESPONSE_CHANGE_SIM_OK));
			response.setProduct(new ArrayList<ProductType> (productos.values()));
			
			response.setMainBalance(Double.parseDouble(prop.obtenerPropiedad(Constantes.MAIN_BALANCE_GET_PRODUCT)));
			response.setSubscriberType(prop.obtenerPropiedad(Constantes.SUBSCRIBERTYPE_GET_PRODUCT));
			
			if(prop.obtenerPropiedad(Constantes.OK_PL_GET_PRODUCT).equalsIgnoreCase(""+ response.getResultCode())) {
				GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
			} else {
				GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ERROR_GET_PRODUCT);
			}
			
		} catch (ParameterException e) {
			logger.error("Los parametros no son correctos algunos estan nulos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCOMPLETOS);
		} catch (ParameterEmptyException e) {
			logger.error("Los parametros no son correctos algunos estan vacios");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_VACIOS);
		} catch (InvalidParameterException e) {
			logger.error("enterpriceId, providerId, country o serviceName no son correctos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCORRECTOS);
		} catch (AutenticationException ex) {
			logger.error("Usuario o contraseña errados");
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_AUTENTICA);
		} catch (BusinessException e) {
			logger.error("Bussiness Exception ", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PROCEDIMIENTO);
		} catch (Exception tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_INESPERADO);
		} finally {
			ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
					response.getResultCode() != null ? response.getResultCode().toString() : "",
							response.getResultMessage(),
							Propiedades.getInstance().obtenerPropiedad(Constantes.METODO_GET_PRODUCT));
			logger.info("<< Respuesta de getProduct " + response);
			Configurador.cerrarTransaccion(ini, logger);
		}

		return response;
	}

	@Override
	public CancelProductResponse cancelProduct(String callback, String username, String password,
			CancelProductRequest request) {
		ParametrosIniciales ini = Configurador.iniciarTransaccion();
		logger.info(">>Solicitud de cancelar producto " + request.toString());
		CancelProductResponse response = new CancelProductResponse();
		List<CancelledProductType> cancelProductList = new ArrayList<CancelledProductType>();
		
		try {
			response.setCorrelatorId(request.getCorrelatorId());
			
			if (username == null || password == null || !ServiciosBD.autentica(username, password,
					Propiedades.getInstance().obtenerPropiedad(Constantes.METODO_CANCEL_PRODUCT))) {
				throw new AutenticationException();
			} 
			
			request.esValido(prop.obtenerPropiedad(Constantes.COUNTRIES_CANCEL_PRODUCT), prop.obtenerPropiedad(Constantes.SERVICE_NAMES_CANCEL_PRODUCT)
					, prop.obtenerPropiedad(Constantes.PROVIDERID_CANCEL_PRODUCT));
			
			response.setIccid(request.getIccid() == null ? "":request.getIccid());
			response.setImsi(request.getImsi() == null ? "":request.getImsi());
			response.setMsisdn(request.getMsisdn() == null ? "": request.getMsisdn().length() < 12 ? prop.obtenerPropiedad(Constantes.VALOR_57) + request.getMsisdn() : request.getMsisdn());
			response.setProviderId(request.getProviderId() == null ? "":request.getProviderId());
			response.setCountry(request.getCountry() == null ? "":request.getCountry());
			response.setCancelProductList(cancelProductList);
			response.setOrderId(request.getOrderId() == null || request.getOrderId().isEmpty()? generarOrderId() : request.getOrderId());
			
			String mincorto = request.getMsisdn().length() > 10 ? request.getMsisdn().substring(request.getMsisdn().length() - 10) : request.getMsisdn();
			
			ArrayList<String>consultaPase=ServiciosBD.consultaPase(mincorto, request.getProductId());

			if (consultaPase.get(1)==null || consultaPase.get(1).isEmpty()) { 
				GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PROCEDIMIENTO);
				return response;
			}

			ServiciosBD.cancelProduct(consultaPase.get(1), response);
			response.setCountry(prop.obtenerPropiedad(Constantes.VALOR_COUNTRY));

			Calendar calendar = Calendar.getInstance();
			DateFormat sdf = new SimpleDateFormat(prop.obtenerPropiedad(Constantes.FORMATO_FECHA_EFECTIVEDATE));
			sdf.setTimeZone(TimeZone.getTimeZone(prop.obtenerPropiedad(Constantes.ZONA_HORARIA_GMT)));
			Date date = calendar.getTime();
			CancelledProductType producto = new CancelledProductType();
			
			producto.setEffectiveDate(sdf.format(date));
			producto.setProductId(request.getProductId());
			producto.setCurrency(prop.obtenerPropiedad(Constantes.CURRENCY_CANCEL_PRODUCT));
			producto.setRefundAmount(prop.obtenerPropiedad(Constantes.REFUNDAMOUNT_CANCEL_PRODUCT));
			cancelProductList.add(producto);			
			
			response.setCancelProductList(cancelProductList);
			
			if(response.getResultCode().equals(prop.obtenerPropiedad(Constantes.PRC_DESAPROVISIONA_EXITOSO))) {
				ServiciosBD.instalaTickler(mincorto, request.getProductId(), prop.obtenerPropiedad(Constantes.METODO_CANCEL_PRODUCT));
			} else {
				GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PROCEDIMIENTO);
				return response;				
			}

			//Lanza hilo para insertar en inh_smo.TBL_CTRL_PASE
			if(response.getResultCode().equals(prop.obtenerPropiedad(Constantes.PRC_DESAPROVISIONA_EXITOSO))) {
				ServiciosGetInternetBalance hilo = new ServiciosGetInternetBalance();
				hilo.setIni(ini);
				hilo.setMetodo(2);
				request.setMsisdn(mincorto);
				hilo.setRequestCan(request);
				hilo.setResponCan(response);
				hilo.start();

				//hilo actualiza comcorp
				ActualizaPaseThread hiloComcorp = new ActualizaPaseThread(mincorto);
				hiloComcorp.start();
			}
			
			GeneradorResponses.generarRespuesta(response, Constantes.RESPUESTA_ACK);
			
		} catch (ParameterException e) {
			logger.error("Los parametros no son correctos algunos estan nulos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCOMPLETOS);
		} catch (ParameterEmptyException e) {
			logger.error("Los parametros no son correctos algunos estan vacios");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_VACIOS);
		} catch (InvalidParameterException e) {
			logger.error("enterpriceId, providerId, country o serviceName no son correctos");
			GeneradorResponses.generarRespuesta(response, Constantes.PARAMETROS_INCORRECTOS);
		} catch (AutenticationException ex) {
			logger.error("Usuario o contraseña errados");
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_AUTENTICA);
		} catch (BusinessException e) {
			logger.error("Bussiness Exception ", e);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_PROCEDIMIENTO);
		} catch (Exception tr) {
			logger.error("Error desconocido en el sistema", tr);
			GeneradorResponses.generarRespuesta(response, Constantes.ERROR_INESPERADO);
		}finally {	
			ServiciosBD.generarTransaccion(request.toString(), response.toString(), ini.getUuid().toString(),
					response.getResultCode() != null ? response.getResultCode().toString() : "",
							response.getResultMessage(),
							Propiedades.getInstance().obtenerPropiedad(Constantes.METODO_CANCEL_PRODUCT));
			Configurador.cerrarTransaccion(ini, logger);
			logger.info("<< Respuesta de cancelProduct ACK" + response);
		}
		return response;
	}
	private String generarOrderId() {
		String respuesta = prop.obtenerPropiedad(Constantes.VALOR_COUNTRY);
		DateFormat sdf = new SimpleDateFormat(Propiedades.getInstance().obtenerPropiedad( Constantes.FORMATO_FECHA_ORDERID));
		sdf.setTimeZone(TimeZone.getTimeZone(prop.obtenerPropiedad(Constantes.ZONA_HORARIA_ORDER_ID)));
		Date cal = new Date();
		return respuesta + sdf.format(cal);
	}

	
	private boolean esActivable(String respuestaPase)	{	
		logger.debug("ingresa a validar si se debe ejecutar la activacion con "+ respuestaPase);		
		String paseConsulta=Constantes.PIPE+respuestaPase+Constantes.PIPE;	
		String pasesValidos=prop.obtenerPropiedad(Constantes.PASES_ACTIVAR);		
		return pasesValidos.contains(paseConsulta);
	}	
	private boolean esInsertaPase(String respuestaPase) {		
		logger.debug("ingresa a validar si se debe actualizar el pase con "+ respuestaPase)	;	
		String paseConsulta=Constantes.PIPE+respuestaPase+Constantes.PIPE;
		String pasesValidos=prop.obtenerPropiedad(Constantes.PASES_INSERTAR);		
		return pasesValidos.contains(paseConsulta);	
	}
}