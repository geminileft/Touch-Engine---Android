package dev.geminileft.TEGameEngine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class TEUtilRenderer implements GLSurfaceView.Renderer {
/*
    private final String mVertexShader =
        "uniform mat4 uViewMatrix;\n" +
        "uniform mat4 uProjectionMatrix;\n" +
        "attribute vec4 aPosition;\n" +
        "attribute vec2 aTexture;\n" +
        "attribute vec2 aCoords;\n" +
        "varying vec2 vTextureCoord;\n" +
        "void main() {\n" +
        "  mat4 identityMatrix = mat4(1,0,0,0, 0,1,0,0, 0,0,1,0, aCoords.x,aCoords.y,0,1);\n" +
        "  gl_Position = (uProjectionMatrix * (uViewMatrix * (identityMatrix))) * aPosition;\n" +
        "  vTextureCoord = aTexture;\n" +
        "}\n";
*/
/*
    private final String mFragmentShader =
        "precision mediump float;\n" +
        "varying vec2 vTextureCoord;\n" +
        "uniform sampler2D sTexture;\n" +
        "void main() {\n" +
        "  float modulus = mod(gl_FragCoord.x, 2.0);\n" +
        "  int index = int(modulus);\n" +
        "  gl_FragColor = texture2D(sTexture, vTextureCoord);\n" +
        "}\n";
*/
	private TEEngine mGame;
	
    public TEUtilRenderer(TEEngine game) {
    	mGame = game;
    }

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        GLES20.glEnable(GL10.GL_BLEND);
		GLES20.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		TEManagerGraphics.createPrograms();
        mGame.start();
    }

    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    public void onDrawFrame(GL10 glUnused) {
        try {
        GLES20.glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glClear( GLES20.GL_COLOR_BUFFER_BIT);

		mGame.run();
        } catch (Exception e) {
        	Log.v("info", "info");
        }
    }
}
