package dev.geminileft.opengltest;

import android.content.Context;
import dev.geminileft.TEGameEngine.TEDrawableComponent;
import dev.geminileft.TEGameEngine.TEEngine;
import dev.geminileft.TEGameEngine.TERenderManager;

public class SampleGame extends TEEngine {
	private TEDrawableComponent mCard;

	public SampleGame(Context context) {
		super(context);
	}
	
	@Override
	public void start() {
        mCard = new TEDrawableComponent(getGraphicsManager(), getContext(), R.drawable.c_a, 200, 400);
	}

	@Override
	public void run() {
		mCard.draw();
		
	}
}
