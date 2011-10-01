package dev.geminileft.TEGameEngine;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public class TEManagerGraphics {
	
	public enum ScreenOrientation {
		Landscape
		, Portrait
	};
	
	private static int mWidth;
	private static int mHeight;
	private static int mProgram;
	private static ScreenOrientation mScreenOrientation;
	
	public static void setScreenOrientation(ScreenOrientation orientation) {
		mScreenOrientation = orientation;
	}
	public static int getProgram() {
		return mProgram;
	}
	
	public static int getAttributeLocation(String attribute) {
		return GLES20.glGetAttribLocation(mProgram, attribute);
	}
	
	public static int getUniformLocation(String uniform) {
		return GLES20.glGetUniformLocation(mProgram, uniform);
	}
	
	public static void setScreenSize(int width, int height) {
		mWidth = width;
		mHeight = height;
	}
	
	public static TESize getScreenSize() {
		return new TESize(mWidth, mHeight);
	}

    public static int createProgram(String vertexSource, String fragmentSource) {
        mProgram = GLES20.glCreateProgram();
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        GLES20.glAttachShader(mProgram, vertexShader);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] != GLES20.GL_TRUE) {
            Log.e("Error", "Could not link program: ");
            Log.e("Error", GLES20.glGetProgramInfoLog(mProgram));
            GLES20.glDeleteProgram(mProgram);
            mProgram = 0;
        }

        return mProgram;
    }

    private static int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        GLES20.glShaderSource(shader, source);
        GLES20.glCompileShader(shader);
        return shader;
    }

    public static void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
        	String errorMsg = GLES20.glGetProgramInfoLog(mProgram);
            Log.e("info", op + ": glError " + error + errorMsg);
            throw new RuntimeException(op + ": glError " + error);
        }
    }
    
    public static float[] getViewMatrix() {
    	float viewMatrix[] = new float[16];
        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.translateM(viewMatrix, 0, -mWidth / 2, -mHeight / 2, -mHeight / 2);
        return viewMatrix;
    }
    
    public static float[] getProjectionMatrix() {
    	float projectionMatrix[] = new float[16];
    	final float ratio = (float)mWidth / mHeight;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 1, mHeight / 2);
        return projectionMatrix;
    }
    
 

}
