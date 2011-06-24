package dev.geminileft.opengltest;

import android.content.Context;
import dev.geminileft.TEGameEngine.Point;
import dev.geminileft.TEGameEngine.RenderColorBox;
import dev.geminileft.TEGameEngine.RenderImage;
import dev.geminileft.TEGameEngine.Size;
import dev.geminileft.TEGameEngine.TEEngine;
import dev.geminileft.TEGameEngine.TEGameObject;
import dev.geminileft.TEGameEngine.TouchBounce;
import dev.geminileft.TEGameEngine.TouchDrag;

public class SampleGame extends TEEngine {

	public SampleGame(Context context) {
		super(context);
	}
	
	@Override
	public void start() {
		TEGameObject gameObject;
		RenderImage image;
        RenderColorBox colorBox;
        TouchDrag touchDrag;
        TouchBounce touchBounce;

        int x = 20;
    	for (int counter = 0; counter < 4; ++counter) {
    		gameObject = new TEGameObject();
    		image = new RenderImage(getContext().getResources().openRawResource(R.drawable.freecell));
    		gameObject.addComponent(image);
    		gameObject.position = new Point(x, 420);
    		addGameObject(gameObject);

    		gameObject = new TEGameObject();
    		image = new RenderImage(getContext().getResources().openRawResource(R.drawable.freecell));
    		gameObject.addComponent(image);
    		gameObject.position = new Point(x, 365);
    		addGameObject(gameObject);

    		gameObject = new TEGameObject();
    		image = new RenderImage(getContext().getResources().openRawResource(R.drawable.c_a));
    		touchDrag = new TouchDrag();
    		gameObject.addComponent(image);
    		gameObject.addComponent(touchDrag);
    		gameObject.position = new Point(x, 365);
    		gameObject.size = image.getSize();
    		addGameObject(gameObject);
    		x += 40;
    	}

    	for (int counter = 0; counter < 4; ++counter) {
    		gameObject = new TEGameObject();
    		image = new RenderImage(getContext().getResources().openRawResource(R.drawable.suit_stack));
    		gameObject.addComponent(image);
    		gameObject.position = new Point(x, 420);
    		addGameObject(gameObject);

    		gameObject = new TEGameObject();
    		image = new RenderImage(getContext().getResources().openRawResource(R.drawable.freecell));
    		gameObject.addComponent(image);
    		gameObject.position = new Point(x, 365);
    		addGameObject(gameObject);

    		gameObject = new TEGameObject();
    		colorBox = new RenderColorBox(new Size(34, 47));
            touchBounce = new TouchBounce();
    		gameObject.addComponent(colorBox);
            gameObject.addComponent(touchBounce);
    		gameObject.position = new Point(x, 365);
    		gameObject.size = new Size(34, 47);		
    		addGameObject(gameObject);
    		x += 40;
    	}
	}
}
