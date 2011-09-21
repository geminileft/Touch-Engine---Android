package dev.geminileft.TEGameEngine;

public class TETexture2D {
	public final static int INVALID_TEXTURE = -1; 
	public int mName = INVALID_TEXTURE;
	
	public TETexture2D(int name) {
		super();
		mName = name;
	}
	
	public int getName() { 
		return mName;
	}
}
