package dev.geminileft.TEGameEngine;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

public class RenderImage extends TEComponentRender {
	private final String PROGRAM_NAME = "texture";
	public TEUtilDrawable mDrawable;
	private int mWidth;
	private int mHeight;
	private int mCoordsHandle;
	private int mName;
	private float mX;
	private float mY;
	private FloatBuffer mPositionBuffer;
	private FloatBuffer mCropBuffer;
	private long mPositionHash;
	private long mCropHash;
	private int maPositionHandle;
	private int maTextureHandle;
	private int mProgram;
	
	private TEComponent.EventListener mMoveToTopListener = new TEComponent.EventListener() {
		
		public void invoke() {
			RenderImage.this.getManager().moveComponentToTop(RenderImage.this);
		}
	};
	
	public RenderImage(int resourceId) {
		this(resourceId, TEPoint.zero(), TESize.zero());
	}

	public RenderImage(int resourceId, TEPoint position, TESize size) {
		super();
		mProgram = TEManagerGraphics.getProgram(PROGRAM_NAME);
        mCoordsHandle = TEManagerGraphics.getAttributeLocation(mProgram, "aCoords");
        maPositionHandle = TEManagerGraphics.getAttributeLocation(mProgram, "aPosition");
        maTextureHandle = TEManagerGraphics.getAttributeLocation(mProgram, "aTexture");
		mDrawable = new TEUtilDrawable(resourceId, size, position);
		mName = mDrawable.mTexture.mName;
		addEventSubscription(TEComponent.Event.EVENT_MOVE_TO_TOP, mMoveToTopListener);
	}

	public void draw() {
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
        GLES20.glVertexAttrib2f(mCoordsHandle, mX, mY);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
	}

	@Override
	public void update(long dt) {
		mX = parent.position.x;
		mY = parent.position.y;
		mPositionBuffer = mDrawable.mPositionBuffer;
		mCropBuffer = mDrawable.mCropBuffer;
		mPositionHash = mDrawable.mPositionHash;
		mCropHash = mDrawable.mCropHash;
	}
	
	public TESize getSize() {
		return new TESize(mWidth, mHeight);
	}
}
