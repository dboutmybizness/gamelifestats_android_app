package com.gamelifestats.glselevate;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

public class GLS_Act_main extends Activity {
	TextView statline,upload_stats;
	MCareer career;
	
	TextView points,rebounds,assists,games;
	View elevate_link;
	TextView gls_link;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(R.string.home_court);
        
        elevate_link = (View) findViewById(R.id.elevate_child);
        elevate_link.setOnLongClickListener(new OnLongClickListener(){
			@Override
			public boolean onLongClick(View v) {
				goToGLS();
				return false;
			}
        });
        
        gls_link = (TextView) findViewById(R.id.gls_com);
        gls_link.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				goToGLS();
			}
        });
        
        points = (TextView) findViewById(R.id.dis_points);
        games = (TextView) findViewById(R.id.dis_games);
        rebounds = (TextView) findViewById(R.id.dis_rebounds);
        assists = (TextView) findViewById(R.id.dis_assists);
        
        career = new MCareer(this);
        
        statline = (TextView) findViewById(R.id.statline);
        statline.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), Statbook.class));
			}
        });
        upload_stats = (TextView) findViewById(R.id.upload_gamestats);
        upload_stats.setOnClickListener(new OnClickListener(){

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
    
    private void goToGLS(){
    	Resources res = getResources();
    	String url = res.getString(R.string.gls_site);
    	Intent i = new Intent(Intent.ACTION_VIEW);
    	i.setData(Uri.parse(url));
    	startActivity(i);
    }
    
}
