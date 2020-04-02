package com.claro.hubiot.fachada;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="buyProductRequest")
public class BuyProductRequest {
	
	
	@XmlElement(name = "buyProductRequest")
	private com.claro.hubiot.dto.BuyProductRequest buyProductRequest;

	public com.claro.hubiot.dto.BuyProductRequest getBuyProductRequest() {
		return buyProductRequest;
	}

	public void setBuyProductRequest(com.claro.hubiot.dto.BuyProductRequest buyProductRequest) {
		this.buyProductRequest = buyProductRequest;
	}
	
	@Override
	public String toString(){
	 StringBuilder sb = new StringBuilder();
	  if( buyProductRequest != null ){
		  sb.append( "<BuyProductRequest>" );
		  sb.append( buyProductRequest );
		  sb.append( "</BuyProductRequest>" );  
	  }
	 return sb.toString();
	}
	

}
