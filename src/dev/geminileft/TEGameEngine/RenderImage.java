package dev.geminileft.TEGameEngine;

import javax.microedition.khronos.opengles.GL10;

public class RenderImage extends TEComponentRender {
	private int mWidth;
	private int mHeight;
	float mX = 0;
	float mY = 0;
	private TEUtilTexture mTexture;
	private GL10 mGL;
	
	private TEComponent.EventListener mMoveToTopListener = new TEComponent.EventListener() {
		
		@Override
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
	}

	public void draw() {
		mGL.glPushMatrix();
		mGL.glTranslatef(parent.position.x, parent.position.y, 0.0f);
		//gl.glEnable(GL10.GL_TEXTURE_2D);
		mGL.glBindTexture(GL10.GL_TEXTURE_2D, mTexture.textureName);
		mGL.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTexture.textureBuffer);
		mGL.glVertexPointer(2, GL10.GL_FLOAT, 0, mTexture.vertexBuffer);
		mGL.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);			
		//gl.glDisable(GL10.GL_TEXTURE_2D);
		mGL.glPopMatrix();
	}
	
	@Override
	public void update() {}
	
	public Size getSize() {
		return new Size(mWidth, mHeight);
	}
}
