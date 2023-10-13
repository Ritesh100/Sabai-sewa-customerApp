package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order{

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("customerorderlist")
	private List<CustomerorderlistItem> customerorderlist;

	@SerializedName("Result")
	private String result;

	public String getResponseCode(){
		return responseCode;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public List<CustomerorderlistItem> getCustomerorderlist(){
		return customerorderlist;
	}

	public String getResult(){
		return result;
	}
}