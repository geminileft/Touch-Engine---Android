package dev.geminileft.TEGameEngine;

public class StackFreeCell extends TEComponentStack {

	@Override
	public void pushStack(TEComponentStack stack) {
		super.pushStack(stack);
		stack.getParent().position = new Point(getParent().position.x, getParent().position.y);
	}
	
	@Override
	public void popStack(TEComponentStack stack) {};
}
