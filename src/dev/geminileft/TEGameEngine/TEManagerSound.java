package dev.geminileft.TEGameEngine;

public class TEManagerSound extends TEManagerComponent {
	private static TEManagerSound mSharedInstance = null;
	
	public static TEManagerSound sharedManager() {
		if (mSharedInstance == null) {
			mSharedInstance = new TEManagerSound();
		}
		return mSharedInstance;
	}

	public final void moveComponentToTop(TEComponent component) {}

}
