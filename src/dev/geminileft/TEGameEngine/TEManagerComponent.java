package dev.geminileft.TEGameEngine;

import java.util.Iterator;
import java.util.Vector;

public abstract class TEManagerComponent extends TEManager {
	private Vector<TEComponent> mComponents;
	
	public TEManagerComponent() {
		mComponents = new Vector<TEComponent>();
	}

	public void update() {
		Vector<TEComponent> components = getComponents();
		Iterator<TEComponent> itr = components.iterator();
	    while(itr.hasNext()) {
	    	TEComponent component = itr.next();
	    	component.update();
	    }
	}
	
	public void addComponent(TEComponent component) {
		addComponent(component, -1);
	}
	
	public void addComponent(TEComponent component, int index) {
		if (index == -1) {
			mComponents.add(component);
		} else {
			mComponents.add(index, component);
		}
		component.setManager(this);
	}
	
	public final Vector<TEComponent> getComponents() {
		return mComponents;
	}

	public abstract void moveComponentToTop(TEComponent component);
/*
	public final void moveComponentToTop(TEComponent component) {
		if (mComponents.remove(component)) {
			addComponent(component, 0);			
		} else {
			Log.v("TEManagerComponent.moveComponentToTop", "did not find component");
		}
	}
*/
}
