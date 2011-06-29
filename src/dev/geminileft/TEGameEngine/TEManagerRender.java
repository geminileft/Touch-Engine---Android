package dev.geminileft.TEGameEngine;

import java.util.Iterator;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

public class TEManagerRender extends TEManagerComponent {

	private static TEManagerRender mSharedInstance = null;
	
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
}
