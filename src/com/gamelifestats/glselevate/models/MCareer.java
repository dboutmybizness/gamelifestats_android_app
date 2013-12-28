package com.gamelifestats.glselevate.models;


import java.sql.SQLException;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;

import com.gamelifestats.glselevate.helper.Statline;
import com.gamelifestats.glselevate.helper.StatsHelper;


public class MCareer extends ModelBase {

	static final String TABLE = "career";
	static final String[] FIELDS_ARRAY = {
		"_id:0",
		"user_id:1", "tgames:1", "tminutes:1", "tpoints:1", "trebounds:1",
		"trebs_off:1", "trebs_def:1", "tassists:1", "tsteals:1", "tblocks:1",
		"tturnovers:1", "tfouls:1", "tfg2m:1", "tfg2a:1", "tfg3m:1",
		"tfg3a:1", "tfgm:1", "tfga:1", "tftm:1", "tfta:1"
	};

	final HashMap<String,String> extrafields = new HashMap<String,String>();
	
	public MCareer() {
		super(FIELDS_ARRAY, TABLE);
		extrafields.put("apoints", "tpoints");
		extrafields.put("arebounds", "trebounds");
		extrafields.put("aassists", "tassists");
		extrafields.put("asteals", "tsteals");
		extrafields.put("ablocks", "tblocks");
		extrafields.put("aturnovers", "tturnovers");
		extrafields.put("afouls", "tfouls");
		extrafields.put("aminutes", "tminutes");
		extrafields.put("arebs_off", "trebs_off");
		extrafields.put("arebs_def", "trebs_def");
		
		extrafields.put("afgm", "tfgm");
        extrafields.put("afg2m", "tfg2m");
        extrafields.put("afg3m", "tfg3m");
        extrafields.put("aftm", "tftm");
        extrafields.put("afga", "tfga");
        extrafields.put("afg2a", "tfg2a");
        extrafields.put("afg3a", "tfg3a");
        extrafields.put("afta", "tfta");
	}
	
	@Override
	public HashMap<String,String> insertInitial(){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put(FIELD_NAMES.get(1), "1");
		map.put(FIELD_NAMES.get(2), "0");
		return map;
	}
	
	private void renderGamesByQuery(MGames games, Cursor all_games){
		int tgames = all_games.getCount();
		if ( tgames > 0){
			
			Statline st_obj = new Statline();
			st_obj.loadstats(games.FIELD_VALUES, null);
			all_games.moveToFirst();
			do {
				for(int i = 2; i < 20; i++){
					int curr = st_obj.stat_as_ints.get(games.FIELDS_LIST_ARRAY[i]);
					
					st_obj.stat_as_ints.put(games.FIELDS_LIST_ARRAY[i], curr + all_games.getInt(i));
				}
				
			} while(all_games.moveToNext());
			
			st_obj.render_strings();
			
			FIELD_VALUES.clear();
			FIELD_VALUES.put("tgames", String.valueOf(tgames));
			
			HashMap<String,String> fvals = new HashMap<String,String>();
			fvals.put("tpoints", "points");
			fvals.put("trebounds", "rebounds");
			fvals.put("trebs_off", "reb_off");
			fvals.put("trebs_def", "reb_def");
			fvals.put("tassists", "assists");
			fvals.put("tsteals", "steals");
			fvals.put("tblocks", "blocks");
			fvals.put("tturnovers", "turnovers");
			fvals.put("tfouls", "fouls");
			fvals.put("tminutes", "minutes");
			
			fvals.put("tfg2m", "fg2m");
			fvals.put("tfg2a", "fg2a");
			fvals.put("tfg3m", "fg3m");
			fvals.put("tfg3a", "fg3a");
			fvals.put("tftm", "ftm");
			fvals.put("tfta", "fta");
			fvals.put("tfgm", "fgm");
			fvals.put("tfga", "fga");
			
			
			for (HashMap.Entry <String, String> entry : fvals.entrySet()) {
			    FIELD_VALUES.put(entry.getKey(), st_obj.stat_as_strings.get(entry.getValue()));
			}
		}
	}
	
	public Boolean saveCareer(Context ctx){
		MGames games = new MGames();
		DBAdapter db = new DBAdapter(ctx);
		try{
			db.open();
			Cursor all_games = db.getAllRows(MGames.TABLE, games.FIELDS_LIST_ARRAY, "user_id=1");
			renderGamesByQuery(games, all_games);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		
		return super.update(ctx, "user_id=1");
	}
	
	public Boolean getCareer(Context ctx){
		boolean result = super.readRow(ctx, "user_id=1");
		
		if (result){
			String tgames = FIELD_VALUES.get("tgames");
			if ( tgames == null ) return false;
			if ( tgames.equals("0") ) return false;
			
			for (HashMap.Entry <String, String> entry : extrafields.entrySet()) {
				if ( FIELD_VALUES.get(entry.getValue()) == null) continue;
			    FIELD_VALUES.put(entry.getKey(), gAvg(Integer.valueOf(FIELD_VALUES.get(entry.getValue()))));
			}
		}
		
		return result;
	}
	
	public Boolean getCareer(Context ctx, int active_status){
		//query games where active_status =as
		MGames games = new MGames();
		DBAdapter db = new DBAdapter(ctx);
		try{
			db.open();
			Cursor all_games = db.getAllRows(MGames.TABLE, games.FIELDS_LIST_ARRAY, "user_id=1 AND active_status=" + active_status);
			renderGamesByQuery(games, all_games);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		
		String tgames = FIELD_VALUES.get("tgames");
		if ( tgames == null ) return false;
		if ( tgames.equals("0") ) return false;
		
		for (HashMap.Entry <String, String> entry : extrafields.entrySet()) {
			if ( FIELD_VALUES.get(entry.getValue()) == null) continue;
		    FIELD_VALUES.put(entry.getKey(), gAvg(Integer.valueOf(FIELD_VALUES.get(entry.getValue()))));
		}
		
		return true;
	}
	
	private String gAvg(int stat){
		float val = StatsHelper.divPerc(stat, FIELD_VALUES.get("tgames"));
		return String.valueOf(val);
	}
	
	public void getPercentages(){
		Statline st_obj = new Statline();
		st_obj.setStattype(1);
		String[] flds = new String[]{"tfgm","tfga","tfg3m","tfg3a","tftm","tfta"};
		HashMap<String,String> iniB = new HashMap<String,String>();
		
		for (int i =0; i<flds.length; i++){
			iniB.put(flds[i], FIELD_VALUES.get(flds[i]));
		}
		
		st_obj.loadstats(iniB, null);
		iniB = st_obj.calReturn();
		
		String[] renF = new String[]{"fgp", "fg3p", "ftp"};
		
		for(int i = 0; i < renF.length; i++){
			FIELD_VALUES.put(renF[i], iniB.get(renF[i]));
		}
		
		
	}
	
	
	

}
