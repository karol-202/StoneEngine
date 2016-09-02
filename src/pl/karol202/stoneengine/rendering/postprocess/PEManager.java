package pl.karol202.stoneengine.rendering.postprocess;

import pl.karol202.stoneengine.rendering.FramebufferSet;
import pl.karol202.stoneengine.rendering.Texture2D;
import pl.karol202.stoneengine.rendering.TextureRenderer;
import pl.karol202.stoneengine.rendering.shader.Shader;
import pl.karol202.stoneengine.util.Vector2f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL30.*;

public class PEManager
{
	private ArrayList<PostEffect> effects;
	private TextureRenderer renderer;
	private FramebufferSet framebufferA;
	private FramebufferSet framebufferB;
	private int width;
	private int height;
	private int offsetX;
	private int offsetY;
	private boolean renderToA;
	
	public PEManager(int width, int height)
	{
		this.effects = new ArrayList<>();
		this.renderer = new TextureRenderer(new Vector2f(-1f, -1f), new Vector2f(1f, 1f), null);
		this.framebufferA = new FramebufferSet();
		this.framebufferB = new FramebufferSet();
		this.width = width;
		this.height = height;
		
		framebufferA.initFramebuffer(width, height);
		framebufferB.initFramebuffer(width, height);
	}
	
	public FramebufferSet renderAll(int framebufferMS)
	{
		glBindFramebuffer(GL_READ_FRAMEBUFFER, framebufferMS);
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, framebufferA.getFramebuffer());
		glBlitFramebuffer(getOffsetX(), getOffsetY(), getOffsetX() + getWidth(), getOffsetY() + getHeight(),
						  getOffsetX(), getOffsetY(), getOffsetX() + getWidth(), getOffsetY() + getHeight(),
						  GL_COLOR_BUFFER_BIT, GL_NEAREST);
		renderToA = false;
		effects.forEach(effect -> effect.render(this));
		return renderToA ? framebufferB : framebufferA;
	}
	
	public void render(Shader shader, boolean dontBind)
	{
		if(renderToA) framebufferA.bind();
		else framebufferB.bind();
		if(!dontBind) renderer.setTexture(new Texture2D((renderToA ? framebufferB : framebufferA).getColorTexture()));
		renderer.render(shader, !dontBind);
		renderToA = !renderToA;
	}
	
	public FramebufferSet getCurrentFramebuffer()
	{
		return renderToA ? framebufferB : framebufferA;
	}
	
	public void addEffect(PostEffect effect)
	{
		effects.add(effect);
	}
	
	public void removeEffect(PostEffect effect)
	{
		effects.remove(effect);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getOffsetX()
	{
		return offsetX;
	}
	
	public void setOffsetX(int offsetX)
	{
		this.offsetX = offsetX;
	}
	
	public int getOffsetY()
	{
		return offsetY;
	}
	
	public void setOffsetY(int offsetY)
	{
		this.offsetY = offsetY;
	}
}