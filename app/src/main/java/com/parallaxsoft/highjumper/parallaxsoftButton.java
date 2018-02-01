package com.parallaxsoft.highjumper;

public class parallaxsoftButton extends parallaxsoftRHDrawable {
	private boolean showButton = false;
	public float lastX;
	
	public parallaxsoftButton(float _x, float _y, float _z, float _width, float _height){
		super(_x, _y, _z, _width, _height);
		x=lastX=_x;
		y=_y;
		z=_z;
		width=_width;
		height=_height;
	}
	public void parallaxsoftsetShowButton(boolean toSet){
		showButton=toSet;
	}
	public boolean parallaxsoftgetShowButton(){
		return showButton;
	}
	public int getWidth(){
		return (int)width;
	}
	public int getHeight(){
		return (int)height;
	}
	public int getX(){
		return (int)x;
	}
	public int getY(){
		return (int)y;
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
