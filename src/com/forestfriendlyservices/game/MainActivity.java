package com.forestfriendlyservices.game;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import dev.geminileft.AnimationDemo2D.AnimationDemo;
import dev.geminileft.TEGameEngine.TEEngine;
import dev.geminileft.TEGameEngine.TEGameRenderer;

public class MainActivity extends Activity {
	private TEEngine mGame;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        GLSurfaceView view = new GLSurfaceView(this);
        Display display = getWindowManager().getDefaultDisplay(); 
   		//mGame = new FreeCellGame(display.getWidth(), display.getHeight());
        mGame = new AnimationDemo(display.getWidth(), display.getHeight());
   		mGame.setContext(this);
   		view.setRenderer(new TEGameRenderer(mGame));
   		setContentView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	return mGame.onTouchEvent(event);
    }
}