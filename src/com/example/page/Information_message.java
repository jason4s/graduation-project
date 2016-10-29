package com.example.page;

import java.util.ArrayList;
import java.util.List;

import com.example.jason4s.Login;
import com.example.jason4s.R;
import com.example.javabean.*;
import com.example.tool.Data;
import com.example.tool.Utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class Information_message extends Activity {
	private Button left;
	private Button right;
	private TextView title;
	private TextView message;
	private ImageView collection;
	private String check_or_update = "check";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_information);

		right = (Button) findViewById(R.id.right);
		left = (Button) findViewById(R.id.left);
		title = (TextView) findViewById(R.id.title);
		message = (TextView) findViewById(R.id.message);
		collection = (ImageView) findViewById(R.id.collection);

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("action.recreate");
		registerReceiver(mRefreshBroadcastReceiver, intentFilter);

		getinformation_item();

		update_collection();

		if (Data.getUser_name() == "") {

			collection.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Toast.makeText(Information_message.this, "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(Information_message.this, Login.class));
				}
			});
			
		} else {

			collection.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub collection_onclick
					if (Utils.isFastDoubleClick()) {
						return;
					}
					update_collection();
				}
			});
		}
		
		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub left_onclick
				Animation alpha = new AlphaAnimation(0.5f, 1.0f);
				alpha.setDuration(200);
				right.startAnimation(alpha);

				Intent intent = new Intent();
				intent.putExtra("title", title.getText().toString());
				intent.setClass(Information_message.this, Post.class);
				startActivity(intent);
			}
		});

		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub left_onclick
				Animation alpha = new AlphaAnimation(0.5f, 1.0f);
				alpha.setDuration(200);
				left.startAnimation(alpha);

				onBackPressed();
			}
		});
	}

	private void update_collection() {
		// TODO Auto-generated method stub update_history

		BmobQuery<Collection> query = new BmobQuery<Collection>();
		BmobQuery<Collection> query1 = new BmobQuery<Collection>();
		BmobQuery<Collection> query2 = new BmobQuery<Collection>();
		BmobQuery<Collection> query3 = new BmobQuery<Collection>();
		List<BmobQuery<Collection>> andQuerys = new ArrayList<BmobQuery<Collection>>();
		query1.addWhereEqualTo("user_id", Data.getUser_id());
		query2.addWhereEqualTo("information_type", Data.getType().toString());
		query3.addWhereEqualTo("information_order", Data.getOrder());
		andQuerys.add(query1);
		andQuerys.add(query2);
		andQuerys.add(query3);
		query.and(andQuerys);
		query.findObjects(Information_message.this, new FindListener<Collection>() {

			@Override
			public void onSuccess(List<Collection> object) {
				if (object.size() == 0) {
					if (check_or_update.equals("check")) {
						check_or_update = "update";
					} else {
						new_collection();

						collection.setBackgroundResource(R.drawable.collection_y);

						ScaleAnimation animation1 = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
								Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
						animation1.setDuration(1000);
						ScaleAnimation animation2 = new ScaleAnimation(1.4f, 1.0f, 1.4f, 1.0f,
								Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
						animation2.setDuration(1000);
						animation2.setInterpolator(new BounceInterpolator());
						collection.startAnimation(animation1);
						collection.startAnimation(animation2);
					}
				} else {
					for (Collection collection1 : object) {
						if (check_or_update.equals("check")) {
							check_or_update = "update";
							collection.setBackgroundResource(R.drawable.collection_y);
						} else {
							delete_one_collection(collection1.getObjectId());

							collection.setBackgroundResource(R.drawable.collection_n);

							ScaleAnimation animation1 = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
									Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
							animation1.setDuration(1000);
							ScaleAnimation animation2 = new ScaleAnimation(1.4f, 1.0f, 1.4f, 1.0f,
									Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
							animation2.setDuration(1000);
							animation2.setInterpolator(new BounceInterpolator());
							collection.startAnimation(animation1);
							collection.startAnimation(animation2);
						}
					}
				}
			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Information_message.this, "读取错误1", Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void new_collection() {
		Collection collection = new Collection();
		collection.setUser_id(Data.getUser_id());
		collection.setPost_type("");
		collection.setInformation_type(Data.getType().toString());
		collection.setInformation_order(Data.getOrder());
		collection.setHost_user_name("");
		collection.setTitle(title.getText().toString());
		collection.save(Information_message.this, new SaveListener() {
			@Override
			public void onSuccess() {
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(Information_message.this, "添加收藏失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void delete_one_collection(String s) {

		Collection collection = new Collection();
		collection.delete(this, s, new DeleteListener() {

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

	private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
		// TODO Auto-generated method stub mRefreshBroadcastReceiver

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("action.recreate")) {
				recreate();
			}
		}
	};

	public void onBackPressed() {
		// TODO Auto-generated method stub onBackPressed
		super.onBackPressed();
		finish();
	};

	private void getinformation_item() {
		BmobQuery<Information> query = new BmobQuery<Information>();
		BmobQuery<Information> query1 = new BmobQuery<Information>();
		BmobQuery<Information> query2 = new BmobQuery<Information>();
		List<BmobQuery<Information>> andQuerys = new ArrayList<BmobQuery<Information>>();
		query1.addWhereEqualTo("type", Data.getType().toString());
		query2.addWhereEqualTo("order", Data.getOrder());
		andQuerys.add(query1);
		andQuerys.add(query2);
		query.and(andQuerys);
		query.findObjects(Information_message.this, new FindListener<Information>() {

			@Override
			public void onSuccess(List<Information> object) {

				for (Information information : object) {
					title.setText(information.getTitle());
					message.setText(information.getMessage());
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Information_message.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
