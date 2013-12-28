package com.gamelifestats.glselevate.frags;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.gamelifestats.glselevate.FragAvgTotals;
import com.gamelifestats.glselevate.GameManager;
import com.gamelifestats.glselevate.R;


public class Stats_career extends Fragment {
	
	View rootView;
	Context ctx;
	
	FragmentManager fm;
	int select_count = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootView =  inflater.inflate(R.layout.avg_totals, container, false);
		ctx = rootView.getContext();
		
		Button to_gm = (Button) rootView.findViewById(R.id.to_game_manager);
        to_gm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startActivity(new Intent(ctx, GameManager.class));
				
			}
        			
        });
        /*
        
        Button to_arc = (Button) rootView.findViewById(R.id.to_arc);
        to_arc.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				nextT();
			}
        			
        });*/
        
        Spinner sp = (Spinner) rootView.findViewById(R.id.spinner_arc);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ctx,
                R.array.archive_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				if ( select_count >= 1){
					nextT(pos);
				}
				select_count++;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
        	
        });
        
        
        fm = getFragmentManager();
        nextT(0);
        
        
        
        return rootView;
	}
	
	private void nextT(int clicked){
		FragAvgTotals frag = new FragAvgTotals();
		Bundle bundle = new Bundle();
		bundle.putInt("active_status", clicked);
		frag.setArguments(bundle);
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.frag_holder, frag);
		ft.commit();
	}

	
		
}
