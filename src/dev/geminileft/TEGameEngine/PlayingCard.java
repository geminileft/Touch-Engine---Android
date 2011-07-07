package dev.geminileft.TEGameEngine;

public class PlayingCard {

	public enum SuitColor {
		Red
		, Black
	}
	
	public enum FaceValue {
		Ace(1)
		, Two(2)
		, Three(3)
		, Four(4)
		, Five(5)
		, Six(6)
		, Seven(7)
		, Eight(8)
		, Nine(9)
		, Ten(10)
		, Jack(11)
		, Queen(12)
		, King(13)
		;
		
		private int mValue;
		
		private FaceValue(int value) {
			mValue = value;
		}
		
		public int getValue() {
			return mValue;
		}
	}
	
	public enum Suit {
		Spade(SuitColor.Black)
		, Club(SuitColor.Black)
		, Diamond(SuitColor.Red)
		, Heart(SuitColor.Red);
		
		private SuitColor mSuitColor;
		
		private Suit(SuitColor suitColor) {
			mSuitColor = suitColor;
		}
		
		public SuitColor getSuitColor() {
			return mSuitColor;
		}
	}
	
	private FaceValue mFaceValue;
	private Suit mSuit;
	
	public PlayingCard(FaceValue faceValue, Suit suit) {
		mFaceValue = faceValue;
		mSuit = suit;
	}
	
	public FaceValue getFaceValue() {
		return mFaceValue;
	}
	
	public Suit getSuit() {
		return mSuit;
	}
	
	public String getCardName() {
		String Value;
		
		switch(mSuit) {
		case Spade:
			Value = "Spade";
			break;
		case Club:
			Value = "Club";
			break;
		case Diamond:
			Value = "Diamond";
			break;
		case Heart:
			Value = "Heart";
			break;
		default:
			Value = "";
			break;
		}
		
		switch(mFaceValue) {
		case Ace:
			Value = Value + "Ace";
			break;
		case Two:
			Value = Value + "Two";
			break;
		case Three:
			Value = Value + "Three";
			break;
		case Four:
			Value = Value + "Four";
			break;
		case Five:
			Value = Value + "Five";
			break;
		case Six:
			Value = Value + "Six";
			break;
		case Seven:
			Value = Value + "Seven";
			break;
		case Eight:
			Value = Value + "Eight";
			break;
		case Nine:
			Value = Value + "Nine";
			break;
		case Ten:
			Value = Value + "Ten";
			break;
		case Jack:
			Value = Value + "Jack";
			break;
		case Queen:
			Value = Value + "Queen";
			break;
		case King:
			Value = Value + "King";
			break;
		default:
			Value = Value + "";
		}
		
		return Value;
	}
	
	public boolean canStack(PlayingCard stackCard) {
		PlayingCard.Suit suit = this.getSuit();
		PlayingCard.SuitColor suitColor = suit.getSuitColor();
		PlayingCard.SuitColor stackSuitColor = stackCard.getSuit().getSuitColor();
		return ((suitColor != stackSuitColor) && ((this.getFaceValue().getValue() - 1) == stackCard.getFaceValue().getValue()));		
	}
}
