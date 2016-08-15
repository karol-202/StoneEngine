package pl.karol202.stoneengine.rendering.light;

import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.util.Vector3f;

public class PointLight extends Light
{
	private float lightAttenLinear;
	private float lightAttenQuadratic;
	private float range;
	
	public PointLight(Vector3f color, float intensity)
	{
		this(color, intensity, 0f, 1f, 10f);
	}
	
	public PointLight(Vector3f color, float intensity, float lightAttenLinear, float lightAttenQuadratic, float range)
	{
		super(color, intensity);
		this.lightAttenLinear = lightAttenLinear;
		this.lightAttenQuadratic = lightAttenQuadratic;
		this.range = range;
	}
	
	@Override
	public void init()
	{
		super.init();
		ForwardRendering.addPointLight(this);
	}
	
	public float getLightAttenLinear()
	{
		return lightAttenLinear;
	}
	
	public void setLightAttenLinear(float lightAttenLinear)
	{
		this.lightAttenLinear = lightAttenLinear;
	}
	
	public float getLightAttenQuadratic()
	{
		return lightAttenQuadratic;
	}
	
	public void setLightAttenQuadratic(float lightAttenQuadratic)
	{
		this.lightAttenQuadratic = lightAttenQuadratic;
	}
	
	public float getRange()
	{
		return range;
	}
	
	public void setRange(float range)
	{
		this.range = range;
	}
}
