package com.parallaxsoft.highjumper;
import javax.microedition.khronos.opengles.GL10;
import android.graphics.Bitmap;

public class ParalaxBackground extends parallaxsoftGroup {
	private parallaxsoftRHDrawable parallaxsoftbackgroundLayer1;
	private parallaxsoftRHDrawable parallaxsoftbackgroundLayer2;
	private parallaxsoftRHDrawable parallaxsoftbackgroundLayer3;
	private Bitmap parallaxsoftBGImg1 = null;
	private Bitmap parallaxsoftBGImg2 = null;
	private Bitmap parallaxsoftBGImg3 = null;
	private int parallaxsoftfetmWidth;
	private int parallaxsoftfetmHeight;



	public ParalaxBackground(int width, int heigth) {
		parallaxsoftfetmWidth = width;
		parallaxsoftfetmHeight = heigth;
	}
	
	public void cleanup() {
		if (parallaxsoftBGImg1 != null) parallaxsoftBGImg1.recycle();
		if (parallaxsoftBGImg2 != null) parallaxsoftBGImg2.recycle();
		if (parallaxsoftBGImg3 != null) parallaxsoftBGImg3.recycle();
	}
	
	
	public void parallaxsoftloadLayerNear(Bitmap image){
		parallaxsoftBGImg1 = image;
		parallaxsoftbackgroundLayer1 = new parallaxsoftRHDrawable(0, 0, -1, parallaxsoftfetmWidth*4, parallaxsoftfetmHeight);
		parallaxsoftbackgroundLayer1.parallaxsoftlloadBitmap(parallaxsoftBGImg1, GL10.GL_REPEAT, GL10.GL_CLAMP_TO_EDGE);
		parallaxsoftbackgroundLayer1.z = -0.1f;
		add(parallaxsoftbackgroundLayer1);
		float parallaxsofttextureCoordinates[] = { 0.0f, 1.0f,
				2.0f, 1.0f,
				0.0f, 0.0f,
				2.0f, 0.0f,
		};
		
		parallaxsoftbackgroundLayer1.parallaxsoftsetTextureCoordinates(parallaxsofttextureCoordinates);
	}
	
	public void parallaxsoftloadLayerMiddle(Bitmap image) {
		parallaxsoftBGImg2 = image;
		parallaxsoftbackgroundLayer2 = new parallaxsoftRHDrawable(0, 0, -1, parallaxsoftfetmWidth*4, parallaxsoftfetmHeight);
		parallaxsoftbackgroundLayer2.parallaxsoftlloadBitmap(parallaxsoftBGImg2, GL10.GL_REPEAT, GL10.GL_CLAMP_TO_EDGE);
		parallaxsoftbackgroundLayer2.z = -0.2f;

		add(parallaxsoftbackgroundLayer2);
		
		float parallaxsofttextureCoordinates[] = { 0.0f, 1.0f,
				2.0f, 1.0f,
				0.0f, 0.0f,
				2.0f, 0.0f,
		};
		
		
		parallaxsoftbackgroundLayer2.parallaxsoftsetTextureCoordinates(parallaxsofttextureCoordinates);
	}
	
	public void parallaxsoftloadLayerFar(Bitmap image) {

		parallaxsoftBGImg3 = image;
		parallaxsoftbackgroundLayer3 = new parallaxsoftRHDrawable(0, 0, -1, parallaxsoftfetmWidth*4, parallaxsoftfetmHeight);
		parallaxsoftbackgroundLayer3.parallaxsoftlloadBitmap(parallaxsoftBGImg3, GL10.GL_REPEAT, GL10.GL_CLAMP_TO_EDGE);
		parallaxsoftbackgroundLayer3.z = -0.3f;

		add(parallaxsoftbackgroundLayer3);
		float parallaxsofttextureCoordinates[] = { 0.0f, 1.0f,
				2.0f, 1.0f,
				0.0f, 0.0f,
				2.0f, 0.0f,
		};
		
		parallaxsoftbackgroundLayer3.parallaxsoftsetTextureCoordinates(parallaxsofttextureCoordinates);
	}
	
	
	public void update(){
		parallaxsoftbackgroundLayer1.x -= 1;
		if (parallaxsoftbackgroundLayer1.x < -parallaxsoftfetmWidth*2)
			parallaxsoftbackgroundLayer1.x = 0;
		
		parallaxsoftbackgroundLayer2.x -= 0.5f;
		if (parallaxsoftbackgroundLayer2.x < -parallaxsoftfetmWidth*2)
			parallaxsoftbackgroundLayer2.x = 0;
		
		parallaxsoftbackgroundLayer3.x -= 0.2f;
		if (parallaxsoftbackgroundLayer3.x < -parallaxsoftfetmWidth*2)
			parallaxsoftbackgroundLayer3.x = 0;
		
	}
	
	
};
	
