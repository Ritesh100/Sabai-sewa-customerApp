package com.cscodetech.townclap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TypeSubCat {

    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("Result")
    @Expose
    private String result;
    @SerializedName("ResponseMsg")
    @Expose
    private String responseMsg;
    @SerializedName("Subcatdata")
    @Expose
    private List<SubcatDatum> subcatdata = null;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public List<SubcatDatum> getSubcatdata() {
        return subcatdata;
    }

    public void setSubcatdata(List<SubcatDatum> subcatdata) {
        this.subcatdata = subcatdata;
    }

}
