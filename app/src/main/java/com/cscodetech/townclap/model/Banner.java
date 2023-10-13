package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

public class Banner {

	@SerializedName("img")
	private String img;

	@SerializedName("id")
	private String id;

	@SerializedName("status")
	private String status;

	@SerializedName("cat_id")
	private String catId;

	@SerializedName("subcat_id")
	private String subcatId;

	@SerializedName("cat_subtitle")
	private String catSubtitle;

	@SerializedName("cat_name")
	private String catName;

	@SerializedName("cat_video")
	private String catVideo;

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
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

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getSubcatId() {
		return subcatId;
	}

	public void setSubcatId(String subcatId) {
		this.subcatId = subcatId;
	}

	public String getCatSubtitle() {
		return catSubtitle;
	}

	public void setCatSubtitle(String catSubtitle) {
		this.catSubtitle = catSubtitle;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getCatVideo() {
		return catVideo;
	}

	public void setCatVideo(String catVideo) {
		this.catVideo = catVideo;
	}
}