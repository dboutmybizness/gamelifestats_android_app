package com.gamelifestats.glselevate.interfaces;

import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class ViewsHelper {

	public String getText2Str(TextView tv) {
		return tv.getText().toString();
	}
	
	public String getText2Str(SeekBar sb) {
		return String.valueOf(sb.getProgress());
	}
	public String getText2Str(RatingBar rb){
		return String.valueOf(rb.getRating());
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
