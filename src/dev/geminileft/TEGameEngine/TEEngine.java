package dev.geminileft.TEGameEngine;

import java.util.Iterator;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public abstract class TEEngine {
	private Context mContext;
	private Vector<TEGameObject> mGameObjects;

	public TEEngine(Context context) {
		mContext = context;
		mGameObjects = new Vector<TEGameObject>();
	}
	
	public abstract void start();
	public abstract void run();
	
	public void setGraphicManager(GL10 gl) {
		TEGraphicsManager.setGL(gl);
	}
	
	public Context getContext() {
		return mContext;
	}
	
	public final void addGameObject(TEGameObject gameObject) {
		TERenderManager renderManager = TERenderManager.sharedManager();
		Vector<TEComponent> components = gameObject.getComponents();
		Iterator<TEComponent> iterator = components.iterator();
		TEComponent component;
		while (iterator.hasNext()) {
			component = iterator.next();
			if (component instanceof Image) {
				renderManager.addComponent(component);
			}
		}
		mGameObjects.add(gameObject);
	}
}
