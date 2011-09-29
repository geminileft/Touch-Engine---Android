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
		TESize imageSize = TESize.make(0, 0);
		mTexture = textureManager.getTexture2D(resourceId, imageSize);
		mCrop = new int[CROP_SIZE];
		//mapping goes from bottom left to top right
		mCrop[START_X] = (int)offset.x;//start-x
		mCrop[START_Y] = size.height + (int)offset.y;//start-y
		mCrop[WIDTH] = size.width;//width
		mCrop[HEIGHT] = -size.height;//height
		mSize = size;
		mPositionBuffer = TEManagerTexture.getPositionBuffer(size);
		mCropBuffer = TEManagerTexture.getCropBuffer(imageSize, size, offset);
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
