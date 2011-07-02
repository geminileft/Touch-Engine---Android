package dev.geminileft.opengltest;

import android.content.Context;
import dev.geminileft.TEGameEngine.Point;
import dev.geminileft.TEGameEngine.StackCard;
import dev.geminileft.TEGameEngine.StackFreeCell;
import dev.geminileft.TEGameEngine.TEEngine;
import dev.geminileft.TEGameEngine.TEGameObject;

public class SampleGame extends TEEngine {

	public SampleGame(Context context) {
		super(context);
	}
	
	@Override
	public void start() {
		TEGameObject gameObject;
		StackFreeCell freeCellStack;
		StackCard cardStack;
		StackCard mainStack;
		
		gameObject = SampleGameObjectFactory.createBackground(new Point(240, 427));
		addGameObject(gameObject);

		gameObject = SampleGameObjectFactory.createFreeCell(new Point(35, 350));
    	freeCellStack = new StackFreeCell();
    	gameObject.addComponent(freeCellStack);
    	addGameObject(gameObject);
    	
    	gameObject = SampleGameObjectFactory.createPlayingCard(null);
    	cardStack = new StackCard();
    	gameObject.addComponent(cardStack);
    	addGameObject(gameObject);
    	freeCellStack.pushStack(cardStack);

    	gameObject = SampleGameObjectFactory.createPlayingCard(new Point(90, 350));
    	mainStack = new StackCard();
    	gameObject.addComponent(mainStack);
    	addGameObject(gameObject);

    	gameObject = SampleGameObjectFactory.createPlayingCard(null);
    	cardStack = new StackCard();
    	gameObject.addComponent(cardStack);
    	addGameObject(gameObject);
    	mainStack.pushStack(cardStack);

	}
}
