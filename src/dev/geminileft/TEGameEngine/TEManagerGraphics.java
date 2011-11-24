package dev.geminileft.TEGameEngine;

import java.util.HashMap;
import java.util.LinkedList;

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
	private static HashMap<Integer, LinkedList<String>> mProgramAttributes = new HashMap<Integer, LinkedList<String>>();

	public static void createPrograms() {
		String vertexShader;
		String fragmentShader;
		int program;
		
        vertexShader = TEManagerFile.readFileContents("texture.vs");
        fragmentShader = TEManagerFile.readFileContents("texture.fs");
        program = createProgram("texture", vertexShader, fragmentShader);
        addProgramAttribute(program, "aPosition");
        addProgramAttribute(program, "aTexture");

        vertexShader = TEManagerFile.readFileContents("colorbox.vs");
		fragmentShader = TEManagerFile.readFileContents("colorbox.fs");
        program = createProgram("colorbox", vertexShader, fragmentShader);
        addProgramAttribute(program, "vertices");
        
        vertexShader = TEManagerFile.readFileContents("effectbox.vs");
		fragmentShader = TEManagerFile.readFileContents("colorbox.fs");
        program = createProgram("effectbox", vertexShader, fragmentShader);
        addProgramAttribute(program, "vertices");

	}
	
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

	private static void addProgramAttribute(int program, String attribute) {
		LinkedList<String> list = mProgramAttributes.get(program);
		if (list == null) {
			list = new LinkedList<String>();
			mProgramAttributes.put(program, list);
		}
		list.add(attribute);
	}
	
    private static int createProgram(String programName, String vertexSource, String fragmentSource) {
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
        checkGlError("glShaderSource");
        GLES20.glCompileShader(shader);
        checkGlError("glCompileShader");
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
    	if (true) {
    		Matrix.setIdentityM(viewMatrix, 0);
    		Matrix.translateM(viewMatrix, 0, 0, 0, -1);
    		//Matrix.translateM(viewMatrix, 0, -mWidth / 2, -mHeight / 2, -mHeight / 2);
    	} else {
    		Matrix.setLookAtM(viewMatrix, 0, 0, mHeight / 4, 0, 0, 0, 0, 0, 1, 0);
    	}
		return viewMatrix;
    }
    
    public static float[] getProjectionMatrix() {
    	float projectionMatrix[] = new float[16];
    	final float ratio = (float)mWidth / mHeight;
        //Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 1, mHeight / 2);
        Matrix.frustumM(projectionMatrix, 0, -.5f, .5f, -.75f, .75f, .5f, 200.0f);
        return projectionMatrix;
    }
    
    public static int getProgram(String programName) {
    	Integer intVal = mPrograms.get(programName);
    	if (intVal == null) {
    		throw new RuntimeException();
    	}
    	return intVal;
    }

    public static void switchProgram(String programName) {
        final int program = mPrograms.get(programName);
        GLES20.glUseProgram(program);
        checkGlError("glUseProgram");
        
        LinkedList<String> list = mProgramAttributes.get(program);
        if (list != null) {
        	final int size = list.size();
        	for (int i = 0;i < size;++i) {
                int positionHandle = TEManagerGraphics.getAttributeLocation(program, list.get(i));
                GLES20.glEnableVertexAttribArray(positionHandle);
                checkGlError("glEnableVertexAttribArray");        		
        	}
        }

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
