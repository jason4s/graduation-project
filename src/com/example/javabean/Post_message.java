package com.example.javabean;

import cn.bmob.v3.BmobObject;

@SuppressWarnings("serial")
public class Post_message extends BmobObject {
	
	private String message;
	private Integer message_order;
	private String type;
	private String this_user_name;
	private String reply_user_name;
	private Integer order;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getMessage_order() {
		return message_order;
	}
	public void setMessage_order(Integer message_order) {
		this.message_order = message_order;
	}
	public String getThis_user_name() {
		return this_user_name;
	}
	public void setThis_user_name(String this_user_name) {
		this.this_user_name = this_user_name;
	}
	public String getReply_user_name() {
		return reply_user_name;
	}
	public void setReply_user_name(String reply_user_name) {
		this.reply_user_name = reply_user_name;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	

}
