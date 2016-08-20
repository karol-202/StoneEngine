package pl.karol202.stoneengine.rendering.light;

import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.rendering.Texture;
import pl.karol202.stoneengine.rendering.camera.ShadowmapCameraDirectional;
import pl.karol202.stoneengine.util.Vector3f;

public class DirectionalLight extends Light
{
	private float shadowZNear;
	private float shadowZFar;
	private int shadowResolutionX;
	private int shadowResolutionY;
	private ShadowmapCameraDirectional shadowmapCamera;
	
	public DirectionalLight(Vector3f color, float intensity)
	{
		super(color, intensity);
		shadowZNear = 0.1f;
		shadowZFar = 10f;
		shadowResolutionX = 1024;
		shadowResolutionY = 1024;
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
	
	public Texture getShadowmap()
	{
		return shadowmapCamera.getShadowmap();
	}
}
