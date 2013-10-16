package com.gamelifestats.glselevate;

import java.sql.SQLException;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gamelifestats.glselevate.models.MProfile;


public class DBAdapter {
	
	private String TABLE;
	private String[] getFields;
	private String ORDER_BY = "";
	

	static final String TAG = "DBAdapter";
	static final String DATABASE_NAME = "GLSDB";
	static final int DATABASE_VERSION = 4;
	
	public static String[] FIELD_TYPES_ARRAY = {
		"integer primary key autoincrement", "integer", "text", "float"
	};
	
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
			
			
			ModelBase[] tables = new ModelBase[]{
				new MProfile()
			};
			
			for (int i=0; i < tables.length; i++){
				ModelBase obj = tables[i];
				db.execSQL(obj.getCreateStatement());
				HashMap<String,String> map = obj.insertInitial();
				if ( map != null) {
					ContentValues args = new ContentValues();
					for (HashMap.Entry <String, String> entry : map.entrySet()) {
					    args.put(entry.getKey(), entry.getValue());
					}
					db.insert(obj.TABLE, null, args);
				}
			}

			
			
			String[] TABLES_TO_CREATE = new String[]{
					MAppMeta.CREATE_TABLE,
					MGames.CREATE_TABLE,
					MCareer.CREATE_TABLE,
					MScout.CREATE_TABLE
			};
			
			for ( int i = 0; i< TABLES_TO_CREATE.length; i++){
				db.execSQL(TABLES_TO_CREATE[i]);
			}
			
			try {
				createInitial(db);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
			Log.w(TAG, "Upgrading database from version "+ oldVersion + " to " + newVersion + ", which will destroy old data");
			if ( oldVersion == 1 ){
				//need to add fields
				
				db.execSQL("ALTER TABLE "+MGames.TABLE+" ADD COLUMN "+MGames.GAME_RESULT+" INTEGER");
				db.execSQL("ALTER TABLE "+MGames.TABLE+" ADD COLUMN "+MGames.GAME_TYPE+" INTEGER");
				db.execSQL("ALTER TABLE "+MGames.TABLE+" ADD COLUMN "+MGames.CREATED_TIME+" INTEGER");
				db.execSQL("UPDATE "+MGames.TABLE+" SET "+MGames.GAME_RESULT+" =0, "+MGames.GAME_TYPE+"=0, "+MGames.CREATED_TIME+"=0");
				
			}
			
			if ( oldVersion < 3){
				//add new fields to 
				db.execSQL("ALTER TABLE "+MCareer.TABLE+" ADD COLUMN "+MCareer.TFG2M+" INTEGER");
				db.execSQL("UPDATE "+MCareer.TABLE+" SET "+MCareer.TFG2M+"=0");
				
				db.execSQL("ALTER TABLE "+MCareer.TABLE+" ADD COLUMN "+MCareer.TFG2A+" INTEGER");
				db.execSQL("UPDATE "+MCareer.TABLE+" SET "+MCareer.TFG2A+"=0");
				
				db.execSQL("ALTER TABLE "+MCareer.TABLE+" ADD COLUMN "+MCareer.TFG3M+" INTEGER");
				db.execSQL("UPDATE "+MCareer.TABLE+" SET "+MCareer.TFG3M+"=0");
				
				db.execSQL("ALTER TABLE "+MCareer.TABLE+" ADD COLUMN "+MCareer.TFG3A+" INTEGER");
				db.execSQL("UPDATE "+MCareer.TABLE+" SET "+MCareer.TFG3A+"=0");
				
				db.execSQL("ALTER TABLE "+MCareer.TABLE+" ADD COLUMN "+MCareer.TFGM+" INTEGER");
				db.execSQL("UPDATE "+MCareer.TABLE+" SET "+MCareer.TFGM+"=0");
				
				db.execSQL("ALTER TABLE "+MCareer.TABLE+" ADD COLUMN "+MCareer.TFGA+" INTEGER");
				db.execSQL("UPDATE "+MCareer.TABLE+" SET "+MCareer.TFGA+"=0");
				
				db.execSQL("ALTER TABLE "+MCareer.TABLE+" ADD COLUMN "+MCareer.TFTM+" INTEGER");
				db.execSQL("UPDATE "+MCareer.TABLE+" SET "+MCareer.TFTM+"=0");
				
				db.execSQL("ALTER TABLE "+MCareer.TABLE+" ADD COLUMN "+MCareer.TFTA+" INTEGER");
				db.execSQL("UPDATE "+MCareer.TABLE+" SET "+MCareer.TFTA+"=0");
				
				db.execSQL("ALTER TABLE "+MGames.TABLE+" ADD COLUMN "+MGames.FGM+" INTEGER");
				db.execSQL("UPDATE "+MGames.TABLE+" SET "+MGames.FGM+"=0");
				
				db.execSQL("ALTER TABLE "+MGames.TABLE+" ADD COLUMN "+MGames.FGA+" INTEGER");
				db.execSQL("UPDATE "+MGames.TABLE+" SET "+MGames.FGA+"=0");
			}
			
			if ( oldVersion < 4){
				db.execSQL("ALTER TABLE "+MGames.TABLE+" ADD COLUMN "+MGames.ACTIVE_STATUS+" INTEGER");
				db.execSQL("UPDATE "+MGames.TABLE+" SET "+MGames.ACTIVE_STATUS+" = 1");
			}
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
		// insert app meta
		HashMap <String,String> meta = MAppMeta.initializeApp();
		ContentValues args = new ContentValues();
		for (HashMap.Entry <String, String> entry : meta.entrySet()) {
		    args.put(entry.getKey(), entry.getValue());
		}
		db.insert(MAppMeta.TABLE, null, args);
		
		// insert career
		ContentValues argus = new ContentValues();
		argus.put(MCareer.USERID, "1");
		db.insert(MCareer.TABLE, null, argus);
		
		// insert scout
		ContentValues s_args = new ContentValues();
		s_args.put(MScout.USERID, "1");
		s_args.put(MScout.HASPRO, "0");
		db.insert(MScout.TABLE, null, s_args);
		
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
	
	protected Boolean update(HashMap<String,String> map, String where) throws SQLException{

		ContentValues args = new ContentValues();
		for (HashMap.Entry <String, String> entry : map.entrySet()) {
		    args.put(entry.getKey(), entry.getValue());
		}
		return db.update(this.TABLE, args, where, null) > 0;

	}
	
	protected Boolean update(String tablename, HashMap<String,String> map, String where) throws SQLException{

		ContentValues args = new ContentValues();
		for (HashMap.Entry <String, String> entry : map.entrySet()) {
		    args.put(entry.getKey(), entry.getValue());
		}
		return db.update(tablename, args, where, null) > 0;

	}
	
	protected Cursor getRow(long rowId) throws SQLException{
		Cursor mCursor = db.query(true, this.TABLE, this.getFields, "_id="+rowId, null, null, null, null, null);
		return mCursor;
	}
	protected Cursor getRow(String row, long rowId) throws SQLException{
		Cursor mCursor = db.query(true, this.TABLE, this.getFields, row+"="+rowId, null, null, null, null, null);
		return mCursor;
	}
	protected Cursor getRow(String tablename,String[] getFields, String where) throws SQLException{
		Cursor mCursor = db.query(true, tablename, getFields, where, null, null, null, null, null);
		return mCursor;
	}
	

	protected Cursor getAllRowsWhere(String whereStatement){
		Cursor mCursor = db.query(true, this.TABLE, this.getFields, whereStatement, null,null,null,null,null);
		return mCursor;
	}
	
	protected Cursor getRowIds(){
		String[] cols = {"_id"};
		Cursor mCursor = db.query(true, this.TABLE, cols, null, null, null, null, this.ORDER_BY, null);
		return mCursor;
	}
	
	
}
