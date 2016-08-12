package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.util.Vector3f;

public class Material
{
	private Vector3f color;
	private Texture texture;
	
	public Vector3f getColor()
	{
		return color;
	}
	
	public void setColor(Vector3f color)
	{
		this.color = color;
	}
	
	public Texture getTexture()
	{
		return texture;
	}
	
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}
}
