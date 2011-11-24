package dev.geminileft.TEGameEngine;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

public class RenderMenu extends TEComponentRender {
	private final String PROGRAM_NAME = "effectbox";
	
	private int mProgram;
	private int mVerticesHandle;
	private int mPositionHandle;
	private int mColorHandle;
	private int mDistanceHandle;
	private FloatBuffer mVerticesBuffer;
	private long mVerticesHash;
	private int mVertexCount;
	private int mInterval;
	private int mCount;
	private float mDistance;
	private float mScale;
	
	public RenderMenu() {
		super();
		mCount = 10;
		mProgram = TEManagerGraphics.getProgram(PROGRAM_NAME);
		mVerticesHandle = TEManagerGraphics.getAttributeLocation(mProgram, "vertices");
        mPositionHandle = TEManagerGraphics.getAttributeLocation(mProgram, "position");
        mDistanceHandle = TEManagerGraphics.getUniformLocation(mProgram, "distance");
        mColorHandle = TEManagerGraphics.getUniformLocation(mProgram, "color");
		TESize size = TESize.make(854, 480);
		mVerticesHash = TEManagerTexture.getPositionHash(size);
		mVerticesBuffer = TEManagerTexture.getPositionBuffer(mVerticesHash);
        mVertexCount = 4;
	}
	
	@Override
	public void draw() {
		if (mLastProgram != mProgram) {
			TEManagerGraphics.switchProgram(PROGRAM_NAME);
			mLastProgram = mProgram;
		}

		mLastPositionHash = -1;
		GLES20.glVertexAttribPointer(mVerticesHandle, 2, GLES20.GL_FLOAT, false, 0, mVerticesBuffer);

        GLES20.glVertexAttrib2f(mPositionHandle, parent.position.x, parent.position.y);
        GLES20.glUniform4f(mColorHandle, 0.0f, 0.0f, 0.0f, mDistance / 2.0f);
        GLES20.glUniform1f(mDistanceHandle, mScale);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, mVertexCount);
	}

	@Override
	public void update(long dt) {
		// TODO Auto-generated method stub
		mInterval += (mInterval >= mCount) ? 0 : 1;
		mDistance = (float)MathUtils.easeInOut((1.0f * mInterval)/(mCount));
		mScale = .3f + (mDistance * .7f);
	}

}
