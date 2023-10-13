package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubcatSectionItem {

	@SerializedName("cat_name")
	private String catName;

	@SerializedName("cat_subtitle")
	private String catSubtitle;

	@SerializedName("cat_id")
	private String catId;

	@SerializedName("subcat_data")
	private ArrayList<SubcatDatum> subcatData;

	public void setCatName(String catName){
		this.catName = catName;
	}

	public String getCatName(){
		return catName;
	}

	public void setCatSubtitle(String catSubtitle){
		this.catSubtitle = catSubtitle;
	}

	public String getCatSubtitle(){
		return catSubtitle;
	}

	public void setCatId(String catId){
		this.catId = catId;
	}

	public String getCatId(){
		return catId;
	}

	public void setSubcatData(ArrayList<SubcatDatum> subcatData){
		this.subcatData = subcatData;
	}

	public ArrayList<SubcatDatum> getSubcatData(){
		return subcatData;
	}
}