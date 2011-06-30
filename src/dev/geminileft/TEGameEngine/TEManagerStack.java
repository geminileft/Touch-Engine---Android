package dev.geminileft.TEGameEngine;

public class TEManagerStack extends TEManagerComponent {
	private static TEManagerStack mSharedInstance = null;
	
	public static TEManagerStack sharedManager() {
		if (mSharedInstance == null) {
			mSharedInstance = new TEManagerStack();
		}
		return mSharedInstance;
	}

	public final void moveComponentToTop(TEComponent component) {}
	
}
