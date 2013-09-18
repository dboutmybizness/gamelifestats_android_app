package com.gamelifestats.glselevate;

import java.sql.SQLException;
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
import android.view.ViewGroup;
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
	
	public void setUpAVG(){
		
		try {
			dbGames.open();
			c = dbGames.getGame(1);
			c.moveToFirst();
			Toast.makeText(getBaseContext(), c.getString(1), Toast.LENGTH_SHORT).show();
			dbGames.close();
			 Toast.makeText(getBaseContext(), "ghhh", Toast.LENGTH_SHORT).show();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			//career = new Model_Career(getParentContext);
			
			return rootView;
		}
		
		@Override
		public void onResume(){
			super.onResume();
			career = new MCareer(getParentContext);
			
			tot_games = (TextView) getActivity().findViewById(R.id.tot_games);
			tot_minutes = (TextView) getActivity().findViewById(R.id.tot_minutes);
			tot_points = (TextView) getActivity().findViewById(R.id.tot_points);
			tot_rebounds = (TextView) getActivity().findViewById(R.id.tot_rebounds);
			tot_reb_off = (TextView) getActivity().findViewById(R.id.tot_reb_off);
			//tot_reb_def = (TextView) getActivity().findViewById(R.id.tot_reb_def);
			tot_assists = (TextView) getActivity().findViewById(R.id.tot_assists);
			tot_steals = (TextView) getActivity().findViewById(R.id.tot_steals);
			tot_blocks = (TextView) getActivity().findViewById(R.id.tot_blocks);
			tot_turnovers = (TextView) getActivity().findViewById(R.id.tot_turnovers);
			tot_fouls = (TextView) getActivity().findViewById(R.id.tot_fouls);
			
			avg_games = (TextView) getActivity().findViewById(R.id.avg_games);
			avg_minutes = (TextView) getActivity().findViewById(R.id.avg_minutes);
			avg_points = (TextView) getActivity().findViewById(R.id.avg_points);
			avg_rebounds = (TextView) getActivity().findViewById(R.id.avg_rebounds);
			avg_reb_off = (TextView) getActivity().findViewById(R.id.avg_reb_off);
			//avg_reb_def = (TextView) getActivity().findViewById(R.id.avg_reb_def);
			avg_assists = (TextView) getActivity().findViewById(R.id.avg_assists);
			avg_steals = (TextView) getActivity().findViewById(R.id.avg_steals);
			avg_blocks = (TextView) getActivity().findViewById(R.id.avg_blocks);
			avg_turnovers = (TextView) getActivity().findViewById(R.id.avg_turnovers);
			avg_fouls = (TextView) getActivity().findViewById(R.id.avg_fouls);

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
		TextView pts;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			rootView = inflater.inflate(R.layout.gamebygame, container, false);
			base = rootView.getContext();
			pts = (TextView) rootView.findViewById(R.id.pts);
			//setUpGames();
			return rootView;
			
		}
		
		public void setUpGames(){
			dbGames = new MGames(base);
			
			try {
				mCursor = dbGames.getGame(1);
				if ( mCursor == null){
					Toast.makeText(base, "something wrong", Toast.LENGTH_SHORT).show();
				} else {
					
					//Log.w("GLS", mCursor.getString(1));
					//Toast.makeText(base, mCursor.getCount()+'r', Toast.LENGTH_SHORT).show();
					//mCursor.moveToFirst();
					//pts.setText(mCursor.getString(1));
					//Toast.makeText(base, "something right", Toast.LENGTH_SHORT).show();
				}
					//if (mCursor.moveToFirst()){
					//Toast.makeText(rootView.getContext(), mCursor.getString(1), Toast.LENGTH_SHORT).show();
				//}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			/*renderGames();
			if (mCursor.moveToFirst()){
				pts.setText(mCursor.getString(1));
			}*/
		}
		public void renderGames(){
			try {
				mCursor = dbGames.getGame(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
