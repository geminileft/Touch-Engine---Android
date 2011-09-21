package dev.geminileft.TEGameEngine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public final class TEManagerInput extends TEManagerComponent {
	private final static TEManagerInput mSharedInstance = new TEManagerInput();
	private HashMap<Integer, TEInputTouch> mTouches;
	private HashMap<Integer, TEInputKey> mKeys;
	private HashMap<Integer, TEInputKey> mKeyBuffer;
	
	public TEManagerInput() {
		mTouches = new HashMap<Integer, TEInputTouch>();
		mKeys = new HashMap<Integer, TEInputKey>();
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
		TEInputKey key = new TEInputKey(keyCode);
		mKeys.put(keyCode, key);
	}
	
	public void endKeyPress(int keyCode) {
		TEInputKey key = mKeys.get(keyCode);
		if (key != null) {
			key.endKey();
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

	public HashMap<Integer, TEInputKey> getKeyState() {
		return mKeyBuffer;
	}

	@Override
	public void moveComponentToTop(TEComponent component) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(long dt) {
		mKeyBuffer = new HashMap<Integer, TEInputKey>();
		Collection<TEInputKey> collection = mKeys.values();
		TEInputKey key;
		final Iterator<TEInputKey> iterator = collection.iterator();
		while (iterator.hasNext()) {
			key = iterator.next().copy();
			mKeyBuffer.put(key.getKeyCode(), key);
			if (key.didEnd()) {
				mKeys.remove(key.getKeyCode());
			} else {
				key.reset();
			}
		}
	}
}
