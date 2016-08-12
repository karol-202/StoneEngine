package pl.karol202.stoneengine.rendering.light;

import pl.karol202.stoneengine.util.Vector3f;

public class SpotLight extends PointLight
{
	private float spotAngle;
	
	public SpotLight(Vector3f color, float intensity)
	{
		super(color, intensity);
	}
	
	public float getSpotAngle()
	{
		return spotAngle;
	}
	
	public void setSpotAngle(float spotAngle)
	{
		this.spotAngle = spotAngle;
	}
}
