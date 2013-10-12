package com.gamelifestats.glselevate.frags;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gamelifestats.glselevate.R;

public class BballProfile_bio extends Fragment {
	View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootView = inflater.inflate(R.layout.profile_bio, container, false);
		//getParentContext = rootView.getContext();
		return rootView;
	}
}
