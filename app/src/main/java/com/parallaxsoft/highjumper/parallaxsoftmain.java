package com.parallaxsoft.highjumper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.parallaxsoft.highscore.parallaxsoftmHighscoreAdapterParallaxsoft;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class parallaxsoftmain extends Activity {
		PowerManager.WakeLock wakeLock ;
		private static long parallaxsoftlastCreationTime = 0;
		private static final int parallaxsoftMIN_CREATION_TIMEOUT = 10000;
		MediaPlayer parallaxsoftmusicPlayerLoop;
		boolean parallaxsoftMusicLoopStartedForFirstTime = false;
		boolean parallaxsoftisRunning = false;
		parallaxsoftgameIsLoading parallaxsoftmGameView = null;
	    private static final int parallaxsoftSLEEP_TIME = 300;
		private InterstitialAd mInterstitialAd;


	    @Override
		public void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);


			prepareAd();

			ScheduledExecutorService scheduler =
					Executors.newSingleThreadScheduledExecutor();
			scheduler.scheduleAtFixedRate(new Runnable() {

				Handler handler = new Handler();
				public void run() {
					Log.i("hello", "world");
					runOnUiThread(new Runnable() {
						public void run() {
							if (mInterstitialAd.isLoaded()) {
								Log.d("TAG"," Loaded another ad");
								mInterstitialAd.show();
							}

							else {
								Log.d("TAG"," Interstitial not loaded");
							}
							prepareAd();
						}
					});

				}
			}, 30, 60, TimeUnit.SECONDS);


	    	PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "tag");
			wakeLock.acquire();
			parallaxsoftSoundManager.getInstance();
	        parallaxsoftSoundManager.parallaxsoftparallaxsoftURL_(this);
	        parallaxsoftSoundManager.loadSounds();
	        parallaxsoftmusicPlayerLoop = MediaPlayer.create(getApplicationContext(), R.raw.gamebackground);
	        parallaxsoftmusicPlayerLoop.setLooping(true);
			parallaxsoftmusicPlayerLoop.seekTo(0);
			parallaxsoftmusicPlayerLoop.setVolume(0.5f, 0.5f);
			requestWindowFeature(Window.FEATURE_NO_TITLE);  
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
			parallaxsoftisRunning = true;
			parallaxsoftmGameView = new parallaxsoftgameIsLoading(getApplicationContext());
			setContentView(parallaxsoftmGameView);
		}

		public void  prepareAd(){
			mInterstitialAd = new InterstitialAd(this);
			mInterstitialAd.setAdUnitId("ca-app-pub-6861884208088827/9267345501");
			mInterstitialAd.loadAd(new AdRequest.Builder().build());
		}
	    @Override
	    protected void onDestroy() {
	    	if(parallaxsoftSettings.RHDEBUG)
	    		Log.d("debug", "onDestroy main");
	    	parallaxsoftisRunning = false;
	    	
			wakeLock.release();
			parallaxsoftmusicPlayerLoop.release();
			parallaxsoftSoundManager.cleanup();
			if (parallaxsoftmGameView != null) parallaxsoftmGameView.cleanup();
			System.gc();
			super.onDestroy();
		}
		@Override
		public void onResume() {
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "onResume");
			wakeLock.acquire();
			if(parallaxsoftMusicLoopStartedForFirstTime)
				parallaxsoftmusicPlayerLoop.start();
			super.onResume();

		}
		@Override
		public void onStop() {
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "onStop");
			super.onStop();
		}
		@Override
		public void onRestart() {
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "onRestart");
			super.onRestart();
		}
		@Override
		public void onPause() {
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "onPause");
			wakeLock.release();
			parallaxsoftmusicPlayerLoop.pause();
			super.onPause();
		}
	    
		public void saveScore(int score) {			
			Intent myIntent = new Intent (this, parallaxsoftHighScoreForm.class);
			myIntent.putExtra("score", score);			
			startActivity (myIntent);
		}

		public void sleep() {
			sleep(parallaxsoftSLEEP_TIME);
		}

		public void sleep(int time) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    
	public class parallaxsoftgameIsLoading extends GLSurfaceView implements Runnable {
		private parallaxsoftPlayer parallaxsoftPlayer = null;
		private parallaxsoftLevel parallaxsoftLevel;
		private ParalaxBackground background;
		private int width;
		private int height;
		private parallaxsoftButton parallaxsoftresetParallaxsoftButton = null;
		private Bitmap parallaxsoftresetButtonImg = null;
		private parallaxsoftButton parallaxsoftsaveParallaxsoftButton = null;
		private Bitmap parallaxsoftsaveButtonImg = null;
		private parallaxsoftRHDrawable parallaxsoftblackRHD = null;
		private Bitmap parallaxsoftblackImg = null;
		private parallaxsoftRHDrawable parallaxsoftgameLoadingRHD = null;
		private Bitmap parallaxsoftgameLoadingImg = null;
		private float parallaxsoftblackImgAlpha;
		private boolean parallaxsoftscoreWasSaved = false;
		private boolean parallaxsoftdeathSoundPlayed = false;
		private parallaxsoftOpenGLRenderer parallaxsoftmRenderer = null;
		private parallaxsoftCounterGroup parallaxsoftmCounterGroup;
		private parallaxsoftCounterDigit parallaxsoftmParallaxsoftCounterDigit1;
		private parallaxsoftCounterDigit parallaxsoftmParallaxsoftCounterDigit2;
		private parallaxsoftCounterDigit parallaxsoftmParallaxsoftCounterDigit3;
		private parallaxsoftCounterDigit parallaxsoftmParallaxsoftCounterDigit4;
		private Bitmap parallaxsoftCounterFont = null;;
		private Bitmap parallaxsoftCounterYourScoreImg = null;;
		private parallaxsoftRHDrawable parallaxsoftCounterYourScoreDrawable = null;;;
		public  boolean parallaxsoftdoUpdateCounter = true;
		private long parallaxsofttimeAtLastSecond;
		private int parallaxsoftrunCycleCounter;
		private parallaxsoftmHighscoreAdapterParallaxsoft parallaxsofthighScoreAdapter;

		private int parallaxsoftmTotalHighscores = 0;
		private int parallaxsoftTotalHighscores = 0;
		private int parallaxsoftTotalHighscores2 = 0;
		private int parallaxsoftTotalHighscores3 = 0;
		private int parallaxsoftTotalHighscores4 = 0;
		private int parallaxsoftTotalHighscores5 = 0;

		private parallaxsoftHighscoreMark parallaxsoftmParallaxsoftHighscoreMark1 = null;
		private parallaxsoftHighscoreMark parallaxsoftmParallaxsoftHighscoreMark2 = null;
		private parallaxsoftHighscoreMark parallaxsoftmParallaxsoftHighscoreMark3 = null;
		private parallaxsoftHighscoreMark parallaxsoftmParallaxsoftHighscoreMark4 = null;
		private parallaxsoftHighscoreMark parallaxsoftmParallaxsoftHighscoreMark5 = null;
		
		private Bitmap parallaxsoftmHighscoreMarkBitmap = null;
		private parallaxsoftRHDrawable mNewHighscore = null;
		
		private int totalScore = 0;
		private boolean parallaxsoftnineKwasplayed = false;
		private boolean parallaxsoftgameIsLoading = true;
		

		public parallaxsoftgameIsLoading(Context context) {
			super(context);
			parallaxsoftmRenderer = new parallaxsoftOpenGLRenderer();
			this.setRenderer(parallaxsoftmRenderer);
			parallaxsoftUtil.getInstance().parallaxsoftsetAppContext(context);
			parallaxsoftUtil.getInstance().setAppRenderer(parallaxsoftmRenderer);
	        Thread rHThread = new Thread(this);
			rHThread.start();
		}
		
		public void cleanup() {
			if (parallaxsoftsaveButtonImg != null) parallaxsoftsaveButtonImg.recycle();
			if (parallaxsoftblackImg != null) parallaxsoftblackImg.recycle();
			if (parallaxsoftresetButtonImg!= null) parallaxsoftresetButtonImg.recycle();
			if (background != null) background.cleanup();
			if (parallaxsoftmHighscoreMarkBitmap != null) parallaxsoftmHighscoreMarkBitmap.recycle();
			if (parallaxsoftLevel != null) parallaxsoftLevel.cleanup();
			if (parallaxsoftPlayer != null) parallaxsoftPlayer.cleanup();
		}

		private void initialize() {
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "initialize begin");
			
			long timeOfInitializationStart = System.currentTimeMillis();
			parallaxsoftUtil.parallaxsoftroundStartTime = System.currentTimeMillis();
			Context context = parallaxsoftUtil.getAppContext();
			Rect rectgle= new Rect();
			Window window= getWindow();
			window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
			DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
			Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			width= display.getWidth();  
			height= Math.abs(rectgle.top - rectgle.bottom);
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "displaywidth: " + width + ", displayheight: " + height);
			parallaxsoftUtil.parallaxsoftmScreenHeight=height;
			parallaxsoftUtil.parallaxsoftmScreenWidth=width;
			parallaxsoftUtil.parallaxsoftfetmWidthHeightRatio=width/height;
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inTempStorage = new byte[16*1024];
			parallaxsoftgameLoadingImg = parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_iuytreoading.jpg");
			parallaxsoftgameLoadingRHD = new parallaxsoftRHDrawable(0, 0, 1, width, height);
			parallaxsoftgameLoadingRHD.parallaxsoftlloadBitmap(parallaxsoftgameLoadingImg);
			parallaxsoftmRenderer.addMesh(parallaxsoftgameLoadingRHD);
			long currentTime = System.currentTimeMillis();
	    	
	    	if (currentTime < parallaxsoftlastCreationTime + parallaxsoftMIN_CREATION_TIMEOUT) {
	    		long sleeptime = parallaxsoftMIN_CREATION_TIMEOUT - (currentTime - parallaxsoftlastCreationTime);
	    		parallaxsoftlastCreationTime = currentTime;
    			try {
					Thread.sleep(sleeptime);
				} catch (InterruptedException e) {
					e.printStackTrace();
					finish();
				}
	    	}
	    	parallaxsoftlastCreationTime = System.currentTimeMillis();
			background = new ParalaxBackground(width, height);
			parallaxsoftmRenderer.addMesh(background);
			sleep();
			try {
			
				background.parallaxsoftloadLayerFar(parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_backgutyreround_layer_3.png"));
				sleep();
			} catch (OutOfMemoryError oome) {
				System.gc();
				try {
					Thread.sleep(parallaxsoftMIN_CREATION_TIMEOUT);
			
					background.parallaxsoftloadLayerFar(
							parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_backgutyreround_layer_3.png"));
					sleep();

				}catch (OutOfMemoryError e) { 
					e.printStackTrace();
					setResult(1);
					finish();
				} catch (InterruptedException e) {
					setResult(0);
					finish();
				}
			}
			
			try {
				background.parallaxsoftloadLayerMiddle(parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_backgjyhgtrsefround_layer_2.png"));
				sleep();
			} catch (OutOfMemoryError oome) {
				System.gc();
				try {
					Thread.sleep(parallaxsoftMIN_CREATION_TIMEOUT);
					background.parallaxsoftloadLayerMiddle(parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_backgjyhgtrsefround_layer_2.png"));
					sleep();

				}catch (OutOfMemoryError e) { 
					e.printStackTrace();
					setResult(1);
					finish();
				} catch (InterruptedException e) {
					setResult(0);
					finish();
				}
			}

			try {
				background.parallaxsoftloadLayerNear(parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("asdfwertytuw4e.png"));
				sleep();

			} catch (OutOfMemoryError oome) {
				System.gc();
				try {
					Thread.sleep(parallaxsoftMIN_CREATION_TIMEOUT);
					background.parallaxsoftloadLayerNear(parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("asdfwertytuw4e.png"));
					sleep();

				}catch (OutOfMemoryError e) { 
					e.printStackTrace();
					setResult(1);
					finish();
				} catch (InterruptedException e) {
					setResult(0);
					finish();
				}
			}
			
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "before addMesh");

			parallaxsoftresetButtonImg = parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_but56454554ton_play_again.png");
			parallaxsoftresetParallaxsoftButton = new parallaxsoftButton(
					parallaxsoftUtil.getPercentOfScreenWidth(72),
					height- parallaxsoftUtil.getPercentOfScreenHeight(18),
					-2, 
					parallaxsoftUtil.getPercentOfScreenWidth(26),
					parallaxsoftUtil.getPercentOfScreenHeight(13));
			parallaxsoftresetParallaxsoftButton.parallaxsoftlloadBitmap(parallaxsoftresetButtonImg);
			parallaxsoftmRenderer.addMesh(parallaxsoftresetParallaxsoftButton);
			parallaxsoftsaveButtonImg = parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_button_save.png");
			parallaxsoftsaveParallaxsoftButton = new parallaxsoftButton(
					parallaxsoftUtil.getPercentOfScreenWidth(42),
					height- parallaxsoftUtil.getPercentOfScreenHeight(18),
					-2, 
					parallaxsoftUtil.getPercentOfScreenWidth(26),
					parallaxsoftUtil.getPercentOfScreenHeight(13));
			parallaxsoftsaveParallaxsoftButton.parallaxsoftlloadBitmap(parallaxsoftsaveButtonImg);
			parallaxsoftmRenderer.addMesh(parallaxsoftsaveParallaxsoftButton);
			parallaxsoftPlayer = new parallaxsoftPlayer(getApplicationContext(), parallaxsoftmRenderer, height);
			sleep();
			parallaxsoftLevel = new parallaxsoftLevel(context, parallaxsoftmRenderer, width, height);
			sleep();
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "after player creation");
		    if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "after loading messages");
		    
		    parallaxsofthighScoreAdapter = new parallaxsoftmHighscoreAdapterParallaxsoft(context);

		    if(parallaxsoftSettings.RHDEBUG)
		    	Log.d("debug", "after parallaxsoftmHighscoreAdapter");
			parallaxsoftCounterYourScoreImg = parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_background_score.png");
			parallaxsoftCounterYourScoreDrawable = new parallaxsoftRHDrawable(
					parallaxsoftUtil.getPercentOfScreenWidth(5),
					height- parallaxsoftUtil.getPercentOfScreenHeight(15),
					0.9f, 
					parallaxsoftUtil.getPercentOfScreenWidth(27),
					parallaxsoftUtil.getPercentOfScreenHeight(10));

			parallaxsoftCounterYourScoreDrawable.parallaxsoftlloadBitmap(parallaxsoftCounterYourScoreImg);
			parallaxsoftmRenderer.addMesh(parallaxsoftCounterYourScoreDrawable);

			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "after parallaxsoftCounterYourScoreDrawable addMesh");
			parallaxsoftCounterFont = parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_numberfertyont.png");
			parallaxsoftmCounterGroup = new parallaxsoftCounterGroup(
					parallaxsoftUtil.getPercentOfScreenWidth(14),
					height- parallaxsoftUtil.getPercentOfScreenHeight(13.5f),
					0.9f, parallaxsoftUtil.getPercentOfScreenWidth(16),
					parallaxsoftUtil.getPercentOfScreenHeight(6),
					25);

			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "after parallaxsoftmCounterGroup");
			parallaxsoftmParallaxsoftCounterDigit1 = new parallaxsoftCounterDigit(
					parallaxsoftUtil.getPercentOfScreenWidth(19),
					height- parallaxsoftUtil.getPercentOfScreenHeight(13.5f),
					0.9f, 
					parallaxsoftUtil.getPercentOfScreenWidth(3),
					parallaxsoftUtil.getPercentOfScreenHeight(6));
			parallaxsoftmParallaxsoftCounterDigit1.parallaxsoftlloadBitmap(parallaxsoftCounterFont);
			parallaxsoftmCounterGroup.add(parallaxsoftmParallaxsoftCounterDigit1);
			parallaxsoftmParallaxsoftCounterDigit2 = new parallaxsoftCounterDigit(
					parallaxsoftUtil.getPercentOfScreenWidth(22),
					height- parallaxsoftUtil.getPercentOfScreenHeight(13.5f),
					0.9f,
					parallaxsoftUtil.getPercentOfScreenWidth(3),
					parallaxsoftUtil.getPercentOfScreenHeight(6));
			parallaxsoftmParallaxsoftCounterDigit2.parallaxsoftlloadBitmap(parallaxsoftCounterFont);
			parallaxsoftmCounterGroup.add(parallaxsoftmParallaxsoftCounterDigit2);
			parallaxsoftmParallaxsoftCounterDigit3 = new parallaxsoftCounterDigit(
					parallaxsoftUtil.getPercentOfScreenWidth(25),
					height- parallaxsoftUtil.getPercentOfScreenHeight(13.5f),
					0.9f,
					parallaxsoftUtil.getPercentOfScreenWidth(3),
					parallaxsoftUtil.getPercentOfScreenHeight(6));
			parallaxsoftmParallaxsoftCounterDigit3.parallaxsoftlloadBitmap(parallaxsoftCounterFont);
			parallaxsoftmCounterGroup.add(parallaxsoftmParallaxsoftCounterDigit3);
			parallaxsoftmParallaxsoftCounterDigit4 = new parallaxsoftCounterDigit(
					parallaxsoftUtil.getPercentOfScreenWidth(28),
					height- parallaxsoftUtil.getPercentOfScreenHeight(13.5f),
					0.9f, 
					parallaxsoftUtil.getPercentOfScreenWidth(3),
					parallaxsoftUtil.getPercentOfScreenHeight(6));

			parallaxsoftmParallaxsoftCounterDigit4.parallaxsoftlloadBitmap(parallaxsoftCounterFont);
			parallaxsoftmCounterGroup.add(parallaxsoftmParallaxsoftCounterDigit4);
			parallaxsoftmRenderer.addMesh(parallaxsoftmCounterGroup);
			sleep();
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "after counter");
			

			parallaxsoftblackImg = Bitmap.createBitmap(16, 16, Bitmap.Config.ARGB_8888);
			parallaxsoftblackRHD = new parallaxsoftRHDrawable(0, 0, 1, width, height);
			parallaxsoftblackImg.eraseColor(-16777216);
			parallaxsoftblackImgAlpha=1;
			parallaxsoftblackRHD.setColor(0, 0, 0, parallaxsoftblackImgAlpha);
			parallaxsoftblackRHD.parallaxsoftlloadBitmap(parallaxsoftblackImg);
			parallaxsoftmRenderer.addMesh(parallaxsoftblackRHD);
			parallaxsoftgameLoadingRHD.z = -1.0f;
			parallaxsoftmHighscoreMarkBitmap = parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_highscoremark.png");
			mNewHighscore = new parallaxsoftRHDrawable(width/2 - 128, height/2 - 64, -2, 256, 128);
			mNewHighscore.parallaxsoftlloadBitmap(parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_new_highscore.png"));
			parallaxsoftmRenderer.addMesh(mNewHighscore);
			if(parallaxsoftSettings.showHighscoreMarks)
				parallaxsoftinitHighscoreMarks();
			while(System.currentTimeMillis() < timeOfInitializationStart+ parallaxsoftSettings.TimeForLoadingScreenToBeVisible)
				sleep(10);
			parallaxsofttimeAtLastSecond = System.currentTimeMillis();
	        parallaxsoftrunCycleCounter=0;
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "parallaxsoftgameIsLoading initiation ended");
		}
		
		public int getAmountOfLocalHighscores() {
			parallaxsofthighScoreAdapter.open();
		    Cursor cursor = parallaxsofthighScoreAdapter.parallaxsoftfetchScores("0");
		    int amount = cursor.getCount();
		    parallaxsofthighScoreAdapter.close();
			return amount;
		}
		public int parallaxsoftgetHighscore(long id) {
			parallaxsofthighScoreAdapter.open();
			if (parallaxsoftmTotalHighscores >= id)
			{
			    Cursor cursor = parallaxsofthighScoreAdapter.parallaxsoftgetHighscore(id);
			    String hs = cursor.getString(cursor.getColumnIndexOrThrow(parallaxsoftmHighscoreAdapterParallaxsoft.parallaxsoftmKEY_SCORE));
			    parallaxsofthighScoreAdapter.close();
			    return new Integer(hs);
			}
			else
				return 0;
		}
		public void run() {
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "run method started");
			try{
				if(parallaxsoftSettings.RHDEBUG)
					Log.d("debug", "run method in try");
				if(parallaxsoftSettings.RHDEBUG)
					Log.d("debug", "parallaxsoftmRenderer.parallaxsoftfirstFrameDone: " + parallaxsoftmRenderer.parallaxsoftfirstFrameDone);
			
				while(!parallaxsoftmRenderer.parallaxsoftfirstFrameDone)
					Thread.sleep(10);
				initialize();
				long timeAtStart = System.currentTimeMillis();
				while (System.currentTimeMillis() < timeAtStart + 2000 && parallaxsoftisRunning)
				{
					parallaxsoftblackImgAlpha-=0.005;
					parallaxsoftblackRHD.setColor(0, 0, 0, parallaxsoftblackImgAlpha);
					Thread.sleep(10);
				}
				parallaxsoftblackImg.recycle();
				parallaxsoftgameLoadingImg.recycle();
				parallaxsoftblackRHD.parallaxsoftshouldBeDrawn = false;
				parallaxsoftgameLoadingRHD.parallaxsoftshouldBeDrawn = false;
				parallaxsoftmRenderer.removeMesh(parallaxsoftblackRHD);
				parallaxsoftmRenderer.removeMesh(parallaxsoftgameLoadingRHD);
				if(parallaxsoftSettings.RHDEBUG)
					Log.d("debug", "after fade in");

				try {
					if(parallaxsoftisRunning && !parallaxsoftmusicPlayerLoop.isPlaying())
						parallaxsoftmusicPlayerLoop.start();
					
				} catch (IllegalStateException e) {
					e.printStackTrace();
					Log.w(parallaxsoftSettings.LOG_TAG, "seems like you startet the game more" +
							" than once in a few seconds or canceld the game start");
					Log.w(parallaxsoftSettings.LOG_TAG, "PLEASE DO NOT DO THIS UNLESS IT IS A STRESS TEST");
					return;
				}
				
				parallaxsoftMusicLoopStartedForFirstTime=true;

			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				return;
			}
			
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "run method after try catch");
			
			parallaxsoftblackRHD.z=-1.0f;
			parallaxsoftblackRHD.setColor(0, 0, 0, 0);
			long timeForOneCycle=0;
			long parallaxsoftcurrentTimeTaken=0;
			long starttime = 0;
	        parallaxsoftgameIsLoading = false;
			
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "run method befor while");
			parallaxsoftUtil.parallaxsoftroundStartTime = System.currentTimeMillis();
			
			while(parallaxsoftisRunning){
				starttime = System.currentTimeMillis();
				parallaxsoftPlayer.playerParallaxsoftSprite.parallaxsoftsetparallaxsoftFrameUpdateTime(
						(parallaxsoftLevel.parallaxsoftbaseSpeedMax+ parallaxsoftLevel.parallaxsoftextraSpeedMax)*10 -
						((parallaxsoftLevel.parallaxsoftbaseSpeed+ parallaxsoftLevel.parallaxsoftextraSpeed)*10) +
						60 );
				if (parallaxsoftPlayer.update()) {
						if(parallaxsoftSettings.RHDEBUG){
							parallaxsoftcurrentTimeTaken = System.currentTimeMillis()- starttime;
							Log.d("runtime", "time after player update: " + Integer.toString((int)parallaxsoftcurrentTimeTaken));
						}
						parallaxsoftLevel.update();
						if(parallaxsoftSettings.RHDEBUG){
							parallaxsoftcurrentTimeTaken = System.currentTimeMillis()- starttime;
							Log.d("runtime", "time after level update: " + Integer.toString((int)parallaxsoftcurrentTimeTaken));
						}
						background.update();
						
						if(parallaxsoftSettings.RHDEBUG){
							parallaxsoftcurrentTimeTaken = System.currentTimeMillis()- starttime;
							Log.d("runtime", "time after background update: " + Integer.toString((int)parallaxsoftcurrentTimeTaken));
						}
						if(parallaxsoftSettings.showHighscoreMarks)
							parallaxsoftinitupdateHighscoreMarks();
						
				} else {
					if(parallaxsoftPlayer.y < 0){
						parallaxsoftdoUpdateCounter=false;
						parallaxsoftresetParallaxsoftButton.parallaxsoftsetShowButton(true);
						parallaxsoftresetParallaxsoftButton.z = 1.0f;
						parallaxsoftsaveParallaxsoftButton.parallaxsoftsetShowButton(true);
						parallaxsoftsaveParallaxsoftButton.z = 1.0f;
						if(!parallaxsoftdeathSoundPlayed){
							parallaxsoftSoundManager.parallaxsoftplaySound(7, 1, 0.5f, 0.5f, 0);
							parallaxsoftdeathSoundPlayed=true;
							
							System.gc();
						}
						if(parallaxsoftSettings.showHighscoreMarks){
							if (totalScore > parallaxsoftTotalHighscores)
								mNewHighscore.z = 1.0f;
						}
					}
				}
				
				if(parallaxsoftPlayer.parallaxsoftcollidedWithObstacle(parallaxsoftLevel.parallaxsoftgetLevelPosition()) ){
					parallaxsoftLevel.lowerSpeed();
				}
				
				if(parallaxsoftdoUpdateCounter)
				{
					totalScore = parallaxsoftLevel.getDistanceScore() + parallaxsoftPlayer.getBonusScore();
					if (parallaxsoftSettings.SHOW_FPS) parallaxsoftmCounterGroup.parallaxsofttryToSetCounterTo(parallaxsoftmRenderer.fps);
					else parallaxsoftmCounterGroup.parallaxsofttryToSetCounterTo(totalScore);
					
					if(totalScore>=9000 && parallaxsoftnineKwasplayed==false)
					{
						parallaxsoftnineKwasplayed=true;
						parallaxsoftSoundManager.parallaxsoftplaySound(9, 1, 1000, 1000, 0);
					}
				}

				if(parallaxsoftSettings.RHDEBUG){
					timeForOneCycle= System.currentTimeMillis()- starttime;
					Log.d("runtime", "time after counter update: " + Integer.toString((int)timeForOneCycle));
				}
				timeForOneCycle= System.currentTimeMillis()- starttime;

				if(timeForOneCycle < 10) {
					try{ Thread.sleep(10-timeForOneCycle); }
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				parallaxsoftrunCycleCounter++;
				
				if(parallaxsoftSettings.RHDEBUG) {
					parallaxsoftcurrentTimeTaken = System.currentTimeMillis()- starttime;
					Log.d("runtime", "time after thread sleep : " + Integer.toString((int)parallaxsoftcurrentTimeTaken));
				}
				timeForOneCycle= System.currentTimeMillis()- starttime;
				if((System.currentTimeMillis() - parallaxsofttimeAtLastSecond) > 1000 && parallaxsoftSettings.RHDEBUG)
				{
					parallaxsofttimeAtLastSecond = System.currentTimeMillis();
					Log.d("runtime", "run cycles per second: " + Integer.toString(parallaxsoftrunCycleCounter));
					parallaxsoftrunCycleCounter=0;
				}
				if(parallaxsoftSettings.RHDEBUG){
					timeForOneCycle= System.currentTimeMillis()- starttime;
					Log.d("runtime", "overall time for this run: " + Integer.toString((int)timeForOneCycle));
				}
			}
			
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "run method ended");
			
		}
		
		private void parallaxsoftinitHighscoreMarks()
		{
			parallaxsoftmTotalHighscores = getAmountOfLocalHighscores();
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "parallaxsoftmTotalHighscores: " + parallaxsoftmTotalHighscores);
			switch(parallaxsoftmTotalHighscores)
			{
			default:
			case 5:
				parallaxsoftTotalHighscores5 = parallaxsoftgetHighscore(5);
				if (parallaxsoftmParallaxsoftHighscoreMark5 == null)
					parallaxsoftmParallaxsoftHighscoreMark5 = new parallaxsoftHighscoreMark(parallaxsoftmRenderer, parallaxsoftmHighscoreMarkBitmap, parallaxsoftCounterFont);
				parallaxsoftmParallaxsoftHighscoreMark5.parallaxsoftsetMarkTo(5, parallaxsoftTotalHighscores5);
				parallaxsoftmParallaxsoftHighscoreMark5.z = 0.0f;
			case 4:
				parallaxsoftTotalHighscores4 = parallaxsoftgetHighscore(4);
				if (parallaxsoftmParallaxsoftHighscoreMark4 == null)
					parallaxsoftmParallaxsoftHighscoreMark4 = new parallaxsoftHighscoreMark(parallaxsoftmRenderer, parallaxsoftmHighscoreMarkBitmap, parallaxsoftCounterFont);
				parallaxsoftmParallaxsoftHighscoreMark4.parallaxsoftsetMarkTo(4, parallaxsoftTotalHighscores4);
				parallaxsoftmParallaxsoftHighscoreMark4.z = 0.0f;
			case 3:
				parallaxsoftTotalHighscores3 = parallaxsoftgetHighscore(3);
				if (parallaxsoftmParallaxsoftHighscoreMark3 == null)
					parallaxsoftmParallaxsoftHighscoreMark3 = new parallaxsoftHighscoreMark(parallaxsoftmRenderer, parallaxsoftmHighscoreMarkBitmap, parallaxsoftCounterFont);
				parallaxsoftmParallaxsoftHighscoreMark3.parallaxsoftsetMarkTo(3, parallaxsoftTotalHighscores3);
				parallaxsoftmParallaxsoftHighscoreMark3.z = 0.0f;
			case 2:
				parallaxsoftTotalHighscores2 = parallaxsoftgetHighscore(2);
				if (parallaxsoftmParallaxsoftHighscoreMark2 == null)
					parallaxsoftmParallaxsoftHighscoreMark2 = new parallaxsoftHighscoreMark(parallaxsoftmRenderer, parallaxsoftmHighscoreMarkBitmap, parallaxsoftCounterFont);
				parallaxsoftmParallaxsoftHighscoreMark2.parallaxsoftsetMarkTo(2, parallaxsoftTotalHighscores2);
				parallaxsoftmParallaxsoftHighscoreMark2.z = 0.0f;
			case 1:
				parallaxsoftTotalHighscores = parallaxsoftgetHighscore(1);

				if(parallaxsoftSettings.RHDEBUG)
					Log.d("debug", "parallaxsoftTotalHighscores: " + parallaxsoftTotalHighscores);
				
				if (parallaxsoftmParallaxsoftHighscoreMark1 == null)
					parallaxsoftmParallaxsoftHighscoreMark1 = new parallaxsoftHighscoreMark(parallaxsoftmRenderer, parallaxsoftmHighscoreMarkBitmap, parallaxsoftCounterFont);
				parallaxsoftmParallaxsoftHighscoreMark1.parallaxsoftsetMarkTo(1, parallaxsoftTotalHighscores);

				parallaxsoftmParallaxsoftHighscoreMark1.z = 0.0f;
			case 0:
			}
		}
		
		private void parallaxsoftinitupdateHighscoreMarks()
		{	
			switch(parallaxsoftmTotalHighscores)
			{
			default:
			case 5:
				if (parallaxsoftmParallaxsoftHighscoreMark5 != null)
				{
					if (totalScore < parallaxsoftTotalHighscores5)
						parallaxsoftmParallaxsoftHighscoreMark5.x = (parallaxsoftTotalHighscores5 - totalScore) * 10 + parallaxsoftPlayer.x;
					else
						parallaxsoftmParallaxsoftHighscoreMark5.x = 0;
				}
			case 4:
				if (parallaxsoftmParallaxsoftHighscoreMark4 != null)
				{
					if (totalScore < parallaxsoftTotalHighscores4)
						parallaxsoftmParallaxsoftHighscoreMark4.x = (parallaxsoftTotalHighscores4 - totalScore) * 10 + parallaxsoftPlayer.x;
					else
					{
						parallaxsoftmParallaxsoftHighscoreMark4.x = 0;
						if (parallaxsoftmParallaxsoftHighscoreMark5 != null)
							parallaxsoftmParallaxsoftHighscoreMark5.z = -2.0f;
					}
				}
			case 3:
				if (parallaxsoftmParallaxsoftHighscoreMark3 != null)
				{
					if (totalScore < parallaxsoftTotalHighscores3)
						parallaxsoftmParallaxsoftHighscoreMark3.x = (parallaxsoftTotalHighscores3 - totalScore) * 10 + parallaxsoftPlayer.x;
					else
					{
						parallaxsoftmParallaxsoftHighscoreMark3.x = 0;
						if (parallaxsoftmParallaxsoftHighscoreMark4 != null)
							parallaxsoftmParallaxsoftHighscoreMark4.z = -2.0f;
					}
				}
			case 2:
				if (parallaxsoftmParallaxsoftHighscoreMark2 != null)
				{
					if (totalScore < parallaxsoftTotalHighscores2)
						parallaxsoftmParallaxsoftHighscoreMark2.x = (parallaxsoftTotalHighscores2 - totalScore) * 10 + parallaxsoftPlayer.x;
					else
					{
						parallaxsoftmParallaxsoftHighscoreMark2.x = 0;
						if (parallaxsoftmParallaxsoftHighscoreMark3 != null)
							parallaxsoftmParallaxsoftHighscoreMark3.z = -2.0f;
					}
				}
			case 1:
				if (parallaxsoftmParallaxsoftHighscoreMark1 != null)
				{
					if (totalScore < parallaxsoftTotalHighscores)
						parallaxsoftmParallaxsoftHighscoreMark1.x = (parallaxsoftTotalHighscores - totalScore) * 10 + parallaxsoftPlayer.x;
					else
					{
						parallaxsoftmParallaxsoftHighscoreMark1.x = 0;
						if (parallaxsoftmParallaxsoftHighscoreMark2 != null)
							parallaxsoftmParallaxsoftHighscoreMark2.z = -2.0f;
					}
				}
			case 0:
			}

		}
		

		public boolean onTouchEvent(MotionEvent event) {
			if(!parallaxsoftgameIsLoading){
				if(event.getAction() == MotionEvent.ACTION_UP)
					parallaxsoftPlayer.setJump(false);
				
				else if(event.getAction() == MotionEvent.ACTION_DOWN){
					if (parallaxsoftresetParallaxsoftButton.parallaxsoftgetShowButton() || parallaxsoftsaveParallaxsoftButton.parallaxsoftgetShowButton()) {
						if(parallaxsoftresetParallaxsoftButton.parallaxsoftisClicked( event.getX(), parallaxsoftUtil.getInstance().toScreenY((int)event.getY()) ) ){
							System.gc();
							parallaxsoftPlayer.reset();
							parallaxsoftLevel.reset();
							parallaxsoftresetParallaxsoftButton.parallaxsoftsetShowButton(false);
							parallaxsoftresetParallaxsoftButton.z = -2.0f;
							parallaxsoftsaveParallaxsoftButton.parallaxsoftsetShowButton(false);
							parallaxsoftsaveParallaxsoftButton.z = -2.0f;
							parallaxsoftsaveParallaxsoftButton.x = parallaxsoftsaveParallaxsoftButton.lastX;
							parallaxsoftmCounterGroup.parallaxsoftresetCounter();
							parallaxsoftscoreWasSaved=false;
							parallaxsoftdeathSoundPlayed=false;
							parallaxsoftSoundManager.parallaxsoftplaySound(1, 1);
							parallaxsoftdoUpdateCounter=true;
							
							if(parallaxsoftSettings.showHighscoreMarks){
								mNewHighscore.z = -2.0f;
								parallaxsoftinitHighscoreMarks();
							}
								
							parallaxsoftnineKwasplayed = false;
							totalScore = 0;
							parallaxsoftUtil.parallaxsoftroundStartTime = System.currentTimeMillis();
						}
						else if(parallaxsoftsaveParallaxsoftButton.parallaxsoftisClicked( event.getX(), parallaxsoftUtil.getInstance().toScreenY((int)event.getY())  ) && !parallaxsoftscoreWasSaved){
							parallaxsoftsaveParallaxsoftButton.parallaxsoftsetShowButton(false);
							parallaxsoftsaveParallaxsoftButton.z = -2.0f;
							parallaxsoftsaveParallaxsoftButton.lastX = parallaxsoftsaveParallaxsoftButton.x;
							parallaxsoftsaveParallaxsoftButton.x = -5000;
							saveScore(totalScore);
							parallaxsoftSoundManager.parallaxsoftplaySound(4, 1);
							parallaxsoftscoreWasSaved=true;
						}
					}
					else {
						parallaxsoftPlayer.setJump(true);
					}
				}
			}
			
			return true;
		}
	}
}
