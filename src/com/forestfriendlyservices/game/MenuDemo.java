package com.forestfriendlyservices.game;

import dev.geminileft.TEGameEngine.RenderColorBox;
import dev.geminileft.TEGameEngine.TEEngine;
import dev.geminileft.TEGameEngine.TEGameObject;

public class MenuDemo extends TEEngine {

	public MenuDemo(int width, int height) {
		super(width, height);
	}

	@Override
	public void start() {
		TEGameObject gameObject = new TEGameObject();

		gameObject = new TEGameObject();		
		gameObject.position.x = 427;
		gameObject.position.y = 240;
		gameObject.addComponent(new RenderColorBox(0.0f, 0.0f, 0.0f, 0.5f));
		addGameObject(gameObject);		
	}

}
