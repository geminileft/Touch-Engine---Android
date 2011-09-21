package dev.geminileft.TEGameEngine;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

public class RenderImage extends TEComponentRender {
	private TEUtilDrawable mDrawable;
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
		this(resourceId, null, null);
	}

	public RenderImage(int resourceId, TEPoint position, TESize size) {
		super();
		mDrawable = new TEUtilDrawable(resourceId, size);
		addEventSubscription(TEComponent.Event.EVENT_MOVE_TO_TOP, mMoveToTopListener);
	}

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
	
	@Override
	public void update(long dt) {}
	
	public TESize getSize() {
		return new TESize(mWidth, mHeight);
	}
}
