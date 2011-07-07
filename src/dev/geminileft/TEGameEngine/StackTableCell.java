package dev.geminileft.TEGameEngine;

public class StackTableCell extends TEComponentStack {

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
			results = true;
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
		--TEComponentStack.openTableCellCount;
	}
	
	@Override
	public void popStack(TEComponentStack stack) {
		super.popStack(stack);
		++TEComponentStack.openTableCellCount;
	}
}
