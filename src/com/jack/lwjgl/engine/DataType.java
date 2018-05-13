package com.jack.lwjgl.engine;

import static org.lwjgl.opengl.GL11.*;

public enum DataType {
	INT, SHORT, LONG, FLOAT, DOUBLE, BYTE, CHAR;
	
	public static int covertToGL(DataType data) {
		switch(data) {
		case INT:
			return GL_UNSIGNED_INT;
		case SHORT:
			return GL_SHORT;
		case FLOAT:
			return GL_FLOAT;
		case BYTE:
		case CHAR:
			return GL_BYTE;
		default:
			return 0;
		}
	}
}
