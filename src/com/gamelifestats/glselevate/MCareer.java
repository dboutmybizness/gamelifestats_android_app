package com.gamelifestats.glselevate;

import java.sql.SQLException;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;

public class MCareer extends DBAdapter {
	static final String TABLE = "career";

	static final String ID =	"_id";
	static final String USERID =	"user_id";
	static final String TGAMES =	"tgames";
	static final String TMINUTES =	"tminutes";
	static final String TPOINTS = 	"tpoints";
	static final String TREBOUNDS =	"trebounds";
	static final String TREBS_OFF =	"trebs_off";
	static final String TREBS_DEF =	"trebs_def";
	static final String TASSISTS =	"tassists";
	static final String TSTEALS =	"tsteals";
	static final String TBLOCKS =	"tblocks";
	static final String TTURNOVERS=	"tturnovers";
	static final String TFOULS =	"tfouls";

	static final String CREATE_TABLE =
		"create table "+TABLE+" (" +
		ID+" integer primary key autoincrement, " +
		USERID+" integer,"+
		TGAMES+" integer,"+
		TMINUTES+" integer,"+
		TPOINTS+" integer," +
		TREBOUNDS+" integer," +
		TREBS_OFF+" integer,"+
		TREBS_DEF+" integer,"+
		TASSISTS+" integer," +
		TSTEALS+" integer," +
		TBLOCKS+" integer," +
		TTURNOVERS+" integer,"+
		TFOULS+" integer" +
		")";	

	static final String[] getFields = {
        	ID,USERID,TGAMES,TMINUTES,TPOINTS,
        	TREBOUNDS,TREBS_OFF,TREBS_DEF,TASSISTS,TSTEALS,
        	TBLOCKS,TTURNOVERS,TFOULS
	};
	
	int tot_games = 0;
	int tot_minutes = 0;
	int tot_points = 0;
	int tot_rebounds = 0;
	int tot_reb_off = 0;
	int tot_reb_def = 0;
	int tot_assists = 0;
	int tot_steals = 0;
	int tot_blocks = 0;
	int tot_turnovers = 0;
	int tot_fouls = 0;
	

	float avg_minutes = 0;
	float avg_points = 0;
	float avg_rebounds = 0;
	float avg_reb_off = 0;
	float avg_reb_def = 0;
	float avg_assists = 0;
	float avg_steals = 0;
	float avg_blocks = 0;
	float avg_turnovers = 0;
	float avg_fouls = 0;
	
	
	public MCareer(Context ctx) {
		super(ctx);
		super.setTable(TABLE);
		super.setGetFields(getFields);
	}
	
	public void getCareerFromDB(int uid){

	}	

	public void buildCareerObject(Cursor c){
		if (c != null){
			tot_games = c.getCount();
			c.moveToFirst();
			do {
				tot_minutes += c.getInt(2);
				tot_points += c.getInt(3);
				tot_rebounds += c.getInt(4);
				tot_reb_off += c.getInt(5);
				tot_reb_def += c.getInt(6);
				tot_assists += c.getInt(7);
				tot_steals += c.getInt(8);
				tot_blocks += c.getInt(9);
				tot_turnovers += c.getInt(10);
				tot_fouls += c.getInt(11);
			} while (c.moveToNext());
				
		}
	}
	
	public void saveCareerObject() throws SQLException{
		HashMap<String, String> m = new HashMap<String,String>();
		m.put(TGAMES, StatsHelper.int2Str(tot_games));
		m.put(TMINUTES, StatsHelper.int2Str(tot_minutes));
		m.put(TPOINTS, StatsHelper.int2Str(tot_points));
		m.put(TREBOUNDS, StatsHelper.int2Str(tot_rebounds));
		m.put(TREBS_OFF, StatsHelper.int2Str(tot_reb_off));
		m.put(TREBS_DEF, StatsHelper.int2Str(tot_reb_def));
		m.put(TASSISTS, StatsHelper.int2Str(tot_assists));
		m.put(TSTEALS, StatsHelper.int2Str(tot_steals));
		m.put(TBLOCKS, StatsHelper.int2Str(tot_blocks));
		m.put(TTURNOVERS, StatsHelper.int2Str(tot_turnovers));
		m.put(TFOULS, StatsHelper.int2Str(tot_fouls));
		
		super.open();
		super.update(m, USERID+"=1");
		super.close();
	}
	
	private float gAvg(int stat){
		return StatsHelper.divPerc(stat, tot_games);
	}
	
	public Boolean loadSavedCareer(){
		Cursor c = null;
		Boolean ck_q = false;
		try {
			super.open();
			c = super.getAllRowsWhere("user_id = 1");
			if ( c != null){
				ck_q = true;
				c.moveToFirst();
				tot_games = c.getInt(2);
				tot_minutes = c.getInt(3);
				tot_points = c.getInt(4);
				tot_rebounds = c.getInt(5);
				tot_reb_off = c.getInt(6);
				tot_reb_def = c.getInt(7);
				tot_assists = c.getInt(8);
				tot_steals = c.getInt(9);
				tot_blocks = c.getInt(10);
				tot_turnovers = c.getInt(11);
				tot_fouls = c.getInt(12);
				
				avg_minutes = gAvg(tot_minutes);
				avg_points = gAvg(tot_points);
				avg_rebounds = gAvg(tot_rebounds);
				avg_reb_off = gAvg(tot_reb_off);
				avg_reb_def = gAvg(tot_reb_def);
				avg_assists = gAvg(tot_assists);
				avg_steals = gAvg(tot_steals);
				avg_blocks = gAvg(tot_blocks);
				avg_turnovers = gAvg(tot_turnovers);
				avg_fouls = gAvg(tot_fouls);
				
			}
			super.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ck_q;
		
	}
}
