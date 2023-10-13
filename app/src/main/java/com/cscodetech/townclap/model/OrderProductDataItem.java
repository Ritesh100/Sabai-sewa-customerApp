package com.cscodetech.townclap.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class OrderProductDataItem implements Parcelable {

    @SerializedName("Product_quantity")
    private String productQuantity;

    @SerializedName("Product_price")
    private double productPrice;

    @SerializedName("Product_name")
    private String productName;

    @SerializedName("Product_discount")
    private double productDiscount;

    @SerializedName("Product_total")
    private double productTotal;

    protected OrderProductDataItem(Parcel in) {
        productQuantity = in.readString();
        productPrice = in.readDouble();
        productName = in.readString();
        productDiscount = in.readDouble();
        productTotal = in.readDouble();
    }

    public static final Creator<OrderProductDataItem> CREATOR = new Creator<OrderProductDataItem>() {
        @Override
        public OrderProductDataItem createFromParcel(Parcel in) {
            return new OrderProductDataItem(in);
        }

        @Override
        public OrderProductDataItem[] newArray(int size) {
            return new OrderProductDataItem[size];
        }
    };

    public String getProductQuantity() {
        return productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductDiscount() {
        return productDiscount;
    }

    public double getProductTotal() {
        return productTotal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productQuantity);
        dest.writeDouble(productPrice);
        dest.writeString(productName);
        dest.writeDouble(productDiscount);
        dest.writeDouble(productTotal);
    }
}