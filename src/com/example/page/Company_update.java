package com.example.page;

import java.util.List;

import com.example.jason4s.R;
import com.example.javabean.Company;
import com.example.tool.Data;
import com.example.tool.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class Company_update extends Activity {
	private EditText company_name;
	private EditText industries;
	private EditText nature;
	private EditText trimmed;
	private EditText address;
	private EditText introduction;

	private Button left;
	private Button right;

	private String company_name_;
	private String industries_;
	private String nature_;
	private String trimmed_;
	private String address_;
	private String introduction_;
	private String id;
	private Boolean flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_company_update);

		company_name = (EditText) findViewById(R.id.company_name_et);
		industries = (EditText) findViewById(R.id.industries_et);
		nature = (EditText) findViewById(R.id.nature_et);
		trimmed = (EditText) findViewById(R.id.trimmed_et);
		address = (EditText) findViewById(R.id.address_et);
		introduction = (EditText) findViewById(R.id.introduction_et);

		left = (Button) findViewById(R.id.left);
		right = (Button) findViewById(R.id.right);

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
		query.findObjects(Company_update.this, new FindListener<Company>() {

			@Override
			public void onSuccess(List<Company> object) {
				if (object.size() == 0) {
					Toast.makeText(Company_update.this, "没有权限", Toast.LENGTH_SHORT).show();
				} else {
					flag = true;
					for (Company company : object) {
						id = company.getObjectId().toString();
					}
					set_hint(id);
					
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Company_update.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void set_hint(String s) {
		// TODO Auto-generated method stub get_cv

		BmobQuery<Company> query = new BmobQuery<Company>();
		query.addWhereEqualTo("objectId", s);
		query.findObjects(Company_update.this, new FindListener<Company>() {

			@Override
			public void onSuccess(List<Company> object) {
				if (object.size() == 0) {
				} else {
					for (Company company : object) {
						company_name.setHint(company.getCompany_name());
						industries.setHint(company.getIndustries());
						nature.setHint(company.getNature());
						trimmed.setHint(company.getTrimmed());
						address.setHint(company.getAddress());
						introduction.setHint(company.getIntroduction());
					}
				}

			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Company_update.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});

	}

	public void onBackPressed() {
		// TODO Auto-generated method stub onBackPressed
		super.onBackPressed();
		finish();
	}

	private void replace_string() {
		// TODO Auto-generated method stub check
		company_name_ = company_name.getText().toString().replace(" ", "");
		industries_ = industries.getText().toString().replace(" ", "");
		nature_ = nature.getText().toString().replace(" ", "");
		trimmed_ = trimmed.getText().toString().replace(" ", "");
		address_ = address.getText().toString().replace(" ", "");
		introduction_ = introduction.getText().toString().replace(" ", "");
		change();
	}

	private void change() {
		if (company_name_.equals("")) {
			company_name_ = company_name.getHint().toString();
		}
		if (industries_.equals("")) {
			industries_ = industries.getHint().toString();
		}
		if (nature_.equals("")) {
			nature_ = nature.getHint().toString();
		}
		if (trimmed_.equals("")) {
			trimmed_ = trimmed.getHint().toString();
		}
		if (address_.equals("")) {
			address_ = address.getHint().toString();
		}
		if (introduction_.equals("")) {
			introduction_ = introduction.getHint().toString();
		}

		update();

	}

	public void update() {

		Company company = new Company();
		company.setCompany_name(company_name_);
		company.setIndustries(industries_);
		company.setNature(nature_);
		company.setTrimmed(trimmed_);
		company.setAddress(address_);
		company.setIntroduction(introduction_);
		company.update(Company_update.this, id, new UpdateListener() {
			@Override
			public void onSuccess() {
				Toast.makeText(Company_update.this, "修改公司信息成功", Toast.LENGTH_SHORT).show();
				onBackPressed();
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(Company_update.this, code+"修改公司信息错误"+msg, Toast.LENGTH_SHORT).show();
			}
		});

	}

}
