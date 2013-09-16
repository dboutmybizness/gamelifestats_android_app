package com.gamelifestats.glselevate;

import android.content.Context;
import android.view.MenuItem;

public class ABSelect {

	Context ctx;
	private String current_selection;
	private Object go_to = null;
	private String current_activity;
	
	public static final String ACT_YOUR_SCOUT = "Your Scout";
	public static final String ACT_PLAY_BALL = "Play Ball";
	public static final String ACT_STATBOOK = "Statbook";
	
	ABSelect(Context ctx, String current){
		this.ctx = ctx;
		this.current_activity = current;
	}
	
	public Object trySelected(MenuItem item){
		current_selection = (String) item.getTitle();
		if ( current_selection.equals(current_activity) ) return null;
  
    	if ( current_selection.equals(ABSelect.ACT_YOUR_SCOUT)){
    		go_to = Scout.class;
    	} else if ( current_selection.equals(ABSelect.ACT_PLAY_BALL)){
    		go_to = PlayBall_GameStatsEdit.class;
    	} else if ( current_selection.equals(ABSelect.ACT_STATBOOK)){
    		go_to = Statbook.class;
    	}
    	
    	return go_to;
	}
	
	
	
	
	
}
