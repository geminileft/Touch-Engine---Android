package dev.geminileft.TEGameEngine;

import javax.microedition.khronos.opengles.GL10;

public class TEGraphicsManager {
	private static GL10 mGL;
	private static int mWidth;
	private static int mHeight;
	
	public static void setGL(GL10 gl) {
		mGL = gl;
	}
	
	public static GL10 getGL() {
		return mGL;
	}
	
	public static void setScreenSize(int width, int height) {
		mWidth = width;
		mHeight = height;
	}
	
	public static Size getScreenSize() {
		return new Size(mWidth, mHeight);
	}
}
