package dev.geminileft.TEGameEngine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.util.Log;
//import android.os.SystemClock;

public class TEGameRenderer implements GLSurfaceView.Renderer {
	private TEEngine mGame;
	private long mPreviousTime;

	public TEGameRenderer(TEEngine game) {
		mGame = game;
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		mGame.setGraphicManager(gl);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glShadeModel(GL10.GL_FLAT);
        gl.glDisable(GL10.GL_DEPTH_TEST);
        gl.glDisable(GL10.GL_DITHER);
        gl.glDisable(GL10.GL_LIGHTING);
        gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);
		//set and forget
        //required for transparency
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		//always drawing textures...enable once
		gl.glEnable(GL10.GL_TEXTURE_2D);
		//required for vertex/textures
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glClearColor(1.0f, 1.0f, 0.0f, 1.0f);
		//gl.glClearColor(0.05f, 0.5f, 1.0f, 1.0f);
        mGame.start();
        //mPreviousTime = SystemClock.uptimeMillis();
	}

	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		mGame.run();
		final long currentTime = SystemClock.uptimeMillis();
		final long dt = currentTime - mPreviousTime;
		Log.v("TEGameRenderer.onDrawFrame", Long.valueOf(dt).toString());
		mPreviousTime = currentTime;
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		//width *= 2;
		//height *= 2;
		final boolean useOrtho = false;
		final int scaleFactor = 1;
		final int zDepth = height / (2 / scaleFactor);
		mGame.setScreenSize(width, height);
		final float ratio = (float)width / height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		if (useOrtho) {
			gl.glOrthof(0.0f, width, 0.0f, height, 0.0f, 1.0f);
		} else {
			gl.glFrustumf(-ratio, ratio, -1, 1, 1, zDepth);
		}
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		if (!useOrtho) {
			gl.glTranslatef(-width / 2, -height / 2, -zDepth);				
		}
	}
}
