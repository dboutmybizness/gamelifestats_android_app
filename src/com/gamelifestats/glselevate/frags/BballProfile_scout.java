package com.gamelifestats.glselevate.frags;

import helper.CallBackHelper;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gamelifestats.glselevate.R;
import com.gamelifestats.glselevate.SetUpPageView;
import com.gamelifestats.glselevate.models.MProfile;

public class BballProfile_scout extends Fragment {
	View rootView;
	Context PContext;
	MProfile profile = new MProfile();
	SetUpPageView SPV;
	Button save_button;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootView = inflater.inflate(R.layout.profile_scout, container, false);
		PContext = rootView.getContext();
		
		SPV = new SetUpPageView();
		if(profile.getUserProfile(PContext)){
			SPV.setOnCreateFieldsHash(profile.FIELD_VALUES);
		}
		SPV.addView((SeekBar) rootView.findViewById(R.id.EditHeight),60,
				new CallBackHelper((TextView) rootView.findViewById(R.id.printHeight),1,36),"height");
		SPV.addView((SeekBar) rootView.findViewById(R.id.EditWeight), 262,
				new CallBackHelper((TextView) rootView.findViewById(R.id.printWeight),3,88),"weight");
		SPV.addView((SeekBar) rootView.findViewById(R.id.EditVerticalLeap),60,
				new CallBackHelper((TextView) rootView.findViewById(R.id.printVertical),2,12),"vertical_leap");
		SPV.addView((SeekBar) rootView.findViewById(R.id.EditWingSpan),60,
				new CallBackHelper((TextView) rootView.findViewById(R.id.printWingspan),2,36),"wingspan");
		
		save_button = (Button) rootView.findViewById(R.id.save_scout);
		save_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				saveScout();
			}
			
		});
		return rootView;
	}
	
	public void saveScout(){
		SPV.loadSaveable();
		
		profile.FIELD_VALUES = SPV.Saveable_fieldHash;
		if ( profile.updateProfile(PContext) ){
			Toast.makeText(PContext, "saved", Toast.LENGTH_SHORT).show();
		}
	}
}
