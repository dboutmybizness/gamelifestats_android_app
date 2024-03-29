package deprecated;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;

import com.gamelifestats.glselevate.helper.StatsHelper;
import com.gamelifestats.glselevate.models.DBAdapter;

public class MGames extends DBAdapter {
	static final String TABLE = "games";

	static final String ID = "_id";
	static final String CREATED_TIME = "created_time";
	static final String GAME_RESULT = "game_result";
	static final String GAME_TYPE = "game_type";
	static final String USERID = "user_id";
	static final String MINUTES = "minutes";
	static final String POINTS = "points";
	static final String REBOUNDS = "rebounds";
	static final String ASSISTS = "assists";
	static final String STEALS = "steals";
	static final String BLOCKS = "blocks";
	static final String TURNOVERS = "turnovers";
	static final String FOULS = "fouls";
	static final String FG2M = "fg2m";
	static final String FG2A = "fg2a";
	static final String FG2p = "fg2p";
	static final String FG3M = "fg3m";
	static final String FG3A = "fg3a";
	static final String FG3p = "fg3p";
	static final String FGM = "fgm";
	static final String FGA = "fga";
	static final String FTM = "ftm";
	static final String FTA = "fta";
	static final String FTP = "ftp";
	static final String REB_OFF = "reb_off";
	static final String REB_DEF = "reb_def";
	static final String ACTIVE_STATUS = "active_status";
	
	public static final String[] wOrl= { "Loss", "Win" };
	
	static final String CREATE_TABLE = 
		"create table "+TABLE+" (" +
		ID+" integer primary key autoincrement, " +
		USERID+" integer," +
		MINUTES+" text, " +
		POINTS+" text," +
		REBOUNDS+" text," +
		REB_OFF+" text,"+
		REB_DEF+" text,"+
		ASSISTS+" text," +
		STEALS+" text," +
		BLOCKS+" text," +
		TURNOVERS+" text," +
		FOULS+" text," +
		
		FG2M+" text," +
		FG2A+" text," +
		
		FG3M+" text," +
		FG3A+" text," +
		
		FTM+" text," +
		FTA+" text," +
		
		GAME_RESULT +" text," +
		GAME_TYPE +" integer," +
		CREATED_TIME +" integer," +
		
		FGM+" text," +
		FGA+" text," +
		
		ACTIVE_STATUS +" integer"+
		
		")";
	
	static final String[] getFields = {
		ID,USERID,MINUTES,POINTS,REBOUNDS,
		REB_OFF,REB_DEF,ASSISTS,STEALS,BLOCKS,
		TURNOVERS,FOULS,FG2M, FG2A,FG3M,
		FG3A,FTM,FTA,GAME_RESULT,GAME_TYPE,
		CREATED_TIME,FGM,FGA,ACTIVE_STATUS
	};
	
	long created_time = 0;
	int game_type = 0;
	public int game_result = 0;
	int active_status = 0;
	
	
	int gameID = 0;
	int userID = 0;
	int minutes = 0;
	int points = 0;
	int rebounds = 0;
	public int reb_def = 0;
	public int reb_off = 0;
	public int assists = 0;
	public int steals = 0;
	public int blocks = 0;
	public int turnovers = 0;
	int fouls = 0;
	
	public int fg2m = 0;
	public int fg2ms = 0;
	int fg2a = 0;
	float fg2p = 0;
	public int fg3m = 0;
	public int fg3ms = 0;
	int fg3a = 0;
	float fg3p = 0;
	public int ftm = 0;
	public int ftms = 0;
	int fta = 0;
	float ftp = 0;
	int fgm = 0;
	int fga = 0;
	float fgp = 0;
	
	String s_minutes;
	public String s_points;
	public String s_rebounds;
	public String s_reb_off;
	String s_reb_def;
	public String s_assists;
	public String s_steals;
	public String s_blocks;
	public String s_turnovers;
	String s_fouls;
	
	String s_fg2m;
	String s_fg2ms;
	String s_fg2a;
	String s_fg2p;
	public String s_fg3m;
	String s_fg3ms;
	public String s_fg3a;
	String s_fg3p;
	public String s_ftm;
	String s_ftms;
	public String s_fta;
	String s_ftp;
	public String s_fgm;
	public String s_fga;
	String s_fgp;

	
	public MGames(Context context) {
		
		super(context);
		super.setTable(MGames.TABLE);
		super.setGetFields(MGames.getFields);
	}
	
	public void insertStats() throws SQLException{
		created_time = StatsHelper.getNowTime();
		active_status = 1;
		HashMap<String, String> m = new HashMap<String,String>();

		
				
		m.put(USERID, "1");
		m.put(MINUTES, s_minutes);
		m.put(POINTS, s_points);
		m.put(REBOUNDS, s_rebounds);
		m.put(REB_OFF, s_reb_off);
		m.put(REB_DEF, s_reb_def);
		m.put(ASSISTS, s_assists);
		m.put(STEALS, s_steals);
		m.put(BLOCKS, s_blocks);
		m.put(TURNOVERS, s_turnovers);
		m.put(FOULS, s_fouls);
		m.put(FG2M, s_fg2m);
		m.put(FG2A, s_fg2a);
		m.put(FG3M, s_fg3m);
		m.put(FG3A, s_fg3a);
		m.put(FGM, s_fgm);
		m.put(FGA, s_fga);
		m.put(FTM, s_ftm);
		m.put(FTA, s_fta);
		
		m.put(GAME_TYPE, String.valueOf(game_type));
		m.put(GAME_RESULT, String.valueOf(game_result));
		m.put(CREATED_TIME, String.valueOf(created_time));
		m.put(ACTIVE_STATUS, String.valueOf(active_status));
		super.create(m);

		//get all games
		super.open();
		//Cursor c = this.getAllGames(0);
		//MCareer career = new MCareer(super.context);
		//career.buildCareerObject(c);
		super.close();
		
		//career.saveCareerObject();
		
	}

	public Cursor getAllGames(int userID){
		return getAllRowsWhere("userID=1");
	}
	
	public Cursor getGame(int rowId) throws SQLException{
		Cursor mCursor = super.getRow(rowId);
		return mCursor;
	}
	
	public ArrayList<Integer> gameHashMap() throws SQLException{
		ArrayList<Integer> gamesList = new ArrayList<Integer>();
		super.open();
		Cursor c = getRowIds();
		
		if ( c.getCount() > 0){
			c.moveToFirst();
			do {
				gamesList.add(c.getInt(0));
			} while ( c.moveToNext());
		}
		
		super.close();
		

		return gamesList;
	}
	

	public void renderStats(){
		fg2a = fg2m + fg2ms;
		fg3a = fg3m + fg3ms;
		fta = ftm + ftms;
		
		fga = fg2a + fg3a;
		fgm = fg2m + fg3m;
		
		//percentages
		fg2p = StatsHelper.getPercent(fg2m, fg2a);
		fg3p = StatsHelper.getPercent(fg3m, fg3a);
		ftp = StatsHelper.getPercent(ftm, fta);
		fgp = StatsHelper.getPercent(fgm, fga);
		
		rebounds = reb_off + reb_def;
		points = getPointsFromFGS(fg2m, fg3m, ftm);
		
		buildStats();
	}
	private String mString(int i){
		return StatsHelper.int2Str(i);
	}
	
	private void buildStats(){
		s_minutes = 	mString(minutes);
		s_points = 		mString(points);
		s_rebounds = 	mString(rebounds);
		s_reb_off = 	mString(reb_off);
		s_reb_def = 	mString(reb_def);
		
		s_assists = 	mString(assists);
		s_steals =		mString(steals);
		s_blocks =		mString(blocks);
		s_turnovers =	mString(turnovers);
		s_fouls =		mString(fouls);
		
		s_fg2m =		mString(fg2m);
		s_fg2ms =		mString(fg2ms);
		s_fg2a =		mString(fg2a);
		//s_fg2p =		mString(fg2p);
		s_fg3m =		mString(fg3m);
		s_fg3ms =		mString(fg3ms);
		s_fg3a =		mString(fg3a);
		//s_fg3p;
		s_ftm =			mString(ftm);
		s_ftms =		mString(ftms);
		s_fta =			mString(fta);
		//s_ftp;
		s_fgm =			mString(fgm);
		s_fga =			mString(fga);
		//s_fgp;
		
	}
	
	private int getPointsFromFGS(int fg2m, int fg3m, int ftm){
		return ftm + (fg2m * 2) + (fg3m * 3);
	}
	
	public ArrayList<String> retArchive() throws SQLException{
		super.open();
		ArrayList<String> alist = new ArrayList<String>();
		
		Cursor c = getAllGames(1);
		if ( c.getCount() > 0){
			
			c.moveToFirst();
			do {
				String id = c.getString(0);
				String date = StatsHelper.dateFromTime(c.getString(20));
				String active = c.getString(23);
				alist.add(
						id +"_"+date+"_"+active
				);
				
			} while (c.moveToNext());
		}
		super.close();
		return alist;
	}
	
	

}
