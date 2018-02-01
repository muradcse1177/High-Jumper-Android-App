package com.parallaxsoft.highjumper;
import java.util.Random;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;


public class parallaxsoftLevel {
	private int width;
	private int height;
	private float parallaxsoftlevelPosition;
	private float parallaxsoftdeltaLevelPosition;
	public float parallaxsoftbaseSpeed;
	public float parallaxsoftbaseSpeedStart;
	public float parallaxsoftbaseSpeedMax;
	public float parallaxsoftbaseSpeedMaxStart;
	public float parallaxsoftbaseSpeedAcceleration;
	public float parallaxsoftextraSpeed;
	public float parallaxsoftextraSpeedStart;
	public float parallaxsoftextraSpeedMax;
	public float parallaxsoftextraSpeedMaxStart;
	public float parallaxsoftextraSpeedAcceleration;
	public int timeUntilNextSpeedIncreaseMillis;
	public static parallaxsoftBlock[] parallaxsoftBlockData;
	public static final int parallaxsoftmaxBlocks = 5;
	private int parallaxsofleftBlockIndex;
	private int parallaxsofrightBlockIndex;
	public static parallaxsoftObstacle[] parallaxsoftobstacleDataSlower;
	public static final int parallaxsoftmaxObstaclesSlower = parallaxsoftmaxBlocks;
	private int parallaxsoftleftSlowerIndex;
	private int parallaxsoftrightSlowerIndex;
	public static parallaxsoftObstacleJump[] parallaxsoftobstacleDataJumper;
	public static final int parallaxsoftmaxObstaclesJumper = parallaxsoftmaxBlocks;
	private int parallaxsoftleftJumperIndex;
	private int parallaxsoftrightJumperIndex;
	public static parallaxsoftObstacleBonus[] parallaxsoftobstacleDataBonus;
	public static final int parallaxsoftmaxObstaclesBonus = parallaxsoftmaxBlocks;
	private int parallaxsoftleftBonusIndex;
	private int parallaxsoftrightBonusIndex;
	private float parallaxsoftobstacleJumperWidth;
	private float parallaxsoftobstacleJumperHeight;
	private float parallaxsoftobstacleSlowerWidth;
	private float parallaxsoftobstacleSlowerHeight;
	private float parallaxsoftobstacleBonusWidth;
	private float parallaxsoftobstacleBonusHeight;
	private float obstacleBonusDistanceToBlock;
	
	private final int parallaxsoftOBSTACLEMASK_0_NO_OBSTACLE = 80;
	private final int parallaxsoftOBSTACLEMASK_1_JUMP = 30;
	private final int parallaxsoftOBSTACLEMASK_2_SLOW = 30;
	private final int parallaxsoftOBSTACLEMASK_3_JUMP_SLOW = 20;
	private final int parallaxsoftOBSTACLEMASK_4_BONUS = 40;
	private final int parallaxsoftOBSTACLEMASK_5_JUMP_BONUS = 20;
	private final int parallaxsoftOBSTACLEMASK_6_SLOW_BONUS = 20;
	private final int parallaxsoftOBSTACLEMASK_7_JUMP_SLOW_BONUS = 10;
	
	private final int OBSTACLEMASK_MAX =
		parallaxsoftOBSTACLEMASK_0_NO_OBSTACLE +
		parallaxsoftOBSTACLEMASK_1_JUMP +
		parallaxsoftOBSTACLEMASK_2_SLOW +
		parallaxsoftOBSTACLEMASK_3_JUMP_SLOW +
		parallaxsoftOBSTACLEMASK_4_BONUS +
		parallaxsoftOBSTACLEMASK_5_JUMP_BONUS +
		parallaxsoftOBSTACLEMASK_6_SLOW_BONUS +
		parallaxsoftOBSTACLEMASK_7_JUMP_SLOW_BONUS;
	
	private Bitmap parallaxsoftobstacleSlowImg = null;
	private Bitmap parallaxsoftobstacleBonusImg = null;
	
	private boolean slowDown;
	Rect blockRect;
	private int parallaxsoftBlockCounter;
	private parallaxsoftOpenGLRenderer renderer;
	private Random randomGenerator;
	private boolean parallaxsoftlastBlockWasSmall = false;
	private int parallaxsoftminBlockWidth = 0;


	private parallaxsoftRHDrawable parallaxsoftmWaves = null;
	
	public parallaxsoftLevel(Context context, parallaxsoftOpenGLRenderer glrenderer, int _width, int _heigth) {
		if(parallaxsoftSettings.RHDEBUG)
			Log.d("debug", "in Level constructor");
		
		width = _width;
		height = _heigth;
		parallaxsoftlevelPosition = 0;
		parallaxsoftdeltaLevelPosition = 0;
		parallaxsoftbaseSpeedStart = parallaxsoftUtil.getPercentOfScreenWidth(0.095f);
		parallaxsoftbaseSpeed = parallaxsoftbaseSpeedStart;
		parallaxsoftbaseSpeedMaxStart = parallaxsoftUtil.getPercentOfScreenWidth(0.2f);
		parallaxsoftbaseSpeedMax = parallaxsoftbaseSpeedMaxStart;
		parallaxsoftbaseSpeedAcceleration = parallaxsoftbaseSpeed*0.025f;
		parallaxsoftextraSpeedStart = parallaxsoftUtil.getPercentOfScreenWidth(0.025f);
		parallaxsoftextraSpeed = parallaxsoftextraSpeedStart;
		parallaxsoftextraSpeedMaxStart = parallaxsoftUtil.getPercentOfScreenWidth(0.5f);
		parallaxsoftextraSpeedMax = parallaxsoftextraSpeedMaxStart;
		parallaxsoftextraSpeedAcceleration = parallaxsoftextraSpeed * 0.005f;
		timeUntilNextSpeedIncreaseMillis = parallaxsoftSettings.TimeOfFirstSpeedIncrease;
		parallaxsoftobstacleJumperWidth = parallaxsoftUtil.getPercentOfScreenWidth(4.875f);
		parallaxsoftobstacleJumperHeight = parallaxsoftUtil.getPercentOfScreenHeight(14.25f);
		parallaxsoftobstacleSlowerWidth = parallaxsoftUtil.getPercentOfScreenWidth(6);
		parallaxsoftobstacleSlowerHeight= parallaxsoftUtil.getPercentOfScreenHeight(6);
		parallaxsoftobstacleBonusWidth = parallaxsoftUtil.getPercentOfScreenWidth(5);
		parallaxsoftobstacleBonusHeight = parallaxsoftobstacleBonusWidth* parallaxsoftUtil.parallaxsoftfetmWidthHeightRatio;
		obstacleBonusDistanceToBlock = parallaxsoftUtil.getPercentOfScreenHeight(12);
		
		
		if(parallaxsoftSettings.RHDEBUG){
			Log.d("debug", "parallaxsoftobstacleJumperWidth" + parallaxsoftobstacleJumperWidth);
			Log.d("debug", "parallaxsoftobstacleJumperHeight" + parallaxsoftobstacleJumperHeight);
			Log.d("debug", "parallaxsoftobstacleSlowerWidth" + parallaxsoftobstacleSlowerWidth);
			Log.d("debug", "parallaxsoftobstacleSlowerHeight" + parallaxsoftobstacleSlowerHeight);
			Log.d("debug", "parallaxsoftobstacleBonusWidth" + parallaxsoftobstacleBonusWidth);
			Log.d("debug", "parallaxsoftobstacleBonusHeight" + parallaxsoftobstacleBonusHeight);
			Log.d("debug", "obstacleBonusDistanceToBlock" + obstacleBonusDistanceToBlock);
		}
		renderer = glrenderer;

		randomGenerator = new Random();
		parallaxsoftBlockData = new parallaxsoftBlock[parallaxsoftmaxBlocks];
		parallaxsofleftBlockIndex = 0;
		parallaxsofrightBlockIndex = parallaxsoftmaxBlocks;
		parallaxsoftobstacleDataSlower = new parallaxsoftObstacle[parallaxsoftmaxObstaclesSlower];
		parallaxsoftleftSlowerIndex = 0;
		parallaxsoftrightSlowerIndex = parallaxsoftmaxObstaclesSlower;
		parallaxsoftobstacleDataJumper = new parallaxsoftObstacleJump[parallaxsoftmaxObstaclesJumper];
		parallaxsoftleftJumperIndex = 0;
		parallaxsoftrightJumperIndex = parallaxsoftmaxObstaclesJumper;
		parallaxsoftobstacleDataBonus = new parallaxsoftObstacleBonus[parallaxsoftmaxObstaclesBonus];
		parallaxsoftleftBonusIndex = 0;
		parallaxsoftrightBonusIndex = parallaxsoftmaxObstaclesBonus;
		parallaxsoftobstacleSlowImg = parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_obstacle_slow.png");
		parallaxsoftobstacleBonusImg = parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("sdfsdfgh.png");
		
		parallaxsoftBlock.parallaxsoftfsetTextureLeft(parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_bl6t5reock_left.png"));
		parallaxsoftBlock.parallaxsoftfsetTextureMiddle(parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_block_middle.png"));
		parallaxsoftBlock.parallaxsoftfsetTextureRight(parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_bltyuiock_right.png"));
		slowDown = false;
		parallaxsoftinitializeBlocks(true);
		parallaxsoftinitializeObstacles(true);
		parallaxsoftmWaves = new parallaxsoftRHDrawable(0, 0, 0.7f, width*4, height);
		parallaxsoftmWaves.parallaxsoftlloadBitmap(parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_waves.png")
				, GL10.GL_REPEAT, GL10.GL_CLAMP_TO_EDGE);
		renderer.addMesh(parallaxsoftmWaves);
		
	}
	
	public void cleanup() {
		if (parallaxsoftobstacleSlowImg != null) parallaxsoftobstacleSlowImg.recycle();
		if (parallaxsoftobstacleBonusImg != null) parallaxsoftobstacleBonusImg.recycle();
		parallaxsoftBlock.cleanup();
	}
	
	public void update() {
		
		synchronized (parallaxsoftBlockData) {
			if (0 > parallaxsoftBlockData[parallaxsofleftBlockIndex].parallaxsoftfetBlockRect.right) {
				parallaxsoftappendBlockToEnd(-1);
				if(parallaxsoftBlockCounter == 5)
					appendObstaclesToEnd(false, false, true);
				if(parallaxsoftBlockCounter == 7)
					appendObstaclesToEnd(true, false, false);
				if(parallaxsoftBlockCounter == 9)
					appendObstaclesToEnd(false, true, false);
				if (parallaxsoftBlockCounter > 15)
					parallaxsoftdecideIfAndWhatObstaclesSpawn();
			}
			
			parallaxsoftbaseSpeedAcceleration = parallaxsoftbaseSpeed * 0.005f;
			parallaxsoftextraSpeedAcceleration = parallaxsoftextraSpeed * 0.002f;
			if(parallaxsoftUtil.getTimeSinceRoundStartMillis() > timeUntilNextSpeedIncreaseMillis){
				timeUntilNextSpeedIncreaseMillis += parallaxsoftSettings.timeToFurtherSpeedIncreaseMillis;
				parallaxsoftbaseSpeedMax += parallaxsoftUtil.getPercentOfScreenWidth(0.075f);
			}
			if(parallaxsoftbaseSpeed < parallaxsoftbaseSpeedMax)
				parallaxsoftbaseSpeed+=parallaxsoftbaseSpeedAcceleration;
			
			if(parallaxsoftextraSpeed < parallaxsoftextraSpeedMax)
				parallaxsoftextraSpeed+=parallaxsoftextraSpeedAcceleration;
			if(slowDown){
				parallaxsoftbaseSpeed=parallaxsoftbaseSpeedStart;
				parallaxsoftextraSpeed /= 2;
				slowDown=false;
			}

			parallaxsoftdeltaLevelPosition = parallaxsoftbaseSpeed + parallaxsoftextraSpeed;
			parallaxsoftlevelPosition += parallaxsoftdeltaLevelPosition;
			
			
			parallaxsoftmWaves.x -= parallaxsoftdeltaLevelPosition+2;
			if (parallaxsoftmWaves.x < -parallaxsoftmWaves.width/2)
				parallaxsoftmWaves.x = 0;
			for (int i = 0; i < parallaxsoftmaxBlocks; i++)
			{
				parallaxsoftBlockData[i].x -= parallaxsoftdeltaLevelPosition;
				parallaxsoftBlockData[i].parallaxsoftfupdateRect();
			}
			
			for (int i = 0; i < parallaxsoftmaxObstaclesJumper; i++)
			{
				parallaxsoftobstacleDataJumper[i].x -= parallaxsoftdeltaLevelPosition;
				parallaxsoftobstacleDataJumper[i].parallaxsoftjumpParallaxsoftSprite.x -= parallaxsoftdeltaLevelPosition;
				parallaxsoftobstacleDataJumper[i].parallaxsoftjumpParallaxsoftSprite.parallaxsofttryToSetNextFrame();
			}
			
			for (int i = 0; i < parallaxsoftmaxObstaclesSlower; i++)
			{
				parallaxsoftobstacleDataSlower[i].x -= parallaxsoftdeltaLevelPosition;
			}
			
			for (int i = 0; i < parallaxsoftmaxObstaclesBonus; i++)
			{
				parallaxsoftobstacleDataBonus[i].centerX -= parallaxsoftdeltaLevelPosition;
				parallaxsoftobstacleDataBonus[i].parallaxsoftupdateObstacleCircleMovement();
				parallaxsoftobstacleDataBonus[i].parallaxsoftBonusScoreEffect.updateparallaxsoftBonusScoreEffect(parallaxsoftdeltaLevelPosition);
			}
		}	
	}
	
	private void parallaxsoftinitializeBlocks(Boolean firstTime) {
		if(parallaxsoftSettings.RHDEBUG)
			Log.d("debug", "in parallaxsoftinitializeBlocks");
		
		if (firstTime)
			parallaxsoftBlockData[0] = new parallaxsoftBlock();
		parallaxsoftBlockData[0].x = 0;
		parallaxsoftBlockData[0].parallaxsoftfsetWidth(width);
		parallaxsoftBlockData[0].parallaxsoftfsetHeight(parallaxsoftSettings.FirstBlockHeight);
		parallaxsoftBlockData[0].parallaxsoftfupdateRect();
		
		if(parallaxsoftSettings.RHDEBUG)
			Log.d("debug", "after blockdata 0");

		if(firstTime)
			renderer.addMesh(parallaxsoftBlockData[0]);
		
		parallaxsofleftBlockIndex = 1;
		parallaxsofrightBlockIndex = 0;
		
		if(parallaxsoftSettings.RHDEBUG)
			Log.d("debug", "before for");
		
		for(int i = 1; i < parallaxsoftmaxBlocks; i++)
		{
			if (firstTime){
				parallaxsoftBlockData[i] = new parallaxsoftBlock();
				renderer.addMesh(parallaxsoftBlockData[i]);
			}
			parallaxsoftappendBlockToEnd(i);
			parallaxsoftBlockData[i].parallaxsoftfupdateRect();
		}
		if(parallaxsoftSettings.RHDEBUG)
			Log.d("debug", "left parallaxsoftinitializeBlocks");
	}
	
	private void parallaxsoftappendBlockToEnd(int BlockNumber)
	{
		if (parallaxsoftminBlockWidth == 0) {
			parallaxsoftminBlockWidth =
					parallaxsoftBlock.parallaxsoftfgetTextureLeftWidth() +
					parallaxsoftBlock.parallaxsoftfgetTextureRightWidth() +
					parallaxsoftBlock.parallaxsoftfgetTextureMiddleWidth() * 2;
		}
		float newHeight=0;
		float oldHeight;
		float newWidth=0;
		float distance;
		float newLeft;
		boolean thisBlockIsSmall = false;
		oldHeight = parallaxsoftBlockData[parallaxsofrightBlockIndex].parallaxsoftfetBlockRect.top;
		if(BlockNumber==-1){
			if (oldHeight > height/2)
				newHeight = (int)(Math.random()*height/3*2 + height/8);
			else
				newHeight = (int)(Math.random()*height/4 + height/8);
			if(parallaxsoftUtil.getTimeSinceRoundStartMillis() > parallaxsoftSettings.timeUntilLongBlocksStopMillis){
				if (parallaxsoftlastBlockWasSmall) parallaxsoftlastBlockWasSmall = false;
				else if (50 - parallaxsoftBlockCounter <= 0) thisBlockIsSmall = true;
				else thisBlockIsSmall = (randomGenerator.nextInt(50 - parallaxsoftBlockCounter) <= 5);
				
				if (thisBlockIsSmall) {
					newWidth = parallaxsoftminBlockWidth;
					parallaxsoftlastBlockWasSmall = true;
				}
				else {
					newWidth = (int)(Math.random()*width/3+width/3);
				}
			}else{
				newWidth = (int)(Math.random()*width/3+width*0.70f);
			}
			
			
			newWidth -= (newWidth - parallaxsoftBlock.parallaxsoftfgetTextureLeftWidth() - parallaxsoftBlock.parallaxsoftfgetTextureRightWidth()) % (parallaxsoftBlock.parallaxsoftfgetTextureMiddleWidth());
			
			distance = (int)(Math.random()*width/16+width/12); 
			
			if(distance <= parallaxsoftPlayer.width)
				distance = parallaxsoftPlayer.width+10;
		}else{	
			distance = parallaxsoftPlayer.width+5;
			switch (BlockNumber){
				case 1:
					newHeight=oldHeight; 
					newWidth= parallaxsoftUtil.getPercentOfScreenWidth(80);
					break;
				case 2:
					newHeight=oldHeight+ parallaxsoftUtil.getPercentOfScreenHeight(8);
					newWidth= parallaxsoftUtil.getPercentOfScreenWidth(80);
					break;
				case 3:
					newHeight=oldHeight;
					newWidth= parallaxsoftUtil.getPercentOfScreenWidth(80);
					break;
				case 4:
					newHeight=oldHeight+ parallaxsoftUtil.getPercentOfScreenHeight(9);
					newWidth= parallaxsoftUtil.getPercentOfScreenWidth(80);
					break;
			}
		}	

		newLeft = parallaxsoftBlockData[parallaxsofrightBlockIndex].parallaxsoftfetBlockRect.right + distance;
		parallaxsoftBlockData[parallaxsofleftBlockIndex].parallaxsoftfsetHeight(newHeight);
		parallaxsoftBlockData[parallaxsofleftBlockIndex].parallaxsoftfsetWidth(newWidth);
		parallaxsoftBlockData[parallaxsofleftBlockIndex].x = newLeft;
		parallaxsofleftBlockIndex++;
	    if (parallaxsofleftBlockIndex == parallaxsoftmaxBlocks)
	    	parallaxsofleftBlockIndex = 0;
	    
	    parallaxsofrightBlockIndex++;
	    if (parallaxsofrightBlockIndex== parallaxsoftmaxBlocks)
	    	parallaxsofrightBlockIndex = 0;
		    
		parallaxsoftBlockCounter++;
	}
	
	private void parallaxsoftinitializeObstacles(Boolean firstTime)
	{
		for(int i = 0; i < parallaxsoftmaxObstaclesJumper; i++)
		{
			if (firstTime)
			{
				parallaxsoftobstacleDataJumper[i] = new parallaxsoftObstacleJump(-1000, 0, 0.9f, parallaxsoftobstacleJumperWidth, parallaxsoftobstacleJumperHeight, 'j', 60, 4);
			}
			parallaxsoftobstacleDataJumper[i].x = parallaxsoftobstacleDataJumper[i].parallaxsoftjumpParallaxsoftSprite.x = -1000;
			parallaxsoftobstacleDataJumper[i].parallaxsoftdidTrigger = false;
		}
		for(int i = 0; i < parallaxsoftmaxObstaclesSlower; i++)
		{
			if (firstTime)
			{
				parallaxsoftobstacleDataSlower[i] = new parallaxsoftObstacle(-1000, 0, 0.9f, parallaxsoftobstacleSlowerWidth, parallaxsoftobstacleSlowerHeight, 's');
				renderer.addMesh(parallaxsoftobstacleDataSlower[i]);
				parallaxsoftobstacleDataSlower[i].parallaxsoftlloadBitmap(parallaxsoftobstacleSlowImg);
			}
			
			parallaxsoftobstacleDataSlower[i].x = -1000;
			parallaxsoftobstacleDataSlower[i].parallaxsoftdidTrigger = false;
		}
		for(int i = 0; i < parallaxsoftmaxObstaclesBonus; i++)
		{
			if (firstTime)
			{
				parallaxsoftobstacleDataBonus[i] = new parallaxsoftObstacleBonus(-1000, 0, 0.9f, parallaxsoftobstacleBonusWidth, parallaxsoftobstacleBonusHeight, 'b');
				renderer.addMesh(parallaxsoftobstacleDataBonus[i]);
				parallaxsoftobstacleDataBonus[i].parallaxsoftlloadBitmap(parallaxsoftobstacleBonusImg);
			}
			parallaxsoftobstacleDataBonus[i].centerX = -1000;
			parallaxsoftobstacleDataBonus[i].parallaxsoftBonusScoreEffect.parallaxsofteffectX=-1000;
			parallaxsoftobstacleDataBonus[i].parallaxsoftdidTrigger = false;
		}
	}
	
	private void parallaxsoftdecideIfAndWhatObstaclesSpawn()
	{
		int obstacleValue =randomGenerator.nextInt(OBSTACLEMASK_MAX);
		
		if (obstacleValue < parallaxsoftOBSTACLEMASK_0_NO_OBSTACLE)
		{
			return;
		}
		obstacleValue -= parallaxsoftOBSTACLEMASK_0_NO_OBSTACLE;
		
		if (obstacleValue < parallaxsoftOBSTACLEMASK_1_JUMP)
		{
			appendObstaclesToEnd(true, false, false);
			return;
		}
		obstacleValue -= parallaxsoftOBSTACLEMASK_1_JUMP;
		
		if (obstacleValue < parallaxsoftOBSTACLEMASK_2_SLOW)
		{
			appendObstaclesToEnd(false, true, false);
			return;
		}
		obstacleValue -= parallaxsoftOBSTACLEMASK_2_SLOW;
		
		if (obstacleValue < parallaxsoftOBSTACLEMASK_3_JUMP_SLOW)
		{
			appendObstaclesToEnd(true, true, false);
			return;
		}
		obstacleValue -= parallaxsoftOBSTACLEMASK_3_JUMP_SLOW;
		
		if (obstacleValue < parallaxsoftOBSTACLEMASK_4_BONUS)
		{
			appendObstaclesToEnd(false, false, true);
			return;
		}
		obstacleValue -= parallaxsoftOBSTACLEMASK_4_BONUS;
		
		if (obstacleValue < parallaxsoftOBSTACLEMASK_5_JUMP_BONUS)
		{
			appendObstaclesToEnd(true, false, true);
			return;
		}
		obstacleValue -= parallaxsoftOBSTACLEMASK_5_JUMP_BONUS;
		
		if (obstacleValue < parallaxsoftOBSTACLEMASK_6_SLOW_BONUS)
		{
			appendObstaclesToEnd(false, true, true);
			return;
		}
		obstacleValue -= parallaxsoftOBSTACLEMASK_6_SLOW_BONUS;
		
		if (obstacleValue < parallaxsoftOBSTACLEMASK_7_JUMP_SLOW_BONUS)
		{
			appendObstaclesToEnd(true, true, true);
			return;
		}
		obstacleValue -= parallaxsoftOBSTACLEMASK_7_JUMP_SLOW_BONUS;
		
	}
	
	private void appendObstaclesToEnd(Boolean spawnJumper, Boolean spawnSlower, Boolean spawnBonus)
	{
		if (spawnSlower)
		{
			float obstacleLeft;
		    long fraction = (long)(parallaxsoftBlockData[parallaxsofrightBlockIndex].parallaxsoftfetmWidth * 0.33 * randomGenerator.nextDouble());
		    parallaxsoftObstacle newSlowParallaxsoftObstacle = parallaxsoftobstacleDataSlower[parallaxsoftleftSlowerIndex];
		    newSlowParallaxsoftObstacle.parallaxsoftdidTrigger = false;
		    obstacleLeft =  
		    	parallaxsoftBlockData[parallaxsofrightBlockIndex].x + parallaxsoftBlockData[parallaxsofrightBlockIndex].parallaxsoftfetmWidth
    			- newSlowParallaxsoftObstacle.width - fraction;
		    newSlowParallaxsoftObstacle.x = obstacleLeft;
		    newSlowParallaxsoftObstacle.y = parallaxsoftBlockData[parallaxsofrightBlockIndex].parallaxsoftfetmHeight;
		    newSlowParallaxsoftObstacle.setparallaxsoftObstacleRect(
		    		obstacleLeft,
		    		obstacleLeft+ newSlowParallaxsoftObstacle.width,
		    		parallaxsoftBlockData[parallaxsofrightBlockIndex].parallaxsoftfetmHeight,
		    		parallaxsoftBlockData[parallaxsofrightBlockIndex].parallaxsoftfetmHeight- newSlowParallaxsoftObstacle.height);
		    parallaxsoftleftSlowerIndex++;
		    if (parallaxsoftleftSlowerIndex == parallaxsoftmaxObstaclesSlower)
		    	parallaxsoftleftSlowerIndex = 0;
		    parallaxsoftrightSlowerIndex++;
		    if (parallaxsoftrightSlowerIndex == parallaxsoftmaxObstaclesSlower)
		    	parallaxsoftrightSlowerIndex = 0;
		    
		}
		
		if (spawnJumper)
		{
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "in spawnJumper");
			float obstacleLeft;
			parallaxsoftObstacleJump newJumpObstacle = parallaxsoftobstacleDataJumper[parallaxsoftleftJumperIndex];
			newJumpObstacle.parallaxsoftdidTrigger = false;
			long fraction = (long)(parallaxsoftBlockData[parallaxsofrightBlockIndex].parallaxsoftfetmWidth * 0.33 * randomGenerator.nextDouble());
			obstacleLeft =  (parallaxsoftBlockData[parallaxsofrightBlockIndex].x + newJumpObstacle.width + fraction);
			newJumpObstacle.x = obstacleLeft;
			newJumpObstacle.parallaxsoftjumpParallaxsoftSprite.x = obstacleLeft;
		    newJumpObstacle.y = parallaxsoftBlockData[parallaxsofrightBlockIndex].parallaxsoftfetmHeight;
		    newJumpObstacle.parallaxsoftjumpParallaxsoftSprite.y = parallaxsoftBlockData[parallaxsofrightBlockIndex].parallaxsoftfetmHeight;
		    newJumpObstacle.setparallaxsoftObstacleRect(
		    		obstacleLeft,
		    		obstacleLeft+newJumpObstacle.width,
		    		parallaxsoftBlockData[parallaxsofrightBlockIndex].parallaxsoftfetmHeight,
		    		parallaxsoftBlockData[parallaxsofrightBlockIndex].parallaxsoftfetmHeight-newJumpObstacle.height);
		    
		    if(parallaxsoftSettings.RHDEBUG)
		    	Log.d("debug", "x: " + newJumpObstacle.x +
		    		" / y: " + newJumpObstacle.y +
		    		" / z: " + newJumpObstacle.z);
		    
		    
		    parallaxsoftleftJumperIndex++;
		    if (parallaxsoftleftJumperIndex == parallaxsoftmaxObstaclesJumper)
		    	parallaxsoftleftJumperIndex = 0;
		    
		    parallaxsoftrightJumperIndex++;
		    if (parallaxsoftrightJumperIndex == parallaxsoftmaxObstaclesJumper)
		    	parallaxsoftrightJumperIndex = 0;
		    
		}
		
		if (spawnBonus)
		{
			if(parallaxsoftSettings.RHDEBUG)
				Log.d("debug", "in spawnBonus");
			
			float range = parallaxsoftBlockData[parallaxsofrightBlockIndex].parallaxsoftfetmWidth;
			
			int bonusLeft;
		    double fraction = range * randomGenerator.nextDouble();
			parallaxsoftObstacleBonus newBonus = parallaxsoftobstacleDataBonus[parallaxsoftleftBonusIndex];
			newBonus.parallaxsoftdidTrigger = false;
			
		    newBonus.z=0;
			
		    bonusLeft = (int)(parallaxsoftBlockData[parallaxsofrightBlockIndex].x + fraction );
		    newBonus.x = newBonus.centerX = bonusLeft;
		    newBonus.y = newBonus.centerY = parallaxsoftBlockData[parallaxsofrightBlockIndex].parallaxsoftfetmHeight+obstacleBonusDistanceToBlock+randomGenerator.nextInt(75);
		    
		    newBonus.setparallaxsoftObstacleRect(bonusLeft,
		    		bonusLeft+newBonus.width,
		    		parallaxsoftBlockData[parallaxsofrightBlockIndex].parallaxsoftfetmHeight,
		    		parallaxsoftBlockData[parallaxsofrightBlockIndex].parallaxsoftfetmHeight-newBonus.height);
		    
		    if(parallaxsoftSettings.RHDEBUG)
		    	Log.d("debug", "x: " + newBonus.x +
		    		" / y: " + newBonus.y +
		    		" / z: " + newBonus.z);
		    
		    parallaxsoftleftBonusIndex++;
		    if (parallaxsoftleftBonusIndex == parallaxsoftmaxObstaclesBonus)
		    	parallaxsoftleftBonusIndex = 0;
		    
		    parallaxsoftrightBonusIndex++;
		    if (parallaxsoftrightBonusIndex== parallaxsoftmaxObstaclesBonus)
		    	parallaxsoftrightBonusIndex = 0;
		    
		}
	}
	
	public int getDistanceScore()
	{
		return (int)(parallaxsoftlevelPosition * 800 / width / 10);
	}
	
	public void lowerSpeed() {
		slowDown = true;
	}
	public float parallaxsoftgetLevelPosition(){
		return parallaxsoftlevelPosition;
	}
	public void reset() {
		synchronized (parallaxsoftBlockData) {
			parallaxsoftlevelPosition = 0;
			parallaxsoftinitializeBlocks(false);
			parallaxsoftinitializeObstacles(false);
			timeUntilNextSpeedIncreaseMillis = parallaxsoftSettings.TimeOfFirstSpeedIncrease;
			parallaxsoftbaseSpeed = parallaxsoftbaseSpeedStart;
			parallaxsoftextraSpeed = parallaxsoftextraSpeedStart;
			parallaxsoftbaseSpeedMax = parallaxsoftbaseSpeedMaxStart;
			parallaxsoftextraSpeedMax = parallaxsoftextraSpeedMaxStart;
			parallaxsoftBlockCounter=0;
		}
	}
}

