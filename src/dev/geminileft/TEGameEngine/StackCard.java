package dev.geminileft.TEGameEngine;

import java.util.Iterator;
import java.util.LinkedList;

public class StackCard extends TEComponentStack {
	private boolean mMoving = false;
	
	private TEComponent.EventListener mTouchStartedListener = new TEComponent.EventListener() {
		
		@Override
		public void invoke() {
			mMoving = true;
			TEComponentStack component = StackCard.this;
			TEComponentStack parent = component.getParentStack();
			if (parent != null) {
				parent.popStack(component);				
			}
			component.getParent().invokeEvent(TEComponent.Event.EVENT_MOVE_TO_TOP);
			LinkedList<TEComponentStack> components = component.getStack();
			if (!components.isEmpty()) {
				Iterator<TEComponentStack> iterator = components.iterator();
				while (iterator.hasNext()) {
					iterator.next().getParent().invokeEvent(TEComponent.Event.EVENT_MOVE_TO_TOP);
				}
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
		addEventSubscription(TEComponent.Event.EVENT_TOUCH_STARTED, mTouchStartedListener);
		addEventSubscription(TEComponent.Event.EVENT_TOUCH_ENDED, mTouchEndedListener);
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
