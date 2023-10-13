package com.cscodetech.townclap.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CatlistItem implements Parcelable {

	@SerializedName("cat_img")
	private String catImg;

	@SerializedName("cat_name")
	private String catName;

	@SerializedName("Total_subcat")
	private int totalSubcat;

	@SerializedName("cat_subtitle")
	private String catSubtitle;

	@SerializedName("cat_id")
	private String catId;

	@SerializedName("cat_video")
	private String catVideo;

	protected CatlistItem(Parcel in) {
		catImg = in.readString();
		catName = in.readString();
		totalSubcat = in.readInt();
		catSubtitle = in.readString();
		catId = in.readString();
		catVideo = in.readString();
	}

	public static final Creator<CatlistItem> CREATOR = new Creator<CatlistItem>() {
		@Override
		public CatlistItem createFromParcel(Parcel in) {
			return new CatlistItem(in);
		}

		@Override
		public CatlistItem[] newArray(int size) {
			return new CatlistItem[size];
		}
	};

	public void setCatImg(String catImg){
		this.catImg = catImg;
	}

	public String getCatImg(){
		return catImg;
	}

	public void setCatName(String catName){
		this.catName = catName;
	}

	public String getCatName(){
		return catName;
	}

	public void setTotalSubcat(int totalSubcat){
		this.totalSubcat = totalSubcat;
	}

	public int getTotalSubcat(){
		return totalSubcat;
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

	public void setCatVideo(String catVideo){
		this.catVideo = catVideo;
	}

	public String getCatVideo(){
		return catVideo;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(catImg);
		dest.writeString(catName);
		dest.writeInt(totalSubcat);
		dest.writeString(catSubtitle);
		dest.writeString(catId);
		dest.writeString(catVideo);
	}
}