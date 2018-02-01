package com.parallaxsoft.highjumper;
import java.util.Vector;
import javax.microedition.khronos.opengles.GL10;

public class parallaxsoftGroup extends parallaxsoftMesh {
	private final Vector<parallaxsoftMesh> parallaxsoftmChildren = new Vector<parallaxsoftMesh>();

	@Override
	public void draw(GL10 gl) {
		if (!parallaxsoftshouldBeDrawn) return;
		
		int size = parallaxsoftmChildren.size();
		gl.glPushMatrix();
		gl.glTranslatef(x, y, z);
		gl.glRotatef(rx, 1, 0, 0);
		gl.glRotatef(ry, 0, 1, 0);
		gl.glRotatef(rz, 0, 0, 1);

		for (int i = 0; i < size; i++) {
			parallaxsoftmChildren.get(i).draw(gl);
		}
			
		gl.glPopMatrix();
	}

	public void add(int location, parallaxsoftMesh object) {
		parallaxsoftmChildren.add(location, object);
	}
	public boolean add(parallaxsoftMesh object) {
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

}
