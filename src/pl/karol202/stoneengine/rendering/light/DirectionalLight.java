package pl.karol202.stoneengine.rendering.light;

import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.rendering.Texture;
import pl.karol202.stoneengine.rendering.camera.ShadowmapCameraDirectional;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Vector3f;

public class DirectionalLight extends Light
{
	private ShadowmapCameraDirectional shadowmapCamera;
	private float shadowZNear;
	private float shadowZFar;
	private int shadowResolutionX;
	private int shadowResolutionY;
	private float shadowBias;
	
	public DirectionalLight(Vector3f color, float intensity)
	{
		super(color, intensity);
		shadowZNear = 0.1f;
		shadowZFar = 10f;
		shadowResolutionX = 1024;
		shadowResolutionY = 1024;
		shadowBias = 0.001f;
		shadowmapCamera = new ShadowmapCameraDirectional(this);
	}
	
	@Override
	public void init()
	{
		shadowmapCamera.setGameObject(getGameObject());
		
		ForwardRendering.addDirectionalLight(this);
		ForwardRendering.addCamera(shadowmapCamera);
		shadowmapCamera.init();
	}
	
	public Texture getShadowmap()
	{
		return shadowmapCamera.getShadowmap();
	}
	
	public Matrix4f getShadowmapViewProjection()
	{
		return shadowmapCamera.getViewProjectionMatrix();
	}
	
	public float getShadowZNear()
	{
		return shadowZNear;
	}
	
	public void setShadowZNear(float shadowZNear)
	{
		this.shadowZNear = shadowZNear;
	}
	
	public float getShadowZFar()
	{
		return shadowZFar;
	}
	
	public void setShadowZFar(float shadowZFar)
	{
		this.shadowZFar = shadowZFar;
	}
	
	public int getShadowResolutionX()
	{
		return shadowResolutionX;
	}
	
	public void setShadowResolutionX(int shadowResolutionX)
	{
		this.shadowResolutionX = shadowResolutionX;
	}
	
	public int getShadowResolutionY()
	{
		return shadowResolutionY;
	}
	
	public void setShadowResolutionY(int shadowResolutionY)
	{
		this.shadowResolutionY = shadowResolutionY;
	}
	
	public float getShadowBias()
	{
		return shadowBias;
	}
	
	public void setShadowBias(float shadowBias)
	{
		this.shadowBias = shadowBias;
	}
}
