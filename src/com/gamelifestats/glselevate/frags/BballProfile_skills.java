package com.gamelifestats.glselevate.frags;

import helper.SetUpPageView;
import helper.ViewsHelper;

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
import com.gamelifestats.glselevate.models.MProfile;

public class BballProfile_skills extends Fragment {
	View rootView;
	Context PContext;
	MProfile profile = new MProfile();
	SetUpPageView SPV;
	Button save_button;
	
	HashMap <String,Integer> bar_k_v = new HashMap<String,Integer>();
	
	ViewsHelper VH;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootView = inflater.inflate(R.layout.profile_skills, container, false);
		PContext = rootView.getContext();
		
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
		bar_k_v.put("sk_off_awareness", R.id.RatingOffensiveAwareness);
		bar_k_v.put("sk_man_d", R.id.RatingMan_d);
		bar_k_v.put("sk_team_d", R.id.RatingTeam_d);
		bar_k_v.put("sk_post_d", R.id.RatingPost_d);
		bar_k_v.put("sk_closeouts", R.id.RatingCloseouts);
		bar_k_v.put("sk_recovery", R.id.RatingRecovery);
		bar_k_v.put("sk_def_awareness", R.id.RatingDefensiveAwareness);
		
		SPV = new SetUpPageView();
		if(profile.getUserProfile(PContext)){
			SPV.setOnCreateFieldsHash(profile.FIELD_VALUES);
		}
		
		for (HashMap.Entry <String, Integer> entry : bar_k_v.entrySet()) {
			SPV.addView((RatingBar) rootView.findViewById(entry.getValue()), null, entry.getKey());
		}
		SPV.addView((RatingBar) rootView.findViewById(R.id.RatingScoring), null,"sk_scoring");
		
		save_button = (Button) rootView.findViewById(R.id.save_skills);
		save_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				saveSkills();
			}
			
		});
		
		VH = new ViewsHelper();
		
		//setUpViews();
		return rootView;
	}
	
	public void saveSkills(){
		SPV.loadSaveable();
		
		profile.FIELD_VALUES = SPV.Saveable_fieldHash;
		if ( profile.updateProfile(PContext) ){
			Toast.makeText(PContext, "saved", Toast.LENGTH_SHORT).show();
		}
	}
	
}
