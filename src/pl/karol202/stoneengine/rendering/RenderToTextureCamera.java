package pl.karol202.stoneengine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

public class RenderToTextureCamera extends Camera
{
	private int framebuffer;
	private Texture renderTexture;
	private int depthRenderbuffer;
	
	public RenderToTextureCamera(float fov, float zNear, float zFar, int width, int height)
	{
		super(fov, zNear, zFar, width, height);
		renderTexture = new Texture(glGenTextures());
	}
	
	@Override
	public void init()
	{
		super.init();
		framebuffer = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
		
		renderTexture.bind();
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, getWidth(), getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		
		depthRenderbuffer = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, depthRenderbuffer);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, getWidth(), getHeight());
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthRenderbuffer);
		
		glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, renderTexture.getTextureId(), 0);
		glDrawBuffers(new int[] { GL_COLOR_ATTACHMENT0 });
		
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
			throw new RuntimeException("Cannot initialize framebuffer for camera.");
	}
	
	@Override
	public void render()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
		glViewport(getScreenOffsetX(), getScreenOffsetY(), getWidth(), getHeight());
		ForwardRendering.renderCamera(this);
	}
	
	public Texture getRenderTexture()
	{
		return renderTexture;
	}
}
