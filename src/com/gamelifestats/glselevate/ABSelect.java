package com.gamelifestats.glselevate;

import android.content.Context;
import android.view.MenuItem;

public class ABSelect {

	Context ctx;
	private String current_selection;
	private Object go_to = null;
	private String current_activity;
	
	public static final String ACT_BBall_Profile = "BBall Profile";
	public static final String ACT_PLAY_BALL = "Play Ball";
	public static final String ACT_STATS = "Stats";
	
	ABSelect(Context ctx, String current){
		this.ctx = ctx;
		this.current_activity = current;
	}
	
	public Object trySelected(MenuItem item){
		current_selection = (String) item.getTitle();
		if ( current_selection.equals(current_activity) ) return null;
  
    	if ( current_selection.equals(ABSelect.ACT_BBall_Profile)){
    		go_to = BballProfile.class;
    	} else if ( current_selection.equals(ABSelect.ACT_PLAY_BALL)){
    		go_to = GameEdit.class;
    	} else if ( current_selection.equals(ABSelect.ACT_STATS)){
    		go_to = Statbook.class;
    	}
    	
    	return go_to;
	}
	
	
	
	
	
}
