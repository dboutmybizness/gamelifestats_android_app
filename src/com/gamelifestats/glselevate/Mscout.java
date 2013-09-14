package com.gamelifestats.glselevate;

import java.util.HashMap;

import android.content.Context;

public class MScout extends DBAdapter {
	static final String TABLE = "scout";

	static final String ID = "_id";
	static final String USERID = "user_id";
	static final String NAME = "name";
	static final String POSITION = "position";
	static final String JERSEY = "jersey";
	static final String HEIGHT = "height";
	static final String WEIGHT = "weight";
	static final String WINGSPAN = "wingspan";
	static final String VERTICAL_LEAP = "vertical_leap";
	static final String ACTIVE_STATUS = "active_status";

	static final String CREATE_TABLE =
		"create table "+TABLE+" (" +
		ID+" integer primary key, " +
		USERID+" integer,"+
		NAME+" text,"+
		POSITION+" text,"+
		JERSEY+" integer," +
		HEIGHT+" text" +
		WEIGHT+" text,"+
		WINGSPAN+" text,"+
		VERTICAL_LEAP+" text,"+
		ACTIVE_STATUS+" text"+
		")";	

	static final String[] getFields = {
        	ID,USERID,NAME,POSITION,JERSEY,HEIGHT,WEIGHT,WINGSPAN,VERTICAL_LEAP,ACTIVE_STATUS
        };	
	
	
	
	public MAppMeta(Context ctx) {
		super(ctx);
		super.setTable(TABLE);
		super.setGetFields(getFields);
	}
	
	/*
	public static HashMap<String, String> initializeApp(){
		HashMap<String, String> m = new HashMap<String,String>();
		m.put(ID, "1");
		m.put(DB_VERSION, StatsHelper.int2Str(DBAdapter.DATABASE_VERSION));
		m.put(APP_USER_ID, "1");
		return m;
		
	}*/
	
}
