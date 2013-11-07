package com.gamelifestats.glselevate.helper;

import android.app.Activity;
import android.content.res.Resources;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gamelifestats.glselevate.R;

public class SetUpStatView extends SetUpPageView{
	Activity act;
	Button ActiveStatButton = null;
	Resources resources;
	int orange; 
	int white;
	
	SparseArray<Integer[]> viewCallback;
	SparseArray<Button> vHolderButtons;
	SparseArray<TextView> vHolderTextViews;
	SparseArray<String> fHolder;
	
	Button plus1;
	Button minus1;
	
	private int statScreenType;
	
	public SetUpStatView(Activity activity, int statScreenType){
		this.act = activity;
		this.setStatScreenType(statScreenType);
		resources = this.act.getResources();
		orange = resources.getColor(R.color.Orange);
		white = resources.getColor(R.color.White);
		
		vHolderButtons = new SparseArray<Button>();
		vHolderTextViews = new SparseArray<TextView>();
		viewCallback = new SparseArray<Integer[]>();
		fHolder = new SparseArray<String>();
	}
	
	private Button breturn(int button_int){
		return (Button) vHolderButtons.get(button_int);
	}
	private TextView treturn(int textview_int){
		return (TextView) vHolderTextViews.get(textview_int);
	}
	
	public void addView(String db_field, int button_int, Integer[] textview_ints){
		
		vHolderButtons.put(button_int, (Button)this.act.findViewById(button_int));
		for ( int i=0;i<textview_ints.length;i++){
			vHolderTextViews.put(textview_ints[i], (TextView)this.act.findViewById(textview_ints[i]));
		}
		
		viewCallback.put(button_int, textview_ints);
		fHolder.put(button_int, db_field);
		
		Button button = breturn(button_int);
		
		button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				setActiveButton((Button)arg0);
			}
			
		});
	}
	
	private void resetColors(){
		for(int i=0; i< vHolderButtons.size(); i++){
			if (ActiveStatButton == vHolderButtons.valueAt(i)) continue;
			vHolderButtons.valueAt(i).setTextColor(white);
		}
		
		for(int i=0; i< vHolderTextViews.size(); i++){
			vHolderTextViews.valueAt(i).setTextColor(white);
		}
	}
	
	private void setActiveButton(Button b){
		if(ActiveStatButton == b){
			changeStat(1);
			return;
		}
		
		ActiveStatButton = b;
		b.setTextColor(orange);
		
		resetColors();	//reset all text and buttons
		
		Integer[] textviews = viewCallback.get(b.getId());
		if ( textviews.length > 0){
			for(int i = 0; i < textviews.length; i++){
				TextView tv = treturn(textviews[i]);
				tv.setTextColor(orange);
			}
		}
	}
	
	private void changeStat(int i){
		if ( ActiveStatButton == null) return;
		
		int b_id = ActiveStatButton.getId();
		String field_to_update = fHolder.get(b_id);
		String current_value = this.fieldsHash.get(field_to_update);
		int pre_val = i + Integer.parseInt(current_value);
		int val = (pre_val < 1) ? 0 : pre_val;
		this.fieldsHash.put(field_to_update, String.valueOf(val));
		
		renderStats();
	}
	
	private void wHash(String key, int val){
		this.fieldsHash.put(key, String.valueOf(val));
	}
	
	private int rHash(String key){
		return Integer.parseInt(this.fieldsHash.get(key)); 
	}
	
	private void renderStats(){
		if ( this.statScreenType == 0){
			int fg2m = rHash("fg2m");
			int fg2ms = rHash("fg2ms");
			int fg2a = rHash("fg2a");
			
			int fg3m = rHash("fg3m");
			int fg3ms = rHash("fg3ms");
			int fg3a = rHash("fg3a");
			
			int ftm = rHash("ftm");
			int ftms = rHash("ftms");
			int fta = rHash("fta");
			
			int fgm = rHash("fgm");
			int fga = rHash("fga");
			
			int reb_off = rHash("reb_off");
			int reb_def = rHash("reb_def");
			
			wHash("assists", rHash("assists"));
			wHash("steals", rHash("steals"));
			wHash("blocks", rHash("blocks"));
			wHash("turnovers", rHash("turnovers"));
			wHash("fouls", rHash("fouls"));
			
			fg2a = fg2m + fg2ms;
			fg3a = fg3m + fg3ms;
			fta = ftm + ftms;
			
			fgm = fg2m + fg3m;
			fga = fg2a + fg3a;
			
			
			int points = (fg2m * 2) + (fg3m *3) + ftm;
			int rebounds = reb_off + reb_def;
			
			wHash("fgm", fgm);
			wHash("fga", fga);
			wHash("fg2m", fg2m);
			wHash("fg2a", fg2a);
			wHash("fg3m", fg3m);
			wHash("fg3a", fg3a);
			wHash("ftm", ftm);
			wHash("fta", fta);
			wHash("reb_def", reb_def);
			wHash("reb_off", reb_off);
			wHash("rebounds", rebounds);
			
			wHash("points", points);
			
			VH.rViews(this.act.findViewById(R.id.stat_fgs), this.fieldsHash.get("fgm")+ '-' + this.fieldsHash.get("fga"));
			VH.rViews(this.act.findViewById(R.id.stat_fg2s), this.fieldsHash.get("fg2m")+ '-' + this.fieldsHash.get("fg2a"));
			VH.rViews(this.act.findViewById(R.id.stat_fg3s), this.fieldsHash.get("fg3m")+ '-' + this.fieldsHash.get("fg3a"));
			VH.rViews(this.act.findViewById(R.id.stat_fts), this.fieldsHash.get("ftm")+ '-' + this.fieldsHash.get("fta"));
			VH.rViews(this.act.findViewById(R.id.dis_rebounds), this.fieldsHash.get("rebounds"));
			VH.rViews(this.act.findViewById(R.id.stat_def), this.fieldsHash.get("reb_def"));
			VH.rViews(this.act.findViewById(R.id.stat_off), this.fieldsHash.get("reb_off"));
			VH.rViews(this.act.findViewById(R.id.dis_points), this.fieldsHash.get("points"));
			VH.rViews(this.act.findViewById(R.id.dis_assists), this.fieldsHash.get("assists"));
			VH.rViews(this.act.findViewById(R.id.dis_steals), this.fieldsHash.get("steals"));
			VH.rViews(this.act.findViewById(R.id.dis_blocks), this.fieldsHash.get("blocks"));
			VH.rViews(this.act.findViewById(R.id.stat_turnovers), this.fieldsHash.get("turnovers"));
			VH.rViews(this.act.findViewById(R.id.stat_fouls), this.fieldsHash.get("fouls"));
		}
		/*
		fg2a = fg2m + fg2ms;
		fg3a = fg3m + fg3ms;
		fta = ftm + ftms;
		
		fga = fg2a + fg3a;
		fgm = fg2m + fg3m;
		
		//percentages
		fg2p = StatsHelper.getPercent(fg2m, fg2a);
		fg3p = StatsHelper.getPercent(fg3m, fg3a);
		ftp = StatsHelper.getPercent(ftm, fta);
		fgp = StatsHelper.getPercent(fgm, fga);
		
		rebounds = reb_off + reb_def;
		points = getPointsFromFGS(fg2m, fg3m, ftm);*/

		
		
		
		
	}
	
	public void setPlusButton(Button button){
		plus1 = button;
		plus1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				changeStat(1);
			}
			
		});
	}
	
	public void setMinusButton(Button button){
		minus1 = button;
		minus1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				changeStat(-1);
			}
			
		});
	}

	public int getStatScreenType() {
		return statScreenType;
	}

	public void setStatScreenType(int statScreenType) {
		this.statScreenType = statScreenType;
	}

}
