package com.gamelifestats.glselevate.helper;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class StatsHelper {
	StatsHelper(){
		
	}
	
	public static float getPercent(int a, int b){
		if ( a == 0 || b == 0 ) return 0;
		return (float)(a/b);
	}
	
	public static String int2Str(int num){
		return Integer.toString(num);
	}
	
	public static float divPerc(int l, int b){
		if ( l  < 1) return 0;
		Float f = ((l * 1.0f) / b);
		if( f == 100) return 100;
		return Float.valueOf(roundToOneDigit(f));
	}
	
	public static String divPerc(int l, int b, String nuller){
		return String.valueOf(divPerc(l,b));
	}
	
	public static float divPerc(int stat, String string) {
		return StatsHelper.divPerc(stat, Integer.parseInt(string));
	}
	
	public static String roundToOneDigit(float paramFloat) {
	    return String.format("%.1f%n", paramFloat);
	}
	
	public static String percentWithSign(int l, int b){
		if (l == 0 || b == 0 ) return "0%";
		if (l == b ) return "100%";
		Float f = (float) (l / (double)b);
		int i =  (int)Math.round(f * 100);
		return String.valueOf(i) + "%";

		
	}
	
	public static String float2Str(float f){
		//return String.valueOf(f);
		return roundToOneDigit(f).trim();
	}
	
	public static long getNowTime(){
		return System.currentTimeMillis() / 1000L;
	}
	
	public static String dateFromTime(String time){
		Date dt = new Date(Long.parseLong(time) * 1000);
		DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
		return df.format(dt);
	}
	
	public static String inches_to_feet(int inches){
		int feet = inches / 12;
		int rem_inches = inches % 12;
		String s_inches = (rem_inches < 1) ? "" : String.valueOf(rem_inches) + "\"";
		return String.valueOf(feet) + "\'" + s_inches;
		
	}
	
}
