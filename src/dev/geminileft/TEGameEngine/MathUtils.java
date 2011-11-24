package dev.geminileft.TEGameEngine;

public class MathUtils {
	final static int MAX_TEXTURE_SIZE = 1024;

	public static int closestPowerOf2(int n) {
		int c = 1;
		while (c < n && c < MAX_TEXTURE_SIZE)
			c <<= 1;
		return c;
	}
	
	public static int framesToMillis(int framesPerSecond, int frameCount) {
		return (int)(((float)1000 / framesPerSecond) * frameCount);
	}
	
	public static double easeInOut(float x) {
		return (1- Math.sin(Math.PI/2+x*Math.PI))/2;		
	}
}
