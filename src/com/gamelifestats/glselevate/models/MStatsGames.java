package com.gamelifestats.glselevate.models;

import java.util.HashMap;

import android.content.Context;

import com.gamelifestats.glselevate.helper.StatsHelper;




public class MStatsGames extends ModelBase {
	
	static final String TABLE = "games";
	static final String[] FIELDS_ARRAY = {
		"_id:0",
		"user_id:1", "minutes:1", "points:1", "rebounds:1", "reb_off:1",
		"reb_def:1", "assists:1", "steals:1", "blocks:1", "turnovers:1",
		"fouls:1", "fg2m:1", "fg2a:1", "fg3m:1", "fg3a:1",
		"ftm:1", "fta:1", "fgm:1", "fga:1", "game_result:1", 
		"game_type:1", "created_time:1", "active_status:1"
	};
	
	public static final String[] wOrl= { "Win", "Loss" };
	
	final HashMap<String,String> extrafields = new HashMap<String,String>();
	
	public MStatsGames() {
		super(FIELDS_ARRAY, TABLE);
		FIELD_VALUES.put("fg2ms", "0");
		FIELD_VALUES.put("fg3ms", "0");
		FIELD_VALUES.put("ftms", "0");

	}
	
	public Boolean is_Saveable(){
		int ck = 0;
		
		int min = Integer.parseInt(FIELD_VALUES.get("minutes"));
		if ( min < 1) ck++;
		
		HashMap<String,Integer> ck_arr = new HashMap<String,Integer>();
		ck_arr.put("points", 130);
		ck_arr.put("rebounds", 50);
		ck_arr.put("assists", 35);
		ck_arr.put("steals", 20);
		ck_arr.put("blocks", 20);
		ck_arr.put("turnovers", 20);
		ck_arr.put("fouls", 6);
		for (HashMap.Entry <String, Integer> entry : ck_arr.entrySet()) {
		    int i = Integer.parseInt(FIELD_VALUES.get(entry.getKey()));
		    if ( i > entry.getValue() ) ck++;
		}
		
		if ( ck < 1) return true;
		return false;
	}
	
	public Boolean saveGame(Context ctx){
		FIELD_VALUES.put("created_time", String.valueOf(StatsHelper.getNowTime()));
		FIELD_VALUES.put("active_status", "1");
		FIELD_VALUES.put("user_id", "1");
		FIELD_VALUES.put("game_type", "0");
		
		if (super.createRow(ctx)){
			MStatsCareer career = new MStatsCareer();
			career.saveCareer(ctx);
		}
		return false;
	}

}
