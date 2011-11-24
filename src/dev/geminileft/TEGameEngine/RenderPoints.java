package dev.geminileft.TEGameEngine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

public class RenderPoints extends TEComponentRender {
	private final String PROGRAM_NAME = "colorbox";
	private final int FLOAT_SIZE = 4;

	private int mProgram;
	private int mPositionHandle;
	private int mVerticesHandle;
	private int mColorHandle;
	private FloatBuffer mPointsBuffer;
	private int mPointCount;

	public RenderPoints() {
		super();
		mProgram = TEManagerGraphics.getProgram(PROGRAM_NAME);
		mVerticesHandle = TEManagerGraphics.getAttributeLocation(mProgram, "vertices");
        mPositionHandle = TEManagerGraphics.getAttributeLocation(mProgram, "position");
        mColorHandle = TEManagerGraphics.getUniformLocation(mProgram, "color");

        String fileContent = TEManagerFile.readFileContents("points.txt");
        String points[] = fileContent.split("\\|");
        float verticesData[] = new float[points.length * 2];
        int index = 0;
        for (String s : points) {
        	String point[] = s.split(",");
        	String x = point[0];
        	verticesData[(index * 2)] = Float.parseFloat(point[0]);
        	verticesData[(index * 2) + 1] = Float.parseFloat(point[1]);
        	++index;
        }
        /*
		final int width = 64;
		final int height = 64;
		final int halfWidth = (width / 2) -1;
		final int halfHeight = (height / 2);
		float verticesData[] = new float[height * width * 2];
		for (int y = 0;y < height;++y) {
			for (int x = 0;x < width;++x) {
				verticesData[(y * 2 * width) + (x * 2)] = x - halfWidth;
				verticesData[(y * 2 * width) + (x * 2) + 1] = y - halfHeight;
			}
		}
        */
        mPointCount = verticesData.length / 2;
        mPointsBuffer = ByteBuffer.allocateDirect(verticesData.length
                * FLOAT_SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mPointsBuffer.put(verticesData).position(0);
        int x = 0;
        x += 1;
	}
	
	@Override
	public void draw() {
		if (mLastProgram != mProgram) {
			TEManagerGraphics.switchProgram(PROGRAM_NAME);
			mLastProgram = mProgram;
		}

		mLastPositionHash = -1;
		GLES20.glVertexAttribPointer(mVerticesHandle, 2, GLES20.GL_FLOAT, false, 0, mPointsBuffer);

        GLES20.glVertexAttrib2f(mPositionHandle, parent.position.x, parent.position.y);
        GLES20.glUniform4f(mColorHandle, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, mPointCount);
	}

	@Override
	public void update(long dt) {
		// TODO Auto-generated method stub
	}

}
