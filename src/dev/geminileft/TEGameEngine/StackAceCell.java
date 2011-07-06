package dev.geminileft.TEGameEngine;

import dev.geminileft.TEGameEngine.PlayingCard.FaceValue;

public class StackAceCell extends TEComponentStack {
	public StackAceCell() {
		super(StackType.AceCell);
	}
	
	@Override
	public final int getStackOffset(boolean isFirst) {
		return 0;
	}
	
	@Override
	public final boolean doesAccept(TEComponentStack stack) {
		boolean accept = false;
		if (stack.getChildStack() == null) {
			PlayingCard card = stack.getPlayingCard();
			TEComponentStack childStack = getChildStack();
			if (childStack == null) {
				accept = (card.getFaceValue() == FaceValue.Ace);
			} else {
				PlayingCard childCard = childStack.getPlayingCard();
				if (card.getSuit() == childCard.getSuit()) {
					while (childStack.getChildStack() != null) {
						childStack = childStack.getChildStack();
					}
					childCard = childStack.getPlayingCard();
					accept = ((childCard.getFaceValue().getValue() + 1) == card.getFaceValue().getValue());
				}
			}
		}
		return accept;
	}
}
