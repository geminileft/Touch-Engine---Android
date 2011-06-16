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
		Image component;
		    while(itr.hasNext()) {
		    	component = (Image)itr.next();
		    	component.update();
		    	component.draw();
		    }
	}
}
