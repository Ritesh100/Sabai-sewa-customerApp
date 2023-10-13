package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceListItem{


	@SerializedName("service_title")
	private String serviceTitle;

	@SerializedName("service_price")
	private double servicePrice;

	@SerializedName("service_subcat_id")
	private String serviceSubcatId;

	@SerializedName("service_sdesc")
	private String serviceSdesc;

	@SerializedName("service_childcat_id")
	private String serviceChildcatId;

	@SerializedName("service_img")
	private List<String> serviceImg;

	@SerializedName("service_id")
	private String serviceId;

	@SerializedName("service_s_show")
	private int serviceSShow;

	@SerializedName("service_video")
	private String serviceVideo;

	@SerializedName("service_cat_id")
	private String serviceCatId;

	@SerializedName("service_discount")
	private double serviceDiscount;

	@SerializedName("service_ttken")
	private String serviceTtken;


	@SerializedName("service_mqty")
	private String serviceMqty;

	private String serviceQty;

	public String getServiceMqty(){
		return serviceMqty;
	}

	public String getServiceTitle(){
		return serviceTitle;
	}

	public double getServicePrice(){
		return servicePrice;
	}

	public String getServiceSubcatId(){
		return serviceSubcatId;
	}

	public String getServiceSdesc(){
		return serviceSdesc;
	}

	public String getServiceChildcatId(){
		return serviceChildcatId;
	}

	public List<String> getServiceImg(){
		return serviceImg;
	}

	public String getServiceId(){
		return serviceId;
	}

	public int getServiceSShow(){
		return serviceSShow;
	}

	public String getServiceVideo(){
		return serviceVideo;
	}

	public String getServiceCatId(){
		return serviceCatId;
	}

	public double getServiceDiscount(){
		return serviceDiscount;
	}

	public String getServiceTtken(){
		return serviceTtken;
	}

	public void setServiceMqty(String serviceMqty) {
		this.serviceMqty = serviceMqty;
	}

	public void setServiceTitle(String serviceTitle) {
		this.serviceTitle = serviceTitle;
	}

	public void setServicePrice(double servicePrice) {
		this.servicePrice = servicePrice;
	}

	public void setServiceSubcatId(String serviceSubcatId) {
		this.serviceSubcatId = serviceSubcatId;
	}

	public void setServiceSdesc(String serviceSdesc) {
		this.serviceSdesc = serviceSdesc;
	}

	public void setServiceChildcatId(String serviceChildcatId) {
		this.serviceChildcatId = serviceChildcatId;
	}

	public void setServiceImg(List<String> serviceImg) {
		this.serviceImg = serviceImg;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public void setServiceSShow(int serviceSShow) {
		this.serviceSShow = serviceSShow;
	}

	public void setServiceVideo(String serviceVideo) {
		this.serviceVideo = serviceVideo;
	}

	public void setServiceCatId(String serviceCatId) {
		this.serviceCatId = serviceCatId;
	}

	public void setServiceDiscount(double serviceDiscount) {
		this.serviceDiscount = serviceDiscount;
	}

	public void setServiceTtken(String serviceTtken) {
		this.serviceTtken = serviceTtken;
	}

	public String getServiceQty() {
		return serviceQty;
	}

	public void setServiceQty(String serviceQty) {
		this.serviceQty = serviceQty;
	}
}