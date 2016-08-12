package pl.karol202.stoneengine.rendering.light;

import pl.karol202.stoneengine.component.GameComponent;
import pl.karol202.stoneengine.rendering.shader.Shader;
import pl.karol202.stoneengine.util.Vector3f;

public class Light extends GameComponent
{
	private Vector3f color;
	private float intensity;
	
	public Light(Vector3f color, float intensity)
	{
		this.color = color;
		this.intensity = intensity;
	}
	
	@Override
	public void init() { }
	
	@Override
	public void update() { }
	
	@Override
	public void render(Shader shader, Light light) { }
	
	public Vector3f getColor()
	{
		return color;
	}
	
	public void setColor(Vector3f color)
	{
		this.color = color;
	}
	
	public float getIntensity()
	{
		return intensity;
	}
	
	public void setIntensity(float intensity)
	{
		this.intensity = intensity;
	}
}
