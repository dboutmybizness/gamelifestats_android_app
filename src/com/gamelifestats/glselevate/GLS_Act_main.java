package com.gamelifestats.glselevate;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gamelifestats.glselevate.models.MStatsCareer;

public class GLS_Act_main extends Activity {
	Button stats,upload_game;
	//MCareer career;
	MStatsCareer career = new MStatsCareer();
	SetUpPageView SPV;
	//TextView points,rebounds,assists,games,steals,blocks,turnovers,fouls,minutes;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(R.string.home_court);
  
        SPV = new SetUpPageView();
        
        stats = (Button) findViewById(R.id.button_stats);
        stats.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), Statbook.class));
			}
        });
        upload_game = (Button) findViewById(R.id.button_upload_game);
        upload_game.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), GameEdit.class));
			}
        });
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	if ( career.getCareer(this) ){
        	
        	SPV.setOnCreateFieldsHash(career.FIELD_VALUES);
        
	        SparseArray<String> pageview = new SparseArray<String>();
	        pageview.put(R.id.dis_games, "tgames");
	        pageview.put(R.id.dis_points, "apoints");
	        pageview.put(R.id.dis_rebounds, "arebounds");
	        pageview.put(R.id.dis_assists, "aassists");
	        pageview.put(R.id.dis_blocks, "ablocks");
	        pageview.put(R.id.dis_turnovers, "aturnovers");
	        pageview.put(R.id.dis_fouls, "afouls");
	        pageview.put(R.id.dis_minutes, "aminutes");
	        
	        for(int i = 0; i < pageview.size(); i++) {
	        	int key = pageview.keyAt(i);
	        	String val = pageview.get(key);
			    SPV.addView((TextView)findViewById(key), null, val);
			}
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	ABSelect bar_select = new ABSelect(this, null);
    	Object cls = bar_select.trySelected(item);
    	if ( cls != null ){
    		startActivity(new Intent(this, (Class<?>) cls));
    		return true;
    	}
    	return super.onOptionsItemSelected(item);
    }
    
}
