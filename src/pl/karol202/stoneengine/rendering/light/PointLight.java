package pl.karol202.stoneengine.rendering.light;

import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.rendering.Texture;
import pl.karol202.stoneengine.rendering.camera.ShadowmapPointCamera;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Vector3f;

public class PointLight extends Light
{
	private float attenLinear;
	private float attenQuadratic;
	private float range;
	
	private ShadowmapPointCamera shadowmapCamera;
	private float shadowZNear;
	private int shadowResolutionX;
	private int shadowResolutionY;
	private float shadowBias;
	
	private float shadowRange;
	
	public PointLight(Vector3f color, float intensity)
	{
		this(color, intensity, 0f, 1f, 10f);
	}
	
	public PointLight(Vector3f color, float intensity, float attenLinear, float attenQuadratic, float range)
	{
		super(color, intensity);
		this.attenLinear = attenLinear;
		this.attenQuadratic = attenQuadratic;
		this.setRange(range);
		
		shadowResolutionX = 1024;
		shadowResolutionY = 1024;
		shadowBias = 0.005f;
		setShadowZNear(0.1f);
		shadowmapCamera = new ShadowmapPointCamera(this);
	}
	
	@Override
	public void init()
	{
		shadowmapCamera.setGameObject(getGameObject());
		shadowmapCamera.init();
		
		ForwardRendering.addPointLight(this);
		ForwardRendering.addCamera(shadowmapCamera);
	}
	
	@Override
	public void update()
	{
		super.update();
		shadowmapCamera.update();
	}
	
	@Override
	public void updateTransformation()
	{
		super.updateTransformation();
		shadowmapCamera.updateTransformation();
	}
	
	public Texture getShadowmap()
	{
		return shadowmapCamera.getShadowmap();
	}
	
	public Matrix4f[] getShadowmapViewProjectionMatrices()
	{
		return shadowmapCamera.getViewProjectionMatrices();
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
		shadowRange = range - shadowZNear;
		if(shadowmapCamera != null) shadowmapCamera.updateProjection();
	}
	
	public float getShadowZNear()
	{
		return shadowZNear;
	}
	
	public void setShadowZNear(float shadowZNear)
	{
		this.shadowZNear = shadowZNear;
		shadowRange = range - shadowZNear;
		if(shadowmapCamera != null) shadowmapCamera.updateProjection();
	}
	
	public int getShadowResolutionX()
	{
		return shadowResolutionX;
	}
	
	public void setShadowResolutionX(int shadowResolutionX)
	{
		this.shadowResolutionX = shadowResolutionX;
		shadowmapCamera.updateProjection();
	}
	
	public int getShadowResolutionY()
	{
		return shadowResolutionY;
	}
	
	public void setShadowResolutionY(int shadowResolutionY)
	{
		this.shadowResolutionY = shadowResolutionY;
		shadowmapCamera.updateProjection();
	}
	
	public float getShadowBias()
	{
		return shadowBias;
	}
	
	public void setShadowBias(float shadowBias)
	{
		this.shadowBias = shadowBias;
		shadowmapCamera.updateProjection();
	}
	
	public float getShadowRange()
	{
		return shadowRange;
	}
}
