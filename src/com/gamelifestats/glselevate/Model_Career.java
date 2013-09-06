package com.gamelifestats.glselevate;

import android.content.Context;

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
	
	int tot_points;
	int tot_rebounds;
	int tot_reb_off;
	int tot_reb_def;
	int tot_assists;
	int tot_steals;
	int tot_blocks;
	int tot_turnovers;
	int tot_fouls;
	
	
	public Model_Career(Context ctx) {
		super(ctx);
		super.setTable(TABLE);
		super.setGetFields(getFields);
	}
	
	public void getCareerFromDB(int uid){

	}	
	//public void renderCareer
	
}
