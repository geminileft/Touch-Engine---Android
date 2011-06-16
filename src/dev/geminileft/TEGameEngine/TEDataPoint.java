package dev.geminileft.TEGameEngine;

public class TEDataPoint extends TEData {
	private float mX;
	private float mY;
	
	public TEDataPoint(float x, float y) {
		mX = x;
		mY = y;
	}
	
	@Override
	public Object getData() {
		return new Point(mX, mY);
	}
}
