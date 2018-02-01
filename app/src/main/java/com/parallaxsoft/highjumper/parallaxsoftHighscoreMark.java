package com.parallaxsoft.highjumper;
import android.graphics.Bitmap;
import android.util.Log;
public class parallaxsoftHighscoreMark extends parallaxsoftGroup
{
	private parallaxsoftOpenGLRenderer parallaxsoftmRenderer;
	private parallaxsoftCounterDigit parallaxsoftmHighscoreDigit1;
	private parallaxsoftCounterDigit parallaxsoftmHighscoreDigit2;
	private parallaxsoftCounterDigit parallaxsoftmHighscoreDigit3;
	private parallaxsoftCounterDigit parallaxsoftmHighscoreDigit4;
	private parallaxsoftCounterDigit parallaxsoftmHighscoreId;
	private parallaxsoftCounterGroup parallaxsoftmHighscoreGroup;
	private parallaxsoftRHDrawable parallaxsoftmHighscoreMark;
	
	public parallaxsoftHighscoreMark(parallaxsoftOpenGLRenderer glrenderer, Bitmap highscoreMarkBitmap, Bitmap counterFont)
	{
		parallaxsoftmRenderer = glrenderer;
		

		parallaxsoftmHighscoreMark = new parallaxsoftRHDrawable(0, 0, 1, 64, 512);
		parallaxsoftmHighscoreMark.parallaxsoftlloadBitmap(highscoreMarkBitmap);
		this.add(parallaxsoftmHighscoreMark);
		parallaxsoftmHighscoreGroup = new parallaxsoftCounterGroup(0, 0, 1, 128*4, 20, 25);
		parallaxsoftmHighscoreDigit1 = new parallaxsoftCounterDigit(0, 0, 1, 16, 20);
		parallaxsoftmHighscoreDigit1.parallaxsoftlloadBitmap(counterFont);
		parallaxsoftmHighscoreGroup.add(parallaxsoftmHighscoreDigit1);
		parallaxsoftmHighscoreDigit2 = new parallaxsoftCounterDigit(15, 0, 1, 16, 20);
		parallaxsoftmHighscoreDigit2.parallaxsoftlloadBitmap(counterFont);
		parallaxsoftmHighscoreGroup.add(parallaxsoftmHighscoreDigit2);
		parallaxsoftmHighscoreDigit3 = new parallaxsoftCounterDigit(30, 0, 1, 16, 20);
		parallaxsoftmHighscoreDigit3.parallaxsoftlloadBitmap(counterFont);
		parallaxsoftmHighscoreGroup.add(parallaxsoftmHighscoreDigit3);
		parallaxsoftmHighscoreDigit4 = new parallaxsoftCounterDigit(45, 0, 1, 16, 20);
		parallaxsoftmHighscoreDigit4.parallaxsoftlloadBitmap(counterFont);
		parallaxsoftmHighscoreGroup.add(parallaxsoftmHighscoreDigit4);
		this.add(parallaxsoftmHighscoreGroup);
		parallaxsoftmHighscoreId = new parallaxsoftCounterDigit(5, 16, 1, 16, 20);
		parallaxsoftmHighscoreId.parallaxsoftlloadBitmap(counterFont);
		this.add(parallaxsoftmHighscoreId);
		parallaxsoftmRenderer.addMesh(this);
	}
	
	public void parallaxsoftsetMarkTo(int id, int score)
	{
		parallaxsoftmHighscoreId.parallaxsoftsetDigitTo(id);
		parallaxsoftmHighscoreGroup.parallaxsofttryToSetCounterTo(score);

		if(parallaxsoftSettings.RHDEBUG)
			Log.d("debug", "setting mark " + id + " to score " + score);
		
	}
}
