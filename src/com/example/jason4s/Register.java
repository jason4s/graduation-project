package com.example.jason4s;

import java.util.List;

import com.example.jason4s.R;
import com.example.javabean.User;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class Register extends Activity implements OnCheckedChangeListener {

	private EditText user_id_et;
	private EditText user_name_et;
	private EditText user_password_et;
	private EditText user_password_again_et;
	private RadioGroup sex_rg;
	private EditText birthday_et_year_et;
	private EditText birthday_et_month_et;
	private EditText birthday_et_day_et;
	private EditText individual_resume_et;
	private String sex = "男";
	private boolean if_userid_used = true;
	private boolean if_username_used = true;
	private String user_id;
	private String user_name;
	private String user_password;
	private String user_password_again;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_register);

		user_id_et = (EditText) findViewById(R.id.user_id_et);
		user_name_et = (EditText) findViewById(R.id.user_name_et);
		user_password_et = (EditText) findViewById(R.id.user_password_et);
		user_password_again_et = (EditText) findViewById(R.id.user_password_again_et);
		sex_rg = (RadioGroup) findViewById(R.id.sex_rg);
		birthday_et_year_et = (EditText) findViewById(R.id.birthday_et_year);
		birthday_et_month_et = (EditText) findViewById(R.id.birthday_et_month);
		birthday_et_day_et = (EditText) findViewById(R.id.birthday_et_day);
		individual_resume_et = (EditText) findViewById(R.id.individual_resume_et);

		sex_rg.setOnCheckedChangeListener(this);

		findViewById(R.id.zhuce).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Animation alpha = new AlphaAnimation(0.5f, 1.0f);
				alpha.setDuration(200);
				findViewById(R.id.zhuce).startAnimation(alpha);
				
				user_id = user_id_et.getText().toString().replace(" ", "");
				user_name = user_name_et.getText().toString().replace(" ", "");
				user_password = user_password_et.getText().toString().replace(" ", "");
				user_password_again = user_password_again_et.getText().toString().replace(" ", "");
				
				if (user_id.equals("")) {
					Toast.makeText(Register.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
				} else if (user_name.equals("")) {
					Toast.makeText(Register.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
				} else if (user_password.equals("")) {
					Toast.makeText(Register.this, "密码不能为空", Toast.LENGTH_SHORT).show();
				} else if (!user_password.equals(user_password_again)) {
					Toast.makeText(Register.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
				} else if (birthday_et_year_et.getText().toString().equals("")||
						birthday_et_month_et.getText().toString().equals("")||
						birthday_et_day_et.getText().toString().equals("")) {
					Toast.makeText(Register.this, "日期不能为空", Toast.LENGTH_SHORT).show();
				} else if (individual_resume_et.getText().toString().equals("")) {
					Toast.makeText(Register.this, "个性签名不能为空", Toast.LENGTH_SHORT).show();
				} else {
					checkinput();
				}
			}
		});

	}

	public void checkinput() {

		Integer year = Integer.parseInt(birthday_et_year_et.getText().toString());
		Integer month = Integer.parseInt(birthday_et_month_et.getText().toString());
		Integer day = Integer.parseInt(birthday_et_day_et.getText().toString());

		if (year < 1800 || year > 2016 || month < 1 || month > 12 || day < 1) {
			Toast.makeText(Register.this, "日期输入有误", Toast.LENGTH_SHORT).show();
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
					if_userid_used();
				} else {
					Toast.makeText(Register.this, "日期输入有误", Toast.LENGTH_SHORT).show();
				}

				break;

			case 4:
			case 6:
			case 9:
			case 11:
				if (day < 31) {
					if_userid_used();
				} else {
					Toast.makeText(Register.this, "日期输入有误", Toast.LENGTH_SHORT).show();
				}

				break;

			case 2:
				if (year % 4 == 0 && day < 30 || year % 4 != 0 && day < 29) {
					if_userid_used();
				} else {
					Toast.makeText(Register.this, "日期输入有误", Toast.LENGTH_SHORT).show();
				}

				break;

			default:
				break;
			}
		}
	}

	public void if_userid_used() {
		BmobQuery<User> query = new BmobQuery<User>();
		query.addWhereEqualTo("user_id", user_id);
		query.findObjects(Register.this, new FindListener<User>() {
			@Override
			public void onSuccess(List<User> arg0) {
				// TODO Auto-generated method stub

				if (arg0.size() == 0) {
					if_userid_used = false;
				}
				if_username_used();
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(Register.this, "user_id查询失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void if_username_used() {
		BmobQuery<User> query1 = new BmobQuery<User>();
		query1.addWhereEqualTo("user_name", user_name);
		query1.findObjects(Register.this, new FindListener<User>() {
			@Override
			public void onSuccess(List<User> arg0) {
				// TODO Auto-generated method stub
				if (arg0.size() == 0) {
					if_username_used = false;
				}
				zhuce();
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(Register.this, "user_name查询失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void zhuce() {
		if (if_userid_used == true) {
			Toast.makeText(Register.this, "用户名已被使用", Toast.LENGTH_SHORT).show();
		} else if (if_username_used == true) {
			Toast.makeText(Register.this, "昵称已被使用", Toast.LENGTH_SHORT).show();
		} else {
			String y = birthday_et_year_et.getText().toString();
			String m1 = birthday_et_month_et.getText().toString();
			String d1 = birthday_et_day_et.getText().toString();
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

			User user = new User();
			user.setUser_id(user_id);
			user.setPassword(user_password);
			user.setUser_name(user_name);
			user.setSex(sex);
			user.setBirthday(birthday);
			user.setIndividual_resume(individual_resume_et.getText().toString());
			user.save(Register.this, new SaveListener() {
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
					onBackPressed();
				}

				@Override
				public void onFailure(int code, String msg) {
					// TODO Auto-generated method stub
					Toast.makeText(Register.this, "注册错误", Toast.LENGTH_SHORT).show();
				}
			});

		}
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
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

}
