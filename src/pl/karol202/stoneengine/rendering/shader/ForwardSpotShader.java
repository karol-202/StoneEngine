package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.light.SpotLight;
import pl.karol202.stoneengine.util.Matrix4f;

public class ForwardSpotShader extends Shader
{
	public ForwardSpotShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/forward/spot.vs"));
		addFragmentShader(loadShader("./res/shaders/forward/spot.fs"));
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
		addUniform("lightSpotDirectionMat");
		addUniform("lightAttenLinear");
		addUniform("lightAttenQuadratic");
		addUniform("lightRange");
		addUniform("lightInnerAngle");
		addUniform("lightOuterAngle");
		addUniform("shadowmap");
		addUniform("shadowBias");
		addUniform("shadowRange");
		addUniform("shadowZNear");
		addUniform("shadowSoftness");
		addUniform("shadowSamples");
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light, Camera camera)
	{
		super.updateShader(transformation, material, light, camera);
		if(!(light instanceof SpotLight))
			throw new RuntimeException("Error during updating shader's uniforms: light passed to shader is of invalid type.");
		
		SpotLight spotLight = (SpotLight) light;
		Matrix4f MVP = camera.getViewProjectionMatrix().mul(transformation);
		setUniform("MVP", MVP, true);
		setUniform("M", transformation, true);
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
		setUniform("lightSpotDirectionMat", light.getGameObject().getTransformation(), true);
		setUniform("lightAttenLinear", spotLight.getAttenLinear());
		setUniform("lightAttenQuadratic", spotLight.getAttenQuadratic());
		setUniform("lightRange", spotLight.getRange());
		setUniform("lightInnerAngle", spotLight.getInnerAngle());
		setUniform("lightOuterAngle", spotLight.getOuterAngle());
		setUniform("shadowmap", spotLight.getShadowmap());
		setUniform("shadowBias", spotLight.getShadowBias());
		setUniform("shadowRange", spotLight.getShadowRange());
		setUniform("shadowZNear", spotLight.getShadowZNear());
		setUniform("shadowSoftness", spotLight.getShadowSoftness());
		setUniform("shadowSamples", spotLight.getShadowSamples());
	}
}
