package com.parallaxsoft.highjumper;

import android.graphics.Rect;

public class parallaxsoftObstacle extends parallaxsoftRHDrawable {
	private Rect parallaxsoftObstacleRect;
	public char parallaxsoftObstacleType;
	public boolean parallaxsoftdidTrigger;
	
	public parallaxsoftObstacle(float _x, float _y, float _z, float _width, float _height, char type){
		super((int)_x, (int)_y, (int)_z, (int)_width, (int)_height);
		
		x=_x;
		y=_y;
		z=_z;
		parallaxsoftObstacleType=type;
		parallaxsoftObstacleRect = new Rect ((int)x, (int)y, (int)x+(int)width, (int)y+(int)height);
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
		
		parallaxsoftdidTrigger=false;
}
	

	public void setparallaxsoftObstacleRect(float l, float r, float top, float bottom){
		parallaxsoftObstacleRect.left=(int)l;
		parallaxsoftObstacleRect.right=(int)r;
		parallaxsoftObstacleRect.top=(int)top;
		parallaxsoftObstacleRect.bottom=(int)bottom;
	}
	public void setparallaxsoftObstacleRectRight(int r){
		parallaxsoftObstacleRect.right=r;
	}

	public void updateparallaxsoftObstacleRect(int parallaxsoftlevelPosition){
		parallaxsoftObstacleRect.left -= parallaxsoftlevelPosition;
		parallaxsoftObstacleRect.right -= parallaxsoftlevelPosition;
	}
	public boolean parallaxsoftisClicked(float clickX, float clickY){
		if(clickX <= x+width && clickX > x){
			if(clickY <= y+height && clickY > y){
				return true;
			}
		}
		return false;
	}
}
