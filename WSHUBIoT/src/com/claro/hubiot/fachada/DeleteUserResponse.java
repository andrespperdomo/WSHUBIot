package com.claro.hubiot.fachada;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeleteUserResponse {
	    @XmlElement(name = "deleteUserResponse")
		private com.claro.hubiot.dto.DeleteUserResponse  response;

		public com.claro.hubiot.dto.DeleteUserResponse getResponse() {
			return response;
		}

		public void setResponse(com.claro.hubiot.dto.DeleteUserResponse response) {
			this.response = response;
		}
	    
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("<DeleteUserResponse><");
			if (response != null) {
				builder.append("response>");
				builder.append(response);
				builder.append("</response");
			}
			builder.append("></DeleteUserResponse>");
			return builder.toString();
		}
}
