package dev.geminileft.TEGameEngine;

public class TEPoint {
	public float x;
	public float y;
	
	public TEPoint(float newX, float newY) {
		x = newX;
		y = newY;
	}
	
	public void update(TEPoint point) {
		x = point.x;
		y = point.y;
	}
	
	public static TEPoint make(float newX, float newY) {
		return new TEPoint(newX, newY);
	}
	
	public static TEPoint zero() {
		return new TEPoint(0, 0);
	}
	
	public boolean isZero() {
		return (x == 0) && (y == 0);
	}
}
