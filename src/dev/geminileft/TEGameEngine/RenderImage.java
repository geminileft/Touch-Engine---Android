package dev.geminileft.TEGameEngine;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class RenderImage extends TEComponentRender {
	public TEUtilDrawable mDrawable;
	private int mWidth;
	private int mHeight;
	private int maPositionHandle;
	private int maTextureHandle;
	private int mCoordsHandle;
	
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
        maPositionHandle = TEManagerGraphics.getAttributeLocation("aPosition");
        maTextureHandle = TEManagerGraphics.getAttributeLocation("aTexture");
        mCoordsHandle = TEManagerGraphics.getAttributeLocation("aCoords");
		mDrawable = new TEUtilDrawable(resourceId, size, position);
		addEventSubscription(TEComponent.Event.EVENT_MOVE_TO_TOP, mMoveToTopListener);
	}

	public void draw() {
		TEManagerTexture.bindTexture(mDrawable.mTexture.mName);
        //GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mDrawable.mTexture.mName);
        GLES20.glVertexAttribPointer(maPositionHandle, 2, GLES20.GL_FLOAT, false,
                0, mDrawable.mPositionBuffer);
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glVertexAttribPointer(maTextureHandle, 2, GLES20.GL_FLOAT, false,
                0, mDrawable.mCropBuffer);
        GLES20.glEnableVertexAttribArray(maTextureHandle);
        GLES20.glVertexAttrib2f(mCoordsHandle, parent.position.x, parent.position.y);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
	}

	@Override
	public void update(long dt) {}
	
	public TESize getSize() {
		return new TESize(mWidth, mHeight);
	}
}
