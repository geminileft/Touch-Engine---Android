package dev.geminileft.TEGameEngine;

import java.util.Vector;

public abstract class TEManager {
	private Vector<TEComponent> mComponents;
	
	public TEManager() {
		mComponents = new Vector<TEComponent>();
	}
	public void addComponent(TEComponent component) {
		mComponents.add(component);
	}
	
	public abstract void update();
	
	public Vector<TEComponent> getComponents() {
		return mComponents;
	}
}
