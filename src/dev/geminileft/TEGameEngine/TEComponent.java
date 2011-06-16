package dev.geminileft.TEGameEngine;

public abstract class TEComponent {
	private TEGameObject mParent;

	public void update() {}
	public final void setParent(TEGameObject parent) {
		mParent = parent;
	}
	
	public final TEGameObject getParent() {
		return mParent;
	}
}
