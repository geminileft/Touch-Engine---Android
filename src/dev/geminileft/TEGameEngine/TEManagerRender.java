package dev.geminileft.TEGameEngine;

import java.util.Iterator;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

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
		GL10 gl = TEManagerGraphics.getGL();
		Vector<TEComponent> components = getComponents();
		Iterator<TEComponent> itr = components.iterator();
		TEComponentRender component;
		    while(itr.hasNext()) {
		    	component = (TEComponentRender)itr.next();
		    	component.update();
		    	component.draw(gl);
		    }
	}
	
	public TEComponent.EventListener getTouchStartedListener() {
		return mTouchStartedListener;
	}

	public final void moveComponentToTop(TEComponent component) {
		Vector<TEComponent> components = getComponents();
		if (components.remove(component)) {
			int size = components.size();
			addComponent(component, size);			
		} else {
			Log.v("TEManagerComponent.moveComponentToTop", "did not find component");
		}
	}
}
