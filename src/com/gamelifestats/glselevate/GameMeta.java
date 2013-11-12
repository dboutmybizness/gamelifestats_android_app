package com.gamelifestats.glselevate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gamelifestats.glselevate.helper.CallBackHelper;
import com.gamelifestats.glselevate.helper.SetUpPageView;

public class GameMeta extends Activity {
	
	SeekBar minutes;
	RadioGroup wl;
	
	SetUpPageView SPV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_meta);
		
		
		SPV = new SetUpPageView();
		minutes = (SeekBar) findViewById(R.id.seek_minutes);
		wl = (RadioGroup)findViewById(R.id.winlosegroup);
		SPV.addView(minutes, 60, new CallBackHelper((TextView)findViewById(R.id.print_min)), null);
		
		minutes.setProgress(Integer.parseInt(getIntent().getStringExtra("minutes")));
		
		
		RadioButton[] rb = new RadioButton[]{
				(RadioButton) findViewById(R.id.win),
				(RadioButton) findViewById(R.id.loss)
		};
		if ( Integer.parseInt(getIntent().getStringExtra("game_result")) > 0){
			rb[1].setChecked(true);
		}
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			//NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void doneMeta(View v){
		
		Intent i = new Intent();
		i.putExtra("minutes", String.valueOf(minutes.getProgress()));
		int ck_id = wl.getCheckedRadioButtonId();
		int game_result = (ck_id == R.id.win)? 0:1;
		
		i.putExtra("game_result", game_result);
		
		setResult(RESULT_OK, i);
		finish();
		
	}

}
