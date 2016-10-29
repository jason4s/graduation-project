package com.example.jason4s;

import java.util.List;

import com.example.jason4s.R;
import com.example.javabean.User;
import com.example.tool.Data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class Login extends Activity {
	private EditText user_id;
	private EditText password;
	private boolean flag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_login);

		user_id = (EditText) findViewById(R.id.user_id_et);
		password = (EditText) findViewById(R.id.user_password_et);

		findViewById(R.id.zhuce).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (flag == true) {
					Animation alpha = new AlphaAnimation(0.5f, 1.0f);
					alpha.setDuration(200);
					findViewById(R.id.zhuce).startAnimation(alpha);
					startActivity(new Intent(Login.this, Register.class));
				}
			}

		});

		findViewById(R.id.login).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				flag = false;
				Animation alpha = new AlphaAnimation(0.5f, 1.0f);
				alpha.setDuration(200);
				findViewById(R.id.login).startAnimation(alpha);
				if (user_id.getText().toString().equals("") || password.getText().toString().equals("")) {
					Toast.makeText(Login.this, "密码或用户名不能为空", Toast.LENGTH_SHORT).show();
					flag = true;
					return;
				}
				BmobQuery<User> query = new BmobQuery<User>();
				query.addWhereEqualTo("user_id", user_id.getText().toString());
				query.findObjects(Login.this, new FindListener<User>() {

					@Override
					public void onSuccess(List<User> object) {
						// TODO Auto-generated method stub
						if (object.size() == 0) {
							Toast.makeText(Login.this, "用户名不存在", Toast.LENGTH_SHORT).show();
						}

						for (User user : object) {
							user.getPassword();

							if (user.getPassword().toString().equals(password.getText().toString())) {
								Toast.makeText(Login.this, "欢迎回来，" + user.getUser_name().toString(), Toast.LENGTH_SHORT)
										.show();
								Data.setUser_id(user.getUser_id().toString());
								Data.setUser_name(user.getUser_name().toString());
								Data.setId(user.getObjectId().toString());
								Intent intent = new Intent();
								intent.setAction("action.recreate");
								sendBroadcast(intent);
								onBackPressed();
							} else {
								Toast.makeText(Login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
							};

						}

					}

					@Override
					public void onError(int code, String msg) {
						// TODO Auto-generated method stub
						Toast.makeText(Login.this, "登录错误", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		flag = true;
	}

}
