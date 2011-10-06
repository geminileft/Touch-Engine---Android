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
		//RenderColorBox colorBox = new RenderColorBox(0, 1, 1, 1, TESize.make(64, 64));
		TEGameObject gameObject = new TEGameObject();
		gameObject.position.x = 200;
		gameObject.position.y = 200;
		gameObject.addComponent(new RenderColorBox(0, 0, 0, 1));
		//gameObject.addComponent(colorBox);
		addGameObject(gameObject);
	}
}
