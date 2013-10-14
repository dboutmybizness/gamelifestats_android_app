package com.gamelifestats.glselevate.models;

import java.util.HashMap;

import com.gamelifestats.glselevate.ModelBase;

public class MProfile extends ModelBase {

	static final String TABLE = "profile";
	static final String[] FIELDS_ARRAY = {
		"_id:0",
		"user_id:1", "has_profile:1", "name:2", "nickname:2", "hometown:1",
		"gender:2", "dob_mon:1", "dob_day:1", "active_status:1", "highest_level:2",
		"jersey:1", "position:2", "height:1", "weight:1", "vertical_leap:1",
		"best_offensive:2", "best_defensive:2", "range:2", "style_of_play:2", "sk_scoring:2"
	};

	int _id = 0;
	int user_id = 1;
	int has_profile = 0;
	String name = "";
	String nickname = "";
	int hometown = 0;
	int gender = 0;
	int dob_mon = 0;
	int dob_day = 0;
	int active_status = 0;
	String highest_level = "";
	int jersey = 0;
	int height = 0;
	int weight = 0;
	int vertical_leap = 0;
	String best_offensive = "";
	String best_defensive = "";
	String range = "";
	String style_of_play = "";
	String sk_scoring = "";
	
	
	//int position;
	
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
	

}
