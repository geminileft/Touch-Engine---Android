package dev.geminileft.TEGameEngine;

import javax.microedition.khronos.opengles.GL10;

public abstract class TERenderComponent extends TEComponent {
	public abstract void draw(GL10 gl);
}
