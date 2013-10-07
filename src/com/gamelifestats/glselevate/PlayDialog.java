package com.gamelifestats.glselevate;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PlayDialog extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_dialog);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play_dialog, menu);
		return true;
	}

}
