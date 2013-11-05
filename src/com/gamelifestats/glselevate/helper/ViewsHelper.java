package com.gamelifestats.glselevate.helper;

import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class ViewsHelper {

	public String getText2Str(View v){
		if (v instanceof TextView) {
			return getText2Str((TextView) v);
		} else if(v instanceof EditText){
			return getText2Str((EditText) v);
		} else if (v instanceof SeekBar){
			return getText2Str((SeekBar) v);
		} else if (v instanceof RatingBar){
			return getText2Str((RatingBar) v);
		}
		return null;
	}
	
	public String getText2Str(TextView tv) {
		return tv.getText().toString();
	}
	public String getText2Str(EditText et) {
		return et.getText().toString();
	}
	
	public String getText2Str(SeekBar sb) {
		return String.valueOf(sb.getProgress());
	}
	public String getText2Str(RatingBar rb){
		return String.valueOf(rb.getRating());
	}
	
	public void rViews(View v, int i){
		rViews(v, String.valueOf(i));
	}
	
	public void rViews(View v, String str){
		if (v instanceof TextView) {
			rViews((TextView) v, str);
		} else if (v instanceof EditText){
			rViews((EditText) v, str);
		} else if (v instanceof SeekBar){
			rViews((SeekBar) v, str);
		} else if (v instanceof RatingBar){
			rViews((RatingBar) v, str);
		}

	}
	
	
	public void rViews(RatingBar rb, String str){
		if ( str != null ) rb.setRating(Float.parseFloat(str));
	}
	public void rViews(EditText editText, String str){
		if ( str!=null ) editText.setText(str);
	}
	public void rViews(SeekBar seekBar, String str){
		int i = (str == null)? 0 : Integer.parseInt(str);
		seekBar.setProgress(i);
	}
	public void rViews(TextView textView, String str){
		textView.setText(str);
	}
	
	public void rViews(TextView textView, int i){
		rViews(textView, String.valueOf(i));
	}
	
	


}
