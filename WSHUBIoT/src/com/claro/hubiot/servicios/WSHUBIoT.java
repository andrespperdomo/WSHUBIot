package com.claro.hubiot.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.claro.hubiot.fachada.BuyProductRequest;
import com.claro.hubiot.fachada.BuyProductResponse;
import com.claro.hubiot.fachada.CancelProductRequest;
import com.claro.hubiot.fachada.CancelProductResponse;
import com.claro.hubiot.fachada.ChangePlanRequest;
import com.claro.hubiot.fachada.ChangePlanResponse;
import com.claro.hubiot.fachada.ChangeSimRequest;
import com.claro.hubiot.fachada.ChangeSimResponse;
import com.claro.hubiot.fachada.CreateUserRequest;
import com.claro.hubiot.fachada.CreateUserResponse;
import com.claro.hubiot.fachada.DeleteUserRequest;
import com.claro.hubiot.fachada.DeleteUserResponse;
import com.claro.hubiot.fachada.GetDownloadProfileStatusRequest;
import com.claro.hubiot.fachada.GetDownloadProfileStatusResponse;
import com.claro.hubiot.fachada.GetProductsRequest;
import com.claro.hubiot.fachada.GetProductsResponse;
import com.claro.hubiot.fachada.ProvisioningRequest;
import com.claro.hubiot.fachada.ProvisioningResponse;
import com.claro.hubiot.fachada.QueryPlanListRequest;
import com.claro.hubiot.fachada.QueryPlanListResponse;
import com.claro.hubiot.fachada.QueryUserRequest;
import com.claro.hubiot.fachada.QueryUserResponse;
import com.claro.hubiot.fachada.UnProvisioningRequest;
import com.claro.hubiot.fachada.UnProvisioningResponse;
import com.claro.hubiot.fachada.UpdateUserRequest;
import com.claro.hubiot.fachada.UpdateUserResponse;
import com.claro.hubiot.util.Constantes;
import com.claro.hubiot.util.TimerHUBIoT;
import co.com.globalhitss.util.configuracion.Configurador;
import co.com.globalhitss.util.configuracion.ConstantesUtil;

/**
 * Web Service que realiza ....
 * @author AUTOR
 *
 */
@Path("esb/services/")
public class WSHUBIoT {


	static {
		Configurador.configurar(Constantes.RUTA_ARCHIVO_PROPIEDADES, Constantes.LOGGER_PRINCIPAL, Constantes.APLICACION);
		TimerHUBIoT.iniciarTimer();
	}
	/**
	 * Servicio encargado de ...
	 * @param req
	 * @return
	 */
	@POST
	@Path("plan/queryPlanList")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public QueryPlanListResponse listaPaquetes(@HeaderParam("Username") String usu,@HeaderParam("Password") String pass , QueryPlanListRequest req){
		IServicios ser = new ServiciosImp();
		QueryPlanListResponse response = new QueryPlanListResponse();
		response.setResponse(ser.consultaPlanes(usu,pass,req.getRequest()));
		return response;
	}

	/**
	 * Servicio encargado de ...
	 * @param req
	 * @return
	 */
	@POST
	@Path("userprofile/provisioning")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public ProvisioningResponse provisioning(@HeaderParam("AspCallBackUrl") String callback,@HeaderParam("Username") String usu,@HeaderParam("Password") String pass , ProvisioningRequest req){
		IServicios ser = new ServiciosImp();
		ProvisioningResponse response = new ProvisioningResponse();
		response.setResponse((ser.provisioning(callback,usu,pass,req.getRequest())));
		return response;
	}

	/**
	 * Servicio encargado de ...
	 * @param req
	 * @return
	 */
	@POST
	@Path("plan/changePlan")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public ChangePlanResponse changePlan(@HeaderParam("AspCallBackUrl") String callback,@HeaderParam("Username") String usu,@HeaderParam("Password") String pass , ChangePlanRequest req){
		IServicios ser = new ServiciosImp();
		ChangePlanResponse  response = new ChangePlanResponse();
		response.setResponse(ser.changePlan(callback,usu,pass,req.getRequest()));
		return response;
	}

	/**
	 * Servicio encargado de ...
	 * @param req
	 * @return
	 */
	@POST
	@Path("userprofile/getDownloadProfileStatus")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public GetDownloadProfileStatusResponse getDownloadProfileStatus(@HeaderParam("AspCallBackUrl") String callback,@HeaderParam("Username") String usu,@HeaderParam("Password") String pass , GetDownloadProfileStatusRequest req){
		IServicios ser = new ServiciosImp();
		GetDownloadProfileStatusResponse response = new GetDownloadProfileStatusResponse();
		response.setResponse(ser.getDownloadProfileStatus(callback,usu,pass,req.getGetDownloadProfileStatusRequest()));
		return response ;
	}
	
	
	@POST
	@Path("userprofile/unProvisioning")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public UnProvisioningResponse unProvisioning(@HeaderParam("AspCallBackUrl") String callback,@HeaderParam("Username") String usu,@HeaderParam("Password") String pass , UnProvisioningRequest req){
		IServicios ser = new ServiciosImp();
		UnProvisioningResponse response = new UnProvisioningResponse();
		response.setResponse(ser.unProvisioning(callback,usu,pass,req.getRequest()));
		return response;
	}
	
	@POST
	@Path("user/createUser")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public CreateUserResponse createUser(@HeaderParam("AspCallBackUrl") String callback,@HeaderParam("Username") String usu,@HeaderParam("Password") String pass , CreateUserRequest req){
		IServicios ser = new ServiciosImp();
		CreateUserResponse response = new CreateUserResponse();
		response.setResponse(ser.createUser(callback,usu,pass,req.getRequest()));
		return response;
	}
	
	@POST
	@Path("user/updateUser")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public UpdateUserResponse updateUser(@HeaderParam("AspCallBackUrl") String callback,@HeaderParam("Username") String usu,@HeaderParam("Password") String pass , UpdateUserRequest req){
		IServicios ser = new ServiciosImp();
		UpdateUserResponse response = new UpdateUserResponse();
		response.setResponse(ser.updateUser(callback,usu,pass,req.getRequest()));
		return response;
	}
	
	@POST
	@Path("user/deleteUser")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public DeleteUserResponse deleteUser(@HeaderParam("AspCallBackUrl") String callback,@HeaderParam("Username") String usu,@HeaderParam("Password") String pass , DeleteUserRequest req){
		IServicios ser = new ServiciosImp();
		DeleteUserResponse response = new DeleteUserResponse();
		response.setResponse(ser.deleteUser(callback,usu,pass,req.getRequest()));
		return response;
	}
	
	@POST
	@Path("user/queryUser")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public QueryUserResponse queryUser(@HeaderParam("AspCallBackUrl") String callback,@HeaderParam("Username") String usu,@HeaderParam("Password") String pass , QueryUserRequest req){
		IServicios ser = new ServiciosImp();
		QueryUserResponse response = new QueryUserResponse();
		response.setResponse(ser.queryUser(callback,usu,pass,req.getRequest()));
		return response ;
	}
	
	
	
	@POST
	@Path("retail/buyProduct")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public BuyProductResponse buyProduct(@HeaderParam("AspCallBackUrl") String callback,@HeaderParam("Username") String usu,@HeaderParam("Password") String pass , BuyProductRequest req){
		IServicios ser = new ServiciosImp();
		BuyProductResponse response = new BuyProductResponse(); 
		response.setBuyProductResponse( ser.buyProduct( callback, usu, pass, req.getBuyProductRequest() ) );
		return response ;
	}
	
	@POST
	@Path("subscriber/sim/changeSim")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public ChangeSimResponse changeSim(@HeaderParam("AspCallBackUrl") String callback,@HeaderParam("Username") String usu,@HeaderParam("Password") String pass , ChangeSimRequest req){
		IServicios ser = new ServiciosImp();
		ChangeSimResponse response = new ChangeSimResponse(); 
        response.setChangeSimResponse( ser.changeSim( callback, usu, pass, req.getChangeSimRequest() ) );
		return response ;
	}
	
	/**
	 * Servicio encargado de obtener los productos
	 * @param req
	 * @return
	 */
	@POST
	@Path("retail/getProducts")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public GetProductsResponse getProduct(@HeaderParam("AspCallBackUrl") String callback,@HeaderParam("Username") String usu,@HeaderParam("Password") String pass , GetProductsRequest req){
		IServicios ser = new ServiciosImp();
		GetProductsResponse response = new GetProductsResponse(); 
		response.setResponse(ser.getProduct(callback, usu, pass, req.getRequest()));
		return response ;
	}
	
	/**
	 * Servicio encargado de cancelación de productos
	 * @param usuario: Usuario que realiza la solicitud
	 * @param psw: Contraseña de usuario
	 * @return: Mensaje informado si se realizo o no la cancelacion del producto
	 */
	@POST
	@Path("retail/cancelProduct")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public CancelProductResponse cancelProduct(@HeaderParam("AspCallBackUrl") String callback,@HeaderParam("Username") String usu,@HeaderParam("Password") String pass , CancelProductRequest req){
		IServicios ser = new ServiciosImp();
		CancelProductResponse response = new CancelProductResponse(); 
		response.setResponse(ser.cancelProduct(callback, usu, pass, req.getRequest()));
		return response ;
	}
	
	/**
	 * Servicio que realiza la actualizacion de las propiedades del sistema
	 * @param usuario: Usuario que realiza la solicitud
	 * @param psw: Contraseña de usuario
	 * @return: Mensaje informado si se realizo o no la actualizacion de las propiedades
	 */
	@GET
	@Path("resetPropiedades")
	public Response resetPropiedades(@QueryParam("usuario") String usuario, @QueryParam("psw")String psw){
		String respuesta=Configurador.resetPropiedades(Constantes.RUTA_ARCHIVO_PROPIEDADES, Constantes.LOGGER_PRINCIPAL, Constantes.APLICACION, usuario, psw);
		if (respuesta.equals(ConstantesUtil.DATOS_VAL_RESET)) {
			TimerHUBIoT.detenerTimer();
			TimerHUBIoT.iniciarTimer();
		}
		return Response.status(200).entity(respuesta).build();
	}
}
