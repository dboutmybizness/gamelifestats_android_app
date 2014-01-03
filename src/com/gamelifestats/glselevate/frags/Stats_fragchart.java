package com.gamelifestats.glselevate.frags;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gamelifestats.glselevate.R;
import com.gamelifestats.glselevate.helper.SetUpPageView;
import com.gamelifestats.glselevate.models.MCareer;

public class Stats_fragchart extends Fragment {

	MCareer career = new MCareer();
	SetUpPageView SPV;
	Context ctx;
	View rootView;
	WebView webview;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
		rootView = inflater.inflate(R.layout.avg_totals_frag, container, false);
		ctx = rootView.getContext();
		SPV = new SetUpPageView();
		
		webview = (WebView) rootView.findViewById(R.id.stat_web);
		webview.setBackgroundColor(0x00000000);
		
		Bundle bundle = this.getArguments();
		int active_status_req = bundle.getInt("active_status", 6);
		
		Boolean has_career = false;
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
		
		if ( has_career == false ){
			((LinearLayout)webview.getParent()).removeView(webview);
			TextView tv = (TextView) rootView.findViewById(R.id.errText);
			tv.setText("Filter yeilds no stats");
		}
		
		

			
		return rootView;
	}
	
	private void loadCareerView(){
		career.getPercentages();
    	SPV.setOnCreateFieldsHash(career.FIELD_VALUES);

        SparseArray<String> pageview = new SparseArray<String>();

        
        pageview.put(R.id.dis_fgp, "fgp");
        pageview.put(R.id.dis_fg3p, "fg3p");
        pageview.put(R.id.dis_ftp, "ftp");
        
        for(int i = 0; i < pageview.size(); i++) {
        	int key = pageview.keyAt(i);
        	String val = pageview.get(key);
		    SPV.addView((TextView)rootView.findViewById(key), null, val);
		}
        
        //
        TextView fgma = (TextView) rootView.findViewById(R.id.ss_fgma);
        fgma.setText("("+career.FIELD_VALUES.get("tfgm")+" - "+career.FIELD_VALUES.get("tfga")+")");
        TextView fg3ma = (TextView) rootView.findViewById(R.id.ss_fg3ma);
        fg3ma.setText("("+career.FIELD_VALUES.get("tfg3m")+" - "+career.FIELD_VALUES.get("tfg3a")+")");
        TextView ftma = (TextView) rootView.findViewById(R.id.ss_ftma);
        ftma.setText("("+career.FIELD_VALUES.get("tftm")+" - "+career.FIELD_VALUES.get("tfta")+")");
        
        
        ArrayList<String[]> gsl = new ArrayList<String[]>();
        
        gsl.add(new String[]{"Games","",career.FIELD_VALUES.get("tgames")});
        gsl.add(new String[]{"Minutes", career.FIELD_VALUES.get("aminutes"), career.FIELD_VALUES.get("tminutes"), career.FIELD_VALUES.get("hminutes")});
        gsl.add(new String[]{"Points", career.FIELD_VALUES.get("apoints"), career.FIELD_VALUES.get("tpoints"), career.FIELD_VALUES.get("hpoints")});
        gsl.add(new String[]{"Rebounds", career.FIELD_VALUES.get("arebounds"), career.FIELD_VALUES.get("trebounds"), career.FIELD_VALUES.get("hrebounds")});
        gsl.add(new String[]{"Off. Rebs", career.FIELD_VALUES.get("arebs_off"), career.FIELD_VALUES.get("trebs_off"), career.FIELD_VALUES.get("hreb_off")});
        gsl.add(new String[]{"Def. Rebs", career.FIELD_VALUES.get("arebs_def"), career.FIELD_VALUES.get("trebs_def"), career.FIELD_VALUES.get("hreb_def")});
        
        gsl.add(new String[]{"Assists", career.FIELD_VALUES.get("aassists"), career.FIELD_VALUES.get("tassists"), career.FIELD_VALUES.get("hassists")});
        gsl.add(new String[]{"Steals", career.FIELD_VALUES.get("asteals"), career.FIELD_VALUES.get("tsteals"), career.FIELD_VALUES.get("hsteals")});
        gsl.add(new String[]{"Blocks", career.FIELD_VALUES.get("ablocks"), career.FIELD_VALUES.get("tblocks"), career.FIELD_VALUES.get("hblocks")});
        gsl.add(new String[]{"Turnovers", career.FIELD_VALUES.get("aturnovers"), career.FIELD_VALUES.get("tturnovers"), career.FIELD_VALUES.get("hturnovers")});
        gsl.add(new String[]{"Fouls", career.FIELD_VALUES.get("afouls"), career.FIELD_VALUES.get("tfouls"), career.FIELD_VALUES.get("hfouls")});
        
        gsl.add(new String[]{"FG Made", career.FIELD_VALUES.get("afgm"), career.FIELD_VALUES.get("tfgm"), career.FIELD_VALUES.get("hfgm")});
        gsl.add(new String[]{"FG Att", career.FIELD_VALUES.get("afga"), career.FIELD_VALUES.get("tfga"), career.FIELD_VALUES.get("hfga")});
        gsl.add(new String[]{"3's Made", career.FIELD_VALUES.get("afg3m"), career.FIELD_VALUES.get("tfg3m"), career.FIELD_VALUES.get("hfg3m")});
        gsl.add(new String[]{"3's Att", career.FIELD_VALUES.get("afg3a"), career.FIELD_VALUES.get("tfg3a"), career.FIELD_VALUES.get("hfg3a")});
        gsl.add(new String[]{"2's Made", career.FIELD_VALUES.get("afg2m"), career.FIELD_VALUES.get("tfg2m"), career.FIELD_VALUES.get("hfg2m")});
        gsl.add(new String[]{"2's Att", career.FIELD_VALUES.get("afg2a"), career.FIELD_VALUES.get("tfg2a"), career.FIELD_VALUES.get("hfg2a")});
        gsl.add(new String[]{"FT Made", career.FIELD_VALUES.get("aftm"), career.FIELD_VALUES.get("tftm"), career.FIELD_VALUES.get("hftm")});
        gsl.add(new String[]{"FT Att", career.FIELD_VALUES.get("afta"), career.FIELD_VALUES.get("tfta"), career.FIELD_VALUES.get("hfta")});
        
		StringBuilder sb = new StringBuilder();
		sb.append("<html><body><table style='color:white;'>");
		sb.append("<tr><th></th><th>AVG</th><th>Totals</th><th>Hi's</th></tr>");
		
		for (int i=0; i<gsl.size(); i++){
			String[] s = gsl.get(i);
		    sb.append("<tr>" +
		    		"<td style='color: orange; padding-right: 7px;'>"+s[0]+"</td>" +
		    		"<td style='padding-right: 13px;'>"+s[1]+"</td>" +
		    		"<td>"+s[2]+"</td>");
		    try{
		    	sb.append("<td>"+s[3]+"</td>");
		    } catch (Exception e){
		    
		    }
		    sb.append("</tr>");
		}
		
		sb.append("</table></body></html>");
		
		webview.loadData(sb.toString(), "text/html", "utf-8");
		//webview.setBackgroundColor(0x00000000);
		//webview.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
	}

}
