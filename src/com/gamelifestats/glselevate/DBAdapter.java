package com.gamelifestats.glselevate;

import java.sql.SQLException;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {
	
	private String TABLE;
	private String[] getFields;
	
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
			//db.execSQL(DATABASE_CREATE);
			db.execSQL(Model_Games.CREATE_TABLE);	//games model
			db.execSQL(Model_Career.CREATE_TABLE);
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
	
	public void setTable(String table) {
		this.TABLE = table;
	}
	public void setGetFields(String[] s){
		this.getFields = s;
	}
	
	protected void create(HashMap<String, String> map) throws SQLException{
		open();
		ContentValues args = new ContentValues();
		for (HashMap.Entry <String, String> entry : map.entrySet()) {
		    args.put(entry.getKey(), entry.getValue());
		}
		db.insert(this.TABLE, null, args);
		close();
	}
	
	protected Cursor getRow(long rowId) throws SQLException{
		Cursor mCursor = db.query(true, this.TABLE, this.getFields, "_id="+rowId, null, null, null, null, null);
		return mCursor;
	}

	protected Cursor getAllRowsWhere(String whereStatement){
		Cursor mCursor = db.query(true, this.TABLE, this.getFields, whereStatement, null,null,null,null,null);
		return mCursor;
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
	
	
}
