package dev.geminileft.TEGameEngine;

import java.util.Iterator;
import java.util.Vector;

public class TERenderManager extends TEManager {

	private static TERenderManager mSharedInstance = null;
	
	public static TERenderManager sharedManager() {
		if (mSharedInstance == null) {
			mSharedInstance = new TERenderManager();
		}
		return mSharedInstance;
	}

	public TERenderManager() {
		super();
	}
	
	public void update() {
		Vector<TEComponent> components = getComponents();
		  Iterator<TEComponent> itr = components.iterator();
		    while(itr.hasNext()) {
		    	((TEComponent)itr).update();
		    	try {
		    	((TEDrawableComponent)itr).draw();
		    	}
		    	catch (Exception e) {
		    		int i = 0;
		    	}
		    }
	}
}
