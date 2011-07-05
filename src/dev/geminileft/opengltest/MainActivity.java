package dev.geminileft.opengltest;

import java.util.HashMap;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import dev.geminileft.TEGameEngine.Point;
import dev.geminileft.TEGameEngine.TEGameRenderer;

public class MainActivity extends Activity {
	private SampleGame mGame;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        GLSurfaceView view = new GLSurfaceView(this);
        Display display = getWindowManager().getDefaultDisplay(); 
        int width = display.getWidth();
        int height = display.getHeight();
   		mGame = new SampleGame(this, width, height);
   		view.setRenderer(new TEGameRenderer(mGame));
   		setContentView(view);
   		Point point = new Point(100.0f, 200.0f);
   		HashMap<String, Point> map = new HashMap<String, Point>();
   		map.put("index1", point);
   		HashMap<String, Point> map2 = new HashMap<String, Point>(map);
   		point.x = 200;
   		Log.v("info", "map Count: " + String.valueOf(map.get("index1").x) + " map2 Count: " + String.valueOf(map2.get("index1").x));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	return mGame.onTouchEvent(event);
    }
}
