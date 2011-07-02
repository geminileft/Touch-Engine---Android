package dev.geminileft.TEGameEngine;

public class TEUtilRect {
		public float left;
		public float right;
		public float top;
		public float bottom;
		
		public TEUtilRect(Point position, Size size) {
			left = position.x - ((float)size.width / 2);
			right = left + size.width;
			bottom = position.y - ((float)size.height / 2);
			top = bottom + size.height;
		}
		
		public boolean overlaps(TEUtilRect rect) {
			return
				(left <= rect.right)
				&& (right >= rect.left)
				&& (bottom <= rect.top)
				&& (top >= rect.bottom);
		}
}
