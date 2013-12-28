package com.gamelifestats.glselevate;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gamelifestats.glselevate.helper.SetUpPageView;
import com.gamelifestats.glselevate.models.MCareer;

public class FragAvgTotals extends Fragment {

	MCareer career = new MCareer();
	SetUpPageView SPV;
	Context ctx;
	View rootView;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
		rootView = inflater.inflate(R.layout.avg_totals_frag, container, false);
		ctx = rootView.getContext();
		SPV = new SetUpPageView();
		
		Bundle bundle = this.getArguments();
		int active_status_req = bundle.getInt("active_status", 6);
		
		Boolean has_career;
		if ( active_status_req != 6){
			if ( active_status_req == 0 || active_status_req == 1){
				int actual_pass = (active_status_req == 1) ? 0 : 1;
				has_career = career.getCareer(ctx, actual_pass);
			} else {
				has_career = career.getCareer(ctx);
			}
			
			if ( has_career ){
				loadCareerView();
			}
		}
			
		return rootView;
	}
	
	private void loadCareerView(){
		career.getPercentages();
    	SPV.setOnCreateFieldsHash(career.FIELD_VALUES);

        SparseArray<String> pageview = new SparseArray<String>();
        pageview.put(R.id.tot_games, "tgames");

        pageview.put(R.id.tot_minutes, "tminutes");
        pageview.put(R.id.tot_points, "tpoints");
        pageview.put(R.id.tot_rebounds, "trebounds");
        pageview.put(R.id.tot_reb_off, "trebs_off");
        pageview.put(R.id.tot_reb_def, "trebs_def");
        pageview.put(R.id.tot_assists, "tassists");
        pageview.put(R.id.tot_steals, "tsteals");
        pageview.put(R.id.tot_blocks, "tblocks");
        pageview.put(R.id.tot_turnovers, "tturnovers");
        pageview.put(R.id.tot_fouls, "tfouls");

        pageview.put(R.id.avg_minutes, "aminutes");
        pageview.put(R.id.avg_points, "apoints");
        pageview.put(R.id.avg_rebounds, "arebounds");
        pageview.put(R.id.avg_reb_off, "arebs_off");
        pageview.put(R.id.avg_reb_def, "arebs_def");
        pageview.put(R.id.avg_assists, "aassists");
        pageview.put(R.id.avg_steals, "asteals");
        pageview.put(R.id.avg_blocks, "ablocks");
        pageview.put(R.id.avg_turnovers, "aturnovers");
        pageview.put(R.id.avg_fouls, "afouls");

        pageview.put(R.id.tot_fgm, "tfgm");
        pageview.put(R.id.tot_fg2m, "tfg2m");
        pageview.put(R.id.tot_fg3m, "tfg3m");
        pageview.put(R.id.tot_ftm, "tftm");
        pageview.put(R.id.tot_fga, "tfga");
        pageview.put(R.id.tot_fg2a, "tfg2a");
        pageview.put(R.id.tot_fg3a, "tfg3a");
        pageview.put(R.id.tot_fta, "tfta");
        
        pageview.put(R.id.avg_fgm, "afgm");
        pageview.put(R.id.avg_fg2m, "afg2m");
        pageview.put(R.id.avg_fg3m, "afg3m");
        pageview.put(R.id.avg_ftm, "aftm");
        pageview.put(R.id.avg_fga, "afga");
        pageview.put(R.id.avg_fg2a, "afg2a");
        pageview.put(R.id.avg_fg3a, "afg3a");
        pageview.put(R.id.avg_fta, "afta");
        
        pageview.put(R.id.dis_fgp, "fgp");
        pageview.put(R.id.dis_fg3p, "fg3p");
        pageview.put(R.id.dis_ftp, "ftp");

        
        for(int i = 0; i < pageview.size(); i++) {
        	int key = pageview.keyAt(i);
        	String val = pageview.get(key);
		    SPV.addView((TextView)rootView.findViewById(key), null, val);
		}
	}

}
