package dev.geminileft.TEGameEngine;

public abstract class TEComponentStack extends TEComponent {
	private TEComponentStack mChildStack = null;
	private TEComponentStack mParentStack = null;
	private boolean mTopStack = true;
	private boolean mEvaluateReady = false;

	public void pushStack(TEComponentStack stack) {
		mChildStack = stack;
		stack.setParentStack(this);
		mTopStack = false;
		adjustStackPositions();
	}
	
	public final void popStack(TEComponentStack stack) {
		if (stack == mChildStack) {
			stack.setParentStack(null);
			mChildStack = null;
			mTopStack = true;
		}
	}
	
	public final void setParentStack(TEComponentStack stack) {
		mParentStack = stack;
	}
	
	public final TEComponentStack getParentStack() {
		return mParentStack;
	}
	
	public final boolean doesOverlap(TEComponentStack stack) {
		boolean returnValue = false;
		if (!isParentOf(stack)) {
			TEGameObject parent = getParent();
			TEUtilRect parentRect = new TEUtilRect(parent.position, parent.size);
			TEGameObject stackParent = stack.getParent();
			TEUtilRect stackRect = new TEUtilRect(stackParent.position, stackParent.size);
			returnValue = parentRect.overlaps(stackRect);
		}
		return returnValue;
	}
	
	public final boolean isTopStack() {
		return mTopStack;
	}
	
	public final boolean isEvaluateReady() {
		return mEvaluateReady;
	}
	
	public abstract int getStackOffset();
	
	public final void adjustStackPositions() {
		TEComponentStack rootStack = getRootStack();
		int offset = rootStack.getStackOffset();
		TEGameObject rootParent = rootStack.getParent();
		Point position = new Point(rootParent.position.x, rootParent.position.y);
		while (rootStack.getChildStack() != null) {
			position.y -= offset;
			rootStack = rootStack.getChildStack();
			rootStack.getParent().position = new Point(position.x, position.y);
			offset = rootStack.getStackOffset();
		}
	}
	
	public TEComponentStack getRootStack() {
		TEComponentStack rootStack;
		if (mParentStack != null) {
			rootStack = mParentStack;
			while (rootStack.getParentStack() != null) {
				rootStack = rootStack.getParentStack();
			}
		} else {
			rootStack = this;
		}
		return rootStack;
	}
	
	public final TEComponentStack getChildStack() {
		return mChildStack;
	}
	
	public final void evaluate() {
		mEvaluateReady = true;
	}
	
	public final void resetEvaluate() {
		mEvaluateReady = false;
	}
	
	private final boolean isParentOf(TEComponentStack stack) {
		boolean returnValue = (stack == this);
		TEComponentStack childStack = this.getChildStack();
		while (!returnValue && (childStack != null)) {
			returnValue = (stack == childStack);
			childStack = childStack.getChildStack();
		}
		return returnValue;
	}
}