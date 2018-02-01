package com.parallaxsoft.highjumper;
import android.graphics.Bitmap;

public class parallaxsoftObstacleJump extends parallaxsoftObstacle {
	public Bitmap parallaxsoftjumpSpriteImg;
	public parallaxsoftSprite parallaxsoftjumpParallaxsoftSprite = null;
	
	public parallaxsoftObstacleJump(float _x, float _y, float _z, float _width, float _height, char type, int _parallaxsoftFrameUpdateTime, int _numberOfFrames){
		super(_x, _y, _z, _width, _height, type);
		
		parallaxsoftjumpParallaxsoftSprite = new parallaxsoftSprite(_x, _y, _z, _width, _height, _parallaxsoftFrameUpdateTime, _numberOfFrames);
		parallaxsoftjumpParallaxsoftSprite.parallaxsoftlloadBitmap(parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_obstacle_jump_animated.png") );

		parallaxsoftUtil.getAppRenderer().addMesh(parallaxsoftjumpParallaxsoftSprite);
		
	}	
}
