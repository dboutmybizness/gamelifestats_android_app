package com.gamelifestats.glselevate;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GameManagerArrayAdapter extends ArrayAdapter<String>{

	private final Context context;
	private final ArrayList<String> labels;
	private final ArrayList<Boolean> bools;
	Resources res;

	
	
	public GameManagerArrayAdapter(Context context, ArrayList<String> labels, ArrayList<Boolean> bools) {
		super(context, R.layout.game_manager_list, labels);
		this.context = context;
		this.labels = labels;
		this.bools = bools;
		res = context.getResources();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.game_manager_list, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.gm_item);
		textView.setText(labels.get(position));
		
		
		
		int setColor = res.getColor(R.color.gls_blue);
		Boolean ck_bool = bools.get(position);
		if (!ck_bool) setColor = res.getColor(R.color.Gray);

		textView.setTextColor(setColor);
		
		
		return rowView;
		
		
	}
	
}
