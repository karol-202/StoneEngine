package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.light.PointLight;
import pl.karol202.stoneengine.util.Matrix4f;

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
		addUniform("normalMapIntensity");
		addUniform("normalMap");
		addUniform("lightColor");
		addUniform("lightIntensity");
		addUniform("lightPos");
		addUniform("lightAttenLinear");
		addUniform("lightAttenQuadratic");
		addUniform("lightRange");
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light, Camera camera)
	{
		super.updateShader(transformation, material, light, camera);
		if(!(light instanceof PointLight))
			throw new RuntimeException("Error during updating shader's uniforms: light passed to shader is of invalid type.");
		
		PointLight pointLight = (PointLight) light;
		Matrix4f MVP = camera.getViewProjectionMatrix().mul(transformation);
		setUniform("MVP", MVP);
		setUniform("M", transformation);
		setUniform("cameraPos", camera.getGameObject().getTransform().getTranslation());
		setUniform("diffuseColor", material.getDiffuseColor());
		setUniform("diffuseTexture", material.getDiffuseTexture());
		setUniform("specularColor", material.getSpecularColor());
		setUniform("specularTexture", material.getSpecularTexture());
		setUniform("normalMapIntensity", material.getNormalMapIntensity());
		setUniform("normalMap", material.getNormalMap());
		setUniform("lightColor", light.getColor());
		setUniform("lightIntensity", light.getIntensity());
		setUniform("lightPos", light.getGameObject().getTransform().getTranslation());
		setUniform("lightAttenLinear", pointLight.getAttenLinear());
		setUniform("lightAttenQuadratic", pointLight.getAttenQuadratic());
		setUniform("lightRange", pointLight.getRange());
	}
}
