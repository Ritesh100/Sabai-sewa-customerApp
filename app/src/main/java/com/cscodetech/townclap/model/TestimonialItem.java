package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

public class TestimonialItem{

	@SerializedName("img")
	private String img;

	@SerializedName("comment")
	private String comment;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("status")
	private String status;

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return comment;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}