package dev.geminileft.TEGameEngine;

import java.util.Iterator;
import java.util.LinkedList;

public class StackCard extends TEComponentStack {
	private boolean mMoving = false;
	
	private TEComponent.EventListener mTouchStartedListener = new TEComponent.EventListener() {
		
		@Override
		public void invoke() {
			mMoving = true;
			TEComponentStack parent = StackCard.this.getParentStack();
			if (parent != null) {
				parent.popStack(StackCard.this);				
			}
		}
	};
	
	private TEComponent.EventListener mTouchEndedListener = new TEComponent.EventListener() {
		
		@Override
		public void invoke() {
			mMoving = false;
		}
	};
	
	public StackCard() {
		super();
		addEventSubscription("touch started", mTouchStartedListener);
		addEventSubscription("touch ended", mTouchEndedListener);
	}
	
	@Override
	public void pushStack(TEComponentStack stack) {
		super.pushStack(stack);
		adjustStackPosition(stack);
	}

	@Override
	public void update() {
		super.update();
		LinkedList<TEComponentStack> stack = getStack();
		if (mMoving && !stack.isEmpty()) {
			Iterator<TEComponentStack> iterator = stack.iterator();
			while (iterator.hasNext()) {
				TEComponentStack nextStack = iterator.next();
				adjustStackPosition(nextStack);
			}
		}
	}
	
	private final void adjustStackPosition(TEComponentStack stack) {
		stack.getParent().position = new Point(getParent().position.x, getParent().position.y - 40);		
	}
	
	@Override
	public final void popStack(TEComponentStack stack) {
		LinkedList<TEComponentStack> componentStack = getStack();
		int index = 0;
		boolean found = false;
		if (!componentStack.isEmpty()) {
			Iterator<TEComponentStack> iterator = componentStack.iterator();
			while (iterator.hasNext()) {
				TEComponentStack localStack = iterator.next();
				if (found) {
					stack.pushStack(localStack);
					componentStack.remove(index);
				} else {
					found = (stack == localStack);
					++index;
				}
			}
			if (found) {
				componentStack.removeLast();
			}
		}
	}
	
}
