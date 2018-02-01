package com.parallaxsoft.highjumper;
import android.graphics.Bitmap;

public class parallaxsoftObstacleBonus extends parallaxsoftObstacle {
	public char parallaxsoftObstacleType;
	public boolean parallaxsoftdidTrigger;
	private int radX;
	private int radY;
	public float centerX;
	public float centerY;
	private float angle;
	private float orbitSpeed;
	public parallaxsoftBonusScoreEffect parallaxsoftBonusScoreEffect;
	public Bitmap bonusScoreEffectImg;
	
	public parallaxsoftObstacleBonus(float _x, float _y, float _z, float _width, float _height, char type){
		super(_x, _y, _z, _width, _height, type);
		
		x=_x;
		y=_y;
		z=_z;
		
		radX=radY=(int)(_width*2);
		angle=0;
		orbitSpeed=_width/1000;
		centerX=x;
		centerY=y;
		bonusScoreEffectImg = parallaxsoftUtil.parallaxsoftlloadBitmapFromAssets("game_bonusscore.png");
		float bonusScoreEffectImgWidthHeightFector = bonusScoreEffectImg.getWidth()/bonusScoreEffectImg.getHeight();
		float bonusScoreEffectWidth = parallaxsoftUtil.getPercentOfScreenWidth(20);
		float bonusScoreEffectHeight = bonusScoreEffectWidth/bonusScoreEffectImgWidthHeightFector;
		parallaxsoftBonusScoreEffect = new parallaxsoftBonusScoreEffect(x-bonusScoreEffectWidth/2, y-bonusScoreEffectHeight/2, 0.85f, bonusScoreEffectWidth, bonusScoreEffectHeight);
		parallaxsoftBonusScoreEffect.parallaxsoftlloadBitmap(bonusScoreEffectImg);
		parallaxsoftUtil.getAppRenderer().addMesh(parallaxsoftBonusScoreEffect);
		
		
	}
	

	public void parallaxsoftupdateObstacleCircleMovement(){
		x = centerX+(float)Math.cos(angle)*radX;
		y = centerY+(float)Math.sin(angle)*radY;
		angle += orbitSpeed;
	}	
}
