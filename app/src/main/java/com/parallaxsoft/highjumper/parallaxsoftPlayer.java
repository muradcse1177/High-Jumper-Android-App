package com.parallaxsoft.highjumper;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

public class parallaxsoftPlayer {
	private static float parallaxsoftMAX_JUMP_HEIGHT = parallaxsoftUtil.getPercentOfScreenHeight(15);
	private float parallaxsoftlastPosY;
	static public float width;
	static public float height;
	public float x;
	public float y;
	private boolean jumping = false;
	private boolean parallaxsoftjumpingsoundplayed = true;
	private boolean onGround = false;
	private boolean parallaxsoftreachedPeak = false;
	private boolean parallaxsoftslowSoundplayed = false;
	private float parallaxsoftjumpStartY;
	
	private float parallaxsoftvelocity = 0;
	private float parallaxsoftvelocityMax = 0;
	private float parallaxsoftvelocityDownfallSpeed = 0;
	
	private Rect parallaxsoftplayerRect;
	private Rect parallaxsoftObstacleRect;
	private float parallaxspeedoffsetX = 0;
	private float parallaxspeedoffsetXStart;
	private float parallaxspeedoffsetXMax;
	private float parallaxspeedoffsetXStep;
	private Bitmap playerSpriteImg = null; 
	public parallaxsoftSprite playerParallaxsoftSprite;
	private boolean fingerOnScreen = false;
	private float bonusVelocity = 0;
	private float bonusVelocityDownfallSpeed = 0;
	
	public int bonusItems = 0;
	private int bonusScorePerItem = 200;
	

	public parallaxsoftPlayer(Context context, parallaxsoftOpenGLRenderer glrenderer, int ScreenHeight) {
		x = parallaxsoftUtil.getPercentOfScreenWidth(9);
		y = parallaxsoftSettings.FirstBlockHeight+ parallaxsoftUtil.getPercentOfScreenHeight(4);
		width = parallaxsoftUtil.getPercentOfScreenWidth(9);
		height = width* parallaxsoftUtil.parallaxsoftfetmWidthHeightRatio;
		parallaxsoftvelocityMax = parallaxsoftUtil.getPercentOfScreenHeight(3);
		parallaxsoftvelocityDownfallSpeed = parallaxsoftvelocityMax/30.0f;
		bonusVelocityDownfallSpeed = parallaxsoftvelocityDownfallSpeed / 6.0f;
		parallaxspeedoffsetXStart = x;
		parallaxspeedoffsetXMax = parallaxsoftUtil.getPercentOfScreenWidth(7);
		parallaxspeedoffsetXStep = parallaxsoftUtil.getPercentOfScreenWidth(0.002f);
		playerSpriteImg = parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_character_spritesheet.png");
		playerParallaxsoftSprite = new parallaxsoftSprite(x, y, 0.5f, width, height, 25, 8);
		playerParallaxsoftSprite.parallaxsoftlloadBitmap(playerSpriteImg);
		glrenderer.addMesh(playerParallaxsoftSprite);
		parallaxsoftplayerRect = new Rect();
		parallaxsoftplayerRect.left =(int)x;
		parallaxsoftplayerRect.top =(int)(y+height);
		parallaxsoftplayerRect.right =(int)(x+width);
		parallaxsoftplayerRect.bottom =(int)y;
		
		parallaxsoftObstacleRect = new Rect();
	}
	
	public void cleanup() {
		if (playerSpriteImg != null) playerSpriteImg.recycle();
	}
	
	public void setJump(boolean jump) {
		fingerOnScreen = jump;
		if(!jump)
		{
			parallaxsoftreachedPeak = true;
			bonusVelocity = 0.0f;
		}
		
		if(parallaxsoftreachedPeak || !onGround) return;
		
		parallaxsoftjumpStartY = y;
		jumping = true;
		if(jump)
			parallaxsoftjumpingsoundplayed = false;
	}
	
	public boolean update() {
		playerParallaxsoftSprite.updatePosition(x, y);
		playerParallaxsoftSprite.parallaxsofttryToSetNextFrame();
		
		if(parallaxsoftjumpingsoundplayed==false){
			parallaxsoftSoundManager.parallaxsoftplaySound(3, 1);
			parallaxsoftjumpingsoundplayed = true;
		}
		
		if (jumping && !parallaxsoftreachedPeak) {
			parallaxsoftvelocity += 1.5f * (parallaxsoftMAX_JUMP_HEIGHT - (y - parallaxsoftjumpStartY)) / 100.f;


			if(parallaxsoftSettings.RHDEBUG){
				Log.d("debug", "y: " + (y));
				Log.d("debug", "y + height: " + (y + height));
			}
			if(y - parallaxsoftjumpStartY >= parallaxsoftMAX_JUMP_HEIGHT)
			{
				parallaxsoftreachedPeak = true;
			}
		}
		else
		{
			parallaxsoftvelocity -= parallaxsoftvelocityDownfallSpeed;
		}
		
		
		if (parallaxsoftvelocity < -parallaxsoftvelocityMax)
			parallaxsoftvelocity = -parallaxsoftvelocityMax;
		else if (parallaxsoftvelocity > parallaxsoftvelocityMax)
			parallaxsoftvelocity = parallaxsoftvelocityMax;
		
		y += parallaxsoftvelocity + bonusVelocity;

		bonusVelocity-= bonusVelocityDownfallSpeed;
		if (bonusVelocity < 0)
			bonusVelocity = 0;
		
		parallaxsoftplayerRect.left =(int)x;
		parallaxsoftplayerRect.top =(int)(y+height);
		parallaxsoftplayerRect.right =(int)(x+width);
		parallaxsoftplayerRect.bottom =(int)y;
		
		onGround = false;
		
		for (int i = 0; i < parallaxsoftLevel.parallaxsoftmaxBlocks; i++)
		{
			if( parallaxsoftcheckIntersect(parallaxsoftplayerRect, parallaxsoftLevel.parallaxsoftBlockData[i].parallaxsoftfetBlockRect) )
			{
				if(parallaxsoftlastPosY >= parallaxsoftLevel.parallaxsoftBlockData[i].parallaxsoftfetmHeight && parallaxsoftvelocity <= 0)
				{
					y= parallaxsoftLevel.parallaxsoftBlockData[i].parallaxsoftfetmHeight;
					parallaxsoftvelocity = 0;
					parallaxsoftreachedPeak = false;
					jumping = false;
					onGround = true;
					bonusVelocity = 0.0f;
				}
				else{
					return false;
				}
			}
		}
		parallaxsoftlastPosY = y;
		
		if(parallaxspeedoffsetX<parallaxspeedoffsetXMax )
			parallaxspeedoffsetX += parallaxspeedoffsetXStep;
		x=parallaxspeedoffsetXStart+parallaxspeedoffsetX;
		if(y + height < 0){
			y = -height;
			return false;
		}
		return true;
	}	
	
	public boolean parallaxsoftcollidedWithObstacle(float parallaxsoftlevelPosition) {
		
		for(int i = 0; i < parallaxsoftLevel.parallaxsoftmaxObstaclesJumper; i++)
		{
			parallaxsoftObstacleRect.left =  (int) parallaxsoftLevel.parallaxsoftobstacleDataJumper[i].x;
			parallaxsoftObstacleRect.top = (int) parallaxsoftLevel.parallaxsoftobstacleDataJumper[i].y+(int) parallaxsoftLevel.parallaxsoftobstacleDataJumper[i].height;
			parallaxsoftObstacleRect.right = (int) parallaxsoftLevel.parallaxsoftobstacleDataJumper[i].x+(int) parallaxsoftLevel.parallaxsoftobstacleDataJumper[i].width;
			parallaxsoftObstacleRect.bottom = (int) parallaxsoftLevel.parallaxsoftobstacleDataJumper[i].y;
			
			if( parallaxsoftcheckIntersect(parallaxsoftplayerRect, parallaxsoftObstacleRect) && !parallaxsoftLevel.parallaxsoftobstacleDataJumper[i].parallaxsoftdidTrigger)
			{
				parallaxsoftLevel.parallaxsoftobstacleDataJumper[i].parallaxsoftdidTrigger=true;
				
				parallaxsoftSoundManager.parallaxsoftplaySound(6, 1);
				parallaxsoftvelocity = parallaxsoftUtil.getPercentOfScreenHeight(2.6f);//6; //katapultiert den player wie ein trampolin nach oben
				
				if (fingerOnScreen)
					bonusVelocity = parallaxsoftUtil.getPercentOfScreenHeight(1.5f);
			}
		}
		
		for(int i = 0; i < parallaxsoftLevel.parallaxsoftmaxObstaclesSlower; i++)
		{
			parallaxsoftObstacleRect.left =  (int) parallaxsoftLevel.parallaxsoftobstacleDataSlower[i].x;
			parallaxsoftObstacleRect.top = (int) parallaxsoftLevel.parallaxsoftobstacleDataSlower[i].y+(int) parallaxsoftLevel.parallaxsoftobstacleDataSlower[i].height;
			parallaxsoftObstacleRect.right = (int) parallaxsoftLevel.parallaxsoftobstacleDataSlower[i].x+(int) parallaxsoftLevel.parallaxsoftobstacleDataSlower[i].width;
			parallaxsoftObstacleRect.bottom = (int) parallaxsoftLevel.parallaxsoftobstacleDataSlower[i].y;

			if( parallaxsoftcheckIntersect(parallaxsoftplayerRect, parallaxsoftObstacleRect) && !parallaxsoftLevel.parallaxsoftobstacleDataSlower[i].parallaxsoftdidTrigger)
			{
				parallaxsoftLevel.parallaxsoftobstacleDataSlower[i].parallaxsoftdidTrigger=true;

				if(!parallaxsoftslowSoundplayed){
					parallaxsoftSoundManager.parallaxsoftplaySound(5, 1);
					parallaxsoftslowSoundplayed=true;
				}
				return true;
			}
		}
		
		for(int i = 0; i < parallaxsoftLevel.parallaxsoftmaxObstaclesBonus; i++)
		{
			parallaxsoftObstacleRect.left =  (int) parallaxsoftLevel.parallaxsoftobstacleDataBonus[i].x;
			parallaxsoftObstacleRect.top = (int) parallaxsoftLevel.parallaxsoftobstacleDataBonus[i].y+(int) parallaxsoftLevel.parallaxsoftobstacleDataBonus[i].height;
			parallaxsoftObstacleRect.right = (int) parallaxsoftLevel.parallaxsoftobstacleDataBonus[i].x+(int) parallaxsoftLevel.parallaxsoftobstacleDataBonus[i].width;
			parallaxsoftObstacleRect.bottom = (int) parallaxsoftLevel.parallaxsoftobstacleDataBonus[i].y;

			if( parallaxsoftcheckIntersect(parallaxsoftplayerRect, parallaxsoftObstacleRect) && !parallaxsoftLevel.parallaxsoftobstacleDataBonus[i].parallaxsoftdidTrigger)
			{
				parallaxsoftSoundManager.parallaxsoftplaySound(8, 1);
				parallaxsoftLevel.parallaxsoftobstacleDataBonus[i].parallaxsoftdidTrigger=true;
				parallaxsoftLevel.parallaxsoftobstacleDataBonus[i].parallaxsoftBonusScoreEffect.parallaxsofteffectX = parallaxsoftLevel.parallaxsoftobstacleDataBonus[i].x;
				parallaxsoftLevel.parallaxsoftobstacleDataBonus[i].parallaxsoftBonusScoreEffect.parallaxsofeffectY = parallaxsoftLevel.parallaxsoftobstacleDataBonus[i].y;
				parallaxsoftLevel.parallaxsoftobstacleDataBonus[i].parallaxsoftBonusScoreEffect.parallaxsoftfdoparallaxsoftBonusScoreEffect=true;
				bonusItems++;
				parallaxsoftLevel.parallaxsoftobstacleDataBonus[i].z= -1;
			}
		}
		parallaxsoftslowSoundplayed=false;
		return false;
	}

	public boolean parallaxsoftcheckIntersect(Rect parallaxsoftplayerRect, Rect blockRect) {
		if(parallaxsoftplayerRect.bottom >= blockRect.bottom && parallaxsoftplayerRect.bottom <= blockRect.top)
		{
			if(parallaxsoftplayerRect.right >= blockRect.left && parallaxsoftplayerRect.right <= blockRect.right )
				return true;
			else if(parallaxsoftplayerRect.left >= blockRect.left && parallaxsoftplayerRect.left <= blockRect.right )
				return true;
		}
		else if(parallaxsoftplayerRect.top >= blockRect.bottom && parallaxsoftplayerRect.top <= blockRect.top){
			if(parallaxsoftplayerRect.right >= blockRect.left && parallaxsoftplayerRect.right <= blockRect.right )
				return true;
			else if(parallaxsoftplayerRect.left >= blockRect.left && parallaxsoftplayerRect.left <= blockRect.right )
				return true;
		}
		if(blockRect.bottom >= parallaxsoftplayerRect.bottom && blockRect.bottom <= parallaxsoftplayerRect.top)
			if(blockRect.right >= parallaxsoftplayerRect.left && blockRect.right <= parallaxsoftplayerRect.right )
				return true;
		
		return false;
	}
	
	public void reset() {
		parallaxsoftvelocity = 0;
		x = 70;
		y = parallaxsoftSettings.FirstBlockHeight+20;
		
		parallaxspeedoffsetX = 0;
		bonusItems = 0;
	}
	
	public int getBonusScore()
	{
		return bonusItems * bonusScorePerItem;
	}

}
