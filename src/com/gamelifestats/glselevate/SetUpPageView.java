package com.gamelifestats.glselevate;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.gamelifestats.glselevate.interfaces.ViewsHelper;

public class SetUpPageView {
	ViewsHelper VH = new ViewsHelper();
	public ArrayList<View> views_on_page = new ArrayList<View>();
	public HashMap<String,String> fieldsHash = new HashMap<String,String>();
	
	
	public SetUpPageView(){
		
	}
	
	public void setOnCreateFieldsHash(HashMap<String,String> map){
		this.fieldsHash = map;
	}
	
	private void _addView(View v){
		views_on_page.add(v);
	}
	
	public void addView(SeekBar v, int max, final CallBackHelper callback, String fieldname){
		v.setMax(max);
		addView(v,callback,fieldname);
	}
	
	public void addView(SeekBar v, final CallBackHelper callback, String fieldname){
		_addView(v);
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
	

}
