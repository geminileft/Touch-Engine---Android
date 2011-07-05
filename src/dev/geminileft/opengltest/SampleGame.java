package dev.geminileft.opengltest;

import android.content.Context;
import dev.geminileft.TEGameEngine.PlayingCard;
import dev.geminileft.TEGameEngine.PlayingCard.FaceValue;
import dev.geminileft.TEGameEngine.PlayingCard.Suit;
import dev.geminileft.TEGameEngine.Point;
import dev.geminileft.TEGameEngine.StackCard;
import dev.geminileft.TEGameEngine.StackTableCell;
import dev.geminileft.TEGameEngine.TEComponentStack.StackType;
import dev.geminileft.TEGameEngine.TEEngine;
import dev.geminileft.TEGameEngine.TEGameObject;

public class SampleGame extends TEEngine {
	private Context mContext;
	private int mWidth;
	private int mHeight;
	private final int START_X = 35;
	
	public SampleGame(Context context, int width, int height) {
		super(context);
		mContext = context;
		mWidth = width;
		mHeight = height;
	}
	
	@Override
	public void start() {
		TEGameObject gameObject;
		StackTableCell tableStack;
		StackCard cardStack;
		PlayingCard card;
		
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
		
		x = START_X;
		y -= 70;
		
		gameObject = factory.createTableCellStack(new Point(x, y));
		tableStack = new StackTableCell(StackType.TableCell);
    	gameObject.addComponent(tableStack);
    	addGameObject(gameObject);
    	card = new PlayingCard(FaceValue.Ace, Suit.Spade);
    	gameObject = factory.createPlayingCard(null, card);
    	cardStack = new StackCard(card);
    	gameObject.addComponent(cardStack);
    	tableStack.pushStack(cardStack);
    	addGameObject(gameObject);
    	x += 55;

		gameObject = factory.createTableCellStack(new Point(x, y));
		tableStack = new StackTableCell(StackType.TableCell);
    	gameObject.addComponent(tableStack);
    	addGameObject(gameObject);    	
    	card = new PlayingCard(FaceValue.Two, Suit.Spade);
    	gameObject = factory.createPlayingCard(null, card);
    	cardStack = new StackCard(card);
    	gameObject.addComponent(cardStack);
    	tableStack.pushStack(cardStack);
    	addGameObject(gameObject);
    	x += 55;

    	gameObject = factory.createTableCellStack(new Point(x, y));
		tableStack = new StackTableCell(StackType.TableCell);
    	gameObject.addComponent(tableStack);
    	addGameObject(gameObject);    	
    	card = new PlayingCard(FaceValue.Three, Suit.Spade);
    	gameObject = factory.createPlayingCard(null, card);
    	cardStack = new StackCard(card);
    	gameObject.addComponent(cardStack);
    	tableStack.pushStack(cardStack);
    	addGameObject(gameObject);
    	x += 55;

    	gameObject = factory.createTableCellStack(new Point(x, y));
		tableStack = new StackTableCell(StackType.TableCell);
    	gameObject.addComponent(tableStack);
    	addGameObject(gameObject);    	
    	card = new PlayingCard(FaceValue.Ace, Suit.Diamond);
    	gameObject = factory.createPlayingCard(null, card);
    	cardStack = new StackCard(card);
    	gameObject.addComponent(cardStack);
    	tableStack.pushStack(cardStack);
    	addGameObject(gameObject);
    	x += 55;

		for (int i = 0;i < 4;++i) {
	    	gameObject = factory.createTableCellStack(new Point(x, y));
			tableStack = new StackTableCell(StackType.TableCell);
	    	gameObject.addComponent(tableStack);
	    	addGameObject(gameObject);
	    	x += 55;
		}    	
	}
}
