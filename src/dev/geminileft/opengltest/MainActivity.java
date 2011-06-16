package dev.geminileft.opengltest;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends Activity {
	private SampleGame mGame;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        GLSurfaceView view = new GLSurfaceView(this);
   		view.setRenderer(new GameRenderer());
   		setContentView(view);
   		mGame = new SampleGame(MainActivity.this);
    }

    private class GameRenderer implements GLSurfaceView.Renderer {

		public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
			mGame.setGraphicManager(gl);
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
			//gl.glClearColor(0.05f, 0.5f, 1.0f, 1.0f);
			gl.glClearColor(1.0f, 1.0f, 0.0f, 1.0f);
	        gl.glShadeModel(GL10.GL_FLAT);
	        gl.glDisable(GL10.GL_DEPTH_TEST);
	        gl.glEnable(GL10.GL_TEXTURE_2D);
	        gl.glDisable(GL10.GL_DITHER);
	        gl.glDisable(GL10.GL_LIGHTING);
	        gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);
	        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	        mGame.start();
		}

		public void onDrawFrame(GL10 gl) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			mGame.run();
		}

		public void onSurfaceChanged(GL10 gl, int width, int height) {
			final float ratio = (float)width / height;

			gl.glViewport(0, 0, width, height);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glFrustumf(-ratio, ratio, -1, 1, 1, height / 2);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glTranslatef(-width / 2, - height / 2, -height / 2);
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
	}
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	Log.v("info", "Just a test.");
    	return false;
    }

}