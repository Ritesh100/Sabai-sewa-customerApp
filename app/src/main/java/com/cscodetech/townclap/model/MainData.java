package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

public class MainData{

	@SerializedName("r_key")
	private String rKey;

	@SerializedName("refercredit")
	private String refercredit;

	@SerializedName("d_title")
	private String dTitle;

	@SerializedName("one_key")
	private String oneKey;

	@SerializedName("timezone")
	private String timezone;

	@SerializedName("signupcredit")
	private String signupcredit;

	@SerializedName("r_hash")
	private String rHash;

	@SerializedName("about")
	private String about;

	@SerializedName("one_hash")
	private String oneHash;

	@SerializedName("terms")
	private String terms;

	@SerializedName("p_limit")
	private String pLimit;

	@SerializedName("contact")
	private String contact;

	@SerializedName("logo")
	private String logo;

	@SerializedName("d_s_title")
	private String dSTitle;

	@SerializedName("currency")
	private String currency;

	@SerializedName("id")
	private String id;

	@SerializedName("policy")
	private String policy;

	@SerializedName("wallet")
	private String wallet;

	public void setRKey(String rKey){
		this.rKey = rKey;
	}

	public String getRKey(){
		return rKey;
	}

	public void setRefercredit(String refercredit){
		this.refercredit = refercredit;
	}

	public String getRefercredit(){
		return refercredit;
	}

	public void setDTitle(String dTitle){
		this.dTitle = dTitle;
	}

	public String getDTitle(){
		return dTitle;
	}

	public void setOneKey(String oneKey){
		this.oneKey = oneKey;
	}

	public String getOneKey(){
		return oneKey;
	}

	public void setTimezone(String timezone){
		this.timezone = timezone;
	}

	public String getTimezone(){
		return timezone;
	}

	public void setSignupcredit(String signupcredit){
		this.signupcredit = signupcredit;
	}

	public String getSignupcredit(){
		return signupcredit;
	}

	public void setRHash(String rHash){
		this.rHash = rHash;
	}

	public String getRHash(){
		return rHash;
	}

	public void setAbout(String about){
		this.about = about;
	}

	public String getAbout(){
		return about;
	}

	public void setOneHash(String oneHash){
		this.oneHash = oneHash;
	}

	public String getOneHash(){
		return oneHash;
	}

	public void setTerms(String terms){
		this.terms = terms;
	}

	public String getTerms(){
		return terms;
	}

	public void setPLimit(String pLimit){
		this.pLimit = pLimit;
	}

	public String getPLimit(){
		return pLimit;
	}

	public void setContact(String contact){
		this.contact = contact;
	}

	public String getContact(){
		return contact;
	}

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return logo;
	}

	public void setDSTitle(String dSTitle){
		this.dSTitle = dSTitle;
	}

	public String getDSTitle(){
		return dSTitle;
	}

	public void setCurrency(String currency){
		this.currency = currency;
	}

	public String getCurrency(){
		return currency;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPolicy(String policy){
		this.policy = policy;
	}

	public String getPolicy(){
		return policy;
	}

	public String getWallet() {
		return wallet;
	}

	public void setWallet(String wallet) {
		this.wallet = wallet;
	}
}