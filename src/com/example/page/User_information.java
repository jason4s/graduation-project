package com.example.page;

import java.util.ArrayList;
import java.util.List;

import com.example.jason4s.*;
import com.example.javabean.Focus_people;
import com.example.javabean.User;
import com.example.tool.*;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class User_information extends Activity implements OnClickListener, OnCheckedChangeListener {
	private TextView name_tv;
	private TextView password_tv;
	private TextView sex_tv;
	private TextView birthday_tv;
	private TextView individual_resume_tv2;
	private TextView date_between1;
	private TextView date_between2;
	private TextView cancel;

	private EditText name_et;
	private EditText password_et;
	private RadioGroup sex_rg;
	private EditText individual_resume_et2;
	private EditText birthday_et_year;
	private EditText birthday_et_month;
	private EditText birthday_et_day;

	private Button back;
	private Button out_login;
	private Button change;

	private RadioButton male;
	private RadioButton female;
	private String sex;
	private boolean if_username_used = true;
	private String username;
	private TextView textView_password;
	private String check_or_update = "check";
	private String name;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_user_information);

		name_tv = (TextView) findViewById(R.id.name_tv);
		password_tv = (TextView) findViewById(R.id.password_tv);
		sex_tv = (TextView) findViewById(R.id.sex_tv);
		birthday_tv = (TextView) findViewById(R.id.birthday_tv);
		individual_resume_tv2 = (TextView) findViewById(R.id.individual_resume_tv2);
		date_between1 = (TextView) findViewById(R.id.date_between1);
		date_between2 = (TextView) findViewById(R.id.date_between2);
		cancel = (TextView) findViewById(R.id.cancel);
		back = (Button) findViewById(R.id.back);
		out_login = (Button) findViewById(R.id.out_login);
		change = (Button) findViewById(R.id.change);
		name_et = (EditText) findViewById(R.id.name_et);
		password_et = (EditText) findViewById(R.id.password_et);
		sex_rg = (RadioGroup) findViewById(R.id.sex_rg);
		individual_resume_et2 = (EditText) findViewById(R.id.individual_resume_et2);
		birthday_et_year = (EditText) findViewById(R.id.birthday_et_year);
		birthday_et_month = (EditText) findViewById(R.id.birthday_et_month);
		birthday_et_day = (EditText) findViewById(R.id.birthday_et_day);
		textView_password = (TextView) findViewById(R.id.textView_password);

		finish_change();

		back.setOnClickListener(this);
		change.setOnClickListener(this); 
		out_login.setOnClickListener(this);

	}

	private void change() {
		// TODO Auto-generated method stub change
		back.setBackgroundColor(Color.TRANSPARENT);
		cancel.setText("取消");
		change.setText("完成");
		name_tv.setText("");
		password_tv.setText("");
		sex_tv.setText("");
		birthday_tv.setText("");
		individual_resume_tv2.setText("");
		date_between1.setText(R.string.date_between);
		date_between2.setText(R.string.date_between);

		name_et.setFocusableInTouchMode(true);
		name_et.setFocusable(true);
		password_et.setFocusableInTouchMode(true);
		password_et.setFocusable(true);
		password_et.requestFocus();
		individual_resume_et2.setFocusableInTouchMode(true);
		individual_resume_et2.setFocusable(true);
		individual_resume_et2.requestFocus();
		birthday_et_year.setFocusableInTouchMode(true);
		birthday_et_year.setFocusable(true);
		birthday_et_year.requestFocus();
		birthday_et_month.setFocusableInTouchMode(true);
		birthday_et_month.setFocusable(true);
		birthday_et_month.requestFocus();
		birthday_et_day.setFocusableInTouchMode(true);
		birthday_et_day.setFocusable(true);
		birthday_et_day.requestFocus();
		name_et.requestFocus();

		male = new RadioButton(this);
		male.setText("男");
		female = new RadioButton(this);
		female.setText("女");
		sex_rg.addView(male, new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		sex_rg.addView(female, new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		sex_rg.invalidate();
		sex_rg.setOnCheckedChangeListener(this);

		BmobQuery<User> query = new BmobQuery<User>();
		query.addWhereEqualTo("user_name", Data.getUser_name());
		query.findObjects(User_information.this, new FindListener<User>() {

			@Override
			public void onSuccess(List<User> object) {
				for (User user : object) {
					name_et.setHint(user.getUser_name());
					individual_resume_et2.setHint(user.getIndividual_resume());
					String year = user.getBirthday().substring(0, 4);
					String month = user.getBirthday().substring(6, 7);
					String day = user.getBirthday().substring(9, 10);
					birthday_et_year.setHint(year);
					birthday_et_month.setHint(month);
					birthday_et_day.setHint(day);
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(User_information.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void update() {
		// TODO Auto-generated method stub update

		String y = birthday_et_year.getText().toString();
		String m1 = birthday_et_month.getText().toString();
		String d1 = birthday_et_day.getText().toString();
		String m;
		String d;

		if (m1.length() < 2) {
			m = "0" + m1;
		} else {
			m = m1;
		}
		if (d1.length() < 2) {
			d = "0" + d1;
		} else {
			d = d1;
		}

		String birthday = y + "年" + m + "月" + d + "日";
		
		BmobQuery<User> query1 = new BmobQuery<User>();
		query1.addWhereEqualTo("user_name", Data.getUser_name());
		query1.findObjects(User_information.this, new FindListener<User>() {
			@Override
			public void onSuccess(List<User> object) {

				for (User user : object) {
					Data.setId(user.getObjectId());
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				Toast.makeText(User_information.this, "user_id查询失败", Toast.LENGTH_SHORT).show();
			}
		});

		User user = new User();
		if (password.equals("")) {
			password=Data.getPassword();
		}
		if (individual_resume_et2.getText().toString().equals("")) {
			individual_resume_et2.setText(individual_resume_et2.getHint().toString());
		}
		
		user.setUser_name(name);
		user.setPassword(password);
		user.setIndividual_resume(individual_resume_et2.getText().toString());
		user.setBirthday(birthday);
		user.setSex(sex);

		user.update(this, Data.getId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				Log.i("bmob", "更新成功：");
				finish_change();
			}

			@Override
			public void onFailure(int code, String msg) {
				Log.i("bmob", "更新失败：" + msg);
			}
		});

		Data.setUser_name(name);

		Intent intent = new Intent();
		intent.setAction("action.recreate");
		sendBroadcast(intent);
	}

	private void finish_change() {
		// TODO Auto-generated method stub finish_change

		if (Data.getOther_name().toString().equals("")||Data.getOther_name().equals(Data.getUser_name())) {
			change.setText("修改");
			password_tv.setText("点击右上修改");
			username = Data.getUser_name();
			textView_password.setText(R.string.password2);
			out_login.setText(R.string.out_login);
		} else {
				change.setText("");
				out_login.setText("关注");
				username = Data.getOther_name();
				update_focus();
		}

		back.setBackgroundResource(R.drawable.ic_back);
		cancel.setText("");

		date_between1.setText("");
		date_between2.setText("");
		name_et.setFocusable(false);
		name_et.setFocusableInTouchMode(false);
		password_et.setFocusable(false);
		password_et.setFocusableInTouchMode(false);
		individual_resume_et2.setFocusable(false);
		individual_resume_et2.setFocusableInTouchMode(false);
		birthday_et_year.setFocusable(false);
		birthday_et_year.setFocusableInTouchMode(false);
		birthday_et_month.setFocusable(false);
		birthday_et_month.setFocusableInTouchMode(false);
		birthday_et_day.setFocusable(false);
		birthday_et_day.setFocusableInTouchMode(false);

		name_et.setHint("");
		individual_resume_et2.setHint("");
		birthday_et_year.setHint("");
		birthday_et_month.setHint("");
		birthday_et_day.setHint("");
		name_et.setText("");
		individual_resume_et2.setText("");
		birthday_et_year.setText("");
		birthday_et_month.setText("");
		birthday_et_day.setText("");
		password_et.setText("");

		sex_rg.removeView(male);
		sex_rg.removeView(female);
		sex_rg.invalidate();

		BmobQuery<User> query = new BmobQuery<User>();
		query.addWhereEqualTo("user_name", username);
		query.findObjects(User_information.this, new FindListener<User>() {

			@Override
			public void onSuccess(List<User> object) {
				for (User user : object) {
					name_tv.setText(user.getUser_name());
					sex_tv.setText(user.getSex());
					sex = user.getSex();
					birthday_tv.setText(user.getBirthday());
					individual_resume_tv2.setText(user.getIndividual_resume());
					Data.setPassword(user.getPassword());
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(User_information.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub onClick
		Animation alpha = new AlphaAnimation(0.5f, 1.0f);
		alpha.setDuration(200);

		switch (v.getId()) {
		case R.id.back:
			back.startAnimation(alpha);

			if (cancel.getText().equals("")) {
				Intent intent = new Intent();
				intent.setAction("action.recreate");
				sendBroadcast(intent);
				onBackPressed();
			} else {
				finish_change();
			}

			break;

		case R.id.out_login:
			if (Utils.isFastDoubleClick()) {
		        return;
		    }
			if (Data.getUser_name().equals("")) {
				out_login.startAnimation(alpha);
				Toast.makeText(User_information.this, "请先登录", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(User_information.this, Login.class));
			}else {
				if (out_login.getText().equals("退出登录")) {
					out_login.startAnimation(alpha);
					Data.setUser_name("");
					Data.setUser_head("");
					Intent intent = new Intent();
					intent.setAction("action.recreate");
					sendBroadcast(intent);
					onBackPressed();
				}else {
					update_focus();
				}
			}
			break;

		case R.id.change:
			if (Utils.isFastDoubleClick()) {
		        return;
		    }
			change.startAnimation(alpha);
			if (change.getText().equals("修改")) {
				change();
			} else if (change.getText().equals("完成")) {
				check();

				InputMethodManager imm = (InputMethodManager) v.getContext() // 隐藏输入法
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm.isActive()) {
					imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

				}
			}
			break;
		}

	};

	private void check() {
		// TODO Auto-generated method stub check
		
		name = name_et.getText().toString().replace(" ", "");
		password = password_et.getText().toString().replace(" ", "");
		

		if (name.equals("")) {
			name=Data.getUser_name().toString();
		}

		if (name.equals(Data.getUser_name().toString())) {
			if_username_used = false;
			checkinput();
		} else {
			BmobQuery<User> query1 = new BmobQuery<User>();
			query1.addWhereEqualTo("user_name", name);
			query1.findObjects(User_information.this, new FindListener<User>() {
				@Override
				public void onSuccess(List<User> arg0) {

					if (arg0.size() == 0) {
						if_username_used = false;
					}
					checkinput();
				}

				@Override
				public void onError(int arg0, String arg1) {
					Toast.makeText(User_information.this, "user_name查询失败", Toast.LENGTH_SHORT).show();
				}
			});
		}

	}

	public void checkinput() {

		if (birthday_et_year.getText().toString().equals("")) {
			birthday_et_year.setText(birthday_et_year.getHint().toString());
		}
		if (birthday_et_month.getText().toString().equals("")) {
			birthday_et_month.setText(birthday_et_month.getHint().toString());
		}
		if (birthday_et_day.getText().toString().equals("")) {
			birthday_et_day.setText(birthday_et_day.getHint().toString());
		}

		Integer year = Integer.parseInt(birthday_et_year.getText().toString());
		Integer month = Integer.parseInt(birthday_et_month.getText().toString());
		Integer day = Integer.parseInt(birthday_et_day.getText().toString());

		if (year < 1800 || year > 2016 || month < 1 || month > 12 || day < 1) {
			Toast.makeText(User_information.this, "日期输入有误", Toast.LENGTH_SHORT).show();
		} else if (if_username_used == true) {
			Toast.makeText(User_information.this, "昵称已被使用", Toast.LENGTH_SHORT).show();
		} else {
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				if (day < 32) {
					update();
				} else {
					Toast.makeText(User_information.this, "日期输入有误", Toast.LENGTH_SHORT).show();
				}

				break;

			case 4:
			case 6:
			case 9:
			case 11:
				if (day < 31) {
					update();
				} else {
					Toast.makeText(User_information.this, "日期输入有误", Toast.LENGTH_SHORT).show();
				}

				break;

			case 2:
				if (year % 4 == 0 && day < 30 || year % 4 != 0 && day < 29) {
					update();
				} else {
					Toast.makeText(User_information.this, "日期输入有误", Toast.LENGTH_SHORT).show();
				}

				break;

			default:
				break;
			}
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub onCheckedChanged
		switch (arg1) {
		case R.id.radio0:
			sex = "男";
			break;

		case R.id.radio1:
			sex = "女";
			break;

		default:
			break;
		}
	}

	public void onBackPressed() {
		Data.setOther_name("");
		super.onBackPressed();
		finish();
	}
	
	private void update_focus() {
		// TODO Auto-generated method stub update_history

		BmobQuery<Focus_people> query = new BmobQuery<Focus_people>();
		BmobQuery<Focus_people> query1 = new BmobQuery<Focus_people>();
		BmobQuery<Focus_people> query2 = new BmobQuery<Focus_people>();
		List<BmobQuery<Focus_people>> andQuerys = new ArrayList<BmobQuery<Focus_people>>();
		query1.addWhereEqualTo("host_id", Data.getUser_id());
		query2.addWhereEqualTo("focus_people_name", Data.getOther_name());
		andQuerys.add(query1);
		andQuerys.add(query2);
		query.and(andQuerys);
		query.findObjects(User_information.this, new FindListener<Focus_people>() {

			@Override
			public void onSuccess(List<Focus_people> object) {
				if (object.size() == 0) {
					if (check_or_update.equals("check")) {
						check_or_update = "update";
					} else {
						new_focus();

						out_login.setText("已关注");

						ScaleAnimation animation1 = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
								Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
						animation1.setDuration(1000);
						ScaleAnimation animation2 = new ScaleAnimation(1.4f, 1.0f, 1.4f, 1.0f,
								Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
						animation2.setDuration(1000);
						animation2.setInterpolator(new BounceInterpolator());
						out_login.startAnimation(animation1);
						out_login.startAnimation(animation2);
					}
				} else {
					for (Focus_people focus_people : object) {
						if (check_or_update.equals("check")) {
							check_or_update = "update";
							out_login.setText("已关注");
						} else {
							delete_one_focus(focus_people.getObjectId());

							out_login.setText("关注");

							ScaleAnimation animation1 = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
									Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
							animation1.setDuration(1000);
							ScaleAnimation animation2 = new ScaleAnimation(1.4f, 1.0f, 1.4f, 1.0f,
									Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
							animation2.setDuration(1000);
							animation2.setInterpolator(new BounceInterpolator());
							out_login.startAnimation(animation1);
							out_login.startAnimation(animation2);
						}
					}
				}
			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(User_information.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});

	}
	
	private void new_focus() {
		Focus_people focus_people = new Focus_people();
		focus_people.setHost_id(Data.getUser_id());
		focus_people.setFocus_people_name(Data.getOther_name());
		focus_people.save(User_information.this, new SaveListener() {
			@Override
			public void onSuccess() {
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(User_information.this, "添加关注失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void delete_one_focus(String s) {

		Focus_people focus_people = new Focus_people();
		focus_people.delete(this, s, new DeleteListener() {

			@Override
			public void onSuccess() {
				Log.i("bmob", "取消收藏成功：");
			}

			@Override
			public void onFailure(int code, String msg) {
				Log.i("bmob", "取消收藏失败：" + msg);
			}
		});

	}

}
