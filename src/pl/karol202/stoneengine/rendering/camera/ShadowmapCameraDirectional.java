package pl.karol202.stoneengine.rendering.camera;

import pl.karol202.stoneengine.rendering.light.DirectionalLight;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Transform;
import pl.karol202.stoneengine.util.Utils;

public class ShadowmapCameraDirectional extends ShadowmapCamera
{
	private DirectionalLight light;
	
	public ShadowmapCameraDirectional(DirectionalLight light)
	{
		super();
		this.light = light;
	}
	
	@Override
	public void init()
	{
		setWidth(light.getShadowResolutionX());
		setHeight(light.getShadowResolutionY());
		super.init();
	}
	
	@Override
	public void updateProjection()
	{
		projectionMatrix = new Matrix4f().initOrthagonal(-5, 5, -3, 7, light.getShadowZNear(), light.getShadowZFar());
		updateViewProjection();
	}
	
	@Override
	protected void updateView()
	{
		Transform tr = getGameObject().getTransform();
		Matrix4f cameraRotation = new Matrix4f().initRotation(Utils.getForwardFromEuler(tr.getRotation()),
															  Utils.getRightFromEuler(tr.getRotation()));
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(Utils.getForwardFromEuler(tr.getRotation()).mul(-3f));
		
		viewMatrix = cameraRotation.mul(cameraTranslation);
		updateViewProjection();
	}
}