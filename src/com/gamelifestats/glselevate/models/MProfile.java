package com.gamelifestats.glselevate.models;

import java.util.ArrayList;

import com.gamelifestats.glselevate.DBAdapter;

public class MProfile {

	static final String TABLE = "profile";
	
	private final String[] FIELDS_ARRAY = {
		"_id:0",
		"user_id:1", "has_profile:1", "name:2", "nickname:2", "hometown:1",
		"gender:2", "dob_mon:1", "dob_day:1", "active_status:1", "highest_level:2",
		"jersey:1", "height:1", "weight:1", "vertical_leap:1", "best_offensive:2",
		"best_defensive:2", "range:2", "style_of_play:2", "sk_scoring:3"
	};

	private ArrayList<String> FIELD_NAMES = new ArrayList<String>();
	private ArrayList<String> FIELD_TYPES = new ArrayList<String>();


	int has_profile = 0;
	String name = "";
	String nickname = "";
	int position;
	int jersey = 0;
	
	public MProfile() {
		parse_fields_array();
	}
	
	private String[] setFields(){
		return FIELD_NAMES.toArray(new String[FIELD_NAMES.size()]);
	}
	
	private void parse_fields_array(){
		for (int i = 0; i < FIELDS_ARRAY.length; i++){
			String[] str = FIELDS_ARRAY[i].split(":");
			this.FIELD_NAMES.add(str[0]);
			this.FIELD_TYPES.add(DBAdapter.FIELD_TYPES_ARRAY[Integer.parseInt(str[1])]);
		}
	}
	
	public String getCreateStatement(){
		String str = "";
		for ( int i = 0; i < FIELDS_ARRAY.length; i++){
			str += FIELD_NAMES.get(i) + " " + FIELD_TYPES.get(i);
			if ( i < (FIELDS_ARRAY.length - 1)) str += ", ";
		}
		return "create table "+MProfile.TABLE+" (" + str + " )";
	}

	


}
