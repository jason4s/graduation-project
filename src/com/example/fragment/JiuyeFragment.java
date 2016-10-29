package com.example.fragment;

import com.example.jason4s.R;
import com.example.page.Information_list;
import com.example.page.Type_list;
import com.example.tool.Data;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class JiuyeFragment extends Fragment implements OnClickListener{
	
	private View rootView;
	private LinearLayout j1;
	private RelativeLayout j2;
	private RelativeLayout j3;
	private RelativeLayout j4;
	private RelativeLayout j5;
	private RelativeLayout j6;
	private RelativeLayout j7;
	private RelativeLayout j8;
	private RelativeLayout j9;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.fragment_jiuye, null);
		
		j1=(LinearLayout) rootView.findViewById(R.id.j1);
		j2=(RelativeLayout) rootView.findViewById(R.id.j2);
		j3=(RelativeLayout) rootView.findViewById(R.id.j3);
		j4=(RelativeLayout) rootView.findViewById(R.id.j4);
		j5=(RelativeLayout) rootView.findViewById(R.id.j5);
		j6=(RelativeLayout) rootView.findViewById(R.id.j6);
		j7=(RelativeLayout) rootView.findViewById(R.id.j7);
		j8=(RelativeLayout) rootView.findViewById(R.id.j8);
		j9=(RelativeLayout) rootView.findViewById(R.id.j9);
		
		j1.setOnClickListener(this);
		j2.setOnClickListener(this);
		j3.setOnClickListener(this);
		j4.setOnClickListener(this);
		j5.setOnClickListener(this);
		j6.setOnClickListener(this);
		j7.setOnClickListener(this);
		j8.setOnClickListener(this);
		j9.setOnClickListener(this);
		
		return rootView;

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		Animation alpha = new AlphaAnimation(0.5f, 1.0f);
		alpha.setDuration(200);
		
		switch (arg0.getId()) {
		
		case R.id.j1:
			j1.startAnimation(alpha);
			startActivity(new Intent(getActivity(), Type_list.class));
			Data.setWhich(1);
			break;
			
		case R.id.j2:
			j2.startAnimation(alpha);
			startActivity(new Intent(getActivity(), Information_list.class));
			Data.setWhich(2);
			break;
			
		case R.id.j3:
			j3.startAnimation(alpha);
			startActivity(new Intent(getActivity(), Information_list.class));
			Data.setWhich(3);
			break;
			
		case R.id.j4:
			j4.startAnimation(alpha);
			startActivity(new Intent(getActivity(), Information_list.class));
			Data.setWhich(4);
			break;
			
		case R.id.j5:
			j5.startAnimation(alpha);
			startActivity(new Intent(getActivity(), Information_list.class));
			Data.setWhich(5);
			break;
			
		case R.id.j6:
			j6.startAnimation(alpha);
			startActivity(new Intent(getActivity(), Information_list.class));
			Data.setWhich(6);
			break;
			
		case R.id.j7:
			j7.startAnimation(alpha);
			startActivity(new Intent(getActivity(), Information_list.class));
			Data.setWhich(7);
			break;
			
		case R.id.j8:
			j8.startAnimation(alpha);
			startActivity(new Intent(getActivity(), Information_list.class));
			Data.setWhich(8);
			break;
			
		case R.id.j9:
			j9.startAnimation(alpha);
			startActivity(new Intent(getActivity(), Information_list.class));
			Data.setWhich(9);
			break;
			
		default:
			break;
		}
	}

}
