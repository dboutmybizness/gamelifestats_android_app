package com.gamelifestats.glselevate;

import java.sql.SQLException;
import java.util.HashMap;

import android.content.Context;

public class Model_Games extends DBAdapter {
	static final String TABLE = "games";

	static final String ID = "_id";
	static final String MINUTES = "minutes";
	static final String POINTS = "points";
	static final String REBOUNDS = "rebounds";
	static final String ASSISTS = "assists";
	static final String STEALS = "steals";
	static final String BLOCKS = "blocks";
	static final String FOULS = "fouls";

	static final String CREATE_DB = 
		"create table "+TABLE+" (" +
		ID+" integer primary key autoincrement, " +
		MINUTES+" integer, " +
		POINTS+" integer," +
		REBOUNDS+" integer," +
		ASSISTS+" integer," +
		STEALS+" integer," +
		BLOCKS+" integer," +
		FOULS+" integer" +
		")";

	int minutes = 0;
	int points = 0;
	int rebounds = 0;
	int reb_def = 0;
	int reb_off = 0;
	int assists = 0;
	int steals = 0;
	int blocks = 0;
	int turnovers = 0;
	int fouls = 0;
	
	int fg2m = 0;
	int fg2ms = 0;
	int fg2a = 0;
	float fg2p = 0;
	int fg3m = 0;
	int fg3ms = 0;
	int fg3a = 0;
	float fg3p = 0;
	int ftm = 0;
	int ftms = 0;
	int fta = 0;
	float ftp = 0;
	int fgm = 0;
	int fga = 0;
	float fgp = 0;
	
	String s_minutes;
	String s_points;
	String s_rebounds;
	String s_reb_off;
	String s_assists;
	String s_steals;
	String s_blocks;
	String s_turnovers;
	String s_fouls;
	
	String s_fg2m;
	String s_fg2ms;
	String s_fg2a;
	String s_fg2p;
	String s_fg3m;
	String s_fg3ms;
	String s_fg3a;
	String s_fg3p;
	String s_ftm;
	String s_ftms;
	String s_fta;
	String s_ftp;
	String s_fgm;
	String s_fga;
	String s_fgp;
	
	Model_Games(Context context) {
		super(context);
		super.setTable(TABLE);
	}
	


	public void insertStats() throws SQLException{
		HashMap<String, String> m = new HashMap<String,String>();
		m.put(MINUTES, s_minutes);
		create(m);
	}
	

	public void renderStats(){
		fg2a = fg2m + fg2ms;
		fg3a = fg3m + fg3ms;
		fta = ftm + ftms;
		
		fga = fg2a + fg3a;
		fgm = fg2m + fg3m;
		
		//percentages
		fg2p = StatsHelper.getPercent(fg2m, fg2a);
		fg3p = StatsHelper.getPercent(fg3m, fg3a);
		ftp = StatsHelper.getPercent(ftm, fta);
		fgp = StatsHelper.getPercent(fgm, fga);
		
		rebounds = reb_off + reb_def;
		points = getPointsFromFGS(fg2m, fg3m, ftm);
		
		buildStats();
	}
	private String mString(int i){
		return StatsHelper.int2String(i);
	}
	
	private void buildStats(){
		s_minutes = 	mString(minutes);
		s_points = 		mString(points);
		s_rebounds = 	mString(rebounds);
		s_reb_off = 	mString(reb_off);
		s_assists = 	mString(assists);
		s_steals =		mString(steals);
		s_blocks =		mString(blocks);
		s_turnovers =	mString(turnovers);
		s_fouls =		mString(fouls);
		
		s_fg2m =		mString(fg2m);
		s_fg2ms =		mString(fg2ms);
		s_fg2a =		mString(fg2a);
		//s_fg2p =		mString(fg2p);
		s_fg3m =		mString(fg2m);
		s_fg3ms =		mString(fg3ms);
		s_fg3a =		mString(fg3a);
		//s_fg3p;
		s_ftm =			mString(ftm);
		s_ftms =		mString(ftms);
		s_fta =			mString(fta);
		//s_ftp;
		s_fgm =			mString(fgm);
		s_fga =			mString(fga);
		//s_fgp;
		
	}
	
	private int getPointsFromFGS(int fg2m, int fg3m, int ftm){
		return ftm + (fg2m * 2) + (fg3m * 3);
	}

}
