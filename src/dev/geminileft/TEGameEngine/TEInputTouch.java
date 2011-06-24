package dev.geminileft.TEGameEngine;

public class TEInputTouch {
	private boolean mBegan;
	private boolean mEnd;
	private Point mStartPoint;
	private Point mEndPoint;
	private int mHash;
	
	public TEInputTouch(int hash, float x, float y) {
		mStartPoint = new Point(x,y);
		mEndPoint = new Point(x,y);
		mBegan = true;
		mEnd = false;
	}

	public TEInputTouch(boolean began, boolean ended, Point startPoint, Point endPoint, int hash) {
		mBegan = began;
		mEnd = ended;
		mStartPoint = startPoint;
		mEndPoint = endPoint;
		mHash = hash;	
	}
	
	public boolean began() {
		return mBegan;
	}
	
	public boolean ended() {
		return mEnd;
	}
	
	public Point getStartPoint() {
		return mStartPoint;
	}
	
	public Point getEndPoint() {
		return mEndPoint;
	}
	
	public int getPointerId() {
		return mHash;
	}
	
	public void endTouch(Point point) {
		mEndPoint.x = point.x;
		mEndPoint.y = point.y;
		mEnd = true;
	}

	public TEInputTouch copy() {
		TEInputTouch touch = new TEInputTouch(mBegan, mEnd,  mStartPoint, mEndPoint, mHash);
		return touch;
	}
	
	public void reset() {
		mBegan = false;
		mEnd = false;
		mStartPoint = new Point(mEndPoint.x, mEndPoint.y);
	}
	
}
