package dev.geminileft.TEGameEngine;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

public class RenderColorBox extends TEComponentRender {
	private final int VECTOR_SIZE = 4;
	private final int MAX_COLOR = 256;
	private float mRed;
	private float mGreen;
	private float mBlue;
	private float mAlpha;
	private long mPositionHash;
	private FloatBuffer mPositionBuffer;
	private int mPositionHandle;
	
	private int maPositionHandle;
	private int mColorHandle;
	
	private float mColor[];
	
	public RenderColorBox(float r, float g, float b, float a) {
		mRed = r;
		mGreen = g;
		mBlue = b;
		mAlpha = a;
		TESize size = TESize.make(TEUtilNode.GRID_SIZE, TEUtilNode.GRID_SIZE);

		mColor = new float[TEUtilNode.GRID_SIZE * TEUtilNode.GRID_SIZE * VECTOR_SIZE];
		for (int i = 0;i < 64;++i) {
			mColor[(i * VECTOR_SIZE)] = ((float)i * 4) / MAX_COLOR;
			mColor[(i * VECTOR_SIZE) + 1] = ((float)i * 4) / MAX_COLOR;
			mColor[(i * VECTOR_SIZE) + 2] = ((float)i * 4) / MAX_COLOR;
			mColor[(i * VECTOR_SIZE) + 3] = 1;
		}
		maPositionHandle = TEManagerGraphics.getAttributeLocation("aPosition");
        mPositionHandle = TEManagerGraphics.getAttributeLocation("position");
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
		GLES20.glUniform4fv(mColorHandle, 1500, mColor, 0);
        GLES20.glVertexAttrib2f(mPositionHandle, parent.position.x, parent.position.y);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

	}

	@Override
	public void update(long dt) {
		//TEPoint point = TEUtilNode.simulateGrowthStep();
	}
}
