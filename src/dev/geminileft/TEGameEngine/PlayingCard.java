package dev.geminileft.TEGameEngine;

public class PlayingCard {

	public enum SuitColor {
		Red
		, Black
	}
	
	public enum FaceValue {
		Ace(1)
		, Two(2)
		, Three(3);
		
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
		default:
			Value = Value + "";
		}
		
		return Value;
	}
}
