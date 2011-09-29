package dev.geminileft.TEGameEngine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import dev.geminileft.opengltest.R;

public class TEUtilRenderer implements GLSurfaceView.Renderer {
	private static final int POSITION_SIZE = 2;
	private static final int TEXTURE_SIZE = 2;
	
    public TEUtilRenderer() {
    }

    public void onDrawFrame(GL10 glUnused) {
        GLES20.glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glClear( GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(mProgram);
        checkGlError("glUseProgram");

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mRenderImage.mDrawable.mTexture.mName);

        GLES20.glVertexAttribPointer(maPositionHandle, POSITION_SIZE, GLES20.GL_FLOAT, false,
                0, mDrawable.mPositionBuffer);
        checkGlError("glVertexAttribPointer maPosition");        
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        checkGlError("glEnableVertexAttribArray maPositionHandle");
        
        GLES20.glVertexAttribPointer(maTextureHandle, TEXTURE_SIZE, GLES20.GL_FLOAT, false,
                0, mDrawable.mCropBuffer);
        checkGlError("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(maTextureHandle);
        checkGlError("glEnableVertexAttribArray maTextureHandle");

        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 100, 100, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);

        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        checkGlError("glDrawArrays");
        mRenderImage.draw();
    }

    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        mProjMatrix = TEManagerGraphics.getProjectionMatrix();
        mViewMatrix = TEManagerGraphics.getViewMatrix();
    }

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        mProgram = TEManagerGraphics.createProgram(mVertexShader, mFragmentShader);
        maPositionHandle = TEManagerGraphics.getAttributeLocation("aPosition");
        maTextureHandle = TEManagerGraphics.getAttributeLocation("aTexture");
        muMVPMatrixHandle = TEManagerGraphics.getUniformLocation("uMVPMatrix");
        
        mDrawable = new TEUtilDrawable(R.raw.robot, TESize.make(128, 128), TEPoint.make(0, 0));
        mRenderImage = new RenderImage(R.raw.robot);
    }

    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
        	String errorMsg = GLES20.glGetProgramInfoLog(mProgram);
            Log.e(TAG, op + ": glError " + error + errorMsg);
            throw new RuntimeException(op + ": glError " + error);
        }
    }

    private final String mVertexShader =
        "uniform mat4 uMVPMatrix;\n" +
        "attribute vec4 aPosition;\n" +
        "attribute vec2 aTexture;\n" +
        "varying vec2 vTextureCoord;\n" +
        "void main() {\n" +
        "  gl_Position = uMVPMatrix * aPosition;\n" +
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

    private float[] mMVPMatrix = new float[16];
    private float[] mProjMatrix = new float[16];
    private float[] mModelMatrix = new float[16];
    private float[] mViewMatrix = new float[16];

    private int mProgram;
    private int muMVPMatrixHandle;
    private int maPositionHandle;
    private int maTextureHandle;
    private TEUtilDrawable mDrawable;
    private RenderImage mRenderImage;

    private static String TAG = "GLES20TriangleRenderer";
}
