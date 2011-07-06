package dev.geminileft.opengltest;

import android.content.Context;
import dev.geminileft.TEGameEngine.PlayingCard;
import dev.geminileft.TEGameEngine.PlayingCard.FaceValue;
import dev.geminileft.TEGameEngine.PlayingCard.Suit;
import dev.geminileft.TEGameEngine.Point;
import dev.geminileft.TEGameEngine.StackCard;
import dev.geminileft.TEGameEngine.StackTableCell;
import dev.geminileft.TEGameEngine.TEComponentStack;
import dev.geminileft.TEGameEngine.TEComponentStack.StackType;
import dev.geminileft.TEGameEngine.TEEngine;
import dev.geminileft.TEGameEngine.TEGameObject;

public class SampleGame extends TEEngine {
	//private int mWidth;
	private int mHeight;
	private final int START_X = 35;
	
	public SampleGame(Context context, int width, int height) {
		super(context);
		//mWidth = width;
		mHeight = height;
	}
	
	@Override
	public void start() {
		TEGameObject gameObject;

		SampleGameObjectFactory factory = new SampleGameObjectFactory();
		
		gameObject = factory.createBackground(new Point(240, 427));
		addGameObject(gameObject);
		int x = START_X;
		int y = mHeight - 50;
		
		for (int i = 0;i < 4;++i) {
			gameObject = factory.createFreeCell(new Point(x, y));
	    	addGameObject(gameObject);
	    	x += 55;
		}
		
		for (int i = 0;i < 4;++i) {
			gameObject = factory.createAceCellStack(new Point(x, y));
	    	addGameObject(gameObject);
	    	x += 55;
		}
		
		PlayingCard cards[][] = new PlayingCard[4][7];

    	cards[0][0] = new PlayingCard(FaceValue.Four, Suit.Club);
    	cards[0][1] = new PlayingCard(FaceValue.Jack, Suit.Club);
    	cards[0][2] = new PlayingCard(FaceValue.Ace, Suit.Diamond);
    	cards[0][3] = new PlayingCard(FaceValue.Four, Suit.Diamond);
    	cards[0][4] = new PlayingCard(FaceValue.Eight, Suit.Heart);
    	cards[0][5] = new PlayingCard(FaceValue.Queen, Suit.Spade);
    	cards[0][6] = new PlayingCard(FaceValue.Four, Suit.Heart);

    	cards[1][0] = new PlayingCard(FaceValue.Nine, Suit.Spade);
    	cards[1][1] = new PlayingCard(FaceValue.Three, Suit.Club);
    	cards[1][2] = new PlayingCard(FaceValue.Jack, Suit.Diamond);
    	cards[1][3] = new PlayingCard(FaceValue.Jack, Suit.Spade);
    	cards[1][4] = new PlayingCard(FaceValue.King, Suit.Heart);
    	cards[1][5] = new PlayingCard(FaceValue.Eight, Suit.Spade);
    	cards[1][6] = new PlayingCard(FaceValue.Ten, Suit.Diamond);

    	cards[2][0] = new PlayingCard(FaceValue.Four, Suit.Spade);
    	cards[2][1] = new PlayingCard(FaceValue.Seven, Suit.Heart);
    	cards[2][2] = new PlayingCard(FaceValue.King, Suit.Diamond);
    	cards[2][3] = new PlayingCard(FaceValue.Five, Suit.Club);
    	cards[2][4] = new PlayingCard(FaceValue.Six, Suit.Diamond);
    	cards[2][5] = new PlayingCard(FaceValue.Nine, Suit.Diamond);
    	cards[2][6] = new PlayingCard(FaceValue.Ten, Suit.Heart);

    	cards[3][0] = new PlayingCard(FaceValue.Five, Suit.Spade);
    	cards[3][1] = new PlayingCard(FaceValue.King, Suit.Spade);
    	cards[3][2] = new PlayingCard(FaceValue.Ten, Suit.Club);
    	cards[3][3] = new PlayingCard(FaceValue.Six, Suit.Club);
    	cards[3][4] = new PlayingCard(FaceValue.Three, Suit.Heart);
    	cards[3][5] = new PlayingCard(FaceValue.Six, Suit.Heart);
    	cards[3][6] = new PlayingCard(FaceValue.Two, Suit.Club);
    
		PlayingCard cards2[][] = new PlayingCard[4][6];

    	cards2[2][0] = new PlayingCard(FaceValue.Three, Suit.Spade);
    	cards2[2][1] = new PlayingCard(FaceValue.Nine, Suit.Club);
    	cards2[2][2] = new PlayingCard(FaceValue.Five, Suit.Heart);
    	cards2[2][3] = new PlayingCard(FaceValue.Nine, Suit.Heart);
    	cards2[2][4] = new PlayingCard(FaceValue.Queen, Suit.Club);
    	cards2[2][5] = new PlayingCard(FaceValue.Seven, Suit.Diamond);

    	cards2[0][0] = new PlayingCard(FaceValue.Ten, Suit.Spade);
    	cards2[0][1] = new PlayingCard(FaceValue.Queen, Suit.Diamond);
    	cards2[0][2] = new PlayingCard(FaceValue.Five, Suit.Diamond);
    	cards2[0][3] = new PlayingCard(FaceValue.Seven, Suit.Spade);
    	cards2[0][4] = new PlayingCard(FaceValue.Jack, Suit.Heart);
    	cards2[0][5] = new PlayingCard(FaceValue.Ace, Suit.Heart);

    	cards2[1][0] = new PlayingCard(FaceValue.Eight, Suit.Spade);
    	cards2[1][1] = new PlayingCard(FaceValue.Three, Suit.Club);
    	cards2[1][2] = new PlayingCard(FaceValue.Eight, Suit.Diamond);
    	cards2[1][3] = new PlayingCard(FaceValue.Two, Suit.Spade);
    	cards2[1][4] = new PlayingCard(FaceValue.King, Suit.Heart);
    	cards2[1][5] = new PlayingCard(FaceValue.Eight, Suit.Spade);

    	cards2[3][0] = new PlayingCard(FaceValue.Queen, Suit.Heart);
    	cards2[3][1] = new PlayingCard(FaceValue.Six, Suit.Spade);
    	cards2[3][2] = new PlayingCard(FaceValue.Two, Suit.Heart);
    	cards2[3][3] = new PlayingCard(FaceValue.Seven, Suit.Club);
    	cards2[3][4] = new PlayingCard(FaceValue.Two, Suit.Diamond);
    	cards2[3][5] = new PlayingCard(FaceValue.Ace, Suit.Club);
    
    	addTableStack(START_X, factory, cards);
    	addTableStack(START_X + 220, factory, cards2);
	}
	
	public void addTableStack(int startX, SampleGameObjectFactory factory, PlayingCard[][] cards) {
		int x = startX;
		int y = mHeight - 120;
    	for (int j = 0; j < cards.length;++j) {
    		TEGameObject gameObject = factory.createTableCellStack(new Point(x, y));
    		StackTableCell tableStack = new StackTableCell(StackType.TableCell);
        	gameObject.addComponent(tableStack);
        	addGameObject(gameObject);
        	TEComponentStack stack = tableStack;
	    	for (int i = 0;i < cards[0].length;++i) {
				StackCard cardStack;
				PlayingCard card = cards[j][i];
		    	gameObject = factory.createPlayingCard(null, card);
		    	cardStack = new StackCard(card);
		    	gameObject.addComponent(cardStack);
		    	stack.pushStack(cardStack);
		    	stack = cardStack;
		    	addGameObject(gameObject);		
			}
	    	x += 55;
    	}
	}
}
