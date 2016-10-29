package com.example.javabean;

import cn.bmob.v3.BmobObject;

@SuppressWarnings("serial")
public class Job extends BmobObject {
	private String company_id;
	private String job_name;
	private String salary;
	private String place;
	private String count;
	private String fuli;
	private String tag;
	private String introduction;

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getJob_name() {
		return job_name;
	}

	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getFuli() {
		return fuli;
	}

	public void setFuli(String fuli) {
		this.fuli = fuli;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

}
