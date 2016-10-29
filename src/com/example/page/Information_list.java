package com.example.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.jason4s.Login;
import com.example.jason4s.R;
import com.example.javabean.*;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
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

public class Information_list extends Activity implements OnItemClickListener, OnScrollListener {

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
	private TextView listView_bg;
	private FrameLayout search_frameLayout;
	private String post_type;
	private String information_type;
	private int information_order;
	private String title;
	private String host_user_name;
	private String in_type;
	private String job_id;
	private String job_string;
	private String cv_string;
	private String delete_update = "";
	private String flag3 = "false"; // 查看的简历是否被收藏
	private static List<Map<String, String>> datalist = new ArrayList<Map<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub onCreate
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_information_list);

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
		listView_bg = (TextView) findViewById(R.id.listView_bg);
		List = (ListView) findViewById(R.id.listView);
		search_frameLayout = (FrameLayout) findViewById(R.id.search_frameLayout);

		sim = new SimpleAdapter(Information_list.this, Data.getDatalist(), R.layout.item,
				new String[] { "tv", "id_tv", "order", "message_order", "type", "see_message", "see_message_2" },
				new int[] { R.id.tv, R.id.id_tv, R.id.order, R.id.message_order, R.id.type, R.id.see_message,
						R.id.see_message_2 }) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView see_message = (TextView) view.findViewById(R.id.see_message);
				TextView see_message_2 = (TextView) view.findViewById(R.id.see_message_2);
				TextView tv = (TextView) view.findViewById(R.id.tv);
				TextView order1 = (TextView) view.findViewById(R.id.order);

				see_message.setTag(R.id.tag_first, tv.getText().toString());
				see_message.setTag(R.id.tag_second, order1.getText().toString());
				see_message.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (Utils.isFastDoubleClick()) {
							return;
						}
						if (Data.getWhich() == 22) {
							delete_one_cv(arg0.getTag(R.id.tag_second).toString());
						} else if (Data.getWhich() == 20) {
							delete_one_job(arg0.getTag(R.id.tag_second).toString());
							update();
						} else if (Data.getWhich() == -12) {
							delete_update = "delete";
							get_cv_control(arg0.getTag(R.id.tag_second).toString());
						} else {
							Data.setWhich(-1);
							Data.getDatalist().clear();
							datalist.clear();
							Data.setTimes(10);
							host_user_name = arg0.getTag(R.id.tag_first).toString();
							flag2 = false;
							update();
							flag = true;
						}
					}
				});

				see_message_2.setTag(order1.getText().toString());
				see_message_2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						delete_update = "update";
						get_cv_control(arg0.getTag().toString());
					}
				});

				return view;
			}
		};

		List.setAdapter(sim);
		List.setOnScrollListener(this);
		List.setOnItemClickListener(this);

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("action.recreate");
		intentFilter.addAction("action.updatelist");
		registerReceiver(mRefreshBroadcastReceiver, intentFilter);

		update();

		if (Data.getWhich2() == 1) {
			job_string = "\n 删 \n除\n";
		}

		if (Data.getWhich2() == 2) {
			cv_string = "\n 收 \n藏\n";
		}

		if (Data.getWhich2() == 3) {
			flag3 = "true";
		}

		left.setText("返回");
		order_change_tv.setText("正序");

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
						Intent intent = new Intent(Information_list.this, Login.class);
						Information_list.this.startActivity(intent);
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
						Intent intent = new Intent(Information_list.this, User_information.class);
						Information_list.this.startActivity(intent);
					}
				}
			});

		}

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

		if (Data.getWhich() == 6 || Data.getWhich() == 10 || Data.getWhich() == 15 || Data.getWhich() == 22
				|| Data.getWhich() == 18 || Data.getWhich() == 19 || Data.getWhich() == 16 || Data.getWhich() == 21) {
			search.setAlpha(0f);
			search_iv.setAlpha(0f);
			search_tv.setAlpha(0f);
			search_bg_tv.setAlpha(1f);
			if (Data.getWhich() == 6 || Data.getWhich() == 10) {
				search_bg_tv.setText("发表主题");
			}
			if (Data.getWhich() == 22) {
				search_bg_tv.setText("新建简历");
				search_tv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub search_tv_onclick
						Animation alpha = new AlphaAnimation(0.5f, 1.0f);
						alpha.setDuration(200);
						search_frameLayout.startAnimation(alpha);
						Data.setWhich(0);

						Intent intent = new Intent(Information_list.this, Curriculum_vitae_update.class);
						Information_list.this.startActivity(intent);
					}
				});
			}
			if (Data.getUser_name() == "") {
				if (Data.getWhich() == 6 || Data.getWhich() == 10) {
					search_tv.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub search_tv_onclick
							Toast.makeText(Information_list.this, "请先登录", Toast.LENGTH_SHORT).show();
							Animation alpha = new AlphaAnimation(0.5f, 1.0f);
							alpha.setDuration(200);
							search_frameLayout.startAnimation(alpha);

							Intent intent = new Intent(Information_list.this, Login.class);
							Information_list.this.startActivity(intent);
						}
					});
				}
			} else {
				if (Data.getWhich() == 6 || Data.getWhich() == 10) {
					search_tv.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub search_tv_onclick
							Animation alpha = new AlphaAnimation(0.5f, 1.0f);
							alpha.setDuration(200);
							search_frameLayout.startAnimation(alpha);

							Intent intent = new Intent(Information_list.this, Post_send.class);
							Information_list.this.startActivity(intent);
						}
					});
				}
			}
			search_iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub search_iv_onclick
					if (Utils.isFastDoubleClick()) {
						return;
					}
					if (flag2 == true) {
						RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
								Animation.RELATIVE_TO_SELF, 0.5f);
						animation.setDuration(1000);
						animation.setRepeatCount(2);
						search_bg_iv.startAnimation(animation);
						Toast.makeText(Information_list.this, "正在刷新", Toast.LENGTH_SHORT).show();
						flag2 = false;
						update();
						flag = true;
					}
				}
			});

			order_change_b.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub order_change_b_onclick
					if (Utils.isFastDoubleClick()) {
						return;
					}
					Animation alpha = new AlphaAnimation(0.5f, 1.0f);
					alpha.setDuration(200);
					order_change_tv.startAnimation(alpha);
					RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					animation.setDuration(1000);
					animation.setRepeatCount(2);
					search_bg_iv.startAnimation(animation);
					Toast.makeText(Information_list.this, "正在刷新", Toast.LENGTH_SHORT).show();

					if (flag2 == true) {
						flag2 = false;
						if (order_change_tv.getText().equals("正序")) {
							order_change_tv.setText("倒序");
							update();
							flag = true;
						} else {
							order_change_tv.setText("正序");
							update();
							flag = true;
						}
					}
				}
			});

		} else {
			search.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub search_onclick
					Animation alpha = new AlphaAnimation(0.5f, 1.0f);
					alpha.setDuration(200);
					search_frameLayout.startAnimation(alpha);

					Intent intent = new Intent(Information_list.this, Search.class);
					Information_list.this.startActivity(intent);
				}
			});
		}
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub onScrollStateChanged

		switch (arg1) {
		case SCROLL_STATE_TOUCH_SCROLL: // 手指滚动状态̬
			break;

		case SCROLL_STATE_FLING: // 手指不动了，但是屏幕还在滚动状态
			break;

		case SCROLL_STATE_IDLE: // 静止状态
			if (flag == true && List.getLastVisiblePosition() == List.getCount() - 1) {
				List.setEnabled(false);
				flag2 = false;
				Toast.makeText(Information_list.this, "正在加载", Toast.LENGTH_SHORT).show();

				if (Data.getTimes() + 5 >= datalist.size()) {

					for (int i = Data.getTimes(); i < datalist.size(); i++) {
						Data.getDatalist().add(datalist.get(i));
					}
					sim.notifyDataSetChanged();
					flag = false;
					Toast.makeText(Information_list.this, "已加载全部信息", Toast.LENGTH_SHORT).show();

				} else {

					for (int i = Data.getTimes(); i < Data.getTimes() + 5; i++) {
						Data.getDatalist().add(datalist.get(i));
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
		if (Utils.isFastDoubleClick()) {
			return;
		}

		TextView order = (TextView) arg1.findViewById(R.id.order);
		TextView type = (TextView) arg1.findViewById(R.id.type);
		TextView tv = (TextView) arg1.findViewById(R.id.tv);
		TextView id_tv = (TextView) arg1.findViewById(R.id.id_tv);
		TextView message_order = (TextView) arg1.findViewById(R.id.message_order);

		if (Data.getWhich() == 6 || Data.getWhich() == 10 || Data.getWhich() == 15 || Data.getWhich() == -1) {

			Data.setType(type.getText().toString());
			Data.setOrder(Integer.parseInt(order.getText().toString()));

			title = tv.getText().toString();
			post_type = Data.getType().toString();
			information_type = "";
			information_order = Data.getOrder();
			host_user_name = id_tv.getText().toString();
			if (!Data.getUser_name().equals("")) {
				update_history();
			}

			Intent intent = new Intent();
			intent.putExtra("title", tv.getText().toString());
			intent.setClass(Information_list.this, Post.class);
			startActivity(intent);

		} else if (Data.getWhich() == 16 || Data.getWhich() == 21) {

			if (message_order.getText().toString().equals("")) {
				if (id_tv.getText().toString().equals("")) {
					Data.setType(type.getText().toString());
					Data.setOrder(Integer.parseInt(order.getText().toString()));

					title = tv.getText().toString();
					post_type = "";
					information_type = Data.getType().toString();
					information_order = Data.getOrder();
					host_user_name = "";
					job_id = "";
					if (!Data.getUser_name().equals("")) {
						update_history();
					}

					startActivity(new Intent(Information_list.this, Information_message.class));
				} else {

					Data.setType(type.getText().toString());
					Data.setOrder(Integer.parseInt(order.getText().toString()));

					title = tv.getText().toString();
					post_type = type.getText().toString();
					information_type = "";
					information_order = Data.getOrder();
					host_user_name = id_tv.getText().toString();
					job_id = "";
					if (!Data.getUser_name().equals("")) {
						update_history();
					}

					Intent intent = new Intent();
					intent.putExtra("title", tv.getText().toString());
					intent.setClass(Information_list.this, Post.class);
					startActivity(intent);

				}
			} else {
				Data.setTag(message_order.getText().toString());

				title = tv.getText().toString();
				post_type = "";
				information_type = "";
				information_order = 0;
				host_user_name = "职位";
				job_id = message_order.getText().toString();
				if (!Data.getUser_name().equals("")) {
					update_job_history();
				}

				Intent intent = new Intent();
				intent.setClass(Information_list.this, Job_show.class);
				startActivity(intent);
			}

		} else if (Data.getWhich() == 19) {

		} else if (Data.getWhich() == -5) {
			new_Cv_control(order.getText().toString());
		} else if (Data.getWhich() == 20) {
			if (Data.getWhich2() == 1) {
				Data.setWhich(-3);
				Intent intent = new Intent();
				intent.putExtra("job_id", order.getText().toString());
				intent.setClass(Information_list.this, Job_update.class);
				startActivity(intent);
			} else {
				Data.setWhich(-12);
				job_id = order.getText().toString();
				update();
			}
		} else if (Data.getWhich() == 22 || Data.getWhich() == -12) {
			Data.setId(order.getText().toString());
			startActivity(new Intent(Information_list.this, Curriculum_vitae_show.class));
		} else if (Data.getWhich() == 18) {
			Data.setOther_name(tv.getText().toString());
			startActivity(new Intent(Information_list.this, User_information.class));
		} else if (Data.getWhich() == 13 || Data.getWhich() == 1) {
			title = tv.getText().toString();
			post_type = "";
			information_type = "";
			information_order = 0;
			host_user_name = "职位";
			job_id = type.getText().toString();
			if (!Data.getUser_name().equals("")) {
				update_job_history();
			}
			Data.setTag(type.getText().toString());
			startActivity(new Intent(Information_list.this, Job_show.class));
		} else {
			Data.setType(type.getText().toString());
			Data.setOrder(Integer.parseInt(order.getText().toString()));

			title = tv.getText().toString();
			post_type = "";
			information_type = Data.getType().toString();
			information_order = Data.getOrder();
			host_user_name = "";
			if (!Data.getUser_name().equals("")) {
				update_history();
			}

			startActivity(new Intent(Information_list.this, Information_message.class));
		}

	}

	private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
		// TODO Auto-generated method stub mRefreshBroadcastReceiver

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("action.recreate")) {
				recreate();
			}
			if (action.equals("action.updatelist")) {
				if (flag2 == true) {
					RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					animation.setDuration(1000);
					animation.setRepeatCount(2);
					search_bg_iv.startAnimation(animation);
					Toast.makeText(Information_list.this, "正在刷新", Toast.LENGTH_SHORT).show();
					flag2 = false;
					update();
					flag = true;
				}
			}
		}
	};

	public void onBackPressed() {
		// TODO Auto-generated method stub onBackPressed
		Data.setTag("");
		if (Data.getWhich() == -1) {
			Data.setWhich(18);
			update();
		} else if (Data.getWhich() == -5) {
			Data.setWhich(Data.getWhich2());
			finish();
		} else if (Data.getWhich() == -12) {
			Data.setWhich(20);
			update();
		} else {
			List.setAlpha(1f);
			super.onBackPressed();
			listView_bg.setAlpha(0f);
			finish();
		}
	};

	private void getinformation() {
		// TODO Auto-generated method stub getinformation

		BmobQuery<Information> query = new BmobQuery<Information>();
		query.addWhereEqualTo("type", Data.getType());
		// query.addWhereEqualTo("order", Data.getNumber());
		query.findObjects(Information_list.this, new FindListener<Information>() {

			@Override
			public void onSuccess(List<Information> object) {
				if (object.size() == 0) {
					flag = false;
					Toast.makeText(Information_list.this, "没有信息", Toast.LENGTH_SHORT).show();
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				} else {

					for (Information information : object) {
						map = new HashMap<String, String>();
						map.put("tv", information.getTitle());
						map.put("order", information.getOrder() + "");
						map.put("type", information.getType());
						map.put("updateAt", information.getUpdatedAt());
						datalist.add(map);
					}

					Collections.sort(datalist, new MapComparator());
					if (order_change_tv.getText().equals("倒序")) {
						Collections.reverse(datalist);
					}

					if (Data.getTimes() >= datalist.size()) {
						Data.setTimes(datalist.size());
						flag = false;
					}

					for (int i = 0; i < Data.getTimes(); i++) {
						Data.getDatalist().add(datalist.get(i));
					}
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Information_list.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void getpost_title() {
		// TODO Auto-generated method stub getpost_title

		BmobQuery<Post_title> query = new BmobQuery<Post_title>();
		query.addWhereEqualTo("type", Data.getType());
		query.findObjects(Information_list.this, new FindListener<Post_title>() {

			@Override
			public void onSuccess(List<Post_title> object) {
				if (object.size() == 0) {
					flag = false;
					Toast.makeText(Information_list.this, "没有信息", Toast.LENGTH_SHORT).show();
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				} else {
					for (Post_title post_title : object) {
						map = new HashMap<String, String>();
						map.put("tv", post_title.getTitle());
						map.put("id_tv", post_title.getHost_user_name());
						map.put("order", post_title.getOrder() + "");
						map.put("type", post_title.getType());
						map.put("updateAt", post_title.getUpdatedAt());
						datalist.add(map);
					}

					Collections.sort(datalist, new MapComparator());

					if (order_change_tv.getText().equals("倒序")) {
						Collections.reverse(datalist);
					}

					if (Data.getTimes() >= datalist.size()) {
						Data.setTimes(datalist.size());
						flag = false;
					}

					for (int i = 0; i < Data.getTimes(); i++) {
						Data.getDatalist().add(datalist.get(i));
					}
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Information_list.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void get_mine() {
		// TODO Auto-generated method stub get_mine

		BmobQuery<Post_title> query = new BmobQuery<Post_title>();
		query.addWhereEqualTo("host_user_name", host_user_name);
		query.findObjects(Information_list.this, new FindListener<Post_title>() {

			@Override
			public void onSuccess(List<Post_title> object) {
				if (object.size() == 0) {
					flag = false;
					Toast.makeText(Information_list.this, "没有信息", Toast.LENGTH_SHORT).show();
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				} else {
					for (Post_title post_title : object) {
						map = new HashMap<String, String>();
						map.put("tv", post_title.getTitle());
						map.put("id_tv", post_title.getHost_user_name());
						map.put("order", post_title.getOrder() + "");
						map.put("type", post_title.getType() + "");
						map.put("updateAt", post_title.getUpdatedAt());
						datalist.add(map);
					}

					Collections.sort(datalist, new MapComparator());

					if (order_change_tv.getText().equals("倒序")) {
						Collections.reverse(datalist);
					}

					if (Data.getTimes() >= datalist.size()) {
						Data.setTimes(datalist.size());
						flag = false;
					}
					for (int i = 0; i < Data.getTimes(); i++) {
						Data.getDatalist().add(datalist.get(i));
					}

					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Information_list.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	static class MapComparator implements Comparator<Map<String, String>> {

		@Override
		public int compare(Map<String, String> o1, Map<String, String> o2) {
			String b1 = o1.get("updateAt");
			String b2 = o2.get("updateAt");
			if (b2 != null) {
				return b2.compareTo(b1);
			}
			return 0;
		}

	}

	static class MapComparator_createdAt implements Comparator<Map<String, String>> {

		@Override
		public int compare(Map<String, String> o1, Map<String, String> o2) {
			String b1 = o1.get("createdAt");
			String b2 = o2.get("createdAt");
			if (b2 != null) {
				return b2.compareTo(b1);
			}
			return 0;
		}

	}

	private void update_history() {
		// TODO Auto-generated method stub update_history

		if (post_type.equals("")) {
			BmobQuery<History> query = new BmobQuery<History>();
			BmobQuery<History> query1 = new BmobQuery<History>();
			BmobQuery<History> query2 = new BmobQuery<History>();
			BmobQuery<History> query3 = new BmobQuery<History>();
			List<BmobQuery<History>> andQuerys = new ArrayList<BmobQuery<History>>();
			query1.addWhereEqualTo("user_id", Data.getUser_id());
			query2.addWhereEqualTo("information_type", information_type);
			query3.addWhereEqualTo("information_order", information_order);
			andQuerys.add(query1);
			andQuerys.add(query2);
			andQuerys.add(query3);
			query.and(andQuerys);
			query.findObjects(Information_list.this, new FindListener<History>() {

				@Override
				public void onSuccess(List<History> object) {
					if (object.size() == 0) {
						new_history();
					} else {
						for (History history : object) {
							update_one_history(history.getObjectId());
						}
					}
				}

				@Override
				public void onError(int code, String msg) {
					Toast.makeText(Information_list.this, "读取错误1", Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			BmobQuery<History> query = new BmobQuery<History>();
			BmobQuery<History> query1 = new BmobQuery<History>();
			BmobQuery<History> query2 = new BmobQuery<History>();
			BmobQuery<History> query3 = new BmobQuery<History>();
			List<BmobQuery<History>> andQuerys = new ArrayList<BmobQuery<History>>();
			query1.addWhereEqualTo("user_id", Data.getUser_id());
			query2.addWhereEqualTo("post_type", post_type);
			query3.addWhereEqualTo("information_order", information_order);
			andQuerys.add(query1);
			andQuerys.add(query2);
			andQuerys.add(query3);
			query.and(andQuerys);
			query.findObjects(Information_list.this, new FindListener<History>() {

				@Override
				public void onSuccess(List<History> object) {
					if (object.size() == 0) {
						new_history();
					} else {
						for (History history : object) {
							update_one_history(history.getObjectId());
						}
					}
				}

				@Override
				public void onError(int code, String msg) {
					Toast.makeText(Information_list.this, "读取错误2", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	private void update_job_history() {
		// TODO Auto-generated method stub update_history

		if (post_type.equals("")) {
			BmobQuery<History> query = new BmobQuery<History>();
			BmobQuery<History> query1 = new BmobQuery<History>();
			BmobQuery<History> query2 = new BmobQuery<History>();
			List<BmobQuery<History>> andQuerys = new ArrayList<BmobQuery<History>>();
			query1.addWhereEqualTo("user_id", Data.getUser_id());
			query2.addWhereEqualTo("job_id", job_id);
			andQuerys.add(query1);
			andQuerys.add(query2);
			query.and(andQuerys);
			query.findObjects(Information_list.this, new FindListener<History>() {

				@Override
				public void onSuccess(List<History> object) {
					if (object.size() == 0) {
						new_history();
					} else {
						for (History history : object) {
							update_one_history(history.getObjectId());
						}
					}
				}

				@Override
				public void onError(int code, String msg) {
					Toast.makeText(Information_list.this, "读取错误1", Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			BmobQuery<History> query = new BmobQuery<History>();
			BmobQuery<History> query1 = new BmobQuery<History>();
			BmobQuery<History> query2 = new BmobQuery<History>();
			BmobQuery<History> query3 = new BmobQuery<History>();
			List<BmobQuery<History>> andQuerys = new ArrayList<BmobQuery<History>>();
			query1.addWhereEqualTo("user_id", Data.getUser_id());
			query2.addWhereEqualTo("post_type", post_type);
			query3.addWhereEqualTo("information_order", information_order);
			andQuerys.add(query1);
			andQuerys.add(query2);
			andQuerys.add(query3);
			query.and(andQuerys);
			query.findObjects(Information_list.this, new FindListener<History>() {

				@Override
				public void onSuccess(List<History> object) {
					if (object.size() == 0) {
						new_history();
					} else {
						for (History history : object) {
							update_one_history(history.getObjectId());
						}
					}
				}

				@Override
				public void onError(int code, String msg) {
					Toast.makeText(Information_list.this, "读取错误2", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	private void update_one_history(String s) {

		History History1 = new History();
		History1.setInformation_order(information_order);
		History1.update(this, s, new UpdateListener() {

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

	private void new_history() {
		History history = new History();
		history.setUser_id(Data.getUser_id());
		history.setPost_type(post_type);
		history.setInformation_type(information_type);
		history.setInformation_order(information_order);
		history.setHost_user_name(host_user_name);
		history.setTitle(title);
		history.setJob_id(job_id);
		history.save(Information_list.this, new SaveListener() {
			@Override
			public void onSuccess() {
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(Information_list.this, "添加历史记录失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void get_history() {
		// TODO Auto-generated method stub get_history

		BmobQuery<History> query = new BmobQuery<History>();
		query.addWhereEqualTo("user_id", Data.getUser_id());
		query.findObjects(Information_list.this, new FindListener<History>() {

			@Override
			public void onSuccess(List<History> object) {
				if (object.size() == 0) {
					flag = false;
					Toast.makeText(Information_list.this, "没有信息", Toast.LENGTH_SHORT).show();
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				} else {
					for (History history : object) {
						if (history.getHost_user_name().equals("")) {
							in_type = history.getInformation_type();
						} else {
							in_type = history.getPost_type();
						}
						map = new HashMap<String, String>();
						map.put("type", in_type);
						map.put("order", history.getInformation_order() + "");
						map.put("id_tv", history.getHost_user_name());
						map.put("tv", history.getTitle());
						map.put("updateAt", history.getUpdatedAt());
						map.put("message_order", history.getJob_id());
						datalist.add(map);

					}

					Collections.sort(datalist, new MapComparator());

					if (order_change_tv.getText().equals("倒序")) {
						Collections.reverse(datalist);
					}

					if (Data.getTimes() >= datalist.size()) {
						Data.setTimes(datalist.size());
						flag = false;
					}
					for (int i = 0; i < Data.getTimes(); i++) {
						Data.getDatalist().add(datalist.get(i));
					}

					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Information_list.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void get_collection() {
		// TODO Auto-generated method stub get_history

		BmobQuery<Collection> query = new BmobQuery<Collection>();
		query.addWhereEqualTo("user_id", Data.getUser_id());
		query.findObjects(Information_list.this, new FindListener<Collection>() {

			@Override
			public void onSuccess(List<Collection> object) {
				if (object.size() == 0) {
					flag = false;
					Toast.makeText(Information_list.this, "没有信息", Toast.LENGTH_SHORT).show();
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				} else {
					for (Collection collection : object) {
						if (collection.getHost_user_name().equals("")) {
							in_type = collection.getInformation_type();
						} else {
							in_type = collection.getPost_type();
						}
						map = new HashMap<String, String>();
						map.put("type", in_type);
						map.put("order", collection.getInformation_order() + "");
						map.put("id_tv", collection.getHost_user_name());
						map.put("tv", collection.getTitle());
						map.put("updateAt", collection.getUpdatedAt());
						map.put("message_order", collection.getJob_id());
						datalist.add(map);
					}

					Collections.sort(datalist, new MapComparator());

					if (order_change_tv.getText().equals("倒序")) {
						Collections.reverse(datalist);
					}

					if (Data.getTimes() >= datalist.size()) {
						Data.setTimes(datalist.size());
						flag = false;
					}
					for (int i = 0; i < Data.getTimes(); i++) {
						Data.getDatalist().add(datalist.get(i));
					}

					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Information_list.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void get_focus_people() {
		// TODO Auto-generated method stub get_focus_people

		BmobQuery<Focus_people> query = new BmobQuery<Focus_people>();
		query.addWhereEqualTo("host_id", Data.getUser_id());
		query.findObjects(Information_list.this, new FindListener<Focus_people>() {

			@Override
			public void onSuccess(List<Focus_people> object) {
				if (object.size() == 0) {
					flag = false;
					Toast.makeText(Information_list.this, "没有信息", Toast.LENGTH_SHORT).show();
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				} else {
					for (Focus_people focus_people : object) {
						map = new HashMap<String, String>();
						map.put("tv", focus_people.getFocus_people_name());
						map.put("see_message", " ta \n的\n帖\n子");

						datalist.add(map);
					}

					Collections.sort(datalist, new MapComparator());

					if (order_change_tv.getText().equals("正序")) {
						Collections.reverse(datalist);
					}

					if (Data.getTimes() >= datalist.size()) {
						Data.setTimes(datalist.size());
						flag = false;
					}
					for (int i = 0; i < Data.getTimes(); i++) {
						Data.getDatalist().add(datalist.get(i));
					}

					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Information_list.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void get_notify() {
		// TODO Auto-generated method stub get_notify

		BmobQuery<Notify> query = new BmobQuery<Notify>();
		query.addWhereNotEqualTo("message", "");
		query.findObjects(Information_list.this, new FindListener<Notify>() {

			@Override
			public void onSuccess(List<Notify> object) {
				if (object.size() == 0) {
					flag = false;
					Toast.makeText(Information_list.this, "没有信息", Toast.LENGTH_SHORT).show();
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				} else {
					for (Notify Notify : object) {
						map = new HashMap<String, String>();
						map.put("tv", Notify.getMessage());
						map.put("id_tv", Notify.getCreatedAt().toString());

						datalist.add(map);
					}

					Collections.sort(datalist, new MapComparator_createdAt());

					if (order_change_tv.getText().equals("正序")) {
						Collections.reverse(datalist);
					}

					if (Data.getTimes() >= datalist.size()) {
						Data.setTimes(datalist.size());
						flag = false;
					}
					for (int i = 0; i < Data.getTimes(); i++) {
						Data.getDatalist().add(datalist.get(i));
					}

					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Information_list.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void get_curriculum_vitae() {
		// TODO Auto-generated method stub get_notify

		BmobQuery<Curriculum_vitae> query = new BmobQuery<Curriculum_vitae>();
		query.addWhereEqualTo("user_id", Data.getUser_id());
		query.findObjects(Information_list.this, new FindListener<Curriculum_vitae>() {

			@Override
			public void onSuccess(List<Curriculum_vitae> object) {
				if (object.size() == 0) {
					flag = false;
					Toast.makeText(Information_list.this, "没有简历", Toast.LENGTH_SHORT).show();
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				} else {
					for (Curriculum_vitae curriculum_vitae : object) {
						map = new HashMap<String, String>();
						map.put("tv", curriculum_vitae.getCv_name());
						map.put("id_tv", curriculum_vitae.getCreatedAt().toString());
						map.put("order", curriculum_vitae.getObjectId().toString());
						map.put("see_message", "\n 删\n除\n");

						datalist.add(map);
					}

					Collections.sort(datalist, new MapComparator_createdAt());

					if (order_change_tv.getText().equals("正序")) {
						Collections.reverse(datalist);
					}

					if (Data.getTimes() >= datalist.size()) {
						Data.setTimes(datalist.size());
						flag = false;
					}
					for (int i = 0; i < Data.getTimes(); i++) {
						Data.getDatalist().add(datalist.get(i));
					}

					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Information_list.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void getjob() {
		// TODO Auto-generated method stub getjob

		BmobQuery<Job> query = new BmobQuery<Job>();
		query.addWhereNotEqualTo("job_name", "");
		query.findObjects(Information_list.this, new FindListener<Job>() {

			@Override
			public void onSuccess(List<Job> object) {
				if (object.size() == 0) {
					flag = false;
					Toast.makeText(Information_list.this, "没有信息", Toast.LENGTH_SHORT).show();
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				} else {

					for (Job job : object) {
						if (job.getTag().contains(Data.getKey())) {
							map = new HashMap<String, String>();
							map.put("tv", job.getJob_name());
							map.put("type", job.getObjectId());
							map.put("id_tv", job.getSalary() + "元/天");
							map.put("updateAt", job.getUpdatedAt());
							datalist.add(map);
						}
					}

					Collections.sort(datalist, new MapComparator());
					if (order_change_tv.getText().equals("倒序")) {
						Collections.reverse(datalist);
					}

					if (Data.getTimes() >= datalist.size()) {
						Data.setTimes(datalist.size());
						flag = false;
					}

					for (int i = 0; i < Data.getTimes(); i++) {
						Data.getDatalist().add(datalist.get(i));
					}
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Information_list.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void get_mycompany() {

		BmobQuery<Company> query = new BmobQuery<Company>();
		query.addWhereEqualTo("user_id", Data.getUser_id());
		query.findObjects(Information_list.this, new FindListener<Company>() {

			@Override
			public void onSuccess(List<Company> object) {
				if (object.size() == 0) {
					Toast.makeText(Information_list.this, "没有公司", Toast.LENGTH_SHORT).show();
				} else {

					for (Company company : object) {
						get_myjob(company.getObjectId());
					}
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Information_list.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void get_myjob(String s) {
		// TODO Auto-generated method stub getjob

		BmobQuery<Job> query = new BmobQuery<Job>();
		query.addWhereEqualTo("company_id", s);
		query.findObjects(Information_list.this, new FindListener<Job>() {

			@Override
			public void onSuccess(List<Job> object) {
				if (object.size() == 0) {
					flag = false;
					Toast.makeText(Information_list.this, "没有职位", Toast.LENGTH_SHORT).show();
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				} else {

					for (Job job : object) {
						map = new HashMap<String, String>();
						map.put("tv", job.getJob_name());
						map.put("order", job.getObjectId());
						map.put("id_tv", job.getSalary() + "元/天");
						map.put("see_message", job_string);
						map.put("updateAt", job.getUpdatedAt());
						datalist.add(map);
					}

					Collections.sort(datalist, new MapComparator());
					if (order_change_tv.getText().equals("倒序")) {
						Collections.reverse(datalist);
					}

					if (Data.getTimes() >= datalist.size()) {
						Data.setTimes(datalist.size());
						flag = false;
					}

					for (int i = 0; i < Data.getTimes(); i++) {
						Data.getDatalist().add(datalist.get(i));
					}
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Information_list.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void delete_one_job(String s) {

		Job job = new Job();
		job.delete(this, s, new DeleteListener() {

			@Override
			public void onSuccess() {
				Log.i("bmob", "删除职位成功：");
			}

			@Override
			public void onFailure(int code, String msg) {
				Log.i("bmob", "删除职位失败：" + msg);
			}
		});

	}

	private void delete_one_cv_control(String s) {

		Cv_control cv_control = new Cv_control();
		cv_control.delete(this, s, new DeleteListener() {

			@Override
			public void onSuccess() {
				Log.i("bmob", "删除简历成功：");
				update();
			}

			@Override
			public void onFailure(int code, String msg) {
				Log.i("bmob", "删除简历失败：" + msg);
			}
		});

	}
	
	private void delete_one_cv(String s) {

		Curriculum_vitae curriculum_vitae = new Curriculum_vitae();
		curriculum_vitae.delete(this, s, new DeleteListener() {

			@Override
			public void onSuccess() {
				Log.i("bmob", "删除简历成功：");
				update();
			}

			@Override
			public void onFailure(int code, String msg) {
				Log.i("bmob", "删除简历失败：" + msg);
			}
		});

	}

	private void update_one_cv_control(String s, String n, String m) {

		Cv_control cv_control = new Cv_control();
		cv_control.setCv_id(n);
		cv_control.setJob_id(m);
		cv_control.setCollection("true");
		cv_control.update(Information_list.this, s, new UpdateListener() {
			@Override
			public void onSuccess() {
				Toast.makeText(Information_list.this, "收藏简历成功", Toast.LENGTH_SHORT).show();
				update();
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(Information_list.this, code + "收藏简历错误" + msg, Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void new_Cv_control(String s) {
		Cv_control cv_control = new Cv_control();
		cv_control.setCv_id(s);
		cv_control.setJob_id(Data.getTag().toString());
		cv_control.setCollection("false");
		cv_control.save(Information_list.this, new SaveListener() {
			@Override
			public void onSuccess() {
				Toast.makeText(Information_list.this, "申请成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setAction("action.rename");
				sendBroadcast(intent);
				onBackPressed();
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(Information_list.this, "申请失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void get_mycv() {
		// TODO Auto-generated method stub get_mycv

		BmobQuery<Cv_control> query = new BmobQuery<Cv_control>();
		BmobQuery<Cv_control> query1 = new BmobQuery<Cv_control>();
		BmobQuery<Cv_control> query2 = new BmobQuery<Cv_control>();
		List<BmobQuery<Cv_control>> andQuerys = new ArrayList<BmobQuery<Cv_control>>();
		query1.addWhereEqualTo("job_id", job_id);
		query2.addWhereEqualTo("collection", flag3);
		andQuerys.add(query1);
		andQuerys.add(query2);
		query.and(andQuerys);
		query.findObjects(Information_list.this, new FindListener<Cv_control>() {

			@Override
			public void onSuccess(List<Cv_control> object) {
				if (object.size() == 0) {
					flag = false;
					Toast.makeText(Information_list.this, "没有信息", Toast.LENGTH_SHORT).show();
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				} else {

					for (Cv_control cv_control : object) {
						get_cv(cv_control.getCv_id());
					}
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Information_list.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void get_cv(String s) {
		// TODO Auto-generated method stub get_cv

		BmobQuery<Curriculum_vitae> query = new BmobQuery<Curriculum_vitae>();
		query.addWhereEqualTo("objectId", s);
		query.findObjects(Information_list.this, new FindListener<Curriculum_vitae>() {

			@Override
			public void onSuccess(List<Curriculum_vitae> object) {
				if (object.size() == 0) {
					flag = false;
					Toast.makeText(Information_list.this, "没有简历", Toast.LENGTH_SHORT).show();
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				} else {
					for (Curriculum_vitae curriculum_vitae : object) {
						map = new HashMap<String, String>();
						map.put("tv", curriculum_vitae.getCv_name());
						map.put("id_tv", curriculum_vitae.getCreatedAt().toString());
						map.put("order", curriculum_vitae.getObjectId().toString());
						map.put("see_message", "\n 删 \n除\n");
						map.put("see_message_2", cv_string);

						datalist.add(map);
					}

					Collections.sort(datalist, new MapComparator_createdAt());

					if (order_change_tv.getText().equals("正序")) {
						Collections.reverse(datalist);
					}

					if (Data.getTimes() >= datalist.size()) {
						Data.setTimes(datalist.size());
						flag = false;
					}
					for (int i = 0; i < Data.getTimes(); i++) {
						Data.getDatalist().add(datalist.get(i));
					}

					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Information_list.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void get_cv_control(String s) {
		// TODO Auto-generated method stub get_mycv

		BmobQuery<Cv_control> query = new BmobQuery<Cv_control>();
		BmobQuery<Cv_control> query1 = new BmobQuery<Cv_control>();
		BmobQuery<Cv_control> query2 = new BmobQuery<Cv_control>();
		List<BmobQuery<Cv_control>> andQuerys = new ArrayList<BmobQuery<Cv_control>>();
		query1.addWhereEqualTo("job_id", job_id);
		query2.addWhereEqualTo("cv_id", s);
		andQuerys.add(query1);
		andQuerys.add(query2);
		query.and(andQuerys);
		query.findObjects(Information_list.this, new FindListener<Cv_control>() {

			@Override
			public void onSuccess(List<Cv_control> object) {
				if (object.size() == 0) {
					flag = false;
					Toast.makeText(Information_list.this, "没有信息", Toast.LENGTH_SHORT).show();
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				} else {

					for (Cv_control cv_control : object) {
						if (delete_update.equals("update")) {
							update_one_cv_control(cv_control.getObjectId(), cv_control.getCv_id(),
									cv_control.getJob_id());
						} else {
							delete_one_cv_control(cv_control.getObjectId());
						}
					}
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Information_list.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void update() {
		Data.getDatalist().clear();
		datalist.clear();
		Data.setTimes(10);
		switch (Data.getWhich()) {

		case 1:
			Data.setType("公司信息");
			getjob();
			break;

		case 2:
			Data.setType("人才市场");
			getinformation();
			break;

		case 3:
			Data.setType("考试培训");
			getinformation();
			break;

		case 4:
			Data.setType("行业前景");
			getinformation();
			break;

		case 5:
			Data.setType("求职攻略");
			getinformation();
			break;

		case 6:
			Data.setType("就业");
			getpost_title();
			break;

		case 7:
			Data.setType("就业指南");
			getinformation();
			break;

		case 8:
			Data.setType("校园招聘");
			getinformation();
			break;

		case 9:
			listView_bg.setAlpha(1f);
			break;

		case 10:
			Data.setType("招生");
			getpost_title();
			break;

		case 11:
			Data.setType("招生信息");
			getinformation();
			break;

		case 12:
			Data.setType("高校信息");
			getinformation();
			break;

		case 13:
			Data.setType("公司信息");
			Data.setKey("实习");
			getjob();
			break;

		case 15:
			host_user_name = Data.getUser_name();
			get_mine();
			break;

		case 16:
			get_history();
			break;

		case 18:
			get_focus_people();
			break;

		case 19:
			get_notify();
			break;

		case 20:
			get_mycompany();
			break;

		case 21:
			get_collection();
			break;

		case 22:
			get_curriculum_vitae();
			break;

		case -1:
			get_mine();
			break;

		case -2:
			getinformation();
			break;

		case -5:
			get_curriculum_vitae();
			break;

		case -12:
			get_mycv();
			break;

		default:
			break;
		}
	}

}
