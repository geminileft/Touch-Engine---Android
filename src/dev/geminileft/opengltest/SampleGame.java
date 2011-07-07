package dev.geminileft.opengltest;

import java.util.Random;

import android.content.Context;
import android.os.SystemClock;
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
	private int mWidth;
	private int mHeight;
	private final int START_X = 35;
	
	public SampleGame(Context context, int width, int height) {
		super(context);
		mWidth = width;
		mHeight = height;
	}
	
	@Override
	public void start() {
		TEGameObject gameObject;
		
		SampleGameObjectFactory factory = new SampleGameObjectFactory();
		
		gameObject = factory.createBackground(new Point(240, 427));
		addGameObject(gameObject);
		
		gameObject = factory.createHUDTimer(new Point(mWidth / 2, 50));
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
		
		PlayingCard stacks[][] = new PlayingCard[8][7];
		PlayingCard deck[] = new PlayingCard[52];
		deck[0] = new PlayingCard(FaceValue.Ace, Suit.Spade);
		deck[1] = new PlayingCard(FaceValue.Two, Suit.Spade);
		deck[2] = new PlayingCard(FaceValue.Three, Suit.Spade);
		deck[3] = new PlayingCard(FaceValue.Four, Suit.Spade);
		deck[4] = new PlayingCard(FaceValue.Five, Suit.Spade);
		deck[5] = new PlayingCard(FaceValue.Six, Suit.Spade);
		deck[6] = new PlayingCard(FaceValue.Seven, Suit.Spade);
		deck[7] = new PlayingCard(FaceValue.Eight, Suit.Spade);
		deck[8] = new PlayingCard(FaceValue.Nine, Suit.Spade);
		deck[9] = new PlayingCard(FaceValue.Ten, Suit.Spade);
		deck[10] = new PlayingCard(FaceValue.Jack, Suit.Spade);
		deck[11] = new PlayingCard(FaceValue.Queen, Suit.Spade);
		deck[12] = new PlayingCard(FaceValue.King, Suit.Spade);
		deck[13] = new PlayingCard(FaceValue.Ace, Suit.Club);
		deck[14] = new PlayingCard(FaceValue.Two, Suit.Club);
		deck[15] = new PlayingCard(FaceValue.Three, Suit.Club);
		deck[16] = new PlayingCard(FaceValue.Four, Suit.Club);
		deck[17] = new PlayingCard(FaceValue.Five, Suit.Club);
		deck[18] = new PlayingCard(FaceValue.Six, Suit.Club);
		deck[19] = new PlayingCard(FaceValue.Seven, Suit.Club);
		deck[20] = new PlayingCard(FaceValue.Eight, Suit.Club);
		deck[21] = new PlayingCard(FaceValue.Nine, Suit.Club);
		deck[22] = new PlayingCard(FaceValue.Ten, Suit.Club);
		deck[23] = new PlayingCard(FaceValue.Jack, Suit.Club);
		deck[24] = new PlayingCard(FaceValue.Queen, Suit.Club);
		deck[25] = new PlayingCard(FaceValue.King, Suit.Club);
		deck[26] = new PlayingCard(FaceValue.Ace, Suit.Heart);
		deck[27] = new PlayingCard(FaceValue.Two, Suit.Heart);
		deck[28] = new PlayingCard(FaceValue.Three, Suit.Heart);
		deck[29] = new PlayingCard(FaceValue.Four, Suit.Heart);
		deck[30] = new PlayingCard(FaceValue.Five, Suit.Heart);
		deck[31] = new PlayingCard(FaceValue.Six, Suit.Heart);
		deck[32] = new PlayingCard(FaceValue.Seven, Suit.Heart);
		deck[33] = new PlayingCard(FaceValue.Eight, Suit.Heart);
		deck[34] = new PlayingCard(FaceValue.Nine, Suit.Heart);
		deck[35] = new PlayingCard(FaceValue.Ten, Suit.Heart);
		deck[36] = new PlayingCard(FaceValue.Jack, Suit.Heart);
		deck[37] = new PlayingCard(FaceValue.Queen, Suit.Heart);
		deck[38] = new PlayingCard(FaceValue.King, Suit.Heart);
		deck[39] = new PlayingCard(FaceValue.Ace, Suit.Diamond);
		deck[40] = new PlayingCard(FaceValue.Two, Suit.Diamond);
		deck[41] = new PlayingCard(FaceValue.Three, Suit.Diamond);
		deck[42] = new PlayingCard(FaceValue.Four, Suit.Diamond);
		deck[43] = new PlayingCard(FaceValue.Five, Suit.Diamond);
		deck[44] = new PlayingCard(FaceValue.Six, Suit.Diamond);
		deck[45] = new PlayingCard(FaceValue.Seven, Suit.Diamond);
		deck[46] = new PlayingCard(FaceValue.Eight, Suit.Diamond);
		deck[47] = new PlayingCard(FaceValue.Nine, Suit.Diamond);
		deck[48] = new PlayingCard(FaceValue.Ten, Suit.Diamond);
		deck[49] = new PlayingCard(FaceValue.Jack, Suit.Diamond);
		deck[50] = new PlayingCard(FaceValue.Queen, Suit.Diamond);
		deck[51] = new PlayingCard(FaceValue.King, Suit.Diamond);
		
		Random rand = new Random(SystemClock.uptimeMillis());
		int wLeft = 52;
		for (int i = 0;i < 52;++i) {
			int j = Math.abs(rand.nextInt()) % wLeft;
		        stacks[(i % 8)][i / 8] = deck[j];
		        deck[j] = deck[--wLeft];
		}
    	addTableStack(START_X, factory, stacks);
    	TEComponentStack.openFreeCellCount = 4;
    	TEComponentStack.openTableCellCount = 0;
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
				PlayingCard card = cards[j][i];
				if (card != null) {
					StackCard cardStack;
			    	gameObject = factory.createPlayingCard(null, card);
			    	cardStack = new StackCard(card);
			    	gameObject.addComponent(cardStack);
			    	stack.pushStack(cardStack);
			    	stack = cardStack;
			    	addGameObject(gameObject);				
				}
			}
	    	x += 55;
    	}
	}
}
