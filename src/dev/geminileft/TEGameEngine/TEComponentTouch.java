package dev.geminileft.TEGameEngine;

public abstract class TEComponentTouch extends TEComponent {
	public abstract boolean addTouch(TEInputTouch touch);
	public abstract boolean updateTouch(TEInputTouch touch);

	public boolean containsPoint(TEPoint point) {
		boolean returnValue = false;
		TEPoint position = parent.position;
		TESize size  = parent.size;
		float left = (float)position.x - ((float)size.width / 2);
		float right = (float)position.x + ((float)size.width / 2);
		float bottom = (float)position.y - ((float)size.height / 2);
		float top = (float)position.y + ((float)size.height / 2);
		if ((point.x >= left) && (point.x <= right) && (point.y >= bottom) && (point.y <= top)) {
			returnValue = true;
		}
		return returnValue;
	}
}
