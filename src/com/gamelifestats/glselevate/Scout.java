package com.gamelifestats.glselevate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Scout extends Activity {
	TextView name,nickname,position;
	MScout db;
	String [] positions;
	Button edit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scout);
		// Show the Up button in the action bar.
		setupActionBar();
		
		db = new MScout(this);
		setUpViews();
		
		positions = getResources().getStringArray(R.array.positions_array);

	}
	
	@Override
	protected void onResume(){
		super.onResume();
		db.getScout();
		loadViews();
	}

	private void setUpViews(){
		edit = (Button) findViewById(R.id.edit_scout_button);
		edit.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), ScoutEdit.class));
				
			}
		});
		
		name = (TextView) findViewById(R.id.v_name);
		nickname = (TextView) findViewById(R.id.v_nickname);
		position = (TextView) findViewById(R.id.v_position);
		
	}
	
	private void loadViews(){
		name.setText(db.name);
		nickname.setText(db.nickname);
		position.setText(positions[db.position]);
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if ( item.getTitle().equals(ABSelect.ACT_YOUR_SCOUT)){
			startActivity(new Intent(this, ScoutEdit.class));
			finish();
			return true;
		}
		
		ABSelect bar_select = new ABSelect(this, ABSelect.ACT_YOUR_SCOUT);
    	Object cls = bar_select.trySelected(item);
    	if ( cls != null ){
    		startActivity(new Intent(this, (Class<?>) cls));
    		return true;
    	}
		
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
