package com.cscodetech.townclap.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubcatDatum implements Parcelable{
	@SerializedName("cat_id")
	@Expose
	private String catid;
	@SerializedName("subcat_id")
	@Expose
	private String subcatId;
	@SerializedName("img")
	@Expose
	private String img;
	@SerializedName("title")
	@Expose
	private String title;
	@SerializedName("subtitle")
	@Expose
	private String subtitle;
	@SerializedName("video")
	@Expose
	private String video;


	protected SubcatDatum(Parcel in) {
		catid = in.readString();
		subcatId = in.readString();
		img = in.readString();
		title = in.readString();
		subtitle = in.readString();
		video = in.readString();
	}

	public static final Creator<SubcatDatum> CREATOR = new Creator<SubcatDatum>() {
		@Override
		public SubcatDatum createFromParcel(Parcel in) {
			return new SubcatDatum(in);
		}

		@Override
		public SubcatDatum[] newArray(int size) {
			return new SubcatDatum[size];
		}
	};

	public String getCatid() {
		return catid;
	}

	public void setCatid(String catid) {
		this.catid = catid;
	}

	public String getSubcatId() {
		return subcatId;
	}

	public void setSubcatId(String subcatId) {
		this.subcatId = subcatId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(catid);
		dest.writeString(subcatId);
		dest.writeString(img);
		dest.writeString(title);
		dest.writeString(subtitle);
		dest.writeString(video);
	}
}