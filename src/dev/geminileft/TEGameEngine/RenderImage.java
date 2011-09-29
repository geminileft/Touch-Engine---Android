package dev.geminileft.TEGameEngine;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class RenderImage extends TEComponentRender {
	public TEUtilDrawable mDrawable;
	private int mWidth;
	private int mHeight;
	float mX = 0;
	float mY = 0;
	
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
		mDrawable = new TEUtilDrawable(resourceId, size, position);
		addEventSubscription(TEComponent.Event.EVENT_MOVE_TO_TOP, mMoveToTopListener);
	}

/*
	public void draw(GL10 gl) {
		//TESize size = mDrawable.getSize();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mDrawable.mTexture.mName);
		TESize size = mDrawable.mSize;
		int width = size.width;
		int height = size.height;
        ((GL11)gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, mDrawable.mCrop, 0);
        ((GL11Ext)gl).glDrawTexfOES(parent.position.x - (width / 2), parent.position.y - (height / 2), 
        		0.001f, width, height);
	}
*/

	public void draw() {
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
                0, mDrawable.mCropBuffer);
        TEManagerGraphics.checkGlError("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(maTextureHandle);
        TEManagerGraphics.checkGlError("glEnableVertexAttribArray maTextureHandle");
        float mMVPMatrix[] = new float[16];
        float mModelMatrix[] = new float[16];
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 427, 240, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, TEManagerGraphics.getViewMatrix(), 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, TEManagerGraphics.getProjectionMatrix(), 0, mMVPMatrix, 0);

        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        TEManagerGraphics.checkGlError("glDrawArrays");

	}

	@Override
	public void update(long dt) {}
	
	public TESize getSize() {
		return new TESize(mWidth, mHeight);
	}
}
