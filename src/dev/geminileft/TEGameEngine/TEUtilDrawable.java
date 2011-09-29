package dev.geminileft.TEGameEngine;

import java.nio.FloatBuffer;

public class TEUtilDrawable {
	public static final int START_X = 0;
	public static final int START_Y = 1;
	public static final int WIDTH = 2;
	public static final int HEIGHT = 3;
	
	private final int CROP_SIZE = 4;
	public TETexture2D mTexture;
	public int mCrop[];
	public TESize mSize;
	public FloatBuffer mPositionBuffer;
	public FloatBuffer mCropBuffer;
	
	public TEUtilDrawable(int resourceId, TESize size, TEPoint offset) {
		TEManagerTexture textureManager = TEManagerTexture.sharedInstance();
		mTexture = textureManager.getTexture2D(resourceId);
		if (size.isZero()) {
			size = mTexture.getSize();
		}
		mCrop = new int[CROP_SIZE];
		//mapping goes from bottom left to top right
		mCrop[START_X] = (int)offset.x;//start-x
		mCrop[START_Y] = size.height + (int)offset.y;//start-y
		mCrop[WIDTH] = size.width;//width
		mCrop[HEIGHT] = -size.height;//height
		mSize = size;
		mPositionBuffer = TEManagerTexture.getPositionBuffer(size);
		mCropBuffer = TEManagerTexture.getCropBuffer(mTexture.getSize(), size, offset);
	}
	
	public TETexture2D getTexture() {
		return mTexture;
	}
	
	public int[] getCrop() {
		return mCrop;
	}
	
	public TESize getSize() {
		return mSize;
	}
}
