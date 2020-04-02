package com.claro.hubiot.servicios;

import com.claro.hubiot.dto.BuyProductRequest;
import com.claro.hubiot.dto.BuyProductResponse;
import com.claro.hubiot.dto.CancelProductRequest;
import com.claro.hubiot.dto.CancelProductResponse;
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

/**
 * Interfaz que define los servicios que se exponene en el WS ....
 * @author AUTOR
 *
 */
public interface IServicios {

	/**
	 * Servicio encargado de ....
	 */
	public QueryPlanListResponse consultaPlanes(String username, String Password,QueryPlanListRequest request);

	public ProvisioningResponse  provisioning(String callback,String username, String Password, ProvisioningRequest provisioningRequest);

	public ChangePlanResponse changePlan(String callback,String username, String Password, ChangePlanRequest changePlanRequest);

	public GetDownloadProfileStatusResponse getDownloadProfileStatus(String callback,String username, String Password, GetDownloadProfileStatusRequest getDownloadProfileStatusRequest);

	public UnProvisioningResponse unProvisioning(String callback,String username, String Password,UnProvisioningRequest request);
	
	public CreateUserResponse createUser(String callback,String username, String Password, CreateUserRequest request);
	
	public UpdateUserResponse updateUser(String callback,String username, String Password, UpdateUserRequest request);
	
	public DeleteUserResponse deleteUser(String callback,String username, String Password, DeleteUserRequest request);
	
	public QueryUserResponse queryUser(String callback,String username, String Password, QueryUserRequest request);
	
	public BuyProductResponse buyProduct(String callback, String username, String Password, BuyProductRequest request);
	
	public ChangeSimResponse changeSim(String callback, String username, String Password, ChangeSimRequest request);
	
	public GetProductsResponse getProduct(String callback, String username, String Password, GetProductsRequest request);

	public CancelProductResponse cancelProduct(String callback, String usu, String pass, CancelProductRequest req);
}
