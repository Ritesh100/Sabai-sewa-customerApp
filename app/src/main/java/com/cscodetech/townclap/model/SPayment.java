package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

public class SPayment {

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("Transaction_id")
	private String transactionId;

	@SerializedName("Result")
	private String result;

	public String getResponseCode(){
		return responseCode;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public String getResult(){
		return result;
	}
}