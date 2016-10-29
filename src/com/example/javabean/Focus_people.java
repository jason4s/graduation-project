package com.example.javabean;

import cn.bmob.v3.BmobObject;

@SuppressWarnings("serial")
public class Focus_people extends BmobObject {
	private String host_id;
	private String focus_people_name;
	
	
	public String getHost_id() {
		return host_id;
	}
	public void setHost_id(String host_id) {
		this.host_id = host_id;
	}
	public String getFocus_people_name() {
		return focus_people_name;
	}
	public void setFocus_people_name(String focus_people_name) {
		this.focus_people_name = focus_people_name;
	}
	
	

	

}
