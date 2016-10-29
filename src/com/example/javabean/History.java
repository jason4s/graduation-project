package com.example.javabean;

import cn.bmob.v3.BmobObject;

@SuppressWarnings("serial")
public class History extends BmobObject {
	private String user_id;
	private String post_type;
	private String information_type;
	private int information_order;
	private String title;
	private String host_user_name;
	private String job_id;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPost_type() {
		return post_type;
	}

	public void setPost_type(String post_type) {
		this.post_type = post_type;
	}

	public String getInformation_type() {
		return information_type;
	}

	public void setInformation_type(String information_type) {
		this.information_type = information_type;
	}

	public int getInformation_order() {
		return information_order;
	}

	public void setInformation_order(int information_order) {
		this.information_order = information_order;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHost_user_name() {
		return host_user_name;
	}

	public void setHost_user_name(String host_user_name) {
		this.host_user_name = host_user_name;
	}

	public String getJob_id() {
		return job_id;
	}

	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}

}
