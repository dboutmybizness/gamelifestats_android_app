package com.gamelifestats.glselevate.models;

import java.util.Date;
import java.util.HashMap;



public class MAppMeta extends ModelBase {

	static final String TABLE = "app_meta";
	static final String[] FIELDS_ARRAY = {
		"_id:0",
		"date_download_original:1"
	};
	
	public MAppMeta() {
		super(FIELDS_ARRAY, TABLE);
	}
	
	@Override
	public HashMap<String,String> insertInitial(){
		long secs = (new Date().getTime())/1000;
		HashMap<String,String> map = new HashMap<String,String>();
		map.put(FIELD_NAMES.get(1), String.valueOf(secs));
		return map;
	}
	/*
	public Boolean updateProfile(Context ctx){
		FIELD_VALUES.put(FIELD_NAMES.get(2), "1");
		return super.update(ctx, "_id=1");
	}
	
	public Boolean getUserProfile(Context ctx){
		return super.readRow(ctx, "_id=1");
	}
	*/
	
	

}
