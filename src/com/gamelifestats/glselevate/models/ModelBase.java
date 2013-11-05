package com.gamelifestats.glselevate.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;

import com.gamelifestats.glselevate.interfaces.ModelSetup;
import com.gamelifestats.glselevate.interfaces.ModelTransactions;

public class ModelBase implements ModelSetup, ModelTransactions{

	protected String TABLE; 
	protected String[] FIELDS_ARRAY;
	protected ArrayList<String> FIELD_NAMES = new ArrayList<String>();
	protected ArrayList<String> FIELD_TYPES = new ArrayList<String>();
	protected String[] FIELDS_LIST_ARRAY;
	public HashMap<String,String> FIELD_VALUES = new HashMap<String,String>();
	
	public ModelBase(String[] dbconfig, String tablename){
		this.FIELDS_ARRAY = dbconfig;
		this.TABLE = tablename;
		parseFields();
		setFields();
	}
	
	@Override
	public String getCreateStatement() {
		String str = "";
		for ( int i = 0; i < FIELDS_ARRAY.length; i++){
			str += FIELD_NAMES.get(i) + " " + FIELD_TYPES.get(i);
			if ( i < (FIELDS_ARRAY.length - 1)) str += ", ";
		}
		return "create table "+this.TABLE+" (" + str + " )";
	}

	@Override
	public void parseFields(){
		for (int i = 0; i < FIELDS_ARRAY.length; i++){
			String[] str = FIELDS_ARRAY[i].split(":");
			String fname = str[0];
			int ftype = Integer.parseInt(str[1]);
			String default_value = null;
			
			this.FIELD_NAMES.add(fname);
			this.FIELD_TYPES.add(DBAdapter.FIELD_TYPES_ARRAY[ftype]);
			
			switch(ftype){
				case 0:
				case 1: default_value = "0";
					break;
				case 2: default_value = "";
			}
			this.FIELD_VALUES.put(fname, default_value);
		}
	}
	
	@Override
	public void setFields(){
		FIELDS_LIST_ARRAY = FIELD_NAMES.toArray(new String[FIELD_NAMES.size()]);
	}

	@Override
	public HashMap<String,String> insertInitial() {
		return null;
	}

	@Override
	public Boolean update(Context ctx, String where){
		if (FIELD_VALUES.size() < 1) return null;

		Boolean val = false;
		DBAdapter db = new DBAdapter(ctx);
		try {
			db.open();
			val = db.update(TABLE, FIELD_VALUES, where);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		return val;
	}

	@Override
	public Boolean readRow(Context ctx, String where) {
		Cursor cursor = null;
		Boolean has_row = false;
		DBAdapter db = new DBAdapter(ctx);
		
		try {
			db.open();
			cursor = db.getRow(TABLE, FIELDS_LIST_ARRAY, "_id=1");
			if ( cursor.getCount() > 0){
				has_row = true;
				cursor.moveToFirst();
				for ( int i=0; i < FIELDS_LIST_ARRAY.length; i++){
					FIELD_VALUES.put(FIELD_NAMES.get(i), cursor.getString(i));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		return has_row;
	}
	
	

}
