package dev.geminileft.TEGameEngine;

import android.content.Context;

public class TEStaticSettings {
	private static Context mContext;
	
	public static void setContext(Context context) {
		mContext = context;
	}
	
	public static Context getContext() {
		return mContext;
	}
	
}
