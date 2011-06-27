package dev.geminileft.TEGameEngine;

import android.media.AudioManager;
import android.media.SoundPool;

public class TEManagerAudio {
	private final static int MAX_STREAMS = 8;
	
	private static SoundPool mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
	
	public static SoundPool getSoundPool() {
		return mSoundPool;
	}
}
