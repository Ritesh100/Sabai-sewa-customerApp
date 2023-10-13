package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Addon{

	@SerializedName("Addondata")
	private ArrayList<AddOnDataItem> addondata;

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("Result")
	private String result;

	public ArrayList<AddOnDataItem> getAddondata(){
		return addondata;
	}

	public String getResponseCode(){
		return responseCode;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public String getResult(){
		return result;
	}
}