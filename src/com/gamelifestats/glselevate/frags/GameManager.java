package com.gamelifestats.glselevate.frags;

import java.sql.SQLException;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.gamelifestats.glselevate.GameManagerArrayAdapter;
import com.gamelifestats.glselevate.MGames;
import com.gamelifestats.glselevate.R;

public class GameManager extends Fragment {

	ListView lv;
	ArrayList<String> data;
	MGames games;
	ArrayList<Integer> gameIDs;
	ArrayList<String> itemStrings;
	ArrayList<Boolean> itemBoolean;
	Resources res;
	View rootView;
	Context PContext;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootView = inflater.inflate(R.layout.activity_game_manager, container, false);
		PContext = rootView.getContext();
		
		
		if (pullData()){
			popListElements(data);
			res = getResources();
			
			lv = (ListView) rootView.findViewById(R.id.list_gm);
			GameManagerArrayAdapter gm = new GameManagerArrayAdapter(PContext, itemStrings, itemBoolean);
			lv.setAdapter(gm);
			lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					View v = parent.getChildAt(position);
					updateListItem(position, v);
				}
			}); 
		}
		return rootView;
	}
	
	private void popListElements(ArrayList<String> data){
		gameIDs = new ArrayList<Integer>();
		itemStrings = new ArrayList<String>();
		itemBoolean = new ArrayList<Boolean>();
		
		for(int i = 0; i < data.size(); i++){
			String str = (String) data.get(i);
			String[] splitter = str.split("_");
			gameIDs.add(Integer.getInteger(splitter[0]));
			itemStrings.add(splitter[1]);
			itemBoolean.add((splitter[2].equals("0")) ? false  : true);

		}
	}
	
	private boolean pullData(){
		games = new MGames(PContext);
		try {
			data = games.retArchive();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (data.size() > 0) return true;
		return false;
	}
	
	private void updateListItem(int position, View view){
		TextView tv = (TextView) view.findViewById(R.id.gm_item);
		//View layout = (View) view.findViewById(R.id.manage_list);
		//layout.setBackgroundColor(res.getColor(R.color.gls_blue));
		if ( itemBoolean.get(position) ){
			tv.setTextColor(res.getColor(R.color.Gray));
			itemBoolean.set(position, false);
		} else {
			tv.setTextColor(res.getColor(R.color.gls_blue));
			itemBoolean.set(position, true);
		}
	}

	
	public void saveArchive(View v){
		commitSave();
	}
	
	public void commitSave(){
		//games.updateArchive(gameIDs, itemBoolean);
	}

}
