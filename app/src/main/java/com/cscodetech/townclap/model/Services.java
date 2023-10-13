package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Services{

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("Servicelistdata")
	private List<ServicelistdataItem> servicelistdata;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("Result")
	private String result;

	public String getResponseCode(){
		return responseCode;
	}

	public List<ServicelistdataItem> getServicelistdata(){
		return servicelistdata;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public String getResult(){
		return result;
	}
}