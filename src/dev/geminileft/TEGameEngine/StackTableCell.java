package dev.geminileft.TEGameEngine;

public class StackTableCell extends TEComponentStack {

	public StackTableCell(StackType stackType) {
		super(stackType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getStackOffset() {
		// TODO Auto-generated method stub
		return 40;
	}

	@Override
	public boolean doesAccept(TEComponentStack stack) {
		// TODO Auto-generated method stub
		PlayingCard card = getPlayingCard();
		PlayingCard stackCard = stack.getPlayingCard();
		PlayingCard.Suit suit = card.getSuit();
		PlayingCard.SuitColor suitColor = suit.getSuitColor();
		PlayingCard.SuitColor stackSuitColor = stackCard.getSuit().getSuitColor();
		boolean results = ((suitColor != stackSuitColor)
				&& ((card.getFaceValue().getValue() - 1) == stackCard.getFaceValue().getValue()));
		return results;
	}

}
