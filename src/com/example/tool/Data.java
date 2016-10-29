package com.example.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Data {
	private static String user_id = "";
	private static String user_name = "";
	private static String user_head = "";
	private static String id = "";        //objectId
	private static String password = "";
	private static List<Map<String, String>> datalist = new ArrayList<Map<String, String>>();
	private static int times;
	private static int which;
	private static int order;
	private static int which2;
	private static String type = "";
	private static List<Map<String, String>> datalist2 = new ArrayList<Map<String, String>>();
	private static String other_name = "";
	private static String tag = "";
	private static String key = "";
	private static List<Map<String, String>> datalist3 = new ArrayList<Map<String, String>>();

	public static String getUser_name() {
		return user_name;
	}

	public static void setUser_name(String user_name) {
		Data.user_name = user_name;
	}

	public static String getUser_head() {
		return user_head;
	}

	public static void setUser_head(String user_head) {
		Data.user_head = user_head;
	}

	public static String getId() {
		return id;
	}

	public static void setId(String id) {
		Data.id = id;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		Data.password = password;
	}

	public static List<Map<String, String>> getDatalist() {
		return datalist;
	}

	public static void setDatalist(List<Map<String, String>> datalist) {
		Data.datalist = datalist;
	}

	public static int getTimes() {
		return times;
	}

	public static void setTimes(int times) {
		Data.times = times;
	}

	public static int getWhich() {
		return which;
	}

	public static void setWhich(int which) {
		Data.which = which;
	}

	public static int getOrder() {
		return order;
	}

	public static void setOrder(int order) {
		Data.order = order;
	}

	public static String getType() {
		return type;
	}

	public static void setType(String type) {
		Data.type = type;
	}

	public static List<Map<String, String>> getDatalist2() {
		return datalist2;
	}

	public static void setDatalist2(List<Map<String, String>> datalist2) {
		Data.datalist2 = datalist2;
	}

	public static String getOther_name() {
		return other_name;
	}

	public static void setOther_name(String other_name) {
		Data.other_name = other_name;
	}

	public static String getUser_id() {
		return user_id;
	}

	public static void setUser_id(String user_id) {
		Data.user_id = user_id;
	}

	public static String getTag() {
		return tag;
	}

	public static void setTag(String tag) {
		Data.tag = tag;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		Data.key = key;
	}

	public static List<Map<String, String>> getDatalist3() {
		return datalist3;
	}

	public static void setDatalist3(List<Map<String, String>> datalist3) {
		Data.datalist3 = datalist3;
	}

	public static int getWhich2() {
		return which2;
	}

	public static void setWhich2(int which2) {
		Data.which2 = which2;
	}
}
