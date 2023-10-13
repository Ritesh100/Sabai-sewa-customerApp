package com.cscodetech.townclap.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PartnerListDataItem implements Parcelable {

	@SerializedName("address")
	private String address;

	@SerializedName("city")
	private String city;

	@SerializedName("pimg")
	private String pimg;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("bio")
	private String bio;

	@SerializedName("ccode")
	private String ccode;

	@SerializedName("password")
	private String password;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("rdate")
	private String rdate;

	@SerializedName("rate")
	private String rate;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("credit")
	private String credit;

	@SerializedName("email")
	private String email;

	@SerializedName("aprove")
	private String aprove;

	@SerializedName("status")
	private String status;

	@SerializedName("cat_name")
	private String catName;

	@SerializedName("total_complete")
	private String totalComplete;

	@SerializedName("total_earn")
	private String totalEarn;

	protected PartnerListDataItem(Parcel in) {
		address = in.readString();
		city = in.readString();
		pimg = in.readString();
		mobile = in.readString();
		bio = in.readString();
		ccode = in.readString();
		password = in.readString();
		categoryId = in.readString();
		rdate = in.readString();
		rate = in.readString();
		name = in.readString();
		id = in.readString();
		credit = in.readString();
		email = in.readString();
		aprove = in.readString();
		status = in.readString();
		catName = in.readString();
		totalComplete = in.readString();
		totalEarn = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(address);
		dest.writeString(city);
		dest.writeString(pimg);
		dest.writeString(mobile);
		dest.writeString(bio);
		dest.writeString(ccode);
		dest.writeString(password);
		dest.writeString(categoryId);
		dest.writeString(rdate);
		dest.writeString(rate);
		dest.writeString(name);
		dest.writeString(id);
		dest.writeString(credit);
		dest.writeString(email);
		dest.writeString(aprove);
		dest.writeString(status);
		dest.writeString(catName);
		dest.writeString(totalComplete);
		dest.writeString(totalEarn);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<PartnerListDataItem> CREATOR = new Creator<PartnerListDataItem>() {
		@Override
		public PartnerListDataItem createFromParcel(Parcel in) {
			return new PartnerListDataItem(in);
		}

		@Override
		public PartnerListDataItem[] newArray(int size) {
			return new PartnerListDataItem[size];
		}
	};

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPimg() {
		return pimg;
	}

	public void setPimg(String pimg) {
		this.pimg = pimg;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getCcode() {
		return ccode;
	}

	public void setCcode(String ccode) {
		this.ccode = ccode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAprove() {
		return aprove;
	}

	public void setAprove(String aprove) {
		this.aprove = aprove;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getTotalComplete() {
		return totalComplete;
	}

	public void setTotalComplete(String totalComplete) {
		this.totalComplete = totalComplete;
	}

	public String getTotalEarn() {
		return totalEarn;
	}

	public void setTotalEarn(String totalEarn) {
		this.totalEarn = totalEarn;
	}
}