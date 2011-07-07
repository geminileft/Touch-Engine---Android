package dev.geminileft.TEGameEngine;

import android.util.Log;

public class TouchDrag extends TEComponentTouch {
	private TEInputTouch mTouch;
	private Point mTouchOffset;
	private boolean mTouchValid;

	private TEComponent.EventListener mEventTouchAccept = new TEComponent.EventListener() {
		
		@Override
		public void invoke() {
			mTouchValid = true;
		}
	};
	
	private TEComponent.EventListener mEventTouchReject = new TEComponent.EventListener() {
		
		@Override
		public void invoke() {
			mTouchValid = false;
			mTouch = null;
			mTouchOffset = null;
			
		}
	};
	public TouchDrag() {
		super();
		this.addEventSubscription(Event.EVENT_TOUCH_ACCEPT, mEventTouchAccept);
		this.addEventSubscription(Event.EVENT_TOUCH_REJECT, mEventTouchReject);
	}

	@Override
	public boolean addTouch(TEInputTouch touch) {
		boolean added = false;
		if (mTouch == null) {
			added = true;
			mTouch = touch.copy();
			Point pt = getParent().position;
			mTouchOffset = new Point(pt.x - touch.getStartPoint().x, pt.y - touch.getStartPoint().y);
        	getParent().invokeEvent(TEComponent.Event.EVENT_TOUCH_STARTED);
			this.getManager().moveComponentToTop(this);
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
		if (mTouchValid && (mTouch != null)) {
	        float x = mTouch.getEndPoint().x + mTouchOffset.x;
	        float y = mTouch.getEndPoint().y + mTouchOffset.y;
	        if (mTouch.ended()) {
	        	getParent().invokeEvent(TEComponent.Event.EVENT_TOUCH_ENDED);
	            mTouch = null;
	            mTouchValid = false;
	        }
	        TEGameObject parent = getParent();
	        parent.position = new Point(x, y);
	    }
	}
}
