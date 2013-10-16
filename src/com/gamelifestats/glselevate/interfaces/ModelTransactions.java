package com.gamelifestats.glselevate.interfaces;

import android.content.Context;

public interface ModelTransactions {
	Boolean readRow(Context ctx, String where);
	Boolean update(Context ctx, String where);
}
