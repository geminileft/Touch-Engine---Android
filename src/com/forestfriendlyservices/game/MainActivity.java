package com.forestfriendlyservices.game;

import android.app.Activity;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import dev.geminileft.AnimationDemo2D.AnimationDemo;
import dev.geminileft.TEGameEngine.TEEngine;
import dev.geminileft.TEGameEngine.TEManagerGraphics;
import dev.geminileft.TEGameEngine.TEManagerGraphics.ScreenOrientation;
import dev.geminileft.TEGameEngine.TEUtilRenderer;

public class MainActivity extends Activity {
	private TEEngine mGame;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        GLSurfaceView view = new GLSurfaceView(this);
        Display display = getWindowManager().getDefaultDisplay(); 
   		mGame = new AnimationDemo(display.getWidth(), display.getHeight());
   		//mGame = new LightningDemo(display.getWidth(), display.getHeight());
   		//mGame = new FreeCellGame(display.getWidth(), display.getHeight());
   		mGame.setContext(this);
        view.setEGLContextClientVersion(2);
        Configuration config = getResources().getConfiguration();
        ScreenOrientation orientation = (config.orientation == Configuration.ORIENTATION_LANDSCAPE)
        	? ScreenOrientation.Landscape : ScreenOrientation.Portrait;  
        TEManagerGraphics.setScreenOrientation(orientation);
   		view.setRenderer(new TEUtilRenderer(mGame));
   		setContentView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	return mGame.onTouchEvent(event);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	boolean result = false;
    	try {
    	result = mGame.onKeyDown(keyCode, event);
    	} catch (Exception e) {
    		Log.v("info", "failed here");
    	}
    	return result;
    }
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
    	return mGame.onKeyUp(keyCode, event);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ScreenOrientation orientation = (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
    		? ScreenOrientation.Landscape : ScreenOrientation.Portrait;  
        TEManagerGraphics.setScreenOrientation(orientation);
    }

}
