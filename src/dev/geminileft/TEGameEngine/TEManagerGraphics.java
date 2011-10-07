package dev.geminileft.TEGameEngine;

import java.util.HashMap;

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
	private static int mCurrentProgram;
	private static ScreenOrientation mScreenOrientation;
	private static HashMap<String, Integer> mPrograms = new HashMap<String, Integer>();
	
	public static void setScreenOrientation(ScreenOrientation orientation) {
		mScreenOrientation = orientation;
	}
	public static int getAttributeLocation(int program, String attribute) {
		return GLES20.glGetAttribLocation(program, attribute);
	}
	
	public static int getUniformLocation(int program, String uniform) {
		return GLES20.glGetUniformLocation(program, uniform);
	}
	
	public static void setScreenSize(int width, int height) {
		mWidth = width;
		mHeight = height;
	}
	
	public static TESize getScreenSize() {
		return new TESize(mWidth, mHeight);
	}

    public static int createProgram(String programName, String vertexSource, String fragmentSource) {
        int program = GLES20.glCreateProgram();
        mPrograms.put(programName, program);
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        GLES20.glAttachShader(program, vertexShader);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glLinkProgram(program);
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] != GLES20.GL_TRUE) {
            Log.e("Error", "Could not link program: ");
            Log.e("Error", GLES20.glGetProgramInfoLog(program));
            GLES20.glDeleteProgram(program);
            program = 0;
        }
        return program;
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
        	String errorMsg = GLES20.glGetProgramInfoLog(mCurrentProgram);
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
    
    public static int getProgram(String programName) {
    	return mPrograms.get(programName);
    }

    public static void switchProgram(String programName) {
        final int program = mPrograms.get(programName);
        GLES20.glUseProgram(program);
        checkGlError("glUseProgram");

        int positionHandle = TEManagerGraphics.getAttributeLocation(program, "vertices");
        GLES20.glEnableVertexAttribArray(positionHandle);
        checkGlError("glEnableVertexAttribArray");

        final float projectionMatrix[] = TEManagerGraphics.getProjectionMatrix();
        final float viewMatrix[] = TEManagerGraphics.getViewMatrix();

        final int projectionHandle = getUniformLocation(program, "uProjectionMatrix");
        GLES20.glUniformMatrix4fv(projectionHandle, 1, false, projectionMatrix, 0);
        checkGlError("glUniformMatrix4fv");

        final int viewHandle = getUniformLocation(program, "uViewMatrix");
        GLES20.glUniformMatrix4fv(viewHandle, 1, false, viewMatrix, 0);
        checkGlError("glUniformMatrix4fv");
    }
}
