package com.jack.lwjgl.engine.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TextFile {
	
	private String text;
	
	public TextFile(String path) {
		
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		
		try {
		
			File file = new File(path);
			FileInputStream stream = new FileInputStream(file);
			
			while((length = stream.read(buffer)) != -1) {
				result.write(buffer,0,length);
			}
			
			stream.close();
			
			this.text = result.toString("UTF-8");
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	public String getText() {
		return text;
	}
	
}
