package com.example.page;

import java.util.List;

import com.example.jason4s.R;
import com.example.javabean.Company;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class Company_show extends Activity {
	private TextView company_name;
	private TextView industries;
	private TextView nature;
	private TextView trimmed;
	private TextView address;
	private TextView introduction;
	private Button left;
	private String company_id;
	private Button buttom;
	private String company_name__;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_company_show);

		company_name = (TextView) findViewById(R.id.company_name);
		industries = (TextView) findViewById(R.id.industries);
		nature = (TextView) findViewById(R.id.nature);
		trimmed = (TextView) findViewById(R.id.trimmed);
		address = (TextView) findViewById(R.id.address);
		introduction = (TextView) findViewById(R.id.introduction);
		left = (Button) findViewById(R.id.left);
		buttom = (Button) findViewById(R.id.buttom);

		Intent intent = getIntent();
		company_id = intent.getStringExtra("company_id");

		get();

		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onBackPressed();
			}
		});

		buttom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Animation alpha = new AlphaAnimation(0.5f, 1.0f);
				alpha.setDuration(200);
				buttom.startAnimation(alpha);

				Intent intent = new Intent();
				intent.putExtra("title", company_name__);
				intent.setClass(Company_show.this, Post.class);
				startActivity(intent);
			}
		});

	}

	private void get() {
		// TODO Auto-generated method stub get

		BmobQuery<Company> query = new BmobQuery<Company>();
		query.addWhereEqualTo("objectId", company_id);
		query.findObjects(Company_show.this, new FindListener<Company>() {

			@Override
			public void onSuccess(List<Company> object) {
				if (object.size() == 0) {
					Toast.makeText(Company_show.this, "没有信息", Toast.LENGTH_SHORT).show();
				} else {
					for (Company company : object) {
						company_name.setText(company.getCompany_name());
						industries.setText(company.getIndustries());
						nature.setText(company.getNature());
						trimmed.setText(company.getTrimmed());
						address.setText(company.getAddress());
						introduction.setText(company.getIntroduction());
						company_name__ = company.getCompany_name();
					}

				}
			}

			@Override
			public void onError(int code, String msg) {
				Toast.makeText(Company_show.this, "读取错误", Toast.LENGTH_SHORT).show();
			}
		});

	}

	public void onBackPressed() {
		// TODO Auto-generated method stub onBackPressed
		super.onBackPressed();
		finish();
	};

}
