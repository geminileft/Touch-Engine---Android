package dev.geminileft.TEGameEngine;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.util.Log;

public class TEManagerGraphics {
	private static GL10 mGL;
	private static int mWidth;
	private static int mHeight;
	
	public static void setGL(GL10 gl) {
		mGL = gl;
	}
	
	public static GL10 getGL() {
		return mGL;
	}
	
	public static void setScreenSize(int width, int height) {
		mWidth = width;
		mHeight = height;
	}
	
	public static TESize getScreenSize() {
		return new TESize(mWidth, mHeight);
	}

    public static int createProgram(String vertexSource, String fragmentSource) {
        int program = GLES20.glCreateProgram();
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

}
