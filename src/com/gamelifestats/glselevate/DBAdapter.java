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
	

	static final String TAG = "DBAdapter";
	static final String DATABASE_NAME = "GLSDB";
	static final int DATABASE_VERSION = 1;
	

	
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
			
			String[] TABLES_TO_CREATE = new String[]{
					MAppMeta.CREATE_TABLE,
					MGames.CREATE_TABLE,
					MCareer.CREATE_TABLE
			};
			
			for ( int i = 0; i< TABLES_TO_CREATE.length; i++){
				db.execSQL(TABLES_TO_CREATE[i]);
			}
			
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
	
	private static void createInitial(SQLiteDatabase db) throws SQLException{
		HashMap <String,String> meta = MAppMeta.initializeApp();
		DBAdapter dba = new DBAdapter();
		create(meta);
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
