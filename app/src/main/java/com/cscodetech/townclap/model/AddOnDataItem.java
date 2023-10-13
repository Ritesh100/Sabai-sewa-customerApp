package com.cscodetech.townclap.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AddOnDataItem implements Parcelable {

	@SerializedName("price")
	private String price;

	@SerializedName("title")
	private String title;

	@SerializedName("img")
	private String img;

	public boolean isSelected;

	protected AddOnDataItem(Parcel in) {
		price = in.readString();
		title = in.readString();
		img = in.readString();
		isSelected = in.readByte() != 0;
	}

	public static final Creator<AddOnDataItem> CREATOR = new Creator<AddOnDataItem>() {
		@Override
		public AddOnDataItem createFromParcel(Parcel in) {
			return new AddOnDataItem(in);
		}

		@Override
		public AddOnDataItem[] newArray(int size) {
			return new AddOnDataItem[size];
		}
	};

	public String getPrice(){
		return price;
	}

	public String getTitle(){
		return title;
	}

	public String getImg() {
		return img;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(price);
		dest.writeString(title);
		dest.writeString(img);
		dest.writeByte((byte) (isSelected ? 1 : 0));
	}
}