package dev.geminileft.TEGameEngine;

public class TEUtilDrawable {
	public static final int START_X = 0;
	public static final int START_Y = 1;
	public static final int WIDTH = 2;
	public static final int HEIGHT = 3;
	
	private final int CROP_SIZE = 4;
	public TETexture2D mTexture;
	public int mCrop[];
	public TESize mSize;
	
	public TEUtilDrawable(int resourceId, TESize size) {
		TEManagerTexture textureManager = TEManagerTexture.sharedInstance();
		mTexture = textureManager.getTexture2D(resourceId);
		mCrop = new int[CROP_SIZE];
		//mapping goes from top left to bottom right
		mCrop[START_X] = 0;//start-x
		mCrop[START_Y] = size.height;//start-y
		mCrop[WIDTH] = size.width;//width
		mCrop[HEIGHT] = -size.height;//height
		mSize = size;
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
