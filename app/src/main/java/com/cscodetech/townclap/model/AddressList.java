
package com.cscodetech.townclap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AddressList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("hno")
    @Expose
    private String houseno;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("lat_map")
    @Expose
    private String latMap;
    @SerializedName("long_map")
    @Expose
    private String longMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getHouseno() {
        return houseno;
    }

    public void setHouseno(String houseno) {
        this.houseno = houseno;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLatMap() {
        return latMap;
    }

    public void setLatMap(String latMap) {
        this.latMap = latMap;
    }

    public String getLongMap() {
        return longMap;
    }

    public void setLongMap(String longMap) {
        this.longMap = longMap;
    }

}
