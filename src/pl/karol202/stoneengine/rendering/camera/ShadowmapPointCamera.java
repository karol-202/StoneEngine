package pl.karol202.stoneengine.rendering.camera;

import pl.karol202.stoneengine.rendering.Cubemap;
import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.rendering.light.PointLight;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT16;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

public class ShadowmapPointCamera extends ShadowmapCamera
{
	private PointLight light;
	private int side;
	private Matrix4f[] viewProjectionMatrices;
	
	public ShadowmapPointCamera(PointLight light)
	{
		super();
		this.depthTexture = new Cubemap(glGenTextures());
		this.light = light;
		this.side = GL_TEXTURE_CUBE_MAP_POSITIVE_X;
		this.viewProjectionMatrices = new Matrix4f[6];
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
		for(int i = GL_TEXTURE_CUBE_MAP_POSITIVE_X; i <= GL_TEXTURE_CUBE_MAP_NEGATIVE_Z; i++)
			glTexImage2D(i, 0, GL_DEPTH_COMPONENT16, getWidth(), getHeight(), 0, GL_DEPTH_COMPONENT, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
		//glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_COMPARE_FUNC, GL_LEQUAL);
		//glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_COMPARE_MODE, GL_COMPARE_R_TO_TEXTURE);
		glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, depthTexture.getTextureId(), 0);
	}
	
	@Override
	public void render()
	{
		if(render)
		{
			super.render();
			ForwardRendering.renderShadowmapPoint(this, light);
			render = false;
		}
	}
	
	@Override
	public void updateProjection()
	{
		projectionMatrix = new Matrix4f().initPerspective(light.getShadowZNear(), light.getRange(), getWidth() / (float) getHeight(), 90f);
		updateViewProjection();
	}
	
	@Override
	protected void updateView()
	{
		updateViewProjection();
	}
	
	@Override
	protected void updateViewProjection()
	{
		if(projectionMatrix == null) return;
		Matrix4f translation = new Matrix4f().initTranslation(getGameObject().getTransform().getTranslation().mul(-1f));
		viewProjectionMatrices[0] = projectionMatrix.mul(
				new Matrix4f().initRotation(new Vector3f(1f, 0f, 0f), new Vector3f(0f, 0f, -1f), new Vector3f(0f, -1f, 0f))
						.mul(translation));
		viewProjectionMatrices[1] = projectionMatrix.mul(
				new Matrix4f().initRotation(new Vector3f(-1f, 0f, 0f), new Vector3f(0f, 0f, 1f), new Vector3f(0f, -1f, 0f))
						.mul(translation));
		viewProjectionMatrices[2] = projectionMatrix.mul(
				new Matrix4f().initRotation(new Vector3f(0f, 1f, 0f), new Vector3f(1f, 0f, 0f), new Vector3f(0f, 0f, 1f))
						.mul(translation));
		viewProjectionMatrices[3] = projectionMatrix.mul(
				new Matrix4f().initRotation(new Vector3f(0f, -1f, 0f), new Vector3f(1f, 0f, 0f), new Vector3f(0f, 0f, -1f))
						.mul(translation));
		viewProjectionMatrices[4] = projectionMatrix.mul(
				new Matrix4f().initRotation(new Vector3f(0f, 0f, 1f), new Vector3f(1f, 0f, 0f), new Vector3f(0f, -1f, 0f))
						.mul(translation));
		viewProjectionMatrices[5] = projectionMatrix.mul(
				new Matrix4f().initRotation(new Vector3f(0f, 0f, -1f), new Vector3f(-1f, 0f, 0f), new Vector3f(0f, -1f, 0f))
						.mul(translation));
		render = true;
		//viewProjectionMatrices[0] = projectionMatrix;
	}
	
	public Matrix4f[] getViewProjectionMatrices()
	{
		return viewProjectionMatrices;
	}
}
