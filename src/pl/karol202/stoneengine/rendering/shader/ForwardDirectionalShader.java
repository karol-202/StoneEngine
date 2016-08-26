package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.light.DirectionalLight;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.util.Matrix4f;

public class ForwardDirectionalShader extends Shader
{
	private Matrix4f SMVPTransform;
	
	public ForwardDirectionalShader()
	{
		super();
		SMVPTransform = new Matrix4f().initTranslation(0.5f, 0.5f, 0.5f).mul(new Matrix4f().initScale(0.5f, 0.5f, 0.5f));
		
		addVertexShader(loadShader("./res/shaders/forward/directional.vs"));
		addFragmentShader(loadShader("./res/shaders/forward/directional.fs"));
		compileShader();
		addUniform("MVP");
		addUniform("M");
		addUniform("shadowmapMVP");
		addUniform("cameraPos");
		addUniform("diffuseColor");
		addUniform("diffuseTexture");
		addUniform("specularColor");
		addUniform("specularTexture");
		addUniform("normalMapIntensity");
		addUniform("normalMap");
		addUniform("lightColor");
		addUniform("lightIntensity");
		addUniform("lightRotation");
		addUniform("shadowmap");
		addUniform("shadowBias");
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light, Camera camera)
	{
		super.updateShader(transformation, material, light, camera);
		if(!(light instanceof DirectionalLight))
			throw new RuntimeException("Error during updating shader's uniforms: light passed to shader is of invalid type.");
		DirectionalLight directionalLight = (DirectionalLight) light;
			
		Matrix4f MVP = camera.getViewProjectionMatrix().mul(transformation);
		Matrix4f shadowmapMVP = SMVPTransform.mul(directionalLight.getShadowmapViewProjection().mul(transformation));
		setUniform("MVP", MVP, true);
		setUniform("M", transformation, true);
		setUniform("shadowmapMVP", shadowmapMVP, true);
		setUniform("cameraPos", camera.getGameObject().getTransform().getTranslation());
		setUniform("diffuseColor", material.getDiffuseColor());
		setUniform("diffuseTexture", material.getDiffuseTexture());
		setUniform("specularColor", material.getSpecularColor());
		setUniform("specularTexture", material.getSpecularTexture());
		setUniform("normalMapIntensity", material.getNormalMapIntensity());
		setUniform("normalMap", material.getNormalMap());
		setUniform("lightColor", light.getColor());
		setUniform("lightIntensity", light.getIntensity());
		setUniform("lightRotation", light.getGameObject().getTransformation(), true);
		setUniform("shadowmap", directionalLight.getShadowmap());
		setUniform("shadowBias", directionalLight.getShadowBias());
	}
}