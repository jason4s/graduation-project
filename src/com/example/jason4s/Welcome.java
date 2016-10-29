package com.example.jason4s;

import android.os.Bundle;
import cn.bmob.v3.Bmob;

import java.util.Timer;
import java.util.TimerTask;

import com.example.jason4s.R;

import android.app.Activity;
import android.content.Intent;

public class Welcome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		Bmob.initialize(this, "fc51ad746669c5d92e2d39fd399e8c32");
		final Intent intent = new Intent(Welcome.this, Main.class);  
		Timer timer = new Timer(); 
		TimerTask task = new TimerTask() {  
		    @Override  
		    public void run() {   
		    startActivity(intent);  
		     } 
		 };
		timer.schedule(task, 1000 * 2); 
	}

	

}
