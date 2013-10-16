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
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.gamelifestats.glselevate.R;
import com.gamelifestats.glselevate.interfaces.ViewsHelper;
import com.gamelifestats.glselevate.models.MProfile;

public class BballProfile_bio extends Fragment {
	View rootView;
	Context PContext;
	EditText name,nickname;
	TextView print_jersey;
	SeekBar jersey;
	Button save_button;
	MProfile profile = new MProfile();
	ViewsHelper VH;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootView = inflater.inflate(R.layout.profile_bio, container, false);
		PContext = rootView.getContext();
		VH = new ViewsHelper();
		setUpViews();
		return rootView;
	}
	
	
	
	public void saveBio(){
		
		profile.FIELD_VALUES.clear();
		profile.FIELD_VALUES.put("name", VH.getText2Str(name));
		profile.FIELD_VALUES.put("nickname", VH.getText2Str(nickname));
		profile.FIELD_VALUES.put("jersey", VH.getText2Str(jersey));
		if ( profile.updateProfile(PContext) ){
			Toast.makeText(PContext, "saved", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void setUpViews(){
		
		
		name = (EditText) rootView.findViewById(R.id.editName);
		nickname = (EditText) rootView.findViewById(R.id.editNickName);
		
		jersey = (SeekBar) rootView.findViewById(R.id.EditJersey);
		print_jersey = (TextView) rootView.findViewById(R.id.PrintJersey);
		jersey.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				updateJersey();
			}
			
		});
		
		save_button = (Button) rootView.findViewById(R.id.save_bio);
		save_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				saveBio();
			}
			
		});
		
		if ( profile.getUserProfile(PContext)){
			VH.rViews(name, profile.FIELD_VALUES.get("name"));
			VH.rViews(nickname, profile.FIELD_VALUES.get("nickname"));
			VH.rViews(jersey, profile.FIELD_VALUES.get("jersey"));
			updateJersey();
			
		}
		
	}
	
	public void updateJersey(){
		int progress = jersey.getProgress();
		String str = (progress == 100)? "00" : String.valueOf(progress); 
		VH.rViews(print_jersey,str);
	}
	
	
}
