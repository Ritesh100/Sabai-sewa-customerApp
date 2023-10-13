package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

public class ChildcatItem{

	@SerializedName("img")
	private String img;

	@SerializedName("childcat_id")
	private String childcatId;

	@SerializedName("title")
	private String title;

	public String getImg(){
		return img;
	}

	public String getChildcatId(){
		return childcatId;
	}

	public String getTitle(){
		return title;
	}
}