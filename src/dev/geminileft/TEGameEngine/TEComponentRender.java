package dev.geminileft.TEGameEngine;

import javax.microedition.khronos.opengles.GL10;

public abstract class TEComponentRender extends TEComponent {
	public static int mLastTexture = 0;
	public static int mLastCrop[] = new int[4];
	public abstract void draw(GL10 gl);
}
