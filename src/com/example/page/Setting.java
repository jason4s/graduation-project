package com.example.page;

import com.example.jason4s.R;
import com.example.tool.Data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Setting extends Activity {
	private Button job;
	private Button company;
	private Button cv;
	private Button back;
	private Button top;
	private Button collection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_setting);

		job = (Button) findViewById(R.id.job);
		company = (Button) findViewById(R.id.company);
		cv = (Button) findViewById(R.id.cv);
		back = (Button) findViewById(R.id.back);
		top = (Button) findViewById(R.id.top);
		collection = (Button) findViewById(R.id.collection);

		job.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Data.setWhich2(1);
				startActivity(new Intent(Setting.this, Information_list.class));
			}
		});

		company.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(Setting.this, Company_update.class));
			}
		});
		
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onBackPressed();
			}
		});
		
		top.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Data.setWhich(-4);
				startActivity(new Intent(Setting.this, Job_update.class));
			}
		});
		
		cv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Data.setWhich2(2);
				startActivity(new Intent(Setting.this, Information_list.class));
			}
		});
		
		collection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Data.setWhich2(3);
				startActivity(new Intent(Setting.this, Information_list.class));
			}
		});

	}

	public void onBackPressed() {
		// TODO Auto-generated method stub onBackPressed
		super.onBackPressed();
		finish();
	};

}
