package dev.geminileft.AnimationDemo2D;

import dev.geminileft.TEGameEngine.MathUtils;
import dev.geminileft.TEGameEngine.RenderAnimation;
import dev.geminileft.TEGameEngine.RenderImage;
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
		RenderAnimation animation = new RenderAnimation();
		TESize size = TESize.make(64, 128);
		final int frameDuration = MathUtils.framesToMillis(30, 3);
		animation.addFrameAnimation(MathUtils.framesToMillis(30, 8), new TEUtilDrawable(R.drawable.rabbit_redone, size));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_001, size));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_002, size));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_003, size));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_004, size));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_005, size));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_006, size));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_007, size));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_008, size));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_009, size));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_010, size));
		animation.addFrameAnimation(frameDuration, new TEUtilDrawable(R.drawable.rabbit_frame_011, size));
		gameObject.addComponent(animation);
        addGameObject(gameObject);

		gameObject = new TEGameObject();
		gameObject.position = TEPoint.make(300, 200);
		RenderImage renderImage = new RenderImage(R.drawable.tree12_e, null, TESize.make(64, 128));
		gameObject.addComponent(renderImage);
		addGameObject(gameObject);
	}
}
