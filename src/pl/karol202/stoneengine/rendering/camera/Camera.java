package pl.karol202.stoneengine.rendering.camera;

import pl.karol202.stoneengine.component.GameComponent;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Transform;
import pl.karol202.stoneengine.util.Utils;

public abstract class Camera extends GameComponent
{
	protected Matrix4f viewMatrix;
	protected Matrix4f projectionMatrix;
	protected Matrix4f viewProjectionMatrix;
	private int width;
	private int height;
	private int screenOffsetX;
	private int screenOffsetY;
	
	public Camera()
	{
		super();
	}
	
	@Override
	public void init()
	{
		updateView();
		updateProjection();
	}
	
	@Override
	public void update() { }
	
	@Override
	public void updateTransformation()
	{
		super.updateTransformation();
		updateView();
	}
	
	public abstract void render();
	
	public abstract void updateProjection();
	
	protected void updateView()
	{
		Transform tr = getGameObject().getTransform();
		Matrix4f cameraRotation = new Matrix4f().initRotation(Utils.getForwardFromEuler(tr.getRotation()), Utils.getRightFromEuler(tr.getRotation()));
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(-tr.getTranslation().getX(),
																	-tr.getTranslation().getY(),
																	-tr.getTranslation().getZ());
		
		viewMatrix = cameraRotation.mul(cameraTranslation);
		updateViewProjection();
	}
	
	protected void updateViewProjection()
	{
		if(viewMatrix == null || projectionMatrix == null) return;
		viewProjectionMatrix = projectionMatrix.mul(viewMatrix);
	}
	
	public Matrix4f getViewProjectionMatrix()
	{
		return viewProjectionMatrix;
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