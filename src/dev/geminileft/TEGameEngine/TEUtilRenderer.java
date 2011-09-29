package dev.geminileft.TEGameEngine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;
import dev.geminileft.opengltest.R;

public class TEUtilRenderer implements GLSurfaceView.Renderer {
	private static final int POSITION_SIZE = 2;
	private static final int TEXTURE_SIZE = 2;
	private static final int VERTEX_SIZE = POSITION_SIZE + TEXTURE_SIZE;
	
    public TEUtilRenderer(Context context) {
        mContext = context;
    }

    public void onDrawFrame(GL10 glUnused) {
        // Ignore the passed-in GL10 interface, and use the GLES20
        // class's static methods instead.
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glClear( GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(mProgram);
        checkGlError("glUseProgram");

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mDrawable.mTexture.mName);

        mTriangleVertices.position(TRIANGLE_VERTICES_DATA_POS_OFFSET);
        //GLES20.glVertexAttribPointer(maPositionHandle, POSITION_SIZE, GLES20.GL_FLOAT, false,
        //        TRIANGLE_VERTICES_DATA_STRIDE_BYTES, mTriangleVertices);
        GLES20.glVertexAttribPointer(maPositionHandle, POSITION_SIZE, GLES20.GL_FLOAT, false,
                0, mDrawable.mPositionBuffer);
        checkGlError("glVertexAttribPointer maPosition");        
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        checkGlError("glEnableVertexAttribArray maPositionHandle");
        
        mTriangleVertices.position(TRIANGLE_VERTICES_DATA_UV_OFFSET);
        /*
        GLES20.glVertexAttribPointer(maTextureHandle, TEXTURE_SIZE, GLES20.GL_FLOAT, false,
                TRIANGLE_VERTICES_DATA_STRIDE_BYTES, mTriangleVertices);
        */
        GLES20.glVertexAttribPointer(maTextureHandle, TEXTURE_SIZE, GLES20.GL_FLOAT, false,
                0, mDrawable.mCropBuffer);
        checkGlError("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(maTextureHandle);
        checkGlError("glEnableVertexAttribArray maTextureHandle");

        //long time = SystemClock.uptimeMillis() % 4000L;
        //float angle = 0.090f * ((int) time);
        //Matrix.setRotateM(mMMatrix, 0, angle, 0, 0, 1.0f);
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 427, 240, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);

        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        //GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, 4, GLES20.GL_FLOAT, null);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        checkGlError("glDrawArrays");
    }

    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Ignore the passed-in GL10 interface, and use the GLES20
        // class's static methods instead.
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 1, height / 2);
        Matrix.setIdentityM(mViewMatrix, 0);
        Matrix.translateM(mViewMatrix, 0, -width / 2, -height / 2, -height / 2);
        //Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
    }

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        mProgram = TEManagerGraphics.createProgram(mVertexShader, mFragmentShader);
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        maTextureHandle = GLES20.glGetAttribLocation(mProgram, "aTexture");
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);

        mTextureID = textures[0];
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_REPEAT);

        InputStream is = mContext.getResources()
            .openRawResource(R.raw.robot);
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
            } catch(IOException e) {
                // Ignore.
            }
        }

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        final float[] verticesData = {
                // X, Y, Z, U, V
        	-32.0f,  -32.0f, 0.0f, 1.0f
    		, -32.0f, 32.0f, 0.0f, 0.0f
    		, 32.0f,  -32.0f, 1.0f, 1.0f
    		, 32.0f, 32.0f, 1.0f, 0.0f
        };
        mTriangleVertices = ByteBuffer.allocateDirect(verticesData.length
                * FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTriangleVertices.put(verticesData).position(0);
        mDrawable = new TEUtilDrawable(R.raw.robot, TESize.make(128, 128), TEPoint.make(0, 0));
    }


    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
        	String errorMsg = GLES20.glGetProgramInfoLog(mProgram);
            Log.e(TAG, op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }

    private static final int FLOAT_SIZE_BYTES = 4;
    private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = VERTEX_SIZE * FLOAT_SIZE_BYTES;
    private static final int TRIANGLE_VERTICES_DATA_POS_OFFSET = 0;
    private static final int TRIANGLE_VERTICES_DATA_UV_OFFSET = POSITION_SIZE;
    
    private FloatBuffer mTriangleVertices;

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
    private int mTextureID;
    private int muMVPMatrixHandle;
    private int maPositionHandle;
    private int maTextureHandle;
    private TEUtilDrawable mDrawable;

    private Context mContext;
    private static String TAG = "GLES20TriangleRenderer";
}
