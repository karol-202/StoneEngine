package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Camera;
import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.light.DirectionalLight;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.util.Matrix4f;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class ForwardDirectionalShader extends Shader
{
	public ForwardDirectionalShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/forward/directional.vs"));
		addFragmentShader(loadShader("./res/shaders/forward/directional.fs"));
		compileShader();
		addUniform("MVP");
		addUniform("M");
		addUniform("cameraPos");
		addUniform("diffuseColor");
		addUniform("diffuseTexture");
		addUniform("specularColor");
		addUniform("specularTexture");
		addUniform("lightColor");
		addUniform("lightIntensity");
		addUniform("lightRotation");
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light)
	{
		if(!(light instanceof DirectionalLight))
			throw new RuntimeException("Error during updating shader's uniforms: light passed to shader is of invalid type.");
		
		if(material.getDiffuseTexture() != null)
		{
			glActiveTexture(GL_TEXTURE0);
			material.getDiffuseTexture().bind();
		}
		if(material.getSpecularTexture() != null)
		{
			glActiveTexture(GL_TEXTURE1);
			material.getSpecularTexture().bind();
		}
		
		Matrix4f MVP = Camera.mainCamera.getViewProjectionMatrix().mul(transformation);
		setUniform("MVP", MVP);
		setUniform("M", transformation);
		setUniform("cameraPos", Camera.mainCamera.getGameObject().getTransform().getTranslation());
		setUniform("diffuseColor", material.getDiffuseColor());
		setUniform("diffuseTexture", 0);
		setUniform("specularColor", material.getSpecularColor());
		setUniform("specularTexture", 1);
		setUniform("lightColor", light.getColor());
		setUniform("lightIntensity", light.getIntensity());
		setUniform("lightRotation", light.getGameObject().getTransformation());
	}
}