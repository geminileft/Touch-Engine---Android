package dev.geminileft.TEGameEngine;

import java.nio.FloatBuffer;

public class TEUtilDrawable {
	public TETexture2D mTexture;
	public TESize mSize;
	public FloatBuffer mPositionBuffer;
	public FloatBuffer mCropBuffer;
	public FloatBuffer mInverseXCropBuffer;
	
	public TEUtilDrawable(int resourceId, TESize size, TEPoint offset) {
		TEManagerTexture textureManager = TEManagerTexture.sharedInstance();
		mTexture = textureManager.getTexture2D(resourceId);
		if (size.isZero()) {
			size = mTexture.getSize();
		}
		mSize = size;
		mPositionBuffer = TEManagerTexture.getPositionBuffer(size);
		mCropBuffer = TEManagerTexture.getCropBuffer(mTexture.getSize(), size, offset, false);
		mInverseXCropBuffer = TEManagerTexture.getCropBuffer(mTexture.getSize(), size, offset, true);
	}
	
	public TETexture2D getTexture() {
		return mTexture;
	}
	
	public TESize getSize() {
		return mSize;
	}
}
