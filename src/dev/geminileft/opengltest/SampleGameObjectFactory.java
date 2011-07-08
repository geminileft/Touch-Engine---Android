package dev.geminileft.opengltest;

import java.util.HashMap;

import dev.geminileft.TEGameEngine.PlayingCard;
import dev.geminileft.TEGameEngine.Point;
import dev.geminileft.TEGameEngine.RenderImage;
import dev.geminileft.TEGameEngine.RenderHUDMoves;
import dev.geminileft.TEGameEngine.Size;
import dev.geminileft.TEGameEngine.StackAceCell;
import dev.geminileft.TEGameEngine.StackFreeCell;
import dev.geminileft.TEGameEngine.TEComponent;
import dev.geminileft.TEGameEngine.TEGameObject;
import dev.geminileft.TEGameEngine.TouchDrag;

public final class SampleGameObjectFactory {
	private HashMap<String, Integer> mCardMap = new HashMap<String, Integer>();
	
	public SampleGameObjectFactory() {
		super();
		mCardMap.put("SpadeAce", R.drawable.spade_ace);
		mCardMap.put("SpadeTwo", R.drawable.spade_two);
		mCardMap.put("SpadeThree", R.drawable.spade_three);
		mCardMap.put("SpadeFour", R.drawable.spade_four);
		mCardMap.put("SpadeFive", R.drawable.spade_five);
		mCardMap.put("SpadeSix", R.drawable.spade_six);
		mCardMap.put("SpadeSeven", R.drawable.spade_seven);
		mCardMap.put("SpadeEight", R.drawable.spade_eight);
		mCardMap.put("SpadeNine", R.drawable.spade_nine);
		mCardMap.put("SpadeTen", R.drawable.spade_ten);
		mCardMap.put("SpadeJack", R.drawable.spade_jack);
		mCardMap.put("SpadeQueen", R.drawable.spade_queen);
		mCardMap.put("SpadeKing", R.drawable.spade_king);
		
		mCardMap.put("DiamondAce", R.drawable.diamond_ace);
		mCardMap.put("DiamondTwo", R.drawable.diamond_two);
		mCardMap.put("DiamondThree", R.drawable.diamond_three);
		mCardMap.put("DiamondFour", R.drawable.diamond_four);
		mCardMap.put("DiamondFive", R.drawable.diamond_five);
		mCardMap.put("DiamondSix", R.drawable.diamond_six);
		mCardMap.put("DiamondSeven", R.drawable.diamond_seven);
		mCardMap.put("DiamondEight", R.drawable.diamond_eight);
		mCardMap.put("DiamondNine", R.drawable.diamond_nine);
		mCardMap.put("DiamondTen", R.drawable.diamond_ten);
		mCardMap.put("DiamondJack", R.drawable.diamond_jack);
		mCardMap.put("DiamondQueen", R.drawable.diamond_queen);
		mCardMap.put("DiamondKing", R.drawable.diamond_king);
		
		mCardMap.put("HeartAce", R.drawable.heart_ace);
		mCardMap.put("HeartTwo", R.drawable.heart_two);
		mCardMap.put("HeartThree", R.drawable.heart_three);
		mCardMap.put("HeartFour", R.drawable.heart_four);
		mCardMap.put("HeartFive", R.drawable.heart_five);
		mCardMap.put("HeartSix", R.drawable.heart_six);
		mCardMap.put("HeartSeven", R.drawable.heart_seven);
		mCardMap.put("HeartEight", R.drawable.heart_eight);
		mCardMap.put("HeartNine", R.drawable.heart_nine);
		mCardMap.put("HeartTen", R.drawable.heart_ten);
		mCardMap.put("HeartJack", R.drawable.heart_jack);
		mCardMap.put("HeartQueen", R.drawable.heart_queen);
		mCardMap.put("HeartKing", R.drawable.heart_king);
		
		mCardMap.put("ClubAce", R.drawable.club_ace);
		mCardMap.put("ClubTwo", R.drawable.club_two);
		mCardMap.put("ClubThree", R.drawable.club_three);
		mCardMap.put("ClubFour", R.drawable.club_four);
		mCardMap.put("ClubFive", R.drawable.club_five);
		mCardMap.put("ClubSix", R.drawable.club_six);
		mCardMap.put("ClubSeven", R.drawable.club_seven);
		mCardMap.put("ClubEight", R.drawable.club_eight);
		mCardMap.put("ClubNine", R.drawable.club_nine);
		mCardMap.put("ClubTen", R.drawable.club_ten);
		mCardMap.put("ClubJack", R.drawable.club_jack);
		mCardMap.put("ClubQueen", R.drawable.club_queen);
		mCardMap.put("ClubKing", R.drawable.club_king);
	}

	public TEGameObject createBackground(Point position) {
		TEGameObject gameObject = new TEGameObject();
		Size size = new Size(480, 854);
		RenderImage image = new RenderImage(R.drawable.table_background, null, size);
		gameObject.addComponent(image);
		gameObject.position = position;
		gameObject.size = size;
		return gameObject;
	}

	public TEGameObject createPlayingCard(Point position, PlayingCard card) {
		TEGameObject gameObject = new TEGameObject();
		Size size = new Size(48, 64);
		String key = card.getCardName();
		Integer resource = mCardMap.get(key);
		int resourceId;
		if (resource == null) {
			resourceId = R.drawable.spade_ace;
		} else {
			resourceId = resource;
		}
		RenderImage image = new RenderImage(resourceId, null, size);
		gameObject.addComponent(image);
		gameObject.addComponent(new TouchDrag());
		gameObject.position = position;
		gameObject.size = size;
		return gameObject;
	}

	public TEGameObject createFreeCell(Point position) {
		TEGameObject gameObject = new TEGameObject();
		Size size = new Size(48, 64);
		gameObject.addComponent(new RenderImage(R.drawable.free_cell, null, size));
		gameObject.addComponent(new StackFreeCell());
		gameObject.position = position;
		gameObject.size = size;
		return gameObject;
	}

	public TEGameObject createAceCellStack(Point position) {
		TEGameObject gameObject = new TEGameObject();
		Size size = new Size(48, 64);
		gameObject.addComponent(new RenderImage(R.drawable.ace_cell, null, size));
    	gameObject.addComponent(new StackAceCell());
    	gameObject.position = position;
		gameObject.size = size;
		return gameObject;
	}

	public TEGameObject createTableCellStack(Point position) {
		TEGameObject gameObject = new TEGameObject();
		Size size = new Size(48, 64);
		gameObject.addComponent(new RenderImage(R.drawable.free_cell, null, size));
    	gameObject.position = position;
		gameObject.size = size;
		return gameObject;
	}
}
