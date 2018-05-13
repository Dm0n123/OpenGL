package com.jack.lwjgl.engine.graphics.buffers;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.List;

import com.jack.lwjgl.engine.DataType;

public class VertexArray {
	private int id;
	private VertexBuffer vbo;
	private IndexBuffer ebo;
	private DataType datatype;
	private List<VertexArrayElement> elements = new ArrayList<VertexArrayElement>();
	private int size = 0;
	private int vertices = 0;
	
	public VertexArray() {
		id = glGenVertexArrays();
	}
	
	public VertexArray(VertexBuffer buffer) {
		id = glGenVertexArrays();
		vbo = buffer;
	}
	
	public VertexArray(VertexBuffer vbo, IndexBuffer ebo) {
		id = glGenVertexArrays();
		this.vbo = vbo;
		this.ebo = ebo;
	}
	
	public void setIndexBuffer(IndexBuffer buffer) {
		ebo = buffer;
	}
	
	public void setVertexBuffer(VertexBuffer buffer) {
		vbo = buffer;
	}
	
	public boolean hasIndexBuffer() {
		return ebo != null;
	}
	
	public void bind() {
		glBindVertexArray(id);
	}
	
	public void unbind() {
		glBindVertexArray(0);
	}
	
	public void push(DataType type, int amount) {
		if(type != this.datatype && this.datatype != null) {
			System.err.println("Vertex Array Expected " + this.datatype.name() + ", but got " + type.name());
		} else if(this.datatype == null) {
			this.datatype = type;
		}
		
		elements.add(new VertexArrayElement(type, amount));
		VertexArrayElement element = elements.get(elements.size()-1);
		size += element.getSize();
		bind();
		vbo.bind();
		if(hasIndexBuffer()) {
			ebo.bind();
		}
		int index = 0;
		long offset = 0;
		int total = 0;
		for(VertexArrayElement e : elements) {
			glVertexAttribPointer(index, e.getAmount(), e.getType(), false, size, offset);
			glEnableVertexAttribArray(index);
			offset += e.getSize();
			total += e.getAmount();
			index++;
		}
		vertices = vbo.getSize()/total;
	}
	
	public int getVertices() {
		return vertices;
	}
	
	public int getIndices() {
		return hasIndexBuffer() ? ebo.getSize() : 0;
	}
	
}
