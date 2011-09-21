package dev.geminileft.TEGameEngine;

public class TEUtilDrawable {
	private final int CROP_SIZE = 4;
	public TETexture2D mTexture;
	public int mCrop[];
	public TESize mSize;
	
	public TEUtilDrawable(int resourceId, TESize size) {
		TEManagerTexture textureManager = TEManagerTexture.sharedInstance();
		mTexture = textureManager.getTexture2D(resourceId);
		mCrop = new int[CROP_SIZE];
		mCrop[0] = 0;
		mCrop[1] = size.height;
		mCrop[2] = size.width;
		mCrop[3] = -size.height;
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
