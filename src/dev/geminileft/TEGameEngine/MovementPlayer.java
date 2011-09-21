package dev.geminileft.TEGameEngine;

import java.util.HashMap;

import android.view.KeyEvent;

public class MovementPlayer extends TEComponentMovement {
	private boolean mMoving = false;
	private final int MOVE_VELOCITY = 3;
	
	@Override
	public void update(long dt) {
		TEManagerInput inputManager = TEManagerInput.sharedManager();
		HashMap<Integer, TEInputKey> keyInput = inputManager.getKeyState();
		TEInputKey rightKey = keyInput.get(KeyEvent.KEYCODE_D);
		TEInputKey leftKey = keyInput.get(KeyEvent.KEYCODE_A);
		if (rightKey != null || leftKey != null) {
			parent.state = TEGameObject.ObjectState.MOVING;
			mMoving = true;
			int velocity = 0;
			if (rightKey != null) {
				velocity = MOVE_VELOCITY;
			} else if (leftKey != null) {
				velocity = -MOVE_VELOCITY;
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
