package dev.geminileft.TEGameEngine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class TEUtilRenderer implements GLSurfaceView.Renderer {
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

    private final String mFragmentShader =
        "precision mediump float;\n" +
        "varying vec2 vTextureCoord;\n" +
        "uniform sampler2D sTexture;\n" +
        "uniform sampler2D sTexture2;\n" +
        "void main() {\n" +
        "  gl_FragColor = texture2D(sTexture, vTextureCoord);\n" +
        "}\n";

    private int mProgram;
    private static String TAG = "GLES20TriangleRenderer";
	private TEEngine mGame;
	private float mProjectionMatrix[];
	private float mViewMatrix[];
	private int mProjectionHandle;
	private int mViewHandle;
	//private int maPositionHandle;
	private int maTextureHandle;
	
    public TEUtilRenderer(TEEngine game) {
    	mGame = game;
    }

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        mProgram = TEManagerGraphics.createProgram(mVertexShader, mFragmentShader);
        mProjectionHandle = TEManagerGraphics.getUniformLocation("uProjectionMatrix");
        checkGlError("error");
        mViewHandle = TEManagerGraphics.getUniformLocation("uViewMatrix");
        checkGlError("error");
        int positionHandle = TEManagerGraphics.getAttributeLocation("aPosition");
        checkGlError("error");
        maTextureHandle = TEManagerGraphics.getAttributeLocation("aTexture");
        checkGlError("error");
        GLES20.glEnableVertexAttribArray(positionHandle);
        checkGlError("error");
        TEManagerTexture.setPositionHandle(positionHandle);
        GLES20.glEnableVertexAttribArray(maTextureHandle);
        checkGlError("error");
        TEManagerTexture.setCropHandle(maTextureHandle);
		GLES20.glEnable(GL10.GL_BLEND);
		GLES20.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        mGame.start();
    }

    public void onDrawFrame(GL10 glUnused) {
        try {
        //Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        GLES20.glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glClear( GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(mProgram);
        checkGlError("glUseProgram");
        GLES20.glUniformMatrix4fv(mProjectionHandle, 1, false, mProjectionMatrix, 0);
        checkGlError("mProjectionHandle");
        GLES20.glUniformMatrix4fv(mViewHandle, 1, false, mViewMatrix, 0);
        checkGlError("mViewHandle");

		mGame.run();
        } catch (Exception e) {
        	Log.v("info", "info");
        }
    }

    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        mProjectionMatrix = TEManagerGraphics.getProjectionMatrix();
        mViewMatrix = TEManagerGraphics.getViewMatrix();
    }

    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
        	String errorMsg = GLES20.glGetProgramInfoLog(mProgram);
            Log.e(TAG, op + ": glError " + error + errorMsg);
            throw new RuntimeException(op + ": glError " + error);
        }
    }
}
