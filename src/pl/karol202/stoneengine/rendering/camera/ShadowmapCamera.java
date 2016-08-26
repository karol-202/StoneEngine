package pl.karol202.stoneengine.rendering.camera;

import pl.karol202.stoneengine.rendering.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public abstract class ShadowmapCamera extends Camera
{
	protected int framebuffer;
	protected Texture depthTexture;
	protected boolean render;
	
	@Override
	public void init()
	{
		super.init();
		framebuffer = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
		
		depthTexture.bind();
		initTexture();
		glDrawBuffer(GL_NONE);
		glReadBuffer(GL_NONE);
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
			throw new RuntimeException("Cannot initialize framebuffer for camera.");
	}
	
	protected abstract void initTexture();
	
	@Override
	public void render()
	{
		glDisable(GL_CULL_FACE);
		glViewport(0, 0, getWidth(), getHeight());
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
		glClear(GL_DEPTH_BUFFER_BIT);
	}
	
	@Override
	protected void updateViewProjection()
	{
		super.updateViewProjection();
		render = true;
	}
	
	public Texture getShadowmap()
	{
		return depthTexture;
	}
}
