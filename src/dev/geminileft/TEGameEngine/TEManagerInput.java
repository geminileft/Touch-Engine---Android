package dev.geminileft.TEGameEngine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import android.view.KeyEvent;

public final class TEManagerInput {
	private final static TEManagerInput mSharedInstance = new TEManagerInput();
	private HashMap<Integer, TEInputTouch> mTouches;
	private long mButtonsPressed;
	public static final long LEFT_BUTTON = 1;
	public static final long RIGHT_BUTTON = 2;
	
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

	public void beginKeyPress(int keyCode) {
		final long buttonCode = getButtonCode(keyCode);
		mButtonsPressed |= buttonCode;
	}
	
	public void endKeyPress(int keyCode) {
		final long buttonCode = getButtonCode(keyCode);
		mButtonsPressed ^= buttonCode;
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

	public long getButtonState() {
		return mButtonsPressed;
	}

	private long getButtonCode(int keyCode) {
		long value = 0;
		switch(keyCode) {
			case KeyEvent.KEYCODE_D:
				value = RIGHT_BUTTON;
				break;
			case KeyEvent.KEYCODE_A:
				value = LEFT_BUTTON;
				break;
		}
		return value;
	}
}
