package dev.geminileft.TEGameEngine;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class RenderImage extends TEComponentRender {
	public TEUtilDrawable mDrawable;
	private int mWidth;
	private int mHeight;
	private float mX = 0;
	private float mY = 0;
	private int maPositionHandle;
	private int maTextureHandle;
	private int mModelHandle;
	private int muMVPMatrixHandle;
	private int mCoordsHandle;
	private float mViewMatrix[];
	private float mProjectionMatrix[];
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
        maPositionHandle = TEManagerGraphics.getAttributeLocation("aPosition");
        maTextureHandle = TEManagerGraphics.getAttributeLocation("aTexture");
        mCoordsHandle = TEManagerGraphics.getAttributeLocation("aCoords");
        muMVPMatrixHandle = TEManagerGraphics.getUniformLocation("uMVPMatrix");
        mModelHandle = TEManagerGraphics.getUniformLocation("uModelMatrix");
        mViewMatrix = TEManagerGraphics.getViewMatrix();
        mProjectionMatrix = TEManagerGraphics.getProjectionMatrix();
        mProgram = TEManagerGraphics.getProgram();
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
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mDrawable.mTexture.mName);
        GLES20.glVertexAttribPointer(maPositionHandle, 2, GLES20.GL_FLOAT, false,
                0, mDrawable.mPositionBuffer);
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glVertexAttribPointer(maTextureHandle, 2, GLES20.GL_FLOAT, false,
                0, mDrawable.mCropBuffer);
        GLES20.glEnableVertexAttribArray(maTextureHandle);
        GLES20.glVertexAttrib2f(mCoordsHandle, parent.position.x, parent.position.y);
/*
        float mModelMatrix[] = {
        		1, 0, 0, 0
        		, 0, 1, 0, 0
        		, 0, 0, 1, 0
        		, parent.position.x, parent.position.y, 0, 1
        };
        
        GLES20.glUniformMatrix4fv(mModelHandle, 1, false, mModelMatrix, 0);
*/
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        //TEManagerGraphics.checkGlError("glDrawArrays");
	}

	@Override
	public void update(long dt) {}
	
	public TESize getSize() {
		return new TESize(mWidth, mHeight);
	}
}
