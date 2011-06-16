package dev.geminileft.TEGameEngine;

import javax.microedition.khronos.opengles.GL10;
import android.content.Context;

public abstract class TEEngine {
	private GL10 mGL;
	private Context mContext;

	public TEEngine(Context context) {
		mContext = context;
	}
	
	public abstract void start();
	public abstract void run();
	
	public void setGraphicManager(GL10 gl) {
		mGL = gl;
	}
	
	public Context getContext() {
		return mContext;
	}
	
	public GL10 getGraphicsManager() {
		return mGL;
	}

}
