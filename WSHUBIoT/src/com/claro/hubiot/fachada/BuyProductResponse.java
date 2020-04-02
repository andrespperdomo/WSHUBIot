package com.claro.hubiot.fachada;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement( name="buyProductResponse" )
public class BuyProductResponse {
	
	@XmlElement( name="buyProductResponse" )
	private com.claro.hubiot.dto.BuyProductResponse buyProductResponse;

	public com.claro.hubiot.dto.BuyProductResponse getBuyProductResponse() {
		return buyProductResponse;
	}

	public void setBuyProductResponse(com.claro.hubiot.dto.BuyProductResponse buyProductResponse) {
		this.buyProductResponse = buyProductResponse;
	}
	
	@Override
	public String toString(){
      StringBuilder sb = new StringBuilder();
       if( buyProductResponse != null ){
    	  sb.append( "<BuyProductResponse>" );
    	  sb.append( buyProductResponse );
    	  sb.append( "</BuyProductResponse>" );  
       }
      return sb.toString();
	}

}
