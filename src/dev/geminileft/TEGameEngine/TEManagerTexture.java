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
	private static int mTexture;
	private static long mPositionHash;
	private static int maPositionHandle;
	private static long mCropHash;
	private static int maTextureHandle;
	private static HashMap<Long, FloatBuffer> mPositionMap = new HashMap<Long, FloatBuffer>();
	private static HashMap<Long, FloatBuffer> mCropBuffer = new HashMap<Long, FloatBuffer>();
	
	public enum HashType {
		POSITION
		, CROP
	};
	
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
	
	public static void setPositionHandle(int handle) {
		maPositionHandle = handle;
	}
	
	public static void setCropHandle(int handle) {
		maTextureHandle = handle;
	}
	
	public TETexture2D getTexture2D(int resourceId) {
		TETexture2D texture = mTextures.get(resourceId);
		if (texture == null) {
			//GL10 gl = TEManagerGraphics.getGL();
			Context context = TEStaticSettings.getContext();
	
			int textures[] = new int[1];
			GLES20.glGenTextures(1, textures, 0);
			InputStream is = context.getResources().openRawResource(resourceId);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
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
			texture = new TETexture2D(textures[0], bitmapWidth, bitmapHeight);
			mTextures.put(resourceId, texture);
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

	public static FloatBuffer getCropBuffer(TESize imageSize, TESize textureSize, TEPoint offset, boolean inverseX) {
		float left, right;
		if (inverseX) {
			left = (offset.x + textureSize.width) / imageSize.width;
			right = offset.x / imageSize.width;
			
		} else {
			left = offset.x / imageSize.width;
			right = (offset.x + textureSize.width) / imageSize.width;
		}
		final float[] cropData = {
        		left, (offset.y + textureSize.height) / imageSize.height
        		, left, offset.y / imageSize.height
        		, right, (offset.y + textureSize.height) / imageSize.height
        		, right, offset.y / imageSize.height
        };
        FloatBuffer cropBuffer = ByteBuffer.allocateDirect(cropData.length
                * FLOAT_SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer();
        cropBuffer.put(cropData).position(0);
        
		final int[] cropArray = {
				textureSize.width
				, textureSize.height
				, imageSize.width
				, imageSize.height
				, (int)offset.x
				, (int)offset.y
		};
        
        long hash = hash(cropArray, HashType.CROP);
        mCropBuffer.put(hash, cropBuffer);
        return cropBuffer;
	}
	
	public static void bindTexture(int texture) {
		if (texture != mTexture) {
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
		}
	}
	
	public static void setPosition(long hash, FloatBuffer position) {
		if (mPositionHash != hash) {
			GLES20.glVertexAttribPointer(maPositionHandle, 2, GLES20.GL_FLOAT, false, 0, position);
        	mPositionHash = hash;
		}
	}
	
	public static void setCrop(long hash, FloatBuffer crop) {
		if (mCropHash != hash) {
			//int count = mCropBuffer.size();
			//FloatBuffer buffer = mCropBuffer.get(hash);
	        GLES20.glVertexAttribPointer(maTextureHandle, 2, GLES20.GL_FLOAT, false, 0, crop);
	        mCropHash = hash;
		}
	}
	
	public static long hash(int[] key, HashType type) {
	    long hash;
	    int  i;
	    for(hash = i = 0; i < key.length; ++i) {
	        hash += key[i];
	        hash += (hash << 10);
	        hash ^= (hash >> 6);
	    }
	    hash += (hash << 3);
	    hash ^= (hash >> 11);
	    hash += (hash << 15);
	    return hash;
	}
}
