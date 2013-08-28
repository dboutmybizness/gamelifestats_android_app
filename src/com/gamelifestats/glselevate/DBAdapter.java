package com.gamelifestats.glselevate;

import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {
	
	static final String KEY_ROWID = "_id";
	static final String KEY_NAME = "name";
	static final String KEY_POSITION = "position";
	static final String KEY_HAS_SCOUT = "has_scout";
	static final String TAG = "DBAdapter";
	
	static final String DATABASE_NAME = "GLSDB";
	static final String DATABASE_TABLE = "scout";
	static final int DATABASE_VERSION = 1;
	
	static final String DATABASE_CREATE = 
			"create table scout ("+
			"_id integer primary key autoincrement, "+
			"name text not null, "+
			"position text not null, "+
			"has_scout integer" +
			")";
	
	final Context context;
	
	DatabaseHelper DBHelper;
	SQLiteDatabase db;
	
	public DBAdapter(Context ctx){
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	
	
	private static class DatabaseHelper extends SQLiteOpenHelper{
		DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db){
			db.execSQL(DATABASE_CREATE);
			db.execSQL(Games.CREATE_DB);
			createInitial(db);
			
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
			Log.w(TAG, "Upgrading database from version "+ oldVersion + " to " + newVersion + ", which will destroy old data");
			db.execSQL("DROP TABLE IF EXISTS scout");
			onCreate(db);
		}
	}
	
	public DBAdapter open() throws SQLException{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		DBHelper.close();
	}
	
	private static void createInitial(SQLiteDatabase db){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_HAS_SCOUT, 0);
		initialValues.put(KEY_ROWID, 1);
		initialValues.put(KEY_NAME, "my name");
		initialValues.put(KEY_POSITION, "Guard");
		db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	//---below is public quering---
	public boolean updateScout(String name, String position){
		ContentValues args = new ContentValues();
		args.put(KEY_NAME, name);
		args.put(KEY_POSITION, position);
		args.put(KEY_HAS_SCOUT, 1);
		return db.update(DATABASE_TABLE, args, KEY_ROWID+"="+1, null) > 0;
	}
	public Cursor getScout() throws SQLException{
		Cursor mCursor = 
				db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_POSITION}, KEY_ROWID +"="+1, null, null, null, null, null);
		if ( mCursor != null){
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	public class Games {
		static final String TABLE = "games";
		static final String ID = "_id";
		static final String MINUTES = "minutes";
		static final String POINTS = "points";
		static final String REBOUNDS = "rebounds";
		static final String ASSISTS = "assists";
		static final String STEALS = "steals";
		static final String BLOCKS = "blocks";
		static final String FOULS = "fouls";
		
		static final String CREATE_DB = 
			"create table"+ TABLE +" ("+
			ID+" integer primary key autoincrement, "+
			MINUTES+" text, "+
			POINTS+" text,"+
			REBOUNDS+" text,"+
			ASSISTS+" text,"+
			STEALS+" text,"+
			BLOCKS+" text,"+
			FOULS+" text"+
			")";

		String minutes, points, rebounds, assists, steals, blocks, fouls;
		
		public void insertStats(){
			ContentValues args = new ContentValues();
			args.put(MINUTES, minutes);
			args.put(POINTS, points);
			db.insert(TABLE, null, args);
		}
		
	}
	
}