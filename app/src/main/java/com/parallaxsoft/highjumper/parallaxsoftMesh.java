package com.parallaxsoft.highjumper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

public class parallaxsoftMesh {
	private FloatBuffer parallaxsoftmVerticesBuffer = null;
	private ShortBuffer parallaxsoftfmIndicesBuffer = null;
	private FloatBuffer parallaxsoftmTextureBuffer;
	private int parallaxsoftmTextureId = -1;
	private Bitmap parallaxsoftmBitmap;
	private boolean parallaxsoftmShouldLoadTexture = false;
	private int parallaxsoftmWrapS;
	private int parallaxmWrapT;
	private ByteBuffer byteBuf;
	private int parallaxsoftmNumOfIndices = -1;
	private final float[] parallaxsoftmRGBA = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
	private FloatBuffer parallaxsoftmColorBuffer = null;
	public float x = 0;
	public float y = 0;
	public float z = 0;
	public float rx = 0;
	public float ry = 0;
	public float rz = 0;
	public boolean parallaxsoftshouldBeDrawn = true;
	public void draw(GL10 gl) {
		if (!parallaxsoftshouldBeDrawn) return;
		
		gl.glPushMatrix();
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, parallaxsoftmVerticesBuffer);
		gl.glColor4f(parallaxsoftmRGBA[0], parallaxsoftmRGBA[1], parallaxsoftmRGBA[2], parallaxsoftmRGBA[3]);
		if (parallaxsoftmColorBuffer != null) {
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, parallaxsoftmColorBuffer);
		}
		if (parallaxsoftmShouldLoadTexture) {
			parallaxsoftloadGLTexture(gl);
			parallaxsoftmShouldLoadTexture = false;
		}
		if (parallaxsoftmTextureId != -1 && parallaxsoftmTextureBuffer != null) {
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, parallaxsoftmTextureBuffer);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, parallaxsoftmTextureId);
		}
		gl.glTranslatef(x, y, z);
		gl.glRotatef(rx, 1, 0, 0);
		gl.glRotatef(ry, 0, 1, 0);
		gl.glRotatef(rz, 0, 0, 1);
		gl.glDrawElements(GL10.GL_TRIANGLES, parallaxsoftmNumOfIndices,
				GL10.GL_UNSIGNED_SHORT, parallaxsoftfmIndicesBuffer);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		if (parallaxsoftmTextureId != -1 && parallaxsoftmTextureBuffer != null) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glPopMatrix();
	}

	protected void parallaxsoftsetVertices(float[] vertices) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		parallaxsoftmVerticesBuffer = vbb.asFloatBuffer();
		parallaxsoftmVerticesBuffer.put(vertices);
		parallaxsoftmVerticesBuffer.position(0);
	}
	protected void parallaxsoftsetIndices(short[] indices) {
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		parallaxsoftfmIndicesBuffer = ibb.asShortBuffer();
		parallaxsoftfmIndicesBuffer.put(indices);
		parallaxsoftfmIndicesBuffer.position(0);
		parallaxsoftmNumOfIndices = indices.length;
	}

	protected void parallaxsoftsetTextureCoordinates(float[] textureCoords) {
		byteBuf = ByteBuffer.allocateDirect(textureCoords.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		parallaxsoftmTextureBuffer = byteBuf.asFloatBuffer();
		parallaxsoftmTextureBuffer.put(textureCoords);
		parallaxsoftmTextureBuffer.position(0);
	}
	protected void setColor(float red, float green, float blue, float alpha) {
		parallaxsoftmRGBA[0] = red;
		parallaxsoftmRGBA[1] = green;
		parallaxsoftmRGBA[2] = blue;
		parallaxsoftmRGBA[3] = alpha;
	}

	protected void setColors(float[] colors) {
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		parallaxsoftmColorBuffer = cbb.asFloatBuffer();
		parallaxsoftmColorBuffer.put(colors);
		parallaxsoftmColorBuffer.position(0);
	}

	public void parallaxsoftlloadBitmap(Bitmap bitmap) {
		this.parallaxsoftmBitmap = bitmap;
		parallaxsoftmShouldLoadTexture = true;
		parallaxsoftmWrapS = GL10.GL_CLAMP_TO_EDGE;
		parallaxmWrapT = GL10.GL_CLAMP_TO_EDGE;
	}
	
	public void parallaxsoftlloadBitmap(Bitmap bitmap, int wrapS, int wrapT) {
		this.parallaxsoftmBitmap = bitmap;
		parallaxsoftmShouldLoadTexture = true;
		parallaxsoftmWrapS = wrapS;
		parallaxmWrapT = wrapT;
	}

	private void parallaxsoftloadGLTexture(GL10 gl) {
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		parallaxsoftmTextureId = textures[0];
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		if ( gl instanceof GL11 ) {
		        gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
		}
		gl.glBindTexture(GL10.GL_TEXTURE_2D, parallaxsoftmTextureId);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				parallaxsoftmWrapS);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				parallaxmWrapT);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, parallaxsoftmBitmap, 0);
	}
}
