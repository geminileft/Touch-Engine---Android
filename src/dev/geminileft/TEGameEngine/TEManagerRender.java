package dev.geminileft.TEGameEngine;

import android.util.Log;

public class TEManagerRender extends TEManagerComponent {
	private static TEManagerRender mSharedInstance = null;
	
	private TEComponent.EventListener mTouchStartedListener = new TEComponent.EventListener() {
		
		public void invoke() {
		}
	};
	
	public static TEManagerRender sharedManager() {
		if (mSharedInstance == null) {
			mSharedInstance = new TEManagerRender();
		}
		return mSharedInstance;
	}

	public TEManagerRender() {
		super();
	}
	
	public void update(long dt) {
		TEComponentContainer components = getComponents();
		final int size = components.size();
		TEComponentRender.mLastTexture = -1;
		TEComponentRender.mLastCropHash = -1;
		TEComponentRender.mLastPositionHash = -1;
		for(int i = 0; i < size; ++i) {
				TEComponentRender component = (TEComponentRender)components.get(i);
		    	component.update(dt);
		    	component.draw();
		    }
	}
	
	public TEComponent.EventListener getTouchStartedListener() {
		return mTouchStartedListener;
	}

	public final void moveComponentToTop(TEComponent component) {
		TEComponentContainer components = getComponents();
		if (components.remove(component)) {
			int size = components.size();
			addComponent(component, size);			
		} else {
			Log.v("TEManagerComponent.moveComponentToTop", "did not find component");
		}
	}
}
