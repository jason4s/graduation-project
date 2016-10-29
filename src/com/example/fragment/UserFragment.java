package com.example.fragment;

import com.example.jason4s.Login;
import com.example.jason4s.R;
import com.example.page.*;
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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserFragment extends Fragment implements OnClickListener {
	
	private View rootView;
	private RelativeLayout u1;
	private RelativeLayout u2;
	private RelativeLayout u3;
	private RelativeLayout u4;
	private RelativeLayout u5;
	private RelativeLayout u6;
	private Button user1;
	private Button user2;
	private Button user3;
	private com.example.tool.CircleImageView user_image;
	private TextView user_name;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.fragment_user, null);
		
		user_name=(TextView) rootView.findViewById(R.id.user_name);
		
		u1=(RelativeLayout) rootView.findViewById(R.id.u1);
		u2=(RelativeLayout) rootView.findViewById(R.id.u2);
		u3=(RelativeLayout) rootView.findViewById(R.id.u3);
		u4=(RelativeLayout) rootView.findViewById(R.id.u4);
		u5=(RelativeLayout) rootView.findViewById(R.id.u5);
		u6=(RelativeLayout) rootView.findViewById(R.id.u6);
		user1=(Button) rootView.findViewById(R.id.user1);
		user2=(Button) rootView.findViewById(R.id.user2);
		user3=(Button) rootView.findViewById(R.id.user3);
		user_image=(com.example.tool.CircleImageView) rootView.findViewById(R.id.user_image);
		
		u1.setOnClickListener(this);
		u2.setOnClickListener(this);
		u3.setOnClickListener(this);
		u4.setOnClickListener(this);
		u5.setOnClickListener(this);
		u6.setOnClickListener(this);
		user1.setOnClickListener(this);
		user2.setOnClickListener(this);
		user3.setOnClickListener(this);
		user_image.setOnClickListener(this);

		
		
		
		if (Data.getUser_name()=="") {
			user_image.setImageResource(R.drawable.head_nologin);
			user_name.setText("请登录");
		}
		else if(Data.getUser_head()=="") {
			user_image.setImageResource(R.drawable.head_login);
			user_name.setText(Data.getUser_name());
		}
		else {
			user_name.setText(Data.getUser_name());
		}
		
		
		return rootView;

	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		Animation alpha = new AlphaAnimation(0.5f, 1.0f);
		alpha.setDuration(200);
		
		if (Data.getUser_name() == "") {
			
			switch (arg0.getId()) {
			
				case R.id.u1:
					u1.startAnimation(alpha);
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getActivity(), Login.class));
					
					break;
					
				case R.id.u2:
					u2.startAnimation(alpha);
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getActivity(), Login.class));
					break;
					
				case R.id.u3:
					u3.startAnimation(alpha);
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getActivity(), Login.class));
					break;
					
				case R.id.u4:
					u4.startAnimation(alpha);
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getActivity(), Login.class));
					break;
					
				case R.id.u5:
					u5.startAnimation(alpha);
					startActivity(new Intent(getActivity(), Information_list.class));
					Data.setWhich(19);
					break;
					
				case R.id.u6:
					u6.startAnimation(alpha);
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getActivity(), Login.class));
					break;
					
				case R.id.user1:
					user1.startAnimation(alpha);
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getActivity(), Login.class));
					break;
					
				case R.id.user2:
					user2.startAnimation(alpha);
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getActivity(), Login.class));
					break;
					
				case R.id.user3:
					user3.startAnimation(alpha);
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getActivity(), Login.class));
					break;
					
				case R.id.user_image:
					user_image.startAnimation(alpha);
					startActivity(new Intent(getActivity(), Login.class));
					break;
					
				default:
					break;
			}
			
		}else {
			
			switch (arg0.getId()) {
			
				case R.id.u1:
					u1.startAnimation(alpha);
					startActivity(new Intent(getActivity(), Information_list.class));
					Data.setWhich(15);
					break;
					
				case R.id.u2:
					u2.startAnimation(alpha);
					startActivity(new Intent(getActivity(), Information_list.class));
					Data.setWhich(16);
					break;
					
				case R.id.u3:
					u3.startAnimation(alpha);
					startActivity(new Intent(getActivity(), Post.class));
					Data.setWhich(17);
					break;
					
				case R.id.u4:
					u4.startAnimation(alpha);
					startActivity(new Intent(getActivity(), Information_list.class));
					Data.setWhich(18);
					break;
					
				case R.id.u5:
					u5.startAnimation(alpha);
					startActivity(new Intent(getActivity(), Information_list.class));
					Data.setWhich(19);
					break;
					
				case R.id.u6:
					u6.startAnimation(alpha);
					Data.setWhich(20);
					startActivity(new Intent(getActivity(), Setting.class));
					break;
					
				case R.id.user1:
					user1.startAnimation(alpha);
					startActivity(new Intent(getActivity(), Information_list.class));
					Data.setWhich(21);
					break;
					
				case R.id.user2:
					user2.startAnimation(alpha);
					startActivity(new Intent(getActivity(), Information_list.class));
					Data.setWhich(22);
					break;
					
				case R.id.user3:
					user3.startAnimation(alpha);
					startActivity(new Intent(getActivity(), User_information.class));
					break;
					
				case R.id.user_image:
					user_image.startAnimation(alpha);
					startActivity(new Intent(getActivity(), User_information.class));
					break;
					
				default:
					break;
			}
		}
		
		
	}

}