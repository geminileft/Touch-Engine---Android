package dev.geminileft.AnimationDemo2D;

import dev.geminileft.TEGameEngine.RenderImage;
import dev.geminileft.TEGameEngine.TEEngine;
import dev.geminileft.TEGameEngine.TEGameObject;
import dev.geminileft.TEGameEngine.TEPoint;
import dev.geminileft.opengltest.R;

public class AnimationDemo extends TEEngine {

	public AnimationDemo(int width, int height) {
		super(width, height);
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		TEGameObject gameObject = new TEGameObject();
		gameObject.addComponent(new RenderImage(R.drawable.rabbit_redone, null, null));
		gameObject.position = TEPoint.make(200, 200);
        addGameObject(gameObject);
	}
}
