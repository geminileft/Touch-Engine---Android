package dev.geminileft.TEGameEngine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class TEManagerTexture {
	private static TEManagerTexture mSharedInstance;
	private HashMap<Integer, TETexture2D> mTextures;
	private static final int FLOAT_SIZE = 4;
	
	public TEManagerTexture() {
		super();
		mTextures = new HashMap<Integer, TETexture2D>();
	}
	public static TEManagerTexture sharedInstance() {
		if (mSharedInstance == null) {
			mSharedInstance = new TEManagerTexture();
		}
		return mSharedInstance;
	}
	
	public TETexture2D getTexture2D(int resourceId, TESize imageSize) {
		TETexture2D texture = mTextures.get(resourceId);
		if (texture == null) {
			//GL10 gl = TEManagerGraphics.getGL();
			Context context = TEStaticSettings.getContext();
	
			int textures[] = new int[1];
			GLES20.glGenTextures(1, textures, 0);
			texture = new TETexture2D(textures[0]);
			mTextures.put(resourceId, texture);
			InputStream is = context.getResources().openRawResource(resourceId);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.getName());
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
	        //gl.glTexEnvf(GLES20.GL_TEXTURE_ENV, GLES20.GL_TEXTURE_ENV_MODE, GLES20.GL_MODULATE); //GL10.GL_REPLACE);
	
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
			
			final int bitmapHeight = bitmap.getHeight();
			final int bitmapWidth = bitmap.getWidth();
			imageSize.height = bitmapHeight;
			imageSize.width = bitmapWidth;
			final int textureHeight = MathUtils.closestPowerOf2(bitmapHeight);
			final int textureWidth = MathUtils.closestPowerOf2(bitmapWidth);
			if ((bitmapHeight == textureHeight) && (bitmapWidth == textureWidth)) {
				GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);			
			} else {
				Bitmap adjustedBitmap = Bitmap.createScaledBitmap(bitmap, textureHeight, textureWidth, false);
		        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, adjustedBitmap, 0);
		        adjustedBitmap.recycle();
			}
			bitmap.recycle();
		} else {
			Log.v("info", "Cache hit texture");
		}
		return texture;
	}
	
	public static FloatBuffer getPositionBuffer(TESize textureSize) {
		final float halfWidth = textureSize.width / 2;
		final float halfHeight = textureSize.height / 2;
		final int FLOAT_SIZE = 4;
        final float[] verticesData = {
                // X, Y, Z, U, V
        	-halfWidth,  -halfHeight
    		, -halfWidth, halfHeight
    		, halfWidth,  -halfHeight
    		, halfWidth, halfHeight
        };
        FloatBuffer positionBuffer = ByteBuffer.allocateDirect(verticesData.length
                * FLOAT_SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer();
        positionBuffer.put(verticesData).position(0);
        return positionBuffer;
	}

	public static FloatBuffer getCropBuffer(TESize imageSize, TESize textureSize, TEPoint offset) {
		final float[] cropData = {
        		offset.x / imageSize.width, (offset.y + textureSize.height) / imageSize.height
        		, offset.x / imageSize.width, offset.y / imageSize.height
        		, (offset.x + textureSize.width) / imageSize.width, (offset.y + textureSize.height) / imageSize.height
        		, (offset.x + textureSize.width) / imageSize.width, offset.y / imageSize.height
        };
        FloatBuffer cropBuffer = ByteBuffer.allocateDirect(cropData.length
                * FLOAT_SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer();
        cropBuffer.put(cropData).position(0);
        return cropBuffer;
	}
}
