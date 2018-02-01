package com.parallaxsoft.highjumper;
import java.util.Vector;
import javax.microedition.khronos.opengles.GL10;

public class parallaxsoftCounterGroup extends parallaxsoftCounterDigit {
	private long parallaxsoftLastFrameChangeTime;
	private int parallaxsoftFrameUpdateTime;
	private int parallaxsoftlastCounterValueOnes;
	private int parallaxsoftlastCounterValueTens;
	private int lastCounterValueHundreds;
	private int lastCounterValueThousands;
	private int parallaxsoftcounterValueOnes = 0;
	private int parallaxsoftcounterValueTens = 0;
	private int counterValueHundreds = 0;
	private int counterValueThousands = 0;
	private int size;
	
	
	public parallaxsoftCounterGroup(float _x, float _y, float _z, float _width, float _height, int _parallaxsoftFrameUpdateTime){
		super((int)_x, (int)_y, (int)_z, (int)_width, (int)_height);
		x=_x;
		y=_y;
		width=_width;
		height=_height;
		parallaxsoftFrameUpdateTime = _parallaxsoftFrameUpdateTime;
		parallaxsoftLastFrameChangeTime = System.currentTimeMillis();
		parallaxsoftlastCounterValueOnes = 0;
		parallaxsoftlastCounterValueTens = 0;
		lastCounterValueHundreds = 0;
		lastCounterValueThousands = 0;
	}
	private final Vector<parallaxsoftCounterDigit> parallaxsoftmChildren = new Vector<parallaxsoftCounterDigit>();

	@Override
	public void draw(GL10 gl) {
		size = parallaxsoftmChildren.size();
		for (int i = 0; i < size; i++)
			parallaxsoftmChildren.get(i).draw(gl);
	}

	public void add(int location, parallaxsoftCounterDigit object) {
		parallaxsoftmChildren.add(location, object);
	}
	public boolean add(parallaxsoftCounterDigit object) {
		return parallaxsoftmChildren.add(object);
	}
	public void clear() {
		parallaxsoftmChildren.clear();
	}
	public parallaxsoftMesh get(int location) {
		return parallaxsoftmChildren.get(location);
	}
	public parallaxsoftMesh remove(int location) {
		return parallaxsoftmChildren.remove(location);
	}
	public boolean remove(Object object) {
		return parallaxsoftmChildren.remove(object);
	}
	public int size() {
		return parallaxsoftmChildren.size();
	}
	public void parallaxsoftresetCounter(){
		int size = parallaxsoftmChildren.size();
		for (int i = 0; i < size; i++)
			parallaxsoftmChildren.get(i).parallaxsoftsetDigitToZero();
	}
	public void parallaxsofttryToSetCounterTo(int counterValue) {
		if(  System.currentTimeMillis() > (parallaxsoftLastFrameChangeTime+parallaxsoftFrameUpdateTime) ){
			parallaxsoftLastFrameChangeTime=System.currentTimeMillis();
			
			parallaxsoftcounterValueOnes = counterValue % 10;
			if(parallaxsoftlastCounterValueOnes != parallaxsoftcounterValueOnes){
				parallaxsoftmChildren.get(3).parallaxsoftsetDigitTo(parallaxsoftcounterValueOnes);
				parallaxsoftlastCounterValueOnes = parallaxsoftcounterValueOnes;
			}
			
			parallaxsoftcounterValueTens = (counterValue % 100) / 10 ;
			if(parallaxsoftlastCounterValueTens != parallaxsoftcounterValueTens){
				parallaxsoftmChildren.get(2).parallaxsoftsetDigitTo(parallaxsoftcounterValueTens);
				parallaxsoftlastCounterValueTens = parallaxsoftcounterValueTens;
			}			
			counterValueHundreds = (counterValue % 1000 ) / 100;
			if(lastCounterValueHundreds != counterValueHundreds){
				parallaxsoftmChildren.get(1).parallaxsoftsetDigitTo(counterValueHundreds);
				lastCounterValueHundreds = counterValueHundreds;
			}
			
			counterValueThousands = counterValue / 1000;
			if(lastCounterValueThousands != counterValueThousands){
				parallaxsoftmChildren.get(0).parallaxsoftsetDigitTo(counterValueThousands);
				lastCounterValueThousands = counterValueThousands;
			}

		}
	}
	
}
