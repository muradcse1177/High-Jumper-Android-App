package com.parallaxsoft.highjumper;


public class parallaxsoftCounterDigit extends parallaxsoftMesh {
	protected float width;
	protected float height;
	protected float parallaxsoftwidthOfDigit;
	protected int parallaxsoftdigitValue;
	protected float parallaxsofttextureCoordinates[] = {0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f};
	public parallaxsoftCounterDigit(float _x, float _y, float _z, float _width, float _height) {
		x = _x;
		y = _y;
		z = _z;
		
		width= _width;
		height= _height;
		parallaxsoftwidthOfDigit = 1.0f/10.0f;
		
		parallaxsoftdigitValue=0;
		
		float parallaxsofttextureCoordinates[] = { 0.0f, 1.0f,
				parallaxsoftwidthOfDigit, 1.0f,
				0.0f, 0.0f,
				parallaxsoftwidthOfDigit, 0.0f,
		};

		short[] indices = new short[] { 0, 1, 2, 1, 3, 2 };

		float[] vertices = new float[] { 0, 0, 0, width, 0, 0.0f, 0, height,
				0.0f, width, height, 0.0f };

		parallaxsoftsetIndices(indices);
		parallaxsoftsetVertices(vertices);
		parallaxsoftsetTextureCoordinates(parallaxsofttextureCoordinates);
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
	public void incrementDigit() {
		parallaxsoftdigitValue++;
		if(parallaxsoftdigitValue==10)
			parallaxsoftdigitValue=0;
		
		float parallaxsofttextureCoordinates[] = {parallaxsoftwidthOfDigit*parallaxsoftdigitValue, 1.0f,
				parallaxsoftwidthOfDigit*parallaxsoftdigitValue+parallaxsoftwidthOfDigit, 1.0f,
				parallaxsoftwidthOfDigit*parallaxsoftdigitValue, 0.0f,
				parallaxsoftwidthOfDigit*parallaxsoftdigitValue+parallaxsoftwidthOfDigit, 0.0f,
		};
		parallaxsoftsetTextureCoordinates(parallaxsofttextureCoordinates);
	}
	public void parallaxsoftsetDigitToZero() {
		parallaxsoftdigitValue=0;
		float parallaxsofttextureCoordinates[] = { 0.0f, 1.0f,
				parallaxsoftwidthOfDigit, 1.0f,
				0.0f, 0.0f,
				parallaxsoftwidthOfDigit, 0.0f,
		};
		parallaxsoftsetTextureCoordinates(parallaxsofttextureCoordinates);
	}
	public void parallaxsoftsetDigitTo(int value) {
		parallaxsoftdigitValue=value;
		parallaxsofttextureCoordinates[0] = parallaxsoftwidthOfDigit*parallaxsoftdigitValue;
		parallaxsofttextureCoordinates[1] = 1.0f;
		parallaxsofttextureCoordinates[2] =parallaxsoftwidthOfDigit*parallaxsoftdigitValue+parallaxsoftwidthOfDigit;
		parallaxsofttextureCoordinates[3] = 1.0f;
		parallaxsofttextureCoordinates[4] = parallaxsoftwidthOfDigit*parallaxsoftdigitValue;
		parallaxsofttextureCoordinates[5] = 0.0f;
		parallaxsofttextureCoordinates[6] = parallaxsoftwidthOfDigit*parallaxsoftdigitValue+parallaxsoftwidthOfDigit;
		parallaxsofttextureCoordinates[7] = 0.0f;
		parallaxsoftsetTextureCoordinates(parallaxsofttextureCoordinates);
		
	}
}
