package com.claro.hubiot.fachada;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "changeSimResponse" )
public class ChangeSimResponse {
	
	@XmlElement( name="changeSimResponse" )
	private com.claro.hubiot.dto.ChangeSimResponse changeSimResponse;

	public com.claro.hubiot.dto.ChangeSimResponse getChangeSimResponse() {
		return changeSimResponse;
	}

	public void setChangeSimResponse(com.claro.hubiot.dto.ChangeSimResponse changeSimResponse) {
		this.changeSimResponse = changeSimResponse;
	}
	
	@Override
	public String toString(){
	  StringBuilder sb = new StringBuilder();
	   if( changeSimResponse != null ){
		   sb.append( "<ChangeSimResponse>" );
		   sb.append( changeSimResponse );
		   sb.append( "</ChangeSimResponse>" );
	   }
	  return sb.toString(); 
	}
	

}
