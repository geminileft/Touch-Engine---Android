package dev.geminileft.TEGameEngine;

import dev.geminileft.TEGameEngine.TEComponent.Event;

public class TEManagerStack extends TEManagerComponent {
	private static TEManagerStack mSharedInstance = null;
	
	public static TEManagerStack sharedManager() {
		if (mSharedInstance == null) {
			mSharedInstance = new TEManagerStack();
		}
		return mSharedInstance;
	}

	@Override
	public final void moveComponentToTop(TEComponent component) {}

	@Override
	public void update() {
		TEComponentContainer components = getComponents();
		if (!components.isEmpty()) {
			final int size = components.size();
		    for(int i = 0;i < size;++i) {
		    	TEComponentStack component = (TEComponentStack)components.get(i);
		    	component.update();
		    	if (component.isEvaluateReady) {
		    		TEComponentStack dropStack = getDropStack(component);
		    		if (dropStack == null) {
		    			component.parent.invokeEvent(Event.EVENT_REJECT_MOVE);
		    		} else {
		    			component.parent.invokeEvent(Event.EVENT_ACCEPT_MOVE);
			    		dropStack.pushStack(component);
		    		}
		    		component.resetEvaluate();
		    	}
		    }		
		}
	}
	
	private TEComponentStack getDropStack(TEComponentStack component) {
		TEComponentStack returnStack = null;
		TEComponentContainer components = getComponents();
		if (!components.isEmpty()) {
			final int size = components.size();
		    for(int i = 0;i < size;++i) {
		    	TEComponentStack stack = (TEComponentStack)components.get(i);
		    	if ((stack.isTopStack()) && component.doesOverlap(stack) && stack.getRootStack().doesAccept(component)) {
		    		returnStack = stack;
		    		break;
		    	}
		    }
		}
		return returnStack;
	}
}
