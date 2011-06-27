package dev.geminileft.TEGameEngine;

public class MathUtils {
	final static int MAX_TEXTURE_SIZE = 1024;

	public static int closestPowerOf2(int n) {
		int c = 1;
		while (c < n && c < MAX_TEXTURE_SIZE)
			c <<= 1;
		return c;
	}
}
