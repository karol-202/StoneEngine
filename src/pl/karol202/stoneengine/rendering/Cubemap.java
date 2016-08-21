package pl.karol202.stoneengine.rendering;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.*;

public class Cubemap
{
	public static final int POS_X = GL_TEXTURE_CUBE_MAP_POSITIVE_X;
	public static final int NEG_X = GL_TEXTURE_CUBE_MAP_NEGATIVE_X;
	public static final int POS_Y = GL_TEXTURE_CUBE_MAP_POSITIVE_Y;
	public static final int NEG_Y = GL_TEXTURE_CUBE_MAP_NEGATIVE_Y;
	public static final int POS_Z = GL_TEXTURE_CUBE_MAP_POSITIVE_Z;
	public static final int NEG_Z = GL_TEXTURE_CUBE_MAP_NEGATIVE_Z;
	
	private int textureId;
	private int width;
	private int height;
	
	public Cubemap(int width, int height)
	{
		this.textureId = glGenTextures();
		this.width = width;
		this.height = height;
	}
	
	public void bind()
	{
		glBindTexture(GL_TEXTURE_CUBE_MAP, textureId);
	}
	
	public void addTexture(int side, String path)
	{
		bind();
		
		String[] splitArray = path.split("\\.");
		String ext = splitArray[splitArray.length - 1];
		if(!ext.equals("png")) throw new RuntimeException("File format not supported for mesh data: " + ext);
		
		try(FileInputStream fis = new FileInputStream(path))
		{
			PNGDecoder decoder = new PNGDecoder(fis);
			if(decoder.getWidth() != width) throw new RuntimeException("Could not load cubemap: " + path + " has invalid size.");
			if(decoder.getHeight() != height) throw new RuntimeException("Could not load cubemap: " + path + " has invalid size.");
			ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
			decoder.decode(buffer, width * 4, PNGDecoder.Format.RGBA);
			buffer.flip();
			glTexImage2D(side, 0, GL_RGB, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
		}
		catch(IOException e)
		{
			throw new RuntimeException("Could not load texture from file: " + path);
		}
	}
}
