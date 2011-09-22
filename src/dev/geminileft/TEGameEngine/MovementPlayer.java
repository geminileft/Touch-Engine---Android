package dev.geminileft.TEGameEngine;


public class MovementPlayer extends TEComponentMovement {
	private boolean mMoving = false;
	private final int MOVE_VELOCITY = 3;
	
	@Override
	public void update(long dt) {
		TEManagerInput inputManager = TEManagerInput.sharedManager();
		final long movementButtonBitmask = TEManagerInput.LEFT_BUTTON | TEManagerInput.RIGHT_BUTTON;
		final long buttons = inputManager.getButtonState() & movementButtonBitmask;
		if (buttons > 0) {
			parent.state = TEGameObject.ObjectState.MOVING;
			mMoving = true;
			int velocity = 0;
			if ((buttons & TEManagerInput.RIGHT_BUTTON) > 0) {
				velocity = MOVE_VELOCITY;
				parent.direction = TEGameObject.ObjectDirection.NORMAL;
			} else if ((buttons & TEManagerInput.LEFT_BUTTON) > 0) {
				velocity = -MOVE_VELOCITY;
				parent.direction = TEGameObject.ObjectDirection.REVERSE;
			}
			parent.position.x += velocity;			
		} else {
			if (mMoving) {
				parent.state = TEGameObject.ObjectState.NORMAL;
				mMoving = false;
			}
		}
	}
}
