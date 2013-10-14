package com.gamelifestats.glselevate.frags;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gamelifestats.glselevate.R;
import com.gamelifestats.glselevate.models.MProfile;

public class BballProfile_bio extends Fragment {
	View rootView;
	Context PContext;
	EditText name,nickname;
	Button save_button;
	MProfile profile = new MProfile();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootView = inflater.inflate(R.layout.profile_bio, container, false);
		PContext = rootView.getContext();
		
		setUpViews();
		return rootView;
	}
	
	public void setUpViews(){
		save_button = (Button) rootView.findViewById(R.id.save_bio);
		save_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				saveBio();
				
			}
			
		});

		name = (EditText) rootView.findViewById(R.id.editName);
	}
	
	public void saveBio(){
		profile.FIELD_VALUES.clear();
		profile.FIELD_VALUES.put("name", name.getText().toString());
		profile.updateProfile(PContext);
	}
	
	
}
