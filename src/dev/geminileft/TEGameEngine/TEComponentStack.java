package dev.geminileft.TEGameEngine;

import java.util.LinkedList;

public abstract class TEComponentStack extends TEComponent {
	private LinkedList<TEComponentStack> mStack = new LinkedList<TEComponentStack>();;
	private TEComponentStack mParentStack = null;
	
	@Override
	public void update() {
		super.update();
	}
	
	public void pushStack(TEComponentStack stack) {
		mStack.add(stack);
		stack.getParent().position = new Point(getParent().position.x, getParent().position.y);
		stack.setParentStack(this);
	}
	
	public abstract void popStack(TEComponentStack stack);
	
	public final LinkedList<TEComponentStack> getStack() {
		return mStack;
	}
	
	public final void setParentStack(TEComponentStack stack) {
		mParentStack = stack;
	}
	
	public final TEComponentStack getParentStack() {
		return mParentStack;
	}
}
