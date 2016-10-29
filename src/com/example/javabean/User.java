package com.example.javabean;

import cn.bmob.v3.BmobObject;

@SuppressWarnings("serial")
public class User extends BmobObject {
	private String user_id;
	private String password;
	private String user_name;
	private String sex;
	private String birthday;
	private String individual_resume;
	
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIndividual_resume() {
		return individual_resume;
	}

	public void setIndividual_resume(String individual_resume) {
		this.individual_resume = individual_resume;
	}

	

}
