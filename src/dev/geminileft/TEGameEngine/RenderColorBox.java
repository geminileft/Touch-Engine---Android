package dev.geminileft.TEGameEngine;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

public class RenderColorBox extends TEComponentRender {
	private final String PROGRAM_NAME = "colorbox";
	private float mRed;
	private float mGreen;
	private float mBlue;
	private float mAlpha;

	private long mVerticesHash;
	private FloatBuffer mVerticesBuffer;

	private int mPositionHandle;
	private int mVerticesHandle;
	private int mColorHandle;
	
	//the shaders to use
	private int mProgram;
	
	public RenderColorBox(float r, float g, float b, float a) {
		mRed = r;
		mGreen = g;
		mBlue = b;
		mAlpha = a;
		TESize size = TESize.make(TEUtilNode.GRID_SIZE, TEUtilNode.GRID_SIZE);
		mProgram = TEManagerGraphics.getProgram(PROGRAM_NAME);
		mVerticesHandle = TEManagerGraphics.getAttributeLocation(mProgram, "vertices");
        mPositionHandle = TEManagerGraphics.getAttributeLocation(mProgram, "position");
        mColorHandle = TEManagerGraphics.getUniformLocation(mProgram, "color");
		mVerticesHash = TEManagerTexture.getPositionHash(size);
		mVerticesBuffer = TEManagerTexture.getPositionBuffer(mVerticesHash);

        //TEUtilNode.reset();
	}
	
	@Override
	public void draw() {
		if (mLastProgram != mProgram) {
			TEManagerGraphics.switchProgram(PROGRAM_NAME);
			mLastProgram = mProgram;
		}

		if (mVerticesHash != mLastPositionHash) {
			GLES20.glVertexAttribPointer(mVerticesHandle, 2, GLES20.GL_FLOAT, false, 0, mVerticesBuffer);
	    	mLastPositionHash = mVerticesHash;
		}
		
        GLES20.glVertexAttrib2f(mPositionHandle, parent.position.x, parent.position.y);
        GLES20.glUniform4f(mColorHandle, mRed, mGreen, mBlue, mAlpha);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

	}

	@Override
	public void update(long dt) {
		//TEPoint point = TEUtilNode.simulateGrowthStep();
	}
}
