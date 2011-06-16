package dev.geminileft.opengltest;

import android.content.Context;
import dev.geminileft.TEGameEngine.Image;
import dev.geminileft.TEGameEngine.TEDataPoint;
import dev.geminileft.TEGameEngine.TEEngine;
import dev.geminileft.TEGameEngine.TEGameObject;
import dev.geminileft.TEGameEngine.TERenderManager;

public class SampleGame extends TEEngine {

	public SampleGame(Context context) {
		super(context);
	}
	
	@Override
	public void start() {
		TEGameObject gameObject;
		Image image;
        TEDataPoint dataPoint;

    	int x = 20;
    	for (int counter = 0; counter < 4; ++counter) {
    		gameObject = new TEGameObject();
    		image = new Image(getContext().getResources().openRawResource(R.drawable.freecell));
    		gameObject.addComponent(image);
            dataPoint = new TEDataPoint(x, 420);
    		gameObject.setAttribute("position", dataPoint);
    		addGameObject(gameObject);

    		gameObject = new TEGameObject();
    		image = new Image(getContext().getResources().openRawResource(R.drawable.freecell));
    		gameObject.addComponent(image);
    		dataPoint = new TEDataPoint(x, 365);
    		gameObject.setAttribute("position", dataPoint);
    		addGameObject(gameObject);

    		gameObject = new TEGameObject();
    		image = new Image(getContext().getResources().openRawResource(R.drawable.c_a));
    		gameObject.addComponent(image);
    		dataPoint = new TEDataPoint(x, 365);
    		gameObject.setAttribute("position", dataPoint);
    		addGameObject(gameObject);
    		x += 40;
    	}

    	for (int counter = 0; counter < 4; ++counter) {
    		gameObject = new TEGameObject();
    		image = new Image(getContext().getResources().openRawResource(R.drawable.suit_stack));
    		gameObject.addComponent(image);
            dataPoint = new TEDataPoint(x, 420);
    		gameObject.setAttribute("position", dataPoint);
    		addGameObject(gameObject);

    		gameObject = new TEGameObject();
    		image = new Image(getContext().getResources().openRawResource(R.drawable.freecell));
    		gameObject.addComponent(image);
    		dataPoint = new TEDataPoint(x, 365);
    		gameObject.setAttribute("position", dataPoint);
    		addGameObject(gameObject);

    		x += 40;
    	}
	}

	@Override
	public void run() {
        TERenderManager renderManager = TERenderManager.sharedManager();
        renderManager.update();
	}
}
