package dev.geminileft.TEGameEngine;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

public class RenderImage extends TEComponentRender {
	private int mWidth;
	private int mHeight;
	float mX = 0;
	float mY = 0;
	private TEUtilTexture mTexture;
	private GL10 mGL;
	private int[] mCrop = new int[4];
	
	private TEComponent.EventListener mMoveToTopListener = new TEComponent.EventListener() {
		
		public void invoke() {
			RenderImage.this.getManager().moveComponentToTop(RenderImage.this);
		}
	};
	
	public RenderImage(int resourceId) {
		this(resourceId, null, null);
	}

	public RenderImage(int resourceId, Point position, Size size) {
		super();
		addEventSubscription(TEComponent.Event.EVENT_MOVE_TO_TOP, mMoveToTopListener);
		mTexture = new TEUtilTexture(resourceId, position, size);
		if (size == null) {
			size = mTexture.getBitmapSize();
		}
		mWidth = size.width;
		mHeight = size.height;
		mGL = TEManagerGraphics.getGL();
		mCrop[0] = 0;
		mCrop[1] = mHeight;
		mCrop[2] = mWidth;
		mCrop[3] = -mHeight;
	}

	public void draw() {
		mGL.glBindTexture(GL10.GL_TEXTURE_2D, mTexture.textureName);

		final boolean useDrawTexfOES = true;
		if (useDrawTexfOES) {
	        ((GL11)mGL).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, mCrop, 0);
	        ((GL11Ext)mGL).glDrawTexfOES(parent.position.x - (mWidth / 2), parent.position.y - (mHeight / 2), 
	        		0.001f, mWidth, mHeight);		
		} else {
			mGL.glPushMatrix();
			mGL.glTranslatef(parent.position.x, parent.position.y, 0.0f);
	        mGL.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTexture.textureBuffer);
			mGL.glVertexPointer(2, GL10.GL_FLOAT, 0, mTexture.vertexBuffer);
			mGL.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);			
			mGL.glPopMatrix();
		}
	}
	
	@Override
	public void update() {}
	
	public Size getSize() {
		return new Size(mWidth, mHeight);
	}
}
