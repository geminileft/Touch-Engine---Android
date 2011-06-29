package dev.geminileft.TEGameEngine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public final class TEManagerInput {
	private final static TEManagerInput mSharedInstance = new TEManagerInput();
	private HashMap<Integer, TEInputTouch> mTouches;
	
	public TEManagerInput() {
		mTouches = new HashMap<Integer, TEInputTouch>();
	}

	public static TEManagerInput sharedManager() {
		return mSharedInstance;
	}

	public void beginTouch(TEInputTouch touch) {
		mTouches.put(touch.getPointerId(), touch);
	}

	public void moveTouch(TEInputTouch touch) {
		if (mTouches.containsKey(touch.getPointerId())) {
			mTouches.get(touch.getPointerId()).getEndPoint().update(touch.getEndPoint());
		}
	}

	public void endTouch(TEInputTouch touch) {
		if (mTouches.containsKey(touch.getPointerId())) {
			mTouches.get(touch.getPointerId()).endTouch(touch.getEndPoint());
		}
	}

	public 	HashMap<Integer, TEInputTouch> getInputState() {
		HashMap<Integer, TEInputTouch> touchState = new HashMap<Integer, TEInputTouch>();
		Collection<TEInputTouch> collection = mTouches.values();
		TEInputTouch addTouch;
		final Iterator<TEInputTouch> iterator = collection.iterator();
		while (iterator.hasNext()) {
			addTouch = iterator.next().copy();
			touchState.put(addTouch.getPointerId(), addTouch);
			if (addTouch.ended()) {
				mTouches.remove(addTouch.getPointerId());
			} else {
				mTouches.get(addTouch.getPointerId()).reset();
			}
		}
		return touchState;
	}
}
