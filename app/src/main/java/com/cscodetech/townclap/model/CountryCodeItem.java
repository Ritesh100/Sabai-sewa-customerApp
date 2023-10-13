package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

public class CountryCodeItem{

	@SerializedName("ccode")
	private String ccode;

	@SerializedName("id")
	private String id;

	@SerializedName("status")
	private String status;

	public void setCcode(String ccode){
		this.ccode = ccode;
	}

	public String getCcode(){
		return ccode;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}