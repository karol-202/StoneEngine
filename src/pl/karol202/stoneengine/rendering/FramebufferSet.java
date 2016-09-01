package pl.karol202.stoneengine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.*;

public class FramebufferSet
{
	private int framebuffer;
	private int colorTexture;
	
	public void initFramebuffer(int width, int height)
	{
		framebuffer = glGenFramebuffers();
		bind();
		
		colorTexture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, colorTexture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, colorTexture, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glDrawBuffers(new int[] { GL_COLOR_ATTACHMENT0 });
		
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
			throw new RuntimeException("Cannot initialize framebuffer for PEManager.");
	}
	
	public void bind()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
	}
	
	public int getFramebuffer()
	{
		return framebuffer;
	}
	
	public int getColorTexture()
	{
		return colorTexture;
	}
}