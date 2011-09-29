package dev.geminileft.TEGameEngine;

import java.nio.FloatBuffer;
import java.util.LinkedList;

import android.opengl.GLES20;
import android.opengl.Matrix;


public class RenderAnimation extends TEComponentRender {
	private LinkedList<AnimationFrame> mFrames;
	private long mCurrentFrameDuration;
	private int mCurrentFrameIndex;
	private TEGameObject.ObjectState mState;
	private TEUtilDrawable mDrawable;
	private FloatBuffer mCropBuffer;
	
	public RenderAnimation(TEGameObject.ObjectState state) {
		mFrames = new LinkedList<AnimationFrame>();
		mState = state;
	}
	
	public void addFrameAnimation(long frameDuration, TEUtilDrawable drawable) {
		mFrames.add(new AnimationFrame(frameDuration, drawable));
	}

	public void draw() {
		if (parent.state == mState) {
	        GLES20.glUseProgram(TEManagerGraphics.getProgram());
	        TEManagerGraphics.checkGlError("glUseProgram");
	        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
	        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mDrawable.mTexture.mName);
	        int maPositionHandle = TEManagerGraphics.getAttributeLocation("aPosition");
	        int maTextureHandle = TEManagerGraphics.getAttributeLocation("aTexture");
	        int muMVPMatrixHandle = TEManagerGraphics.getUniformLocation("uMVPMatrix");
	        GLES20.glVertexAttribPointer(maPositionHandle, 2, GLES20.GL_FLOAT, false,
	                0, mDrawable.mPositionBuffer);
	        TEManagerGraphics.checkGlError("glVertexAttribPointer maPosition");        
	        GLES20.glEnableVertexAttribArray(maPositionHandle);
	        TEManagerGraphics.checkGlError("glEnableVertexAttribArray maPositionHandle");
	        
	        GLES20.glVertexAttribPointer(maTextureHandle, 2, GLES20.GL_FLOAT, false,
	                0, mCropBuffer);
	        TEManagerGraphics.checkGlError("glVertexAttribPointer maTextureHandle");
	        GLES20.glEnableVertexAttribArray(maTextureHandle);
	        TEManagerGraphics.checkGlError("glEnableVertexAttribArray maTextureHandle");
	        float mMVPMatrix[] = new float[16];
	        float mModelMatrix[] = new float[16];
	        Matrix.setIdentityM(mModelMatrix, 0);
	        Matrix.translateM(mModelMatrix, 0, parent.position.x, parent.position.y, 0);
	        Matrix.multiplyMM(mMVPMatrix, 0, TEManagerGraphics.getViewMatrix(), 0, mModelMatrix, 0);
	        Matrix.multiplyMM(mMVPMatrix, 0, TEManagerGraphics.getProjectionMatrix(), 0, mMVPMatrix, 0);
	
	        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0);
	        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
	        TEManagerGraphics.checkGlError("glDrawArrays");
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
		if (parent.direction == TEGameObject.ObjectDirection.REVERSE) {
			mCropBuffer = mDrawable.mInverseXCropBuffer;
		} else {
			mCropBuffer = mDrawable.mCropBuffer;			
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
