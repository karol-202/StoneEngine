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
		super(light.getShadowResolutionX(), light.getShadowResolutionY());
		this.light = light;
	}
	
	@Override
	protected void updateProjection()
	{
		projectionMatrix = new Matrix4f().initOrthagonal(-5, 5, -3, 7, light.getShadowZNear(), light.getShadowZFar());
		//projectionMatrix = new Matrix4f().initPerspective(0.1f, 20f, 1f, 70f);
	}
	
	@Override
	protected Matrix4f getViewMatrix()
	{
		Transform tr = getGameObject().getTransform();
		Matrix4f cameraRotation = new Matrix4f().initRotation(Utils.getForwardFromEuler(tr.getRotation()),
															  Utils.getRightFromEuler(tr.getRotation()));
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(Utils.getForwardFromEuler(tr.getRotation()).mul(-3f));
		
		return cameraRotation.mul(cameraTranslation);
	}
}
