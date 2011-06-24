package dev.geminileft.TEGameEngine;

import java.util.Iterator;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

public abstract class TEEngine {
	private Context mContext;
	private Vector<TEGameObject> mGameObjects;
	private Vector<TEManager> mManagers;
	private int mWidth;
	private int mHeight;

	public TEEngine(Context context) {
		mContext = context;
		mGameObjects = new Vector<TEGameObject>();
		mManagers = new Vector<TEManager>();
        TETouchManager touchManager = TETouchManager.sharedManager();
        TERenderManager renderManager = TERenderManager.sharedManager();
        mManagers.add(touchManager);
        mManagers.add(renderManager);
	}
	
	public abstract void start();

	public final void run() {
		final int managerCount = mManagers.size();
		for (int count = 0;count < managerCount; ++count) {
			mManagers.get(count).update();
		}
	}
	
	public void setGraphicManager(GL10 gl) {
		TEGraphicsManager.setGL(gl);
	}
	
	public Context getContext() {
		return mContext;
	}
	
	public final void addGameObject(TEGameObject gameObject) {
		TERenderManager renderManager = TERenderManager.sharedManager();
		TETouchManager touchManager = TETouchManager.sharedManager();
		Vector<TEComponent> components = gameObject.getComponents();
		Iterator<TEComponent> iterator = components.iterator();
		TEComponent component;
		while (iterator.hasNext()) {
			component = iterator.next();
			if (component instanceof TERenderComponent) {
				renderManager.addComponent(component);
			} else if (component instanceof TETouchComponent) {
				touchManager.addComponent(component);
			}
		}
		mGameObjects.add(gameObject);
	}
    public boolean onTouchEvent(MotionEvent event) {
    	TEInputManager inputManager = TEInputManager.sharedManager();
    	int pointerId;
    	float x;
    	float y;
    	String message;
    	final int actionId = event.getAction();
    	final int actionMask = event.getActionMasked();
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
    	mWidth = width;
    	mHeight = height;
		TEGraphicsManager.setScreenSize(width, height);
    }
}
