package dev.geminileft.opengltest;

import dev.geminileft.TEGameEngine.Point;
import dev.geminileft.TEGameEngine.RenderImage;
import dev.geminileft.TEGameEngine.Size;
import dev.geminileft.TEGameEngine.SoundTouch;
import dev.geminileft.TEGameEngine.TEGameObject;
import dev.geminileft.TEGameEngine.TouchDrag;

public final class SampleGameObjectFactory {
	
	public static TEGameObject createPlayingCard(Point position) {
		TEGameObject gameObject = new TEGameObject();
		Size size = new Size(48, 64);
		RenderImage image = new RenderImage(R.drawable.spade_ace, null, size);
		gameObject.addComponent(image);
		gameObject.addComponent(new TouchDrag());
		gameObject.addComponent(new SoundTouch(R.raw.sound));
		gameObject.position = position;
		gameObject.size = size;
		return gameObject;
	}
}
