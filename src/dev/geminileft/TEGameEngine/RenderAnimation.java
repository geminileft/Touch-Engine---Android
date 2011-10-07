package dev.geminileft.TEGameEngine;

import java.nio.FloatBuffer;
import java.util.LinkedList;

import android.opengl.GLES20;
import android.opengl.Matrix;


public class RenderAnimation extends TEComponentRender {
	private final String PROGRAM_NAME = "texture";
	private LinkedList<AnimationFrame> mFrames;
	private long mCurrentFrameDuration;
	private int mCurrentFrameIndex;
	private TEGameObject.ObjectState mState;
	private TEUtilDrawable mDrawable;
	private FloatBuffer mPositionBuffer;
	private FloatBuffer mCropBuffer;
	private int mProgram;
	private int mName;
	private long mPositionHash;
	private long mCropHash;
	private int maPositionHandle;
	private int maTextureHandle;
	private int mCoordsHandle;

	public RenderAnimation(TEGameObject.ObjectState state) {
		mFrames = new LinkedList<AnimationFrame>();
		mState = state;
		mProgram = TEManagerGraphics.getProgram(PROGRAM_NAME);
        mCoordsHandle = TEManagerGraphics.getAttributeLocation(mProgram, "aCoords");
        maPositionHandle = TEManagerGraphics.getAttributeLocation(mProgram, "aPosition");
        maTextureHandle = TEManagerGraphics.getAttributeLocation(mProgram, "aTexture");
	}
	
	public void addFrameAnimation(long frameDuration, TEUtilDrawable drawable) {
		mFrames.add(new AnimationFrame(frameDuration, drawable));
	}

	public void draw() {
		if (parent.state == mState) {
			if (mLastProgram != mProgram) {
				TEManagerGraphics.switchProgram(PROGRAM_NAME);
				mLastProgram = mProgram;
			}

			if (mName != mLastTexture) {
				GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mName);
				mLastTexture = mName;
			}
			if (mPositionHash != mLastPositionHash) {
				GLES20.glVertexAttribPointer(maPositionHandle, 2, GLES20.GL_FLOAT, false, 0, mPositionBuffer);
		    	mLastPositionHash = mPositionHash;
			}
			
			if (mCropHash != mLastCropHash) {
		        GLES20.glVertexAttribPointer(maTextureHandle, 2, GLES20.GL_FLOAT, false, 0, mCropBuffer);
		        mLastCropHash = mCropHash;
			}
	        GLES20.glVertexAttrib2f(mCoordsHandle, parent.position.x, parent.position.y);
	        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
			
			
		}
	}
	@Override
	public void update(long dt) {
		mCurrentFrameDuration += dt;
		final long frameDuration = mFrames.get(mCurrentFrameIndex).getDuration();
		if (mCurrentFrameDuration > frameDuration) {
			mCurrentFrameDuration -= frameDuration; 
			mCurrentFrameIndex = (mCurrentFrameIndex + 1) % mFrames.size();
		}

		AnimationFrame frame = mFrames.get(mCurrentFrameIndex);
		mDrawable = frame.getDrawable();
		mName = mDrawable.mTexture.mName;
		mPositionHash = mDrawable.mPositionHash;

		mPositionBuffer = mDrawable.mPositionBuffer;

		if (parent.direction == TEGameObject.ObjectDirection.REVERSE) {
			mCropBuffer = mDrawable.mInverseXCropBuffer;			
			mCropHash = mDrawable.mInverseXCropHash;
		} else {
			mCropBuffer = mDrawable.mCropBuffer;			
			mCropHash = mDrawable.mCropHash;
		}
	}

	private class AnimationFrame {
		private long mDuration;
		private TEUtilDrawable mDrawable;
		
		public AnimationFrame(long duration, TEUtilDrawable drawable) {
			super();
			mDuration = duration;
			mDrawable = drawable;
		}
		
		public TEUtilDrawable getDrawable() {
			return mDrawable;
		}
		
		public long getDuration() {
			return mDuration;
		}
	}
}
