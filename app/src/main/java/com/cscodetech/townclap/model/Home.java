package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

public class Home{

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("ResultData")
	private HomeData resultData;

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

	public void setResultData(HomeData resultData){
		this.resultData = resultData;
	}

	public HomeData getResultData(){
		return resultData;
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