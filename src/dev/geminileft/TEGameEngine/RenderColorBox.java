package dev.geminileft.TEGameEngine;

import java.nio.FloatBuffer;

import dev.geminileft.opengltest.R;

import android.opengl.GLES20;

public class RenderColorBox extends TEComponentRender {
	private final int VECTOR_SIZE = 4;
	private float mRed;
	private float mGreen;
	private float mBlue;
	private float mAlpha;
	private long mPositionHash;
	private FloatBuffer mPositionBuffer;

	private TEUtilDrawable mDrawable;
	private long mCropHash;
	private FloatBuffer mCropBuffer;
	private int maPositionHandle;
	private int mCoordsHandle;
	private int mColorHandle;
	private int maTextureHandle;
	
	private float mLattice[];
	
	public RenderColorBox(float r, float g, float b, float a) {
		mRed = r;
		mGreen = g;
		mBlue = b;
		mAlpha = a;
		mLattice = new float[TEUtilNode.GRID_SIZE * TEUtilNode.GRID_SIZE * VECTOR_SIZE];
		TESize size = TESize.make(TEUtilNode.GRID_SIZE, TEUtilNode.GRID_SIZE);
		mDrawable = new TEUtilDrawable(R.raw.ace_cell, size, TEPoint.zero());

		int yAdd;
		int xAdd;
		for (int y = 0;y < TEUtilNode.GRID_SIZE;++y) {
			yAdd = y << 8;
			for (int x = 0;x < TEUtilNode.GRID_SIZE;++x) {
				xAdd = x << 2;				
				mLattice[yAdd + xAdd] = (float)x / TEUtilNode.GRID_SIZE;
				mLattice[yAdd + xAdd + 1] = (float)x / TEUtilNode.GRID_SIZE;
				mLattice[yAdd + xAdd + 2] = (float)x / TEUtilNode.GRID_SIZE;
				mLattice[yAdd + xAdd + 3] = 1;			
			}
		}

		yAdd = (TEUtilNode.GRID_SIZE - 1) << 8;
		xAdd = (TEUtilNode.GRID_SIZE / 2) << 2;

		mLattice[yAdd + xAdd] = mRed;
		mLattice[yAdd + xAdd + 1] = mGreen;
		mLattice[yAdd + xAdd + 2] = mBlue;
		mLattice[yAdd + xAdd + 3] = mAlpha;			

		yAdd = (TEUtilNode.GRID_SIZE - 2) << 8;
		
		mLattice[yAdd + xAdd] = mRed;
		mLattice[yAdd + xAdd + 1] = mGreen;
		mLattice[yAdd + xAdd + 2] = mBlue;
		mLattice[yAdd + xAdd + 3] = mAlpha;			

		yAdd = (TEUtilNode.GRID_SIZE - 3) << 8;

		mLattice[yAdd + xAdd] = mRed;
		mLattice[yAdd + xAdd + 1] = mGreen;
		mLattice[yAdd + xAdd + 2] = mBlue;
		mLattice[yAdd + xAdd + 3] = mAlpha;			

		maPositionHandle = TEManagerGraphics.getAttributeLocation("aPosition");
        maTextureHandle = TEManagerGraphics.getAttributeLocation("aTexture");
        mCoordsHandle = TEManagerGraphics.getAttributeLocation("aCoords");
        mColorHandle = TEManagerGraphics.getUniformLocation("color");

		mPositionHash = TEManagerTexture.getPositionHash(size);
		mPositionBuffer = TEManagerTexture.getPositionBuffer(mPositionHash);

        TEUtilNode.reset();
	}
	
	@Override
	public void draw() {		
		if (mPositionHash != mLastPositionHash) {
			GLES20.glVertexAttribPointer(maPositionHandle, 2, GLES20.GL_FLOAT, false, 0, mPositionBuffer);
	    	mLastPositionHash = mPositionHash;
		}
		
		if (mCropHash != mLastCropHash) {
	        GLES20.glVertexAttribPointer(maTextureHandle, 2, GLES20.GL_FLOAT, false, 0, mCropBuffer);
	        mLastCropHash = mCropHash;
		}
		if (mPositionHash != mLastPositionHash) {
			GLES20.glVertexAttribPointer(maPositionHandle, 2, GLES20.GL_FLOAT, false, 0, mPositionBuffer);
	    	mLastPositionHash = mPositionHash;
		}
		final float[] color = {
				mRed
				, mGreen
				, mBlue
				, mAlpha
		};
		
		GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        GLES20.glVertexAttrib2f(mCoordsHandle, parent.position.x, parent.position.y);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

	}

	@Override
	public void update(long dt) {
		TEPoint point = TEUtilNode.simulateGrowthStep();
		final int yAdd = (int)point.y << 8;
		final int xAdd = (int)point.x << 2;

		mLattice[yAdd + xAdd] = mRed;
		mLattice[yAdd + xAdd + 1] = mGreen;
		mLattice[yAdd + xAdd + 2] = mBlue;
		mLattice[yAdd + xAdd + 3] = mAlpha;			

		mPositionBuffer = mDrawable.mPositionBuffer;
		mCropBuffer = mDrawable.mCropBuffer;
		mPositionHash = mDrawable.mPositionHash;
		mCropHash = mDrawable.mCropHash;
	}
}
