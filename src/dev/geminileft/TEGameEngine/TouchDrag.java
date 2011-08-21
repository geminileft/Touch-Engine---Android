package dev.geminileft.TEGameEngine;

import android.os.SystemClock;
import android.util.Log;

public class TouchDrag extends TEComponentTouch {
	private TEInputTouch mTouch;
	private TEPoint mTouchOffset;
	private boolean mTouchValid;
	private TETouchHandler mTouchHandler = new TETouchHandler();
	//private int mTapCount;
	public enum TouchAction {
		NONE
		, TAP
		, DOUBLE_TAP
	}

	private final class TETouchHandler {
		private long mStartTime;
		private int mTapCount;
		private long mLastUpTime;
		static final int TAP_DOWN_THRESHOLD_MS = 130;
		static final int TAP_UP_THRESHOLD_MS = 200;
		//private TouchAction mLastAction;
		
		private final void startTouch(TEInputTouch touch) {
			mStartTime = SystemClock.uptimeMillis();
			final long elapsedTime = mStartTime - mLastUpTime;
			if (elapsedTime > TAP_UP_THRESHOLD_MS) {
				Log.v("TouchDrag.startTouch", "failed tap");
				mTapCount = 0;
			}
		}
		
		private final void endTouch(TEInputTouch touch) {
			mLastUpTime = SystemClock.uptimeMillis();
			final long elapsedTime = mLastUpTime - mStartTime;
			Log.v("TouchDrag.endTouch", "ended");
			if (elapsedTime < TAP_DOWN_THRESHOLD_MS) {
				++mTapCount;
				switch(mTapCount) {
				case 1:
					//mLastAction = TouchAction.TAP;
					break;
				case 2:
					//mLastAction = TouchAction.DOUBLE_TAP;
					parent.invokeEvent(Event.EVENT_MOVE_TO_FOUNDATION);
					break;
				}
			} else {
				mTapCount = 0;
				//mLastAction = TouchAction.NONE;
			}
		}
		
		//private final long splitTime() {
		//	return SystemClock.uptimeMillis() - mStartTime;
		//}
	}
	
	private TEComponent.EventListener mEventTouchAccept = new TEComponent.EventListener() {
		
		public void invoke() {
			mTouchValid = true;
		}
	};
	
	private TEComponent.EventListener mEventTouchReject = new TEComponent.EventListener() {
		
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
			mTouchHandler.startTouch(touch);
			added = true;
			mTouch = touch.copy();
			TEPoint pt = parent.position;
			mTouchOffset = new TEPoint(pt.x - touch.getStartPoint().x, pt.y - touch.getStartPoint().y);
        	parent.invokeEvent(TEComponent.Event.EVENT_TOUCH_STARTED);
			this.getManager().moveComponentToTop(this);
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
	        	mTouchHandler.endTouch(mTouch);
	        	parent.invokeEvent(TEComponent.Event.EVENT_TOUCH_ENDED);
	            mTouch = null;
	            mTouchValid = false;
	        }
	        parent.position = new TEPoint(x, y);
	    }
	}
}
