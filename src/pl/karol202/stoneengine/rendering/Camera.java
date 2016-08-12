package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.component.GameComponent;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Transform;
import pl.karol202.stoneengine.util.Utils;

public class Camera extends GameComponent
{
	public static Camera mainCamera;
	
	private float fov;
	private float zNear;
	private float zFar;
	private float aspect;
	private Matrix4f projectionMatrix;
	
	public Camera(float fov, float zNear, float zFar, float aspect)
	{
		super();
		this.fov = fov;
		this.zNear = zNear;
		this.zFar = zFar;
		this.aspect = aspect;
		updateProjection();
	}
	
	@Override
	public void init()
	{
		Camera.mainCamera = this;
	}
	
	@Override
	public void update() { }
	
	@Override
	public void render() { }
	
	private void updateProjection()
	{
		projectionMatrix = new Matrix4f().initPerspective(zNear, zFar, aspect, fov);
	}
	
	public float getFov()
	{
		return fov;
	}
	
	public void setFov(float fov)
	{
		this.fov = fov;
		updateProjection();
	}
	
	public float getZNear()
	{
		return zNear;
	}
	
	public void setZNear(float zNear)
	{
		this.zNear = zNear;
		updateProjection();
	}
	
	public float getZFar()
	{
		return zFar;
	}
	
	public void setZFar(float zFar)
	{
		this.zFar = zFar;
		updateProjection();
	}
	
	public float getAspect()
	{
		return aspect;
	}
	
	public void setAspect(float aspect)
	{
		this.aspect = aspect;
		updateProjection();
	}
	
	private Matrix4f getViewMatrix()
	{
		Transform tr = getGameObject().getTransform();
		Matrix4f cameraRotation = new Matrix4f().initRotation(Utils.getForwardFromEuler(tr.getRotation()), Utils.getRightFromEuler(tr.getRotation()));
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(-tr.getTranslation().getX(),
																	-tr.getTranslation().getY(),
																	-tr.getTranslation().getZ());
		
		return cameraRotation.mul(cameraTranslation);
	}
	
	public Matrix4f getViewProjectionMatrix()
	{
		return projectionMatrix.mul(getViewMatrix());
	}
}
