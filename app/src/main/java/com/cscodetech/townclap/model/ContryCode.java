package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContryCode{

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("CountryCode")
	private List<CountryCodeItem> countryCode;

	@SerializedName("Result")
	private String result;

	public void setResponseCode(String responseCode){
		this.responseCode = responseCode;
	}

	public String getResponseCode(){
		return responseCode;
	}

	public void setResponseMsg(String responseMsg){
		this.responseMsg = responseMsg;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public void setCountryCode(List<CountryCodeItem> countryCode){
		this.countryCode = countryCode;
	}

	public List<CountryCodeItem> getCountryCode(){
		return countryCode;
	}

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}
}