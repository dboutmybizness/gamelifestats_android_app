package com.gamelifestats.glselevate.frags;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gamelifestats.glselevate.MCareer;
import com.gamelifestats.glselevate.R;
import com.gamelifestats.glselevate.StatsHelper;


public class Stats_career extends Fragment {
	Context getParentContext;
	MCareer career;
	View rootView;
	TextView tot_games,tot_minutes,tot_points,tot_rebounds,tot_reb_off,tot_reb_def,tot_assists,tot_steals,tot_blocks,tot_turnovers,tot_fouls;
	TextView avg_games,avg_minutes,avg_points,avg_rebounds,avg_reb_off,avg_reb_def,avg_assists,avg_steals,avg_blocks,avg_turnovers,avg_fouls;
	
	TextView tot_fg2m,tot_fg2a;
	TextView tot_fg3m,tot_fg3a;
	TextView tot_fgm,tot_fga;
	TextView tot_ftm,tot_fta;
	
	Button archived_games;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootView = inflater.inflate(R.layout.avg_totals, container, false);
		getParentContext = rootView.getContext();
		
		tot_games = setUpStat(R.id.tot_games);
		tot_minutes = setUpStat(R.id.tot_minutes);
		tot_points = setUpStat(R.id.tot_points);
		tot_rebounds = setUpStat(R.id.tot_rebounds);
		tot_reb_off = setUpStat(R.id.tot_reb_off);
		tot_reb_def = setUpStat(R.id.tot_reb_def);
		tot_assists = setUpStat(R.id.tot_assists);
		tot_steals = setUpStat(R.id.tot_steals);
		tot_blocks = setUpStat(R.id.tot_blocks);
		tot_turnovers = setUpStat(R.id.tot_turnovers);
		tot_fouls = setUpStat(R.id.tot_fouls);
		
		//avg_games = setUpStat(R.id.avg_games);
		avg_minutes = setUpStat(R.id.avg_minutes);
		avg_points = setUpStat(R.id.avg_points);
		avg_rebounds = setUpStat(R.id.avg_rebounds);
		avg_reb_off = setUpStat(R.id.avg_reb_off);
		avg_reb_def = setUpStat(R.id.avg_reb_def);
		avg_assists = setUpStat(R.id.avg_assists);
		avg_steals = setUpStat(R.id.avg_steals);
		avg_blocks = setUpStat(R.id.avg_blocks);
		avg_turnovers = setUpStat(R.id.avg_turnovers);
		avg_fouls = setUpStat(R.id.avg_fouls);
		
		tot_fg2m = setUpStat(R.id.tot_fg2m);
		tot_fg2a = setUpStat(R.id.tot_fg2a);
		tot_fg3m = setUpStat(R.id.tot_fg3m);
		tot_fg3a = setUpStat(R.id.tot_fg3a);
		tot_fgm = setUpStat(R.id.tot_fgm);
		tot_fga = setUpStat(R.id.tot_fga);
		tot_ftm = setUpStat(R.id.tot_ftm);
		tot_fta = setUpStat(R.id.tot_fta);
		
		return rootView;
	}
	
	public TextView setUpStat(int i){
		TextView tv = (TextView) rootView.findViewById(i);
		return tv;
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
			tot_reb_def.setText(StatsHelper.int2Str(career.tot_reb_def));
			tot_assists.setText(StatsHelper.int2Str(career.tot_assists));
			tot_steals.setText(StatsHelper.int2Str(career.tot_steals));
			tot_blocks.setText(StatsHelper.int2Str(career.tot_blocks));
			tot_turnovers.setText(StatsHelper.int2Str(career.tot_turnovers));
			tot_fouls.setText(StatsHelper.int2Str(career.tot_fouls));
			tot_fg2m.setText(StatsHelper.int2Str(career.tot_fg2m));
			tot_fg2a.setText(StatsHelper.int2Str(career.tot_fg2a));
			tot_fg3m.setText(StatsHelper.int2Str(career.tot_fg3m));
			tot_fg3a.setText(StatsHelper.int2Str(career.tot_fg3a));
			tot_fgm.setText(StatsHelper.int2Str(career.tot_fgm));
			tot_fga.setText(StatsHelper.int2Str(career.tot_fga));
			tot_ftm.setText(StatsHelper.int2Str(career.tot_ftm));
			tot_fta.setText(StatsHelper.int2Str(career.tot_fta));
			
			
			avg_minutes.setText(StatsHelper.float2Str(career.avg_minutes));
			avg_points.setText(StatsHelper.float2Str(career.avg_points));
			avg_rebounds.setText(StatsHelper.float2Str(career.avg_rebounds));
			avg_reb_off.setText(StatsHelper.float2Str(career.avg_reb_off));
			avg_reb_def.setText(StatsHelper.float2Str(career.avg_reb_def));
			avg_assists.setText(StatsHelper.float2Str(career.avg_assists));
			avg_steals.setText(StatsHelper.float2Str(career.avg_steals));
			avg_blocks.setText(StatsHelper.float2Str(career.avg_blocks));
			avg_turnovers.setText(StatsHelper.float2Str(career.avg_turnovers));
			avg_fouls.setText(StatsHelper.float2Str(career.avg_fouls));
		}
	
	}
		
}
