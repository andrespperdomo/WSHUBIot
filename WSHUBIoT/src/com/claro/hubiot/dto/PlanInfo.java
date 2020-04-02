package com.claro.hubiot.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlanInfo {

	@XmlElement(name = "productType",required=true)
	private String productType;
	@XmlElement(name = "planId",required=true)
	private String planId;
	@XmlElement(name = "price",required=true)
	private Double price;
	@XmlElement(name = "currency",required=true)
	private String currency;
	@XmlElement(name = "tier1",required=false)
	private String tier1;
	@XmlElement(name = "tier2",required=false)
	private String tier2;
	@XmlElement(name = "tier3",required=false)
	private String tier3;
	@XmlElement(name = "mbQuantity",required=true)
	private String mbQuantity;
	@XmlElement(name = "smsQuantity",required=true)
	private String smsQuantity;
	@XmlElement(name = "minutesQuantity",required=true)
	private String minutesQuantity;
	@XmlTransient
	private String enterpriceId;
	@XmlTransient
	private String providerId;
	@XmlTransient
	private String nmtmcode;
	
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTier1() {
		return tier1;
	}
	public void setTier1(String tier1) {
		this.tier1 = tier1;
	}
	public String getTier2() {
		return tier2;
	}
	public void setTier2(String tier2) {
		this.tier2 = tier2;
	}
	public String getTier3() {
		return tier3;
	}
	public void setTier3(String tier3) {
		this.tier3 = tier3;
	}
	public String getMbQuantity() {
		return mbQuantity;
	}
	public void setMbQuantity(String mbQuantity) {
		this.mbQuantity = mbQuantity;
	}
	public String getSmsQuantity() {
		return smsQuantity;
	}
	public void setSmsQuantity(String smsQuantity) {
		this.smsQuantity = smsQuantity;
	}
	public String getMinutesQuantity() {
		return minutesQuantity;
	}
	public void setMinutesQuantity(String minutesQuantity) {
		this.minutesQuantity = minutesQuantity;
	}

	public String getEnterpriceId() {
		return enterpriceId;
	}
	public void setEnterpriceId(String enterpriceId) {
		this.enterpriceId = enterpriceId;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	

	public String getNmtmcode() {
		return nmtmcode;
	}
	public void setNmtmcode(String nmtmcode) {
		this.nmtmcode = nmtmcode;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<PlanInfo><");
		if (productType != null) {
			builder.append("productType>");
			builder.append(productType);
			builder.append("</productType><");
		}
		if (planId != null) {
			builder.append("planId>");
			builder.append(planId);
			builder.append("</planId><");
		}
		if (price != null) {
			builder.append("price>");
			builder.append(price);
			builder.append("</price><");
		}
		if (currency != null) {
			builder.append("currency>");
			builder.append(currency);
			builder.append("</currency><");
		}
		if (tier1 != null) {
			builder.append("tier1>");
			builder.append(tier1);
			builder.append("</tier1><");
		}
		if (tier2 != null) {
			builder.append("tier2>");
			builder.append(tier2);
			builder.append("</tier2><");
		}
		if (tier3 != null) {
			builder.append("tier3>");
			builder.append(tier3);
			builder.append("</tier3><");
		}
		if (mbQuantity != null) {
			builder.append("mbQuantity>");
			builder.append(mbQuantity);
			builder.append("</mbQuantity><");
		}
		if (smsQuantity != null) {
			builder.append("smsQuantity>");
			builder.append(smsQuantity);
			builder.append("</smsQuantity><");
		}
		if (minutesQuantity != null) {
			builder.append("minutesQuantity>");
			builder.append(minutesQuantity);
			builder.append("</minutesQuantity><");
		}
		if (enterpriceId != null) {
			builder.append("enterpriceId>");
			builder.append(enterpriceId);
			builder.append("</enterpriceId><");
		}
		if (providerId != null) {
			builder.append("providerId>");
			builder.append(providerId);
			builder.append("</providerId><");
		}
		if (nmtmcode != null) {
			builder.append("nmtmcode>");
			builder.append(nmtmcode);
			builder.append("</nmtmcode");
		}
		builder.append("></PlanInfo>");
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof PlanInfo)) {
			return false;
		}
		PlanInfo plan = (PlanInfo) obj;
		return (plan.planId==this.planId&&plan.price==this.price&&plan.productType==this.productType);
	}


}
