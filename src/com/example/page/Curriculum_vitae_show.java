package com.example.page;

import java.util.List;

import com.example.jason4s.R;
import com.example.javabean.Curriculum_vitae;
import com.example.tool.Data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class Curriculum_vitae_show extends Activity {
	private TextView name;
	private TextView birthday;
	private TextView length_of_employment;
	private TextView sex;
	private TextView marital_status;
	private TextView habitation;
	private TextView hukou;
	private TextView mobile_phone;
	private TextView mail;
	private TextView job_status;
	private TextView report_duty;
	private TextView job_type;
	private TextView want_industry;
	private TextView location;
	private TextView want_salary;
	private TextView Aim_Function;
	private TextView school;
	private TextView level_of_education;
	private TextView skills;
	private Button left;
	private Button right;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_curriculum_vitae_show);

		name = (TextView) findViewById(R.id.name);
		birthday = (TextView) findViewById(R.id.birthday);
		length_of_employment = (TextView) findViewById(R.id.length_of_employment);
		sex = (TextView) findViewById(R.id.sex);
		marital_status = (TextView) findViewById(R.id.marital_status);
		habitation = (TextView) findViewById(R.id.habitation);
		hukou = (TextView) findViewById(R.id.hukou);
		mobile_phone = (TextView) findViewById(R.id.mobile_phone);
		mail = (TextView) findViewById(R.id.mail);
		job_status = (TextView) findViewById(R.id.job_status);
		report_duty = (TextView) findViewById(R.id.report_duty);
		job_type = (TextView) findViewById(R.id.job_type);
		want_industry = (TextView) findViewById(R.id.want_industry);
		location = (TextView) findViewById(R.id.location);
		want_salary = (TextView) findViewById(R.id.want_salary);
		Aim_Function = (TextView) findViewById(R.id.Aim_Function);
		school = (TextView) findViewById(R.id.school);
		level_of_education = (TextView) findViewById(R.id.level_of_education);
		skills = (TextView) findViewById(R.id.skills);
		left = (Button) findViewById(R.id.left);
		right = (Button) findViewById(R.id.right);
		
		get_cv();

		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onBackPressed();
			}
		});
		
		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(Curriculum_vitae_show.this, Curriculum_vitae_update.class));
			}
		});

	}

	private void get_cv() {
		// TODO Auto-generated method stub get_cv

		BmobQuery<Curriculum_vitae> query = new BmobQuery<Curriculum_vitae>();
		query.addWhereEqualTo("objectId", Data.getId());
		query.findObjects(Curriculum_vitae_show.this, new FindListener<Curriculum_vitae>() {

			@Override
			public void onSuccess(List<Curriculum_vitae> object) {
				if (object.size() == 0) {
					Toast.makeText(Curriculum_vitae_show.this, "没有信息", Toast.LENGTH_SHORT).show();
				} else {
					for (Curriculum_vitae curriculum_vitae : object) {
						name.setText(curriculum_vitae.getName());
						birthday.setText("("+curriculum_vitae.getBirthday()+")");
						length_of_employment.setText(curriculum_vitae.getLength_of_employment());
						sex.setText("|"+curriculum_vitae.getSex());
						marital_status.setText("|"+curriculum_vitae.getMarital_status());
						habitation.setText("居住地："+curriculum_vitae.getHabitation());
						hukou.setText("户口："+curriculum_vitae.getHukou());
						mobile_phone.setText("手机："+curriculum_vitae.getMobile_phone());
						mail.setText("邮箱："+curriculum_vitae.getMail());
						job_status.setText("求职状态："+curriculum_vitae.getJob_status());
						report_duty.setText("到岗时间："+curriculum_vitae.getReport_duty());
						job_type.setText("工作类型："+curriculum_vitae.getJob_type());
						want_industry.setText("希望行业："+curriculum_vitae.getWant_industry());
						location.setText("目标地点："+curriculum_vitae.getLocation());
						want_salary.setText("期望月薪："+curriculum_vitae.getWant_salary());
						Aim_Function.setText("目标职能："+curriculum_vitae.getAim_Function());
						school.setText("学校："+curriculum_vitae.getSchool());
						level_of_education.setText("教育水平："+curriculum_vitae.getLevel_of_education());
						skills.setText(curriculum_vitae.getSkills());
					}
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Curriculum_vitae_show.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});
	
		
	}

	public void onBackPressed() {
		// TODO Auto-generated method stub onBackPressed
		super.onBackPressed();
		finish();
	};

}
