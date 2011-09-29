package dev.geminileft.TEGameEngine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class RenderHUDTimer extends TEComponentRender {

	private final int FLOAT_SIZE = 4;
	private final int MAX_TEXT_SIZE = 18;
	private int mWidth;
	private int mHeight;
	float mX;
	float mY;
	private TEUtilTexture mTexture;
	private FloatBuffer mTextureBuffers[] = new FloatBuffer[11];
	private FloatBuffer mVertexBuffers[] = new FloatBuffer[11];
	private long mElapsedTime;
	//private long mPreviousTime;
	private GL10 mGL;
	
	public RenderHUDTimer(int resourceId) {
		this(resourceId, null, null);
	}
	
	public RenderHUDTimer(int resourceId, TEPoint position, TESize size) {
		super();
		mTexture = new TEUtilTexture(resourceId, position, size);
		if (size == null) {
			size = mTexture.getBitmapSize();
		}
		mWidth = size.width;
		mHeight = size.height;
		
		int x = 0;
		int width = 17;
		final int height = 26;
		
		mTextureBuffers[0] = createTextureBuffer(x, width, height);
		x += width;
		width = 14;
		mTextureBuffers[1] = createTextureBuffer(x, width, height);
		x += width;
		width = 17;
		mTextureBuffers[2] = createTextureBuffer(x, width, height);
		x += width;
		mTextureBuffers[3] = createTextureBuffer(x, width, height);
		x += width;
		width = 18;
		mTextureBuffers[4] = createTextureBuffer(x, width, height);
		x += width;
		width = 17;
		mTextureBuffers[5] = createTextureBuffer(x, width, height);
		x += width;
		mTextureBuffers[6] = createTextureBuffer(x, width, height);
		x += width;
		mTextureBuffers[7] = createTextureBuffer(x, width, height);
		x += width;
		mTextureBuffers[8] = createTextureBuffer(x, width, height);
		x += width;
		//width = 9;
		mTextureBuffers[9] = createTextureBuffer(x, width, height);
		x += width;
		width = 9;
		mTextureBuffers[10] = createTextureBuffer(x, width, height);
		FloatBuffer tempVertexBuffers[] = new FloatBuffer[4];

		width = 9;
		tempVertexBuffers[0] = createVertexBuffer(width, height);
		width = 14;
		tempVertexBuffers[1] = createVertexBuffer(width, height);
		width = 17;
		tempVertexBuffers[2] = createVertexBuffer(width, height);
		width = 18;
		tempVertexBuffers[3] = createVertexBuffer(width, height);
		
		mVertexBuffers[0] = tempVertexBuffers[2];
		mVertexBuffers[1] = tempVertexBuffers[1];
		mVertexBuffers[2] = tempVertexBuffers[2];
		mVertexBuffers[3] = tempVertexBuffers[2];
		mVertexBuffers[4] = tempVertexBuffers[3];
		mVertexBuffers[5] = tempVertexBuffers[2];
		mVertexBuffers[6] = tempVertexBuffers[2];
		mVertexBuffers[7] = tempVertexBuffers[2];
		mVertexBuffers[8] = tempVertexBuffers[2];
		mVertexBuffers[9] = tempVertexBuffers[2];
		mVertexBuffers[10] = tempVertexBuffers[0];
		//mPreviousTime = SystemClock.uptimeMillis();
		//mGL = TEManagerGraphics.getGL();
	}

	public void draw() {
	//public void draw(GL10 gl) {
		final int seconds_size = 2;
		final int minute_size = 2;
		int secondsDigits[] = new int[seconds_size + minute_size + 1];
		int digits = 0;
		int number;
		final int radix = 10;
		long seconds = (mElapsedTime / 1000) % 60;
		while ((digits < seconds_size)) {
			secondsDigits[digits] = (int)seconds % radix;
			seconds /= radix;
			++digits;
		}
		secondsDigits[digits] = 10;
		++digits;
		
		int minutes = (int)mElapsedTime / 60000;
		while ((minutes > 0) && (digits < seconds_size + minute_size + 1)) {
			secondsDigits[digits] = (int)minutes % radix;
			minutes /= radix;
			++digits;
		}
		digits = (digits < 4) ? 4 : digits;
		mGL.glPushMatrix();
		mGL.glTranslatef(mX + (MAX_TEXT_SIZE * (secondsDigits.length - 1)), mY, 0.0f);
		//gl.glEnable(GL10.GL_TEXTURE_2D);
		mGL.glBindTexture(GL10.GL_TEXTURE_2D, mTexture.textureName);
		for(int i = 0;i < digits;i++) {
			number = secondsDigits[i];
			mGL.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffers[number]);
			mGL.glVertexPointer(2, GL10.GL_FLOAT, 0, mVertexBuffers[number]);
			mGL.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
			mGL.glTranslatef(-MAX_TEXT_SIZE, 0.0f, 0.0f);
		}
		//gl.glDisable(GL10.GL_TEXTURE_2D);
		mGL.glPopMatrix();
	}
	
	@Override
	public void update(long dt) {
		TEPoint point = parent.position;
		mX = point.x;
		mY = point.y;
		//final long mCurrentTime = SystemClock.uptimeMillis();
		
		//final long dt = mCurrentTime - mPreviousTime;
		//mElapsedTime += dt;
		//Log.v("RenderHUDTimer.update", Long.valueOf(dt).toString());
		//mPreviousTime = mCurrentTime;
	}
	
	public TESize getSize() {
		return new TESize(mWidth, mHeight);
	}
	
	private FloatBuffer createTextureBuffer(int left, int width, int height) {
		final float minS = (float)left / 256;
		final float maxS = ((float)(width + left) / 256);
		final float maxT = (float)height / 32;
		float coordinates[] = new float[8];
		coordinates[0] = minS;//left
		coordinates[1] = maxT;//top
		coordinates[2] = maxS;//right
		coordinates[3] = maxT;//top
		coordinates[4] = maxS;//right
		coordinates[5] = 0.0f;//bottom
		coordinates[6] = minS;//left
		coordinates[7] = 0.0f;//bottom
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(coordinates.length * FLOAT_SIZE);
		byteBuf.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = byteBuf.asFloatBuffer();
		buffer.put(coordinates);
		buffer.position(0);

		return buffer;
	}
	
	private FloatBuffer createVertexBuffer(int width, int height) {
		final float leftX = -(float)width / 2;
		final float rightX = leftX + width;
		final float bottomY = -(float)height / 2;
		final float topY = bottomY + height;

		float vertices[] = new float[8];
		vertices[0] = leftX;
		vertices[1] = bottomY;
		vertices[2] = rightX;
		vertices[3] = bottomY;
		vertices[4] = rightX;
		vertices[5] = topY;
		vertices[6] = leftX;
		vertices[7] = topY;

		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * FLOAT_SIZE);
		byteBuf.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = byteBuf.asFloatBuffer();
		buffer.put(vertices);
		buffer.position(0);
		return buffer;
	}
}
