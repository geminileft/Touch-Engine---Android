package dev.geminileft.LightningDemo;

import dev.geminileft.TEGameEngine.RenderColorBox;
import dev.geminileft.TEGameEngine.TEEngine;
import dev.geminileft.TEGameEngine.TEGameObject;
import dev.geminileft.TEGameEngine.TESize;

public class LightningDemo extends TEEngine {

	public LightningDemo(int width, int height) {
		super(width, height);
	}
	
	@Override
	public void start() {
		TEGameObject gameObject = new TEGameObject();
		gameObject.position.x = 32;
		gameObject.position.y = 32;
		gameObject.addComponent(new RenderColorBox(1, 1, 1, 1));
		addGameObject(gameObject);

		gameObject = new TEGameObject();
		gameObject.position.x = 100;
		gameObject.position.y = 100;
		gameObject.addComponent(new RenderColorBox(0.3f, 1.0f, 0.14f, 0.55f));
		addGameObject(gameObject);
	
	}
}
