package com.example.javabean;

import cn.bmob.v3.BmobObject;

@SuppressWarnings("serial")
public class Cv_control extends BmobObject {
	private String cv_id;
	private String collection;
	private String job_id;

	public String getCv_id() {
		return cv_id;
	}

	public void setCv_id(String cv_id) {
		this.cv_id = cv_id;
	}

	public String getJob_id() {
		return job_id;
	}

	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

}
