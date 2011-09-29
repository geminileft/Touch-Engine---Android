package dev.geminileft.TEGameEngine;

public class TETexture2D {
	public final static int INVALID_TEXTURE = -1; 
	public int mName = INVALID_TEXTURE;
	private TESize mSize;
	
	public TETexture2D(int name, int width, int height) {
		super();
		mName = name;
		mSize = TESize.make(width, height);
	}
	
	public int getName() { 
		return mName;
	}
	
	public TESize getSize() {
		return mSize;
	}
}
