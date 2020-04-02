package com.claro.hubiot.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.claro.hubiot.database.ServiciosBD;
import com.claro.hubiot.dto.LlaveMensajes;
import com.claro.hubiot.dto.LlaveParametros;
import com.claro.hubiot.dto.LlavePlanes;
import com.claro.hubiot.dto.Mensaje;
import com.claro.hubiot.dto.Parametro;
import com.claro.hubiot.dto.PlanInfo;
import com.claro.hubiot.util.Constantes;

import co.com.globalhitss.util.configuracion.Propiedades;


public class Memoria {

	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);
	private static Propiedades prop = Propiedades.getInstance();

	private static Memoria instance;
	private static TreeMap<LlavePlanes,ArrayList<PlanInfo>> planes;
	private static TreeMap<LlaveMensajes,Mensaje> mensajes;
	private static TreeMap<LlaveParametros,Parametro> parametros;

	private Memoria() {
		Memoria.planes = new TreeMap<LlavePlanes,ArrayList<PlanInfo>>();
		Memoria.mensajes = new TreeMap<LlaveMensajes,Mensaje>();
		Memoria.parametros = new TreeMap<LlaveParametros,Parametro>();
	}

	public static Memoria getInstance() {
		synchronized (Memoria.class) {
			if (instance == null){
				instance = new Memoria();
			} 
		}
		return instance;
	}

	public void configurarMemoria() {
		try {    
			Memoria.mensajes= getMensajesDB();
			Memoria.parametros= getParametrosDB();
			Memoria.planes = getPlanesDB(); 
			imprimeOfertaLog();
			ejecutaDepuracionPases();
		} catch (Exception e) {
			logger.error("Fallo el cargue en memoria", e);
		}
	}


	private void ejecutaDepuracionPases() {
		try {
			ServiciosBD.validaPasesExpirados();
		}catch (Exception e) {
			logger.error("Fallo el procedimineto para depurar los pases en SMSSERV");
		}
		
	}

	private static TreeMap<LlavePlanes,ArrayList<PlanInfo>> getPlanesDB (){
		TreeMap<LlavePlanes,ArrayList<PlanInfo>> planesinter = new TreeMap<>();
		ArrayList<PlanInfo> planesTmp= ServiciosBD.consultaPlanes(null);
		LlavePlanes llave;
		for (PlanInfo planInfo : planesTmp) {
			llave=new LlavePlanes(planInfo.getProviderId(),planInfo.getEnterpriceId());
			if(planesinter.containsKey(llave)){
				ArrayList<PlanInfo> planesfiltro = planesinter.get(llave);
				planesfiltro.add(modifcaParametros(planInfo));
			}else{
				ArrayList<PlanInfo> planesfiltro= new ArrayList<>();
				planesfiltro.add(modifcaParametros(planInfo));
				planesinter.put(llave, planesfiltro);
			}
		}
		return planesinter;
	}

	public static PlanInfo modifcaParametros(PlanInfo plan){
		LlaveParametros llave;
		Parametro param;
		llave=new LlaveParametros(prop.obtenerPropiedad(Constantes.NOMBRE_COL_PRODUCT),plan.getProductType().toUpperCase().trim());
		if(parametros.containsKey(llave)){
			param = parametros.get(llave);
			plan.setProductType(param.getValorFinal());
		}
		llave=new LlaveParametros(prop.obtenerPropiedad(Constantes.NOMBRE_COL_PLAN),plan.getPlanId().toUpperCase().trim());
		if(parametros.containsKey(llave)){
			param = parametros.get(llave);
			plan.setPlanId(param.getValorFinal());
		}
		return plan;
	}
	private static TreeMap<LlaveParametros,Parametro> getParametrosDB (){
		TreeMap<LlaveParametros,Parametro> parametrosinter = new TreeMap<>();
		ArrayList<Parametro> parametrosTmp= ServiciosBD.consultaParametros();
		LlaveParametros llave;
		Parametro param;
		for (Parametro parametro : parametrosTmp) {
			llave=new LlaveParametros(parametro.getParametroPl(),parametro.getValorParametro().toUpperCase().trim());
			if(parametrosinter.containsKey(llave)){
				param= parametrosinter.get(llave);
				param.setParametroPl(parametro.getParametroPl());
				param.setValorParametro(parametro.getValorParametro());
				param.setValorFinal(parametro.getValorFinal());
			}else{
				param= new Parametro();
				param.setParametroPl(parametro.getParametroPl());
				param.setValorParametro(parametro.getValorParametro());
				param.setValorFinal(parametro.getValorFinal());
			}
			parametrosinter.put(llave, param);
		}
		return parametrosinter;
	}

	private static TreeMap<LlaveMensajes,Mensaje> getMensajesDB (){
		TreeMap<LlaveMensajes,Mensaje> mensajesinter = new TreeMap<>();
		ArrayList<Mensaje> mensajesTmp= ServiciosBD.consultaMensajes();
		LlaveMensajes llave;
		Mensaje msj;
		for (Mensaje mensaje : mensajesTmp) {
			llave=new LlaveMensajes(mensaje.getNombrePl(),mensaje.getValorPl());
			if(mensajesinter.containsKey(llave)){
				msj= mensajesinter.get(llave);
				msj.setNombrePl(mensaje.getNombrePl());
				msj.setValorPl(mensaje.getValorPl());
				msj.setCodFinal(mensaje.getCodFinal());
				msj.setDescFinal(mensaje.getDescFinal());
			}else{
				msj= new Mensaje();
				msj.setNombrePl(mensaje.getNombrePl());
				msj.setValorPl(mensaje.getValorPl());
				msj.setCodFinal(mensaje.getCodFinal());
				msj.setDescFinal(mensaje.getDescFinal());
			}
			mensajesinter.put(llave, msj);
		}
		return mensajesinter;
	}

	public List<PlanInfo> obtenerPlanes(LlavePlanes llave){
		ArrayList<PlanInfo> planesfiltro = planes.get(llave);
		return planesfiltro;
	}

	public String obtenerPlanId(String tmCode){
		String planId="";
		Set<LlavePlanes> keys = planes.keySet();
		for(LlavePlanes key: keys){
			ArrayList<PlanInfo> planesfiltro = planes.get(key);
			for (PlanInfo planInfo : planesfiltro) {
				if(planInfo.getNmtmcode().equals(tmCode))
					planId=planInfo.getPlanId();
			}
		}

		return planId;
	}


	public static void guardaPlanes(LlavePlanes llave,List<PlanInfo> listaPlanes){
		planes.put(llave,(ArrayList<PlanInfo>)listaPlanes);
	}
	public static Mensaje obtenerMensaje(LlaveMensajes llave){
		return mensajes.get(llave);
	}
	public static Boolean existeOferta (LlavePlanes llave) {	
		return planes.containsKey(llave); 
	}


	public void recargarMemoria() {

		TreeMap<LlaveMensajes,Mensaje> mensajesTmp = getMensajesDB();
		TreeMap<LlaveParametros,Parametro> parametrosTmp = getParametrosDB();
		TreeMap<LlavePlanes,ArrayList<PlanInfo>> planesTemp = getPlanesDB();
		synchronized (Memoria.class) {
			if(!planesTemp.isEmpty()){
				Memoria.planes = planesTemp;
			}
			if(!mensajesTmp.isEmpty()){
				Memoria.mensajes = mensajesTmp;
			}
			if(!parametrosTmp.isEmpty()){
				Memoria.parametros=parametrosTmp;
			}
		}
		imprimeOfertaLog();
	}

	private static void imprimeOfertaLog(){
		logger.info("Oferta cargada");
		for(Map.Entry<LlavePlanes,ArrayList<PlanInfo>> entry : planes.entrySet()) {
			LlavePlanes key = entry.getKey();
			ArrayList<PlanInfo> value = entry.getValue();
			logger.info("Oferta para la empresa "+key.getEnterpriceId()+", proveedor "+key.getProviderId()+":");
			for (PlanInfo planInfo : value) {
				logger.info(planInfo.toString());
			}
		}
	}
}
