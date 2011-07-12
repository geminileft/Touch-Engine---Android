package dev.geminileft.TEGameEngine;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

public abstract class TEEngine {
	private Context mContext;
	private Vector<TEGameObject> mGameObjects;
	private Vector<TEManager> mManagers;
	private int mHeight;
	private int mWidth;

	public abstract void start();

	public TEEngine(Context context) {
		mContext = context;
        TEStaticSettings.setContext(context);
		mGameObjects = new Vector<TEGameObject>();
		mManagers = new Vector<TEManager>();
        TEManagerTouch touchManager = TEManagerTouch.sharedManager();
        TEManagerStack stackManager = TEManagerStack.sharedManager();
        TEManagerSound soundManager = TEManagerSound.sharedManager();
        TEManagerRender renderManager = TEManagerRender.sharedManager();
        mManagers.add(touchManager);
        mManagers.add(stackManager);
        mManagers.add(soundManager);
        mManagers.add(renderManager);
	}
	
	public final void run() {
		final int managerCount = mManagers.size();
		for (int count = 0;count < managerCount; ++count) {
			mManagers.get(count).update();
		}
	}
	
	public void setGraphicManager(GL10 gl) {
		TEManagerGraphics.setGL(gl);
	}
	
	public Context getContext() {
		return mContext;
	}
	
	public final void addGameObject(TEGameObject gameObject) {
		TEManagerRender renderManager = TEManagerRender.sharedManager();
		TEManagerTouch touchManager = TEManagerTouch.sharedManager();
		TEManagerSound soundManager = TEManagerSound.sharedManager();
		TEManagerStack stackManager = TEManagerStack.sharedManager();
		TEComponentContainer components = gameObject.getComponents();
		final int size = components.size();
		//Iterator<TEComponent> iterator = components.iterator();
		TEComponent component;
		for(int i = 0; i < size; ++i) {
			component = components.get(i);
			if (component instanceof TEComponentRender) {
				renderManager.addComponent(component);
			} else if (component instanceof TEComponentTouch) {
				touchManager.addComponent(component);
			} else if (component instanceof TEComponentSound) {
				soundManager.addComponent(component);
			} else if (component instanceof TEComponentStack) {
				stackManager.addComponent(component);
			}
		}
		mGameObjects.add(gameObject);
	}

    public boolean onTouchEvent(MotionEvent event) {
    	TEManagerInput inputManager = TEManagerInput.sharedManager();
    	int pointerId;
    	float x;
    	float y;
    	final int actionId = event.getAction();
    	TEInputTouch touch;
    	
    	final int pointerCount = event.getPointerCount();
    	for (int i = 0;i < pointerCount; ++i) {
    		pointerId = event.getPointerId(i);
    		x = event.getX(pointerId);
    		y = mHeight - event.getY(pointerId);    		
        	switch (actionId) {
			case MotionEvent.ACTION_DOWN:
				touch = new TEInputTouch(pointerId, x, y);
				inputManager.beginTouch(touch);
				break;
			case MotionEvent.ACTION_MOVE:
				touch = new TEInputTouch(pointerId, x, y);
				inputManager.moveTouch(touch);
				break;
			case MotionEvent.ACTION_UP:
				touch = new TEInputTouch(pointerId, x, y);
				inputManager.endTouch(touch);
				break;
    		default:
    			Log.v("info", "Couldn't identify " + String.valueOf(actionId));
    			break;
        	}
    	}
    	return false;
    }
    
    public void setScreenSize(int width, int height) {
    	mHeight = height;
    	mWidth = width;
		TEManagerGraphics.setScreenSize(width, height);
    }
    
    public Size getScreenSize() {
    	return new Size(mWidth, mHeight);
    }
}
