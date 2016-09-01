package pl.karol202.stoneengine.rendering.camera;

import pl.karol202.stoneengine.rendering.TextureRenderer;
import pl.karol202.stoneengine.rendering.shader.ScreenShader;
import pl.karol202.stoneengine.util.Vector2f;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL30.*;

public class ToScreenCamera extends RTTCamera
{
	private TextureRenderer renderer;
	private ScreenShader shader;
	
	public ToScreenCamera(int width, int height, int samples)
	{
		super(width, height, samples);
		renderer = new TextureRenderer(new Vector2f(-1f, -1f), new Vector2f(1f, 1f), null);
		shader = new ScreenShader();
	}
	
	@Override
	public void render()
	{
		super.render();
		glBindFramebuffer(GL_READ_FRAMEBUFFER, getRenderedFramebuffer());
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
		glBlitFramebuffer(getOffsetX(), getOffsetY(), getOffsetX() + getWidth(), getOffsetY() + getHeight(),
						  getOffsetX(), getOffsetY(), getOffsetX() + getWidth(), getOffsetY() + getHeight(),
						  GL_COLOR_BUFFER_BIT, GL_NEAREST);
		/*glBindFramebuffer(GL_FRAMEBUFFER, 0);
		renderer.setTexture(getRenderedTexture());
		renderer.render(shader);*/
	}
}