package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

public class SearchChildcatdataItem{

	@SerializedName("subcat_id")
	private String subcatId;

	@SerializedName("img")
	private String img;

	@SerializedName("childcat_id")
	private String childcatId;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("title")
	private String title;

	public String getSubcatId(){
		return subcatId;
	}

	public String getImg(){
		return img;
	}

	public String getChildcatId(){
		return childcatId;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public String getTitle(){
		return title;
	}
}