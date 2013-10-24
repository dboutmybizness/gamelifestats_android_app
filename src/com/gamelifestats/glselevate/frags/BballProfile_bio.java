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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gamelifestats.glselevate.CallBackHelper;
import com.gamelifestats.glselevate.R;
import com.gamelifestats.glselevate.SetUpPageView;
import com.gamelifestats.glselevate.models.MProfile;

public class BballProfile_bio extends Fragment {
	View rootView;
	Context PContext;
	MProfile profile = new MProfile();
	SetUpPageView SPV;
	Button save_button;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootView = inflater.inflate(R.layout.profile_bio, container, false);
		PContext = rootView.getContext();
		
		save_button = (Button) rootView.findViewById(R.id.save_bio);
		save_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				saveBio();
			}
			
		});
		
		SPV = new SetUpPageView();
		if(profile.getUserProfile(PContext)){
			SPV.setOnCreateFieldsHash(profile.FIELD_VALUES);
		}
		SPV.addView((SeekBar) rootView.findViewById(R.id.EditJersey), 
				new CallBackHelper((TextView) rootView.findViewById(R.id.PrintJersey),4,0), "jersey");
		SPV.addView((EditText) rootView.findViewById(R.id.editName), null, "name");
		SPV.addView((EditText) rootView.findViewById(R.id.editNickName), null, "nickname");

		return rootView;
	}
	
	
	
	public void saveBio(){
		SPV.loadSaveable();
		
		profile.FIELD_VALUES = SPV.Saveable_fieldHash;
		if ( profile.updateProfile(PContext) ){
			Toast.makeText(PContext, "saved", Toast.LENGTH_SHORT).show();
		}
	}
	
}
