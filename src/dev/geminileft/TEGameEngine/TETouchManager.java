package dev.geminileft.TEGameEngine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import android.util.Log;

public class TETouchManager extends TEComponentManager {
	private static TETouchManager mSharedInstance = null;
	private HashMap<Integer, TETouchComponent> mTouchComponents;
	
	public TETouchManager() {
		mTouchComponents = new HashMap<Integer, TETouchComponent>();
	}
	
	public static TETouchManager sharedManager() {
		if (mSharedInstance == null) {
			mSharedInstance = new TETouchManager();
		}
		return mSharedInstance;
	}

	@Override
	public void update() {
		Vector<TEComponent> components = getComponents();
		Iterator<TEComponent> itr = components.iterator();
		TETouchComponent component;
		TEInputManager inputManager = TEInputManager.sharedManager();
		HashMap<Integer, TEInputTouch> inputState = inputManager.getInputState();
		Collection<TEInputTouch> collection = inputState.values();
		Iterator<TEInputTouch> iterator = collection.iterator();
		TEInputTouch touch;
		while (iterator.hasNext()) {
			touch = iterator.next();
			if (touch.began()) {
				Log.v("info", "x: " + String.valueOf(touch.getStartPoint().x) + " y: " + String.valueOf(touch.getStartPoint().y));
				itr = components.iterator();
				 while(itr.hasNext()) {
					 component = (TETouchComponent)itr.next();
					 if (component.containsPoint(touch.getStartPoint())) {
						if (component.addTouch(touch) && !touch.ended()) {
							mTouchComponents.put(touch.getPointerId(), component);
							Log.v("info", "added touched object");
						}
					 }
			    }
			} else {
				if (mTouchComponents.containsKey(touch.getPointerId())) {
					component = mTouchComponents.get(touch.getPointerId());
					component.updateTouch(touch);
					mTouchComponents.put(touch.getPointerId(), component);
				}
			}
		}
		
	    while(itr.hasNext()) {
	    	component = (TETouchComponent)itr.next();
	    	component.update();
	    }
	}
}
