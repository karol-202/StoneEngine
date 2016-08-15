package pl.karol202.stoneengine.rendering.light;

import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.util.Vector3f;

public class PointLight extends Light
{
	private float attenLinear;
	private float attenQuadratic;
	private float range;
	
	public PointLight(Vector3f color, float intensity)
	{
		this(color, intensity, 0f, 1f, 10f);
	}
	
	public PointLight(Vector3f color, float intensity, float attenLinear, float attenQuadratic, float range)
	{
		super(color, intensity);
		this.attenLinear = attenLinear;
		this.attenQuadratic = attenQuadratic;
		this.range = range;
	}
	
	@Override
	public void init()
	{
		ForwardRendering.addPointLight(this);
	}
	
	public float getAttenLinear()
	{
		return attenLinear;
	}
	
	public void setAttenLinear(float attenLinear)
	{
		this.attenLinear = attenLinear;
	}
	
	public float getAttenQuadratic()
	{
		return attenQuadratic;
	}
	
	public void setAttenQuadratic(float attenQuadratic)
	{
		this.attenQuadratic = attenQuadratic;
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
