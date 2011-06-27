package dev.geminileft.TEGameEngine;

public class TouchBounce extends TEComponentTouch {
	private boolean mMoving = false;

	@Override
	public boolean addTouch(TEInputTouch touch) {
		if (mMoving) {
			mMoving = false;
		} else {
			mMoving = true;
		}
		return true;
	}
	@Override
	public boolean updateTouch(TEInputTouch touch) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void update() {
	    if (mMoving) {
	    	TEGameObject parent = getParent();
			Point position = parent.position;
			if (position.y <= 0) {
				mMoving = false;
			} else {
				parent.position = new Point(position.x, --position.y);
			}
	    }
	}
}
