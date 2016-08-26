package pl.karol202.stoneengine.rendering.camera;

import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.rendering.Texture2D;
import pl.karol202.stoneengine.rendering.light.DirectionalLight;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Transform;
import pl.karol202.stoneengine.util.Utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

public class ShadowmapDirectionalCamera extends ShadowmapCamera
{
	private DirectionalLight light;
	
	public ShadowmapDirectionalCamera(DirectionalLight light)
	{
		super();
		this.depthTexture = new Texture2D(glGenTextures());
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
	protected void initTexture()
	{
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT16, getWidth(), getHeight(), 0, GL_DEPTH_COMPONENT, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_FUNC, GL_LEQUAL);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_MODE, GL_COMPARE_R_TO_TEXTURE);
		glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, depthTexture.getTextureId(), 0);
	}
	
	@Override
	public void render()
	{
		if(render)
		{
			super.render();
			ForwardRendering.renderShadowmapDirectional(this);
			render = false;
		}
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