package com.claro.hubiot.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.claro.hubiot.exceptions.InvalidParameterException;
import com.claro.hubiot.exceptions.ParameterEmptyException;
import com.claro.hubiot.exceptions.ParameterException;
import com.claro.hubiot.servicios.Memoria;
import com.claro.hubiot.util.Constantes;

import co.com.globalhitss.util.configuracion.Propiedades;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unProvisioningRequest", propOrder = {
    "eid",
    "iccid",
    "msisdn",
    "imsi",
    "enterpriseId",
    "correlatorId",
    "providerId",
    "serviceName",
    "version",
    "country",
    "planId",
    "extensionInfo"
})
public class UnProvisioningRequest {
	private static Propiedades prop = Propiedades.getInstance();
    @XmlElement(required = true)
    private String eid;
    @XmlElement(required = true)
    private String iccid;
    @XmlElement(required = true)
    private String msisdn;
    @XmlElement(required = true)
    private String imsi;
    @XmlElement(required = true)
    private String enterpriseId;
    @XmlElement(required = true)
    private String correlatorId;
    @XmlElement(required = true)
    private String providerId;
    @XmlElement(required = true)
    private String serviceName;
    @XmlElement(required = true)
    private String version;
    @XmlElement(required = true)
    private String country;
    @XmlElement(required = true)
    private String planId;
    private List<NamedParameter> extensionInfo;

    /**
     * Obtiene el valor de la propiedad eid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEid() {
        return eid;
    }

    /**
     * Define el valor de la propiedad eid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEid(String value) {
        this.eid = value;
    }

    /**
     * Obtiene el valor de la propiedad iccid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIccid() {
        return iccid;
    }

    /**
     * Define el valor de la propiedad iccid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIccid(String value) {
        this.iccid = value;
    }

    /**
     * Obtiene el valor de la propiedad msisdn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * Define el valor de la propiedad msisdn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsisdn(String value) {
    	
    	this.msisdn = value.length() == 10 ? prop.obtenerPropiedad(Constantes.CODIGO_PAIS)+value : value;
    }

    /**
     * Obtiene el valor de la propiedad imsi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * Define el valor de la propiedad imsi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImsi(String value) {
        this.imsi = value;
    }

    /**
     * Obtiene el valor de la propiedad enterpriseId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnterpriseId() {
        return enterpriseId;
    }

    /**
     * Define el valor de la propiedad enterpriseId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnterpriseId(String value) {
        this.enterpriseId = value;
    }

    /**
     * Obtiene el valor de la propiedad correlatorId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrelatorId() {
        return correlatorId;
    }

    /**
     * Define el valor de la propiedad correlatorId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrelatorId(String value) {
        this.correlatorId = value;
    }

    /**
     * Obtiene el valor de la propiedad providerId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * Define el valor de la propiedad providerId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderId(String value) {
        this.providerId = value;
    }

    /**
     * Obtiene el valor de la propiedad serviceName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Define el valor de la propiedad serviceName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceName(String value) {
        this.serviceName = value;
    }

    /**
     * Obtiene el valor de la propiedad version.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Define el valor de la propiedad version.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Obtiene el valor de la propiedad country.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Define el valor de la propiedad country.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Obtiene el valor de la propiedad planId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanId() {
        return planId;
    }

    /**
     * Define el valor de la propiedad planId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanId(String value) {
        this.planId = value;
    }

   
    public List<NamedParameter> getExtensionInfo() {
        if (extensionInfo == null) {
            extensionInfo = new ArrayList<NamedParameter>();
        }
        return this.extensionInfo;
    }
    
    /**
	 * Valida que el request contenga los campos necesarios para el servicio
	 * @return
	 */
	public Boolean esValido(String countryProp, String serviceNameProp)throws ParameterException, ParameterEmptyException, InvalidParameterException{

		if ( eid == null ||  iccid == null ||  enterpriseId==null ||  correlatorId==null ||  providerId==null ||  serviceName==null || version==null ||  country==null 
				|| planId==null) {
			throw new ParameterException();
		}

		if(eid.isEmpty() || iccid.isEmpty() || enterpriseId.isEmpty() || tieneEspacios(enterpriseId)|| correlatorId.isEmpty() || tieneEspacios(correlatorId) ||
				providerId.isEmpty() || tieneEspacios(providerId) || serviceName.isEmpty() || tieneEspacios(serviceName) || version.isEmpty() 
				|| country.isEmpty() || tieneEspacios(country) || planId.isEmpty()) {
			throw new ParameterEmptyException();
		}
		if(this.msisdn!=null) {
			this.msisdn = this.msisdn.length() > 10 ? this.msisdn.substring(this.msisdn.length() - 10) : this.msisdn;
		}	

		if(!Memoria.existeOferta(new LlavePlanes(providerId, enterpriseId))) {
			throw new InvalidParameterException();
		}
		if(!countryProp.contains("|" + country + "|") || !serviceNameProp.contains("|" + serviceName + "|")) {
			throw new InvalidParameterException();
		}
		return true;
	}

	public boolean tieneEspacios (String cadena) {
		int longitudInicial = 0;
		int longitudFinal = 0;
		longitudInicial = cadena.length();
		longitudFinal= cadena.replaceAll(" ", "").length();
		if(longitudInicial != longitudFinal) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<UnProvisioningRequest><");
		if (eid != null) {
			builder.append("eid>");
			builder.append(eid);
			builder.append("</eid><");
		}
		if (iccid != null) {
			builder.append("iccid>");
			builder.append(iccid);
			builder.append("</iccid><");
		}
		if (msisdn != null) {
			builder.append("msisdn>");
			builder.append(msisdn);
			builder.append("</msisdn><");
		}
		if (imsi != null) {
			builder.append("imsi>");
			builder.append(imsi);
			builder.append("</imsi><");
		}
		if (enterpriseId != null) {
			builder.append("enterpriseId>");
			builder.append(enterpriseId);
			builder.append("</enterpriseId><");
		}
		if (correlatorId != null) {
			builder.append("correlatorId>");
			builder.append(correlatorId);
			builder.append("</correlatorId><");
		}
		if (providerId != null) {
			builder.append("providerId>");
			builder.append(providerId);
			builder.append("</providerId><");
		}
		if (serviceName != null) {
			builder.append("serviceName>");
			builder.append(serviceName);
			builder.append("</serviceName><");
		}
		if (version != null) {
			builder.append("version>");
			builder.append(version);
			builder.append("</version><");
		}
		if (country != null) {
			builder.append("country>");
			builder.append(country);
			builder.append("</country><");
		}
		if (planId != null) {
			builder.append("planId>");
			builder.append(planId);
			builder.append("</planId><");
		}
		if (extensionInfo != null) {
			builder.append("extensionInfo>");
			builder.append(extensionInfo);
			builder.append("</extensionInfo");
		}
		builder.append("></UnProvisioningRequest>");
		return builder.toString();
	}

    
}
