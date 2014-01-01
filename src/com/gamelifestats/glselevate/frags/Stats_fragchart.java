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
        
        ArrayList<String[]> gsl = new ArrayList<String[]>();
        
        gsl.add(new String[]{"Games","",career.FIELD_VALUES.get("tgames")});
        gsl.add(new String[]{"Minutes", career.FIELD_VALUES.get("aminutes"), career.FIELD_VALUES.get("tminutes")});
        gsl.add(new String[]{"Points", career.FIELD_VALUES.get("apoints"), career.FIELD_VALUES.get("tpoints")});
        gsl.add(new String[]{"Rebounds", career.FIELD_VALUES.get("arebounds"), career.FIELD_VALUES.get("trebounds")});
        gsl.add(new String[]{"Off. Rebs", career.FIELD_VALUES.get("arebs_off"), career.FIELD_VALUES.get("trebs_off")});
        gsl.add(new String[]{"Def. Rebs", career.FIELD_VALUES.get("arebs_def"), career.FIELD_VALUES.get("trebs_def")});
        
        gsl.add(new String[]{"Assists", career.FIELD_VALUES.get("aassists"), career.FIELD_VALUES.get("tassists")});
        gsl.add(new String[]{"Steals", career.FIELD_VALUES.get("asteals"), career.FIELD_VALUES.get("tsteals")});
        gsl.add(new String[]{"Blocks", career.FIELD_VALUES.get("ablocks"), career.FIELD_VALUES.get("tblocks")});
        gsl.add(new String[]{"Turnovers", career.FIELD_VALUES.get("aturnovers"), career.FIELD_VALUES.get("tturnovers")});
        gsl.add(new String[]{"Fouls", career.FIELD_VALUES.get("afouls"), career.FIELD_VALUES.get("tfouls")});
        
        gsl.add(new String[]{"FG Made", career.FIELD_VALUES.get("afgm"), career.FIELD_VALUES.get("tfgm")});
        gsl.add(new String[]{"FG Att", career.FIELD_VALUES.get("afga"), career.FIELD_VALUES.get("tfga")});
        gsl.add(new String[]{"3's Made", career.FIELD_VALUES.get("afg3m"), career.FIELD_VALUES.get("tfg3m")});
        gsl.add(new String[]{"3's Att", career.FIELD_VALUES.get("afg3a"), career.FIELD_VALUES.get("tfg3a")});
        gsl.add(new String[]{"2's Made", career.FIELD_VALUES.get("afg2m"), career.FIELD_VALUES.get("tfg2m")});
        gsl.add(new String[]{"2's Att", career.FIELD_VALUES.get("afg2a"), career.FIELD_VALUES.get("tfg2a")});
        gsl.add(new String[]{"FT Made", career.FIELD_VALUES.get("aftm"), career.FIELD_VALUES.get("tftm")});
        gsl.add(new String[]{"FT Att", career.FIELD_VALUES.get("afta"), career.FIELD_VALUES.get("tfta")});
        
		StringBuilder sb = new StringBuilder();
		sb.append("<html><body><table style='color:white;'>");
		sb.append("<tr><th></th><th>AVG</th><th>Totals</th></tr>");
		
		for (int i=0; i<gsl.size(); i++){
			String[] s = gsl.get(i);
		    sb.append("<tr>" +
		    		"<td style='color: orange; padding-right: 7px;'>"+s[0]+"</td>" +
		    		"<td style='padding-right: 13px;'>"+s[1]+"</td>" +
		    		"<td>"+s[2]+"</td>" +
		    		"</tr>");
		}
		
		sb.append("</table></body></html>");
		
		webview.loadData(sb.toString(), "text/html", "utf-8");
		//webview.setBackgroundColor(0x00000000);
		//webview.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
	}

}
