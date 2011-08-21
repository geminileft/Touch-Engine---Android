package dev.geminileft.TEGameEngine;

public final class TERandomizer {
	private int mNumber;
	
	public TERandomizer(long seed) {
		mNumber = (int)seed;
	}
	
	public int next() {
		mNumber ^= (mNumber << 13);
		mNumber ^= (mNumber >>> 17);
		mNumber ^= (mNumber << 5);
		return mNumber;
	}
}
