package com.example.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.jason4s.R;
import com.example.javabean.Job;
import com.example.page.Information_list.MapComparator;
import com.example.tool.Data;
import com.example.tool.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class Search extends Activity implements OnItemClickListener, OnScrollListener {
	private TextView left;
	private TextView right;
	private EditText search;
	private SimpleAdapter sim;
	private Map<String, String> map;
	private ListView List;
	private boolean flag = true; // 可否继续加载
	private boolean flag2 = true; // 可否刷新
	private static List<Map<String, String>> datalist3 = new ArrayList<Map<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_search);

		left = (TextView) findViewById(R.id.left);
		right = (TextView) findViewById(R.id.right);
		search = (EditText) findViewById(R.id.search);
		List = (ListView) findViewById(R.id.listView);

		Data.getDatalist3().clear();
		datalist3.clear();
		Data.setTimes(10);

		sim = new SimpleAdapter(Search.this, Data.getDatalist3(), R.layout.item,
				new String[] { "tv", "id_tv", "order", "type", "see_message" },
				new int[] { R.id.tv, R.id.id_tv, R.id.order, R.id.type, R.id.see_message });

		Data.setType("公司信息");

		List.setAdapter(sim);
		List.setOnScrollListener(this);
		List.setOnItemClickListener(this);

		search.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				if (arg1 == KeyEvent.KEYCODE_ENTER) {
					if (Utils.isFastDoubleClick()) {
						return false;
					}
					Animation alpha = new AlphaAnimation(0.5f, 1.0f);
					alpha.setDuration(200);
					right.startAnimation(alpha);
					if (search.getText().toString().equals("")) {
						Toast.makeText(Search.this, "请输入内容", Toast.LENGTH_SHORT).show();
					} else if (flag2 == false) {
						Toast.makeText(Search.this, "请等刷新完成", Toast.LENGTH_SHORT).show();
					} else {
						Data.getDatalist3().clear();
						datalist3.clear();
						Data.setTimes(10);
						flag2 = false;
						Data.setKey(search.getText().toString());
						getjob();
					}
					return true;
				}
				return false;
			}
		});

		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Animation alpha = new AlphaAnimation(0.5f, 1.0f);
				alpha.setDuration(200);
				left.startAnimation(alpha);
				onBackPressed();
			}
		});

		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (Utils.isFastDoubleClick()) {
					return;
				}
				Animation alpha = new AlphaAnimation(0.5f, 1.0f);
				alpha.setDuration(200);
				right.startAnimation(alpha);
				if (search.getText().toString().equals("")) {
					Toast.makeText(Search.this, "请输入内容", Toast.LENGTH_SHORT).show();
				} else if (flag2 == false) {
					Toast.makeText(Search.this, "请等刷新完成", Toast.LENGTH_SHORT).show();
				} else {
					Data.getDatalist3().clear();
					datalist3.clear();
					Data.setTimes(10);
					flag2 = false;
					Data.setKey(search.getText().toString());
					getjob();
				}
			}
		});

	}

	private void getjob() {
		// TODO Auto-generated method stub getjob

		BmobQuery<Job> query = new BmobQuery<Job>();
		query.addWhereNotEqualTo("job_name", "");
		query.findObjects(Search.this, new FindListener<Job>() {

			@Override
			public void onSuccess(List<Job> object) {
				if (object.size() == 0) {
					flag = false;
					Toast.makeText(Search.this, "没有信息", Toast.LENGTH_SHORT).show();
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				} else {

					for (Job job : object) {
						if (job.getTag().contains(Data.getKey()) || job.getJob_name().contains(Data.getKey())
								|| job.getPlace().contains(Data.getKey())) {
							map = new HashMap<String, String>();
							map.put("tv", job.getJob_name());
							map.put("type", job.getObjectId());
							map.put("id_tv", job.getSalary() + "元/天");
							map.put("updateAt", job.getUpdatedAt());
							datalist3.add(map);
						}
					}

					Collections.sort(datalist3, new MapComparator());
					Collections.reverse(datalist3);

					if (Data.getTimes() >= datalist3.size()) {
						Data.setTimes(datalist3.size());
						flag = false;
					}

					for (int i = 0; i < Data.getTimes(); i++) {
						Data.getDatalist3().add(datalist3.get(i));
					}
					sim.notifyDataSetChanged();
					flag2 = true;
					List.setEnabled(true);
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Search.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
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
				Toast.makeText(Search.this, "正在加载", Toast.LENGTH_SHORT).show();

				if (Data.getTimes() + 5 >= datalist3.size()) {

					for (int i = Data.getTimes(); i < datalist3.size(); i++) {
						Data.getDatalist3().add(datalist3.get(i));
					}
					sim.notifyDataSetChanged();
					flag = false;
					Toast.makeText(Search.this, "已加载全部信息", Toast.LENGTH_SHORT).show();

				} else {

					for (int i = Data.getTimes(); i < Data.getTimes() + 5; i++) {
						Data.getDatalist3().add(datalist3.get(i));
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

		Data.setTag(type.getText().toString());
		startActivity(new Intent(Search.this, Job_show.class));

	}

	public void onBackPressed() {
		// TODO Auto-generated method stub onBackPressed
		super.onBackPressed();
		finish();
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

}
