package dev.geminileft.TEGameEngine;

import javax.microedition.khronos.opengles.GL10;

public class RenderText extends TEComponentRender {
	private int mWidth;
	private int mHeight;
	float mX;
	float mY;
	private TEUtilTexture mTexture;
	
	public RenderText(int resourceId) {
		this(resourceId, null, null);
	}

	public RenderText(int resourceId, Point position, Size size) {
		super();
		mTexture = new TEUtilTexture(resourceId, position, size);
		if (size == null) {
			size = mTexture.getBitmapSize();
		}
		mWidth = size.width;
		mHeight = size.height;
	}

	public void draw(GL10 gl) {
		gl.glPushMatrix();
		gl.glTranslatef(mX, mY, 0.0f);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTexture.getName());
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTexture.getTextureBuffer());
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, mTexture.getVertexBuffer());
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);			
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glPopMatrix();
	}
	
	public void update() {
		super.update();
		Point point = getParent().position;
		mX = point.x;
		mY = point.y;
	}
	
	public Size getSize() {
		return new Size(mWidth, mHeight);
	}
}
