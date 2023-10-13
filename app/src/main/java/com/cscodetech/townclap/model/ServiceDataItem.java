package com.cscodetech.townclap.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ServiceDataItem implements Parcelable {

	@SerializedName("img")
	private String img;

	@SerializedName("subtitle")
	private String subtitle;

	@SerializedName("cat_id")
	private String catId;

	@SerializedName("title")
	private String title;

	@SerializedName("cat_title")
	private String catTitle;

	@SerializedName("cat_subtitle")
	private String catSubtitle;

	@SerializedName("video")
	private String video;


	protected ServiceDataItem(Parcel in) {
		img = in.readString();
		subtitle = in.readString();
		catId = in.readString();
		title = in.readString();
		catTitle = in.readString();
		catSubtitle = in.readString();
		video = in.readString();
	}

	public static final Creator<ServiceDataItem> CREATOR = new Creator<ServiceDataItem>() {
		@Override
		public ServiceDataItem createFromParcel(Parcel in) {
			return new ServiceDataItem(in);
		}

		@Override
		public ServiceDataItem[] newArray(int size) {
			return new ServiceDataItem[size];
		}
	};

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setSubtitle(String subtitle){
		this.subtitle = subtitle;
	}

	public String getSubtitle(){
		return subtitle;
	}

	public void setCatId(String catId){
		this.catId = catId;
	}

	public String getCatId(){
		return catId;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getCatTitle() {
		return catTitle;
	}

	public void setCatTitle(String catTitle) {
		this.catTitle = catTitle;
	}

	public String getCatSubtitle() {
		return catSubtitle;
	}

	public void setCatSubtitle(String catSubtitle) {
		this.catSubtitle = catSubtitle;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(img);
		dest.writeString(subtitle);
		dest.writeString(catId);
		dest.writeString(title);
		dest.writeString(catTitle);
		dest.writeString(catSubtitle);
		dest.writeString(video);
	}
}