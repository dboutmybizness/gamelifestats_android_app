package com.gamelifestats.glselevate;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.SparseArray;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.gamelifestats.glselevate.interfaces.ViewsHelper;

public class SetUpPageView {
	ViewsHelper VH = new ViewsHelper();
	public ArrayList<View> views_on_page = new ArrayList<View>();
	public SparseArray<String> db_field_map = new SparseArray<String>();
	public ArrayList <Integer> db_field_map_view_ids = new ArrayList<Integer>();
	public HashMap<String,String> fieldsHash = new HashMap<String,String>();
	public HashMap<String, String> Saveable_fieldHash = new HashMap<String,String>();
	
	
	public SetUpPageView(){
		
	}
	
	public void setOnCreateFieldsHash(HashMap<String,String> map){
		this.fieldsHash = map;
	}
	
	private void _addView(View v, String fieldname){
		views_on_page.add(v);
		if ( fieldname != null) {
			int view_id = v.getId();
			db_field_map.put(view_id, fieldname);
			db_field_map_view_ids.add(view_id);
		}
	}
	
	public void addView(SeekBar v, int max, final CallBackHelper callback, String fieldname){
		v.setMax(max);
		addView(v,callback,fieldname);
	}
	
	public void addView(SeekBar v, final CallBackHelper callback, String fieldname){
		_addView(v, fieldname);
		if (fieldname != null) {
			String fieldstr = this.fieldsHash.get(fieldname);
			if (fieldstr != null) {
				v.setProgress(Integer.parseInt(this.fieldsHash.get(fieldname)));
				callback.updateView(v.getProgress());
			}
		}
		
		v.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				callback.updateView(progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	public void loadSaveable(){
		for (int i=0; i < views_on_page.size(); i++){
			if ( db_field_map_view_ids.contains(views_on_page.get(i).getId())){
				int V_id = views_on_page.get(i).getId();
				String key = db_field_map.get(V_id);
				String val = VH.getText2Str(views_on_page.get(i));
				Saveable_fieldHash.put(key ,val);
			}
		}
	}
	

}
