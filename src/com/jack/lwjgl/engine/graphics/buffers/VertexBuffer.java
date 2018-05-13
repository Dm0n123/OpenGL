package com.jack.lwjgl.engine.graphics.buffers;

import static org.lwjgl.opengl.GL15.*;

public class VertexBuffer {

	private int id; 
	private int size;
	
	public VertexBuffer(float[] data) {
		id = glGenBuffers();
		size = data.length;
		bind();
		glBufferData(GL_ARRAY_BUFFER,data,GL_STATIC_DRAW);
	}
	
	public VertexBuffer(int size) {
		id = glGenBuffers();
		this.size = size;
		bind();
		glBufferData(GL_ARRAY_BUFFER,size,GL_STATIC_DRAW);
	}
	
	public void unbind() {
		glBindBuffer(GL_ARRAY_BUFFER,0);
	}
	
	public void bind() {
		glBindBuffer(GL_ARRAY_BUFFER,id);
	}
	
	public int getSize() {
		return size;
	}
	
}
