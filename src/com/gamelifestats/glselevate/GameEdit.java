package com.gamelifestats.glselevate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gamelifestats.glselevate.helper.SetUpStatView;
import com.gamelifestats.glselevate.helper.ViewsHelper;
import com.gamelifestats.glselevate.models.MGames;

public class GameEdit extends Activity {
	
	SetUpStatView Statview;
	MGames gs = new MGames();
	ViewsHelper VH = new ViewsHelper();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_edit);
		

		Statview = new SetUpStatView(this,0);
		
		Statview.setOnCreateFieldsHash(gs.FIELD_VALUES);
		
		Statview.setMinusButton((Button) findViewById(R.id.minus1));
		Statview.setPlusButton((Button) findViewById(R.id.plus1));
		Statview.setSwipe((FrameLayout) findViewById(R.id.swiper));
		
		
		
		Statview.addView("fg2m", R.id.press_fg2made, 
				new Integer[]{ R.id.lab_fgs, R.id.lab_fg2s, R.id.stat_fg2s, R.id.stat_fgs, R.id.dis_points });
		
		Statview.addView("fg2ms", R.id.press_fg2missed, 
				new Integer[]{ R.id.lab_fgs, R.id.lab_fg2s, R.id.stat_fg2s, R.id.stat_fgs });
		
		Statview.addView("fg3m", R.id.press_fg3made, 
				new Integer[]{ R.id.lab_fgs, R.id.lab_fg3s, R.id.stat_fg3s, R.id.stat_fgs, R.id.dis_points });
		
		Statview.addView("fg3ms", R.id.press_fg3missed, 
				new Integer[]{ R.id.lab_fgs, R.id.lab_fg3s, R.id.stat_fg3s, R.id.stat_fgs });
		
		
		Statview.addView("ftm", R.id.press_ftmade, 
				new Integer[]{ R.id.lab_fts, R.id.stat_fts, R.id.dis_points });
		
		Statview.addView("ftms", R.id.press_ftmissed, 
				new Integer[]{ R.id.lab_fts, R.id.stat_fts });
		
		Statview.addView("reb_def", R.id.press_rebdef, 
				new Integer[]{ R.id.lab_def, R.id.stat_def, R.id.dis_rebounds });
		
		Statview.addView("reb_off", R.id.press_reboff, 
				new Integer[]{ R.id.lab_off, R.id.stat_off, R.id.dis_rebounds });
		
		Statview.addView("assists", R.id.press_assists, 
				new Integer[]{ R.id.dis_assists });
		
		Statview.addView("steals", R.id.press_steals, 
				new Integer[]{ R.id.dis_steals });
		
		Statview.addView("blocks", R.id.press_blocks, 
				new Integer[]{ R.id.dis_blocks });
		
		Statview.addView("turnovers", R.id.press_turnovers, 
				new Integer[]{ R.id.lab_turnovers, R.id.stat_turnovers });
		
		Statview.addView("fouls", R.id.press_fouls, 
				new Integer[]{ R.id.lab_fouls, R.id.stat_fouls });
		LinearLayout lp = (LinearLayout) findViewById(R.id.metabar);
		lp.setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
				addMeta(v);
				return false;
			}
			
		});
	}
	
	private void show_error_dialog(){
		
		String mess = "The following errors occured:\n\n";
		for(int i = 0; i < gs.emessage.size(); i++){
			mess += "-"+gs.emessage.get(i)+"\n";
		}
		
		AlertDialog.Builder edialog = new AlertDialog.Builder(this);
		edialog.setTitle("Error")
		.setMessage(mess)
		.setCancelable(true)
		.setNegativeButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
		AlertDialog alertDialog = edialog.create();
        alertDialog.show();
	}
	
	public void saveStats(View v){

		if ( !gs.is_Saveable(Statview.fieldsHash)) {
			show_error_dialog();
			return;
		}
		
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
            .setMessage("Finished?")
            //.setCancelable(false)
            .setPositiveButton("OK, save game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        commitSave();
                        Intent i = new Intent(getBaseContext(), Stats.class);
                        i.putExtra("nav_action", 1);
                        startActivity(i);
                        finish();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            })
            .setNegativeButton("Back", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
	
	public void addMeta(View v){
		Intent i = new Intent(this,GameMeta.class);
		i.putExtra("minutes", gs.FIELD_VALUES.get("minutes"));
		i.putExtra("game_result", gs.FIELD_VALUES.get("game_result"));
		startActivityForResult(i, 1);
	}

	public void commitSave(){
		Statview.setSavable(new String[]{
			"points", "rebounds", "reb_off", "reb_def", "assists",
			"steals", "blocks", "turnovers", "fouls", "fg2m",
			"fg2a", "fg3m", "fg3a", "fgm", "fga",
			"ftm", "fta", "minutes", "game_result"
		});
		Statview.loadSaveable(1);
		gs.FIELD_VALUES = Statview.Saveable_fieldHash;
		if (gs.saveGame(this)){
			Toast.makeText(this, "game saved", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if ( requestCode == 1){
			if ( resultCode == RESULT_OK) {
				
				Statview.fieldsHash.put("minutes", data.getStringExtra("minutes"));
				
				int game_result = data.getIntExtra("game_result", 0);
				gs.FIELD_VALUES.put("game_result", String.valueOf(game_result));
				
				VH.rViews((TextView)findViewById(R.id.dis_minutes), gs.FIELD_VALUES.get("minutes"));
				VH.rViews((TextView)findViewById(R.id.dis_game_result), MGames.wOrl[game_result]);
			}
		}
	}
	

}
