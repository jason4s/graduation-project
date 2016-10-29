package com.example.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.jason4s.Login;
import com.example.jason4s.R;
import com.example.javabean.*;
import com.example.page.Information_list.MapComparator;
import com.example.tool.CircleImageView;
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
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Post extends Activity implements OnItemClickListener, OnScrollListener {

	private FrameLayout login;
	private Button left;
	private FrameLayout search;
	private ImageView search_iv;
	private TextView search_tv;
	private TextView order_change_b;
	private TextView order_change_tv;
	private ImageView search_bg_iv;
	private TextView search_bg_tv;
	private TextView login_tv;
	private com.example.tool.CircleImageView login_iv;
	private ListView List;
	private SimpleAdapter sim;
	private Map<String, String> map;
	private boolean flag = true; // 可否继续加载
	private boolean flag2 = false; // 可否刷新
	private FrameLayout search_frameLayout;
	private EditText reply;
	private Button reply_button;
	private String get_or_send = "get";
	private String reply_user_name = "";
	private ImageView collection;
	private String check_or_update = "check";
	private String reply_or_collection = "";
	private TextView title;

	private static List<Map<String, String>> datalist2 = new ArrayList<Map<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub onCreate
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_post);

		login = (FrameLayout) findViewById(R.id.login);
		left = (Button) findViewById(R.id.left);
		search = (FrameLayout) findViewById(R.id.search);
		search_iv = (ImageView) findViewById(R.id.search_iv);
		search_tv = (TextView) findViewById(R.id.search_tv);
		order_change_b = (TextView) findViewById(R.id.order_change_b);
		order_change_tv = (TextView) findViewById(R.id.order_change_tv);
		search_bg_iv = (ImageView) findViewById(R.id.search_bg_iv);
		search_bg_tv = (TextView) findViewById(R.id.search_bg_tv);
		login_tv = (TextView) findViewById(R.id.login_tv);
		login_iv = (CircleImageView) findViewById(R.id.login_iv);
		List = (ListView) findViewById(R.id.listView);
		search_frameLayout = (FrameLayout) findViewById(R.id.search_frameLayout);
		reply = (EditText) findViewById(R.id.reply);
		reply_button = (Button) findViewById(R.id.reply_button);
		collection = (ImageView) findViewById(R.id.collection);
		title = (TextView) findViewById(R.id.title);

		if (Data.getWhich() == 17) {
			title.setMaxHeight(0);
		} else {
			Intent intent = getIntent();
			String StringE = intent.getStringExtra("title");
			title.setText(StringE);
		}

		update_collection();

		Data.getDatalist2().clear();

		sim = new SimpleAdapter(Post.this, Data.getDatalist2(), R.layout.item,
				new String[] { "see_message", "tv", "order_tv", "type", "order", "message_order", "reply_tv",
						"reply_user_id_tv", "id_tv" },
				new int[] { R.id.see_message, R.id.tv, R.id.order_tv, R.id.type, R.id.order, R.id.message_order,
						R.id.reply_tv, R.id.reply_user_id_tv, R.id.id_tv }) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView id_tv = (TextView) view.findViewById(R.id.id_tv);
				TextView see_message = (TextView) view.findViewById(R.id.see_message);
				TextView type = (TextView) view.findViewById(R.id.type);
				TextView order1 = (TextView) view.findViewById(R.id.order);
				String othername = id_tv.getText().toString();
				id_tv.setTag(othername);
				id_tv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Data.setOther_name(v.getTag().toString());
						Intent intent = new Intent(Post.this, User_information.class);
						Post.this.startActivity(intent);
					}
				});
				see_message.setTag(R.id.tag_first, type.getText().toString());
				see_message.setTag(R.id.tag_second, order1.getText().toString());
				see_message.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Data.setType(arg0.getTag(R.id.tag_first).toString());
						Data.setOrder(Integer.parseInt(arg0.getTag(R.id.tag_second).toString()));
						Data.setWhich(0);
						
						reply_or_collection="";
						check_or_update="check";
						update_collection();
						find_title();
						update();
					}
				});

				return view;
			}
		};
		List.setAdapter(sim);
		List.setOnItemClickListener(this);
		List.setOnScrollListener(this);

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("action.recreate");
		registerReceiver(mRefreshBroadcastReceiver, intentFilter);

		datalist2.clear();
		Data.setTimes(10);
		if (Data.getWhich() == 17) {
			getreply();
		} else {
			getpost_message();
		}

		left.setText("返回");
		search.setAlpha(0f);
		search_iv.setAlpha(0f);
		search_tv.setAlpha(0f);
		search_bg_tv.setAlpha(1f);
		order_change_tv.setText("正序");

		if (Data.getWhich() == 17) {
			search_bg_tv.setText("");
			reply.setHint("");
		} else {
			search_bg_tv.setText("回复主题");
			reply.setHint("回复主题");
		}

		if (Data.getUser_name() == "") {
			login_tv.setText("登录");
			login.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub login_onclick
					Animation alpha = new AlphaAnimation(0.5f, 1.0f);
					alpha.setDuration(200);
					login.startAnimation(alpha);
					if (flag2 == true) {
						Intent intent = new Intent(Post.this, Login.class);
						Post.this.startActivity(intent);
					}
				}
			});

			collection.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (Data.getWhich() == 17) {

					} else {
						Toast.makeText(Post.this, "请先登录", Toast.LENGTH_SHORT).show();
						startActivity(new Intent(Post.this, Login.class));
					}
				}
			});

			reply_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Toast.makeText(Post.this, "请先登录", Toast.LENGTH_SHORT).show();
					Animation alpha = new AlphaAnimation(0.5f, 1.0f);
					alpha.setDuration(200);
					reply_button.startAnimation(alpha);

					Intent intent = new Intent(Post.this, Login.class);
					Post.this.startActivity(intent);
				}
			});

			search_tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Animation alpha = new AlphaAnimation(0.5f, 1.0f);
					alpha.setDuration(200);
					search_frameLayout.startAnimation(alpha);

					if (Data.getWhich() == 17) {

					} else {
						Toast.makeText(Post.this, "请先登录", Toast.LENGTH_SHORT).show();
						reply_user_name = "";

						Intent intent = new Intent(Post.this, Login.class);
						Post.this.startActivity(intent);
					}
				}
			});
		} else {
			if (Data.getUser_head() == "") {
				login_iv.setImageResource(R.drawable.head_login);
			}
			login_tv.setText("");
			login.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub head_onclick
					Animation alpha = new AlphaAnimation(0.5f, 1.0f);
					alpha.setDuration(200);
					login.startAnimation(alpha);
					if (flag2 == true) {
						Intent intent = new Intent(Post.this, User_information.class);
						Post.this.startActivity(intent);
					}
				}
			});

			collection.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub collection_onclick
					if (Utils.isFastDoubleClick()) {
						return;
					}
					if (Data.getWhich() == 17) {

					} else {
						reply_or_collection = "collection";
						update_collection();
					}
				}
			});

			reply_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub reply_button_onclick
					Animation alpha = new AlphaAnimation(0.5f, 1.0f);
					alpha.setDuration(200);
					reply_button.startAnimation(alpha);

					reply_or_collection = "reply";

					if (reply.getText().toString().equals("")) {
						Toast.makeText(Post.this, "回复不能为空", Toast.LENGTH_SHORT).show();
					} else if (reply.getHint().equals("")) {
						Toast.makeText(Post.this, "请选择回复对象", Toast.LENGTH_SHORT).show();
					} else {
						if (flag2 == true) {
							flag2 = false;
							datalist2.clear();
							Data.getDatalist2().clear();
							get_or_send = "send";
							getpost_message();
							flag = true;
						} else {
							Toast.makeText(Post.this, "请等刷新完成再回复", Toast.LENGTH_SHORT).show();
						}

					}
				}
			});

			search_tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub search_onclick
					Animation alpha = new AlphaAnimation(0.5f, 1.0f);
					alpha.setDuration(200);
					search_frameLayout.startAnimation(alpha);

					if (Data.getWhich() == 17) {

					} else {
						reply_user_name = "";
						reply.setHint("回复主题");
					}
				}
			});
		}

		order_change_b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub order_change_b_onclick
				Animation alpha = new AlphaAnimation(0.5f, 1.0f);
				alpha.setDuration(200);
				order_change_tv.startAnimation(alpha);
				RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				animation.setDuration(1000);
				animation.setRepeatCount(2);
				search_bg_iv.startAnimation(animation);
				Toast.makeText(Post.this, "正在刷新", Toast.LENGTH_SHORT).show();

				if (flag2 == true) {
					if (order_change_tv.getText().equals("正序")) {
						order_change_tv.setText("倒序");
						update();
					} else {
						order_change_tv.setText("正序");
						update();
					}
				}
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

		search_iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub search_iv_onclick
				if (flag2 == true) {
					RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					animation.setDuration(1000);
					animation.setRepeatCount(2);
					search_bg_iv.startAnimation(animation);
					Toast.makeText(Post.this, "正在刷新", Toast.LENGTH_SHORT).show();

					update();
				}
			}
		});

	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub onScrollStateChanged

		switch (arg1) {
		case SCROLL_STATE_TOUCH_SCROLL: // 手指滚动状态
			break;

		case SCROLL_STATE_FLING: // 手指不动了，但是屏幕还在滚动状态
			break;

		case SCROLL_STATE_IDLE: // 静止状态
			if (flag == true && List.getLastVisiblePosition() == List.getCount() - 1) {
				List.setEnabled(false);
				Toast.makeText(Post.this, "正在加载", Toast.LENGTH_SHORT).show();
				flag2 = false;

				if (Data.getTimes() + 5 >= datalist2.size()) {

					for (int i = Data.getTimes(); i < datalist2.size(); i++) {
						Data.getDatalist2().add(datalist2.get(i));
					}
					sim.notifyDataSetChanged();
					flag = false;
					Toast.makeText(Post.this, "已加载全部信息", Toast.LENGTH_SHORT).show();

				} else {

					for (int i = Data.getTimes(); i < Data.getTimes() + 5; i++) {
						Data.getDatalist2().add(datalist2.get(i));
					}
					Data.setTimes(Data.getTimes() + 5);
					sim.notifyDataSetChanged();
				}

				flag2 = true;
				List.setEnabled(true);
			}
			break;

		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub onItemClick
		TextView type = (TextView) arg1.findViewById(R.id.type);
		TextView order = (TextView) arg1.findViewById(R.id.order);
		TextView message_order = (TextView) arg1.findViewById(R.id.message_order);

		get_one_post_message(type.getText().toString(), Integer.parseInt(order.getText().toString()),
				Integer.parseInt(message_order.getText().toString()));
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
		if (Data.getWhich() == 0) {
			Data.setWhich(17);
			update();
			title.setText("");
			title.setMaxHeight(0);
		} else {
			search_bg_tv.setText("发表主题");
			super.onBackPressed();
			finish();
		}
	};

	private void getpost_message() {
		// TODO Auto-generated method stub getinformation
		collection.setAlpha(1f);

		BmobQuery<Post_message> query = new BmobQuery<Post_message>();
		BmobQuery<Post_message> query1 = new BmobQuery<Post_message>();
		BmobQuery<Post_message> query2 = new BmobQuery<Post_message>();
		List<BmobQuery<Post_message>> andQuerys = new ArrayList<BmobQuery<Post_message>>();
		query1.addWhereEqualTo("type", Data.getType());
		query2.addWhereEqualTo("order", Data.getOrder());
		andQuerys.add(query1);
		andQuerys.add(query2);
		query.and(andQuerys);
		query.findObjects(Post.this, new FindListener<Post_message>() {

			@Override
			public void onSuccess(List<Post_message> object) {
				if (object.size() == 0) {
					if (get_or_send == "send") {
						post_message_update(1);
						get_or_send = "get";
					} else {
						flag = false;
						flag2 = true;
						Toast.makeText(Post.this, "没有信息", Toast.LENGTH_SHORT).show();
						sim.notifyDataSetChanged();
						List.setEnabled(true);
					}
				} else {

					for (Post_message post_message : object) {

						map = new HashMap<String, String>();
						map.put("tv", post_message.getMessage());
						map.put("order_tv", "#" + post_message.getMessage_order());
						map.put("type", post_message.getType() + "");
						map.put("order", post_message.getOrder() + "");
						map.put("message_order", post_message.getMessage_order() + "");
						map.put("reply_tv", "回复：");
						map.put("reply_user_id_tv", post_message.getReply_user_name());
						map.put("id_tv", post_message.getThis_user_name());
						datalist2.add(map);
					}

					if (get_or_send == "send") {
						post_message_update(datalist2.size() + 1);
						get_or_send = "get";
					} else {

						Collections.sort(datalist2, new MapComparator());

						if (order_change_tv.getText().equals("正序")) {
							Collections.reverse(datalist2);
						}

						if (Data.getTimes() >= datalist2.size()) {
							Data.setTimes(datalist2.size());
							flag = false;
						}

						for (int i = 0; i < Data.getTimes(); i++) {
							Data.getDatalist2().add(datalist2.get(i));
						}

						sim.notifyDataSetChanged();
						flag2 = true;
						List.setEnabled(true);
					}
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Post.this, "读取出错：code =" + ",msg = ", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void get_one_post_message(String type, int order, int message_order) {
		// TODO Auto-generated method stub getinformation

		BmobQuery<Post_message> query = new BmobQuery<Post_message>();
		BmobQuery<Post_message> query1 = new BmobQuery<Post_message>();
		BmobQuery<Post_message> query2 = new BmobQuery<Post_message>();
		BmobQuery<Post_message> query3 = new BmobQuery<Post_message>();
		List<BmobQuery<Post_message>> andQuerys = new ArrayList<BmobQuery<Post_message>>();
		query1.addWhereEqualTo("type", type);
		query2.addWhereEqualTo("order", order);
		query3.addWhereEqualTo("message_order", message_order);
		andQuerys.add(query1);
		andQuerys.add(query2);
		andQuerys.add(query3);
		query.and(andQuerys);
		query.findObjects(Post.this, new FindListener<Post_message>() {

			@Override
			public void onSuccess(List<Post_message> object) {
				if (object.size() == 0) {
					recreate();
					Toast.makeText(Post.this, "该消息已被删除", Toast.LENGTH_SHORT).show();
				}

				for (Post_message post_message : object) {
					if (!post_message.getThis_user_name().equals(Data.getUser_name())) {
						reply_user_name = post_message.getThis_user_name();
						reply.setHint("回复" + post_message.getThis_user_name());
					}
				}
			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Post.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void post_message_update(int order) {
		// TODO Auto-generated method stub post_message_update
		Post_message post_message = new Post_message();
		post_message.setMessage(reply.getText().toString());
		post_message.setType(Data.getType());
		post_message.setOrder(Data.getOrder());
		post_message.setMessage_order(order);
		post_message.setThis_user_name(Data.getUser_name().toString());
		post_message.setReply_user_name(reply_user_name);
		post_message.save(Post.this, new SaveListener() {
			@Override
			public void onSuccess() {
				Toast.makeText(Post.this, "回复成功", Toast.LENGTH_SHORT).show();
				reply.setText("");

				reply_or_collection = "reply";
				find_title();
				update();
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(Post.this, "发表错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void getreply() {
		// TODO Auto-generated method stub getinformation
		collection.setAlpha(0f);

		BmobQuery<Post_message> query = new BmobQuery<Post_message>();
		query.addWhereEqualTo("reply_user_name", Data.getUser_name());
		query.findObjects(Post.this, new FindListener<Post_message>() {

			@Override
			public void onSuccess(List<Post_message> object) {
				if (object.size() == 0) {
					flag = false;
					flag2 = true;
					Toast.makeText(Post.this, "没有信息", Toast.LENGTH_SHORT).show();
					sim.notifyDataSetChanged();
					List.setEnabled(true);
				} else if (Data.getUser_name().equals("")) {
					flag = false;
					flag2 = true;
				} else {

					for (Post_message post_message : object) {

						map = new HashMap<String, String>();
						map.put("see_message", " 查 \n看\n主\n题");
						map.put("tv", post_message.getMessage());
						map.put("order_tv", "#" + post_message.getMessage_order());
						map.put("type", post_message.getType() + "");
						map.put("order", post_message.getOrder() + "");
						map.put("message_order", post_message.getMessage_order() + "");
						map.put("reply_tv", "回复：");
						map.put("reply_user_id_tv", post_message.getReply_user_name());
						map.put("id_tv", post_message.getThis_user_name());
						datalist2.add(map);

					}

					Collections.sort(datalist2, new MapComparator());

					if (order_change_tv.getText().equals("正序")) {
						Collections.reverse(datalist2);
					}

					if (Data.getTimes() >= datalist2.size()) {
						Data.setTimes(datalist2.size());
						flag = false;
					}

					for (int i = 0; i < Data.getTimes(); i++) {
						Data.getDatalist2().add(datalist2.get(i));
					}

					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Post.this, "读取出错：code =" + ",msg = ", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void update() {
		flag2 = false;
		datalist2.clear();
		Data.getDatalist2().clear();
		Data.setTimes(10);

		if (Data.getWhich() == 17) {
			getreply();
		} else {
			getpost_message();
		}
		flag = true;
	}

	private void find_title() {
		BmobQuery<Post_title> query = new BmobQuery<Post_title>();
		BmobQuery<Post_title> query1 = new BmobQuery<Post_title>();
		BmobQuery<Post_title> query2 = new BmobQuery<Post_title>();
		List<BmobQuery<Post_title>> andQuerys = new ArrayList<BmobQuery<Post_title>>();
		query1.addWhereEqualTo("type", Data.getType());
		query2.addWhereEqualTo("order", Data.getOrder());
		andQuerys.add(query1);
		andQuerys.add(query2);
		query.and(andQuerys);
		query.findObjects(Post.this, new FindListener<Post_title>() {

			@Override
			public void onSuccess(List<Post_title> object) {
				if (object.size() == 0) {
					Toast.makeText(Post.this, "信息已被删除", Toast.LENGTH_SHORT).show();
				} else {
					for (Post_title post_title : object) {
						if (reply_or_collection.equals("reply")) {
							update_title(post_title.getObjectId(), post_title.getLast_count(), post_title.getOrder());
						} else if (reply_or_collection.equals("collection")) {
							new_collection(post_title.getTitle(), post_title.getHost_user_name());
						} else {
							title.setMaxHeight(50);
							title.setText(post_title.getTitle());
						}

					}
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Post.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void update_title(String objectId, int last_count, int order) {

		Post_title post_title = new Post_title();
		post_title.setLast_count(last_count);
		post_title.setOrder(order);
		post_title.update(this, objectId, new UpdateListener() {

			@Override
			public void onSuccess() {
				Log.i("bmob", "更新成功：");
			}

			@Override
			public void onFailure(int code, String msg) {
				Log.i("bmob", "更新失败：" + msg);
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
		query2.addWhereEqualTo("post_type", Data.getType().toString());
		query3.addWhereEqualTo("information_order", Data.getOrder());
		andQuerys.add(query1);
		andQuerys.add(query2);
		andQuerys.add(query3);
		query.and(andQuerys);
		query.findObjects(Post.this, new FindListener<Collection>() {

			@Override
			public void onSuccess(List<Collection> object) {
				if (object.size() == 0) {
					if (check_or_update.equals("check")) {
						check_or_update = "update";
						collection.setBackgroundResource(R.drawable.collection_n);
					} else {
						reply_or_collection = "collection";
						find_title();

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
				Toast.makeText(Post.this, "读取错误1", Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void new_collection(String title, String host_user_name) {
		Collection collection = new Collection();
		collection.setUser_id(Data.getUser_id());
		collection.setPost_type(Data.getType());
		collection.setInformation_type("");
		collection.setInformation_order(Data.getOrder());
		collection.setHost_user_name(host_user_name);
		collection.setTitle(title);
		collection.save(Post.this, new SaveListener() {
			@Override
			public void onSuccess() {
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(Post.this, "添加收藏失败", Toast.LENGTH_SHORT).show();
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

}
