package com.jack.lwjgl.engine.graphics;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import com.jack.lwjgl.engine.io.TextFile;

public class Shader {
	
	private int program;
	private Map<String,Integer> uniformCache = new HashMap<String,Integer>();
	
	public Shader(String vertexPath, String fragementPath) {
		
		program = glCreateProgram();
		
		String vertexSrc = new TextFile(vertexPath).getText();
		String fragementSrc = new TextFile(fragementPath).getText();
			
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		int fragementShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(vertexShader, vertexSrc);
		glShaderSource(fragementShader, fragementSrc);
		
		glCompileShader(vertexShader);
		glCompileShader(fragementShader);
		
		checkErr(vertexShader);
		checkErr(fragementShader);
		
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragementShader);
		
		glLinkProgram(program);
		glDeleteShader(vertexShader);
		glDeleteShader(fragementShader);
		
	}
	
	public void setUniform(String name, Matrix4f matrix) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		matrix.get(buffer);
		bind();
		glUniformMatrix4fv(getUniformLocation(name), false, buffer);
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	
	public void bind() {
		glUseProgram(program);
	}
	
	private int getUniformLocation(String name) {
		if(uniformCache.containsKey(name)) {
			return uniformCache.get(name);
		}
		int location = glGetUniformLocation(program, name);
		uniformCache.put(name, location);
		return location;
	}
	
	private static void checkErr(int shader) {
		IntBuffer status = BufferUtils.createIntBuffer(1);
		glGetShaderiv(shader, GL_COMPILE_STATUS, status);
		if(status.get(0) == GL_FALSE) {
			System.err.println("Error compiling shader:\n" + glGetShaderInfoLog(shader));
		}
	}
}
