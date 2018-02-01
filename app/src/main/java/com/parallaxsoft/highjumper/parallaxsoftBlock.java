package com.parallaxsoft.highjumper;
import javax.microedition.khronos.opengles.GL10;
import android.graphics.Bitmap;
import android.graphics.Rect;

public class parallaxsoftBlock extends parallaxsoftGroup {
	
	public float parallaxsoftfetmWidth;
	public float parallaxsoftfetmHeight;
	public Rect parallaxsoftfetBlockRect;
	private parallaxsoftRHDrawable parallaxsoftfetmLeft;
	private parallaxsoftRHDrawable parallaxsoftfmMiddle;
	private parallaxsoftRHDrawable parallaxsoftfmRight;
	private static Bitmap parallaxsoftfmTextureLeft = null;
	private static Bitmap parallaxsoftfmTextureMiddle = null;
	private static Bitmap parallaxsoftfmTextureRight = null;
	private static int parallaxsoftfmTextureWidthLeft = 0;
	private static int parallaxsoftfmTextureWidthMiddle = 0;
	private static int parallaxsoftfmTextureWidthRight = 0;
	private static int parallaxsoftfmTextureHeightLeft = 0;
	private static int parallaxsoftfmTextureHeightMiddle = 0;
	private static int parallaxsoftfmTextureHeightRight = 0;
	
	
	final private static float parallaxsoftfmTextureCoordinates[] = { 0.0f, 1.0f,
		1.0f, 1.0f,
		0.0f, 0.0f,
		1.0f, 0.0f,
	};
	
	private float parallaxsoftfmTextureCoordinatesMiddle[] = { 0, 1.0f,
		1.0f, 1.0f,
		0, 0.0f,
		1.0f, 0.0f,
	};
	final private static short[] parallaxsoftfmIndices = new short[] { 0, 1, 2, 1, 3, 2 };
	
	private float[] parallaxsoftfmVerticesLeft = new float[] { 0, 0, 0, 1, 0, 0, 0, 1,
		0, 1, 1, 0.0f };
	private float[] parallaxsoftfmVerticesMiddle = new float[] { 0, 0, 0, 1, 0, 0, 0, 1,
		0, 1, 1, 0.0f };
	private float[] parallaxsoftfmVerticesRight = new float[] { 0, 0, 0, 1, 0, 0, 0, 1,
		0, 1, 1, 0.0f };

	
	public parallaxsoftBlock() {
		parallaxsoftfetmLeft = new parallaxsoftRHDrawable(0, 0, 0, 10, 10);
		parallaxsoftfmMiddle = new parallaxsoftRHDrawable(0, 0, 0, 10, 10);
		parallaxsoftfmRight = new parallaxsoftRHDrawable(0, 0, 0, 10, 10);
		parallaxsoftfetmLeft.parallaxsoftlloadBitmap(parallaxsoftfmTextureLeft, GL10.GL_REPEAT, GL10.GL_CLAMP_TO_EDGE);
		parallaxsoftfmMiddle.parallaxsoftlloadBitmap(parallaxsoftfmTextureMiddle, GL10.GL_REPEAT, GL10.GL_CLAMP_TO_EDGE);
		parallaxsoftfmRight.parallaxsoftlloadBitmap(parallaxsoftfmTextureRight, GL10.GL_REPEAT, GL10.GL_CLAMP_TO_EDGE);
		parallaxsoftfetmLeft.parallaxsoftsetIndices(parallaxsoftfmIndices);
		parallaxsoftfmMiddle.parallaxsoftsetIndices(parallaxsoftfmIndices);
		parallaxsoftfmRight.parallaxsoftsetIndices(parallaxsoftfmIndices);
		parallaxsoftfetmLeft.parallaxsoftsetVertices(parallaxsoftfmVerticesLeft);
		parallaxsoftfmMiddle.parallaxsoftsetVertices(parallaxsoftfmVerticesMiddle);
		parallaxsoftfmRight.parallaxsoftsetVertices(parallaxsoftfmVerticesRight);
		parallaxsoftfetmLeft.parallaxsoftsetTextureCoordinates(parallaxsoftfmTextureCoordinates);
		parallaxsoftfmMiddle.parallaxsoftsetTextureCoordinates(parallaxsoftfmTextureCoordinates);
		parallaxsoftfmRight.parallaxsoftsetTextureCoordinates(parallaxsoftfmTextureCoordinates);
		add(parallaxsoftfetmLeft);
		add(parallaxsoftfmMiddle);
		add(parallaxsoftfmRight);
		parallaxsoftfetBlockRect = new Rect((int)x, (int)(y+parallaxsoftfetmHeight), (int)(x+parallaxsoftfetmWidth), (int)y );
	}
	
	public static void cleanup() {
		if (parallaxsoftfmTextureLeft != null) parallaxsoftfmTextureLeft.recycle();
		if (parallaxsoftfmTextureMiddle != null) parallaxsoftfmTextureMiddle.recycle();
		if (parallaxsoftfmTextureRight != null) parallaxsoftfmTextureRight.recycle();
	}
	
	public void parallaxsoftfupdateRect()
	{
		parallaxsoftfetBlockRect.left =(int)x;
		parallaxsoftfetBlockRect.top = (int)(y+parallaxsoftfetmHeight);
		parallaxsoftfetBlockRect.right = (int)(x+parallaxsoftfetmWidth);
		parallaxsoftfetBlockRect.bottom = (int)y;
	}
	
	public void parallaxsoftfsetWidth(float width)
	{
		parallaxsoftfetmWidth = width;
		
		parallaxsoftfmVerticesLeft[3] = parallaxsoftfmTextureWidthLeft;
		parallaxsoftfmVerticesLeft[9] = parallaxsoftfmTextureWidthLeft;

		parallaxsoftfmVerticesMiddle[0] = parallaxsoftfmTextureWidthLeft;
		parallaxsoftfmVerticesMiddle[3] = parallaxsoftfetmWidth-parallaxsoftfmTextureWidthRight;
		parallaxsoftfmVerticesMiddle[6] = parallaxsoftfmTextureWidthLeft;
		parallaxsoftfmVerticesMiddle[9] = parallaxsoftfetmWidth-parallaxsoftfmTextureWidthRight;

		parallaxsoftfmVerticesRight[0] = parallaxsoftfetmWidth-parallaxsoftfmTextureWidthRight;
		parallaxsoftfmVerticesRight[3] = parallaxsoftfetmWidth;
		parallaxsoftfmVerticesRight[6] = parallaxsoftfetmWidth-parallaxsoftfmTextureWidthRight;
		parallaxsoftfmVerticesRight[9] = parallaxsoftfetmWidth;
		
		parallaxsoftfmTextureCoordinatesMiddle[2] = (width - parallaxsoftfmTextureWidthLeft - parallaxsoftfmTextureWidthRight) / parallaxsoftfmTextureWidthMiddle;
		parallaxsoftfmTextureCoordinatesMiddle[6] = parallaxsoftfmTextureCoordinatesMiddle[2];
		
		parallaxsoftfetmLeft.parallaxsoftsetVertices(parallaxsoftfmVerticesLeft);
		parallaxsoftfmMiddle.parallaxsoftsetVertices(parallaxsoftfmVerticesMiddle);
		parallaxsoftfmRight.parallaxsoftsetVertices(parallaxsoftfmVerticesRight);
		
		parallaxsoftfmMiddle.parallaxsoftsetTextureCoordinates(parallaxsoftfmTextureCoordinatesMiddle);
	}
	
	public void parallaxsoftfsetHeight(float height)
	{
		parallaxsoftfetmHeight = height;

		parallaxsoftfmVerticesLeft[1] = parallaxsoftfetmHeight-parallaxsoftfmTextureHeightLeft;
		parallaxsoftfmVerticesLeft[4] = parallaxsoftfetmHeight-parallaxsoftfmTextureHeightLeft;
		parallaxsoftfmVerticesLeft[7] = parallaxsoftfetmHeight;
		parallaxsoftfmVerticesLeft[10] = parallaxsoftfetmHeight;

		parallaxsoftfmVerticesMiddle[1] = parallaxsoftfetmHeight-parallaxsoftfmTextureHeightMiddle;
		parallaxsoftfmVerticesMiddle[4] = parallaxsoftfetmHeight-parallaxsoftfmTextureHeightMiddle;
		parallaxsoftfmVerticesMiddle[7] = parallaxsoftfetmHeight;
		parallaxsoftfmVerticesMiddle[10] = parallaxsoftfetmHeight;

		parallaxsoftfmVerticesRight[1] = parallaxsoftfetmHeight-parallaxsoftfmTextureHeightRight;
		parallaxsoftfmVerticesRight[4] = parallaxsoftfetmHeight-parallaxsoftfmTextureHeightRight;
		parallaxsoftfmVerticesRight[7] = parallaxsoftfetmHeight;
		parallaxsoftfmVerticesRight[10] = parallaxsoftfetmHeight;

		parallaxsoftfetmLeft.parallaxsoftsetVertices(parallaxsoftfmVerticesLeft);
		parallaxsoftfmMiddle.parallaxsoftsetVertices(parallaxsoftfmVerticesMiddle);
		parallaxsoftfmRight.parallaxsoftsetVertices(parallaxsoftfmVerticesRight);
	}

	public static void parallaxsoftfsetTextureLeft(Bitmap texture)
	{
		parallaxsoftfmTextureLeft = texture;
		parallaxsoftfmTextureWidthLeft = parallaxsoftfmTextureLeft.getWidth();
		parallaxsoftfmTextureHeightLeft = parallaxsoftfmTextureLeft.getHeight();
	}
	public static void parallaxsoftfsetTextureMiddle(Bitmap texture)
	{
		parallaxsoftfmTextureMiddle = texture;
		parallaxsoftfmTextureWidthMiddle = parallaxsoftfmTextureMiddle.getWidth();
		parallaxsoftfmTextureHeightMiddle = parallaxsoftfmTextureMiddle.getHeight();
	}
	public static void parallaxsoftfsetTextureRight(Bitmap texture)
	{
		parallaxsoftfmTextureRight = texture;
		parallaxsoftfmTextureWidthRight = parallaxsoftfmTextureRight.getWidth();
		parallaxsoftfmTextureHeightRight = parallaxsoftfmTextureRight.getHeight();
	}
	
	public static int parallaxsoftfgetTextureLeftWidth()
	{
		assert(parallaxsoftfmTextureWidthLeft != 0);
		return parallaxsoftfmTextureWidthLeft;
	}
	public static int parallaxsoftfgetTextureMiddleWidth()
	{
		assert(parallaxsoftfmTextureWidthMiddle != 0);
		return parallaxsoftfmTextureWidthMiddle;
	}
	public static int parallaxsoftfgetTextureRightWidth()
	{
		assert(parallaxsoftfmTextureWidthRight != 0);
		return parallaxsoftfmTextureWidthRight;
	}
	
	
	
}


