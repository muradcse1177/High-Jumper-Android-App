package com.parallaxsoft.highjumper;


public class parallaxsoftRHDrawable extends parallaxsoftMesh {
	protected float width;
	protected float height;
	
	public parallaxsoftRHDrawable(float _x, float _y, float _z, float _width, float _height) {
		x = _x;
		y = _y;
		z = _z;
		width= _width;
		height= _height;
		float parallaxsofttextureCoordinates[] = { 0.0f, 1.0f,
				1.0f, 1.0f,
				0.0f, 0.0f,
				1.0f, 0.0f,
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
}
