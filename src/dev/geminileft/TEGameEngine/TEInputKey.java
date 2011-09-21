package dev.geminileft.TEGameEngine;

public class TEInputKey {
	private int mKeyCode;
	private boolean mStarted = true;
	private boolean mEnded = false;
	
	public TEInputKey(int keyCode) {
		mKeyCode = keyCode;
	}
	
	public TEInputKey(int keyCode, boolean started, boolean ended) {
		mKeyCode = keyCode;
		mStarted = started;
		mEnded = ended;
	}
	
	public void endKey() {
		mEnded = true;
	}
	
	public boolean didEnd() {
		return mEnded;
	}
	
	public boolean didStart() { 
		return mStarted;
	}
	
	public int getKeyCode() {
		return mKeyCode;
	}
	
	public void reset() {
		mStarted = false;
	}
	
	public TEInputKey copy() {
		return new TEInputKey(mKeyCode, mStarted, mEnded);
	}
}
