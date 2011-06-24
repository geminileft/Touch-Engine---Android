package dev.geminileft.TEGameEngine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class RenderImage extends TERenderComponent {
	private int mName;
	private int mWidth = 0;
	private int mHeight = 0;
	private FloatBuffer mTextureBuffer;
	private FloatBuffer mVertexBuffer;
	private float[] coordinates = new float[8];
	private float[] vertices = new float[8];
	float mX = 0;
	float mY = 0;
	
	public RenderImage(InputStream is) {
		GL10 gl = TEGraphicsManager.getGL();
		int mTextures[] = new int[1];
		gl.glGenTextures(1, mTextures, 0);
        mName = mTextures[0];		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextures[0]);
		
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE); //GL10.GL_REPLACE);

		Bitmap bitmap = null;
		try {
			//BitmapFactory is an Android graphics utility for images
			bitmap = BitmapFactory.decodeStream(is);

		} finally {
			//Always clear and close
			try {
				is.close();
				is = null;
			} catch (IOException e) {
			}
		}
		
		mHeight = bitmap.getHeight();
		mWidth = bitmap.getWidth();
		
		Bitmap adjustedBitmap = Bitmap.createScaledBitmap(bitmap, 64, 64, false);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, adjustedBitmap, 0);
		bitmap.recycle();
		adjustedBitmap.recycle();
		final int arraySize = 8;
		final int floatSize = 4;

		float maxS = 1.0f;//(float)mWidth / 50;
		float maxT = 1.0f;//(float)mHeight / 50;
		
		coordinates[0] = 0.0f;//bottom
		coordinates[1] = maxT;//right
		coordinates[2] = maxS;//bottom
		coordinates[3] = maxT;//left
		coordinates[4] = maxS;//top
		coordinates[5] = 0.0f;//right
		coordinates[6] = 0.0f;//top
		coordinates[7] = 0.0f;//left
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(arraySize * floatSize);
		byteBuf.order(ByteOrder.nativeOrder());
		mTextureBuffer = byteBuf.asFloatBuffer();
		mTextureBuffer.put(coordinates);
		mTextureBuffer.position(0);

		final float leftX = mX -(float)mWidth / 2;
		final float rightX = leftX + mWidth;
		final float bottomY = mY - (float)mHeight / 2;
		final float topY = bottomY + mHeight;

		vertices[0] = leftX;
		vertices[1] = bottomY;
		vertices[2] = rightX;
		vertices[3] = bottomY;
		vertices[4] = rightX;
		vertices[5] = topY;
		vertices[6] = leftX;
		vertices[7] = topY;

		byteBuf = ByteBuffer.allocateDirect(arraySize * floatSize);
		byteBuf.order(ByteOrder.nativeOrder());
		mVertexBuffer = byteBuf.asFloatBuffer();
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);
	}

	public void draw(GL10 gl) {
		gl.glPushMatrix();
		gl.glTranslatef(mX, mY, 0.0f);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mName);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);			
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glPopMatrix();
	}
	
	public void update() {
		Point point = getParent().position;
		mX = point.x;
		mY = point.y;
	}
	
	public Size getSize() {
		return new Size(mWidth, mHeight);
	}
}
