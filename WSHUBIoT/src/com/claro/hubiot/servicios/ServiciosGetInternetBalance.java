package com.claro.hubiot.servicios;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.claro.hubiot.database.ServiciosBD;
import com.claro.hubiot.dto.BuyProductRequest;
import com.claro.hubiot.dto.BuyProductResponse;
import com.claro.hubiot.dto.CancelProductRequest;
import com.claro.hubiot.dto.CancelProductResponse;
import com.claro.hubiot.dto.PasesType;
import com.claro.hubiot.dto.ProductType;
import com.claro.hubiot.util.Constantes;

import co.com.globalhitss.util.configuracion.ParametrosIniciales;
import co.com.globalhitss.util.configuracion.Propiedades;


public class ServiciosGetInternetBalance extends Thread {

	private static Propiedades prop = Propiedades.getInstance();
	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);

	private BuyProductRequest requestBuy;
	private BuyProductResponse responBuy;
	private TreeMap<String,ProductType> productosBuy=null;
	private CancelProductRequest requestCan;
	private CancelProductResponse responCan;
	private String consecPase;
	private ParametrosIniciales ini;
	
	
	public ParametrosIniciales getIni() {
		return ini;
	}

	public void setIni(ParametrosIniciales ini) {
		this.ini = ini;
	}

	public String getConsecPase() {
		return consecPase;
	}

	public void setConsecPase(String consecPase) {
		this.consecPase = consecPase;
	}

	public CancelProductRequest getRequestCan() {
		return requestCan;
	}

	public void setRequestCan(CancelProductRequest requestCan) {
		this.requestCan = requestCan;
	}

	public CancelProductResponse getResponCan() {
		return responCan;
	}

	public void setResponCan(CancelProductResponse responCan) {
		this.responCan = responCan;
	}

	public BuyProductResponse getResponse() {
		return responBuy;
	}

	public void setResponse(BuyProductResponse response) {
		this.responBuy = response;
	}

	private PasesType infopase;
	private int metodo;


	public PasesType getInfopase() {
		return infopase;
	}

	public int getMetodo() {
		return metodo;
	}

	public void setMetodo(int metodo) {
		this.metodo = metodo;
	}

	public void setInfopase(PasesType infopase) {
		this.infopase = infopase;
	}

	public BuyProductRequest getRequest() {
		return requestBuy;
	}

	public void setRequest(BuyProductRequest request) {
		this.requestBuy = request;
	}
	/**
	 * Metodo que realiza la logica de la aplicacion
	 */
	public void run() {
		ThreadContext.put("UUID", Long.toString(ini.getUuid()));
		logger.info(">>>>> INICIA HILO PARA REGISTRO EN TABLA DE CONTROL <<<<<>");
		if (metodo == 1) { 
			procesarInsert();
		}
		else if(metodo ==2 ) {
			procesarUpdate();
		}

		logger.info(">>>>> TERMINA HILO PARA REGISTRO EN TABLA DE CONTROL <<<<<>");
	}

	private void procesarUpdate() {
		
		int cantidadReintentos = 0;
		int reintentosPermitidos = Integer.parseInt(prop.obtenerPropiedad(Constantes.CANTIDAD_REINTENTOS_CONTROL));

		while (cantidadReintentos <= reintentosPermitidos) {
			try {
				
				logger.info("Parametros enviados al PL-->"
						
				+  " msisdn: " + requestCan.getMsisdn()
				+ ", proveedor: " + requestCan.getProviderId()
				+ ", empresa:" + prop.obtenerPropiedad(Constantes.ENTER_PR_ID)
				+ ", tipoUpdate:" + prop.obtenerPropiedad(Constantes.TIPO_ACTUALIZACION)
				);
				

				Integer rta = ServiciosBD.actualizarControlPase(
						//msisdn, proveedor, empresa, consecActivacion, tipo_actualizacion
						requestCan.getMsisdn(),
						requestCan.getProviderId(),
						//prop.obtenerPropiedad(Constantes.ENTER_PR_ID),
						null,
						prop.obtenerPropiedad(Constantes.TIPO_ACTUALIZACION)
						);

				if (rta != null && prop.obtenerPropiedad(Constantes.RTA_OK_CONTROL).equals(""+rta)) {
					break;
				}
				Thread.sleep(Integer.parseInt(prop.obtenerPropiedad(Constantes.CANTIDAD_TIEMPO_REINTENTOS_CONTROL)));
			} catch (Exception e) {
				logger.error("Error inesperado procesando la actualizacion de control pase", e);
			}finally {
				cantidadReintentos ++;
			}
		}
		
	}

	private void procesarInsert() {

		int cantidadReintentos = 0;
		int reintentosPermitidos = Integer.parseInt(prop.obtenerPropiedad(Constantes.CANTIDAD_REINTENTOS_CONTROL));

		while (cantidadReintentos <= reintentosPermitidos) {
			try {
				SimpleDateFormat sdf1 = new SimpleDateFormat(prop.obtenerPropiedad(Constantes.FORMATO_FECHA_TIMESTAMP));				
				SimpleDateFormat sdf2 = new SimpleDateFormat(prop.obtenerPropiedad(Constantes.FORMATO_FECHA_COMCORP));
				
				Date date = sdf2.parse(infopase.getFechaActivacion());
				infopase.setFechaActivacion(sdf1.format(date));
				
				date = sdf2.parse(infopase.getFechaexpiracion());
				infopase.setFechaexpiracion(sdf1.format(date));

				
				java.sql.Timestamp  fechaActivacion = Timestamp.valueOf(infopase.getFechaActivacion());
				java.sql.Timestamp  fechaFinal = Timestamp.valueOf(infopase.getFechaexpiracion());
				
				sdf1.setTimeZone(TimeZone.getTimeZone(prop.obtenerPropiedad(Constantes.ZONA_HORARIA_PASES)));
				fechaActivacion = Timestamp.valueOf(sdf1.format(fechaActivacion));
				fechaFinal = Timestamp.valueOf(sdf1.format(fechaFinal));
				
				String bytes = productosBuy.get(requestBuy.getProductId()).getIncludedBytes();
				Double mb = Double.parseDouble(bytes) / Double.parseDouble(prop.obtenerPropiedad(Constantes.BUY_PRODUCT_FACTOR_MB));
				logger.info("Parametros enviados al PL-->"
				
				+  " Min: " + requestBuy.getMsisdn()
				+ ", ProviderId: " + requestBuy.getProviderId()
				+ ", EnterpriseId:" + prop.obtenerPropiedad(Constantes.ENTER_PR_ID)
				+ ", PaseId:" + infopase.getSecuencia()
				+ ", OrdenId:" + responBuy.getOrdenId()
				+ ", ConsecutivoActivacion:" + infopase.getSecuencia()
				+ ", DescripcionPase:" + productosBuy.get(requestBuy.getProductId()).getShortName()
				+ ", FechaCompraPase:" + fechaActivacion
				+ ", FechaActivacion:" + null
				+ ", MbIncluidosPase:" + String.format("%.3f", mb)
				+ ", FechaFinalPase:" + fechaFinal
				);
				
				Integer rta = ServiciosBD.insertarControlPase(
						requestBuy.getMsisdn(),
						requestBuy.getProviderId(),
						prop.obtenerPropiedad(Constantes.ENTER_PR_ID),
						"" + infopase.getSecuencia(),
						responBuy.getOrdenId(), 
						"" + infopase.getSecuencia(),
						productosBuy.get(requestBuy.getProductId()).getShortName(),
						fechaActivacion,
						null,
						new Double(mb), 
						fechaFinal
						);

				if (rta != null && prop.obtenerPropiedad(Constantes.RTA_OK_CONTROL).equals(""+rta)) {
					break;
				}
				
				Thread.sleep(Integer.parseInt(Constantes.CANTIDAD_TIEMPO_REINTENTOS_CONTROL));
			} catch (Exception e) {
				logger.error("Error inesperado procesando la inserción de control pase", e);
			}
			finally {
				cantidadReintentos ++;
			}
		}

	}

	public TreeMap<String, ProductType> getProductos() {
		return productosBuy;
	}

	public void setProductos(TreeMap<String, ProductType> productos) {
		this.productosBuy = productos;
	}


}
