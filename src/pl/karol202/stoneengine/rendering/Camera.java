package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.component.GameComponent;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Transform;
import pl.karol202.stoneengine.util.Utils;

import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;

public class Camera extends GameComponent
{
	private float fov;
	private float zNear;
	private float zFar;
	private float aspect;
	private Matrix4f projectionMatrix;
	private int width;
	private int height;
	private int screenOffsetX;
	private int screenOffsetY;
	
	public Camera(float fov, float zNear, float zFar, int width, int height)
	{
		super();
		this.fov = fov;
		this.zNear = zNear;
		this.zFar = zFar;
		this.aspect = (float) width / height;
		updateProjection();
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void init()
	{
		ForwardRendering.addCamera(this);
	}
	
	@Override
	public void update() { }
	
	public void render()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glViewport(screenOffsetX, screenOffsetY, width, height);
		ForwardRendering.renderCamera(this);
	}
	
	private void updateProjection()
	{
		projectionMatrix = new Matrix4f().initPerspective(zNear, zFar, aspect, fov);
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