package pl.karol202.stoneengine.rendering.camera;

import pl.karol202.stoneengine.component.GameComponent;
import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Transform;
import pl.karol202.stoneengine.util.Utils;

public abstract class Camera extends GameComponent
{
	protected Matrix4f projectionMatrix;
	private int width;
	private int height;
	private int screenOffsetX;
	private int screenOffsetY;
	
	public Camera(int width, int height)
	{
		super();
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void init()
	{
		ForwardRendering.addCamera(this);
		updateProjection();
	}
	
	@Override
	public void update() { }
	
	public abstract void render();
	
	protected abstract void updateProjection();
	
	protected Matrix4f getViewMatrix()
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
	
	public int getWidth()
	{
		return width;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public int getScreenOffsetX()
	{
		return screenOffsetX;
	}
	
	public void setScreenOffsetX(int screenOffsetX)
	{
		this.screenOffsetX = screenOffsetX;
	}
	
	public int getScreenOffsetY()
	{
		return screenOffsetY;
	}
	
	public void setScreenOffsetY(int screenOffsetY)
	{
		this.screenOffsetY = screenOffsetY;
	}
}