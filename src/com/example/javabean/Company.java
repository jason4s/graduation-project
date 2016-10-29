package com.example.javabean;

import cn.bmob.v3.BmobObject;

@SuppressWarnings("serial")
public class Company extends BmobObject {
	private String user_id;
	private String company_name;
	private String industries;
	private String nature;
	private String trimmed;
	private String address;
	private String introduction;
	private String order;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getIndustries() {
		return industries;
	}

	public void setIndustries(String industries) {
		this.industries = industries;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getTrimmed() {
		return trimmed;
	}

	public void setTrimmed(String trimmed) {
		this.trimmed = trimmed;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	
	

}
