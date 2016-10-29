package com.example.page;

import com.example.jason4s.R;
import com.example.tool.Data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Type_list extends Activity implements OnClickListener {
	private RelativeLayout button1;
	private LinearLayout button2;
	private LinearLayout button3;
	private TextView button1_tv;
	private TextView button2_tv;
	private TextView button3_tv;
	private Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_type_list);

		button1 = (RelativeLayout) findViewById(R.id.button1);
		button2 = (LinearLayout) findViewById(R.id.button2);
		button3 = (LinearLayout) findViewById(R.id.button3);
		button1_tv = (TextView) findViewById(R.id.button1_tv);
		button2_tv = (TextView) findViewById(R.id.button2_tv);
		button3_tv = (TextView) findViewById(R.id.button3_tv);
		back = (Button) findViewById(R.id.back);

		back.setOnClickListener(this);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);

	}

	public void onBackPressed() {
		// TODO Auto-generated method stub onBackPressed
		super.onBackPressed();
		finish();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub onClick
		Animation alpha = new AlphaAnimation(0.5f, 1.0f);
		alpha.setDuration(200);

		switch (arg0.getId()) {

		case R.id.button1:
			button1.startAnimation(alpha);
			if (Data.getWhich() == 1) {
				Data.setKey(button1_tv.getText().toString());
				startActivity(new Intent(Type_list.this, Information_list.class));
			} else {
				Data.setWhich(-2);
				Data.setType(button1_tv.getText().toString());
				startActivity(new Intent(Type_list.this, Information_list.class));
			}
			break;

		case R.id.button2:
			button2.startAnimation(alpha);
			if (Data.getWhich() == 1) {
				Data.setKey(button2_tv.getText().toString());
				startActivity(new Intent(Type_list.this, Information_list.class));
			} else {
				Data.setWhich(-2);
				Data.setType(button2_tv.getText().toString());
				startActivity(new Intent(Type_list.this, Information_list.class));
			}
			break;

		case R.id.button3:
			button3.startAnimation(alpha);
			if (Data.getWhich() == 1) {
				Data.setKey(button3_tv.getText().toString());
				startActivity(new Intent(Type_list.this, Information_list.class));
			} else {
				Data.setWhich(-2);
				Data.setType(button3_tv.getText().toString());
				startActivity(new Intent(Type_list.this, Information_list.class));
			}
			break;

		case R.id.back:
			onBackPressed();
			break;

		default:
			break;
		}
	};

}
