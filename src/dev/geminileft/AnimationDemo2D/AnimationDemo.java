package dev.geminileft.AnimationDemo2D;

import dev.geminileft.TEGameEngine.MathUtils;
import dev.geminileft.TEGameEngine.MovementPlayer;
import dev.geminileft.TEGameEngine.RenderAnimation;
import dev.geminileft.TEGameEngine.TEEngine;
import dev.geminileft.TEGameEngine.TEGameObject;
import dev.geminileft.TEGameEngine.TEPoint;
import dev.geminileft.TEGameEngine.TESize;
import dev.geminileft.TEGameEngine.TEUtilDrawable;
import dev.geminileft.opengltest.R;

public class AnimationDemo extends TEEngine {

	public AnimationDemo(int width, int height) {
		super(width, height);
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		TEGameObject gameObject = new TEGameObject();
		gameObject.position = TEPoint.make(200, 200);
		RenderAnimation animation = new RenderAnimation(TEGameObject.ObjectState.NORMAL);
		TESize size = TESize.make(64, 128);
		TEPoint offset = TEPoint.make(0, 0);
		animation.addFrameAnimation(MathUtils.framesToMillis(30, 8), new TEUtilDrawable(R.drawable.rabbit_redone, size, offset));
		gameObject.addComponent(animation);
		final int frameDuration = MathUtils.framesToMillis(30, 3);
		animation = new RenderAnimation(TEGameObject.ObjectState.MOVING);
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_001, size, offset));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_002, size, offset));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_003, size, offset));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_004, size, offset));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_005, size, offset));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_006, size, offset));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_007, size, offset));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_008, size, offset));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_009, size, offset));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_010, size, offset));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_011, size, offset));
		gameObject.addComponent(animation);
		gameObject.state = TEGameObject.ObjectState.MOVING;
        addGameObject(gameObject);

        size = TESize.make(91, 81);
		gameObject = new TEGameObject();
		gameObject.position = TEPoint.make(300, 200);
		animation = new RenderAnimation(TEGameObject.ObjectState.NORMAL);
		TEUtilDrawable d1 = new TEUtilDrawable(R.drawable.gunman_atlas, size, TEPoint.make(144, 98)); 
		TEUtilDrawable d2 = new TEUtilDrawable(R.drawable.gunman_atlas, size, TEPoint.make(35, 98)); 
		animation.addFrameAnimation(MathUtils.framesToMillis(30, 8)
				, d1);
		animation.addFrameAnimation(MathUtils.framesToMillis(30, 8)
				, d1);
		animation.addFrameAnimation(MathUtils.framesToMillis(30, 8)
				, d2);
		animation.addFrameAnimation(MathUtils.framesToMillis(30, 8)
				, d2);
		animation.addFrameAnimation(MathUtils.framesToMillis(30, 8)
				, d1);
		gameObject.addComponent(animation);
		animation = new RenderAnimation(TEGameObject.ObjectState.MOVING);
		animation.addFrameAnimation(MathUtils.framesToMillis(30, 8)
				, new TEUtilDrawable(R.drawable.gunman_atlas, size, TEPoint.make(424, 5)));
		animation.addFrameAnimation(MathUtils.framesToMillis(30, 8)
				, new TEUtilDrawable(R.drawable.gunman_atlas, size, TEPoint.make(326, 4)));
		animation.addFrameAnimation(MathUtils.framesToMillis(30, 8)
				, new TEUtilDrawable(R.drawable.gunman_atlas, size, TEPoint.make(220, 2)));
		animation.addFrameAnimation(MathUtils.framesToMillis(30, 8)
				, new TEUtilDrawable(R.drawable.gunman_atlas, size, TEPoint.make(119, 2)));
		animation.addFrameAnimation(MathUtils.framesToMillis(30, 8)
				, new TEUtilDrawable(R.drawable.gunman_atlas, size, TEPoint.make(22, 4)));
		gameObject.addComponent(animation);
		gameObject.addComponent(new MovementPlayer());
		//gameObject.addComponent(animation);
		addGameObject(gameObject);
	}
}
