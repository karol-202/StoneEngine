package pl.karol202.stoneengine.rendering.camera;

import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.rendering.Texture2D;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

public abstract class ShadowmapCamera extends Camera
{
	private int framebuffer;
	private Texture2D depthTexture;
	private boolean render;
	
	public ShadowmapCamera()
	{
		super();
		this.depthTexture = new Texture2D(glGenTextures());
	}
	
	@Override
	public void init()
	{
		super.init();
		framebuffer = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
		
		depthTexture.bind();
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT16, getWidth(), getHeight(), 0, GL_DEPTH_COMPONENT, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_FUNC, GL_LEQUAL);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_MODE, GL_COMPARE_R_TO_TEXTURE);
		glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, depthTexture.getTextureId(), 0);
		
		glDrawBuffers(GL_NONE);
		
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
			throw new RuntimeException("Cannot initialize framebuffer for camera.");
	}
	
	@Override
	public void render()
	{
		if(render)
		{
			glDisable(GL_CULL_FACE);
			glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glViewport(0, 0, getWidth(), getHeight());
			ForwardRendering.renderShadowmap(this);
			render = false;
		}
	}
	
	@Override
	protected void updateViewProjection()
	{
		super.updateViewProjection();
		render = true;
	}
	
	public Texture2D getShadowmap()
	{
		return depthTexture;
	}
}
