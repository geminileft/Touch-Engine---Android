package dev.geminileft.TEGameEngine;

public class Point {
	public float x;
	public float y;
	
	public Point(float newX, float newY) {
		x = newX;
		y = newY;
	}
	
	public void update(Point point) {
		x = point.x;
		y = point.y;
	}
}
