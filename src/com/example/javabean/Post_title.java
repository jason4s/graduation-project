package com.example.javabean;

import cn.bmob.v3.BmobObject;

@SuppressWarnings("serial")
public class Post_title extends BmobObject {
	
	private String title;
	private Integer order;
	private String type;
	private String host_user_name;
	private Integer last_count;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getLast_count() {
		return last_count;
	}
	public void setLast_count(Integer last_count) {
		this.last_count = last_count;
	}
	public String getHost_user_name() {
		return host_user_name;
	}
	public void setHost_user_name(String host_user_name) {
		this.host_user_name = host_user_name;
	}
	

}
