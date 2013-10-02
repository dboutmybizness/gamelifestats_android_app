package com.gamelifestats.glselevate;

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
import android.widget.Toast;

public class GameManager extends Activity {

	ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_manager);
		// Show the Up button in the action bar.
		setupActionBar();
		
		String[] values = new String[]{"d","dee"};
		
		lv = (ListView) findViewById(R.id.list_gm);
		GameManagerArrayAdapter gm = new GameManagerArrayAdapter(this, values);
		lv.setAdapter(gm);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Resources res = getResources();
				//view.setBackgroundColor(res.getColor(R.color.AliceBlue));
				//TextView tv = (TextView) parent.getChildAt(1);
				//tv.setText("good times");
				View v = parent.getChildAt(position);
				TextView tv = (TextView) v.findViewById(R.id.gm_lab);
				tv.setText("happy");
				//v.setBackgroundColor(res.getColor(R.color.AliceBlue));
				Toast.makeText(getApplicationContext(), ""+ parent.getChildCount(), Toast.LENGTH_SHORT).show();
				
			}
		}); 
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
		getMenuInflater().inflate(R.menu.game_manager, menu);
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

}
