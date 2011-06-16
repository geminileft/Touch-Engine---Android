package dev.geminileft.TEGameEngine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class TEDrawableComponent extends TEComponent {
	private int mName;
	private int mWidth = 0;
	private int mHeight = 0;
	private int mX = 0;
	private int mY = 0;
	private GL10 mGL;
	
	private final float coordinates[] = {    		
			// Mapping coordinates for the vertices
			0.0f, 1.0f,		// top left		(V2)
			0.0f, 0.0f,		// bottom left	(V1)
			1.0f, 1.0f,		// top right	(V4)
			1.0f, 0.0f		// bottom right	(V3)
	};

	public TEDrawableComponent(GL10 gl, Context context, int resourceId, int x, int y) {
		mGL = gl;
		mX = x;
		mY = y;
		int mTextures[] = new int[1];
		gl.glGenTextures(1, mTextures, 0);
        mName = mTextures[0];		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextures[0]);
		
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE); //GL10.GL_REPLACE);

        InputStream is = context.getResources().openRawResource(resourceId);
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
}
	
	private FloatBuffer getVertexPointer() {
		final float leftX = mX - (float)mWidth / 2;
		final float rightX = leftX + mWidth;
		final float bottomY = mY - (float)mHeight / 2;
		final float topY = bottomY + mHeight;

		final float vertices[] = {
	    		leftX, bottomY
	    		, leftX, topY
	    		, rightX, bottomY
	    		, rightX, topY
	    };
	    
   		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		FloatBuffer vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		return vertexBuffer;
		
	}

	private FloatBuffer getTexturePointer() {
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(coordinates.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		FloatBuffer textureBuffer = byteBuf.asFloatBuffer();
		textureBuffer.put(coordinates);
		textureBuffer.position(0);
		return textureBuffer;
	}
	
	public void setSize(int width, int height) {
		mWidth = width;
		mHeight = height;
	}
	
	public void draw() {
		mGL.glEnable(GL10.GL_TEXTURE_2D);
		mGL.glBindTexture(GL10.GL_TEXTURE_2D, mName);
		mGL.glTexCoordPointer(2, GL10.GL_FLOAT, 0, getTexturePointer());
		mGL.glVertexPointer(2, GL10.GL_FLOAT, 0, getVertexPointer());
		mGL.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);	
		mGL.glDisable(GL10.GL_TEXTURE_2D);
		
	}
}
