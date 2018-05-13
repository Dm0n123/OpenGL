package com.jack.lwjgl.engine.graphics.buffers;

import static org.lwjgl.opengl.GL11.*;

import com.jack.lwjgl.engine.DataType;

public class VertexArrayElement {
	private int amount;
	private int size;
	private int type;
	
	
	public VertexArrayElement(DataType type, int amount) {
		this.amount = amount;
		switch(type) {
		case INT:
			size = amount*4;
			this.type = GL_INT;
			break;
		case FLOAT:
			size = amount*4;
			this.type = GL_FLOAT;
			break;
		default:
			break;
		}
		
	}
	
	public int getAmount() {
		return amount;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getType() {
		return type;
	}
}
