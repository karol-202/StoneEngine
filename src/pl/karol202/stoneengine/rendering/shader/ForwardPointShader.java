package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Camera;
import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.light.PointLight;
import pl.karol202.stoneengine.util.Matrix4f;

import static org.lwjgl.opengl.GL13.*;

public class ForwardPointShader extends Shader
{
	public ForwardPointShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/forward/point.vs"));
		addFragmentShader(loadShader("./res/shaders/forward/point.fs"));
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
		addUniform("lightPos");
		addUniform("lightAttenLinear");
		addUniform("lightAttenQuadratic");
		addUniform("lightRange");
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light)
	{
		if(!(light instanceof PointLight))
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
		
		PointLight pointLight = (PointLight) light;
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
		setUniform("lightPos", light.getGameObject().getTransform().getTranslation());
		setUniform("lightAttenLinear", pointLight.getLightAttenLinear());
		setUniform("lightAttenQuadratic", pointLight.getLightAttenQuadratic());
		setUniform("lightRange", pointLight.getRange());
	}
}
