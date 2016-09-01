package pl.karol202.stoneengine.rendering.postprocess;

import pl.karol202.stoneengine.rendering.FramebufferSet;
import pl.karol202.stoneengine.rendering.Texture2D;
import pl.karol202.stoneengine.rendering.TextureRenderer;
import pl.karol202.stoneengine.util.Vector2f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class PEManager
{
	private ArrayList<PostEffectShader> effects;
	private TextureRenderer renderer;
	private FramebufferSet framebufferA;
	private FramebufferSet framebufferB;
	private int width;
	private int height;
	private int offsetX;
	private int offsetY;
	
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
	
	public FramebufferSet render(int framebufferMS)
	{
		glBindFramebuffer(GL_READ_FRAMEBUFFER, framebufferMS);
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, framebufferA.getFramebuffer());
		glBlitFramebuffer(getOffsetX(), getOffsetY(), getOffsetX() + getWidth(), getOffsetY() + getHeight(),
						  getOffsetX(), getOffsetY(), getOffsetX() + getWidth(), getOffsetY() + getHeight(),
						  GL_COLOR_BUFFER_BIT, GL_NEAREST);

		framebufferB.bind();
		glClear(GL_COLOR_BUFFER_BIT);
		renderer.setTexture(new Texture2D(framebufferA.getColorTexture()));
		renderer.render(effects.get(0));
		
		return framebufferB;
	}
	
	public void addEffect(PostEffectShader effect)
	{
		effects.add(effect);
	}
	
	public void removeEffect(PostEffectShader effect)
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