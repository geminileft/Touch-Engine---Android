package dev.geminileft.TEGameEngine;

import android.util.Log;

public class TEManagerRender extends TEManagerComponent {
	private static TEManagerRender mSharedInstance = null;
	
	private TEComponent.EventListener mTouchStartedListener = new TEComponent.EventListener() {
		
		@Override
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
	
	public void update() {
		TEComponentContainer components = getComponents();
		final int size = components.size();
		for(int i = 0; i < size; ++i) {
				TEComponentRender component = (TEComponentRender)components.get(i);
		    	component.update();
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
