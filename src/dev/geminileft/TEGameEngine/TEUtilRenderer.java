package dev.geminileft.TEGameEngine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class TEUtilRenderer implements GLSurfaceView.Renderer {
	private boolean isCreated = false;
	private boolean hasChanged = false;

    private final String mVertexShader =
        "uniform mat4 uViewMatrix;\n" +
        "uniform mat4 uProjectionMatrix;\n" +
        "attribute vec2 position;\n" +
        "attribute vec4 aPosition;\n" +
        "void main() {\n" +
        "  mat4 identityMatrix = mat4(1,0,0,0, 0,1,0,0, 0,0,1,0, position.x,position.y,0,1);\n" +
        "  gl_Position = (uProjectionMatrix * (uViewMatrix * (identityMatrix))) * aPosition;\n" +
        "}\n";

    private final String mFragmentShader =
        "precision mediump float;\n" +
        "uniform vec4 color[1500];" +
        "uniform vec4 colorSet[4096];" +
        "void main() {\n" +
        "int index = int(mod(gl_FragCoord.x, 64.0));" +
        "  gl_FragColor = color[index];\n" +
        "}\n";

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
    private int mProgram;
    private static String TAG = "GLES20TriangleRenderer";
	private TEEngine mGame;
	private float mProjectionMatrix[];
	private float mViewMatrix[];
	private int mProjectionHandle;
	private int mViewHandle;
	
    public TEUtilRenderer(TEEngine game) {
    	mGame = game;
    }

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
    	if (!isCreated) {
	        mProgram = TEManagerGraphics.createProgram(mVertexShader, mFragmentShader);
	        mProjectionHandle = TEManagerGraphics.getUniformLocation("uProjectionMatrix");
	        checkGlError("error");
	        mViewHandle = TEManagerGraphics.getUniformLocation("uViewMatrix");
	        checkGlError("error");
	        //int positionHandle = TEManagerGraphics.getAttributeLocation("aPosition");
	        checkGlError("error");
	        //GLES20.glEnableVertexAttribArray(positionHandle);
	        checkGlError("error");
			GLES20.glEnable(GL10.GL_BLEND);
			GLES20.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	        mViewMatrix = TEManagerGraphics.getViewMatrix();
	        mGame.start();
	        isCreated = true;
    	}
    }

    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
    	if (!hasChanged) {
	        GLES20.glViewport(0, 0, width, height);
	        mProjectionMatrix = TEManagerGraphics.getProjectionMatrix();
	        hasChanged = true;
	        GLES20.glUseProgram(mProgram);
	        int positionHandle = TEManagerGraphics.getAttributeLocation("aPosition");
	        checkGlError("error");
	        GLES20.glEnableVertexAttribArray(positionHandle);
	        checkGlError("glUseProgram");
    	}
    }

    public void onDrawFrame(GL10 glUnused) {
        try {
        GLES20.glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glClear( GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUniformMatrix4fv(mProjectionHandle, 1, false, mProjectionMatrix, 0);
        checkGlError("mProjectionHandle");
        GLES20.glUniformMatrix4fv(mViewHandle, 1, false, mViewMatrix, 0);
        checkGlError("mViewHandle");

        int colorHandle = TEManagerGraphics.getUniformLocation("colorSet");
        final float colorSet[] = {
        	1,1,1,1
        	,0,0,0,1
        };
        
        GLES20.glUniform4fv(colorHandle, 2, colorSet, 0);

		mGame.run();
        } catch (Exception e) {
        	Log.v("info", "info");
        }
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
