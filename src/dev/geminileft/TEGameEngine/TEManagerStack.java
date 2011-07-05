package dev.geminileft.TEGameEngine;

import java.util.Iterator;
import java.util.Vector;

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
		Vector<TEComponent> components = getComponents();
		if (!components.isEmpty()) {
			Iterator<TEComponent> itr = components.iterator();
		    while(itr.hasNext()) {
		    	TEComponentStack component = (TEComponentStack)itr.next();
		    	component.update();
		    	if (component.isEvaluateReady()) {
		    		TEComponentStack dropStack = getDropStack(component);
		    		if (dropStack == null) {
		    			component.getParent().invokeEvent(Event.EVENT_REJECT_MOVE);
		    		} else {
			    		dropStack.pushStack(component);
		    		}
		    		component.resetEvaluate();
		    	}
		    }		
		}
	}
	
	private TEComponentStack getDropStack(TEComponentStack component) {
		TEComponentStack returnStack = null;
		Vector<TEComponent> components = getComponents();
		if (!components.isEmpty()) {
			Iterator<TEComponent> itr = components.iterator();
		    while(itr.hasNext()) {
		    	TEComponentStack stack = (TEComponentStack)itr.next();
		    	if ((stack.isTopStack()) && component.doesOverlap(stack) && stack.getRootStack().doesAccept(component)) {
		    		returnStack = stack;
		    		break;
		    	}
		    }
		}
		return returnStack;
	}
}
