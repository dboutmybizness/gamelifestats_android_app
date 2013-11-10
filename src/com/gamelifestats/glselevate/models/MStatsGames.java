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
	
	public Boolean saveGame(Context ctx){
		FIELD_VALUES.put("created_time", String.valueOf(StatsHelper.getNowTime()));
		FIELD_VALUES.put("active_status", "1");
		FIELD_VALUES.put("user_id", "1");
		//FIELD_VALUES.put("minutes", "40");
		FIELD_VALUES.put("game_type", "0");
		//FIELD_VALUES.put("game_result", "w");
		
		if (super.createRow(ctx)){
			MStatsCareer career = new MStatsCareer();
			career.saveCareer(ctx);
		}
		return false;
	}
	/*
	public MStatsGames[] getAllGames(Context ctx){
		
	}
	
	/*
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
	
	/*
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
	*/
	
	
	

}
