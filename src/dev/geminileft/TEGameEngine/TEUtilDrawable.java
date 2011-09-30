package dev.geminileft.TEGameEngine;

import java.nio.FloatBuffer;

import dev.geminileft.TEGameEngine.TEManagerTexture.HashType;

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
		mPositionBuffer = TEManagerTexture.getPositionBuffer(size);
		final int[] sizeArray = {
				size.width
				, size.height
		};
		
		mPositionHash = TEManagerTexture.hash(sizeArray, HashType.POSITION);
		TESize textureSize = mTexture.getSize();
		final int[] cropArray = {
				textureSize.width
				, textureSize.height
				, size.width
				, size.height
				, (int)offset.x
				, (int)offset.y
		};
		mCropBuffer = TEManagerTexture.getCropBuffer(textureSize, size, offset, false);
		mCropHash = TEManagerTexture.hash(cropArray, HashType.CROP);
		mInverseXCropBuffer = TEManagerTexture.getCropBuffer(mTexture.getSize(), size, offset, true);
	}
	
	public TETexture2D getTexture() {
		return mTexture;
	}
	
	public TESize getSize() {
		return mSize;
	}
}
