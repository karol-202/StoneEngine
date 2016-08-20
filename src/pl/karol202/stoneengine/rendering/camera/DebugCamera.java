package pl.karol202.stoneengine.rendering.camera;

import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.rendering.Texture;
import pl.karol202.stoneengine.rendering.TextureRenderer;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Vector2f;

import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;

public class DebugCamera extends Camera
{
	private TextureRenderer renderer;
	
	public DebugCamera(int screenOffsetX, int screenOffsetY, int width, int height, Texture debugTexture)
	{
		super(width, height);
		setScreenOffsetX(screenOffsetX);
		setScreenOffsetY(screenOffsetY);
		this.renderer = new TextureRenderer(new Vector2f(-1f, -1f), new Vector2f(1f, 1f), debugTexture);
	}
	
	@Override
	public void render()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glViewport(getScreenOffsetX(), getScreenOffsetY(), getWidth(), getHeight());
		ForwardRendering.renderDebugTexture(renderer);
	}
	
	@Override
	protected void updateProjection() { }
	
	@Override
	public Matrix4f getViewProjectionMatrix()
	{
		return null;
	}
	
	public Texture getDebugTexture()
	{
		return renderer.getTexture();
	}
	
	public void setDebugTexture(Texture debugTexture)
	{
		renderer.setTexture(debugTexture);
	}
}