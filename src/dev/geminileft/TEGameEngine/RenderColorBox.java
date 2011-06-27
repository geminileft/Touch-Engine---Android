package dev.geminileft.TEGameEngine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class RenderColorBox extends TEComponentRender {
	private FloatBuffer mVertexBuffer;
	private float mX = 0;
	private float mY = 0;
	
	public RenderColorBox(Size size) {
		super();
		final int floatSize = 4;
		final float leftX = - (float)size.width / 2;
		final float rightX = leftX + size.width;
		final float bottomY = - (float)size.height / 2;
		final float topY = bottomY + size.height;

		final float vertices[] = {
	    		leftX, bottomY
	    		, leftX, topY
	    		, rightX, bottomY
	    		, rightX, topY
	    };
   		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * floatSize);
		byteBuf.order(ByteOrder.nativeOrder());
		mVertexBuffer = byteBuf.asFloatBuffer();
	    
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);
	}
	
	@Override
	public void draw(GL10 gl) {
		gl.glPushMatrix();
		gl.glTranslatef(mX, mY, 0);
		gl.glColor4f(0.3f,0.3f,.9f,1.0f);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);	
		gl.glColor4f(1.0f,1.0f,1.0f,1.0f);
		gl.glPopMatrix();
	}

	@Override
	public void update() {
		Point point = getParent().position;
		mX = point.x;
		mY = point.y;
		// TODO Auto-generated method stub
	}
}
