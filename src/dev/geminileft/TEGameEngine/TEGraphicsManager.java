package dev.geminileft.TEGameEngine;

import javax.microedition.khronos.opengles.GL10;

public class TEGraphicsManager {
	private static GL10 mGL;
	
	public static void setGL(GL10 gl) {
		mGL = gl;
	}
	
	public static GL10 getGL() {
		return mGL;
	}
}
