package dev.geminileft.TEGameEngine;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

public class RenderColorBox extends TEComponentRender {
	private float mRed;
	private float mGreen;
	private float mBlue;
	private float mAlpha;
	private long mPositionHash;
	private FloatBuffer mPositionBuffer;
	private int mPositionHandle;
	private int mVerticesHandle;
	
	public RenderColorBox(float r, float g, float b, float a) {
		mRed = r;
		mGreen = g;
		mBlue = b;
		mAlpha = a;
		TESize size = TESize.make(TEUtilNode.GRID_SIZE, TEUtilNode.GRID_SIZE);

		mVerticesHandle = TEManagerGraphics.getAttributeLocation("vertices");
        mPositionHandle = TEManagerGraphics.getAttributeLocation("position");

		mPositionHash = TEManagerTexture.getPositionHash(size);
		mPositionBuffer = TEManagerTexture.getPositionBuffer(mPositionHash);

        //TEUtilNode.reset();
	}
	
	@Override
	public void draw() {
		if (mPositionHash != mLastPositionHash) {
			GLES20.glVertexAttribPointer(mVerticesHandle, 2, GLES20.GL_FLOAT, false, 0, mPositionBuffer);
	    	mLastPositionHash = mPositionHash;
		}
        GLES20.glVertexAttrib2f(mPositionHandle, parent.position.x, parent.position.y);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

	}

	@Override
	public void update(long dt) {
		//TEPoint point = TEUtilNode.simulateGrowthStep();
	}
}
