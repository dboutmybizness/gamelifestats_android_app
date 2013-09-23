package com.gamelifestats.glselevate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Statbook extends FragmentActivity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	Cursor c = null;
	
	MGames dbGames;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_statbook);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Show the Up button in the action bar.
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager() );

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		
		//dbGames = new Model_Games(this);
		//setUpAVG();
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if ( item.getTitle().equals(ABSelect.ACT_STATBOOK) ){
			int goToTab = 0;
			if ( mViewPager.getCurrentItem() == 0 ) goToTab = 1;
			mViewPager.setCurrentItem(goToTab);

		}
		
		ABSelect bar_select = new ABSelect(this, ABSelect.ACT_STATBOOK);
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

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			//Toast.makeText(getBaseContext(), ""+position, Toast.LENGTH_SHORT).show();
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fm;
			if ( position == 0){
				fm = new AVGTOT();
			} else {
				fm = new GAMEBYGAME();
			}
			return fm;
			/*Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;*/
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.avg_totals);
			case 1:
				return getString(R.string.game_by_game);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}
	
	public static class AVGTOT extends Fragment {
		Context getParentContext;
		MCareer career;
		View rootView;
		TextView tot_games,tot_minutes,tot_points,tot_rebounds,tot_reb_off,tot_assists,tot_steals,tot_blocks,tot_turnovers,tot_fouls;
		TextView avg_games,avg_minutes,avg_points,avg_rebounds,avg_reb_off,avg_assists,avg_steals,avg_blocks,avg_turnovers,avg_fouls;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			rootView = inflater.inflate(R.layout.avg_totals, container, false);
			getParentContext = rootView.getContext();
			
			tot_games = (TextView) rootView.findViewById(R.id.tot_games);
			tot_minutes = (TextView) rootView.findViewById(R.id.tot_minutes);
			tot_points = (TextView) rootView.findViewById(R.id.tot_points);
			tot_rebounds = (TextView) rootView.findViewById(R.id.tot_rebounds);
			tot_reb_off = (TextView) rootView.findViewById(R.id.tot_reb_off);
			//tot_reb_def = (TextView) rootView.findViewById(R.id.tot_reb_def);
			tot_assists = (TextView) rootView.findViewById(R.id.tot_assists);
			tot_steals = (TextView) rootView.findViewById(R.id.tot_steals);
			tot_blocks = (TextView) rootView.findViewById(R.id.tot_blocks);
			tot_turnovers = (TextView) rootView.findViewById(R.id.tot_turnovers);
			tot_fouls = (TextView) rootView.findViewById(R.id.tot_fouls);
			
			avg_games = (TextView) rootView.findViewById(R.id.avg_games);
			avg_minutes = (TextView) rootView.findViewById(R.id.avg_minutes);
			avg_points = (TextView) rootView.findViewById(R.id.avg_points);
			avg_rebounds = (TextView) rootView.findViewById(R.id.avg_rebounds);
			avg_reb_off = (TextView) rootView.findViewById(R.id.avg_reb_off);
			//avg_reb_def = (TextView) rootView.findViewById(R.id.avg_reb_def);
			avg_assists = (TextView) rootView.findViewById(R.id.avg_assists);
			avg_steals = (TextView) rootView.findViewById(R.id.avg_steals);
			avg_blocks = (TextView) rootView.findViewById(R.id.avg_blocks);
			avg_turnovers = (TextView) rootView.findViewById(R.id.avg_turnovers);
			avg_fouls = (TextView) rootView.findViewById(R.id.avg_fouls);
			
			
			
			
			return rootView;
		}
		
		@Override
		public void onResume(){
			super.onResume();
			career = new MCareer(getParentContext);
			setUpCareer();
		}
		
		private void setUpCareer(){
			Boolean has_career;
			
			has_career = career.loadSavedCareer();
			
			if ( has_career ){
				tot_games.setText(StatsHelper.int2Str(career.tot_games));
				tot_minutes.setText(StatsHelper.int2Str(career.tot_minutes));
				tot_points.setText(StatsHelper.int2Str(career.tot_points));
				tot_rebounds.setText(StatsHelper.int2Str(career.tot_rebounds));
				tot_reb_off.setText(StatsHelper.int2Str(career.tot_reb_off));
				tot_assists.setText(StatsHelper.int2Str(career.tot_assists));
				tot_steals.setText(StatsHelper.int2Str(career.tot_steals));
				tot_blocks.setText(StatsHelper.int2Str(career.tot_blocks));
				tot_turnovers.setText(StatsHelper.int2Str(career.tot_turnovers));
				tot_fouls.setText(StatsHelper.int2Str(career.tot_fouls));
				
				avg_minutes.setText(StatsHelper.float2Str(career.avg_minutes));
				avg_points.setText(StatsHelper.float2Str(career.avg_points));
				avg_rebounds.setText(StatsHelper.float2Str(career.avg_rebounds));
				avg_reb_off.setText(StatsHelper.float2Str(career.avg_reb_off));
				avg_assists.setText(StatsHelper.float2Str(career.avg_assists));
				avg_steals.setText(StatsHelper.float2Str(career.avg_steals));
				avg_blocks.setText(StatsHelper.float2Str(career.avg_blocks));
				avg_turnovers.setText(StatsHelper.float2Str(career.avg_turnovers));
				avg_fouls.setText(StatsHelper.float2Str(career.avg_fouls));
			}
		
		}
	}
	
	public static class GAMEBYGAME extends Fragment {
		MGames dbGames;
		Cursor mCursor;
		View rootView;
		Context base;
		TextView points,rebounds,assists,steals,blocks,turnovers,fouls,minutes;
		ArrayList<Integer> games;
		int AtRegister = 0;
		private int TOTAL_GAMES;
		private int LAST_GAME;
		private final int FIRST_GAME = 0;
		Button next,prev,first,last;
		Boolean has_games = false;
		ProgressBar game_progress;
		TextView game_count;
		
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			rootView = inflater.inflate(R.layout.game_by_game, container, false);
			base = rootView.getContext();
			
			dbGames = new MGames(base);
			getGames();
			
			if ( has_games ) setUpGames();
			return rootView;
		}
		
		public void getGames(){
			try {
				games = dbGames.gameHashMap();
				TOTAL_GAMES = games.size();
				if ( TOTAL_GAMES > 0) has_games = true;
				LAST_GAME = TOTAL_GAMES - 1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void setUpGames(){
			game_count = (TextView) rootView.findViewById(R.id.game_count);
			
			points = (TextView) rootView.findViewById(R.id.dis_points);
			rebounds = (TextView) rootView.findViewById(R.id.dis_rebounds);
			assists = (TextView) rootView.findViewById(R.id.dis_assists);
			steals = (TextView) rootView.findViewById(R.id.dis_steals);
			blocks = (TextView) rootView.findViewById(R.id.dis_blocks);
			turnovers = (TextView) rootView.findViewById(R.id.dis_turnovers);
			fouls = (TextView) rootView.findViewById(R.id.dis_fouls);
			minutes = (TextView) rootView.findViewById(R.id.dis_minutes);
			
			game_progress = (ProgressBar) rootView.findViewById(R.id.game_progress);
			game_progress.setMax(LAST_GAME);
			
			
			next = (Button) rootView.findViewById(R.id.next_button);
			next.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					updateRegister(1);
				}
			});
			prev = (Button) rootView.findViewById(R.id.prev_button);
			prev.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					updateRegister(-1);
				}
				
			});
			first = (Button) rootView.findViewById(R.id.first_button);
			first.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					updateRegister(FIRST_GAME, true);
				}
				
			});
			last = (Button) rootView.findViewById(R.id.last_button);
			last.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					updateRegister(LAST_GAME, true);
					
				}
				
			});
			if ( TOTAL_GAMES  > 0){
				updateRegister(LAST_GAME, true);
			}
			
			
		}
		
		public void produceGame(int g){
			if ( TOTAL_GAMES > 0){
				try {
					dbGames.open();
					Cursor c = dbGames.getGame(g);
					if ( c.getCount() > 0){
						c.moveToFirst();
						minutes.setText(c.getString(2));
						points.setText(c.getString(3));
						rebounds.setText(c.getString(4));
						assists.setText(c.getString(7));
						steals.setText(c.getString(8));
						blocks.setText(c.getString(9));
						turnovers.setText(c.getString(10));
						fouls.setText(c.getString(11));
						
						game_count.setText((AtRegister+1) + " of " + TOTAL_GAMES );
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				dbGames.close();
			}
		}

		
		private void updateRegister(int i){
			Boolean is_ok = false;
			switch(i){
			case -1: if (AtRegister > 0) is_ok = true;
				break;
			case 1: if ( AtRegister < LAST_GAME) is_ok = true;
				break;
			default:
				break;
			}
			if ( !is_ok ) return;
			
			AtRegister += i;
			game_progress.setProgress(AtRegister);
			produceGame(games.get(AtRegister));
		}
		private void updateRegister(int i, Boolean override){
			AtRegister = i;
			game_progress.setProgress(AtRegister);
			produceGame(games.get(AtRegister));
		}
		
	}

}
