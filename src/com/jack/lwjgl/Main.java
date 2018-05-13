package com.jack.lwjgl;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL43.*;


import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GLDebugMessageCallback;

import com.jack.lwjgl.engine.DataType;
import com.jack.lwjgl.engine.graphics.Shader;
import com.jack.lwjgl.engine.graphics.buffers.IndexBuffer;
import com.jack.lwjgl.engine.graphics.buffers.VertexArray;
import com.jack.lwjgl.engine.graphics.buffers.VertexBuffer;

public class Main {
	
	private static long window;
	private static int screenWidth = 800;
	private static int screenHeight = 600;
	private static VertexBuffer vbo;
	private static Shader shader;
	private static VertexArray vao;
	private static Matrix4f projection = new Matrix4f().ortho(0, screenWidth, screenHeight, 0, -1, 1);
	private static IndexBuffer ebo;
	
	
	public static float vertices[] = {
			100f,100f, 1f,0.5f,0.5f, // x,y
			300f,100f, 1f,0.5f,1f,
			300f,300f, 1f,0.5f,1f,
			100f,300f, 1f,0.5f,1f,
			600f,500f, 1f,0f,0f,
			550f,400f, 0f,1f,0f,
			500f,500f, 0f,0f,1f
			
	};  
	
	private static int indices[] = {
			0, 1, 2,
			0, 3, 2,
			4, 5, 6
	};
	
	public static void main(String[] args) {
		
		init();
		while(!glfwWindowShouldClose(window)) {
			loop();
		}
		glfwDestroyWindow(window);
		glfwTerminate();
	
	}
	
	public static void init() {
		if(!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		glfwSetErrorCallback(new GLFWErrorCallback() {
			@Override
			public void invoke(int error, long desc) {
				System.err.println("GLFW: " + error + ", Description: " + desc);
			}
		});
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		
		window = glfwCreateWindow(640, 480, "LWJGL", 0, 0);
		if(window == NULL) {
			throw new RuntimeException("Failed to create GLFW window");
		}
		
		glfwMakeContextCurrent(window);
		createCapabilities();
		
		glEnable(GL_DEBUG_OUTPUT);
		glDebugMessageCallback(new GLDebugMessageCallback() {
			
			@Override
			public void invoke(int source, int type, int id, int severity, int length, long message, long param) {
				if(id == 131185) {
					return;
				}
				System.err.println("OpenGL Error: " + id + ", DESCRIPTION: " + memUTF8(memByteBuffer(message, length)));
				
			}
		}, 0);
		
		glViewport(0,0,screenWidth,screenHeight);
		
		glfwSetFramebufferSizeCallback(window, new GLFWFramebufferSizeCallback() {
			
			@Override
			public void invoke(long window, int width, int height) {
				screenWidth = width;
				screenHeight = height;
				glViewport(0,0,screenWidth,screenHeight);
			}
		});
		
		glfwSwapInterval(1);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		vbo = new VertexBuffer(vertices);
		
		ebo = new IndexBuffer(indices);
		
		vao = new VertexArray(vbo, ebo);
		vao.push(DataType.FLOAT, 2);
		vao.push(DataType.FLOAT, 3);
		
		
		shader = new Shader("src/com/jack/lwjgl/engine/graphics/shader/shaders/shader.vs","src/com/jack/lwjgl/engine/graphics/shader/shaders/shader.fs");
		
		shader.setUniform("projection", projection);
		
	}
	
	public static void loop() {
		glClearColor(0.2f, 0.3f, 0.3f, 1f);
		glClear(GL_COLOR_BUFFER_BIT);
		
		shader.bind();
		vao.bind();
		glDrawElements(GL_TRIANGLES, 9, GL_UNSIGNED_INT, 0);
		
		glfwPollEvents();
		glfwSwapBuffers(window);
	}
	
	public static int getScreenWidth() {
		return screenWidth;
	}
	
	public static int getScreenHeight() {
		return screenHeight;
	}
	
}