package com.gamelifestats.glselevate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ScoutEdit extends Activity {

	MScout scoutObj;
	TextView name,nickname;
	Spinner position;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_scout_edit);
		// Show the Up button in the action bar.
		setupActionBar();
		
		scoutObj = new MScout(this);
		
		position = (Spinner) findViewById(R.id.spinner_positions);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.positions_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		position.setAdapter(adapter);
		
		this.setupViews();
		scoutObj.getScout();
		this.loadScoutView();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
	}

	public void setupViews(){
		name = (TextView) findViewById(R.id.e_name);
		nickname = (TextView) findViewById(R.id.e_nickname);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void saveScout(View v){
		loadScoutObj();

		if ( scoutObj.trySave() ){
			Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(this, Scout.class));
			finish();
		} else {
			Toast.makeText(this, "There was a problem saving your scout, check that all fields are complete.", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	private void loadScoutObj(){
		scoutObj.name = name.getText().toString();
		scoutObj.nickname = nickname.getText().toString();
		scoutObj.position = position.getSelectedItemPosition();
	}
	
	private void loadScoutView(){
		if ( scoutObj.has_profile > 0){
			name.setText(scoutObj.name);
			nickname.setText(scoutObj.nickname);
			position.setSelection(scoutObj.position);
		}
	}

}
