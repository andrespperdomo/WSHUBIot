package com.claro.hubiot.dto;

public class LlavePlanes implements Comparable<LlavePlanes>{

	private String providerId;
	private String enterpriceId;

	public LlavePlanes(String providerId, String enterpriceId) {
		this.providerId = providerId;
		this.enterpriceId = enterpriceId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getEnterpriceId() {
		return enterpriceId;
	}

	public void setEnterpriceId(String enterpriceId) {
		this.enterpriceId = enterpriceId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enterpriceId == null) ? 0 : enterpriceId.hashCode());
		result = prime * result + ((providerId == null) ? 0 : providerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LlavePlanes other = (LlavePlanes) obj;
		if (enterpriceId == null) {
			if (other.enterpriceId != null)
				return false;
		} else if (!enterpriceId.equals(other.enterpriceId))
			return false;
		if (providerId == null) {
			if (other.providerId != null)
				return false;
		} else if (!providerId.equals(other.providerId))
			return false;
		return true;
	}

	@Override
	public int compareTo(LlavePlanes llave) {
		String temp = this.providerId + this.enterpriceId;
		String temp2 = llave.getProviderId() + llave.getEnterpriceId();
		return temp.compareTo(temp2);
	}

}
