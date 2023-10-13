package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

public class ResponseMessge{

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("Result")
	private String result;

	@SerializedName("signupcredit")
	private String signupcredit;

	@SerializedName("refercredit")
	private String refercredit;

	@SerializedName("code")
	private String code;

	@SerializedName("wallet")
	private String wallet;


	public String getResponseCode(){
		return responseCode;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public String getResult(){
		return result;
	}

	public String getSignupcredit() {
		return signupcredit;
	}

	public void setSignupcredit(String signupcredit) {
		this.signupcredit = signupcredit;
	}

	public String getRefercredit() {
		return refercredit;
	}

	public void setRefercredit(String refercredit) {
		this.refercredit = refercredit;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getWallet() {
		return wallet;
	}

	public void setWallet(String wallet) {
		this.wallet = wallet;
	}
}