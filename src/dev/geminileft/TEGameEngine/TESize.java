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
}
