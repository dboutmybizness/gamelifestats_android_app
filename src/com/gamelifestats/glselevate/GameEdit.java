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
import android.view.View.OnLongClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class GameEdit extends Activity {
	
	SeekBar fg1md, fg1ms, fg2md, fg2ms, fg3md, fg3ms;
	SeekBar rebs_off, rebs_def;
	SeekBar minutes, assists, steals, blocks, turnovers, fouls;
	TextView seekPrint;
	Boolean playbuzzer = false;
	TextView dmin,dfgma, dfg3ma, dftma, dfgp, dfg3p, dftp, dfg2ma;
	TextView dpts, dasts, dstls, dblks, dtos, dfouls;
	TextView dreb, dtreb;
	TextView stline_pts, stline_rebs, stline_asts, stline_stls;
	
	TextView lab_fg2made, lab_fg2missed, lab_fg3made, lab_fg3missed, lab_ftmade, lab_ftmissed, lab_oreb, lab_dreb;
	TextView lab_minutes, lab_assists, lab_steals, lab_blocks, lab_turnovers, lab_fouls;
	RadioGroup winloss;
	
	MGames dbGames;
	
	private final int THRESHOLD_SMALL = 10;
	private final int THRESHOLD_MED = 15;
	private final int THRESHOLD_LARGE = 20;
	private final int THRESHOLD_XLARGE = 25;
	
	Resources res;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_edit);
		// Show the Up button in the action bar.
		setupActionBar();
		
		setUpAllStats();
		dbGames = new MGames(this);
		
		renderPage();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if ( playbuzzer == false){
			playBuzzer();
			playbuzzer = true;
		}
		
		refactorStats();
	}
	
	public void playBuzzer(){
		
		//MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.buzzer);
	    //mp.start();
	    
	}
	
	public TextView initTV(int Id){
		TextView tv = (TextView) findViewById(Id);
		return tv;
	}
	
	private TextView makeLabelListener(int viewId, final SeekBar sb, final String str, boolean canIncrease){
		TextView tv = (TextView) findViewById(viewId);
		tv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				int current_seek = ckSeekBar(sb);
				seekPrinter(current_seek);
				Toast.makeText(getBaseContext(), str+ ": " + current_seek, Toast.LENGTH_SHORT).show();
				
			}
			
		});
		if ( canIncrease){
			tv.setOnLongClickListener(new OnLongClickListener(){
	
				@Override
				public boolean onLongClick(View v) {
					increaseThreshold(sb, str);
					return false;
				}
				
			});
		}
		return tv;
	}
	
	private void increaseThreshold(final SeekBar sb, String str){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
			.setMessage("Increase "+str)
			//.setCancelable(false)
			.setNeutralButton("+5", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					sb.setMax(sb.getMax() + 5);
				}
			})
			.setPositiveButton("+10", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					sb.setMax(sb.getMax() + 10);
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	private int ckSeekBar(SeekBar sb){
		return sb.getProgress();
	}
	
	public void setUpAllStats(){
		res = getResources();
		
		winloss = (RadioGroup) findViewById(R.id.winlosegroup);
		
		stline_pts = initTV(R.id.dis_spoints);
		stline_rebs = initTV(R.id.dis_srebounds);
		stline_asts = initTV(R.id.dis_sassists);
		stline_stls = initTV(R.id.dis_ssteals);
		
		seekPrint = (TextView) findViewById(R.id.seek_print);
		dmin = (TextView) findViewById(R.id.dis_minutes);
		dfgma = (TextView) findViewById(R.id.dis_fgs);
		dfg2ma = (TextView) findViewById(R.id.dis_fg2s);
		dfg3ma = (TextView) findViewById(R.id.dis_fg3s);
		dftma = (TextView) findViewById(R.id.dis_fts);
		dpts = (TextView) findViewById(R.id.dis_points);
		dreb = (TextView) findViewById(R.id.dis_rebounds);
		//dtreb = (TextView) findViewById(R.id.dis_treb);

		dasts = initTV(R.id.dis_assists);
		dstls = initTV(R.id.dis_steals);
		dblks = initTV(R.id.dis_blocks);
		dtos = initTV(R.id.dis_turnovers);
		dfouls = initTV(R.id.dis_fouls);
		//dfgp = initTV(R.id.dis_fgp);
		//dfg3p = initTV(R.id.dis_fg3p);
		//dftp = initTV(R.id.dis_ftp);

		fg2md = setUpSeeks(R.id.ed_fg2md, THRESHOLD_MED);
		fg2ms = setUpSeeks(R.id.ed_fg2ms, THRESHOLD_MED);
		fg3md = setUpSeeks(R.id.ed_fg3md, THRESHOLD_MED);
		fg3ms = setUpSeeks(R.id.ed_fg3ms, THRESHOLD_MED);
		fg1md = setUpSeeks(R.id.ed_ftmd, THRESHOLD_MED);
		fg1ms = setUpSeeks(R.id.ed_ftms, THRESHOLD_MED);
		rebs_off = setUpSeeks(R.id.ed_reb_off, THRESHOLD_MED);
		rebs_def = setUpSeeks(R.id.ed_reb_def, THRESHOLD_XLARGE);
		minutes = setUpSeeks(R.id.ed_minutes, 60);
		assists = setUpSeeks(R.id.ed_assists, THRESHOLD_MED);
		steals = setUpSeeks(R.id.ed_steals, THRESHOLD_SMALL);
		blocks = setUpSeeks(R.id.ed_blocks, THRESHOLD_SMALL);
		turnovers = setUpSeeks(R.id.ed_turnovers, THRESHOLD_SMALL);
		fouls = setUpSeeks(R.id.ed_fouls, 6);
		
		
		lab_fg2made = makeLabelListener(R.id.lab_fg2made, fg2md, res.getString(R.string.pointers_2) + " " +res.getString(R.string.made) , true);
		lab_fg2missed = makeLabelListener(R.id.lab_fg2missed, fg2ms, res.getString(R.string.pointers_2) + " " +res.getString(R.string.missed) , true);
		
		lab_fg3made = makeLabelListener(R.id.lab_fg3made, fg3md, res.getString(R.string.pointers_3) + " " +res.getString(R.string.made) , true);
		lab_fg3missed = makeLabelListener(R.id.lab_fg3missed, fg3ms, res.getString(R.string.pointers_3) + " " +res.getString(R.string.missed) ,true);
		
		lab_ftmade = makeLabelListener(R.id.lab_ftmade, fg1md, res.getString(R.string.free_throws) + " " +res.getString(R.string.made) ,true);
		lab_ftmissed = makeLabelListener(R.id.lab_ftmissed, fg1ms, res.getString(R.string.free_throws) + " " +res.getString(R.string.missed) ,true);
		
		lab_oreb = makeLabelListener(R.id.lab_oreb, rebs_off, res.getString(R.string.rebounds) + " " + res.getString(R.string.offensive), true);
		lab_dreb = makeLabelListener(R.id.lab_dreb, rebs_def, res.getString(R.string.rebounds) + " " + res.getString(R.string.defensive), true);
		
		lab_minutes = makeLabelListener(R.id.lab_seek_minutes, minutes, res.getString(R.string.minutes), false);
		lab_assists = makeLabelListener(R.id.lab_seek_assists, assists, res.getString(R.string.assists), true);
		lab_steals = makeLabelListener(R.id.lab_seek_steals, steals, res.getString(R.string.steals), true);
		lab_blocks = makeLabelListener(R.id.lab_seek_blocks, blocks, res.getString(R.string.blocks), true);
		lab_turnovers = makeLabelListener(R.id.lab_seek_turnovers, turnovers, res.getString(R.string.turnovers), true);
		lab_fouls = makeLabelListener(R.id.lab_seek_fouls, fouls, res.getString(R.string.fouls) ,false);
	}
	
	private SeekBar setUpSeeks(int Id, int max){
		SeekBar sb = (SeekBar) findViewById(Id);
		sb.setMax(max);
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			int showNum;
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				showNum = progress;
				//seekPrint.setText(Integer.toString(showNum));
				seekPrinter(showNum);
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//seekPrint.setText(Integer.toString(showNum));
				refactorStats();
				
			}
			
		});
		return sb;
	}
	
	private void seekPrinter(int i){
		seekPrint.setText(String.valueOf(i));
	}
	
	private String retRadioValue(){
		int idOfRadio = winloss.getCheckedRadioButtonId();
		RadioButton rb = (RadioButton) findViewById(idOfRadio);
		return rb.getText().toString();
	}
	
	private void refactorStats(){
		
		
		dbGames.game_result = (retRadioValue().equals("Win")) ? 1 : 0;
		
		dbGames.minutes = minutes.getProgress();
		
		dbGames.fg2m = fg2md.getProgress();
		dbGames.fg2ms = fg2ms.getProgress();
		
		dbGames.fg3m = fg3md.getProgress();
		dbGames.fg3ms = fg3ms.getProgress();
		
		dbGames.ftm = fg1md.getProgress();
		dbGames.ftms = fg1ms.getProgress();
		
		dbGames.reb_def = rebs_def.getProgress();
		dbGames.reb_off = rebs_off.getProgress();
		
		dbGames.assists = assists.getProgress();
		dbGames.steals = steals.getProgress();
		dbGames.blocks = blocks.getProgress();
		dbGames.turnovers = turnovers.getProgress();
		dbGames.fouls = fouls.getProgress();
		
		dbGames.renderStats();
		renderPage();
	}
	
	public void renderPage(){

		dmin.setText(dbGames.s_minutes);
		dfgma.setText(dbGames.s_fgm + "/" + dbGames.s_fga);
		dfg2ma.setText(dbGames.s_fg2m + "/" + dbGames.s_fg2a);
		dfg3ma.setText(dbGames.s_fg3m + "/" + dbGames.s_fg3a);
		dftma.setText(dbGames.s_ftm + "/" + dbGames.s_fta);
		dpts.setText(dbGames.s_points);
		dreb.setText(dbGames.s_rebounds);
		
		dasts.setText(dbGames.s_assists);
		dstls.setText(dbGames.s_steals);
		dblks.setText(dbGames.s_blocks);
		dtos.setText(dbGames.s_turnovers);
		dfouls.setText(dbGames.s_fouls);
		
		stline_pts.setText(dbGames.s_points);
		stline_rebs.setText(dbGames.s_rebounds);
		stline_asts.setText(dbGames.s_assists);
		stline_stls.setText(dbGames.s_steals);
		
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
	    outState.putBoolean("playbuzzer", playbuzzer);
	    super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	    playbuzzer = savedInstanceState.getBoolean("playbuzzer");
	    super.onRestoreInstanceState(savedInstanceState);
	}
	
	public void resetStats(View v){
		minutes.setProgress(0);
		
		fg2md.setProgress(0);
		fg2ms.setProgress(0);
		
		fg3md.setProgress(0);
		fg3ms.setProgress(0);
		
		fg1md.setProgress(0);
		fg1ms.setProgress(0);
		
		rebs_def.setProgress(0);
		rebs_off.setProgress(0);
		
		assists.setProgress(0);
		steals.setProgress(0);
		blocks.setProgress(0);
		turnovers.setProgress(0);
		fouls.setProgress(0);
		
		refactorStats();
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
						startActivity(new Intent(getBaseContext(), Statbook.class));
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
		dbGames.insertStats();
	}
	
	public void openTips(View v){
		//startActivity(new Intent(this, GameMeta.class));
	}

}
