package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Partner{

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("PartnerListData")
	private List<PartnerListDataItem> partnerListData;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("Result")
	private String result;

	public String getResponseCode(){
		return responseCode;
	}

	public List<PartnerListDataItem> getPartnerListData(){
		return partnerListData;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public String getResult(){
		return result;
	}
}