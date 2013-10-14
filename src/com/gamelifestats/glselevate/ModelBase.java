package com.gamelifestats.glselevate;

import java.util.ArrayList;
import java.util.HashMap;

import com.gamelifestats.glselevate.interfaces.ModelSetup;

public class ModelBase implements ModelSetup{

	protected String TABLE; 
	protected String[] FIELDS_ARRAY;
	protected ArrayList<String> FIELD_NAMES = new ArrayList<String>();
	protected ArrayList<String> FIELD_TYPES = new ArrayList<String>();
	
	public ModelBase(String[] dbconfig, String tablename){
		this.FIELDS_ARRAY = dbconfig;
		this.TABLE = tablename;
		parseFields();
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
			this.FIELD_NAMES.add(str[0]);
			this.FIELD_TYPES.add(DBAdapter.FIELD_TYPES_ARRAY[Integer.parseInt(str[1])]);
		}
	}
	
	@Override
	public String[] setFields(){
		return FIELD_NAMES.toArray(new String[FIELD_NAMES.size()]);
	}

	@Override
	public HashMap<String,String> insertInitial() {
		return null;
	}

}
