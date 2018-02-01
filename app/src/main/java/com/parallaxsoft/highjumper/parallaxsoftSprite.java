package com.parallaxsoft.highjumper;

public class parallaxsoftSprite extends parallaxsoftMesh {
	private float width;
	private float height;
	private int currentFrame;
	private long parallaxsoftLastFrameChangeTime;
	private int parallaxsoftFrameUpdateTime;
	private float numberOfFrames;
	private float textureWidthOfOneFrame;
	private float parallaxsofttextureCoordinates[] = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,};
	
	public parallaxsoftSprite(float _x, float _y, float _z, float _width, float _height, int _parallaxsoftFrameUpdateTime, int _numberOfFrames) {
		x = _x;
		y = _y;
		z = _z;
		
		width= _width;
		height= _height;
		
		currentFrame=0;
		parallaxsoftFrameUpdateTime = _parallaxsoftFrameUpdateTime;
		numberOfFrames = _numberOfFrames;
		textureWidthOfOneFrame = 1 / numberOfFrames;
		
		float parallaxsofttextureCoordinates[] = { 0.0f, 1.0f,
				textureWidthOfOneFrame, 1.0f,
				0.0f, 0.0f,
				textureWidthOfOneFrame, 0.0f,
		};

		short[] indices = new short[] { 0, 1, 2, 1, 3, 2 };

		float[] vertices = new float[] { 0, 0, 0, width, 0, 0.0f, 0, height,
				0.0f, width, height, 0.0f };
		parallaxsoftsetIndices(indices);
		parallaxsoftsetVertices(vertices);
		parallaxsoftsetTextureCoordinates(parallaxsofttextureCoordinates);
		parallaxsoftLastFrameChangeTime = System.currentTimeMillis();
		
	}
	
	public void parallaxsoftfsetWidth(int width)
	{
		this.width = width;
		
		float[] vertices = new float[] { 0, 0, 0, width, 0, 0.0f, 0, height,
				0.0f, width, height, 0.0f };

		parallaxsoftsetVertices(vertices);
	}
	
	public void parallaxsoftfsetHeight(int height)
	{
		this.height = height;
		float[] vertices = new float[] { 0, 0, 0, width, 0, 0.0f, 0, height,
				0.0f, width, height, 0.0f };

		parallaxsoftsetVertices(vertices);
	}
	public void updatePosition(float _x, float _y)
	{
		x = _x;
		y = _y;
	}
	public void parallaxsoftsetparallaxsoftFrameUpdateTime(float _parallaxsoftFrameUpdateTime)
	{
		parallaxsoftFrameUpdateTime = (int)_parallaxsoftFrameUpdateTime;
	}
	public void parallaxsofttryToSetNextFrame() {
		if(  System.currentTimeMillis() > (parallaxsoftLastFrameChangeTime+parallaxsoftFrameUpdateTime) ){
			parallaxsoftLastFrameChangeTime=System.currentTimeMillis();
			currentFrame++;
			if(currentFrame==numberOfFrames)
				currentFrame=0;
			
			parallaxsofttextureCoordinates[0] = textureWidthOfOneFrame*currentFrame;
			parallaxsofttextureCoordinates[1] = 1.0f;
			parallaxsofttextureCoordinates[2] = textureWidthOfOneFrame*currentFrame+textureWidthOfOneFrame;
			parallaxsofttextureCoordinates[3] = 1.0f;
			parallaxsofttextureCoordinates[4] = textureWidthOfOneFrame*currentFrame;
			parallaxsofttextureCoordinates[5] = 0.0f;
			parallaxsofttextureCoordinates[6] = textureWidthOfOneFrame*currentFrame+textureWidthOfOneFrame;
			parallaxsofttextureCoordinates[7] = 0.0f;
			parallaxsoftsetTextureCoordinates(parallaxsofttextureCoordinates);
		}
	}
}
