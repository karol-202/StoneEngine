package pl.karol202.stoneengine.rendering.camera;

import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.rendering.Texture2D;
import pl.karol202.stoneengine.rendering.TextureRenderer;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Vector2f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;

public class DebugCamera extends Camera
{
	private TextureRenderer renderer;
	
	public DebugCamera(int screenOffsetX, int screenOffsetY, int width, int height, Texture2D debugTexture)
	{
		super();
		setOffsetX(screenOffsetX);
		setOffsetY(screenOffsetY);
		setWidth(width);
		setHeight(height);
		this.renderer = new TextureRenderer(new Vector2f(-1f, -1f), new Vector2f(1f, 1f), debugTexture);
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
		glViewport(getOffsetX(), getOffsetY(), getWidth(), getHeight());
		ForwardRendering.renderTextureToScreen(renderer);
	}
	
	@Override
	public void updateProjection() { }
	
	@Override
	public Matrix4f getViewProjectionMatrix()
	{
		return null;
	}
	
	public Texture2D getDebugTexture()
	{
		return renderer.getTexture();
	}
	
	public void setDebugTexture(Texture2D debugTexture)
	{
		renderer.setTexture(debugTexture);
	}
}