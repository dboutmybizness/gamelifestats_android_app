package com.gamelifestats.glselevate;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class PlayBall_GameStatsEdit extends Activity {
	
	SeekBar fg1md, fg1ms, fg2md, fg2ms, fg3md, fg3ms;
	SeekBar rebs_off, rebs_def;
	SeekBar minutes, assists, steals, blocks, turnovers, fouls;
	TextView seekPrint;
	Boolean playbuzzer = false;
	TextView dmin,dfgma, dfg3ma, dftma, dfgp, dfg3p, dftp;
	TextView dpts, dasts, dstls, dblks, dtos, dfouls;
	TextView doreb, dtreb;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_play_ball_game_stats_edit);
		// Show the Up button in the action bar.
		setupActionBar();
		
		setUpAllStats();

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
		
		MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.buzzer);
	    mp.start();
	    
	}
	
	public TextView initTV(int Id){
		TextView tv = (TextView) findViewById(Id);
		return tv;
	}
	
	public void setUpAllStats(){
		seekPrint = (TextView) findViewById(R.id.seek_print);
		dmin = (TextView) findViewById(R.id.dis_min);
		dfgma = (TextView) findViewById(R.id.dis_fgma);
		dfg3ma = (TextView) findViewById(R.id.dis_fg3ma);
		dftma = (TextView) findViewById(R.id.dis_ftma);
		dpts = (TextView) findViewById(R.id.dis_pts);
		doreb = (TextView) findViewById(R.id.dis_oreb);
		dtreb = (TextView) findViewById(R.id.dis_treb);
		dasts = initTV(R.id.dis_ast);
		dstls = initTV(R.id.dis_stl);
		dblks = initTV(R.id.dis_blk);
		dtos = initTV(R.id.dis_tos);
		dfouls = initTV(R.id.dis_fouls);
		dfgp = initTV(R.id.dis_fgp);
		dfg3p = initTV(R.id.dis_fg3p);
		dftp = initTV(R.id.dis_ftp);
		
		
		
		fg2md = setUpSeeks(R.id.ed_fg2md, 25);
		fg2ms = setUpSeeks(R.id.ed_fg2ms, 25);
		fg3md = setUpSeeks(R.id.ed_fg3md, 20);
		fg3ms = setUpSeeks(R.id.ed_fg3ms, 20);
		fg1md = setUpSeeks(R.id.ed_fg1md, 20);
		fg1ms = setUpSeeks(R.id.ed_fg1ms, 20);
		rebs_off = setUpSeeks(R.id.ed_rebs_off, 30);
		rebs_def = setUpSeeks(R.id.ed_rebs_def, 30);
		minutes = setUpSeeks(R.id.ed_min, 60);
		assists = setUpSeeks(R.id.ed_asts, 25);
		steals = setUpSeeks(R.id.ed_stls, 20);
		blocks = setUpSeeks(R.id.ed_blks, 15);
		turnovers = setUpSeeks(R.id.ed_turnovers, 15);
		fouls = setUpSeeks(R.id.ed_fouls, 6);
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
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				seekPrint.setText(Integer.toString(showNum));
				refactorStats();
				
			}
			
		});
		return sb;
	}
	
	private void refactorStats(){
		//combo field vars
		int pre_fg2m = gProg(fg2md);
		int pre_fg3m = gProg(fg3md);
		int pre_fgm = (pre_fg2m + pre_fg3m);
		int pre_fg3ms = gProg(fg3ms);
		int pre_fg2ms = gProg(fg2ms);
		int pre_fgms = pre_fg2ms + pre_fg3ms;
		String fgm = (Integer.toString(pre_fgm));
		int pre_fga = pre_fgms + pre_fgm;
		String fga = (Integer.toString(pre_fga));
		
		
		String fg3m = (Integer.toString(pre_fg3m));
		int pre_fg3a = pre_fg3ms + pre_fg3m;
		String fg3a = (Integer.toString(pre_fg3a));
		
		int pre_ftm = gProg(fg1md);
		int pre_ftms = gProg(fg1ms);
		int pre_fta = pre_ftms + pre_ftm;
		String ftm = Integer.toString(pre_ftm);
		String fta = Integer.toString(pre_fta);
		
		
		
		dmin.setText(Integer.toString(gProg(minutes)));
		dfgma.setText(fgm + "-" +fga);
		dfg3ma.setText(fg3m + "-" + fg3a);
		dftma.setText(ftm + "-" + fta);
		dpts.setText(Integer.toString(pre_ftm + (pre_fg2m * 2) + (pre_fg3m * 3)));
		doreb.setText(Integer.toString(gProg(rebs_off)));
		dtreb.setText(Integer.toString(gProg(rebs_off) + gProg(rebs_def)));
		dasts.setText(Integer.toString(gProg(assists)));
		dstls.setText(Integer.toString(gProg(steals)));
		dblks.setText(Integer.toString(gProg(blocks)));
		dtos.setText(Integer.toString(gProg(turnovers)));
		dfouls.setText(Integer.toString(gProg(fouls)));
		dfgp.setText(divPerc(pre_fgm, pre_fga));
		dfg3p.setText(divPerc(pre_fg3m, pre_fg3a));
		dftp.setText(divPerc(pre_ftm, pre_fta));
		
		
	}
	
	public String divPerc(int l, int b){
		if ( l  < 1) return "0.0";
		Float f = ((l * 100.0f) / b);
		if( f == 100) return "100";
		
		return roundToOneDigit(f);
	}
	
	public static String roundToOneDigit(float paramFloat) {
	    return String.format("%.1f%n", paramFloat);
	}
	
	private int gProg(SeekBar sb){
		return sb.getProgress();
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
		getMenuInflater().inflate(R.menu.gls__act__play_ball__game_stats_edit,
				menu);
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

}
