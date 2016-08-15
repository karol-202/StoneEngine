package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Camera;
import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.util.Matrix4f;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

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
		addUniform("lightColor");
		addUniform("lightIntensity");
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light)
	{
		if(material.getDiffuseTexture() != null)
		{
			glActiveTexture(GL_TEXTURE0);
			material.getDiffuseTexture().bind();
		}
		
		Matrix4f MVP = Camera.mainCamera.getViewProjectionMatrix().mul(transformation);
		setUniform("MVP", MVP);
		setUniform("diffuseColor", material.getDiffuseColor());
		setUniform("diffuseTexture", 0);
		setUniform("lightColor", light.getColor());
		setUniform("lightIntensity", light.getIntensity());
	}
}
