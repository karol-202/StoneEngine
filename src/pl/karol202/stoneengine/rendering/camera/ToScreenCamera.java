package pl.karol202.stoneengine.rendering.camera;

import pl.karol202.stoneengine.rendering.ForwardRendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;

public class ToScreenCamera extends Camera
{
	private CameraSettings settings;
	
	public ToScreenCamera(int width, int height)
	{
		super();
		setWidth(width);
		setHeight(height);
		setSettings(new PerspectiveSettings(70f, 0.1f, 100f, (float) width / height));
	}
	
	@Override
	public void init()
	{
		super.init();
		ForwardRendering.addCamera(this);
	}
	
	@Override
	public void render()
	{
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
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
}
