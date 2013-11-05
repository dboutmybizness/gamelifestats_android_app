package com.gamelifestats.glselevate.models;

import helper.StatsHelper;

import java.util.HashMap;

import android.content.Context;


public class MStatsCareer extends ModelBase {

	static final String TABLE = "career";
	static final String[] FIELDS_ARRAY = {
		"_id:0",
		"user_id:1", "tgames:1", "tminutes:1", "tpoints:1", "trebounds:1",
		"trebs_off:1", "trebs_def:1", "tassists:1", "tsteals:1", "tblocks:1",
		"tturnovers:1", "tfouls:1"
	};
	
	final HashMap<String,String> extrafields = new HashMap<String,String>();
	
	public MStatsCareer() {
		super(FIELDS_ARRAY, TABLE);
		extrafields.put("apoints", "tpoints");
		extrafields.put("arebounds", "trebounds");
		extrafields.put("aassists", "tassists");
		extrafields.put("asteals", "tsteals");
		extrafields.put("ablocks", "tblocks");
		extrafields.put("aturnovers", "tturnovers");
		extrafields.put("afouls", "tfouls");
		extrafields.put("aminutes", "tminutes");
	}
	
	@Override
	public HashMap<String,String> insertInitial(){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put(FIELD_NAMES.get(1), "1");
		map.put(FIELD_NAMES.get(2), "0");
		return map;
	}
	/*
	public Boolean updateProfile(Context ctx){
		FIELD_VALUES.put(FIELD_NAMES.get(2), "1");
		return super.update(ctx, "_id=1");
	}*/
	
	public Boolean getCareer(Context ctx){
		boolean result = super.readRow(ctx, "_id=1");
		
		if (result){
			if ( FIELD_VALUES.get("tgames").equals("0") ) return false;
			
			for (HashMap.Entry <String, String> entry : extrafields.entrySet()) {
			    FIELD_VALUES.put(entry.getKey(), gAvg(Integer.valueOf(FIELD_VALUES.get(entry.getValue()))));
			}
		}
		
		return result;
	}
	
	private String gAvg(int stat){
		float val = StatsHelper.divPerc(stat, FIELD_VALUES.get("tgames"));
		return String.valueOf(val);
	}
	
	
	

}
