package com.gamelifestats.glselevate.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;

import com.gamelifestats.glselevate.helper.StatsHelper;




public class MGames extends ModelBase {
	
	static final String TABLE = "games";
	static final String[] FIELDS_ARRAY = {
		"_id:0",
		"user_id:1", "minutes:1", "points:1", "rebounds:1", "reb_off:1",
		"reb_def:1", "assists:1", "steals:1", "blocks:1", "turnovers:1",
		"fouls:1", "fg2m:1", "fg2a:1", "fg3m:1", "fg3a:1",
		"ftm:1", "fta:1", "fgm:1", "fga:1", "game_result:1", 
		"game_type:1", "created_time:1", "active_status:1"
	};
	
	public ArrayList<String> emessage = new ArrayList<String>();
	
	public static final String[] wOrl= { "Win", "Loss" };
	
	final HashMap<String,String> extrafields = new HashMap<String,String>();
	
	public MGames() {
		super(FIELDS_ARRAY, TABLE);
		FIELD_VALUES.put("fg2ms", "0");
		FIELD_VALUES.put("fg3ms", "0");
		FIELD_VALUES.put("ftms", "0");

	}
	
	public Boolean is_Saveable(HashMap<String,String> map){
		int ck = 0;
		emessage.clear();
		
		int min = Integer.parseInt(map.get("minutes"));
		if ( min < 1) {
			ck++;
			emessage.add("minutes are required");
		}
		
		HashMap<String,Integer> ck_arr = new HashMap<String,Integer>();
		ck_arr.put("points", 130);
		ck_arr.put("rebounds", 50);
		ck_arr.put("assists", 35);
		ck_arr.put("steals", 20);
		ck_arr.put("blocks", 20);
		ck_arr.put("turnovers", 20);
		ck_arr.put("fouls", 6);
		for (HashMap.Entry <String, Integer> entry : ck_arr.entrySet()) {
			String sval = map.get(entry.getKey());
		    int ival = Integer.parseInt(sval);
		    if ( ival > entry.getValue() ) {
		    	ck++;
		    	emessage.add(entry.getKey()+ " max value is "+ entry.getValue());
		    }
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
			MCareer career = new MCareer();
			career.saveCareer(ctx);
		}
		return false;
	}
	
	public ArrayList<String> gameManageList(Context ctx){
		DBAdapter db = new DBAdapter(ctx);
		Cursor c;
		ArrayList<String> alist = new ArrayList<String>();
		try{
			db.open();
			c = db.getAllRows(MGames.TABLE, this.FIELDS_LIST_ARRAY, "user_id=1");
			if( c.getCount() > 0){
				c.moveToFirst();
				do{
					String id = c.getString(0);
					String date = StatsHelper.dateFromTime(c.getString(22));
					String text = date +" - "+ c.getString(3) + "pts, " + c.getString(4) + "reb, " + c.getString(7) + "ast";
					String active = c.getString(23);
					alist.add(
							id +"_"+text+"_"+active
					);
				}while(c.moveToNext());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		
		return alist;
	}
	
	public void updateArchive(Context ctx, ArrayList<Integer> ids, ArrayList<Boolean> vals){
		DBAdapter db = new DBAdapter(ctx);
		try{
			db.open();
			db.setTable(MGames.TABLE);
		
			for (int i=0; i < ids.size(); i++ ){
				String val = (vals.get(i) == true) ? "1" : "0";
				//Toast.makeText(ctx, val, Toast.LENGTH_SHORT).show();
				HashMap<String,String> oneoff = new HashMap<String,String>();
				oneoff.put("active_status", val);
				db.update(oneoff, "_id="+ids.get(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
	}

}
