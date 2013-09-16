package com.gamelifestats.glselevate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class GLS_Act_main extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	ABSelect bar_select = new ABSelect(this, null);
    	Object cls = bar_select.trySelected(item);
    	if ( cls != null ){
    		startActivity(new Intent(this, (Class<?>) cls));
    		return true;
    	}
    	
    	if ( item.getTitle().equals("About") ){
    		AlertDialog.Builder ab = new AlertDialog.Builder(this);
    		ab.setMessage("About APP")
    		.setTitle("About GameLifeStats Elevate")
    		.setCancelable(true)
    		.setNegativeButton("OK", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
    			
    		});
    		ab.show();
    	}
    	
    	return super.onOptionsItemSelected(item);

    }
    
}
