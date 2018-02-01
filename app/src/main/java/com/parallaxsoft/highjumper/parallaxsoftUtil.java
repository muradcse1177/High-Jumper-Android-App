package com.parallaxsoft.highjumper;
import java.io.IOException;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


public class parallaxsoftUtil {
	private static parallaxsoftUtil parallaxsoftmInstance =  null;
	public static int parallaxsoftmScreenHeight = 0;
	public static int parallaxsoftmScreenWidth = 0;
	public static int parallaxsoftfetmWidthHeightRatio = 0;
	private static Context parallaxsoftmContext = null;
	private static parallaxsoftOpenGLRenderer parallaxsoftmRenderer = null;
	public static long parallaxsoftroundStartTime = 0;
	
	public static synchronized parallaxsoftUtil getInstance() {
		if(parallaxsoftmInstance == null)
			parallaxsoftmInstance = new parallaxsoftUtil();
		
		return parallaxsoftmInstance;
	}
	
	public static float getPercentOfScreenWidth(float percent) {
		float percentWidth=parallaxsoftmScreenWidth/100*percent;
		return percentWidth;
	}
	public static float getPercentOfScreenHeight(float percent) {
		float percentHeight=parallaxsoftmScreenHeight/100*percent;
		return percentHeight;
	} 	
	
	public int toScreenY(int y) {
		y *= -1;
		y += parallaxsoftmScreenHeight;
		
		return y;
	}
	
	public void parallaxsoftsetAppContext(Context context)
	{
		parallaxsoftmContext = context;
	}
	
	public static Context getAppContext()
	{
		assert(parallaxsoftmContext != null);
		return parallaxsoftmContext;
	}
	public void setAppRenderer(parallaxsoftOpenGLRenderer renderer)
	{
		parallaxsoftmRenderer = renderer;
	}
	
	public static parallaxsoftOpenGLRenderer getAppRenderer()
	{
		assert(parallaxsoftmRenderer != null);
		return parallaxsoftmRenderer;
	}
	public static long getTimeSinceRoundStartMillis()
	{
		assert(parallaxsoftroundStartTime != 0);
		return System.currentTimeMillis()-parallaxsoftroundStartTime;
	}
	public static Bitmap parallaxsoftlloadBitmapFromAssets(String filename) {
		
		try {
			return BitmapFactory.decodeStream(parallaxsoftmContext.getAssets().open(filename));
		} catch (IOException e) {
			Log.e(parallaxsoftSettings.LOG_TAG, "unable to load asset: " + filename);
			e.printStackTrace();
		}
		
		final int size = 16;
		int []colors = new int[size*size];
		
		for (int i = 0; i < size; i++) {
			colors[i*size+i] = 0xffff0000;
			colors[i*size+i + (size-i*2-1) ] = 0xffff0000;
		}
		
		return Bitmap.createBitmap(colors, size, size, Bitmap.Config.RGB_565);
	}
}
