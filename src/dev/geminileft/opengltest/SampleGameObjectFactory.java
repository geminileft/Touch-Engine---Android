package dev.geminileft.opengltest;

import dev.geminileft.TEGameEngine.Point;
import dev.geminileft.TEGameEngine.RenderImage;
import dev.geminileft.TEGameEngine.Size;
import dev.geminileft.TEGameEngine.TEGameObject;
import dev.geminileft.TEGameEngine.TouchDrag;

public final class SampleGameObjectFactory {

	public static TEGameObject createBackground(Point position) {
		TEGameObject gameObject = new TEGameObject();
		Size size = new Size(480, 854);
		RenderImage image = new RenderImage(R.drawable.table_background, null, size);
		gameObject.addComponent(image);
		gameObject.position = position;
		gameObject.size = size;
		return gameObject;
	}
	
	public static TEGameObject createPlayingCard(Point position) {
		TEGameObject gameObject = new TEGameObject();
		Size size = new Size(48, 64);
		RenderImage image = new RenderImage(R.drawable.spade_ace, null, size);
		gameObject.addComponent(image);
		gameObject.addComponent(new TouchDrag());
		gameObject.position = position;
		gameObject.size = size;
		return gameObject;
	}

	public static TEGameObject createFreeCell(Point position) {
		TEGameObject gameObject = new TEGameObject();
		Size size = new Size(48, 64);
		gameObject.addComponent(new RenderImage(R.drawable.free_cell, null, size));
		gameObject.position = position;
		gameObject.size = size;
		return gameObject;
	}

	public static TEGameObject createAceCell(Point position) {
		TEGameObject gameObject = new TEGameObject();
		Size size = new Size(48, 64);
		RenderImage image = new RenderImage(R.drawable.ace_cell, null, size);
		gameObject.addComponent(image);
		gameObject.position = position;
		gameObject.size = size;
		return gameObject;
	}

}
