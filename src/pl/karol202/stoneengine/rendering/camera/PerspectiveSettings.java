package pl.karol202.stoneengine.rendering.camera;

import pl.karol202.stoneengine.util.Matrix4f;

public class PerspectiveSettings implements CameraSettings
{
	private Camera camera;
	private float fov;
	private float zNear;
	private float zFar;
	private float aspect;
	
	public PerspectiveSettings(float fov, float zNear, float zFar, float aspect)
	{
		this.fov = fov;
		this.zNear = zNear;
		this.zFar = zFar;
		this.aspect = aspect;
	}
	
	@Override
	public Matrix4f getProjection()
	{
		return new Matrix4f().initPerspective(zNear, zFar, aspect, fov);
	}
	
	@Override
	public void setCamera(Camera camera)
	{
		this.camera = camera;
	}
	
	public float getFov()
	{
		return fov;
	}
	
	public void setFov(float fov)
	{
		this.fov = fov;
		camera.updateProjection();
	}
	
	public float getZNear()
	{
		return zNear;
	}
	
	public void setZNear(float zNear)
	{
		this.zNear = zNear;
		camera.updateProjection();
	}
	
	public float getZFar()
	{
		return zFar;
	}
	
	public void setZFar(float zFar)
	{
		this.zFar = zFar;
		camera.updateProjection();
	}
	
	public float getAspect()
	{
		return aspect;
	}
	
	public void setAspect(float aspect)
	{
		this.aspect = aspect;
		camera.updateProjection();
	}
}
