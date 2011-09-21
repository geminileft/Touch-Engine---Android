package dev.geminileft.TEGameEngine;

import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.util.Log;


public class RenderAnimation extends TEComponentRender {
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
	
	private LinkedList<AnimationFrame> mFrames;
	private long mCurrentFrameDuration;
	private int mCurrentFrameIndex;
	
	public RenderAnimation() {
		mFrames = new LinkedList<AnimationFrame>();
	}
	public void addFrameAnimation(long frameDuration, TEUtilDrawable drawable) {
		mFrames.add(new AnimationFrame(frameDuration, drawable));
	}
	
	@Override
	public void draw(GL10 gl) {
		AnimationFrame frame = mFrames.get(mCurrentFrameIndex);
		TEUtilDrawable drawable = frame.getDrawable();
		TESize size = drawable.getSize();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, drawable.getTexture().getName());

        ((GL11)gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, drawable.getCrop(), 0);
        ((GL11Ext)gl).glDrawTexfOES(parent.position.x - (size.width / 2), parent.position.y - (size.height / 2), 
        		0.001f, size.width, size.height);
	}

	@Override
	public void update(long dt) {
		mCurrentFrameDuration += dt;
		final long frameDuration = mFrames.get(mCurrentFrameIndex).getDuration();
		if (mCurrentFrameDuration > frameDuration) {
			mCurrentFrameDuration -= frameDuration; 
			mCurrentFrameIndex = (mCurrentFrameIndex + 1) % mFrames.size();
		}
	}

}
