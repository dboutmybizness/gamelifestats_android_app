package com.gamelifestats.glselevate.frags;

import java.sql.SQLException;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gamelifestats.glselevate.MGames;
import com.gamelifestats.glselevate.R;
import com.gamelifestats.glselevate.StatsHelper;


public class Stats_gamebrowser extends Fragment {
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
	TextView game_count, game_date;
	
	public Stats_gamebrowser(){
		
	}
	
	
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
		game_date = (TextView) rootView.findViewById(R.id.game_date);
		
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
					
					String date = StatsHelper.dateFromTime(c.getString(20));
					dbGames.game_result = c.getInt(18);
					String winloss = String.valueOf(MGames.wOrl[dbGames.game_result]);
					game_date.setText(date + " ("+winloss+")");
					
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
