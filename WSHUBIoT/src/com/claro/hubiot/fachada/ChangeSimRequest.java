package com.claro.hubiot.fachada;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement( name="changeSimRequest" )
public class ChangeSimRequest {
	
	@XmlElement( name="changeSimRequest" )
	private com.claro.hubiot.dto.ChangeSimRequest changeSimRequest;

	public com.claro.hubiot.dto.ChangeSimRequest getChangeSimRequest() {
		return changeSimRequest;
	}

	public void setChangeSimRequest(com.claro.hubiot.dto.ChangeSimRequest changeSimRequest) {
		this.changeSimRequest = changeSimRequest;
	}
	
	public String toString(){
	  StringBuilder sb = new StringBuilder();
	   if( changeSimRequest != null ){
		  sb.append( "<ChangeSimRequest>" );
		  sb.append( changeSimRequest );
		  sb.append( "</ChangeSimRequest>" );
	   }
      return sb.toString();
	}

}
