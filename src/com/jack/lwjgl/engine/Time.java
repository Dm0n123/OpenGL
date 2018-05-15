package com.jack.lwjgl.engine;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.BufferUtils;
import java.nio.ByteBuffer;
import com.jack.lwjgl.Main;

public class Time {
	
	private static int lastFps = (int) getTime();
	private static int frames = 0;
	private static int fps = 0;
	
	public static long getTime() {
		return System.nanoTime() / 1000000;
	}
	
	public static void updateFps() {
		if(getTime() - lastFps >= 1000) {
			String text = "FPS: " + fps + "\0";
			ByteBuffer buffer = BufferUtils.createByteBuffer(text.length());
			buffer.put(text.getBytes());
			fps = frames;
			frames = 0;
			lastFps += 1000;
			buffer.flip();
			glfwSetWindowTitle(Main.window, buffer);
		}
		frames++;
	}
	
}
