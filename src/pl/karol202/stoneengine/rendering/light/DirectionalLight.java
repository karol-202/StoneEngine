package pl.karol202.stoneengine.rendering.light;

import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.rendering.Texture;
import pl.karol202.stoneengine.rendering.camera.ShadowmapDirectionalCamera;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Vector3f;

public class DirectionalLight extends Light
{
	private ShadowmapDirectionalCamera shadowmapCamera;
	private float shadowZNear;
	private float shadowZFar;
	private int shadowResolutionX;
	private int shadowResolutionY;
	private float shadowBias;
	
	public DirectionalLight(Vector3f color, float intensity)
	{
		super(color, intensity);
		shadowZNear = -1f;
		shadowZFar = 10f;
		shadowResolutionX = 1024;
		shadowResolutionY = 1024;
		shadowBias = 0.005f;
		shadowmapCamera = new ShadowmapDirectionalCamera(this);
	}
	
	@Override
	public void init()
	{
		shadowmapCamera.setGameObject(getGameObject());
		shadowmapCamera.init();
		
		ForwardRendering.addDirectionalLight(this);
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
		shadowmapCamera.updateProjection();
	}
	
	public float getShadowZFar()
	{
		return shadowZFar;
	}
	
	public void setShadowZFar(float shadowZFar)
	{
		this.shadowZFar = shadowZFar;
		shadowmapCamera.updateProjection();
	}
	
	public int getShadowResolutionX()
	{
		return shadowResolutionX;
	}
	
	public void setShadowResolutionX(int shadowResolutionX)
	{
		this.shadowResolutionX = shadowResolutionX;
		shadowmapCamera.setWidth(shadowResolutionX);
	}
	
	public int getShadowResolutionY()
	{
		return shadowResolutionY;
	}
	
	public void setShadowResolutionY(int shadowResolutionY)
	{
		this.shadowResolutionY = shadowResolutionY;
		shadowmapCamera.setHeight(shadowResolutionY);
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
