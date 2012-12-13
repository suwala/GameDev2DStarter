package com.badlogic.androidgames.framework.gl;

import com.badlogic.androidgames.framework.impl.GLGraphics;

public class SpriteBatcher {

	final float[] verticesBuffer;
	int bufferIndex;
	final Vertices vertices;
	int numSprites;
	
	public SpriteBatcher(GLGraphics glGraphics,int maxSprites){
		this.verticesBuffer = new float[maxSprites *4 * 4];
		this.vertices = new Vertices(glGraphics, maxSprites*4, maxSprites*4, false, true);
		this.bufferIndex = 0;
		this.numSprites = 0;
		
		short[] indices = new short[maxSprites * 6];
		int len = indices.length;
		short j = 0;
		for(int i=0;i<len;i+=6,j+=4){
			indices[i+0] = (short)(j+0);
			indices[i+1] = (short)(j+1);
			indices[i+2] = (short)(j+2);
			indices[i+3] = (short)(j+2);
			indices[i+4] = (short)(j+3);
			indices[i+5] = (short)(j+0);
			
		}
		vertices.setIndices(indices, 0, indices.length);
	}
}
