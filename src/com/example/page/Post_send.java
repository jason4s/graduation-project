package com.example.page;


import com.example.jason4s.R;
import com.example.javabean.*;
import com.example.tool.Data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.SaveListener;

public class Post_send extends Activity {
	private EditText title_et;
	private EditText message_et;
	private Button send;
	private int max;
	private Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_post_send);

		title_et = (EditText) findViewById(R.id.title_et);
		message_et = (EditText) findViewById(R.id.message_et);
		send = (Button) findViewById(R.id.send);
		back = (Button) findViewById(R.id.back);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Animation alpha = new AlphaAnimation(0.5f, 1.0f);
				alpha.setDuration(200);
				back.startAnimation(alpha);

				onBackPressed();
				finish();
			}
		});

		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub send_onclick
				Animation alpha = new AlphaAnimation(0.5f, 1.0f);
				alpha.setDuration(200);
				send.startAnimation(alpha);

				if (title_et.getText().length() < 3) {
					Toast.makeText(Post_send.this, "标题不能太短", Toast.LENGTH_SHORT).show();
				} else if (message_et.getText().length() < 3) {
					Toast.makeText(Post_send.this, "内容不能太短", Toast.LENGTH_SHORT).show();
				} else {
					getpost_title_count();
				}
			}

		});

	}

	private void getpost_title_count() {
		BmobQuery<Post_title> query = new BmobQuery<Post_title>();
		query.addWhereEqualTo("type", Data.getType());
		query.count(this, Post_title.class, new CountListener() {
			@Override
			public void onSuccess(int count) {
				max = count + 1;
				post_title_update();
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				Toast.makeText(Post_send.this, "查询出错：code =" + ",msg = " + msg, Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void post_title_update() {
		Post_title post_title = new Post_title();
		post_title.setTitle(title_et.getText().toString());
		post_title.setType(Data.getType().toString());
		post_title.setOrder(max);
		post_title.setHost_user_name(Data.getUser_name().toString());
		post_title.setLast_count(1);
		post_title.save(Post_send.this, new SaveListener() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				post_message_update();
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				Toast.makeText(Post_send.this, "发表错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void post_message_update() {
		// TODO Auto-generated method stub
		Post_message post_message = new Post_message();
		post_message.setMessage(message_et.getText().toString());
		post_message.setType(Data.getType().toString());
		post_message.setOrder(max);
		post_message.setMessage_order(1);
		post_message.setThis_user_name(Data.getUser_name().toString());
		post_message.save(Post_send.this, new SaveListener() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction("action.updatelist");
				sendBroadcast(intent);
				onBackPressed();
				Toast.makeText(Post_send.this, "发表成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				Toast.makeText(Post_send.this, "发表错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

}
