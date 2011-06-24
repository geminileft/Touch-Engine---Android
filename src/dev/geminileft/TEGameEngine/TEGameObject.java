package dev.geminileft.TEGameEngine;

public class TEGameObject extends TEComponentManager {
	public Size size;
	public Point position;

	public TEGameObject() {
		super();
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

}
