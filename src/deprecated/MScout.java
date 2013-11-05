package deprecated;

import java.sql.SQLException;
import java.util.HashMap;

import com.gamelifestats.glselevate.models.DBAdapter;

import android.content.Context;
import android.database.Cursor;

public class MScout extends DBAdapter {

	static final String TABLE = "scout";

	static final String ID = "_id";
	static final String HASPRO = "has_profile";
	static final String USERID = "user_id";
	static final String NAME = "name";
	static final String NICKNAME = "nickname";
	static final String POSITION = "position";
	static final String JERSEY = "jersey";
	static final String HEIGHT = "height";
	static final String WEIGHT = "weight";
	static final String WINGSPAN = "wingspan";
	static final String VERTICAL_LEAP = "vertical_leap";
	static final String ACTIVE_STATUS = "active_status";

	static final String CREATE_TABLE =
		"create table "+TABLE+" (" +
		ID+" integer primary key autoincrement, " +
		USERID+" integer,"+
		HASPRO+" integer,"+
		NAME+" text,"+
		NICKNAME+" text,"+
		POSITION+" text,"+
		JERSEY+" integer," +
		HEIGHT+" text," +
		WEIGHT+" text,"+
		WINGSPAN+" text,"+
		VERTICAL_LEAP+" text,"+
		ACTIVE_STATUS+" text"+
		")";	

	static final String[] getFields = {
        	ID,USERID,HASPRO,NAME,NICKNAME,POSITION,JERSEY,HEIGHT,WEIGHT,WINGSPAN,VERTICAL_LEAP,ACTIVE_STATUS
        };
	
	int has_profile = 0;
	String name = "";
	String nickname = "";
	int position;
	int jersey = 0;
	
	
	
	public MScout(Context ctx) {
		super(ctx);
		super.setTable(TABLE);
		super.setGetFields(getFields);
	}
	
	
	public Boolean trySave(){
		int ck = 0;
		if (name.equals("")){ ck += 1; }
		if (nickname.equals("")){ ck += 1; }
		//if (position.equals("")){ ck += 1; }
		
		if ( ck > 0 ){ return false; }
		
		HashMap <String,String> map = new HashMap<String,String>();
		map.put(HASPRO, "1");
		map.put(NAME, name);
		map.put(NICKNAME, nickname);
		map.put(POSITION, String.valueOf(position));
		map.put(JERSEY, String.valueOf(jersey));
		
		
		Boolean results = false;
		
		try {
			super.open();
			results = super.update(map, USERID+"=1");
			super.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
		
	}
	
	public void getScout(){
		Cursor c = null;
		try {
			super.open();
			c = super.getRow(USERID,1);
			if ( c != null){
				c.moveToFirst();
				has_profile = c.getInt(2);
				name = c.getString(3);
				nickname = c.getString(4);
				position = c.getInt(5);
				jersey = c.getInt(6);
			}
			
			super.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
