package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

public class LoginUser{

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("UserLogin")
	private User user;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("Result")
	private String result;

	public void setResponseCode(String responseCode){
		this.responseCode = responseCode;
	}

	public String getResponseCode(){
		return responseCode;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	public void setResponseMsg(String responseMsg){
		this.responseMsg = responseMsg;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}
}