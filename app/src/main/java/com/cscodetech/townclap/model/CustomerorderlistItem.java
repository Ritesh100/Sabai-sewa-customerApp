package com.cscodetech.townclap.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomerorderlistItem implements Parcelable {

	@SerializedName("p_method_name")
	private String pMethodName;

	@SerializedName("customer_address")
	private String customerAddress;

	@SerializedName("Order_Status")
	private String orderStatus;

	@SerializedName("customer_mobile")
	private String customerMobile;

	@SerializedName("add_total")
	private String addTotal;

	@SerializedName("Order_Total")
	private String orderTotal;

	@SerializedName("lats")
	private String lats;

	@SerializedName("Order_flow_id")
	private String orderFlowId;

	@SerializedName("sign")
	private Object sign;

	@SerializedName("order_time")
	private String orderTime;

	@SerializedName("Order_Product_Data")
	private ArrayList<OrderProductDataItem> orderProductData;

	@SerializedName("order_date")
	private String orderDate;

	@SerializedName("longs")
	private String longs;

	@SerializedName("customer_email")
	private String customerEmail;

	@SerializedName("order_dateslot")
	private String orderDateslot;

	@SerializedName("Order_Transaction_id")
	private String orderTransactionId;

	@SerializedName("add_on_data")
	private ArrayList<AddOnDataItem> addOnData;

	@SerializedName("Order_SubTotal")
	private String orderSubTotal;

	@SerializedName("customer_name")
	private String customerName;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("r_credit")
	private String rCredit;

	@SerializedName("wal_amt")
	private String wallet;

	@SerializedName("partner_name")
	private String partnerName;

	@SerializedName("partner_mobile")
	private String partnerMobile;

	@SerializedName("partner_img")
	private String partnerImg;

	@SerializedName("add_desc")
	private String addDesc;

	@SerializedName("add_price")
	private String addPrice;



	protected CustomerorderlistItem(Parcel in) {
		pMethodName = in.readString();
		customerAddress = in.readString();
		orderStatus = in.readString();
		customerMobile = in.readString();
		addTotal = in.readString();
		orderTotal = in.readString();
		lats = in.readString();
		orderFlowId = in.readString();
		orderTime = in.readString();
		orderDate = in.readString();
		longs = in.readString();
		customerEmail = in.readString();
		orderDateslot = in.readString();
		orderTransactionId = in.readString();
		addOnData = in.createTypedArrayList(AddOnDataItem.CREATOR);
		orderSubTotal = in.readString();
		customerName = in.readString();
		orderId = in.readString();
		categoryId = in.readString();
		rCredit = in.readString();
		wallet = in.readString();
		partnerName = in.readString();
		partnerMobile = in.readString();
		partnerImg = in.readString();
		addDesc = in.readString();
		addPrice = in.readString();
	}

	public static final Creator<CustomerorderlistItem> CREATOR = new Creator<CustomerorderlistItem>() {
		@Override
		public CustomerorderlistItem createFromParcel(Parcel in) {
			return new CustomerorderlistItem(in);
		}

		@Override
		public CustomerorderlistItem[] newArray(int size) {
			return new CustomerorderlistItem[size];
		}
	};

	public String getPMethodName(){
		return pMethodName;
	}

	public String getCustomerAddress(){
		return customerAddress;
	}

	public String getOrderStatus(){
		return orderStatus;
	}

	public String getCustomerMobile(){
		return customerMobile;
	}

	public String getAddTotal(){
		return addTotal;
	}

	public String getOrderTotal(){
		return orderTotal;
	}

	public String getLats(){
		return lats;
	}

	public String getOrderFlowId(){
		return orderFlowId;
	}

	public Object getSign(){
		return sign;
	}

	public String getOrderTime(){
		return orderTime;
	}

	public ArrayList<OrderProductDataItem> getOrderProductData(){
		return orderProductData;
	}

	public String getOrderDate(){
		return orderDate;
	}

	public String getLongs(){
		return longs;
	}

	public String getCustomerEmail(){
		return customerEmail;
	}

	public String getOrderDateslot(){
		return orderDateslot;
	}

	public String getOrderTransactionId(){
		return orderTransactionId;
	}

	public ArrayList <AddOnDataItem> getAddOnData(){
		return addOnData;
	}

	public String getOrderSubTotal(){
		return orderSubTotal;
	}

	public String getCustomerName(){
		return customerName;
	}

	public String getOrderId(){
		return orderId;
	}

	public String getRCredit(){
		return rCredit;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getWallet() {
		return wallet;
	}

	public void setWallet(String wallet) {
		this.wallet = wallet;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerMobile() {
		return partnerMobile;
	}

	public void setPartnerMobile(String partnerMobile) {
		this.partnerMobile = partnerMobile;
	}

	public String getPartnerImg() {
		return partnerImg;
	}

	public void setPartnerImg(String partnerImg) {
		this.partnerImg = partnerImg;
	}

	public String getAddDesc() {
		return addDesc;
	}

	public String getAddPrice() {
		return addPrice;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(pMethodName);
		dest.writeString(customerAddress);
		dest.writeString(orderStatus);
		dest.writeString(customerMobile);
		dest.writeString(addTotal);
		dest.writeString(orderTotal);
		dest.writeString(lats);
		dest.writeString(orderFlowId);
		dest.writeString(orderTime);
		dest.writeString(orderDate);
		dest.writeString(longs);
		dest.writeString(customerEmail);
		dest.writeString(orderDateslot);
		dest.writeString(orderTransactionId);
		dest.writeTypedList(addOnData);
		dest.writeString(orderSubTotal);
		dest.writeString(customerName);
		dest.writeString(orderId);
		dest.writeString(categoryId);
		dest.writeString(rCredit);
		dest.writeString(wallet);
		dest.writeString(partnerName);
		dest.writeString(partnerMobile);
		dest.writeString(partnerImg);
		dest.writeString(addDesc);
		dest.writeString(addPrice);
	}
}