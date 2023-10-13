package com.cscodetech.townclap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Times{

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("AddressData")
	@Expose
	private List<AddressList> addressData = null;

	@SerializedName("TimeslotData")
	private List<TimeslotDataItem> timeslotData;

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

	public void setTimeslotData(List<TimeslotDataItem> timeslotData){
		this.timeslotData = timeslotData;
	}

	public List<TimeslotDataItem> getTimeslotData(){
		return timeslotData;
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

	public List<AddressList> getAddressData() {
		return addressData;
	}

	public void setAddressData(List<AddressList> addressData) {
		this.addressData = addressData;
	}
}