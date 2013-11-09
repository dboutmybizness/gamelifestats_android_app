package com.gamelifestats.glselevate.helper;

import java.util.HashMap;

public class Statline {
	
	private int stattype;
	
	private final String[] statitems = new String[]{
		"points","rebounds","reb_off","reb_def", "assists",
		"steals", "blocks", "turnovers", "fouls", "minutes",
		"fg2m", "fg2ms", "fg2a", "fg3m", "fg3ms", 
		"fg3a", "ftm", "ftms", "fta"
	};
	
	public HashMap<String,Integer> stat_as_ints = new HashMap<String,Integer>();
	public HashMap<String,String> stat_as_strings = new HashMap<String,String>();
	
	public Statline(){
		
	}
	
	public Statline(int stattype){
		this.stattype = stattype;
		initial_build();
	}
	
	private void initial_build(){
		for(int i = 0; i < statitems.length; i++){
			stat_as_ints.put(statitems[i], 0);
		}
		render_strings();
	}
	
	public void render_strings(){
		if ( stat_as_ints.size() < 1) return;
		for (HashMap.Entry <String, Integer> entry : stat_as_ints.entrySet()) {
		    stat_as_strings.put(entry.getKey(), String.valueOf(entry.getValue()));
		}
	}
	
	public void loadstats(HashMap<String,Integer> map){
		for (HashMap.Entry <String, Integer> entry : map.entrySet()) {
		    stat_as_ints.put(entry.getKey(), entry.getValue());
		}
	}
	public void loadstats(HashMap<String,String> input, String type){
		HashMap<String, Integer> new_map = new HashMap<String, Integer>();
		for (HashMap.Entry <String,String> entry : input.entrySet()) {
			if ( entry.getValue() == "") continue;
		    new_map.put(entry.getKey(), Integer.parseInt(entry.getValue()));
		}
		loadstats(new_map);
	}
	
	public HashMap<String,String> build_from_input(HashMap<String,Integer> input){
		loadstats(input);
		calculate_stats();
		return stat_as_strings;
	}
	
	public HashMap<String,String> build_from_input(HashMap<String,String> input, String type){
		HashMap<String, Integer> new_map = new HashMap<String, Integer>();
		for (HashMap.Entry <String,String> entry : input.entrySet()) {
			if ( entry.getValue() == "") continue;
		    new_map.put(entry.getKey(), Integer.parseInt(entry.getValue()));
		}
		return build_from_input(new_map);
	}
	
	private void calculate_stats(){
		switch(this.stattype){
			case 0:
				int rebounds = stat_as_ints.get("reb_off") + stat_as_ints.get("reb_def");
				stat_as_ints.put("rebounds", rebounds);
				
				int fg2m = stat_as_ints.get("fg2m");
				int fg2ms = stat_as_ints.get("fg2ms");
				int fg2a =  fg2m + fg2ms;
				
				int fg3m = stat_as_ints.get("fg3m");
				int fg3ms = stat_as_ints.get("fg3ms");
				int fg3a =  fg3m + fg3ms;
				
				int ftm = stat_as_ints.get("ftm");
				int ftms = stat_as_ints.get("ftms");
				int fta =  ftm + ftms;
				
				int fgm = fg2m + fg3m;
				int fga = fg2a + fg3a;
				
				int points = (fg2m *2) + (fg3m *3) + ftm;
				
				///////////////// ADD TO HASH
				
				stat_as_ints.put("points", points);
				stat_as_ints.put("rebounds", rebounds);
				stat_as_ints.put("fg2a", fg2a);
				stat_as_ints.put("fg3a", fg3a);
				stat_as_ints.put("fga", fga);
				stat_as_ints.put("fta", fta);
				stat_as_ints.put("fgm", fgm);
				
				break;
		}
		
		render_strings();
	}
}
