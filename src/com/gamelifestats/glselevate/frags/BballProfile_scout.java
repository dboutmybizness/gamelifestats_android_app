package com.gamelifestats.glselevate.frags;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gamelifestats.glselevate.R;

public class BballProfile_scout extends Fragment {
	View rootView;
	Context getParentContext;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootView = inflater.inflate(R.layout.profile_scout, container, false);
		getParentContext = rootView.getContext();
		
		return rootView;
	}
}
