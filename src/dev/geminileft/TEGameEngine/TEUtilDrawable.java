package dev.geminileft.TEGameEngine;

import java.nio.FloatBuffer;

public class TEUtilDrawable {
	public TETexture2D mTexture;
	public TESize mSize;
	public FloatBuffer mPositionBuffer;
	public FloatBuffer mCropBuffer;
	public long mPositionHash;
	public long mCropHash;
	public FloatBuffer mInverseXCropBuffer;
	
	public TEUtilDrawable(int resourceId, TESize size, TEPoint offset) {
		TEManagerTexture textureManager = TEManagerTexture.sharedInstance();
		mTexture = textureManager.getTexture2D(resourceId);
		if (size.isZero()) {
			size = mTexture.getSize();
		}
		mSize = size;
		mPositionHash = TEManagerTexture.getPositionHash(size);
		final int[] sizeArray = {
				size.width
				, size.height
		};
		
		mPositionHash = TEManagerTexture.hash(sizeArray);
		TESize textureSize = mTexture.getSize();
		mCropHash = TEManagerTexture.getCropHash(textureSize, size, offset, false);
	}
	
	public TETexture2D getTexture() {
		return mTexture;
	}
	
	public TESize getSize() {
		return mSize;
	}
}
