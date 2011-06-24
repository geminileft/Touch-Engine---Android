package dev.geminileft.TEGameEngine;

import java.util.HashMap;

public class TEGameObject extends TEComponentManager {
	private HashMap<String, TEData> mAttributes;
	public Size size;
	public Point position;

	public TEGameObject() {
		super();
		mAttributes = new HashMap<String, TEData>();
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void addComponent(TEComponent component) {
		super.addComponent(component);
		component.setParent(this);
	}

/*
	public final void setAttribute(String attribute, TEData data) {
		mAttributes.put(attribute, data);
	}
	public final TEData getAttribute(String attribute) {
		return mAttributes.get(attribute);
	}
*/
}
