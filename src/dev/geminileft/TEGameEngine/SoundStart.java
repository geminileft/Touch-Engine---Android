package dev.geminileft.TEGameEngine;

import android.content.Context;
import android.media.SoundPool;

public class SoundStart extends TEComponentSound {
	private int mSoundId;
	private boolean hasPlayed;
	
	public SoundStart(int resourceId) {
		super();
		Context context = TEStaticSettings.getContext();
		SoundPool soundPool = TEManagerAudio.getSoundPool();
		if (soundPool != null) {
			mSoundId = soundPool.load(context, resourceId, 1);			
		}
	}
	
	@Override
	public void update(long dt) {
		if (!hasPlayed) {
			SoundPool soundPool = TEManagerAudio.getSoundPool();
			soundPool.play(mSoundId, 1.0f, 1.0f, 1, 0, 1.0f);
			hasPlayed = true;
		}
	}
}
