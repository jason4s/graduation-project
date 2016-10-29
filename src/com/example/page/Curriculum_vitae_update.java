package com.example.page;

import java.util.List;

import com.example.jason4s.R;
import com.example.javabean.Curriculum_vitae;
import com.example.tool.Data;
import com.example.tool.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class Curriculum_vitae_update extends Activity implements OnCheckedChangeListener {
	private EditText cv_name;
	private EditText name;
	private RadioGroup sex_rg;
	private EditText birthday_et_year;
	private EditText birthday_et_month;
	private EditText birthday_et_day;
	private EditText length_of_employment;
	private String sex = "";
	private EditText marital_status;
	private EditText habitation;
	private EditText hukou;
	private EditText mobile_phone;
	private EditText mail;
	private EditText job_status;
	private EditText report_duty;
	private EditText job_type;
	private EditText want_industry;
	private EditText location;
	private EditText want_salary;
	private EditText Aim_Function;
	private EditText school;
	private EditText level_of_education;
	private RadioButton radio0;
	private EditText skills;
	private Button left;
	private Button right;
	private String cv_name_;
	private String name_;
	private String length_of_employment_;
	private String marital_status_;
	private String habitation_;
	private String hukou_;
	private String mobile_phone_;
	private String mail_;
	private String job_status_;
	private String report_duty_;
	private String job_type_;
	private String want_industry_;
	private String location_;
	private String want_salary_;
	private String Aim_Function_;
	private String school_;
	private String level_of_education_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_curriculum_vitae_update);

		name = (EditText) findViewById(R.id.name_et);
		cv_name = (EditText) findViewById(R.id.cv_name_et);
		sex_rg = (RadioGroup) findViewById(R.id.sex_rg);
		birthday_et_year = (EditText) findViewById(R.id.birthday_et_year);
		birthday_et_month = (EditText) findViewById(R.id.birthday_et_month);
		birthday_et_day = (EditText) findViewById(R.id.birthday_et_day);
		length_of_employment = (EditText) findViewById(R.id.leng_th_of_employment_et);
		marital_status = (EditText) findViewById(R.id.marital_status_et);
		habitation = (EditText) findViewById(R.id.habitation_et);
		hukou = (EditText) findViewById(R.id.hukou_et);
		mobile_phone = (EditText) findViewById(R.id.mobile_phone_et);
		mail = (EditText) findViewById(R.id.mail_et);
		job_status = (EditText) findViewById(R.id.job_status_et);
		report_duty = (EditText) findViewById(R.id.report_duty_et);
		job_type = (EditText) findViewById(R.id.job_type_et);
		want_industry = (EditText) findViewById(R.id.want_industry_et);
		location = (EditText) findViewById(R.id.location_et);
		want_salary = (EditText) findViewById(R.id.want_salary_et);
		Aim_Function = (EditText) findViewById(R.id.Aim_Function_et);
		school = (EditText) findViewById(R.id.school_et);
		level_of_education = (EditText) findViewById(R.id.level_of_education_et);
		skills = (EditText) findViewById(R.id.skills_et);
		left = (Button) findViewById(R.id.left);
		right = (Button) findViewById(R.id.right);
		radio0 = (RadioButton) findViewById(R.id.radio0);

		sex_rg.setOnCheckedChangeListener(this);

		if (Data.getWhich() == 22) {
			set_hint();
		} else {
			sex = "男";
			radio0.setChecked(true);
		}

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
				replace_string();
			}
		});

	}

	private void set_hint() {
		// TODO Auto-generated method stub get_cv

		BmobQuery<Curriculum_vitae> query = new BmobQuery<Curriculum_vitae>();
		query.addWhereEqualTo("objectId", Data.getId());
		query.findObjects(Curriculum_vitae_update.this, new FindListener<Curriculum_vitae>() {

			@Override
			public void onSuccess(List<Curriculum_vitae> object) {
				if (object.size() == 0) {
				} else {
					for (Curriculum_vitae curriculum_vitae : object) {
						cv_name.setHint(curriculum_vitae.getCv_name());
						name.setHint(curriculum_vitae.getName());
						String year = curriculum_vitae.getBirthday().substring(0, 4);
						String month = curriculum_vitae.getBirthday().substring(6, 7);
						String day = curriculum_vitae.getBirthday().substring(9, 10);
						birthday_et_year.setHint(year);
						birthday_et_month.setHint(month);
						birthday_et_day.setHint(day);
						length_of_employment.setHint(curriculum_vitae.getLength_of_employment());
						marital_status.setHint(curriculum_vitae.getMarital_status());
						habitation.setHint(curriculum_vitae.getHabitation());
						hukou.setHint(curriculum_vitae.getHukou());
						mobile_phone.setHint(curriculum_vitae.getMobile_phone());
						mail.setHint(curriculum_vitae.getMail());
						job_status.setHint(curriculum_vitae.getJob_status());
						report_duty.setHint(curriculum_vitae.getReport_duty());
						job_type.setHint(curriculum_vitae.getJob_type());
						want_industry.setHint(curriculum_vitae.getWant_industry());
						location.setHint(curriculum_vitae.getLocation());
						want_salary.setHint(curriculum_vitae.getWant_salary());
						Aim_Function.setHint(curriculum_vitae.getAim_Function());
						school.setHint(curriculum_vitae.getSchool());
						level_of_education.setHint(curriculum_vitae.getLevel_of_education());
						skills.setHint(curriculum_vitae.getSkills());
						sex = curriculum_vitae.getSex();
					}
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Curriculum_vitae_update.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});

	}

	public void onBackPressed() {
		// TODO Auto-generated method stub onBackPressed
		Data.setWhich(22);
		super.onBackPressed();
		finish();
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
	};

	private void replace_string() {
		// TODO Auto-generated method stub check
		cv_name_ = cv_name.getText().toString().replace(" ", "");
		name_ = name.getText().toString().replace(" ", "");
		length_of_employment_ = length_of_employment.getText().toString().replace(" ", "");
		marital_status_ = marital_status.getText().toString().replace(" ", "");
		habitation_ = habitation.getText().toString().replace(" ", "");
		hukou_ = hukou.getText().toString().replace(" ", "");
		mobile_phone_ = mobile_phone.getText().toString().replace(" ", "");
		mail_ = mail.getText().toString().replace(" ", "");
		job_status_ = job_status.getText().toString().replace(" ", "");
		report_duty_ = report_duty.getText().toString().replace(" ", "");
		job_type_ = job_type.getText().toString().replace(" ", "");
		want_industry_ = want_industry.getText().toString().replace(" ", "");
		location_ = location.getText().toString().replace(" ", "");
		want_salary_ = want_salary.getText().toString().replace(" ", "");
		Aim_Function_ = Aim_Function.getText().toString().replace(" ", "");
		school_ = school.getText().toString().replace(" ", "");
		level_of_education_ = level_of_education.getText().toString().replace(" ", "");

		if (Data.getWhich() == 22) {
			change();
		} else {
			new_one();
		}
	}

	private void change() {
		if (cv_name_.equals("")) {
			cv_name_ = cv_name.getHint().toString();
		}
		if (name_.equals("")) {
			name_ = name.getHint().toString();
		}
		if (birthday_et_year.getText().toString().equals("")) {
			birthday_et_year.setText(birthday_et_year.getHint().toString());
		}
		if (birthday_et_month.getText().toString().equals("")) {
			birthday_et_month.setText(birthday_et_month.getHint().toString());
		}
		if (birthday_et_day.getText().toString().equals("")) {
			birthday_et_day.setText(birthday_et_day.getHint().toString());
		}
		if (length_of_employment_.equals("")) {
			length_of_employment_ = length_of_employment.getHint().toString();
		}
		if (marital_status_.equals("")) {
			marital_status_ = marital_status.getHint().toString();
		}
		if (habitation_.equals("")) {
			habitation_ = habitation.getHint().toString();
		}
		if (hukou_.equals("")) {
			hukou_ = hukou.getHint().toString();
		}
		if (mobile_phone_.equals("")) {
			mobile_phone_ = mobile_phone.getHint().toString();
		}
		if (mail_.equals("")) {
			mail_ = mail.getHint().toString();
		}
		if (job_status_.equals("")) {
			job_status_ = job_status.getHint().toString();
		}
		if (report_duty_.equals("")) {
			report_duty_ = report_duty.getHint().toString();
		}
		if (job_type_.equals("")) {
			job_type_ = job_type.getHint().toString();
		}
		if (want_industry_.equals("")) {
			want_industry_ = want_industry.getHint().toString();
		}
		if (location_.equals("")) {
			location_ = location.getHint().toString();
		}
		if (want_salary_.equals("")) {
			want_salary_ = want_salary.getHint().toString();
		}
		if (Aim_Function_.equals("")) {
			Aim_Function_ = Aim_Function.getHint().toString();
		}
		if (school_.equals("")) {
			school_ = school.getHint().toString();
		}
		if (level_of_education_.equals("")) {
			level_of_education_ = level_of_education.getHint().toString();
		}

		checkinput();

	}

	private void new_one() {
		if (name_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
		} else if (cv_name_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "简历名字不能为空", Toast.LENGTH_SHORT).show();
		} else if (birthday_et_year.getText().toString().equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "日期不能为空", Toast.LENGTH_SHORT).show();
		} else if (birthday_et_month.getText().toString().equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "日期不能为空", Toast.LENGTH_SHORT).show();
		} else if (birthday_et_day.getText().toString().equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "日期不能为空", Toast.LENGTH_SHORT).show();
		} else if (length_of_employment_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "工作年限不能为空", Toast.LENGTH_SHORT).show();
		} else if (marital_status_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "婚姻状况不能为空", Toast.LENGTH_SHORT).show();
		} else if (habitation_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "居住地不能为空", Toast.LENGTH_SHORT).show();
		} else if (hukou_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "户口不能为空", Toast.LENGTH_SHORT).show();
		} else if (mobile_phone_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
		} else if (mail_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
		} else if (job_status_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "求职状态不能为空", Toast.LENGTH_SHORT).show();
		} else if (report_duty_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "到岗时间不能为空", Toast.LENGTH_SHORT).show();
		} else if (job_type_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "工作类型不能为空", Toast.LENGTH_SHORT).show();
		} else if (want_industry_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "希望行业不能为空", Toast.LENGTH_SHORT).show();
		} else if (location_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "目标地点不能为空", Toast.LENGTH_SHORT).show();
		} else if (want_salary_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "月薪不能为空", Toast.LENGTH_SHORT).show();
		} else if (Aim_Function_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "目标职能不能为空", Toast.LENGTH_SHORT).show();
		} else if (school_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "学校不能为空", Toast.LENGTH_SHORT).show();
		} else if (level_of_education_.equals("")) {
			Toast.makeText(Curriculum_vitae_update.this, "教育水平不能为空", Toast.LENGTH_SHORT).show();
		} else {
			checkinput();
		}
	}

	public void checkinput() {

		Integer year = Integer.parseInt(birthday_et_year.getText().toString());
		Integer month = Integer.parseInt(birthday_et_month.getText().toString());
		Integer day = Integer.parseInt(birthday_et_day.getText().toString());

		if (year < 1800 || year > 2016 || month < 1 || month > 12 || day < 1) {
			Toast.makeText(Curriculum_vitae_update.this, "日期输入有误", Toast.LENGTH_SHORT).show();
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
					if (Data.getWhich() == 22) {
						update();
					} else {
						save();
					}
				} else {
					Toast.makeText(Curriculum_vitae_update.this, "日期输入有误", Toast.LENGTH_SHORT).show();
				}

				break;

			case 4:
			case 6:
			case 9:
			case 11:
				if (day < 31) {
					if (Data.getWhich() == 22) {
						update();
					} else {
						save();
					}
				} else {
					Toast.makeText(Curriculum_vitae_update.this, "日期输入有误", Toast.LENGTH_SHORT).show();
				}

				break;

			case 2:
				if (year % 4 == 0 && day < 30 || year % 4 != 0 && day < 29) {
					if (Data.getWhich() == 22) {
						update();
					} else {
						save();
					}
				} else {
					Toast.makeText(Curriculum_vitae_update.this, "日期输入有误", Toast.LENGTH_SHORT).show();
				}

				break;

			default:
				break;
			}
		}
	}

	public void save() {
		String y = birthday_et_year.getText().toString();
		String m1 = birthday_et_month.getText().toString();
		String d1 = birthday_et_day.getText().toString();
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

		Curriculum_vitae curriculum_vitae = new Curriculum_vitae();
		curriculum_vitae.setCv_name(cv_name_);
		curriculum_vitae.setName(name_);
		curriculum_vitae.setSex(sex);
		curriculum_vitae.setBirthday(birthday);
		curriculum_vitae.setLength_of_employment(length_of_employment_);
		curriculum_vitae.setMarital_status(marital_status_);
		curriculum_vitae.setHabitation(habitation_);
		curriculum_vitae.setHukou(hukou_);
		curriculum_vitae.setMobile_phone(mobile_phone_);
		curriculum_vitae.setMail(mail_);
		curriculum_vitae.setJob_status(job_status_);
		curriculum_vitae.setReport_duty(report_duty_);
		curriculum_vitae.setJob_type(job_type_);
		curriculum_vitae.setWant_industry(want_industry_);
		curriculum_vitae.setLocation(location_);
		curriculum_vitae.setWant_salary(want_salary_);
		curriculum_vitae.setAim_Function(Aim_Function_);
		curriculum_vitae.setSchool(school_);
		curriculum_vitae.setLevel_of_education(level_of_education_);
		curriculum_vitae.setSkills(skills.getText().toString());
		curriculum_vitae.setUser_id(Data.getUser_id());
		curriculum_vitae.save(Curriculum_vitae_update.this, new SaveListener() {
			@Override
			public void onSuccess() {
				Toast.makeText(Curriculum_vitae_update.this, "新建简历成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setAction("action.recreate");
				sendBroadcast(intent);
				onBackPressed();
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(Curriculum_vitae_update.this, "新建简历错误", Toast.LENGTH_SHORT).show();
			}
		});

	}
	
	public void update() {
		String y = birthday_et_year.getText().toString();
		String m1 = birthday_et_month.getText().toString();
		String d1 = birthday_et_day.getText().toString();
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

		Curriculum_vitae curriculum_vitae = new Curriculum_vitae();
		curriculum_vitae.setCv_name(cv_name_);
		curriculum_vitae.setName(name_);
		curriculum_vitae.setSex(sex);
		curriculum_vitae.setBirthday(birthday);
		curriculum_vitae.setLength_of_employment(length_of_employment_);
		curriculum_vitae.setMarital_status(marital_status_);
		curriculum_vitae.setHabitation(habitation_);
		curriculum_vitae.setHukou(hukou_);
		curriculum_vitae.setMobile_phone(mobile_phone_);
		curriculum_vitae.setMail(mail_);
		curriculum_vitae.setJob_status(job_status_);
		curriculum_vitae.setReport_duty(report_duty_);
		curriculum_vitae.setJob_type(job_type_);
		curriculum_vitae.setWant_industry(want_industry_);
		curriculum_vitae.setLocation(location_);
		curriculum_vitae.setWant_salary(want_salary_);
		curriculum_vitae.setAim_Function(Aim_Function_);
		curriculum_vitae.setSchool(school_);
		curriculum_vitae.setLevel_of_education(level_of_education_);
		curriculum_vitae.setSkills(skills.getText().toString());
		curriculum_vitae.setUser_id(Data.getUser_id());
		curriculum_vitae.update(Curriculum_vitae_update.this,Data.getId(), new UpdateListener() {
			@Override
			public void onSuccess() {
				Toast.makeText(Curriculum_vitae_update.this, "修改简历成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setAction("action.recreate");
				sendBroadcast(intent);
				onBackPressed();
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(Curriculum_vitae_update.this, "修改简历错误", Toast.LENGTH_SHORT).show();
			}
		});

	}

}
