package dev.geminileft.TEGameEngine;

import java.util.Iterator;
import java.util.Vector;

public abstract class TEManagerComponent extends TEManager {
	private Vector<TEComponent> mComponents;
	
	public TEManagerComponent() {
		mComponents = new Vector<TEComponent>();
	}

	public void addComponent(TEComponent component) {
		mComponents.add(component);
	}
	
	public Vector<TEComponent> getComponents() {
		return mComponents;
	}

	public void update() {
		Vector<TEComponent> components = getComponents();
		Iterator<TEComponent> itr = components.iterator();
	    while(itr.hasNext()) {
	    	TEComponent component = itr.next();
	    	component.update();
	    }
	}
}
