package com.gamelifestats.glselevate.interfaces;

import java.util.HashMap;


public interface ModelSetup {
	String getCreateStatement();
	void parseFields();
	void setFields();
	HashMap<String,String> insertInitial();
}
