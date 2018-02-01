package com.parallaxsoft.highjumper;
import android.content.Context;
import java.util.HashMap;
import android.media.AudioManager;
import android.media.SoundPool;

public class parallaxsoftSoundManager {
	 
	static private parallaxsoftSoundManager _instance;
	private static SoundPool parallaxsoftmSoundPool;
	private static HashMap<Integer, Integer> parallaxsoftmSoundPoolMap;
	private static AudioManager  parallaxsoftmAudioManager;
	private static Context parallaxsoftmContext;
 
	private parallaxsoftSoundManager()
	{
	}

	static synchronized public parallaxsoftSoundManager getInstance()
	{
	    if (_instance == null)
	      _instance = new parallaxsoftSoundManager();
	    return _instance;
	 }

	public static  void parallaxsoftparallaxsoftURL_(Context theContext)
	{
		 parallaxsoftmContext = theContext;
	     parallaxsoftmSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
	     parallaxsoftmSoundPoolMap = new HashMap<Integer, Integer>();
	     parallaxsoftmAudioManager = (AudioManager)parallaxsoftmContext.getSystemService(Context.AUDIO_SERVICE);
	} 

	public static void parallaxsoftaddSound(int Index,int SoundID)
	{
		parallaxsoftmSoundPoolMap.put(Index, parallaxsoftmSoundPool.load(parallaxsoftmContext, SoundID, 1));
	}

	public static void loadSounds()
	{
		parallaxsoftmSoundPoolMap.put(3, parallaxsoftmSoundPool.load(parallaxsoftmContext, R.raw.jump, 1));
		parallaxsoftmSoundPoolMap.put(4, parallaxsoftmSoundPool.load(parallaxsoftmContext, R.raw.save, 1));
		parallaxsoftmSoundPoolMap.put(5, parallaxsoftmSoundPool.load(parallaxsoftmContext, R.raw.slow , 1));
		parallaxsoftmSoundPoolMap.put(6, parallaxsoftmSoundPool.load(parallaxsoftmContext, R.raw.trampoline, 1));
		parallaxsoftmSoundPoolMap.put(7, parallaxsoftmSoundPool.load(parallaxsoftmContext, R.raw.petenicesplash , 1));
		parallaxsoftmSoundPoolMap.put(8, parallaxsoftmSoundPool.load(parallaxsoftmContext, R.raw.bonus, 1));
		parallaxsoftmSoundPoolMap.put(9, parallaxsoftmSoundPool.load(parallaxsoftmContext, R.raw.ninek, 1));
		
	}
 
	
	public static void parallaxsoftplaySound(int index,float parallaxspeed, float volumeL, float volumeR, int loopMode)
	{
		float streamVolume = parallaxsoftmAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	     streamVolume = streamVolume / parallaxsoftmAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	     if(parallaxsoftmSoundPoolMap.get(index)!=null)
	    	 parallaxsoftmSoundPool.play(parallaxsoftmSoundPoolMap.get(index), streamVolume*volumeL, streamVolume*volumeR, 1, loopMode, parallaxspeed);
	}
	public static void parallaxsoftplaySound(int index,float parallaxspeed)
	{
		parallaxsoftplaySound(index, parallaxspeed, 1.0f, 1.0f, 0);
	}

	public static void stopSound(int index)
	{
		parallaxsoftmSoundPool.stop(parallaxsoftmSoundPoolMap.get(index));
	}

	public static void cleanup()
	{
		if (parallaxsoftmSoundPool != null) parallaxsoftmSoundPool.release();
		parallaxsoftmSoundPool = null;
		if (parallaxsoftmSoundPoolMap != null) parallaxsoftmSoundPoolMap.clear();
		if (parallaxsoftmAudioManager != null) parallaxsoftmAudioManager.unloadSoundEffects();
	    _instance = null;
 
	}
 
}