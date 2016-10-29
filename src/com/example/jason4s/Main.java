package com.example.jason4s;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.fragment.*;
import com.example.jason4s.R;
import com.example.page.*;
import com.example.tool.CircleImageView;
import com.example.tool.Data;
import com.example.tool.Main_bottom_change;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends FragmentActivity implements OnPageChangeListener, OnClickListener {

	private FrameLayout login;
	private Button left;
	private FrameLayout search;
	public JiuyeFragment jiuye_fragment;
	private ZhaoshengFragment zhaosheng_fragment;
	private UserFragment user_fragment;

	private ViewPager fragment_viewpager;
	private List<Fragment> fragment_list = new ArrayList<Fragment>();
	private FragmentPagerAdapter fragmentpager_adapter;
	private TextView login_tv;
	private FrameLayout search_frameLayout;
	private com.example.tool.CircleImageView login_iv;

	private List<Main_bottom_change> mTabIndicator = new ArrayList<Main_bottom_change>();

	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_main);

		login = (FrameLayout) findViewById(R.id.login);
		search = (FrameLayout) findViewById(R.id.search);
		login_tv = (TextView) findViewById(R.id.login_tv);
		login_iv = (CircleImageView) findViewById(R.id.login_iv);
		left = (Button) findViewById(R.id.left);
		search_frameLayout = (FrameLayout) findViewById(R.id.search_frameLayout);

		initDatas();

		fragment_viewpager.setAdapter(fragmentpager_adapter);
		fragment_viewpager.setOnPageChangeListener(this);

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("action.recreate");
		registerReceiver(mRefreshBroadcastReceiver, intentFilter);

		left.setText("退出");

		if (Data.getUser_name() == "") {
			login_tv.setText("登录");
			login.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Animation alpha = new AlphaAnimation(0.5f, 1.0f);
					alpha.setDuration(200);
					login.startAnimation(alpha);

					Intent intent = new Intent(Main.this, Login.class);
					Main.this.startActivity(intent);
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
					// TODO Auto-generated method stub
					Animation alpha = new AlphaAnimation(0.5f, 1.0f);
					alpha.setDuration(200);
					login.startAnimation(alpha);

					Intent intent = new Intent(Main.this, User_information.class);
					Main.this.startActivity(intent);
				}
			});
		}

		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Animation alpha = new AlphaAnimation(0.5f, 1.0f);
				alpha.setDuration(200);
				search_frameLayout.startAnimation(alpha);

				Intent intent = new Intent(Main.this, Search.class);
				Main.this.startActivity(intent);
			}
		});

		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				exitBy2Click();}
		});

	}

	private void initDatas() {
		jiuye_fragment = new JiuyeFragment();
		fragment_list.add(jiuye_fragment);

		zhaosheng_fragment = new ZhaoshengFragment();
		fragment_list.add(zhaosheng_fragment);

		user_fragment = new UserFragment();
		fragment_list.add(user_fragment);

		fragmentpager_adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return fragment_list.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return fragment_list.get(arg0);
			}
		};

		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		fragment_viewpager = (ViewPager) findViewById(R.id.fragment_viewpager);

		Main_bottom_change one = (Main_bottom_change) findViewById(R.id.Main_bottom_1);
		Main_bottom_change two = (Main_bottom_change) findViewById(R.id.Main_bottom_2);
		Main_bottom_change three = (Main_bottom_change) findViewById(R.id.Main_bottom_3);

		mTabIndicator.add(one);
		mTabIndicator.add(two);
		mTabIndicator.add(three);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);

		one.setIconAlpha(1.0f);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击退出函数
		}
		return false;
	}

	private static Boolean isExit = false;

	@SuppressWarnings("deprecation")
	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true;
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false;
				}
			}, 1000 * 2);

		} else {
			int currentVersion = android.os.Build.VERSION.SDK_INT;
			if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
				Intent startMain = new Intent(Intent.ACTION_MAIN);
				startMain.addCategory(Intent.CATEGORY_HOME);
				startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(startMain);
				System.exit(0);
			} else {// android2.1
				ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
				am.restartPackage(getPackageName());
			}
		}
	}

	@Override
	public void onPageSelected(int arg0) {
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		// Log.e("TAG", "position = " + position + " , positionOffset = "
		// + positionOffset);

		if (positionOffset > 0) {
			Main_bottom_change left = mTabIndicator.get(position);
			Main_bottom_change right = mTabIndicator.get(position + 1);

			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onClick(View v) {

		resetOtherTabs();

		switch (v.getId()) {
		case R.id.Main_bottom_1:
			mTabIndicator.get(0).setIconAlpha(1.0f);
			fragment_viewpager.setCurrentItem(0, false);
			break;
		case R.id.Main_bottom_2:
			mTabIndicator.get(1).setIconAlpha(1.0f);
			fragment_viewpager.setCurrentItem(1, false);
			break;
		case R.id.Main_bottom_3:
			mTabIndicator.get(2).setIconAlpha(1.0f);
			fragment_viewpager.setCurrentItem(2, false);
			break;

		}

	}

	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicator.size(); i++) {
			mTabIndicator.get(i).setIconAlpha(0);
		}
	}

	private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("action.recreate")) {
				recreate();
			}
		}
	};
	

}
