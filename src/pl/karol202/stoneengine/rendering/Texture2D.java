package pl.karol202.stoneengine.rendering;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture2D implements Texture
{
	private int textureId;
	
	public Texture2D(int textureId)
	{
		this.textureId = textureId;
	}
	
	public Texture2D(int width, int height, ByteBuffer buffer)
	{
		this.textureId = glGenTextures();
		bind();
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_ANISOTROPY_EXT, 16);
		glGenerateMipmap(GL_TEXTURE_2D);
	}
	
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, textureId);
	}
	
	public static Texture2D loadTexture(String path)
	{
		String[] splitArray = path.split("\\.");
		String ext = splitArray[splitArray.length - 1];
		
		if(!ext.equals("png"))
			throw new RuntimeException("File format not supported for mesh data: " + ext);
		
		try(FileInputStream fis = new FileInputStream(path))
		{
			PNGDecoder decoder = new PNGDecoder(fis);
			ByteBuffer buffer = BufferUtils.createByteBuffer(decoder.getWidth() * decoder.getHeight() * 4);
			decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
			buffer.flip();
			return new Texture2D(decoder.getWidth(), decoder.getHeight(), buffer);
		}
		catch(IOException e)
		{
			throw new RuntimeException("Could not load texture from file: " + path);
		}
	}
	
	public int getTextureId()
	{
		return textureId;
	}
}
