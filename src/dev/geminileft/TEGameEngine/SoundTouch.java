package dev.geminileft.TEGameEngine;

import android.content.Context;
import android.media.SoundPool;
import android.util.Log;

public class SoundTouch extends TEComponentSound {
	private int mSoundId;
	
	private TEComponent.EventListener mTouchEndedEventListener = new TEComponent.EventListener() {
		
		@Override
		public void invoke() {
			Log.v("info", "we are here");
			SoundPool soundPool = TEManagerAudio.getSoundPool();
			soundPool.play(mSoundId, 1.0f, 1.0f, 1, 0, 1.0f);
		}
	};
	
	public SoundTouch(int resourceId) {
		super();
		Context context = TEStaticSettings.getContext();
		SoundPool soundPool = TEManagerAudio.getSoundPool();
		if (soundPool != null) {
			mSoundId = soundPool.load(context, resourceId, 1);			
		}
		addEventSubscription(TEComponent.Event.EVENT_TOUCH_ENDED, mTouchEndedEventListener);
	}
	
	public void update() {
		super.update();
	}
}
