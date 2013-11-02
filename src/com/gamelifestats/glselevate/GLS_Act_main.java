package com.gamelifestats.glselevate;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GLS_Act_main extends Activity {
	Button stats,upload_game;
	MCareer career;
	
	TextView points,rebounds,assists,games,steals,blocks,turnovers,fouls,minutes;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(R.string.home_court);
  
        
        points = (TextView) findViewById(R.id.dis_points);
        games = (TextView) findViewById(R.id.dis_games);
        rebounds = (TextView) findViewById(R.id.dis_rebounds);
        assists = (TextView) findViewById(R.id.dis_assists);
        steals = (TextView) findViewById(R.id.dis_steals);
        blocks = (TextView) findViewById(R.id.dis_blocks);
        turnovers = (TextView) findViewById(R.id.dis_turnovers);
        fouls = (TextView) findViewById(R.id.dis_fouls);
        minutes = (TextView) findViewById(R.id.dis_minutes);
        
        career = new MCareer(this);
        
        stats = (Button) findViewById(R.id.button_stats);
        stats.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), Stats.class));
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
    protected void onResume(){
    	super.onResume();
    	Boolean has_career;
		has_career = career.loadSavedCareer();
		if ( has_career ){
			renderCareer();
		}
    }
    
    private void renderCareer(){
    	games.setText(StatsHelper.int2Str(career.tot_games));
    	points.setText(StatsHelper.float2Str(career.avg_points));
    	rebounds.setText(StatsHelper.float2Str(career.avg_rebounds));
    	assists.setText(StatsHelper.float2Str(career.avg_assists));
    	steals.setText(StatsHelper.float2Str(career.avg_steals));
    	blocks.setText(StatsHelper.float2Str(career.avg_blocks));
    	turnovers.setText(StatsHelper.float2Str(career.avg_turnovers));
    	fouls.setText(StatsHelper.float2Str(career.avg_fouls));
    	minutes.setText(StatsHelper.float2Str(career.avg_minutes));
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
