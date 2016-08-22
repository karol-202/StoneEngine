package pl.karol202.stoneengine.rendering.camera;

import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.rendering.Texture2D;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

public class RTTCamera extends Camera
{
	private CameraSettings settings;
	private int framebuffer;
	private Texture2D renderTexture;
	private int depthRenderbuffer;
	
	public RTTCamera(int width, int height)
	{
		super();
		setWidth(width);
		setHeight(height);
		renderTexture = new Texture2D(glGenTextures());
		setSettings(new PerspectiveSettings(70f, 0.1f, 100f, (float) width / height));
	}
	
	@Override
	public void init()
	{
		super.init();
		ForwardRendering.addCamera(this);
		
		framebuffer = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
		
		renderTexture.bind();
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, getWidth(), getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, renderTexture.getTextureId(), 0);
		glDrawBuffers(new int[] { GL_COLOR_ATTACHMENT0 });
		
		depthRenderbuffer = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, depthRenderbuffer);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, getWidth(), getHeight());
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthRenderbuffer);
		
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
			throw new RuntimeException("Cannot initialize framebuffer for camera.");
	}
	
	@Override
	public void render()
	{
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
		glViewport(getScreenOffsetX(), getScreenOffsetY(), getWidth(), getHeight());
		ForwardRendering.renderCamera(this);
	}
	
	@Override
	public void updateProjection()
	{
		projectionMatrix = settings.getProjection();
		updateViewProjection();
	}
	
	public CameraSettings getSettings()
	{
		return settings;
	}
	
	public void setSettings(CameraSettings settings)
	{
		this.settings = settings;
		this.settings.setCamera(this);
	}
	
	public Texture2D getRenderTexture()
	{
		return renderTexture;
	}
}
