package com.gamelifestats.glselevate.models;

import java.util.HashMap;


import android.content.Context;


public class MProfile extends ModelBase {

	static final String TABLE = "profile";
	static final String[] FIELDS_ARRAY = {
		"_id:0",
		"user_id:1", "has_profile:1", "name:2", "nickname:2", "hometown:1",
		"gender:2", "dob_mon:1", "dob_day:1", "active_status:1", "highest_level:2",
		"jersey:1", "position:2", "height:1", "weight:1", "vertical_leap:1",
		"wingspan:1", "best_offensive:2", "best_defensive:2", "range:2", "style_of_play:2", 
		"sk_scoring:2",	"sk_shooting:2", "sk_midrange:2", "sk_longrange:2", "sk_dunkslayups:2",
		"sk_ballcontrol:2",	"sk_ballhandle:2", "sk_crossover:2", "sk_courtvision:2", "sk_passing:2",
		"sk_off_awareness:2", "sk_def_awareness:2", "sk_man_d:2", "sk_post_d:2", "sk_team_d:2",
		"sk_closeouts:2", "sk_recovery:2"
	};
	
	public MProfile() {
		super(FIELDS_ARRAY, TABLE);
	}
	
	@Override
	public HashMap<String,String> insertInitial(){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put(FIELD_NAMES.get(1), "1");
		map.put(FIELD_NAMES.get(2), "0");
		return map;
	}
	
	public Boolean updateProfile(Context ctx){
		FIELD_VALUES.put(FIELD_NAMES.get(2), "1");
		return super.update(ctx, "_id=1");
	}
	
	public Boolean getUserProfile(Context ctx){
		return super.readRow(ctx, "_id=1");
	}
	
	
	

}
