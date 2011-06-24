package dev.geminileft.TEGameEngine;

import java.util.Iterator;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

public class TERenderManager extends TEComponentManager {

	private static TERenderManager mSharedInstance = null;
	
	public static TERenderManager sharedManager() {
		if (mSharedInstance == null) {
			mSharedInstance = new TERenderManager();
		}
		return mSharedInstance;
	}

	public TERenderManager() {
		super();
	}
	
	public void update() {
		GL10 gl = TEGraphicsManager.getGL();
		Vector<TEComponent> components = getComponents();
		Iterator<TEComponent> itr = components.iterator();
		TERenderComponent component;
		    while(itr.hasNext()) {
		    	component = (TERenderComponent)itr.next();
		    	component.update();
		    	component.draw(gl);
		    }
	}
}
