package dev.geminileft.TEGameEngine;


public abstract class TEComponentRender extends TEComponent {
	public static int mLastTexture;
	public static long mLastPositionHash;
	public static long mLastCropHash;
	public abstract void draw();
}
