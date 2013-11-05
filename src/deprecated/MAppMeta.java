package deprecated;

/*deprecated*/

import java.util.HashMap;

import android.content.Context;

import com.gamelifestats.glselevate.models.DBAdapter;

public class MAppMeta extends DBAdapter {
	static final String TABLE = "app_meta";

	static final String ID = "_id";
	static final String DB_VERSION = "db_version";
	static final String DOWNLOAD_DATE_ORIGINAL = "download_date_original";
	static final String DOWNLOAD_DATE_CURRENT = "download_date_latest";
	static final String APP_VERSION_ORIGINAL = "app_version_original";
	static final String APP_VERSION_CURRENT = "app_version_current";
	static final String APP_USER_ID = 		"app_user_id";
	static final String APP_USER_EMAIL =		"app_user_email";
	static final String APP_USER_HAS_SCOUT =	"app_user_has_scout";	
	static final String APP_USER_HAS_PRO_PIC =	"app_user_has_pro_pic";
	static final String APP_LAST_OPEN =		"app_last_open";
	static final String APP_LAST_SYNC =		"app_last_sync";
	static final String APP_LAST_SYNC_GAMES =		"app_last_sync_games";
	static final String APP_LAST_SYNC_SCOUT =		"app_last_sync_scout";


	static final String CREATE_TABLE =
		"create table "+TABLE+" (" +
		ID+" integer primary key, " +
		DB_VERSION+" text,"+
		DOWNLOAD_DATE_ORIGINAL+" integer,"+
		DOWNLOAD_DATE_CURRENT+" integer,"+
		APP_VERSION_ORIGINAL+" float," +
		APP_VERSION_CURRENT+" float," +
		APP_USER_ID+" integer,"+
		APP_USER_EMAIL+" text,"+
		APP_USER_HAS_SCOUT+" integer,"+
		APP_USER_HAS_PRO_PIC+" integer,"+
		APP_LAST_OPEN+" integer,"+
		APP_LAST_SYNC_GAMES+" integer,"+
		APP_LAST_SYNC_SCOUT+" integer"+
		")";	

	static final String[] getFields = {
        	ID,DB_VERSION,DOWNLOAD_DATE_ORIGINAL,DOWNLOAD_DATE_CURRENT,APP_VERSION_ORIGINAL,APP_VERSION_CURRENT,APP_USER_ID,APP_USER_EMAIL,APP_USER_HAS_SCOUT,APP_USER_HAS_PRO_PIC,APP_LAST_OPEN,APP_LAST_SYNC_GAMES,APP_LAST_SYNC_SCOUT
        };	
	
	
	
	public MAppMeta(Context ctx) {
		super(ctx);
		super.setTable(TABLE);
		super.setGetFields(getFields);
	}
	
	public static HashMap<String, String> initializeApp(){
		HashMap<String, String> m = new HashMap<String,String>();
		m.put(ID, "1");
		//m.put(DB_VERSION, StatsHelper.int2Str(DBAdapter.DATABASE_VERSION));
		m.put(APP_USER_ID, "1");
		return m;
		
	}
	
}
