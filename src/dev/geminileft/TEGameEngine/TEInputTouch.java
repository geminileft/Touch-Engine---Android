package dev.geminileft.TEGameEngine;

public class TEInputTouch {
	private boolean mBegan;
	private boolean mEnd;
	private TEPoint mStartPoint;
	private TEPoint mEndPoint;
	private int mHash;
	
	public TEInputTouch(int hash, float x, float y) {
		mStartPoint = new TEPoint(x,y);
		mEndPoint = new TEPoint(x,y);
		mBegan = true;
		mEnd = false;
	}

	public TEInputTouch(boolean began, boolean ended, TEPoint startPoint, TEPoint endPoint, int hash) {
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
	
	public TEPoint getStartPoint() {
		return mStartPoint;
	}
	
	public TEPoint getEndPoint() {
		return mEndPoint;
	}
	
	public int getPointerId() {
		return mHash;
	}
	
	public void endTouch(TEPoint point) {
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
		mStartPoint = new TEPoint(mEndPoint.x, mEndPoint.y);
	}
	
}
