package dev.geminileft.FreeCellGameEngine;

import java.util.HashMap;

import dev.geminileft.TEGameEngine.PlayingCard;
import dev.geminileft.TEGameEngine.RenderImage;
import dev.geminileft.TEGameEngine.SoundStart;
import dev.geminileft.TEGameEngine.StackAceCell;
import dev.geminileft.TEGameEngine.StackFreeCell;
import dev.geminileft.TEGameEngine.TEComponent.Event;
import dev.geminileft.TEGameEngine.TEEngine;
import dev.geminileft.TEGameEngine.TEGameObject;
import dev.geminileft.TEGameEngine.TEManagerStack;
import dev.geminileft.TEGameEngine.TEPoint;
import dev.geminileft.TEGameEngine.TESize;
import dev.geminileft.TEGameEngine.TouchDrag;
import dev.geminileft.opengltest.R;

public final class FreeCellGameObjectFactory {
	private HashMap<String, Integer> mCardMap = new HashMap<String, Integer>();
	private TEEngine mGame;
	
	public static final int CARD_SIZE_WIDTH = 48;
	public static final int CARD_SIZE_HEIGHT = 64;

	public FreeCellGameObjectFactory(TEEngine game) {
		super();
		mGame = game;
		mCardMap.put("SpadeAce", R.raw.spade_ace);
		mCardMap.put("SpadeTwo", R.raw.spade_two);
		mCardMap.put("SpadeThree", R.raw.spade_three);
		mCardMap.put("SpadeFour", R.raw.spade_four);
		mCardMap.put("SpadeFive", R.raw.spade_five);
		mCardMap.put("SpadeSix", R.raw.spade_six);
		mCardMap.put("SpadeSeven", R.raw.spade_seven);
		mCardMap.put("SpadeEight", R.raw.spade_eight);
		mCardMap.put("SpadeNine", R.raw.spade_nine);
		mCardMap.put("SpadeTen", R.raw.spade_ten);
		mCardMap.put("SpadeJack", R.raw.spade_jack);
		mCardMap.put("SpadeQueen", R.raw.spade_queen);
		mCardMap.put("SpadeKing", R.raw.spade_king);
		
		mCardMap.put("DiamondAce", R.raw.diamond_ace);
		mCardMap.put("DiamondTwo", R.raw.diamond_two);
		mCardMap.put("DiamondThree", R.raw.diamond_three);
		mCardMap.put("DiamondFour", R.raw.diamond_four);
		mCardMap.put("DiamondFive", R.raw.diamond_five);
		mCardMap.put("DiamondSix", R.raw.diamond_six);
		mCardMap.put("DiamondSeven", R.raw.diamond_seven);
		mCardMap.put("DiamondEight", R.raw.diamond_eight);
		mCardMap.put("DiamondNine", R.raw.diamond_nine);
		mCardMap.put("DiamondTen", R.raw.diamond_ten);
		mCardMap.put("DiamondJack", R.raw.diamond_jack);
		mCardMap.put("DiamondQueen", R.raw.diamond_queen);
		mCardMap.put("DiamondKing", R.raw.diamond_king);
		
		mCardMap.put("HeartAce", R.raw.heart_ace);
		mCardMap.put("HeartTwo", R.raw.heart_two);
		mCardMap.put("HeartThree", R.raw.heart_three);
		mCardMap.put("HeartFour", R.raw.heart_four);
		mCardMap.put("HeartFive", R.raw.heart_five);
		mCardMap.put("HeartSix", R.raw.heart_six);
		mCardMap.put("HeartSeven", R.raw.heart_seven);
		mCardMap.put("HeartEight", R.raw.heart_eight);
		mCardMap.put("HeartNine", R.raw.heart_nine);
		mCardMap.put("HeartTen", R.raw.heart_ten);
		mCardMap.put("HeartJack", R.raw.heart_jack);
		mCardMap.put("HeartQueen", R.raw.heart_queen);
		mCardMap.put("HeartKing", R.raw.heart_king);
		
		mCardMap.put("ClubAce", R.raw.club_ace);
		mCardMap.put("ClubTwo", R.raw.club_two);
		mCardMap.put("ClubThree", R.raw.club_three);
		mCardMap.put("ClubFour", R.raw.club_four);
		mCardMap.put("ClubFive", R.raw.club_five);
		mCardMap.put("ClubSix", R.raw.club_six);
		mCardMap.put("ClubSeven", R.raw.club_seven);
		mCardMap.put("ClubEight", R.raw.club_eight);
		mCardMap.put("ClubNine", R.raw.club_nine);
		mCardMap.put("ClubTen", R.raw.club_ten);
		mCardMap.put("ClubJack", R.raw.club_jack);
		mCardMap.put("ClubQueen", R.raw.club_queen);
		mCardMap.put("ClubKing", R.raw.club_king);
	}

	public TEGameObject createBackground() {
	    TEGameObject gameObject = new TEGameObject();
	    TESize size = TESize.make(mGame.mWidth, mGame.mHeight);
	    gameObject.addComponent(new RenderImage(R.raw.table_background, TEPoint.make(0, 0), size));
	    gameObject.addComponent(new SoundStart(R.raw.shuffle));
	    gameObject.position.x = size.width / 2;
	    gameObject.position.y = size.height / 2;
	    gameObject.size.width = size.width;
	    gameObject.size.height = size.height;
	    return gameObject;
	}
	
	public TEGameObject createPlayingCard(PlayingCard card) {
		TEGameObject gameObject = new TEGameObject();
		TESize size = TESize.make(FreeCellGameObjectFactory.CARD_SIZE_WIDTH, FreeCellGameObjectFactory.CARD_SIZE_HEIGHT);
		String key = card.getCardName();
		Integer resource = mCardMap.get(key);
		int resourceId;
		if (resource == null) {
			resourceId = R.raw.spade_ace;
		} else {
			resourceId = resource;
		}
		RenderImage image = new RenderImage(resourceId, TEPoint.zero(), size);
		gameObject.addComponent(image);
		gameObject.addComponent(new TouchDrag());
		gameObject.position = TEPoint.make(0, 0);
		gameObject.size = size;
		TEManagerStack stackManager = TEManagerStack.sharedManager();
		gameObject.addEventSubscription(Event.EVENT_MOVE_TO_FOUNDATION, stackManager.getMoveToAceStackListener());
		return gameObject;
	}

	public TEGameObject createFreeCell(TEPoint position) {
		TEGameObject gameObject = new TEGameObject();
		TESize size = TESize.make(48, 64);
		gameObject.addComponent(new RenderImage(R.raw.free_cell, TEPoint.zero(), size));
		gameObject.addComponent(new StackFreeCell());
		gameObject.position = position;
		gameObject.size = size;
		return gameObject;
	}

	public TEGameObject createAceCellStack(TEPoint position) {
		TEManagerStack stackManager = TEManagerStack.sharedManager();
		TEGameObject gameObject = new TEGameObject();
		StackAceCell aceCell = new StackAceCell();
		TESize size = TESize.make(48, 64);
		gameObject.addComponent(new RenderImage(R.raw.ace_cell, TEPoint.zero(), size));
    	gameObject.addComponent(aceCell);
    	gameObject.position = position;
		gameObject.size = size;
		stackManager.addAceStack(aceCell);
		return gameObject;
	}

	public TEGameObject createTableCellStack(TEPoint position) {
		TEGameObject gameObject = new TEGameObject();
		TESize size = TESize.make(48, 64);
		gameObject.addComponent(new RenderImage(R.raw.free_cell, TEPoint.zero(), size));
    	gameObject.position = position;
		gameObject.size = size;
		return gameObject;
	}

	TEGameObject createHUDTimer() {
		final int height = 15;
		final int x = 105;
		TESize size = TESize.make(46, 14);
		TEPoint offset = TEPoint.make(0, 0);
		RenderImage image = new RenderImage(R.raw.image_time, offset, size);
		TEGameObject gameObject = new TEGameObject();
		gameObject.addComponent(image);
		gameObject.position.x = x;
		gameObject.position.y = height;
		return gameObject;
	}

	TEGameObject createMenu() {
		TESize size = TESize.make(64, 16);
		TEPoint offset = TEPoint.make(0, 0);
		RenderImage image = new RenderImage(R.raw.menu, offset, size);
		TEGameObject gameObject = new TEGameObject();
		gameObject.addComponent(image);
		TESize screenSize = mGame.getScreenSize();
		gameObject.position.x = screenSize.width - (size.width / 2);
		gameObject.position.y = 15;
		return gameObject;
	}

}
