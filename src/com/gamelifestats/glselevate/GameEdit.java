package com.gamelifestats.glselevate;

import java.sql.SQLException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.gamelifestats.glselevate.models.MGames;

public class GameEdit extends Activity {

	TextView dfgm,dfga,d3m,d3a,dftm,dfta;
	TextView dpts,dreb,dreb_off,dasts,dstls,dblks,dtos;
	
	TextView lab_fg2made, lab_fg2missed, lab_fg3made, lab_fg3missed, lab_ftmade, lab_ftmissed, lab_oreb, lab_dreb;
	TextView lab_minutes, lab_assists, lab_steals, lab_blocks, lab_turnovers, lab_fouls;
	RadioGroup winloss;
	MGames db;

	Resources res;
	int active_color;
	int default_color;
	
	final int[] stat_routines = {
		0,1
	};
	Integer active_stat = null;
	TextView active_textview;
	TextView last_textview;
	
	Button press_fg2made,press_fg2missed,press_fg3made,press_fg3missed,press_ftmade, press_ftmissed, press_reboff,press_rebdef,
		press_assists,press_steals,press_blocks,press_turnovers;
	TextView stat_fg2made,stat_fg2missed,stat_fg3made,stat_fg3missed,stat_ftmade,stat_ftmissed,stat_reboff,stat_rebdef,
		stat_assists,stat_steals,stat_blocks,stat_turnovers;
	SeekBar gseek;
	Button plus1, minus1;
	
	FrameLayout swiper;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_edit);

		setupActionBar();
		
		db = new MGames(this);
		
		res = getResources();
		active_color = res.getColor(R.color.gls_blue);
		default_color = res.getColor(R.color.White);
		setUpViews();
	}
	
	public void updateActiveStat(int progress){
		if ( active_stat == null) return;
		
		TextView tv;
		switch(active_stat){
			case 0: tv = stat_fg2made;
				break;
			case 1: tv = stat_fg2missed;
				break;
			case 2: tv = stat_fg3made;
				break; 
			case 3: tv = stat_fg3missed;
				break;
			case 4: tv = stat_ftmade;
				break;
			case 5: tv = stat_ftmissed;
				break;
			case 6: tv = stat_reboff;
				break;
			case 7: tv = stat_rebdef;
				break;
			case 8: tv = stat_assists;
				break;
			case 9: tv = stat_steals;
				break;
			case 10: tv = stat_blocks;
				break;
			case 11: tv = stat_turnovers;
				break;
			default: tv = null;
				break;
		}
		
		if (tv != null){
			tv.setText(makeDoubleZero(progress));
			refactorStats();
		}
	}

	
	public String makeDoubleZero(int i){
		String num = String.valueOf(i);
		if ( num.length() == 1) num = "0" +num;
		return num;
	}
	
	public Button initStatButton(int button_view, final int routine, final TextView tv){
		Button butt = (Button) findViewById(button_view);
		butt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				setActiveStat(routine,tv);
			}
			
		});
		return butt;
	}
	
	public void setLastStat(TextView tv){
		if (active_stat == null) return;
		last_textview = active_textview;
		last_textview.setTextColor(default_color);
	}
	
	public void setActiveStat(int routine,TextView tv){
		
		setLastStat(tv);
		active_stat = routine;
		active_textview = tv;
		active_textview.setTextColor(active_color);
		int stat_value = Integer.parseInt(tv.getText().toString());
		gseek.setProgress(stat_value);
		
	}
	
	public TextView initTV(int Id){
		TextView tv = (TextView) findViewById(Id);
		return tv;
	}
	
	private String retRadioValue(){
		int idOfRadio = winloss.getCheckedRadioButtonId();
		RadioButton rb = (RadioButton) findViewById(idOfRadio);
		return rb.getText().toString();
	}
	
	private void refactorStats(){
		db.game_result = (retRadioValue().equals("Win")) ? 1 : 0;
		
		//db.minutes = minutes.getProgress();
		
		db.fg2m = Integer.parseInt(stat_fg2made.getText().toString());
		db.fg2ms = Integer.parseInt(stat_fg2missed.getText().toString());
		
		
		db.fg3m = Integer.parseInt(stat_fg3made.getText().toString());
		db.fg3ms = Integer.parseInt(stat_fg3missed.getText().toString());
		
		db.ftm = Integer.parseInt(stat_ftmade.getText().toString());
		db.ftms = Integer.parseInt(stat_ftmissed.getText().toString());
		
		db.reb_def = Integer.parseInt(stat_rebdef.getText().toString());
		db.reb_off = Integer.parseInt(stat_reboff.getText().toString());
		
		db.assists = Integer.parseInt(stat_assists.getText().toString());
		db.steals = Integer.parseInt(stat_steals.getText().toString());
		db.blocks = Integer.parseInt(stat_blocks.getText().toString());
		db.turnovers = Integer.parseInt(stat_turnovers.getText().toString());
		//db.fouls = fouls.getProgress();
		
		db.renderStats();
		renderPage();
	}
	
	public void renderPage(){
		//dmin.setText(db.s_minutes);
		dpts.setText(db.s_points);
		dreb.setText(db.s_rebounds);
		dreb_off.setText(db.s_reb_off);
		dasts.setText(db.s_assists);
		dstls.setText(db.s_steals);
		dblks.setText(db.s_blocks);
		dtos.setText(db.s_turnovers);

		dfgm.setText(db.s_fgm);
		dfga.setText(db.s_fga);
		d3m.setText(db.s_fg3m);
		d3a.setText(db.s_fg3a);
		dftm.setText(db.s_ftm);
		dfta.setText(db.s_fta);
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
		if (item.getTitle().equals("Done")){
			this.saveStats(null);
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
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	}
	
	public void resetStats(View v){
		if ( active_stat != null){
			gseek.setProgress(0);
			active_textview.setText("00");
			refactorStats();
		}
		
	}
	
	public void saveStats(View v){
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
			.setMessage("Finished?")
			//.setCancelable(false)
			.setPositiveButton("OK, save game", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					try {
						commitSave();
						Intent i = new Intent(getBaseContext(), Stats.class);
						i.putExtra("nav_action", 1);
						startActivity(i);
						finish();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			})
			.setNegativeButton("Back", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					
				}
			});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	private void commitSave() throws SQLException{
		db.insertStats();
	}
	
	private void plus1(){
		if ( active_stat == null) return;
		int current_progress = gseek.getProgress();
		int new_progress = current_progress + 1;
		if ( new_progress > gseek.getMax() ) gseek.setMax(new_progress);
		active_textview.setText(makeDoubleZero(new_progress));
		refactorStats();
		setActiveStat(active_stat,active_textview);
	}
	
	private void minus1(){
		if ( active_stat == null) return;
		int current_progress = gseek.getProgress();
		int new_progress = current_progress - 1;
		if ( new_progress < 0 ) return;
		active_textview.setText(makeDoubleZero(new_progress));
		refactorStats();
		setActiveStat(active_stat,active_textview);
	}
	
	public void setUpViews(){
		//winloss = (RadioGroup) findViewById(R.id.winlosegroup);
		//dmin = (TextView) findViewById(R.id.dis_minutes);
		
		dpts = initTV(R.id.dis_points);
		/*
		dfgm = initTV(R.id.dis_fgm);
		dfga = initTV(R.id.dis_fga);
		d3m = initTV(R.id.dis_3m);
		d3a = initTV(R.id.dis_3a);
		dftm = initTV(R.id.dis_ftm);
		dfta = initTV(R.id.dis_fta);*/
		
		dreb = initTV(R.id.dis_reb);
		//dreb_off = initTV(R.id.dis_reb_off);

		dasts = initTV(R.id.dis_assists);
		dstls = initTV(R.id.dis_steals);
		dblks = initTV(R.id.dis_blocks);
		dtos = initTV(R.id.dis_turnovers);

		
		stat_fg2made = (TextView) findViewById(R.id.stat_fg2made);
		press_fg2made = initStatButton(R.id.press_fg2made,0, stat_fg2made);
		
		stat_fg2missed = (TextView) findViewById(R.id.stat_fg2missed);
		press_fg2missed = initStatButton(R.id.press_fg2missed,1, stat_fg2missed);
		
		stat_fg3made = (TextView) findViewById(R.id.stat_fg3made);
		press_fg3made = initStatButton(R.id.press_fg3made,2, stat_fg3made);
		
		stat_fg3missed = (TextView) findViewById(R.id.stat_fg3missed);
		press_fg3missed = initStatButton(R.id.press_fg3missed,3, stat_fg3missed);
		
		stat_ftmade = (TextView) findViewById(R.id.stat_ftmade);
		press_ftmade = initStatButton(R.id.press_ftmade,4, stat_ftmade);
		
		stat_ftmissed = (TextView) findViewById(R.id.stat_ftmissed);
		press_ftmissed = initStatButton(R.id.press_ftmissed,5, stat_ftmissed);
		
		stat_reboff = (TextView) findViewById(R.id.stat_reboff);
		press_reboff = initStatButton(R.id.press_reboff,6, stat_reboff);
		
		stat_rebdef = (TextView) findViewById(R.id.stat_rebdef);
		press_rebdef = initStatButton(R.id.press_rebdef,7,stat_rebdef);
		
		stat_assists = (TextView) findViewById(R.id.stat_assists);
		press_assists = initStatButton(R.id.press_assists,8,stat_assists);
		
		stat_steals = (TextView) findViewById(R.id.stat_steals);
		press_steals = initStatButton(R.id.press_steals,9,stat_steals);
		
		stat_blocks = (TextView) findViewById(R.id.stat_blocks);
		press_blocks = initStatButton(R.id.press_blocks,10,stat_blocks);
		
		stat_turnovers = (TextView) findViewById(R.id.stat_turnovers);
		press_turnovers = initStatButton(R.id.press_turnovers,11,stat_turnovers);
		
		
		
		plus1 = (Button) findViewById(R.id.plus1);
		plus1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				plus1();
			}
			
		});
		minus1 = (Button) findViewById(R.id.minus1);
		minus1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				minus1();
			}
			
		});
		
		gseek = (SeekBar) findViewById(R.id.game_seek);
		gseek.setMax(30);
		gseek.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			int cont;
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				cont = progress;
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				updateActiveStat(cont);
			}
			
		});
		
		/*swiper = (FrameLayout) findViewById(R.id.frame_swipe);
		swiper.setOnTouchListener(new OnSwipeTouchListener(){
			public void onSwipeRight() {
		        plus1();
		    }
		    public void onSwipeLeft() {
		        minus1();
		    }
		});*/
		
	}

}
