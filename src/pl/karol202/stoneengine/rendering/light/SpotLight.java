package pl.karol202.stoneengine.rendering.light;

import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.rendering.camera.ShadowmapSpotCamera;
import pl.karol202.stoneengine.util.Vector3f;

public class SpotLight extends PointLight
{
	private float innerAngle;
	private float outerAngle;
	
	public SpotLight(Vector3f color, float intensity)
	{
		this(color, intensity, 0f, 1f, 10f);
	}
	
	public SpotLight(Vector3f color, float intensity, float attenLinear, float attenQuadratic, float range)
	{
		super(color, intensity, attenLinear, attenQuadratic, range);
		innerAngle = 0.3f;
		innerAngle = 0.5f;
	}
	
	@Override
	public void init()
	{
		shadowmapCamera.setGameObject(getGameObject());
		shadowmapCamera.init();
		
		ForwardRendering.addSpotLight(this);
		ForwardRendering.addCamera(shadowmapCamera);
	}
	
	@Override
	protected void initShadowmapCamera()
	{
		shadowmapCamera = new ShadowmapSpotCamera(this);
	}
	
	public float getInnerAngle()
	{
		return innerAngle;
	}
	
	public void setInnerAngle(float innerAngle)
	{
		this.innerAngle = 1 - innerAngle / 180;
	}
	
	public float getOuterAngle()
	{
		return outerAngle;
	}
	
	public void setOuterAngle(float outerAngle)
	{
		this.outerAngle = 1 - outerAngle / 180;
	}
}
