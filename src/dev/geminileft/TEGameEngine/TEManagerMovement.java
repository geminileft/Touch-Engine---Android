package dev.geminileft.TEGameEngine;

public class TEManagerMovement extends TEManagerComponent {
	private static TEManagerMovement mSharedInstance = null;
	
	public static TEManagerMovement sharedManager() {
		if (mSharedInstance == null) {
			mSharedInstance = new TEManagerMovement();
		}
		return mSharedInstance;
	}

	@Override
	public void moveComponentToTop(TEComponent component) {
		// TODO Auto-generated method stub
		
	}

}
