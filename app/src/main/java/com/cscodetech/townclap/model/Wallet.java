package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Wallet{

	@SerializedName("debittotal")
	private int debittotal;

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("wallet")
	private String wallets;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("credittotal")
	private int credittotal;

	@SerializedName("Walletitem")
	private List<WalletitemItem> walletitem;

	@SerializedName("Result")
	private String result;

	public int getDebittotal(){
		return debittotal;
	}

	public String getResponseCode(){
		return responseCode;
	}

	public String getWallets(){
		return wallets;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public int getCredittotal(){
		return credittotal;
	}

	public List<WalletitemItem> getWalletitem(){
		return walletitem;
	}

	public String getResult(){
		return result;
	}
}