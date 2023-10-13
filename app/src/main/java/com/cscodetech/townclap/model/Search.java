package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Search{

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("SearchChildcatdata")
	private List<SearchChildcatdataItem> searchChildcatdata;

	@SerializedName("Result")
	private String result;

	public String getResponseCode(){
		return responseCode;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public List<SearchChildcatdataItem> getSearchChildcatdata(){
		return searchChildcatdata;
	}

	public String getResult(){
		return result;
	}
}