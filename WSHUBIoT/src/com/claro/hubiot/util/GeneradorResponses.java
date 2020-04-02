package com.claro.hubiot.util;


import com.claro.hubiot.dto.BuyProductResponse;
import com.claro.hubiot.dto.CancelProductResponse;
import com.claro.hubiot.dto.ChangePlanResponse;
import com.claro.hubiot.dto.ChangeSimResponse;
import com.claro.hubiot.dto.CreateUserResponse;
import com.claro.hubiot.dto.DeleteUserResponse;
import com.claro.hubiot.dto.GetDownloadProfileStatusResponse;
import com.claro.hubiot.dto.GetProductsResponse;
import com.claro.hubiot.dto.LlaveMensajes;
import com.claro.hubiot.dto.Mensaje;
import com.claro.hubiot.dto.ProvisioningResponse;
import com.claro.hubiot.dto.QueryPlanListResponse;
import com.claro.hubiot.dto.QueryUserResponse;
import com.claro.hubiot.dto.UpdateUserResponse;
import com.claro.hubiot.dto.UnProvisioningResponse;
import com.claro.hubiot.servicios.Memoria;

import co.com.globalhitss.util.configuracion.Propiedades;

public class GeneradorResponses {

	private static final Propiedades prop = Propiedades.getInstance();

	/**
	 * 
	 * @param codigo
	 */
	public static void generarRespuesta(QueryPlanListResponse response, String codigo, Object... paramsMensaje) {
		LlaveMensajes llave = new LlaveMensajes("planList", codigo);
		Mensaje mensaje = Memoria.obtenerMensaje(llave);
		if(mensaje!=null){
			response.setResultCode(mensaje.getCodFinal());
			response.setResultMessage(mensaje.getDescFinal());		
		}
	}


	public static void generarRespuesta(GetDownloadProfileStatusResponse response, String codigo, Object... paramsMensaje) {
		LlaveMensajes llave = new LlaveMensajes(prop.obtenerPropiedad(Constantes.METODO_PROFILE), codigo);
		Mensaje mensaje = Memoria.obtenerMensaje(llave);
		if(mensaje!=null){
			response.setResultCode(mensaje.getCodFinal());
			response.setResultMessage(mensaje.getDescFinal());		
		}
	}

	public static void generarRespuesta(ChangePlanResponse response, String codigo, Object... paramsMensaje) {
		LlaveMensajes llave = new LlaveMensajes(prop.obtenerPropiedad(Constantes.METODO_CHANGE_PLAN), codigo);
		Mensaje mensaje = Memoria.obtenerMensaje(llave);
		if(mensaje!=null){
			response.setResultCode(Integer.parseInt(mensaje.getCodFinal()));
			response.setResultMessage(mensaje.getDescFinal());		
		}
	}

	public static void generarRespuesta(UnProvisioningResponse response, String codigo, Object... paramsMensaje) {
		LlaveMensajes llave = new LlaveMensajes(prop.obtenerPropiedad(Constantes.METODO_UNPROVISIONING), codigo);
		Mensaje mensaje = Memoria.obtenerMensaje(llave);
		if(mensaje!=null){
			response.setResultCode(Integer.parseInt(mensaje.getCodFinal()));
			response.setResultMessage(mensaje.getDescFinal());		
		}
	}

	public static void generarRespuesta(ProvisioningResponse response, String codigo, Object... paramsMensaje) {
		LlaveMensajes llave = new LlaveMensajes(prop.obtenerPropiedad(Constantes.METODO_PROVISIONING), codigo);
		Mensaje mensaje = Memoria.obtenerMensaje(llave);
		if(mensaje!=null){
			response.setResultCode(Integer.parseInt(mensaje.getCodFinal()));
			response.setResultMessage(mensaje.getDescFinal());		
		}
	}

	public static void generarRespuesta(CreateUserResponse response, String codigo) {
		LlaveMensajes llave = new LlaveMensajes(prop.obtenerPropiedad(Constantes.METODO_CREATE_USER), codigo);
		Mensaje mensaje = Memoria.obtenerMensaje(llave);
		if(mensaje!=null){
			response.setResultCode(Integer.parseInt(mensaje.getCodFinal()));
			response.setResultMessage(mensaje.getDescFinal());		
		}
	}
	public static void generarRespuesta(UpdateUserResponse response, String codigo) {
		LlaveMensajes llave = new LlaveMensajes(prop.obtenerPropiedad(Constantes.METODO_UPDATE_USER), codigo);
		Mensaje mensaje = Memoria.obtenerMensaje(llave);
		if(mensaje!=null){
			response.setResultCode(Integer.parseInt(mensaje.getCodFinal()));
			response.setResultMessage(mensaje.getDescFinal());		
		}
	}


	public static void generarRespuesta(DeleteUserResponse response, String codigo) {
		LlaveMensajes llave = new LlaveMensajes(prop.obtenerPropiedad(Constantes.METODO_DELETE_USER), codigo);
		Mensaje mensaje = Memoria.obtenerMensaje(llave);
		if(mensaje!=null){
			response.setResultCode(Integer.parseInt(mensaje.getCodFinal()));
			response.setResultMessage(mensaje.getDescFinal());		
		}
	}
	public static void generarRespuesta(QueryUserResponse response, String codigo) {
		LlaveMensajes llave = new LlaveMensajes(prop.obtenerPropiedad(Constantes.METODO_QUERY_USER), codigo);
		Mensaje mensaje = Memoria.obtenerMensaje(llave);
		if(mensaje!=null){
			response.setResultCode(Integer.parseInt(mensaje.getCodFinal()));
			response.setResultMessage(mensaje.getDescFinal());		
		}
	}
	
	public static void generarRespuesta( BuyProductResponse response, String codigo ){
		LlaveMensajes llave = new LlaveMensajes( prop.obtenerPropiedad(Constantes.METODO_BUY_PRODUCT ), codigo );
		Mensaje mensaje = Memoria.obtenerMensaje(llave);
		if(mensaje!=null){
			response.setResultCode( mensaje.getCodFinal() );
			response.setResultMessage(mensaje.getDescFinal());		
		}
	}
	
	public static void generarRespuesta( ChangeSimResponse response, String codigo ){
		LlaveMensajes llave = new LlaveMensajes( prop.obtenerPropiedad(Constantes.METODO_CHANGE_SIM ), codigo );
		Mensaje mensaje = Memoria.obtenerMensaje(llave);
		if(mensaje!=null){
			response.setResultCode( Integer.parseInt( mensaje.getCodFinal() ) );
			response.setResultMessage(mensaje.getDescFinal());		
		}
	}
	
	public static void generarRespuesta( GetProductsResponse response, String codigo ){
		LlaveMensajes llave = new LlaveMensajes( prop.obtenerPropiedad(Constantes.METODO_GET_PRODUCT ), codigo );
		Mensaje mensaje = Memoria.obtenerMensaje(llave);
		if(mensaje!=null){
			response.setResultCode( mensaje.getCodFinal() );
			response.setResultMessage(mensaje.getDescFinal());		
		}
		
	}


	public static void generarRespuesta(CancelProductResponse response, String codigo) {
		LlaveMensajes llave = new LlaveMensajes( prop.obtenerPropiedad(Constantes.METODO_CANCEL_PRODUCT ), codigo );
		Mensaje mensaje = Memoria.obtenerMensaje(llave);
		if(mensaje!=null){
			response.setResultCode( mensaje.getCodFinal() );
			response.setResultMessage(mensaje.getDescFinal());		
		}
	}
}
