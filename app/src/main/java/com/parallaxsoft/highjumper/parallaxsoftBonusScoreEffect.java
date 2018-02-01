package com.parallaxsoft.highjumper;
import android.util.Log;
public class parallaxsoftBonusScoreEffect extends parallaxsoftRHDrawable {
	
	public boolean parallaxsoftfdoparallaxsoftBonusScoreEffect;
	public boolean parallaxsoftfadeIn;
	public float parallaxsoftcurrentWidth;
	public float parallaxsoftcurrentHeight;
	public float parallaxsoftmaxWidth;
	public float parallaxsoftmaxHeight;
	public float parallaxsoftstepSizeFactor;
	public float parallaxsofteffectX;
	public float parallaxsofeffectY;
	
	public parallaxsoftBonusScoreEffect(float _x, float _y, float _z, float _parallaxsoftmaxWidth, float _parallaxsoftmaxHeight) {
		super(_x, _y, _z, 0, 0);
		
		parallaxsoftmaxWidth=_parallaxsoftmaxWidth;
		parallaxsoftmaxHeight=_parallaxsoftmaxHeight;
		parallaxsoftfdoparallaxsoftBonusScoreEffect = false;
		parallaxsoftfadeIn = true;
		parallaxsoftcurrentWidth=parallaxsoftcurrentHeight=0;
		parallaxsoftstepSizeFactor=25;
	}
	
	public void updateparallaxsoftBonusScoreEffect(float parallaxsoftdeltaLevelPosition){
		if(parallaxsoftfdoparallaxsoftBonusScoreEffect){
			if(parallaxsoftfadeIn){
				parallaxsoftcurrentWidth+=parallaxsoftmaxWidth/parallaxsoftstepSizeFactor;
				parallaxsoftcurrentHeight+=parallaxsoftmaxHeight/parallaxsoftstepSizeFactor;
			}
			else{
				parallaxsoftcurrentWidth-=parallaxsoftmaxWidth/parallaxsoftstepSizeFactor/2;
				parallaxsoftcurrentHeight-=parallaxsoftmaxHeight/parallaxsoftstepSizeFactor/2;
			}
			
			if(parallaxsoftcurrentWidth>=parallaxsoftmaxWidth || parallaxsoftcurrentHeight>=parallaxsoftmaxHeight)
				parallaxsoftfadeIn=false;
			if(parallaxsoftcurrentWidth<=0 || parallaxsoftcurrentHeight<=0){
				parallaxsoftfadeIn=true;
				parallaxsoftfdoparallaxsoftBonusScoreEffect=false;
				parallaxsofteffectX=parallaxsofeffectY=-1000;
				if(parallaxsoftcurrentWidth<0)
					parallaxsoftcurrentWidth=0;
				if(parallaxsoftcurrentHeight<0)
					parallaxsoftcurrentHeight=0;
			}
			if (parallaxsoftSettings.RHDEBUG) {
				Log.d("debug", "parallaxsoftcurrentWidth: " + parallaxsoftcurrentWidth);
				Log.d("debug", "parallaxsoftcurrentHeight: " + parallaxsoftcurrentHeight);
			}
			x=parallaxsofteffectX-parallaxsoftcurrentWidth/2-parallaxsoftdeltaLevelPosition;
			y=parallaxsofeffectY-parallaxsoftcurrentHeight/2;
			parallaxsoftfsetWidth((int)parallaxsoftcurrentWidth);
			parallaxsoftfsetHeight((int)parallaxsoftcurrentHeight);
		}
	}

	

}
