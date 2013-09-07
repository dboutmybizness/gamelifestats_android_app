package com.gamelifestats.glselevate;

import java.sql.SQLException;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;

public class Model_Career extends DBAdapter {
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
        	ID,TGAMES,TMINUTES,TPOINTS,TREBOUNDS,TREBS_OFF,TREBS_DEF,TASSISTS,TSTEALS,TBLOCKS,TTURNOVERS,TFOULS
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
	
	
	public Model_Career(Context ctx) {
		super(ctx);
		super.setTable(TABLE);
		super.setGetFields(getFields);
	}
	
	public void getCareerFromDB(int uid){

	}	
	//public void renderCareer

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

		m.put(USERID, "1");
		m.put(TGAMES, String.valueOf(tot_games));
		super.create(m);
	}
}
