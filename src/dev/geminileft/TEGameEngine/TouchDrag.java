package dev.geminileft.TEGameEngine;

import android.util.Log;

public class TouchDrag extends TEComponentTouch {
	private TEInputTouch mTouch = null;
	private Point mTouchOffset = null;

	public TouchDrag() {
		super();
	}

	@Override
	public boolean addTouch(TEInputTouch touch) {
		boolean added = false;
		if (mTouch == null) {
			Point pt = getParent().position;
			mTouchOffset = new Point(pt.x - touch.getStartPoint().x, pt.y - touch.getStartPoint().y);
			mTouch = touch.copy();
			added = true;
        	getParent().invokeEvent("touch started");
        	Log.v("TEComponentTouch.addTouch", "we are here");
		}
		return added;
	}

	@Override
	public boolean updateTouch(TEInputTouch touch) {
		boolean returnValue = false;
		if (mTouch != null) {
			mTouch = touch.copy();
			returnValue = true;
		}
		return returnValue;
	}

	@Override
	public void update() {
		if (mTouch != null) {
	        float x = mTouch.getEndPoint().x + mTouchOffset.x;
	        float y = mTouch.getEndPoint().y + mTouchOffset.y;
	        if (mTouch.ended()) {
	        	getParent().invokeEvent("touch ended");
	            mTouch = null;
	        }
	        TEGameObject parent = getParent();
	        parent.position = new Point(x, y);
	    }
	}
}
