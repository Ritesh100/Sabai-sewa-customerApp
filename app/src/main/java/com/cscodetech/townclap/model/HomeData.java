package com.cscodetech.townclap.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HomeData {

	@SerializedName("testimonial")
	private List<TestimonialItem> testimonial;

	@SerializedName("Dynmaic_section")
	private List<DynmaicSectionItem> dynmaicSection;

	@SerializedName("Main_Data")
	private MainData mainData;

	@SerializedName("Banner")
	private List<Banner> banner;

	@SerializedName("Catlist")
	private ArrayList<CatlistItem> catlist;

	@SerializedName("Subcat_section")
	private List<SubcatSectionItem> subcatSection;

	public void setTestimonial(List<TestimonialItem> testimonial){
		this.testimonial = testimonial;
	}

	public List<TestimonialItem> getTestimonial(){
		return testimonial;
	}

	public void setDynmaicSection(List<DynmaicSectionItem> dynmaicSection){
		this.dynmaicSection = dynmaicSection;
	}

	public List<DynmaicSectionItem> getDynmaicSection(){
		return dynmaicSection;
	}

	public void setMainData(MainData mainData){
		this.mainData = mainData;
	}

	public MainData getMainData(){
		return mainData;
	}

	public void setBanner(List<Banner> banner){
		this.banner = banner;
	}

	public List<Banner> getBanner(){
		return banner;
	}

	public void setCatlist(ArrayList<CatlistItem> catlist){
		this.catlist = catlist;
	}

	public ArrayList<CatlistItem> getCatlist(){
		return catlist;
	}

	public void setSubcatSection(List<SubcatSectionItem> subcatSection){
		this.subcatSection = subcatSection;
	}

	public List<SubcatSectionItem> getSubcatSection(){
		return subcatSection;
	}
}