package pl.karol202.stoneengine.rendering.camera;

import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.rendering.FramebufferSet;
import pl.karol202.stoneengine.rendering.Texture2D;
import pl.karol202.stoneengine.rendering.postprocess.PEManager;
import pl.karol202.stoneengine.rendering.postprocess.PostEffect;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.GL_TEXTURE_2D_MULTISAMPLE;
import static org.lwjgl.opengl.GL32.glTexImage2DMultisample;

public class RTTCamera extends Camera
{
	private CameraSettings settings;
	private PEManager peManager;
	private FramebufferSet output;
	private int samples;
	
	private int framebufferMS;
	private int colorRenderbufferMS;
	private int depthRenderbufferMS;

	public RTTCamera(int width, int height, int samples)
	{
		super();
		setWidth(width);
		setHeight(height);
		setSettings(new PerspectiveSettings(70f, 0.1f, 100f, (float) width / height));
		this.peManager = new PEManager(width, height);
		this.samples = samples;
	}
	
	@Override
	public void init()
	{
		super.init();
		ForwardRendering.addCamera(this);
		
		framebufferMS = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, framebufferMS);
		
		colorRenderbufferMS = glGenTextures();
		glBindTexture(GL_TEXTURE_2D_MULTISAMPLE, colorRenderbufferMS);
		glTexImage2DMultisample(GL_TEXTURE_2D_MULTISAMPLE, samples, GL_RGBA, getWidth(), getHeight(), true);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D_MULTISAMPLE, colorRenderbufferMS, 0);
		glBindTexture(GL_TEXTURE_2D_MULTISAMPLE, 0);
		glDrawBuffers(new int[] { GL_COLOR_ATTACHMENT0 });
		
		depthRenderbufferMS = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, depthRenderbufferMS);
		glRenderbufferStorageMultisample(GL_RENDERBUFFER, samples, GL_DEPTH_COMPONENT, getWidth(), getHeight());
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthRenderbufferMS);
		glBindRenderbuffer(GL_RENDERBUFFER, 0);
		
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
			throw new RuntimeException("Cannot initialize framebufferMS for camera.");
	}
	
	@Override
	public void render()
	{
		glEnable(GL_CULL_FACE);
		glBindFramebuffer(GL_FRAMEBUFFER, framebufferMS);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glViewport(getOffsetX(), getOffsetY(), getWidth(), getHeight());
		ForwardRendering.renderCamera(this);
		output = peManager.renderAll(framebufferMS);
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
	
	public void addEffect(PostEffect effect)
	{
		peManager.addEffect(effect);
	}
	
	public void removeEffect(PostEffect effect)
	{
		peManager.removeEffect(effect);
	}
	
	@Override
	public void setOffsetX(int offsetX)
	{
		super.setOffsetX(offsetX);
		peManager.setOffsetX(offsetX);
	}
	
	@Override
	public void setOffsetY(int offsetY)
	{
		super.setOffsetY(offsetY);
		peManager.setOffsetY(offsetY);
	}
	
	public int getRenderedFramebuffer()
	{
		return output.getFramebuffer();
	}
	
	public Texture2D getRenderedTexture()
	{
		return new Texture2D(output.getColorTexture());
	}
}
