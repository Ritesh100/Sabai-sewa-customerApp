package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DynmaicSectionItem   {

	@SerializedName("sec_id")
	private String secId;

	@SerializedName("sec_title")
	private String secTitle;

	@SerializedName("sec_subtitle")
	private String secSubtitle;

	@SerializedName("service_data")
	private ArrayList<ServiceDataItem> serviceData;

	public void setSecId(String secId){
		this.secId = secId;
	}

	public String getSecId(){
		return secId;
	}

	public void setSecTitle(String secTitle){
		this.secTitle = secTitle;
	}

	public String getSecTitle(){
		return secTitle;
	}

	public void setSecSubtitle(String secSubtitle){
		this.secSubtitle = secSubtitle;
	}

	public String getSecSubtitle(){
		return secSubtitle;
	}

	public void setServiceData(ArrayList<ServiceDataItem> serviceData){
		this.serviceData = serviceData;
	}

	public ArrayList<ServiceDataItem> getServiceData(){
		return serviceData;
	}
}