package com.gamelifestats.glselevate;

import java.sql.SQLException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class GameManager extends Activity {

	ListView lv;
	ArrayList<String> data;
	MGames games;
	ArrayList<Integer> gameIDs;
	ArrayList<String> itemStrings;
	ArrayList<Boolean> itemBoolean;
	Resources res;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_manager);
		// Show the Up button in the action bar.
		setupActionBar();
		
		
		if (pullData()){
			popListElements(data);
			res = getResources();
			
			lv = (ListView) findViewById(R.id.list_gm);
			GameManagerArrayAdapter gm = new GameManagerArrayAdapter(this, itemStrings, itemBoolean);
			lv.setAdapter(gm);
			lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					View v = parent.getChildAt(position);
					updateListItem(position, v);
				}
			}); 
		}
	}
	
	private void popListElements(ArrayList<String> data){
		gameIDs = new ArrayList<Integer>();
		itemStrings = new ArrayList<String>();
		itemBoolean = new ArrayList<Boolean>();
		
		for(int i = 0; i < data.size(); i++){
			String str = (String) data.get(i);
			String[] splitter = str.split("_");
			gameIDs.add(Integer.getInteger(splitter[0]));
			itemStrings.add(splitter[1]);
			itemBoolean.add((splitter[2].equals("0")) ? false  : true);

		}
	}
	
	private boolean pullData(){
		games = new MGames(this);
		try {
			data = games.retArchive();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (data.size() > 0) return true;
		return false;
	}
	
	private void updateListItem(int position, View view){
		TextView tv = (TextView) view.findViewById(R.id.gm_item);
		//View layout = (View) view.findViewById(R.id.manage_list);
		//layout.setBackgroundColor(res.getColor(R.color.gls_blue));
		if ( itemBoolean.get(position) ){
			tv.setTextColor(res.getColor(R.color.Gray));
			itemBoolean.set(position, false);
		} else {
			tv.setTextColor(res.getColor(R.color.gls_blue));
			itemBoolean.set(position, true);
		}
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
		getMenuInflater().inflate(R.menu.edit_game, menu);
		return true;
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
	
	public void saveArchive(View v){
		commitSave();
	}
	
	public void commitSave(){
		//games.updateArchive(gameIDs, itemBoolean);
	}

}
