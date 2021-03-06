package dev.geminileft.FreeCellGameEngine;

import dev.geminileft.TEGameEngine.PlayingCard;
import dev.geminileft.TEGameEngine.PlayingCard.FaceValue;
import dev.geminileft.TEGameEngine.PlayingCard.Suit;
import dev.geminileft.TEGameEngine.RenderHUDMoves;
import dev.geminileft.TEGameEngine.RenderImage;
import dev.geminileft.TEGameEngine.StackCard;
import dev.geminileft.TEGameEngine.StackTableCell;
import dev.geminileft.TEGameEngine.TEComponent;
import dev.geminileft.TEGameEngine.TEComponent.Event;
import dev.geminileft.TEGameEngine.TEComponentStack;
import dev.geminileft.TEGameEngine.TEComponentStack.StackType;
import dev.geminileft.TEGameEngine.TEEngine;
import dev.geminileft.TEGameEngine.TEGameObject;
import dev.geminileft.TEGameEngine.TEManagerStack;
import dev.geminileft.TEGameEngine.TEManagerTime;
import dev.geminileft.TEGameEngine.TEPoint;
import dev.geminileft.TEGameEngine.TERandomizer;
import dev.geminileft.TEGameEngine.TESize;
import dev.geminileft.opengltest.R;

public class FreeCellGame extends TEEngine {
	private final static int START_X = 35;
	private final static int X_GAP = 2;

	private FreeCellGameObjectFactory mFactory;

	public FreeCellGame(int width, int height) {
		super(width, height);
		 mFactory = new FreeCellGameObjectFactory(this);
	}

	@Override
	public void start() {
	    addGameObject(mFactory.createBackground());

	    int x = START_X;
	    int y = mHeight - 50;
	    
	    TEComponent.EventListener listener = addHUDMoves();
		addGameObject(mFactory.createHUDTimer());
	    addGameObject(mFactory.createMenu());

	    for (int i = 0;i < 4;++i) {
	        TEPoint pt = TEPoint.make(x, y);
	        addGameObject(mFactory.createFreeCell(pt));
	        x += FreeCellGameObjectFactory.CARD_SIZE_WIDTH + X_GAP;
	    }
	    
	    for (int i = 0;i < 4;++i) {
	        TEPoint pt = TEPoint.make(x, y);
	        addGameObject(mFactory.createAceCellStack(pt));
	        x += FreeCellGameObjectFactory.CARD_SIZE_WIDTH + X_GAP;
	    }
	    
		final int stack1 = 8;
		final int stack2 = 7;
		
	    PlayingCard stacks[][] = new PlayingCard[stack1][stack2];
		
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
	    
		TERandomizer rand = new TERandomizer(TEManagerTime.currentTime());
	    int wLeft = 52;
	    for (int i = 0;i < 52;++i) {
			long next  = rand.next();
	        long j = Math.abs(next) % wLeft;
	        stacks[(i % 8)][i / 8] = deck[(int)j];
	        deck[(int)j] = deck[--wLeft];
	    }
	    addTableStack(START_X, mFactory, stacks, listener);
	    TEComponentStack.setOpenFreeCellCount(4);
	    TEComponentStack.setOpenTableCellCount(0);

	}

	private void addTableStack(int startX, FreeCellGameObjectFactory factory, PlayingCard[][] cards, TEComponent.EventListener listener) {
		TEManagerStack stackManager = TEManagerStack.sharedManager();
		TEComponent.EventListener touchAcceptedListener = stackManager.getTouchAcceptListener();
		
		int x = startX;
		int y = mHeight - 120;
    	for (int j = 0; j < cards.length;++j) {
    		TEGameObject gameObject = factory.createTableCellStack(TEPoint.make(x, y));
    		StackTableCell tableStack = new StackTableCell(StackType.TableCell);
    		stackManager.addTableStack(tableStack);
        	gameObject.addComponent(tableStack);
        	addGameObject(gameObject);
        	TEComponentStack stack = tableStack;
	    	for (int i = 0;i < cards[0].length;++i) {
				PlayingCard card = cards[j][i];
				if (card != null) {
					StackCard cardStack;
			    	gameObject = factory.createPlayingCard(card);
			    	cardStack = new StackCard(card);
			    	gameObject.addComponent(cardStack);
			    	stack.pushStack(cardStack);
			    	gameObject.addEventSubscription(Event.EVENT_ACCEPT_MOVE, listener);
			    	gameObject.addEventSubscription(Event.EVENT_ACCEPT_MOVE, touchAcceptedListener);
			    	addGameObject(gameObject);				
			    	stack = cardStack;
				}
			}
	    	x += FreeCellGameObjectFactory.CARD_SIZE_WIDTH + X_GAP;
    	}
	}
	
	private TEComponent.EventListener addHUDMoves() {
		final int height = 50;
		TEComponent.EventListener eventListener;
		final int x = 100;
		RenderImage image = new RenderImage(R.raw.moves, TEPoint.zero(), TESize.make(118, 26));
		TEGameObject gameObject = new TEGameObject();
		gameObject.addComponent(image);
		gameObject.position = TEPoint.make(x, height);
		addGameObject(gameObject);
		TESize size = image.getSize();

		gameObject = new TEGameObject();
		RenderHUDMoves text = new RenderHUDMoves(R.raw.numbers, TEPoint.zero(), TESize.zero());
		eventListener = text.getTouchAcceptListener();
		gameObject.addComponent(text);
		gameObject.position = TEPoint.make(x + size.width / 2 + 17, height);
		addGameObject(gameObject);
		return eventListener;
	}

/*
	private void addHUDTimer() {
		final int height = 50;
		final int x = 275;
		TESize size = TESize.make(90, 26);
		RenderImage image = new RenderImage(R.drawable.image_time, null, size);
		TEGameObject gameObject = new TEGameObject();
		gameObject.addComponent(image);
		gameObject.position = TEPoint.make(x, height);
		addGameObject(gameObject);
		gameObject = new TEGameObject();
		RenderHUDTimer text = new RenderHUDTimer(R.drawable.numbers, null, null);
		gameObject.addComponent(text);
		gameObject.position = TEPoint.make(x + size.width / 2 + 17, height);
		addGameObject(gameObject);
	}
*/
}
