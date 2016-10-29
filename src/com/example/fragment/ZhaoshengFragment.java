package com.example.fragment;

import com.example.jason4s.R;
import com.example.page.Information_list;
import com.example.page.Type_list;
import com.example.tool.Data;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class ZhaoshengFragment extends Fragment implements OnClickListener {
	
	private View rootView;
	private RelativeLayout z1;
	private RelativeLayout z2;
	private RelativeLayout z3;
	private RelativeLayout z4;
	private RelativeLayout z5;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.fragment_zhaosheng, null);
		
		z1=(RelativeLayout) rootView.findViewById(R.id.z1);
		z2=(RelativeLayout) rootView.findViewById(R.id.z2);
		z3=(RelativeLayout) rootView.findViewById(R.id.z3);
		z4=(RelativeLayout) rootView.findViewById(R.id.z4);
		z5=(RelativeLayout) rootView.findViewById(R.id.z5);
		
		z1.setOnClickListener(this);
		z2.setOnClickListener(this);
		z3.setOnClickListener(this);
		z4.setOnClickListener(this);
		z5.setOnClickListener(this);
		
		return rootView;

	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		Animation alpha = new AlphaAnimation(0.5f, 1.0f);
		alpha.setDuration(200);
		
		switch (arg0.getId()) {
		
		case R.id.z1:
			rootView.findViewById(R.id.imageView1).startAnimation(alpha);
			startActivity(new Intent(getActivity(), Information_list.class));
			Data.setWhich(10);
			break;
			
		case R.id.z2:
			z2.startAnimation(alpha);
			startActivity(new Intent(getActivity(), Information_list.class));
			Data.setWhich(11);
			break;
			
		case R.id.z3:
			z3.startAnimation(alpha);
			startActivity(new Intent(getActivity(), Information_list.class));
			Data.setWhich(12);
			break;
			
		case R.id.z4:
			z4.startAnimation(alpha);
			startActivity(new Intent(getActivity(), Information_list.class));
			Data.setWhich(13);
			break;
			
		case R.id.z5:
			z5.startAnimation(alpha);
			startActivity(new Intent(getActivity(), Type_list.class));
			break;
			
		default:
			break;
		}
	}

}