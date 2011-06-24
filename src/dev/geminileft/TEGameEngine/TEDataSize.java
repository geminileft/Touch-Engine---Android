package dev.geminileft.TEGameEngine;

public class TEDataSize extends TEData {
	private int mWidth;
	private int mHeight;
	
	public TEDataSize(Size size) {
		mWidth = size.width;
		mHeight = size.height;
	}
	@Override
	public Object getData() {
		return new Size(mWidth, mHeight);
	}
}
