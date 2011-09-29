package dev.geminileft.TEGameEngine;

public class TESize {
	public int width;
	public int height;
	
	public TESize(int newWidth, int newHeight) {
		width = newWidth;
		height = newHeight;
	}
	
	public static TESize make(int newWidth, int newHeight) {
		return new TESize(newWidth, newHeight);
	}
	
	public static TESize zero() {
		return new TESize(0, 0);
	}
	
	public boolean isZero() {
		return (width == 0) && (height == 0);
	}
}
