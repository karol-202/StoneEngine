package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.util.Matrix4f;

import static org.lwjgl.opengl.GL13.*;

public class ForwardAmbientShader extends Shader
{
	public ForwardAmbientShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/forward/ambient.vs"));
		addFragmentShader(loadShader("./res/shaders/forward/ambient.fs"));
		compileShader();
		addUniform("MVP");
		addUniform("diffuseColor");
		addUniform("diffuseTexture");
		addUniform("ambientOcclussionIntensity");
		addUniform("ambientOcclussionTexture");
		addUniform("lightColor");
		addUniform("lightIntensity");
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light, Camera camera)
	{
		if(material.getDiffuseTexture() != null)
		{
			glActiveTexture(GL_TEXTURE0);
			material.getDiffuseTexture().bind();
		}
		if(material.getAmbientOcclussionTexture() != null)
		{
			glActiveTexture(GL_TEXTURE1);
			material.getAmbientOcclussionTexture().bind();
		}
		
		Matrix4f MVP = camera.getViewProjectionMatrix().mul(transformation);
		setUniform("MVP", MVP);
		setUniform("diffuseColor", material.getDiffuseColor());
		setUniform("diffuseTexture", 0);
		setUniform("ambientOcclussionIntensity", material.getAmbientOcclussionIntensity());
		setUniform("ambientOcclussionTexture", 1);
		setUniform("lightColor", light.getColor());
		setUniform("lightIntensity", light.getIntensity());
	}
}
