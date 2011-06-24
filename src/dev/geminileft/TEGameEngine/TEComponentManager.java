package dev.geminileft.TEGameEngine;

import java.util.Vector;

public abstract class TEComponentManager extends TEManager {
	private Vector<TEComponent> mComponents;
	
	public TEComponentManager() {
		mComponents = new Vector<TEComponent>();
	}

	public void addComponent(TEComponent component) {
		mComponents.add(component);
	}
	
	public Vector<TEComponent> getComponents() {
		return mComponents;
	}

}
