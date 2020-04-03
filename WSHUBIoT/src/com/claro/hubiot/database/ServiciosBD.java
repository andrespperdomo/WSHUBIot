package com.claro.hubiot.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.claro.hubiot.dto.CancelProductResponse;
import com.claro.hubiot.dto.ChangePlanRequest;
import com.claro.hubiot.dto.ChangePlanResponse;
import com.claro.hubiot.dto.ChangeSimRequest;
import com.claro.hubiot.dto.ChangeSimResponse;
import com.claro.hubiot.dto.ConsultaMaestra;
import com.claro.hubiot.dto.CreateUserRequest;
import com.claro.hubiot.dto.CreateUserResponsePL;
import com.claro.hubiot.dto.CursorCreateUser;
import com.claro.hubiot.dto.CursorQueryUser;
import com.claro.hubiot.dto.DeleteUserRequest;
import com.claro.hubiot.dto.DeleteUserResponse;
import com.claro.hubiot.dto.GestionPaquetesIOTRequest;
import com.claro.hubiot.dto.GetDownloadProfileStatusRequest;
import com.claro.hubiot.dto.GetDownloadProfileStatusResponse;
import com.claro.hubiot.dto.LlavePlanes;
import com.claro.hubiot.dto.Mensaje;
import com.claro.hubiot.dto.Parametro;
import com.claro.hubiot.dto.PasesType;
import com.claro.hubiot.dto.PlanInfo;
import com.claro.hubiot.dto.ProductType;
import com.claro.hubiot.dto.ProvisioningRequest;
import com.claro.hubiot.dto.ProvisioningResponse;
import com.claro.hubiot.dto.QueryUserResponsePL;
import com.claro.hubiot.dto.UnProvisioningRequest;
import com.claro.hubiot.dto.UnProvisioningResponse;
import com.claro.hubiot.dto.UpdateUserRequest;
import com.claro.hubiot.exceptions.BusinessException;
import com.claro.hubiot.exceptions.PaseException;
import com.claro.hubiot.util.Constantes;
import com.claro.hubiot.util.Utils;

import co.com.globalhitss.util.configuracion.Propiedades;
import co.com.globalhitss.util.database.Conexion;
import oracle.jdbc.OracleTypes;

/**
 * Clase encargada de la ejecucion de los llamados a consultas, funciones y
 * procedimientos de base de datos.
 * 
 * @author AUTOR
 * 
 */
public class ServiciosBD {

	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);
	private static Propiedades prop = Propiedades.getInstance();

	public static boolean autentica(String usu, String pass, String metodo) throws BusinessException {

		ResultSet resultSet=null;
		Connection con = null;
		CallableStatement cs = null;
		boolean autentica=false;
		String sql = prop.obtenerPropiedad(Constantes.PRC_AUTENTICA);
		try {
			logger.debug("Ingresa a autenticar usuario");
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);

			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_SMSSERV), 
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_SMSSERV));
			cs = con.prepareCall(sql);
			cs.setString(1,usu);
			cs.setString(2,Utils.getEncryptedPass(pass));
			cs.setString(3,Constantes.APLICACION);
			cs.setString(4,metodo);
			cs.registerOutParameter(5, OracleTypes.NUMBER);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_AUT)));
			cs.execute();
			logger.info("Respuesta de llamado a procedimiento almacenado " + cs.getInt(5) + " - " + cs.getString(6));
			if(cs.getString(5).equalsIgnoreCase(prop.obtenerPropiedad(Constantes.AUTENTICA_RESP_EXITOSA))){
				autentica = true;
			}
		} catch (SQLException e) {
			logger.error("Ocurrio una excepcion en autentica");
			throw new BusinessException(Constantes._101, e);
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en autentica", e);
			throw new BusinessException(Constantes._102, e);
		} finally {
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
			Conexion.cerrar(resultSet);
		}
		logger.debug("Termina autenticacion");
		return autentica;

	}
	public static ArrayList<PlanInfo> consultaPlanes(LlavePlanes llave) {
		logger.info("Inicia consulta planes");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.CONSULTA_PLANES);		

		ArrayList<PlanInfo> planes = new ArrayList<>();
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));

			logger.info("Parametros de entrada a consultaPlanes: ");
			cs = con.prepareCall(sql);
			if(llave!= null){
				cs.setString(1, llave.getProviderId());
				cs.setString(2, llave.getEnterpriceId());
				logger.info("PROVIDER_ID   :" + llave.getProviderId());
				logger.info("ENTERPRISE_ID :" + llave.getEnterpriceId());
			}else{
				cs.setNull(1, Types.VARCHAR);
				cs.setNull(2, Types.VARCHAR);
				logger.info("PROVIDER_ID   :null");
				logger.info("ENTERPRISE_ID :null");
			}
			cs.registerOutParameter(3, OracleTypes.CURSOR);			
			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PLANES)));
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de consultaPlanes" + timeFin + " ms");
			rs = (ResultSet) cs.getObject(3);
			if(rs!=null){
				while (rs.next()) {
					PlanInfo plan = new PlanInfo();
					plan.setEnterpriceId(rs.getString(prop.obtenerPropiedad(Constantes.NOMBRE_COL_ENTERPRICE)));
					plan.setProviderId(rs.getString(prop.obtenerPropiedad(Constantes.NOMBRE_COL_PROVIDER)));
					plan.setPlanId(rs.getString(prop.obtenerPropiedad(Constantes.NOMBRE_COL_PLAN)));
					plan.setMinutesQuantity(rs.getString(prop.obtenerPropiedad(Constantes.NOMBRE_COL_MIN)));
					plan.setSmsQuantity(rs.getString(prop.obtenerPropiedad(Constantes.NOMBRE_COL_SMS)));
					plan.setMbQuantity(rs.getString(prop.obtenerPropiedad(Constantes.NOMBRE_COL_MB)));
					plan.setPrice(rs.getDouble(prop.obtenerPropiedad(Constantes.NOMBRE_COL_PRICE)));
					plan.setProductType(rs.getString(prop.obtenerPropiedad(Constantes.NOMBRE_COL_PRODUCT)));
					plan.setCurrency(prop.obtenerPropiedad(Constantes.NOMBRE_COL_CURRENCY));
					plan.setNmtmcode(rs.getString(prop.obtenerPropiedad(Constantes.NOMBRE_COL_NMTMCODE)));
					planes.add(plan);
					logger.debug("Plan consultado "+plan.toString());
				}
			}
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en consultaPlanes: ", e);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return planes;
	}

	public static ArrayList<Parametro> consultaParametros() {
		logger.info("Inicia consulta parametros");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.CONSULTA_PARAMETROS);		

		ArrayList<Parametro> parametros = new ArrayList<>();
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_SMSSERV),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_SMSSERV));

			cs = con.prepareCall(sql);
			cs.registerOutParameter(1, OracleTypes.CURSOR);			
			cs.registerOutParameter(2, OracleTypes.INTEGER);
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PARAM)));
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de consultaParametros" + timeFin + " ms");
			rs = (ResultSet) cs.getObject(1);
			while (rs.next()) {
				Parametro parametro = new Parametro();
				parametro.setParametroPl(rs.getString("NOMBRE_PARAM_PL"));
				parametro.setValorParametro(rs.getString("VALOR_PARAM_PL"));
				parametro.setValorFinal(rs.getString("VALOR_PARAM_RESP"));
				parametros.add(parametro);
				logger.debug("Parametro consultado "+parametro.toString());
			}

		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en consultaParametros: ", e);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return parametros;
	}

	public static ArrayList<Mensaje> consultaMensajes() {
		logger.info("Inicia consulta mensajes");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.CONSULTA_MENSAJES);		

		ArrayList<Mensaje> mensajes = new ArrayList<>();
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_SMSSERV),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_SMSSERV));

			cs = con.prepareCall(sql);
			cs.registerOutParameter(1, OracleTypes.CURSOR);			
			cs.registerOutParameter(2, OracleTypes.INTEGER);
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_MSJ)));
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento en consultaMensajes " + timeFin + " ms");
			rs = (ResultSet) cs.getObject(1);
			while (rs.next()) {
				Mensaje mensaje = new Mensaje();
				mensaje.setNombrePl(rs.getString("NOMBRE_PL"));
				mensaje.setValorPl(rs.getString("COD_PL"));
				mensaje.setCodFinal(rs.getString("COD_RESPUESTA"));
				mensaje.setDescFinal(rs.getString("DESC_RESPUESTA"));
				mensajes.add(mensaje);
				logger.debug("Mensaje consultado "+mensaje.toString());
			}

		} catch (Exception e) {
			logger.error("Ocurrio una excepcion consultaMensajes: ", e);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return mensajes;
	}

	public static void generarTransaccion(String requestString, String responseString, String uuid, String codResp, String descResp, String metodo ) {
		logger.info("Inicia generarTransaccion");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.INSERTAR_TRANSACCION);
		Integer cod = null;
		String mensaje = null;
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_SMSSERV),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_SMSSERV));

			cs = con.prepareCall(sql);
			cs.setString(1, requestString);
			cs.setString(2, responseString);
			cs.setString(3, uuid);
			cs.setString(4, codResp);
			cs.setString(5, descResp);
			cs.setString(6, metodo);
			cs.registerOutParameter(7, OracleTypes.INTEGER);
			cs.registerOutParameter(8, OracleTypes.VARCHAR);
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de generarTransaccion " + timeFin + " ms");
			cod = cs.getInt(7);
			mensaje = cs.getString(8);
			logger.info("Respuesta de PRC_INSERTAR_TRANSACCION " + cod + " - " + mensaje);
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en generarTransaccion: ", e);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
	}

	public static void insertaRegistro(String iccdid, String msisdn,String correlator,String proveedor,String empresa,String callbabk, Long uuid, String metodo ) {
		logger.info("Inicia insertar Registro");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.INSERTAR_REGISTRO);
		Integer cod = null;
		String mensaje = null;
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_SMSSERV),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_SMSSERV));

			logger.info("Paramentros de entrada a insertaRegistro : " + "ICCID: " + iccdid + "|MSISDN: " + msisdn + "|CORRELATOR: " + correlator + "|PROVEEDOR: " + proveedor
					+ "|EMPRESA: " + empresa + "|CALLBACK: " + callbabk + "|UUID: " + uuid + "|METODO: " +metodo);
			cs = con.prepareCall(sql);
			cs.setString(1, iccdid);
			cs.setString(2, msisdn);
			cs.setString(3, correlator);
			cs.setString(4, proveedor);
			cs.setString(5, empresa);
			cs.setString(6, callbabk);
			cs.setString(7, ""+uuid);
			cs.setString(8, metodo);
			cs.registerOutParameter(9, OracleTypes.INTEGER);
			cs.registerOutParameter(10, OracleTypes.VARCHAR);
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de insertaRegistro" + timeFin + " ms");
			cod = cs.getInt(9);
			mensaje = cs.getString(10);
			logger.info("Respuesta de PRC_INSERT_REGISTRO " + cod + " - " + mensaje);
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en insertaRegistro: ", e);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
	}


	public static void cambioPlan(ChangePlanRequest request, ChangePlanResponse response) throws BusinessException {

		logger.info("Inicia cambio plan con tmcode: "+request.getNMtmcode());
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.CAMBIO_PLAN);
		Integer cod = null;
		String mensaje = null;

		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));

			cs = con.prepareCall(sql);
			cs.setString(1, request.getProviderId());
			cs.setString(2, request.getEnterpriseId());
			cs.setString(3, request.getMsisdn());
			cs.setString(4, request.getNMtmcode());
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_CAMBIO)));
			logger.info("Parametros de entrada a cambioPlan : PROVIDER_ID: " + request.getProviderId() + "|ENTERPRISEID: " + request.getEnterpriseId() + "|MSISDN: " + request.getMsisdn() + 
					"|TMCODE: " + request.getNMtmcode());
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de cambioPlan " + timeFin + " ms");
			cod = cs.getInt(5);
			mensaje = cs.getString(6);

			response.setResultCode(cod);
			response.setResultMessage(mensaje);

			logger.info("Respuesta de PRC_CAMBIO_PLAN_IOT " + cod + " - " + mensaje);
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en cambioPlan", e);
			throw new BusinessException(Constantes._102, e);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
	}

	public static void getDownLoadProfile(GetDownloadProfileStatusRequest request, GetDownloadProfileStatusResponse response) throws BusinessException{
		logger.info("Inicia consulta perfil de activacion "+request.getIccid());
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.CONSULTA_PERFIL);

		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));

			cs = con.prepareCall(sql);
			cs.setString(1, request.getIccid());
			cs.registerOutParameter(2, OracleTypes.NUMBER);
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.registerOutParameter(4, OracleTypes.NUMBER);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);

			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PERFIL)));
			logger.info("Parametros de entrada a getDownLoadProfile: ICCID: " + request.getIccid());
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de getDownLoadProfile" + timeFin + " ms");

			response.setCorrelatorId(request.getCorrelatorId());
			response.setServiceName(request.getServiceName());
			response.setProviderId(request.getProviderId());
			response.setIccid(request.getIccid());
			response.setMsisdn( cs.getString(2)==null?"":cs.getString(2) );
			response.setImsi( cs.getString(3)==null?"":cs.getString(3) );
			response.setSimStatus( ( cs.getString(5)==null?"": (cs.getString(5)!=null?
					(cs.getString(5).equalsIgnoreCase(prop.obtenerPropiedad(Constantes.VC_APROVISIONADO_EXITOSO))?prop.obtenerPropiedad(Constantes.ESTADO_ACTIVO):prop.obtenerPropiedad(Constantes.ESTADO_PENDIENTE) ):"") ));
			response.setPlanId( String.valueOf(cs.getInt(4) ) );
			response.setResultCode(cs.getString(6));
			response.setResultMessage(cs.getString(7));
			logger.info("Respuesta de PRC_CONSULTA_PERFIL " + cs.getString(6) + " - " + cs.getString(7)+"-"+cs.getString(5));
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en getDownLoadProfile", e);
			throw new BusinessException(Constantes._102, e);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
	}

	public static void unProvisioning(UnProvisioningRequest request, UnProvisioningResponse response) 
			throws BusinessException {
		logger.info("Inicia UnProvisioning");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.CONSULTA_UNPROVISIONING);
		Integer cod = null;
		String mensaje = null;

		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));

			cs = con.prepareCall(sql);
			cs.setString(1, request.getIccid());
			cs.registerOutParameter(2, OracleTypes.VARCHAR);
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_UNPRO)));
			logger.info("Parametros de entrada a unProvisioning: ICCID: " + request.getIccid());
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de unProvisioning" + timeFin + " ms");
			cod = cs.getInt(2);
			mensaje = cs.getString(3);

			response.setResultCode(cod);
			response.setResultMessage(mensaje);

			logger.info("Respuesta de PRC_DESASIGNAR_RECURSOS " + cod + " - " + mensaje);
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en unProvisioning", e);
			throw new BusinessException(Constantes._102, e);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
	}

	public static String updateUser(UpdateUserRequest request) throws BusinessException{
		logger.info("Inicia actualizacion de datos de suscriptor");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.CONSULTA_ACTUALIZAR_DATOS_USUARIO);
		String cod = null;
		String mensaje = null;

		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_COMCORP),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_COMCORP));

			cs = con.prepareCall(sql);
			cs.setString(1, request.getBillingAccountNumber());
			cs.setString(2, request.getProviderId());
			cs.setString(3, prop.obtenerPropiedad(Constantes.APP_ID_EMPRESA));
			cs.setString(4, request.getOperatorUserId());
			cs.setString(5, request.getCorrelatorId());
			cs.setString(6,  request.getCountry());
			cs.setString(7, request.getIccid());
			cs.setString(8, request.getImsi());
			cs.setString(9,  request.getMsisdn());
			if(request.getSubscriberDetails()!=null) {
				cs.setString(10, request.getSubscriberDetails().getTitle()!=null?request.getSubscriberDetails().getTitle():"");
				cs.setString(11, request.getSubscriberDetails().getFirstName() != null? request.getSubscriberDetails().getFirstName():"");
				cs.setString(12, request.getSubscriberDetails().getLastName() != null? request.getSubscriberDetails().getLastName():"");
				if(request.getSubscriberDetails().getAddress()!=null){
					cs.setString(13, request.getSubscriberDetails().getAddress().getAddressLine1() != null?request.getSubscriberDetails().getAddress().getAddressLine1():"");
					cs.setString(14, request.getSubscriberDetails().getAddress().getAddressLine2() != null? request.getSubscriberDetails().getAddress().getAddressLine2():"");
					cs.setString(15, request.getSubscriberDetails().getAddress().getCity() != null? request.getSubscriberDetails().getAddress().getCity():"");
					cs.setString(16, request.getSubscriberDetails().getAddress().getState() != null? request.getSubscriberDetails().getAddress().getState():"");
					cs.setString(17, request.getSubscriberDetails().getAddress().getPostalCode() != null? request.getSubscriberDetails().getAddress().getPostalCode():"");
					cs.setString(18, request.getSubscriberDetails().getAddress().getCountryM2M() != null? request.getSubscriberDetails().getAddress().getCountryM2M():"");
				}else{
					cs.setNull(13, OracleTypes.VARCHAR);
					cs.setNull(14, OracleTypes.VARCHAR);
					cs.setNull(15, OracleTypes.VARCHAR);
					cs.setNull(16, OracleTypes.VARCHAR);
					cs.setNull(17, OracleTypes.VARCHAR);
					cs.setNull(18, OracleTypes.VARCHAR);
				}

				if(request.getSubscriberDetails().getCommunication()!=null){
					cs.setString(19, request.getSubscriberDetails().getCommunication().getEmailAddress() != null? request.getSubscriberDetails().getCommunication().getEmailAddress():"");
					cs.setString(20, request.getSubscriberDetails().getCommunication().getMobilePhone() != null? request.getSubscriberDetails().getCommunication().getMobilePhone():"");
				}else{
					cs.setNull(19, OracleTypes.VARCHAR);
					cs.setNull(20, OracleTypes.VARCHAR);
				}
				cs.setString(21, request.getSubscriberDetails().getPreferredLanguageCode() != null? request.getSubscriberDetails().getPreferredLanguageCode():"");
			}else{
				cs.setNull(10, OracleTypes.VARCHAR);
				cs.setNull(11, OracleTypes.VARCHAR);
				cs.setNull(12, OracleTypes.VARCHAR);
				cs.setNull(13, OracleTypes.VARCHAR);
				cs.setNull(14, OracleTypes.VARCHAR);
				cs.setNull(15, OracleTypes.VARCHAR);
				cs.setNull(16, OracleTypes.VARCHAR);
				cs.setNull(17, OracleTypes.VARCHAR);
				cs.setNull(18, OracleTypes.VARCHAR);
				cs.setNull(19, OracleTypes.VARCHAR);
				cs.setNull(20, OracleTypes.VARCHAR);
				cs.setNull(21, OracleTypes.VARCHAR);
			}
			if(request.getTermsAndConditions()!=null){
				cs.setString(22, request.getTermsAndConditions().getConsent() != null? request.getTermsAndConditions().getConsent()?prop.obtenerPropiedad(Constantes.APP_CONSTANTE_VERDADERO):
					prop.obtenerPropiedad(Constantes.APP_CONSTANTE_FALSO):"");
				cs.setString(23, request.getTermsAndConditions().getConsentTimestamp() != null? request.getTermsAndConditions().getConsentTimestamp():"");
				cs.setString(24, request.getTermsAndConditions().getDocumentCode() != null? request.getTermsAndConditions().getDocumentCode():"");
				cs.setString(25, request.getTermsAndConditions().getDocumentVersion() != null? request.getTermsAndConditions().getDocumentVersion():"");
				cs.setString(26, request.getTermsAndConditions().getDocumentLanguage() != null? request.getTermsAndConditions().getDocumentLanguage():"");
				cs.setString(27, request.getTermsAndConditions().getValidationIDType() != null? request.getTermsAndConditions().getValidationIDType():"");
			}else{
				cs.setNull(22, OracleTypes.VARCHAR);
				cs.setNull(23, OracleTypes.VARCHAR);
				cs.setNull(24, OracleTypes.VARCHAR);
				cs.setNull(25, OracleTypes.VARCHAR);
				cs.setNull(26, OracleTypes.VARCHAR);
				cs.setNull(27, OracleTypes.VARCHAR);
			}

			if (request.getMnoSubscriberDetails()!=null){
				cs.setString(28, request.getMnoSubscriberDetails().getDateOfBirth() != null? request.getMnoSubscriberDetails().getDateOfBirth():"");
				cs.setString(29, request.getMnoSubscriberDetails().getPlaceOfBirth() != null? request.getMnoSubscriberDetails().getPlaceOfBirth():"");
				cs.setString(30, request.getMnoSubscriberDetails().getIdType() != null? request.getMnoSubscriberDetails().getIdType():"");
				cs.setString(31, request.getMnoSubscriberDetails().getIdValue() != null? request.getMnoSubscriberDetails().getIdValue():"");
			}else{
				cs.setNull(28, OracleTypes.VARCHAR);
				cs.setNull(29, OracleTypes.VARCHAR);
				cs.setNull(30, OracleTypes.VARCHAR);
				cs.setNull(31, OracleTypes.VARCHAR);
			}
			if(request.getServicePlan()!=null){
				cs.setString(32, request.getServicePlan().getPlanId() != null? request.getServicePlan().getPlanId():"");
			}else{
				cs.setNull(32, OracleTypes.VARCHAR);
			}
			cs.registerOutParameter(33, OracleTypes.CURSOR);
			cs.registerOutParameter(34, OracleTypes.VARCHAR);
			cs.registerOutParameter(35, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PERFIL)));
			logger.info("Parametros de entrada a updateUser: " + request.toString());
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de updateUser" + timeFin + " ms");

			cod = cs.getString(34);
			mensaje = cs.getString(35);
			logger.info("Respuesta de GM_ACTUALIZA_SUSCRIPTOR " + cod + " - " + mensaje);
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en updateUser", e);
			throw new BusinessException(Constantes._102, e);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return cod;

	}

	public static void provisioning(ProvisioningRequest request, ProvisioningResponse response) throws BusinessException {
		logger.info("Inicia Provisioning");
		Connection con = null;
		CallableStatement cs = null;
		String sql = prop.obtenerPropiedad(Constantes.CONSULTA_PROVISIONING);
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));

			cs = con.prepareCall(sql);
			cs.setString(1, request.getIccid());
			cs.setString(2, request.getProviderId());
			cs.setString(3, request.getEnterpriseId());
			cs.setString(4, request.getEid());
			cs.setNull(5, OracleTypes.VARCHAR);
			cs.setString(6, request.getPlanId());
			cs.setNull(7, OracleTypes.VARCHAR);
			logger.info("Parametros de entrada a provisioning: ICCID: " + request.getIccid() + "|PROVIDER_ID: " + request.getProviderId() + "|ENTERPRISEID: " + request.getEnterpriseId() 
			+ "|EID: " + request.getEid());
			cs.registerOutParameter(8, OracleTypes.VARCHAR);
			cs.registerOutParameter(9, OracleTypes.VARCHAR);
			cs.registerOutParameter(10, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PRO)));
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de provisioning" + timeFin + " ms");

			logger.info( "Base de Datos:" +cs.getString(9)  + " - Propiedad compara: "+ prop.obtenerPropiedad(Constantes.PRC_APROVISIONA_EXITOSO));
			if(cs.getString(9).equalsIgnoreCase(prop.obtenerPropiedad(Constantes.PRC_APROVISIONA_EXITOSO))){
				request.setMsisdn(cs.getString(8));
				logger.info(" Msisdn: "+request.getMsisdn());				
			}
			response.setResultCode(Integer.parseInt(cs.getString(9)));
			logger.info("Respuesta de PRC_REGISTRA_RECURSOS " + cs.getString(9) + " - " + cs.getString(10));
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en provisioning", e);
			throw new BusinessException(Constantes._102, e);
		} finally {
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
	}



	public static void crearSuscriptor(CreateUserRequest request, CreateUserResponsePL response)  throws BusinessException{
		logger.info("Inicia crearSuscriptor");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			String sql = prop.obtenerPropiedad(Constantes.CREAR_SUSCRIPTOR);
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_COMCORP),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_COMCORP));

			cs = con.prepareCall(sql);
			int i = 1;
			cs.setString(i++, request.getProviderId());
			cs.setString(i++, Propiedades.getInstance().obtenerPropiedad(Constantes.ENTER_PR_ID));
			cs.setString(i++, request.getOperatorUserId());
			cs.setString(i++, request.getCountry());
			cs.setString(i++, request.getCorrelatorId());
			cs.setString(i++, request.getIccid());
			cs.setString(i++, request.getImsi());
			cs.setString(i++, request.getMsisdn());

			if(request.getSubscriberDetails()!=null) {
				cs.setString(i++, request.getSubscriberDetails().getTitle()!=null?request.getSubscriberDetails().getTitle():"");
				cs.setString(i++, request.getSubscriberDetails().getFirstName() != null? request.getSubscriberDetails().getFirstName():"");
				cs.setString(i++, request.getSubscriberDetails().getLastName() != null? request.getSubscriberDetails().getLastName():"");
				if(request.getSubscriberDetails().getAddress()!=null){
					cs.setString(i++, request.getSubscriberDetails().getAddress().getAddressLine1() != null?request.getSubscriberDetails().getAddress().getAddressLine1():"");
					cs.setString(i++, request.getSubscriberDetails().getAddress().getAddressLine2() != null? request.getSubscriberDetails().getAddress().getAddressLine2():"");
					cs.setString(i++, request.getSubscriberDetails().getAddress().getCity() != null? request.getSubscriberDetails().getAddress().getCity():"");
					cs.setString(i++, request.getSubscriberDetails().getAddress().getState() != null? request.getSubscriberDetails().getAddress().getState():"");
					cs.setString(i++, request.getSubscriberDetails().getAddress().getPostalCode() != null? request.getSubscriberDetails().getAddress().getPostalCode():"");
					cs.setString(i++, request.getSubscriberDetails().getAddress().getCountryM2M() != null? request.getSubscriberDetails().getAddress().getCountryM2M():"");
				}else{
					cs.setNull(i++, OracleTypes.VARCHAR);
					cs.setNull(i++, OracleTypes.VARCHAR);
					cs.setNull(i++, OracleTypes.VARCHAR);
					cs.setNull(i++, OracleTypes.VARCHAR);
					cs.setNull(i++, OracleTypes.VARCHAR);
					cs.setNull(i++, OracleTypes.VARCHAR);
				}

				if(request.getSubscriberDetails().getCommunication()!=null){
					cs.setString(i++, request.getSubscriberDetails().getCommunication().getEmailAddress() != null? request.getSubscriberDetails().getCommunication().getEmailAddress():"");
					cs.setString(i++, request.getSubscriberDetails().getCommunication().getMobilePhone() != null? request.getSubscriberDetails().getCommunication().getMobilePhone():"");
				}else{
					cs.setNull(i++, OracleTypes.VARCHAR);
					cs.setNull(i++, OracleTypes.VARCHAR);
				}
				cs.setString(i++, request.getSubscriberDetails().getPreferredLanguageCode() != null? request.getSubscriberDetails().getPreferredLanguageCode():"");
			}else{
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
			}
			if(request.getTermsAndConditions()!=null){
				cs.setString(i++, request.getTermsAndConditions().getConsent() != null? request.getTermsAndConditions().getConsent()?prop.obtenerPropiedad(Constantes.APP_CONSTANTE_VERDADERO): prop.obtenerPropiedad(Constantes.APP_CONSTANTE_FALSO):"");
				cs.setString(i++, request.getTermsAndConditions().getConsentTimestamp() != null? request.getTermsAndConditions().getConsentTimestamp():"");
				cs.setString(i++, request.getTermsAndConditions().getDocumentCode() != null? request.getTermsAndConditions().getDocumentCode():"");
				cs.setString(i++, request.getTermsAndConditions().getDocumentVersion() != null? request.getTermsAndConditions().getDocumentVersion():"");
				cs.setString(i++, request.getTermsAndConditions().getDocumentLanguage() != null? request.getTermsAndConditions().getDocumentLanguage():"");
				cs.setString(i++, request.getTermsAndConditions().getValidationIDType() != null? request.getTermsAndConditions().getValidationIDType():"");
			}else{
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
			}

			if (request.getMnoSubscriberDetails()!=null){
				cs.setString(i++, request.getMnoSubscriberDetails().getDateOfBirth() != null? request.getMnoSubscriberDetails().getDateOfBirth():"");
				cs.setString(i++, request.getMnoSubscriberDetails().getPlaceOfBirth() != null? request.getMnoSubscriberDetails().getPlaceOfBirth():"");
				cs.setString(i++, request.getMnoSubscriberDetails().getIdType() != null? request.getMnoSubscriberDetails().getIdType():"");
				cs.setString(i++, request.getMnoSubscriberDetails().getIdValue() != null? request.getMnoSubscriberDetails().getIdValue():"");
			}else{
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
				cs.setNull(i++, OracleTypes.VARCHAR);
			}
			if(request.getServicePlan()!=null){
				cs.setString(i++, request.getServicePlan().getPlanId() != null? request.getServicePlan().getPlanId():"");
			}else{
				cs.setNull(i++, OracleTypes.VARCHAR);
			}
			cs.registerOutParameter(i++, OracleTypes.CURSOR);
			cs.registerOutParameter(i++, OracleTypes.VARCHAR);
			cs.registerOutParameter(i, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PRO)));
			logger.info("Parametros de entrada a crearSuscriptor: " + request.toString());
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de crearSuscriptor" + timeFin + " ms");
			response.setResultMessage(cs.getString(i--));
			response.setResultCode(Integer.parseInt(cs.getString(i--)));
			rs = (ResultSet) cs.getObject(i--);
			if (rs != null) 
				while (rs.next()) {
					CursorCreateUser cursor = new CursorCreateUser();
					cursor.setProviderId(rs.getString(1));
					cursor.setAccountUserId(rs.getString(2));
					cursor.setIccId(rs.getString(3));
					cursor.setImsi(rs.getString(4));
					cursor.setMsisdn(rs.getString(5));
					response.setCursor(cursor);
				}
			logger.info("Respuesta de GM_CREA_SUSCRIPTOR " + response);
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en crearSuscriptor", e);
			throw new BusinessException(Constantes._102, e);
		} finally {
			Conexion.cerrar(cs);
			Conexion.cerrar(rs);
			Conexion.cerrar(con);
		}

	}

	public static void deleteUser(DeleteUserRequest request, DeleteUserResponse response) throws BusinessException {
		logger.info("Inicia deleteUser");
		Connection con = null;
		CallableStatement cs = null;
		String sql = prop.obtenerPropiedad(Constantes.ACTESTADO_SUSCRIPTOR);
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);

			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_COMCORP), prop.obtenerPropiedad(Constantes.TIPO_CONEXION_COMCORP));

			cs = con.prepareCall(sql);

			cs.setString(1, request.getBillingAccountNumber());
			cs.setString(2, prop.obtenerPropiedad(Constantes.ESTADO_INACTIVO));

			cs.registerOutParameter(3, OracleTypes.CURSOR);
			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);

			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PRO)));
			logger.info("Parametros de entrada a deleteUser: " + request.getBillingAccountNumber());
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento " + timeFin + " ms");

			logger.info("Respuesta de gm_actestado_suscriptor: " + cs.getString(4) + " - " + cs.getString(5));
			response.setResultCode(Integer.parseInt(cs.getString(4)));
			response.setResultMessage(cs.getString(5));
			if (prop.obtenerPropiedad(Constantes.PRC_ACTESTADO_SUSCRIPTOR_EXITOSO).equals(cs.getString(4)) && backupCtrlPase(request.getMsisdn())) {
					deleteCtrlPase(request.getMsisdn());
			}

		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en deleteUser", e);
			throw new BusinessException(Constantes._102, e);
		} finally {
			logger.info("Finaliza deleteUser");
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
	}

	private static boolean backupCtrlPase(String msisdn) throws BusinessException {
        logger.info("Inicia metodo backupCtrlPase");
        boolean retorno = false;
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        String sql = prop.obtenerPropiedad(Constantes.BACKUP_CONTROL_PASE);
        try {
                    if (sql == null || sql.isEmpty())
                               throw new BusinessException(Constantes._100);
                    con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_SMSSERV),
                                           prop.obtenerPropiedad(Constantes.TIPO_CONEXION_SMSSERV));
                    cs = con.prepareCall(sql);
                    cs.setString(1, msisdn);
                    int dias = Integer.parseInt(prop.obtenerPropiedad(Constantes.TIEMPO_VIGENCIA_CTRL_PASE_BACKUP));
                    cs.setInt(2, dias);
                    cs.registerOutParameter(3, OracleTypes.VARCHAR);
                    cs.registerOutParameter(4, OracleTypes.VARCHAR);
                 cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PRO)));
                    logger.info("Parametros de entrada a backupCtrlPase: PE_MSISDN: " + msisdn + ", PE_DIAS: " + dias);
                    long timeIni = System.currentTimeMillis();
                    cs.execute();
                    long timeFin = System.currentTimeMillis() - timeIni;
                    logger.info("Tiempo de procesamiento de backupCtrlPase " + timeFin + " ms");
                    logger.info("Respuesta del procedimento: " + cs.getString(3) + " - " + cs.getString(4));
                    if (cs.getString(3).equalsIgnoreCase(prop.obtenerPropiedad(Constantes.PRC_BACKUP_CTRL_PASE_EXITOSO))) {
                               logger.debug("Se hizo backup de los registros de ctrl_pase con MSISDN: " + msisdn);
                               retorno = true;
                    }
        } catch (Exception e) {
                    logger.error("Ocurrio una excepcion en backupCtrlPase: ", e);
                    throw new BusinessException(Constantes._101);
        } finally {
                    Conexion.cerrar(rs);
                    Conexion.cerrar(cs);
                    Conexion.cerrar(con);
        }
        return retorno;
	}

	private static boolean deleteCtrlPase(String msisdn) throws BusinessException {
		logger.info("Inicia metodo deleteCtrlPase");
		boolean retorno = false;
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.DELETE_CONTROL_PASE);               
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_SMSSERV),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_SMSSERV));
			cs = con.prepareCall(sql);                              
			cs.setString(1, msisdn);
			cs.registerOutParameter(2, OracleTypes.VARCHAR);
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PRO)));
			logger.info("Parametros de entrada a deleteCtrlPase : MSISDN: " + msisdn);
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de deleteCtrlPase" + timeFin + " ms");
			logger.info( "Respuesta del procedimento: " + cs.getString(2)  + " - " + cs.getString(3));
			if(cs.getString(2).equalsIgnoreCase(prop.obtenerPropiedad(Constantes.PRC_DELETE_CTRL_PASE_EXITOSO))){
				logger.debug("Se eliminaron los registros de ctrl_pase con MSISDN: " + msisdn);
				retorno = true;
			}
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en deleteCtrlPase: ", e);
			throw new BusinessException(Constantes._101);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return retorno;
	}

	public static void queryUserPro(String billingAccountNumber, QueryUserResponsePL response) throws BusinessException{
		logger.info("Inicia metodo de ejecucion de procedimiento queryUserPro");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.PRC_QUERY_USER);		
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_COMCORP),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_COMCORP));

			cs = con.prepareCall(sql);			
			cs.setString(1, billingAccountNumber);		
			cs.registerOutParameter(2, OracleTypes.CURSOR);			
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_QUERY)));
			logger.info("Parametros de entrada a queryUserPro: " + billingAccountNumber);
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de queryUserPro" + timeFin + " ms");
			response.setResultCode(Integer.parseInt(cs.getString(3)));
			response.setResultMessage(cs.getString(4));
			rs = (ResultSet) cs.getObject(2);
			CursorQueryUser cursor = new CursorQueryUser();
			if(rs!=null){
				while (rs.next()) {
					cursor.setProviderId(rs.getString("providerid"));
					cursor.setOperatorUserId(rs.getString("operatoruserid"));
					cursor.setIccId(rs.getString("iccid"));
					cursor.setImsi(rs.getString("imsi"));
					cursor.setMsisdn(rs.getString("msisdn"));
					cursor.setCountry(rs.getString("country"));
					cursor.setTitle(rs.getString("sd_title"));
					cursor.setFirstName(rs.getString("sd_firstname"));
					cursor.setLastName(rs.getString("sd_lastname"));
					cursor.setAddressLine1(rs.getString("sda_addressline1"));
					cursor.setAddressLine2(rs.getString("sda_addressline2"));
					cursor.setCity(rs.getString("sda_city"));
					cursor.setState(rs.getString("sda_state"));
					cursor.setPostalCode(rs.getString("sda_postalcode"));
					cursor.setCountryM2M(rs.getString("sda_countrym2m"));
					cursor.setEmailAddress(rs.getString("sdc_emailaddress"));
					cursor.setMobilePhone(rs.getString("sdc_mobilephone"));
					cursor.setPreferredLanguageCode(rs.getString("sd_preferredlanguagecode"));
					cursor.setConsent(rs.getString("tc_consent"));
					cursor.setConsentTimestamp(rs.getString("tc_consenttimestamp"));
					cursor.setDocumentCode(rs.getString("tc_documentcode"));
					cursor.setDocumentVersioivate(rs.getString("tc_documentversion"));
					cursor.setDocumentLanguage(rs.getString("tc_documentlanguage"));
					cursor.setValidationIDType(rs.getString("tc_validationidtype"));
					cursor.setDateOfBirth(rs.getString("msd_dateofbirth"));
					cursor.setPlaceOfBirth(rs.getString("msd_placeofbirth"));
					cursor.setIdType(rs.getString("msd_idtype"));
					cursor.setIdValue(rs.getString("msd_idvalue"));
					cursor.setPlanId(rs.getString("sp_planid"));
					cursor.setBillingAccountNumber(rs.getString("idcuentausuario"));
					cursor.setBillingAccountType(rs.getString("ba_billingaccounttype"));
					cursor.setBillingAccountStatus(rs.getString("ba_billingaccountstatus"));
					cursor.setEstado(rs.getString("estado"));				
					logger.info("Respuesta de procedimiento almacenado de queryUserPro");
				}
			}
			response.setCursor(cursor);
			logger.info("Respuesta procedimiento GM_CONSULTA_SUSCRIPTOR: " + response);
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en queryUserPro: ", e);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}

	}


	public static void consultaDatosPlan(ProvisioningRequest request) throws BusinessException{
		logger.info("Inicia metodo de ejecucion de procedimiento consultaDatosPlan");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.CONSULTA_DATOS_PLAN);		
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));

			cs = con.prepareCall(sql);			
			cs.setString(1, request.getIccid());		
			cs.registerOutParameter(2, OracleTypes.CURSOR);			
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PRO)));
			logger.info("Parametros de entrada a consultaDatosPlan: ICCID: " + request.getIccid());
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de consultaDatosPlan " + timeFin + " ms");
			rs = (ResultSet) cs.getObject(2);
			if(rs!=null){
				while (rs.next()) {
					request.setMsisdn(rs.getString("MSISDN"));
					request.setNMtmcode(rs.getString("TMCODE"));	
				}
			}
			logger.info("Respuesta procedimiento de consultaDatosPlan : " + request.getMsisdn()+"-"+request.getNMtmcode());
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en consultaDatosPlan : ", e);
			throw new BusinessException(Constantes._101);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}

	}


	public static void consultaIccid(GetDownloadProfileStatusRequest request) throws BusinessException{
		logger.info("Inicia metodo de ejecucion de procedimiento consultaIccid");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.CONSULTA_ICCID);		
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_SMSSERV),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_SMSSERV));

			cs = con.prepareCall(sql);			
			cs.setString(1, request.getCorrelatorId());
			cs.setString(2, prop.obtenerPropiedad(Constantes.METODO_PROVISIONING));		
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PRO)));
			logger.info("Parametros de entrada a consultaIccid: CORRELATOR_ID: " + request.getCorrelatorId());
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de consultaIccid " + timeFin + " ms");
			logger.info( "Respuesta del procedimento:" +cs.getString(4)  + " - "+ cs.getString(5));
			if(cs.getString(4).equalsIgnoreCase(prop.obtenerPropiedad(Constantes.PRC_APROVISIONA_EXITOSO))){
				request.setIccid(cs.getString(3));
				logger.info("Iccid: "+request.getIccid());				
			}
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en consultaIccid: ", e);
			throw new BusinessException(Constantes._101);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}

	}

	public static PasesType activaPaquete(ProductType producto, String msisdn) throws BusinessException{
		logger.info("Inicia metodo de ejecucion de procedimiento activaPaquete"+producto+"-"+msisdn);
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		PasesType pase = null;
		String sql = prop.obtenerPropiedad(Constantes.ACTIVA_PAQUETE);
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));

			cs = con.prepareCall(sql);			
			cs.setString(1, producto.getCoid());	
			cs.setString(2, msisdn);	
			cs.setString(3, producto.getIncludedBytes());	
			cs.setString(4, producto.getPrioridad());	

			Calendar calendar = Calendar.getInstance();
			java.sql.Timestamp cal = new Timestamp(calendar.getTimeInMillis());
			
			logger.info("FECHA PARA BSCS: " + cal);
			
			cs.setTimestamp(5, cal);
			cs.setTimestamp(6, producto.getFechaExpiracion());
			cs.setInt(7, Integer.parseInt(prop.obtenerPropiedad(Constantes.NM_GENERA_EVENTO)));	
			cs.setString(8, producto.getType());	
			cs.setNull(9, OracleTypes.VARCHAR);	
			cs.setNull(10, OracleTypes.VARCHAR);	
			cs.setInt(11, Integer.parseInt(prop.obtenerPropiedad(Constantes.NM_RENOVACION)));	
			cs.setString(12, producto.getSku());	
			cs.registerOutParameter(13, OracleTypes.NUMBER);			
			cs.registerOutParameter(14, OracleTypes.NUMBER);
			cs.registerOutParameter(15, OracleTypes.VARCHAR);
			cs.setInt(16, Integer.parseInt(prop.obtenerPropiedad(Constantes.NM_COMMIT)));
			cs.setNull(17, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_ACT)));
			logger.info("Parametros de entrada a activaPaquete: CO_ID: " + producto.getCoid() + "|MSIDN: " + msisdn
					+ "|OTROS: "  + producto.getIncludedBytes() + "|" + producto.getPrioridad() + "|" + producto.getFechaExpiracion() + 
					"|" + producto.getType() + "|" + producto.getSku());
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de activaPaquete" + timeFin + " ms");
			logger.info("Respuesta de llamado a procedimiento almacenado " + cs.getInt(14) + " - " + cs.getString(15));
			
			logger.info("FECHA ACTIVAPAQUETE: |" + cal + "|FECHA EXPIRACION: |" + producto.getFechaExpiracion() + "|");
			
			pase= new PasesType(cs.getString(14),msisdn, producto.getSku(), cs.getInt(13), cal, producto.getFechaExpiracion());
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en activaPaquete : ", e);
			throw new BusinessException(Constantes._101); 
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return pase;

	}
	
	public static String procesaPase(PasesType pase, Boolean inserta) throws PaseException{
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = null;
		String respuesta=null;
		if (inserta) {
			sql = prop.obtenerPropiedad(Constantes.INSERTA_PASE);	
			logger.info("Inicia metodo de ejecucion de procedimiento procesaPase Insercion");
		}else{
			sql = prop.obtenerPropiedad(Constantes.ACTUALIZA_PASE);
			logger.info("Inicia metodo de ejecucion de procedimiento procesaPase Actualizacion");
		}
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_COMCORP),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_COMCORP));

			cs = con.prepareCall(sql);			
			cs.setString(1, pase.getMsisdn() );		
			cs.setString(2,	pase.getProductId() );
			cs.setInt(3, pase.getSecuencia());
			cs.setString(4, pase.getFechaActivacion());
			cs.setString(5, pase.getFechaexpiracion());
			cs.registerOutParameter(6, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PASE)));
			logger.info("Parametros de entrada a procesaPase: " + pase.toString());
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de procesaPase" + timeFin + " ms");
			respuesta = cs.getString(6);
			logger.info("Respuesta procedimiento : " + respuesta);
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en procesaPase: ", e);
			throw new PaseException();
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return respuesta;
	}

	public static ArrayList<String> consultaPase(String msisdn, String productId) throws BusinessException{
		logger.info("Inicia metodo de ejecucion de procedimiento consultaPase");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		ArrayList<String> respuesta=new ArrayList<>();	
		String sql =  prop.obtenerPropiedad(Constantes.CONSULTA_PASE);
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_COMCORP),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_COMCORP));

			logger.info("Parametros de entrada a consultaPase: MIN:" + msisdn + "| PRODUCT_ID: " + productId);
			cs = con.prepareCall(sql);			
			cs.setString(1,msisdn );
			cs.setString(2,	productId);
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.registerOutParameter(4, OracleTypes.NUMBER);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PASE)));
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento " + timeFin + " ms");
			respuesta.add(cs.getString(3));
			respuesta.add(cs.getString(4));
			logger.info("Respuesta procedimiento de consultaPase: " + respuesta.toString());
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en consultaPase: ", e);
			throw new BusinessException(Constantes._101);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return respuesta;
	}

	public static TreeMap<String,ProductType> consultaProductos(String empresa, String proveedor, String msisdn) throws BusinessException{
		logger.info("Inicia metodo de ejecucion de procedimiento consultaProductos con lo datos:"+proveedor+"-"+empresa+"-"+msisdn);
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.CONSULTA_PRODUCTOS);	
		TreeMap<String,ProductType> productos = new TreeMap<>();
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));

			cs = con.prepareCall(sql);			
			cs.setString(1, proveedor);
			if(empresa == null) {
				cs.setNull(2, OracleTypes.NULL);
			}else {
				cs.setString(2, empresa);
			}
			
			if (msisdn.isEmpty()) {
				cs.setNull(3, OracleTypes.VARCHAR);//codigo
			}else {
				cs.setString(3, msisdn);
			}
			cs.setNull(4, OracleTypes.VARCHAR);//codigo
			cs.registerOutParameter(5, OracleTypes.CURSOR);//cursor oferta			
			cs.registerOutParameter(6, OracleTypes.VARCHAR);//codigo
			cs.registerOutParameter(7, OracleTypes.VARCHAR);//descripcion
			cs.registerOutParameter(8, OracleTypes.VARCHAR);//enterprise
			cs.registerOutParameter(9, OracleTypes.VARCHAR);//provider
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PRODUCTS)));
			logger.info("Parametros de entrada a consultaProductos: PROVEEDOR: " + proveedor + "|EMPRESA: " + empresa);
			logger.info(msisdn.isEmpty() ?"":"|MSISDN: " + msisdn);			
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de consultaProductos" + timeFin + " ms");
			rs = (ResultSet) cs.getObject(5);
			if(rs!=null){
				while (rs.next()) {
					ProductType producto = new ProductType();
					producto.setCoid(rs.getString("NM_COID"));
					producto.setSku(rs.getString("VCCODIGO"));
					producto.setName(rs.getString("VCDESCLARGA"));
					producto.setPrice(rs.getDouble("NMVALORBASE"));
					producto.setCurrency(prop.obtenerPropiedad(Constantes.NOMBRE_COL_CURRENCY));
					producto.setVat(rs.getDouble("NMVALORIVA"));
					producto.setDuration(rs.getInt("NMDURACIONDIAS"));
					producto.setShortName(rs.getString("VCDESCCORTA"));
					producto.setGroup(prop.obtenerPropiedad(Constantes.GRUPO_PRODUCTO));
					producto.setType(rs.getString("VCTIPOPASE"));
					producto.setIncludedHours(prop.obtenerPropiedad(Constantes.HORAS_PRODUCTO));
					producto.setIncludedBytes(rs.getString("NMVOLUMENDADOS"));
					producto.setRoaming(prop.obtenerPropiedad(Constantes.PRODUCTO_ROAMING));
					producto.setAltNameOne(rs.getString("VCDESCCORTA"));
					producto.setAltNameTwo(rs.getString("VCDESCCORTA"));
					producto.setFechaExpiracion(rs.getTimestamp("DTFECHAEXP"));
					producto.setPrioridad(rs.getString("VCPRIORIDAD"));

					productos.put(producto.getSku(), producto);
				}
			}
			logger.info("Respuesta procedimiento : "+cs.getString(6)+ "-"+ cs.getString(7));
			logger.info("Productos encontrados : " + productos.toString());
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en consultaProductos: ", e);
			throw new BusinessException(Constantes._101);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return productos;
	}

	public static void cancelProduct(String secuencia, CancelProductResponse response) throws BusinessException {
		logger.info("Inicia ejecucion del procedimiento para cancelProduct con secuencia:"+secuencia);
		Connection con = null;
		CallableStatement cs = null;
		String sql = prop.obtenerPropiedad(Constantes.PROCEDURE_CANCEL_PRODUCT);
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));

			cs = con.prepareCall(sql);			
			cs.setString(1, prop.obtenerPropiedad(Constantes.CANCEL_TIPO));
			cs.setString(2, secuencia);		
			cs.setNull(3, OracleTypes.VARCHAR);
			cs.setNull(4, OracleTypes.DATE);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);
			cs.setInt(7, Integer.parseInt(prop.obtenerPropiedad(Constantes.CANCEL_COMMIT)));
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_CANCELA_PAQ)));
			logger.info("Parametros de entrada a cancelProduct: TIPO: " + prop.obtenerPropiedad(Constantes.CANCEL_TIPO) + "SECUENCIA: " + secuencia);
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de cancelProduct" + timeFin + " ms");
			response.setResultCode(cs.getString(5));
			response.setResultMessage(cs.getString(6));
			logger.info( "Respuesta del procedimento:" +cs.getString(5)  + " - "+ cs.getString(6));

		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en cancelProduct: ", e);
			throw new BusinessException(Constantes._101);
		} finally {
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
	}

	public static void instalaTickler(String msisdn, String productId, String metodo)  {
		logger.info("Ingresa a instalar tickler para: " + metodo);
		String consDatosCliente =prop.obtenerPropiedad(Constantes.CONSULTA_DATOS_CLIENTE);
		String sqlCreaTickler =prop.obtenerPropiedad(Constantes.TICKLER_SQL);
		PreparedStatement prpStmt = null;
		CallableStatement cllStmt = null;
		ResultSet rs = null;
		Connection cnxBSCS = null;
		try {
			if (consDatosCliente == null || consDatosCliente.isEmpty() || sqlCreaTickler == null || sqlCreaTickler.isEmpty())
				throw new BusinessException(Constantes._100);
			cnxBSCS = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));
			prpStmt = cnxBSCS.prepareStatement(consDatosCliente);
			prpStmt.setString(1, msisdn);
			rs = prpStmt.executeQuery();
			String respuesta = "";
			if (rs.next()) {
				String codigoTickler = metodo.equals(prop.obtenerPropiedad(Constantes.METODO_BUY_PRODUCT))?prop.obtenerPropiedad(Constantes.TICKLER_CODE_BUY):prop.obtenerPropiedad(Constantes.TICKLER_CODE_CANCEL);
				String descCorta = metodo.equals(prop.obtenerPropiedad(Constantes.METODO_BUY_PRODUCT))?prop.obtenerPropiedad(Constantes.TICKLER_DESC_CORTA_BUY):prop.obtenerPropiedad(Constantes.TICKLER_DESC_CORTA_CANCEL);
				String descLarga = metodo.equals(prop.obtenerPropiedad(Constantes.METODO_BUY_PRODUCT))?prop.obtenerPropiedad(Constantes.TICKLER_DESC_LARGA_BUY):prop.obtenerPropiedad(Constantes.TICKLER_DESC_LARGA_CANCEL);
				cllStmt = cnxBSCS.prepareCall(sqlCreaTickler);
				cllStmt.setLong(1, Long.parseLong(rs.getString("CUSTOMER_ID")));
				cllStmt.setLong(2, Long.parseLong(rs.getString("CO_ID")));
				cllStmt.setString(3, codigoTickler); 
				cllStmt.setString(4, msisdn);
				cllStmt.setString(5, productId);
				cllStmt.setString(6, descCorta);
				cllStmt.setString(7, descLarga);
				cllStmt.registerOutParameter(8, OracleTypes.VARCHAR);
				cllStmt.setString(9, prop.obtenerPropiedad(Constantes.TICKLER_UPDATE));
				logger.info("Paramentros de entrada a instalaTickler : CUSTOMER_ID:" + rs.getString("CUSTOMER_ID") + "| CO_ID: " + rs.getString("CO_ID") + "|CODIGO_TICKLER: "+ codigoTickler
						+ "|MSISDN: " + msisdn + "|PRODUCT_ID:" + productId  + "|DESC_CORTA: " + descCorta + "|DESC_LARGA: "+ descLarga + "|UPDATE: " + prop.obtenerPropiedad(Constantes.TICKLER_UPDATE));
				long timeIni = System.currentTimeMillis();
				cllStmt.execute();
				long timeFin = System.currentTimeMillis() - timeIni;
				logger.info("Tiempo de procesamiento de instalaTickler" + timeFin + " ms");
				respuesta = cllStmt.getString(8);
				
			}
			if(respuesta.isEmpty()) {
				logger.info("No se encuetra informacion del contrato del usuario.");
			}else {
				if(prop.obtenerPropiedad(Constantes.TICKLER_OK).equals(respuesta)) {
					logger.info("Creacion de tickler exitosa : " + respuesta);
				}else {
					logger.info("Creacion de tickler no exitosa : " + respuesta);
				}
				
			}
		}
		catch (SQLException ex) {
			logger.info("Error creando tickler ", ex);
		}
		catch (Exception ex2) {
			logger.info("Error creando tickler ", ex2);
		}
		finally {
			Conexion.cerrar(cllStmt);
			Conexion.cerrar(prpStmt);
			Conexion.cerrar(rs);
			Conexion.cerrar(cnxBSCS);
		}
	}

	public static void cambioSIM(ChangeSimRequest request, ChangeSimResponse response) throws BusinessException{
		logger.info("Inicia metodo de ejecucion de procedimiento cambioSIM");
		Connection con = null;
		CallableStatement cs = null;
		String sql =  prop.obtenerPropiedad(Constantes.SQL_CAMBIO_SIM);
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));

			cs = con.prepareCall(sql);			
			cs.setString(1,request.getIccid2());		
			cs.setString(2,	request.getIccid1());
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_CAMBIO_SIM)));
			logger.info("Parametros de entrada a cambioSIM: ICCID_1: " + request.getIccid1() + "|ICCID_2: " + request.getIccid2());
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de cambioSIM" + timeFin + " ms");
			response.setResultCode(cs.getInt(3));
			response.setResultMessage(cs.getString(4));

			logger.info("Respuesta procedimiento : " + response.getResultCode()+"-"+response.getResultMessage());
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en cambioSIM: ", e);
			throw new BusinessException(Constantes._101);
		} finally {
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
	}
	public static String consultarImsi(String  iccid) throws BusinessException {
		logger.info("Inicia consulta imsi para: "+ iccid);
		Connection con = null;
		CallableStatement cs = null;
		String sql = prop.obtenerPropiedad(Constantes.CONSULTA_PERFIL);

		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));

			cs = con.prepareCall(sql);
			cs.setString(1, iccid);
			cs.registerOutParameter(2, OracleTypes.NUMBER);
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.registerOutParameter(4, OracleTypes.NUMBER);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);

			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PERFIL)));
			logger.info("Parametros de entrada a consultarImsi: ICCID: " + iccid);
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de consultarImsi" + timeFin + " ms");

			String  imsi = cs.getString(3)==null ? "" : cs.getString(3);

			logger.info("Respuesta de PRC_CONSULTA_PERFIL " + cs.getString(6) + " - " + cs.getString(7)+"-"+cs.getString(5));
			logger.info("IMSI: " + imsi);
			return imsi;
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en consultarImsi", e);
			throw new BusinessException(Constantes._102, e);
		} finally {
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
	}
	
	public static Integer insertarControlPase(String msisdn, String proveedor, String empresa, String pase, String orden, String consecActivacion, String descPase, 
			Timestamp fechaCompraPase, Timestamp  fechaActivacionPase, Double mbIncluidosPase, Timestamp fechaFinalPase ) {
		
		logger.info("Inicia Insertar Control Pase");
		Connection con = null;
		CallableStatement cs = null;
		String sql = prop.obtenerPropiedad(Constantes.INSERTAR_CONTROL_PASE);
		Integer cod = null;
		String mensaje = null;
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_SMSSERV),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_SMSSERV));

			cs = con.prepareCall(sql);
			cs.setString(1, msisdn);
			cs.setString(2, proveedor);
			cs.setString(3, empresa);
			cs.setString(4, pase);
			cs.setString(5, orden);
			cs.setString(6, consecActivacion);
			cs.setString(7, descPase);
			cs.setTimestamp(8, fechaCompraPase);
			cs.setTimestamp(9, fechaActivacionPase);
			cs.setDouble(10, mbIncluidosPase);
			cs.setTimestamp(11, fechaFinalPase);
			cs.registerOutParameter(12, OracleTypes.INTEGER);
			cs.registerOutParameter(13, OracleTypes.VARCHAR);
			logger.info("Parametros de entrada a insertarControlPase : MSISDN: " + msisdn + "|PROVEEDOR: " + proveedor + "|EMPRESA: " + empresa + "|PASE: " + pase + "|ORDEN: " + orden + 
					"|CONSECUTIVO: " + consecActivacion + "|DESCRIPCION: " + descPase + "|FECHA_COMPRA: " + fechaCompraPase + "|FECHA_ACTIVACION: " + fechaActivacionPase + 
					"|MB: " + mbIncluidosPase  + "|FECHA_FINAL: " + fechaFinalPase);
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de insertarControlPase " + timeFin + " ms");
			cod = cs.getInt(12);
			mensaje = cs.getString(13);
			logger.info("Respuesta de insertarControlPase " + cod + " - " + mensaje);
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en insertarControlPase: ", e);
		} finally {
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return cod;
	}
	
	public static Integer actualizarControlPase(String msisdn, String proveedor, String consecActivacion, String tipoActualizacion ) {
		logger.info("Inicia actualizarControlPase");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.ACTUALIZAR_CONTROL_PASE);
		Integer cod = null;
		String mensaje = null;
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_SMSSERV),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_SMSSERV));

			cs = con.prepareCall(sql);
			cs.setString(1, msisdn);
			cs.setString(2, proveedor);
			cs.setNull(3, OracleTypes.VARCHAR);
			cs.setNull(4, OracleTypes.VARCHAR);
			cs.registerOutParameter(5, OracleTypes.INTEGER);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);
			cs.setString(7, tipoActualizacion);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_ACTUALIZA_PASE)));
			logger.info("Parametros de entrada a actualizarControlPase: MSISDN: " + msisdn + "|PROVEEDOR: " + proveedor + "|TIPO_ACTUALIZACION: " + tipoActualizacion);
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de actualizarControlPase " + timeFin + " ms");
			cod = cs.getInt(5);
			mensaje = cs.getString(6);
			logger.info("Respuesta de actualizarControlPase " + cod + " - " + mensaje);
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en actualizarControlPase: ", e);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return cod;
	}
	public static String actualizaPaseFechacomcorp(String msisdn) throws BusinessException {
		logger.info("Inicia metodo de actualizacion de finalizacion de pase en COMCORP");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql =  prop.obtenerPropiedad(Constantes.SQL_ACTUALIZA_PASE_COMCORP);
		String respuesta = "";
		int secuencia = 0;
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_COMCORP),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_COMCORP));
			
			cs = con.prepareCall(sql);			
			cs.setString(1, msisdn);
			java.util.Date fecha = new java.util.Date();
			DateFormat sd = new SimpleDateFormat(Propiedades.getInstance().obtenerPropiedad( Constantes.FORMATO_FECHA_COMCORP));
			sd.setTimeZone(TimeZone.getTimeZone(prop.obtenerPropiedad(Constantes.ZONA_HORARIA_COMCORP)));
			cs.setString(2,	sd.format(fecha));
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.registerOutParameter(4, OracleTypes.NUMBER);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_ACTUALIZA_PASE)));
			logger.info("Parametros de entrada a actualizaPaseFechacomcorp: MIN:" + msisdn + "|FECHA: " + sd.format(fecha));
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de actualizaPaseFechacomcorp" + timeFin + " ms");
			respuesta = Constantes.PIPE +  cs.getString(3) + Constantes.PIPE; 
			logger.info("Respuesta del procedimiento: " + respuesta);
			if(prop.obtenerPropiedad(Constantes.ACTUALIZA_PASE_COMCORP_OK).contains(respuesta)) {
				secuencia = cs.getInt(4);
				logger.info("Se realiza la actualizacion del pase exitosamente, secuencia: [" + secuencia + "]");
			}else {
				logger.info("Se obtuvo respuesta no exitosa en la actualizacion de la fecha del pase.");
			}
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en actualizaPaseFechacomcorp: ", e);
			throw new BusinessException(Constantes._101);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}	
		return respuesta;
	}	
	
	public static String obtenerEnterpriseId(String empresa, String proveedor, String msisdn) throws BusinessException{
		logger.info("Inicia metodo de ejecucion de procedimiento para obtener el ENTERPRISE_ID");
		Connection con = null;
		CallableStatement cs = null;
		String sql = prop.obtenerPropiedad(Constantes.CONSULTA_PRODUCTOS);	
		String enterpriseId = "";
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));

			cs = con.prepareCall(sql);			
			cs.setString(1, proveedor);
			if(empresa.isEmpty()) {
				cs.setNull(2, OracleTypes.VARCHAR);
			}else {
				cs.setString(2, empresa);				
			}
			
			if (msisdn.isEmpty()) {
				cs.setNull(3, OracleTypes.VARCHAR);//codigo
			}else {
				cs.setString(3, msisdn);
			}
			cs.setNull(4, OracleTypes.VARCHAR);//codigo
			cs.registerOutParameter(5, OracleTypes.CURSOR);//cursor oferta			
			cs.registerOutParameter(6, OracleTypes.VARCHAR);//codigo
			cs.registerOutParameter(7, OracleTypes.VARCHAR);//descripcion
			cs.registerOutParameter(8, OracleTypes.VARCHAR);//enterprise
			cs.registerOutParameter(9, OracleTypes.VARCHAR);//provider
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PRODUCTS)));
			logger.info("Parametros de entrada a obtenerEnterpriseId: PROVEEDOR: " + proveedor);
			logger.info(msisdn.isEmpty() ?"":"|MSISDN: " + msisdn);	
			logger.info(empresa.isEmpty() ?"":"|EMPRESA: " + empresa);			
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento " + timeFin + " ms");
			logger.info("Respuesta procedimiento : "+cs.getString(6)+ "-"+ cs.getString(7));
			logger.info("ENTERPRISE_ID: "+cs.getString(8)+ "|PROVIDER_ID: "+ cs.getString(9));
			enterpriseId = cs.getString(8);
			return enterpriseId;
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en obtenerEnterpriseId: ", e);
			throw new BusinessException(Constantes._101);
		} finally {
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		
	}
	
	public static void validaPasesExpirados() {
		
		logger.info("Inicia metodo de validacion de pases expirados");
		Connection con = null;
		CallableStatement cs = null;
		String sql =  prop.obtenerPropiedad(Constantes.PL_VALIDA_PASES_EXPIRADOS);
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_SMSSERV),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_SMSSERV));

			logger.info("Parametros de entrada a validaPasesExpirados: PALABRA:" + prop.obtenerPropiedad(Constantes.PL_VALIDA_PASES_EXPIRADOS_PALABRA) +
					"| MESES_ATRAS: " + prop.obtenerPropiedad(Constantes.PL_VALIDA_PASES_EXPIRADOS_MESES_ATRAS));
			
			cs = con.prepareCall(sql);			
			cs.setString(1, prop.obtenerPropiedad(Constantes.PL_VALIDA_PASES_EXPIRADOS_PALABRA) );
			cs.setInt(2, Integer.parseInt(prop.obtenerPropiedad(Constantes.PL_VALIDA_PASES_EXPIRADOS_MESES_ATRAS)));
			cs.registerOutParameter(3, OracleTypes.NUMBER);
			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PASE)));
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de validaPasesExpirados" + timeFin + " ms");
			logger.info("Respuesta procedimiento : " + cs.getInt(3) + " | " + cs.getString(4));
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en validaPasesExpirados: ", e);
		} finally {
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
	}
	
	public static ConsultaMaestra consultaMaestraActiva(ProvisioningRequest request) {
		
		logger.info("Inicia metodo de consulta maestra activa");
		Connection con = null;
		CallableStatement cs = null;
		String sql =  prop.obtenerPropiedad(Constantes.PL_CONSULTA_MAESTRA_ACTIVA);
		ConsultaMaestra consulta = null;
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));

			logger.info("Parametros de entrada a consultaMaestraActiva: PROVIDER_id:|" + request.getProviderId() + "| ENTERPRISE_ID:|" + request.getEnterpriseId());
			
			cs = con.prepareCall(sql);			
			cs.setString(1, request.getProviderId());
			cs.setString(2, request.getEnterpriseId());
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.registerOutParameter(4, OracleTypes.NUMBER);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.registerOutParameter(6, OracleTypes.NUMBER);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_CONSULTA_MAESTRA)));
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de consultaMaestraActiva" + timeFin + " ms");

			if(String.valueOf(cs.getInt(6)).equals(prop.obtenerPropiedad(Constantes.OK_PL_CONSULTA_MAESTRA_ACTIVA))) {
				consulta = new ConsultaMaestra();
				consulta.setCustcode(cs.getString(3));
				consulta.setTipoDoc(String.valueOf(cs.getInt(4)));
				consulta.setNumDoc(cs.getString(5));
				consulta.setCodigoSalida(String.valueOf(cs.getInt(6)));
				consulta.setDescripcionSalida(cs.getString(7));
				logger.info("Consulta maestra : " + consulta.toString());
				logger.info("Respuesta procedimiento : " + cs.getString(6) + "|" + cs.getString(7));
			} else {
				logger.info("Respuesta procedimiento : " + cs.getString(6) + "|" + cs.getString(7));
				return null;
			}
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en consultaMaestraActiva: ", e);
		} finally {
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return consulta;
	}
	
	public static String activacionAutomatica(String idAct, String canal) {
		logger.info("Inicia metodo para la activacion automatica");
		Connection con = null;
		CallableStatement cs = null;
		String sql =  prop.obtenerPropiedad(Constantes.PL_ACTIVACION_AUTOMATICA);
		String resp = null;
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));

			logger.info("Parametros de entrada a activacionAutomatica: ID_ACTIVACION:" + idAct + "| CANAL: " + canal);
			
			cs = con.prepareCall(sql);			
			cs.setInt(1, Integer.parseInt(idAct));
			cs.setString(2, canal);
			cs.registerOutParameter(3, OracleTypes.NUMBER);
			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PRC_ACT_AUTOMATICA)));
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de activacionAutomatica" + timeFin + " ms");
			logger.info("Respuesta procedimiento : " + cs.getInt(3) + " | " + cs.getString(4));
			resp = String.valueOf(cs.getInt(3));
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en activacionAutomatica: ", e);
		} finally {
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return resp;
	}
	
	public static void consultarDatos(ProvisioningRequest request) throws BusinessException {
		logger.info("Inicia consulta datos para: "+ request.getIccid());
		Connection con = null;
		CallableStatement cs = null;
		String sql = prop.obtenerPropiedad(Constantes.CONSULTA_PERFIL);

		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));

			cs = con.prepareCall(sql);
			cs.setString(1, request.getIccid());
			cs.registerOutParameter(2, OracleTypes.NUMBER);
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.registerOutParameter(4, OracleTypes.NUMBER);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);

			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PERFIL)));
			logger.info("Parametros de entrada a consultarDatos: ICCID: " + request.getIccid());
			long timeIni = System.currentTimeMillis();
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de consultarDatos " + timeFin + " ms");

			request.setImsi(cs.getString(3)==null ? "" : cs.getString(3));
			request.setMsisdn(cs.getString(2)==null ? "" : cs.getString(2));
			request.setNMtmcode(cs.getString(4)==null ? "" : cs.getString(4));
			
			logger.info("Respuesta de PRC_CONSULTA_PERFIL |MSISDN:" + cs.getString(2) + "|IMSI: " + cs.getString(3)+"|TMCODE: "+cs.getString(4) 
			+ "|RESP: " + cs.getString(6) + "|DESC: " + cs.getString(7));
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en consultarDatos ", e);
			throw new BusinessException(Constantes._102, e);
		} finally {
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
	}
	public static String consultarTmcode(String msisdn) throws BusinessException {
		logger.info("Inicia consultaTmcode");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.CONSULTA_PERFIL);
		String tmcode = null;
		
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BSCS),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BSCS));

			cs = con.prepareCall(sql);
			cs.setString(1, prop.obtenerPropiedad(Constantes.PARAMETRO_PERFIL));
			cs.setString(2, msisdn);
			cs.setString(3, prop.obtenerPropiedad(Constantes.TIPO_PERFIL));
			cs.registerOutParameter(4, OracleTypes.CURSOR);
			cs.registerOutParameter(5, OracleTypes.CURSOR);
			cs.registerOutParameter(6, OracleTypes.CURSOR);
			cs.registerOutParameter(7, OracleTypes.CURSOR);
			cs.registerOutParameter(8, OracleTypes.CURSOR);
			cs.registerOutParameter(9, OracleTypes.VARCHAR);

			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PLANES)));
			Long timeIni = System.currentTimeMillis();
			cs.execute();
			logger.info("tiempo de procesamiento:" + (System.currentTimeMillis() - timeIni) + " ms");
			rs = (ResultSet) cs.getObject(4);

			if (rs != null) {
				while (rs.next()) {
					tmcode = rs.getString(prop.obtenerPropiedad(Constantes.NOMBRE_COL_TMCODE));
					logger.info("tmcode encontrado " + tmcode);
				}
			}

			logger.info("Respuesta de PRC_CONSULTA_PERFIL " + cs.getString(9));
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en consultarTmcode", e);
			throw new BusinessException(Constantes._102, e);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return tmcode;
	}
	
	/**
	 * Metodo encargado de ejecutar el procedimiento almacenado PCR_CONSULTA_OFFER_MESSAGE desde el package PKG_Q_PLAN_LIST
	 * sobre la base de datos SMSSERV, se obtiene el OFFER_MESSAGE apartir del valor de ESTYPE
	 * 
	 * @author Juan Barreto Díaz - Hitss
	 * @since 13-02-2020
	 * @param request Objeto con informacion del request capturado desde el servicio expuesto
	 * @throws BusinessException Exception lanzada por ejecucion erronea del la logica
	 */
	public static String consultaOfferMessage(String esType) throws BusinessException{
		logger.info("Inicia metodo de ejecucion de procedimiento consultaOfferMessage");
		
		Connection con = null;
		CallableStatement cs = null;
		String sql = prop.obtenerPropiedad(Constantes.CONSULTA_OFFER_MESSAGE);
		String resp = null;
		
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_SMSSERV),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_SMSSERV));

			cs = con.prepareCall(sql);
			cs.setString(1, esType);
			cs.registerOutParameter(2, OracleTypes.VARCHAR);
			cs.registerOutParameter(3, OracleTypes.NUMBER);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PRO)));
			
			logger.info("Parametros de entrada a consultaOfferMessage: TYPE_ES: " + esType);
			
			long timeIni = System.currentTimeMillis();
			
			cs.execute();
			
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de consultaOfferMessage " + timeFin + " ms");
			logger.info( "Respuesta del procedimento PCR_CONSULTA_OFFER_MESSAGE:" + cs.getInt(3) +"|"+ cs.getString(2));
			
			resp = cs.getString(2);
			
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en consultaOfferMessage: ", e);
			throw new BusinessException(Constantes._101);
		} finally {
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return resp;
	}
	
	/**
	 * Metodo encargado de ejecutar el procedimiento almacenado PCR_CONSULTA_PROFILE_TYPE desde el package PKG_Q_PLAN_LIST
	 * sobre la base de datos SMSSERV, se obtiene el PROFILETYPE apartir del valor de PROVIDERID
	 * 
	 * @author Juan Barreto Díaz - Hitss
	 * @since 13-02-2020
	 * @param request Objeto con informacion del request capturado desde el servicio expuesto
	 * @throws BusinessException Exception lanzada por ejecucion erronea del la logica
	 */
	public static String consultaProfileType(String providerID) throws BusinessException{
		logger.info("Inicia metodo de ejecucion de procedimiento consultaProfileType");
		
		Connection con = null;
		CallableStatement cs = null;
		String sql = prop.obtenerPropiedad(Constantes.CONSULTA_PROFILE_TYPE);
		String resp = null;
		
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_SMSSERV),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_SMSSERV));

			cs = con.prepareCall(sql);			
			cs.setString(1, providerID);		
			cs.registerOutParameter(2, OracleTypes.VARCHAR);
			cs.registerOutParameter(3, OracleTypes.NUMBER);
			cs.setQueryTimeout(Integer.parseInt(prop.obtenerPropiedad(Constantes.QUERY_TIME_OUT_PRO)));
			
			logger.info("Parametros de entrada a consultaProfileType: PROVIDER_ID: " + providerID );
			
			long timeIni = System.currentTimeMillis();
			
			cs.execute();
			
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de consultaProfileType " + timeFin + " ms");
			logger.info( "Respuesta del procedimento PCR_CONSULTA_PROFILE_TYPE:" + cs.getInt(3) +"|"+ cs.getString(2));
			
			resp = cs.getString(2);
			
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en consultaProfileType: ", e);
			throw new BusinessException(Constantes._101);
		} finally {
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return resp;
	}
	
	/**
	 * Brief 31072 - Paquetes y Planes
	 * @param request
	 * @throws BusinessException
	 */
	public static int gestionPaQIoT(GestionPaquetesIOTRequest request) throws BusinessException{
		logger.info("Inicio de metodo ServiciosBD gestionPaQIoT - PL - PRC_GESTION_PAQ_IOT - BILLRO");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String sql = prop.obtenerPropiedad(Constantes.PRC_GESTION_PAQ_IOT);
		int respuesta;
		try {
			if (sql == null || sql.isEmpty())
				throw new BusinessException(Constantes._100);
			con = Conexion.getInstance().getConnection(prop.obtenerPropiedad(Constantes.BASE_DATOS_BILLRO),
					prop.obtenerPropiedad(Constantes.TIPO_CONEXION_BILLRO));

			cs = con.prepareCall(sql);			
			cs.setString(1, request.getAccion());
			cs.setString(2, request.getMin());
			cs.setDouble(3, request.getCoid());
			cs.setString(4, request.getImsi());
			cs.setInt(5, request.getTmCodeNew());
			
			if(request.getAccion().equals(Constantes.PAQ_IOT_V_ACCION_AL) || request.getAccion().equals(Constantes.PAQ_IOT_V_ACCION_DL)) {
		        cs.setNull(6, OracleTypes.NUMBER);
			}
			else {
				cs.setInt(6, request.getTmCodeOld());	
			}
			
			cs.registerOutParameter(7, OracleTypes.NUMBER);
			cs.registerOutParameter(8, OracleTypes.VARCHAR);
			
			long timeIni = System.currentTimeMillis();
			logger.info("Parametros de entrada a gestionPaQIoT:" + request.toString());
			cs.execute();
			long timeFin = System.currentTimeMillis() - timeIni;
			logger.info("Tiempo de procesamiento de gestionPaQIoT " + timeFin + " ms");
			logger.info( "Respuesta del procedimento PRC_GESTION_PAQ_IOT:" +cs.getInt(7)  + " - "+ cs.getString(8));
			
			if(cs.getInt(7) != Integer.parseInt(prop.obtenerPropiedad(Constantes.PRC_GESTION_PAQ_IOT_EXITOSO))) {
				logger.info("Error controlado de PRC_GESTION_PAQ_IOT: " + cs.getString(8));
			}
			
			respuesta = cs.getInt(7);
			
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion en gestionPaqIoT: ", e);
			throw new BusinessException(Constantes._101);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		return respuesta;

	}
	
}


