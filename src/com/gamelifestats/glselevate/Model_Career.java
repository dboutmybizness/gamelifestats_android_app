package com.gamelifestats.glselevate;

import android.content.Context;

public class Model_Career extends DBAdapter {
	static final String TABLE = "career";
	
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
	}
	
	
}