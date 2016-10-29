package com.example.page;

import java.util.ArrayList;
import java.util.List;

import com.example.jason4s.Login;
import com.example.jason4s.R;
import com.example.javabean.Collection;
import com.example.javabean.Company;
import com.example.javabean.Curriculum_vitae;
import com.example.javabean.Cv_control;
import com.example.javabean.Job;
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

public class Job_show extends Activity {
	private TextView job_name;
	private TextView salary;
	private TextView company_name;
	private TextView nature;
	private TextView trimmed;
	private TextView place;
	private TextView count;
	private TextView fuli;
	private TextView tag;
	private TextView createdAt;
	private TextView address;
	private TextView introduction;
	private Button left;
	private Button right;
	private Button ask;

	private String company_id;
	private ImageView talk;
	private ImageView collection;
	private String company_name__;
	private String check_or_update = "check";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_job_show);

		job_name = (TextView) findViewById(R.id.job_name);
		salary = (TextView) findViewById(R.id.salary);
		company_name = (TextView) findViewById(R.id.company_name);
		nature = (TextView) findViewById(R.id.nature);
		trimmed = (TextView) findViewById(R.id.trimmed);
		place = (TextView) findViewById(R.id.place);
		count = (TextView) findViewById(R.id.count);
		fuli = (TextView) findViewById(R.id.fuli);
		tag = (TextView) findViewById(R.id.tag);
		createdAt = (TextView) findViewById(R.id.createdAt);
		address = (TextView) findViewById(R.id.address);
		introduction = (TextView) findViewById(R.id.introduction);
		left = (Button) findViewById(R.id.left);
		right = (Button) findViewById(R.id.right);
		ask = (Button) findViewById(R.id.ask);
		talk = (ImageView) findViewById(R.id.talk);
		collection = (ImageView) findViewById(R.id.collection);

		ask.setText("申请");

		get();
		update_collection();
		get_curriculum_vitae();

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("action.rename");
		registerReceiver(mRefreshBroadcastReceiver, intentFilter);

		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onBackPressed();
			}
		});

		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Animation alpha = new AlphaAnimation(0.5f, 1.0f);
				alpha.setDuration(200);
				right.startAnimation(alpha);

				Intent intent = new Intent();
				intent.putExtra("company_id", company_id);
				intent.setClass(Job_show.this, Company_show.class);
				startActivity(intent);
			}
		});

		talk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Animation alpha = new AlphaAnimation(0.5f, 1.0f);
				alpha.setDuration(200);
				talk.startAnimation(alpha);

				Intent intent = new Intent();
				intent.putExtra("title", company_name__);
				intent.setClass(Job_show.this, Post.class);
				startActivity(intent);
			}
		});

		ask.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (ask.getText().toString().equals("申请")) {
					Animation alpha = new AlphaAnimation(0.5f, 1.0f);
					alpha.setDuration(200);
					talk.startAnimation(alpha);

					if (Data.getUser_name() == "") {
						Toast.makeText(Job_show.this, "请先登录", Toast.LENGTH_SHORT).show();
						startActivity(new Intent(Job_show.this, Login.class));
					} else {
						Data.setWhich2(Data.getWhich());
						Data.setWhich(-5);
						startActivity(new Intent(Job_show.this, Information_list.class));
					}
				}
			}
		});

		collection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (Utils.isFastDoubleClick()) {
					return;
				}
				update_collection();
			}
		});

	}

	private void get() {
		// TODO Auto-generated method stub get

		BmobQuery<Job> query = new BmobQuery<Job>();
		query.addWhereEqualTo("objectId", Data.getTag().toString());
		query.findObjects(Job_show.this, new FindListener<Job>() {

			@Override
			public void onSuccess(List<Job> object) {
				if (object.size() == 0) {
					Toast.makeText(Job_show.this, "没有信息", Toast.LENGTH_SHORT).show();
				} else {
					for (Job job : object) {
						job_name.setText(job.getJob_name());
						salary.setText(job.getSalary());
						place.setText(job.getPlace());
						count.setText(job.getCount());
						fuli.setText(job.getFuli());
						tag.setText(job.getTag());
						createdAt.setText(job.getCreatedAt());
						introduction.setText(job.getIntroduction());
						get_company(job.getCompany_id());
						company_id = job.getCompany_id();
					}

				}
			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Job_show.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});

	}

	public void onBackPressed() {
		// TODO Auto-generated method stub onBackPressed
		super.onBackPressed();
		finish();
	};

	private void get_company(String s) {
		// TODO Auto-generated method stub get_company

		BmobQuery<Company> query1 = new BmobQuery<Company>();
		query1.addWhereEqualTo("objectId", s);
		query1.findObjects(Job_show.this, new FindListener<Company>() {

			@Override
			public void onSuccess(List<Company> object) {
				if (object.size() == 0) {
					Toast.makeText(Job_show.this, "没有信息", Toast.LENGTH_SHORT).show();
				} else {
					for (Company company : object) {
						company_name.setText(company.getCompany_name());
						nature.setText(company.getNature());
						trimmed.setText(company.getTrimmed());
						address.setText(company.getAddress());
						company_name__ = company.getCompany_name();
					}

				}
			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Job_show.this, code + "!!!!!!" + msg, Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void update_collection() {
		// TODO Auto-generated method stub update_history

		BmobQuery<Collection> query = new BmobQuery<Collection>();
		BmobQuery<Collection> query1 = new BmobQuery<Collection>();
		BmobQuery<Collection> query2 = new BmobQuery<Collection>();
		List<BmobQuery<Collection>> andQuerys = new ArrayList<BmobQuery<Collection>>();
		query1.addWhereEqualTo("user_id", Data.getUser_id());
		query2.addWhereEqualTo("job_id", Data.getTag());
		andQuerys.add(query1);
		andQuerys.add(query2);
		query.and(andQuerys);
		query.findObjects(Job_show.this, new FindListener<Collection>() {

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
				Toast.makeText(Job_show.this, "读取错误1", Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void new_collection() {
		Collection collection = new Collection();
		collection.setUser_id(Data.getUser_id());
		collection.setPost_type("");
		collection.setInformation_type("");
		collection.setInformation_order(0);
		collection.setHost_user_name("职位");
		collection.setTitle(job_name.getText().toString());
		collection.setJob_id(Data.getTag());
		;
		collection.save(Job_show.this, new SaveListener() {
			@Override
			public void onSuccess() {
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(Job_show.this, "添加收藏失败", Toast.LENGTH_SHORT).show();
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

	private void update_cv_control(String s) {
		// TODO Auto-generated method stub update_history

		BmobQuery<Cv_control> query = new BmobQuery<Cv_control>();
		BmobQuery<Cv_control> query1 = new BmobQuery<Cv_control>();
		BmobQuery<Cv_control> query2 = new BmobQuery<Cv_control>();
		List<BmobQuery<Cv_control>> andQuerys = new ArrayList<BmobQuery<Cv_control>>();
		query1.addWhereEqualTo("cv_id", s);
		query2.addWhereEqualTo("job_id", Data.getTag());
		andQuerys.add(query1);
		andQuerys.add(query2);
		query.and(andQuerys);
		query.findObjects(Job_show.this, new FindListener<Cv_control>() {

			@Override
			public void onSuccess(List<Cv_control> object) {
				if (object.size() == 0) {
				} else {
					ask.setText("已申请");
				}
			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Job_show.this, "读取错误1", Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void get_curriculum_vitae() {
		// TODO Auto-generated method stub get_notify

		BmobQuery<Curriculum_vitae> query = new BmobQuery<Curriculum_vitae>();
		query.addWhereEqualTo("user_id", Data.getUser_id());
		query.findObjects(Job_show.this, new FindListener<Curriculum_vitae>() {

			@Override
			public void onSuccess(List<Curriculum_vitae> object) {
				if (object.size() == 0) {
					Toast.makeText(Job_show.this, "没有简历", Toast.LENGTH_SHORT).show();
				} else {
					for (Curriculum_vitae curriculum_vitae : object) {
						update_cv_control(curriculum_vitae.getObjectId());
					}

				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Job_show.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("action.rename")) {
				ask.setText("已申请");
			}
		}
	};

}
