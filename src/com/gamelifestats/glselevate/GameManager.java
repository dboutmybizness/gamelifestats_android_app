package com.gamelifestats.glselevate;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gamelifestats.glselevate.models.MGames;

public class GameManager extends Activity {

	ListView lv;
	ArrayList<String> data;
	MGames games = new MGames();
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
			gameIDs.add(Integer.parseInt(splitter[0]));
			itemStrings.add(splitter[1]);
			itemBoolean.add((splitter[2].equals("0")) ? false  : true);

		}
	}
	
	private boolean pullData(){
		
		data = games.gameManageList(this);
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void saveArchive(View v){
		commitSave();
	}
	
	public void commitSave(){
		games.updateArchive(this, gameIDs, itemBoolean);
		Toast.makeText(this, "Archive saved", Toast.LENGTH_SHORT).show();
		finish();
	}

}
