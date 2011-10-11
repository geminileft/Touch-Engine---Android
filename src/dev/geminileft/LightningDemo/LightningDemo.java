package dev.geminileft.LightningDemo;

import dev.geminileft.TEGameEngine.RenderColorBox;
import dev.geminileft.TEGameEngine.RenderPoints;
import dev.geminileft.TEGameEngine.TEEngine;
import dev.geminileft.TEGameEngine.TEGameObject;

public class LightningDemo extends TEEngine {

	public LightningDemo(int width, int height) {
		super(width, height);
	}
	
	@Override
	public void start() {
		TEGameObject gameObject = new TEGameObject();

		gameObject = new TEGameObject();		
		gameObject.position.x = 240;
		gameObject.position.y = 427;
		gameObject.addComponent(new RenderColorBox(0.0f, 0.0f, 0.0f, 1.0f));
		addGameObject(gameObject);

		gameObject = new TEGameObject();
		gameObject.position.x = 100;
		gameObject.position.y = 100;
		gameObject.addComponent(new RenderColorBox(1.0f, 0.0f, 0.0f, 1.0f));
		addGameObject(gameObject);	

		gameObject = new TEGameObject();
		gameObject.addComponent(new RenderPoints());
		gameObject.position.x = 240;
		gameObject.position.y = 427;
		addGameObject(gameObject);
	}
}
