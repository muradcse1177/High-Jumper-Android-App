package com.parallaxsoft.highjumper;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class parallaxsoftOpenGLRenderer implements Renderer {
	private final parallaxsoftGroup root;
	private long parallaxsofttimeAtLastSecond = 0;
	private long parallaxsoftcurrentTimeTaken=0;
	private long starttime = 0;
	private int fpsCounter;
	public int fps = 0;
	public boolean parallaxsoftfirstFrameDone = false;

	public parallaxsoftOpenGLRenderer() {
		parallaxsoftGroup parallaxsoftGroup = new parallaxsoftGroup();
		root = parallaxsoftGroup;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		gl.glDisable(GL10.GL_DITHER);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA); 
        
        parallaxsofttimeAtLastSecond = System.currentTimeMillis();
        fpsCounter=0;
	}

	public void onDrawFrame(GL10 gl) {
		if(parallaxsoftSettings.RHDEBUG){
			starttime = System.currentTimeMillis();
			parallaxsoftcurrentTimeTaken= System.currentTimeMillis()- starttime;
			Log.d("frametime", "time at beginning: " + Integer.toString((int)parallaxsoftcurrentTimeTaken));
		}
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		if(parallaxsoftSettings.RHDEBUG){
			parallaxsoftcurrentTimeTaken= System.currentTimeMillis()- starttime;
			Log.d("frametime", "time after clear and loadident: " + Integer.toString((int)parallaxsoftcurrentTimeTaken));
		}
		synchronized (root) {
			root.draw(gl);
		}
		fpsCounter++;
		if(parallaxsoftSettings.RHDEBUG){
			parallaxsoftcurrentTimeTaken= System.currentTimeMillis()- starttime;
			Log.d("frametime", "time after draw: " + Integer.toString((int)parallaxsoftcurrentTimeTaken));
		}
		
		if((System.currentTimeMillis() - parallaxsofttimeAtLastSecond) > 1000){
			parallaxsofttimeAtLastSecond = System.currentTimeMillis();
			fps = fpsCounter;
			fpsCounter=0;
			if(parallaxsoftSettings.RHDEBUG) {
				Log.d("framerate", "draws per second: " + Integer.toString(fpsCounter));
			}
		}
		
		parallaxsoftfirstFrameDone = true;
	}
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if(parallaxsoftSettings.RHDEBUG)
			Log.d("frametime", "onSurfaceChanged called");
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, 0, width, 0, height);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

	}

	public void addMesh(parallaxsoftMesh parallaxsoftMesh) {
		synchronized (root) {
			root.add(parallaxsoftMesh);
		}
	}
	
	public void removeMesh(parallaxsoftMesh parallaxsoftMesh) {
		synchronized (root) {
			root.remove(parallaxsoftMesh);
		}
	}
}
