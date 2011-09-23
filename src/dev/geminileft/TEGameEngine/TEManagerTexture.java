package dev.geminileft.TEGameEngine;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

public class TEManagerTexture {
	private static TEManagerTexture mSharedInstance;
	private HashMap<Integer, TETexture2D> mTextures;
	
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
	
	public TETexture2D getTexture2D(int resourceId) {
		TETexture2D texture = mTextures.get(resourceId);
		if (texture == null) {
			GL10 gl = TEManagerGraphics.getGL();
			Context context = TEStaticSettings.getContext();
	
			int textures[] = new int[1];
			gl.glGenTextures(1, textures, 0);
			texture = new TETexture2D(textures[0]);
			mTextures.put(resourceId, texture);
			InputStream is = context.getResources().openRawResource(resourceId);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texture.getName());
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
			
			final int bitmapHeight = bitmap.getHeight();
			final int bitmapWidth = bitmap.getWidth();
	
			final int textureHeight = MathUtils.closestPowerOf2(bitmapHeight);
			final int textureWidth = MathUtils.closestPowerOf2(bitmapWidth);
			if ((bitmapHeight == textureHeight) && (bitmapWidth == textureWidth)) {
				GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);			
			} else {
				Bitmap adjustedBitmap = Bitmap.createScaledBitmap(bitmap, textureHeight, textureWidth, false);
		        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, adjustedBitmap, 0);
		        adjustedBitmap.recycle();
			}
			bitmap.recycle();
		} else {
			Log.v("info", "Cache hit texture");
		}
		return texture;
	}
}
