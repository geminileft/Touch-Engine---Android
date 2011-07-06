package dev.geminileft.TEGameEngine;

public class StackFreeCell extends TEComponentStack {

	public StackFreeCell() {
		super(StackType.FreeCell);
	}
	
	@Override
	public final int getStackOffset(boolean isFirst) {
		return 0;
	}

	@Override
	public final boolean doesAccept(TEComponentStack stack) {
		return ((getChildStack() == null) && (stack.getChildStack() == null));
	}
}
