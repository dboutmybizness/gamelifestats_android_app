package com.gamelifestats.glselevate.frags;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.gamelifestats.glselevate.R;
import com.gamelifestats.glselevate.interfaces.ViewsHelper;
import com.gamelifestats.glselevate.models.MProfile;

public class BballProfile_scout extends Fragment {
	View rootView;
	Context PContext;
	Button save_button;
	HashMap<String,Integer> pageItems = new HashMap<String,Integer>();
	ArrayList<SeekBar> seeks = new ArrayList<SeekBar>();
	ArrayList<String> db_fields = new ArrayList<String>();
	MProfile profile = new MProfile();
	
	ViewsHelper VH;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootView = inflater.inflate(R.layout.profile_scout, container, false);
		PContext = rootView.getContext();
		VH = new ViewsHelper();
		setUpViews();
		return rootView;
	}
	
	public void saveScout(){
		
		profile.FIELD_VALUES.clear();
		for(int i = 0; i < db_fields.size(); i++){
			profile.FIELD_VALUES.put(db_fields.get(i), VH.getText2Str(seeks.get(i)));
		}

		if ( profile.updateProfile(PContext) ){
			Toast.makeText(PContext, "saved", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void setUpViews(){
		pageItems.put("height", R.id.EditHeight);
		pageItems.put("weight", R.id.EditWeight);
		pageItems.put("vertical_leap", R.id.EditVerticalLeap);
		pageItems.put("wingspan", R.id.EditWingSpan);
		
		
		for (HashMap.Entry <String, Integer> entry : pageItems.entrySet()) {
		    seeks.add((SeekBar) rootView.findViewById(entry.getValue()));
		    db_fields.add(entry.getKey());
		}

		save_button = (Button) rootView.findViewById(R.id.save_scout);
		save_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				saveScout();
			}
			
		});
		
		if ( profile.getUserProfile(PContext)){
			for (int i=0; i<seeks.size(); i++){
				VH.rViews(seeks.get(i),profile.FIELD_VALUES.get(db_fields.get(i)));
			}
		}
	}
}
