package com.claro.hubiot.util;

/**
 * Clase que almacena las constantes utilizadas a lo largo de la aplicacion
 * 
 * @author AUTOR
 *
 */
public abstract class Constantes {

	public static final String RUTA_ARCHIVO_PROPIEDADES = "/PropertiesAppWL/" + "WSHUBIoT/WSHUBIoT.properties";
	public static final String APLICACION = "WSHUBIoT";

	// Logger
	public static final String LOGGER_PRINCIPAL = "appLogger";

	// Excepciones
	public static final String EXCEPTION = "app.exception.";
	public static final String _100 = "100";
	public static final String _101 = "101";
	public static final String _102 = "102";
	public static final String _000 = "000";

	// Bases de Datos
	public static final String BASE_DATOS_SMSSERV = "app.conexion.SMSSERV";
	public static final String TIPO_CONEXION_SMSSERV = "app.tipo.conexion.SMSSERV";
	public static final String BASE_DATOS_BSCS = "app.conexion.BSCS";
	public static final String TIPO_CONEXION_BSCS = "app.tipo.conexion.BSCS";

	public static final String BASE_DATOS_COMCORP = "app.conexion.COMCORP";
	public static final String TIPO_CONEXION_COMCORP = "app.tipo.conexion.COMCORP";

	// Consultas SQL
	public static final String PRC_AUTENTICA = "app.consulta.autentica";
	public static final String CONSULTA_PLANES = "app.sql.planes";
	public static final String PRC_QUERY_USER = "app.sql.query.user";
	public static final String CONSULTA_PARAMETROS = "app.sql.parametros";
	public static final String CONSULTA_MENSAJES = "app.sql.mensajes";
	public static final String INSERTAR_TRANSACCION = "app.sql.transaccion";
	public static final String INSERTAR_REGISTRO = "app.sql.inserta.registro";

	public static final String CAMBIO_PLAN = "app.sql.cambio.plan";
	public static final String CONSULTA_PERFIL = "app.sql.consulta.perfil.activacion";
	public static final String CONSULTA_UNPROVISIONING = "app.sql.unprovisioning";
	public static final String CONSULTA_DATOS_PLAN = "app.sql.datos.plan";
	public static final String CONSULTA_ICCID = "app.sql.iccd";
	public static final String CONSULTA_PROVISIONING = "app.sql.provisioning";
	public static final String CONSULTA_ACTUALIZAR_DATOS_USUARIO = "app.sql.updateUser";
	public static final String ACTESTADO_SUSCRIPTOR = "app.sql.actestado.suscriptor";
	public static final String CONSULTA_PRODUCTOS = "app.sql.productos";
	public static final String ACTIVA_PAQUETE = "app.sql.activa.paquete";
	public static final String INSERTA_PASE = "app.sql.inserta.pase";
	public static final String ACTUALIZA_PASE = "app.sql.actualiza.pase";
	public static final String CONSULTA_PASE = "app.sql.consulta.pase";
	public static final String SQL_CAMBIO_SIM = "sql.cambio.sim";

	public static final String CREAR_SUSCRIPTOR = "app.sql.crear.suscriptor";

	public static final String QUERY_TIME_OUT_AUT = "consulta.tiempo.espera.aut";
	public static final String QUERY_TIME_OUT_PLANES = "consulta.tiempo.espera.planes";
	public static final String QUERY_TIME_OUT_PARAM = "consulta.tiempo.espera.param";
	public static final String QUERY_TIME_OUT_MSJ = "consulta.tiempo.espera.msj";
	public static final String QUERY_TIME_OUT_TRANS = "consulta.tiempo.espera.trans";
	public static final String QUERY_TIME_OUT_CAMBIO = "consulta.tiempo.espera.cambio";
	public static final String QUERY_TIME_OUT_PERFIL = "consulta.tiempo.espera.perfil";
	public static final String QUERY_TIME_OUT_UNPRO = "consulta.tiempo.espera.desaprovi";
	public static final String QUERY_TIME_OUT_PRO = "consulta.tiempo.espera.aprovi";
	public static final String QUERY_TIME_OUT_QUERY = "consulta.tiempo.espera.queryuser";
	public static final String QUERY_TIMEOUT_CREAR_USUARIO = "consulta.tiempo.espera.crear.usuario";
	public static final String QUERY_TIME_OUT_PRODUCTS = "consulta.tiempo.espera.products";
	public static final String QUERY_TIME_OUT_ACT = "consulta.tiempo.espera.activacion";
	public static final String QUERY_TIME_OUT_PASE = "consulta.tiempo.espera.pases";
	public static final String QUERY_TIME_OUT_CAMBIO_SIM = "consulta.tiempo.espera.cambio.sim";
	public static final String QUERY_TIME_OUT_CANCELA_PAQ = "consulta.tiempo.espera.cacela.paq";
	public static final String QUERY_TIME_OUT_NOTIFICACIONHUB = "consulta.tiempo.espera.notificacion.hub";
	public static final String QUERY_TIME_OUT_ACTUALIZA_PASE = "consulta.tiempo.espera.actualiza.pase";

	public static final String PRC_APROVISIONA_EXITOSO = "app.prc.aprvisiona.exito";
	public static final String PRC_DESAAPROVISIONA_EXITOSO = "app.prc.desaprvisiona.exito";
	public static final String PRC_CAMBIO_PLAN_EXITOSO = "app.prc.poliedro.exito";
	public static final String CAMBIO_SIM_EXITOSO = "app.cambio.sim.exito";
	public static final String PROCESO_EXITOSO = "001";

	public static final String RESPUESTA_ACK = "000";

	/**/
	public static final String ERROR_PARAMETROS_INCOMPLETOS = "900";
	public static final String ERROR_SIN_OFERTA = "901";
	public static final String ERROR_AUTENTICA = "906";
	/**/

	public static final String PARAMETROS_INCOMPLETOS = "900";
	public static final String PARAMETROS_VACIOS = "901";
	public static final String PARAMETROS_INCORRECTOS = "902";

	public static final String ERROR_PROCEDIMIENTO = "903";
	public static final String ERROR_INESPERADO = "999";
	public static final String ERROR_ACT = "904";
	public static final String ERROR_SIN_REGISTRO = "905";

	//
	public static final String CODIGO_PAIS = "app.country.code";
	public static final String USSD = "USSD";
	public static final String SMS = "SMS";

	public static final String AUTENTICA_RESP_EXITOSA = "prc.autentica.exitoso";
	public static final String TIPO_ENCRIPTACION = "app.tipo.encriptacion";

	public static final String HORA_INICIO = "app.hora.inicial";
	public static final String MINUTOS_INICIO = "app.minuto.inicial";
	public static final String RETRASO = "app.min.retraso";
	public static final String VALOR_COUNTRY = "app.val.country";
	public static final String NOMBRE_COL_ENTERPRICE = "app.nom.enterprice";
	public static final String NOMBRE_COL_PROVIDER = "app.nom.provider";
	public static final String NOMBRE_COL_PLAN = "app.nom.plan";
	public static final String NOMBRE_COL_NMTMCODE = "app.nom.nmtcode";
	public static final String NOMBRE_COL_MIN = "app.nom.min";
	public static final String NOMBRE_COL_SMS = "app.nom.sms";
	public static final String NOMBRE_COL_MB = "app.nom.mb";
	public static final String NOMBRE_COL_PRICE = "app.nom.price";
	public static final String NOMBRE_COL_PRODUCT = "app.nom.product";
	public static final String NOMBRE_COL_CURRENCY = "app.currency.country";

	public static final String NOMBRE_COL_IMSI = "app.nom.imsi";
	public static final String NOMBRE_COL_MSISDN = "app.nom.msisdn";
	public static final String NOMBRE_COL_CODSAL = "app.nom.codsal";
	public static final String NOMBRE_COL_DESSAL = "app.nom.dessal";

	// METODOS
	public static final String METODO_PROFILE = "app.nom.metodo.profile";
	public static final String METODO_CHANGE_PLAN = "app.nom.metodo.change";
	public static final String METODO_CREATE_USER = "app.nom.metodo.create.user";
	public static final String METODO_UPDATE_USER = "app.nom.metodo.update.user";
	public static final String METODO_UNPROVISIONING = "app.nom.metodo.unprovisioning";
	public static final String METODO_PROVISIONING = "app.nom.metodo.provisioning";
	public static final String METODO_DELETE_USER = "app.nom.metodo.delete.user";
	public static final String METODO_QUERY_USER = "app.nom.metodo.query.user";
	public static final String METODO_BUY_PRODUCT = "app.nom.metodo.buy.product";
	public static final String METODO_CHANGE_SIM = "app.nom.metodo.change.sim";

	public static final String END_POINT_WS_NOTIFICACION = "app.endpoint.notificacion";

	public static final String APP_ID_EMPRESA = "app.codigo.empresa";
	public static final String APP_CONSTANTE_VERDADERO = "app.constante.verdadero";
	public static final String APP_CONSTANTE_FALSO = "app.constante.falso";

	public static final String ENTER_PR_ID = "app.enter.pr.id";
	public static final String ICCID_DEFAULT = "app.iccid.default";
	public static final String VC_APROVISIONADO_EXITOSO = "app.vc.aprov.exitoso";
	public static final String ESTADO_ACTIVO = "app.estado.activo";
	public static final String ESTADO_APROVISIONADO = "app.estado.aprovisionado";
	public static final String ESTADO_NO_APROVISIONADO = "app.estado.no.aprovisionado";
	public static final String ESTADO_PENDIENTE = "app.estado.pendiente";
	public static final String ESTADO_INACTIVO = "app.estado.inactivo";

	public static final String RESPONSE_BUY_PRODUCT_OK = "app.response.buy.product.ok";
	public static final String ACTIVACION_EXITOSA = "app.activacion.exitosa";
	public static final String RESPONSE_CHANGE_SIM_OK = "app.response.change.sim.ok";

	public static final String METODO_GET_PRODUCT = "app.nom.metodo.get.product";
	public static final String RESPONSE_GET_PRODUCT_OK = "app.response.get.product.ok";

	public static final String METODO_CANCEL_PRODUCT = "app.nom.metodo.cancel.product";
	public static final String PROCEDURE_CANCEL_PRODUCT = "app.sql.procedure.cancel.product";
	public static final String FORMATO_FECHA = "app.formato.fecha.cancela";
	public static final String FORMATO_FECHA_ORDERID = "app.formato.fecha.orderid";
	public static final String FORMATO_FECHA_COMCORP = "app.formato.fecha.activa.pase";

	public static final String NM_GENERA_EVENTO = "app.numero.genera.evento.activacion";
	public static final String NM_RENOVACION = "app.numero.renovacion.activacion";
	public static final String NM_COMMIT = "app.numero.commit.activacion";
	public static final String PIPE = "|";
	public static final String PASES_ACTIVAR = "app.pases.activar";
	public static final String PASES_INSERTAR = "app.pases.insertar";
	public static final String PASES_EXITOSO = "app.pases.exitoso";

	// constantes get product
	public static final String GRUPO_PRODUCTO = "app.grupo.producto";
	public static final String HORAS_PRODUCTO = "app.horas.producto";
	public static final String PRODUCTO_ROAMING = "app.producto.roaming";

	// constantes cancel product
	public static final String CANCEL_TIPO = "app.cancel.tipo";
	public static final String CANCEL_COMMIT = "app.cancel.commit";
	public static final String RUTA_ARCHIVO_UUID = "app.archivo.uuid";
	// Falga para guardar registro en el cap
	public static final String STR_CERO = "app.str.cero";
	public static final String NOTIFICACION_ACTIVACION = "app.notificacion.activacion";
	public static final String NOTIFICACION_ACTIVACION_SISTEMA = "app.notificacion.activacion.sistema";
	public static final String NOTIFICACION_ACTIVACION_ESTADO = "app.notificacion.activacion.estado";
	public static final String NOTIFICACION_ACTIVACION_CODIGO = "app.notificacion.activacion.codigo";
	public static final String NOTIFICACION_ACTIVACION_MENSAJE = "app.notificacion.activacion.mensaje";
	public static final String SLEEP_THREAD = "app.sleep.thread.provisioning";

	public static final String SEPARADOR_FECHA_COMCORP = "app.comcorp.fecha.separador";
	public static final String ACTUALIZAR_CONTROL_PASE = "app.sql.actualizar.control.pase";
	public static final String INSERTAR_CONTROL_PASE = "app.sql.insertar.control.pase";
	public static final String CANTIDAD_REINTENTOS_CONTROL = "app.cantidad.reintentos.control";
	public static final String CANTIDAD_TIEMPO_REINTENTOS_CONTROL = "app.cantidad.tiempo.reintentos.control";
	public static final String RTA_OK_CONTROL = "app.respuesta.ok";
	public static final String FORMATO_FECHA_INFOPASE = "app.formato.fecha.infopase";
	public static final String TIPO_ACTUALIZACION = "app.tipo.actualizacion";
	public static final String BUY_PRODUCT_FACTOR_MB = "app.buy.product.factor.mb";

	public static final String CONSULTA_DATOS_CLIENTE = "sql.consulta.datos.cliente";
	public static final String TICKLER_SQL = "sql.crea.tickler";
	public static final String TICKLER_CODE_BUY = "app.tickler.code.buy";
	public static final String TICKLER_CODE_CANCEL = "app.tickler.code.cancel";
	public static final String TICKLER_DESC_CORTA_BUY = "app.tickler.desc.corta.buy";
	public static final String TICKLER_DESC_LARGA_BUY = "app.tickler.desc.larga.buy";
	public static final String TICKLER_DESC_CORTA_CANCEL = "app.tickler.desc.corta.cancel";
	public static final String TICKLER_DESC_LARGA_CANCEL = "app.tickler.desc.larga.cancel";
	public static final String TICKLER_UPDATE = "app.tickler.update";
	public static final String TICKLER_OK = "app.tickler.ok";
	public static final String SQL_ACTUALIZA_PASE_COMCORP = "app.sql.actualiza.pase.comcorp";
	public static final String ACTUALIZA_PASE_COMCORP_OK = "app.actualiza.pase.ok";
	public static final String REINTENTOS_ACTUALIZA_HILO_COMCORP = "app.reintentos.actualiza.pase.comcorp";
	public static final String SLEEP_HILO_ACTUALIZA_PASE = "app.sleep.hilo.actualiza.pase.comcorp";
	/* C28722 */
	public static final String PRC_APROVISIONA_EXITOSO_27 = "app.codigo.pl.ok-27";
	public static final String ICCID_ALREADY_IN_USE = "907";
	public static final String NOTIFICACION_DESACTIVACION = "app.notificacion.unprovisioning";
	public static final String NOTIFICACION_DESACTIVACION_ESTADO = "app.notificacion.desactivacion.estado";
	public static final String PRC_DESAPROVISIONA_EXITOSO_27 = "app.codigo.desact.pl.ok-27";
	public static final String LINE_IS_ALREADY_CANCELLED = "905";

	public static final String PRC_DESAPROVISIONA_EXITOSO_07 = "app.codigo.pl.ok-07";
	public static final String PRC_DESAPROVISIONA_EXITOSO = "app.prc.desaprovisiona.exito";
	public static final String ERROR_DESACT = "904";

	public static final String NOTIFICACION_CHANGEPLAN = "app.notificacion.changeplan";
	public static final String NOTIFICACION_CHANGEPLAN_SISTEMA = "app.notificacion.changeplan.sistema";
	public static final String NOTIFICACION_CHANGEPLAN_ESTADO = "app.notificacion.changeplan.estado";
	public static final String PRC_CHANGEPLAN_EXITOSO_16 = "app.codigo.pl.ok-16";
	public static final String PRC_CHANGEPLAN_EXITOSO = "app.prc.changeplan.exito";
	public static final String ALREADY_HAS_THE_PLANID = "904";
	public static final String ERROR_CHANGEPLAN = "907";

	public static final String NOTIFICACION_CHANGESIM = "app.notificacion.changesim";
	public static final String NOTIFICACION_CHANGESIM_SISTEMA = "app.notificacion.changesim.sistema";
	public static final String NOTIFICACION_CHANGESIM_ESTADO = "app.notificacion.changesim.estado";
	public static final String PRC_CHANGESIM_EXITOSO = "app.prc.changesim.exito";
	public static final String ERROR_CHANGESIM = "908";

	public static final String COUNTRIES_PROVISIONING = "app.nom.countries.provisioning";
	public static final String SERVICE_NAMES_PROVISIONING = "app.nom.service.names.provisioning";
	public static final String COUNTRIES_UNPROVISIONING = "app.nom.countries.unprovisioning";
	public static final String SERVICE_NAMES_UNPROVISIONING = "app.nom.service.names.unprovisioning";
	public static final String COUNTRIES_CHANGEPLAN = "app.nom.countries.changeplan";
	public static final String SERVICE_NAMES_CHANGEPLAN = "app.nom.service.names.changeplan";
	public static final String COUNTRIES_CHANGESIM = "app.nom.countries.changesim";
	public static final String SERVICE_NAMES_CHANGESIM = "app.nom.service.names.changesim";
	public static final String PROVIDERID_CHANGESIM = "app.nom.providerid.changesim";
	public static final String IMSI_DEFAULT = "app.imsi.default";
	public static final String TMCODE_DEFAULT = "app.tmcode.default";
	public static final String NOTIFICACION_DESACTIVACION_SISTEMA = "app.desactivacion.sistema";

	/* FASE III */
	public static final String COUNTRIES_QUERYPLANLIST = "app.nom.countries.queryplanlist";
	public static final String SERVICE_NAMES_QUERYPLANLIST = "app.nom.service.names.queryplanlist";
	public static final String METODO_QUERYPLANLIST = "app.nom.metodo.query.plan.list";
	public static final String PROFILE_TYPE = "app.profile.type.query.plan.list";
	public static final String PROFILE_TYPE_VALOR = "app.profile.type.valor.query.plan.list";
	public static final String SERVICE_NAMES_GETCOWNLOADPROFILESTATUS = "app.nom.service.names.downloadprofile";
	public static final String COUNTRIES_GETCOWNLOADPROFILESTATUS = "app.nom.countries.downloadprofile";
	public static final String PROVIDERID_GETCOWNLOADPROFILESTATUS = "app.nom.provider.downloadprofile";
	public static final String PRC_GETCOWNLOADPROFILESTATUS_EXITOSO_24 = "app.downloadprofile.ok.pl-24";
	public static final String PRC_GETCOWNLOADPROFILESTATUS_EXITOSO = "app.downloadprofile.ok.pl";
	public static final String ERROR_GETTING_THE_SUBSCRIBER_DATA = "904";
	public static final String CREATION_FAILED = "907";
	public static final String COUNTRIES_CREATE_USER = "app.nom.countries.create.user";
	public static final String SERVICE_NAMES_CREATE_USER = "app.nom.service.names.create.user";
	public static final String PROVIDERID_CREATE_USER = "app.nom.provider.create.user";
	public static final String OK_PL_CREATE_USER = "app.ok.pl.create.user";
	public static final String COUNTRIES_UPDATE_USER = "app.nom.countries.update.user";
	public static final String SERVICE_NAMES_UPDATE_USER = "app.nom.service.names.update.user";
	public static final String PROVIDERID_UPDATE_USER = "app.nom.provider.update.user";

	public static final String ERROR_PLAN_LIST = "904";
	public static final String ERROR_CHANGE_PLAN = "907";
	public static final String ERROR_CREATE_USER = "907";
	public static final String OK_PL_UPDATE_USER = "app.update.user.ok.pl";
	public static final String OK_PL_UPDATE_USER_1 = "app.update.user.ok.pl-1";
	public static final String ERROR_UPDATE_USER = "907";
	public static final String UPDATE_USER_NO_FOUND = "904";

	public static final String COUNTRIES_DELETE_USER = "app.nom.countries.delete.user";
	public static final String SERVICE_NAMES_DELETE_USER = "app.nom.service.names.delete.user";
	public static final String PROVIDERID_DELETE_USER = "app.nom.provider.delete.user";
	public static final String OK_PL_DELETE_USER = "app.delete.user.ok.pl";
	public static final String OK_PL_DELETE_USER_1 = "app.delete.user.ok.pl-1";
	public static final String ERROR_DELETE_USER = "907";
	public static final String DELETE_USER_NO_FOUND = "904";
	public static final String COUNTRIES_QUERY_USER = "app.nom.countries.query.user";
	public static final String SERVICE_NAMES_QUERY_USER = "app.nom.service.names.query.user";
	public static final String PROVIDERID_QUERY_USER = "app.nom.provider.query.user";
	public static final String OK_PL_QUERY_USER = "app.query.user.ok.pl";
	public static final String OK_PL_QUERY_USER_1 = "app.query.user.ok.pl-1";
	public static final String ERROR_QUERY_USER = "907";
	public static final String QUERY_USER_NO_FOUND = "904";
	public static final String COUNTRIES_BUY_PRODUCT = "app.nom.countries.buy.product";
	public static final String SERVICE_NAMES_BUY_PRODUCT = "app.nom.service.names.buy.product";
	public static final String PROVIDERID_BUY_PRODUCT = "app.nom.provider.buy.product";
	public static final String ERROR_BUY_PRODUCT_NO_PRODUCTS = "904";
	public static final String ERROR_BUY_PRODUCT_COMCORP = "907";
	public static final String PL_BUY_PRODUCT_2 = "app.pl.buy.product.-2";
	public static final String ERROR_BUY_PRODUCT_INVALID_PRODUCT = "908";

	public static final String COUNTRIES_GET_PRODUCT = "app.nom.countries.get.product";
	public static final String SERVICE_NAMES_GET_PRODUCT = "app.nom.service.names.get.product";
	public static final String PROVIDERID_GET_PRODUCT = "app.nom.provider.get.product";

	public static final String OK_PL_GET_PRODUCT = "app.ok.pl.get.product";
	public static final String RESPUESTA_ERROR_GET_PRODUCT = "904";
	public static final String NO_PRODUCTS_AVAILABLE = "907";

	public static final String COUNTRIES_CANCEL_PRODUCT = "app.nom.countries.cancel.product";
	public static final String SERVICE_NAMES_CANCEL_PRODUCT = "app.nom.service.names.cancel.product";
	public static final String PROVIDERID_CANCEL_PRODUCT = "app.nom.provider.cancel.product";

	public static final String QUERY_USER_OPERATOR_ID_DEFAULT = "app.query.user.operator.default";
	public static final String QUERY_USER_ICCID_DEFAULT = "app.query.user.iccid.default";
	public static final String QUERY_USER_IMSI_DEFAULT = "app.query.user.imsi.default";
	public static final String PL_VALIDA_PASES_EXPIRADOS = "app.pl.valida.pases.expirados";
	public static final String PL_VALIDA_PASES_EXPIRADOS_PALABRA = "app.valida.pases.palabra";
	public static final String PL_VALIDA_PASES_EXPIRADOS_MESES_ATRAS = "app.valida.pases.meses";
	public static final String ENTERPRISEID_GET_PRODUCT = "app.nom.enterprise.id.get.product";
	public static final String FORMATO_FECHA_EFECTIVEDATE = "app.formato.effective.date";
	public static final String CURRENCY_CANCEL_PRODUCT = "app.currency.cancel.product";
	public static final String REFUNDAMOUNT_CANCEL_PRODUCT = "app.refund.cancel.product";
	public static final String VALOR_57 = "app.valor.57.msisdn";

	public static final String MAIN_BALANCE_GET_PRODUCT = "app.main.balance.get.product";
	public static final String SUBSCRIBERTYPE_GET_PRODUCT = "app.subscribertype.get.product";
	public static final String ZONA_HORARIA_GMT = "app.time.zone.effective.date";
	public static final String ZONA_HORARIA_ORDER_ID = "app.time.zone.oreder.id";
	public static final String ZONA_HORARIA_PASES_TYPE = "app.time.zone.pases.type";
	public static final String ZONA_HORARIA_PASES = "app.time.zone.pases";
	public static final String ZONA_HORARIA_COMCORP = "app.time.zone.comcorp";
	public static final String FORMATO_FECHA_TIMESTAMP = "app.formato.fecha.timestamp";
	
	public static final String PL_CONSULTA_MAESTRA_ACTIVA = "app.sql.consulta.maestra.activa";
	public static final String QUERY_TIME_OUT_CONSULTA_MAESTRA = "app.time.out.consulta.maestra.activa";
	public static final String OK_PL_CONSULTA_MAESTRA_ACTIVA = "app.ok.pl.consulta.maestra.activa";
	public static final String QUERY_TIME_OUT_MAKE_ACTIVATION = "app.time.out.make.activation";
	public static final String MAKE_ACTIVATION_PRODUCT_ID = "app.make.activation.product.id";
	public static final String MAKE_ACTIVATION_USER_CODE = "app.make.activation.user.code";
	public static final String ACT_AUTOMATICA_CANAL = "app.act.automatica.canal";
	public static final String PL_ACTIVACION_AUTOMATICA = "app.pl.activacion.automatica";
	public static final String QUERY_TIME_OUT_PRC_ACT_AUTOMATICA = "app.time.out.act.automatica";
	public static final String OK_PL_ACT_AUTOMATICA = "app.ok.pl.act.automatica";
	public static final String MAKE_ACTIVATION_INCLUDE_MASTER_CODE = "app.poliedro.include.master.code";
	public static final String END_POINT_WS_POLIEDRO = "app.endpoint.make.activation";
	public static final String REINTENTOS_PROVISIONING = "app.reintentos.provisioning";
	public static final String SLEEP_INTENTOS = "app.sleep.intentos";
	
	public static final String CONTENT_LEGTH = "Content-Length";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String XML_UTF8 = "text/xml; charset=utf-8";
	public static final String SOAP_ACTION = "SOAPAction";
	public static final String POST = "POST";
	public static final String METODO_PROVISIONING_POLIEDRO = "app.soapaction.poliedro";
	public static final String REQUEST_POLIEDRO_PROVISIONING = "app.request.poliedro.provisioning";
	public static final String DEALER_ID_PROVISIONING = "app.poliedro.dealer.id";
	public static final String MAKE_ACTIVATION_SELLER_ID = "app.poliedro.seller.id";
	public static final String MAKE_ACTIVATION_MSISDN = "app.poliedro.msisdn";
	public static final String MAKE_ACTIVATION_IMEI = "app.poliedro.imei.default";
	
	// delete user CR4
	public static final String BACKUP_CONTROL_PASE = "app.sql.backup.control.pase";
    public static final String DELETE_CONTROL_PASE = "app.sql.delete.control.pase";
    public static final String PRC_BACKUP_CTRL_PASE_EXITOSO = "app.prc.backup.ctrl.pase.exito";
    public static final String PRC_DELETE_CTRL_PASE_EXITOSO = "app.prc.delete.ctrl.pase.exito";
    public static final String PRC_ACTESTADO_SUSCRIPTOR_EXITOSO = "app.sql.actestado.suscriptor.exito";
    public static final String TIEMPO_VIGENCIA_CTRL_PASE_BACKUP = "app.tiempo.vigencia.tbl.ctrl.pase.backup";
    
	public static final String EXITOSO_MAKE_ACTIVATION = "app.poliedro.ok.make.activation";
	public static final String TIPO_PERFIL = "app.tipo.perfil";
	public static final String PARAMETRO_PERFIL = "app.parametro.perfil";
	public static final String NOMBRE_COL_TMCODE = "app.nom.tmcode";
	
	//para extender respuesta de queryPlanList
	public static final String LLAVE_PROMOTION = "app.llave.promotion";
	public static final String VALOR_TYPE_ES = "app.valor.type.es";
	public static final String CONSULTA_OFFER_MESSAGE = "app.consulta.offer.message";
	public static final String CONSULTA_PROFILE_TYPE = "app.consulta.profile.type";
}