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
import android.widget.RatingBar;
import android.widget.Toast;

import com.gamelifestats.glselevate.R;
import com.gamelifestats.glselevate.interfaces.ViewsHelper;
import com.gamelifestats.glselevate.models.MProfile;

public class BballProfile_skills extends Fragment {
	View rootView;
	Context PContext;
	MProfile profile = new MProfile();
	
	ArrayList<RatingBar> bar_items = new ArrayList<RatingBar>();
	ArrayList<String> db_fields = new ArrayList<String>();
	HashMap <String,Integer> bar_k_v = new HashMap<String,Integer>();
	Button save_button;
	ViewsHelper VH;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootView = inflater.inflate(R.layout.profile_skills, container, false);
		PContext = rootView.getContext();
		VH = new ViewsHelper();
		
		setUpViews();
		return rootView;
	}
	
	public void saveSkills(){
		
		profile.FIELD_VALUES.clear();
		for(int i = 0; i < db_fields.size(); i++){
			profile.FIELD_VALUES.put(db_fields.get(i), VH.getText2Str(bar_items.get(i)));
		}

		if ( profile.updateProfile(PContext) ){
			Toast.makeText(PContext, "saved", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void setUpViews(){
		
		bar_k_v.put("sk_scoring", R.id.RatingScoring);
		bar_k_v.put("sk_shooting", R.id.RatingShooting);
		bar_k_v.put("sk_midrange", R.id.RatingMidRange);
		bar_k_v.put("sk_longrange", R.id.RatingLongRange);
		bar_k_v.put("sk_dunkslayups", R.id.RatingDunksLayups);
		bar_k_v.put("sk_ballcontrol", R.id.RatingBallControl);
		bar_k_v.put("sk_ballhandle", R.id.RatingBallHandle);
		bar_k_v.put("sk_crossover", R.id.RatingCrossover);
		bar_k_v.put("sk_courtvision", R.id.RatingCourtVision);
		bar_k_v.put("sk_passing", R.id.RatingPassing);

		for (HashMap.Entry <String, Integer> entry : bar_k_v.entrySet()) {
		    bar_items.add((RatingBar) rootView.findViewById(entry.getValue()));
		    db_fields.add(entry.getKey());
		}

		save_button = (Button) rootView.findViewById(R.id.save_skills);
		save_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				saveSkills();
			}
			
		});
		
		if ( profile.getUserProfile(PContext)){
			for (int i=0; i<bar_items.size(); i++){
				VH.rViews(bar_items.get(i),profile.FIELD_VALUES.get(db_fields.get(i)));
			}
		}
	}
	
}
