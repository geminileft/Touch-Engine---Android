package dev.geminileft.TEGameEngine;

public class StackTableCell extends TEComponentStack {

	private boolean mClear;
	
	public StackTableCell(StackType stackType) {
		super(stackType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getStackOffset(boolean isFirst) {
		int offset = 0;
		if (isFirst) {
			offset = 0;
		} else {
			offset = CARD_OFFSET;
		}
		return offset;
	}

	@Override
	public boolean doesAccept(TEComponentStack stack) {
		boolean results = false;
		TEComponentStack childStack = getChildStack();
		
		if (childStack == null) {
			final int dropCount = getPickupCount(mOpenFreeCellCount, mOpenTableCellCount - 1);
			childStack = stack;
			int stackCount = 0;
			while (childStack != null) {
				++stackCount;
				childStack = childStack.getChildStack();
			}
			results = stackCount <= dropCount;
		} else {
			while (childStack.getChildStack() != null) {
				childStack = childStack.getChildStack();
			}
			PlayingCard card = childStack.getPlayingCard();
			PlayingCard stackCard = stack.getPlayingCard();
			results = card.canStack(stackCard);	
		}
		return results;
	}

	@Override
	public void pushStack(TEComponentStack stack) {
		super.pushStack(stack);
		--TEComponentStack.mOpenTableCellCount;
	}
	
	@Override
	public void popStack(TEComponentStack stack) {
		super.popStack(stack);
		++TEComponentStack.mOpenTableCellCount;
	}

	@Override
	public void update(long dt) {}
	
	public boolean getClear() {
		return mClear;
	}
	
	public void setClear() {
		mClear = true;
	}
}
