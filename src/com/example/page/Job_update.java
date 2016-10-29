package com.example.page;

import java.util.List;

import com.example.jason4s.R;
import com.example.javabean.Company;
import com.example.javabean.Job;
import com.example.tool.Data;
import com.example.tool.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class Job_update extends Activity {
	private EditText job_name;
	private EditText salary;
	private EditText place;
	private EditText count;
	private EditText fuli;
	private EditText tag;
	private EditText introduction;

	private Button left;
	private Button right;

	private String job_name_;
	private String salary_;
	private String place_;
	private String count_;
	private String fuli_;
	private String tag_;
	private String introduction_;
	private String id;
	private String job_id;
	private Boolean flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_job_update);

		job_name = (EditText) findViewById(R.id.job_name_et);
		salary = (EditText) findViewById(R.id.salary_et);
		place = (EditText) findViewById(R.id.place_et);
		count = (EditText) findViewById(R.id.count_et);
		fuli = (EditText) findViewById(R.id.fuli_et);
		tag = (EditText) findViewById(R.id.tag_et);
		introduction = (EditText) findViewById(R.id.introduction_et);

		left = (Button) findViewById(R.id.left);
		right = (Button) findViewById(R.id.right);
		
		Intent intent = getIntent();
		job_id = intent.getStringExtra("job_id");

		getcompany();

		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onBackPressed();
			}
		});

		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (Utils.isFastDoubleClick()) {
					return;
				}
				if (flag == true) {
					replace_string();
				}
			}
		});

	}

	private void getcompany() {
		// TODO Auto-generated method stub get_cv

		BmobQuery<Company> query = new BmobQuery<Company>();
		query.addWhereEqualTo("user_id", Data.getUser_id().toString());
		query.findObjects(Job_update.this, new FindListener<Company>() {

			@Override
			public void onSuccess(List<Company> object) {
				if (object.size() == 0) {
					Toast.makeText(Job_update.this, "没有权限", Toast.LENGTH_SHORT).show();
				} else {
					flag = true;
					for (Company company : object) {
						id = company.getObjectId().toString();
					}
					if (Data.getWhich() == -3) {
						set_hint(id);
					}

				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Job_update.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void set_hint(String s) {
		// TODO Auto-generated method stub get_cv

		BmobQuery<Job> query = new BmobQuery<Job>();
		query.addWhereEqualTo("company_id", s);
		query.findObjects(Job_update.this, new FindListener<Job>() {

			@Override
			public void onSuccess(List<Job> object) {
				if (object.size() == 0) {
				} else {
					for (Job job : object) {
						job_name.setHint(job.getJob_name());
						salary.setHint(job.getSalary());
						place.setHint(job.getPlace());
						count.setHint(job.getCount());
						fuli.setHint(job.getFuli());
						tag.setHint(job.getTag());
						introduction.setHint(job.getIntroduction());
					}
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Job_update.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});

	}

	public void onBackPressed() {
		// TODO Auto-generated method stub onBackPressed
		Data.setWhich(20);
		super.onBackPressed();
		finish();
	}

	private void replace_string() {
		// TODO Auto-generated method stub check
		job_name_ = job_name.getText().toString().replace(" ", "");
		salary_ = salary.getText().toString().replace(" ", "");
		place_ = place.getText().toString().replace(" ", "");
		count_ = count.getText().toString().replace(" ", "");
		fuli_ = fuli.getText().toString().replace(" ", "");
		tag_ = tag.getText().toString().replace(" ", "");
		introduction_ = introduction.getText().toString().replace(" ", "");
		if (Data.getWhich() == -3) {
			change();
		} else {
			new_one();
		}
	}

	private void change() {
		if (job_name_.equals("")) {
			job_name_ = job_name.getHint().toString();
		}
		if (salary_.equals("")) {
			salary_ = salary.getHint().toString();
		}
		if (place_.equals("")) {
			place_ = place.getHint().toString();
		}
		if (count_.equals("")) {
			count_ = count.getHint().toString();
		}
		if (fuli_.equals("")) {
			fuli_ = fuli.getHint().toString();
		}
		if (tag_.equals("")) {
			tag_ = tag.getHint().toString();
		}
		if (introduction_.equals("")) {
			introduction_ = introduction.getHint().toString();
		}

		update();

	}

	public void update() {

		Job job = new Job();
		job.setJob_name(job_name_);
		job.setSalary(salary_);
		job.setPlace(place_);
		job.setCount(count_);
		job.setFuli(fuli_);
		job.setTag(tag_);
		job.setIntroduction(introduction_);
		job.update(Job_update.this, job_id, new UpdateListener() {
			@Override
			public void onSuccess() {
				Toast.makeText(Job_update.this, "修改职位信息成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setAction("action.recreate");
				sendBroadcast(intent);
				onBackPressed();
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(Job_update.this, code + "修改职位信息错误" + msg, Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void new_one() {
		if (job_name_.equals("")) {
			Toast.makeText(Job_update.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
		} else if (salary_.equals("")) {
			Toast.makeText(Job_update.this, "户口不能为空", Toast.LENGTH_SHORT).show();
		} else if (place_.equals("")) {
			Toast.makeText(Job_update.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
		} else if (count_.equals("")) {
			Toast.makeText(Job_update.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
		} else if (fuli_.equals("")) {
			Toast.makeText(Job_update.this, "求职状态不能为空", Toast.LENGTH_SHORT).show();
		} else if (tag_.equals("")) {
			Toast.makeText(Job_update.this, "到岗时间不能为空", Toast.LENGTH_SHORT).show();
		} else if (introduction_.equals("")) {
			Toast.makeText(Job_update.this, "工作类型不能为空", Toast.LENGTH_SHORT).show();
		} else {
			save();
		}
	}

	public void save() {

		Job job = new Job();
		job.setJob_name(job_name_);
		job.setSalary(salary_);
		job.setPlace(place_);
		job.setCount(count_);
		job.setFuli(fuli_);
		job.setTag(tag_);
		job.setIntroduction(introduction_);
		job.setCompany_id(id);
		job.save(Job_update.this, new SaveListener() {
			@Override
			public void onSuccess() {
				Toast.makeText(Job_update.this, "新建职位成功", Toast.LENGTH_SHORT).show();
				onBackPressed();
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(Job_update.this, "新建职位错误", Toast.LENGTH_SHORT).show();
			}
		});

	}

}
