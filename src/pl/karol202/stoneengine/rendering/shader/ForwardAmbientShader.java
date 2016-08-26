package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.util.Matrix4f;

public class ForwardAmbientShader extends Shader
{
	public ForwardAmbientShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/forward/ambient.vs"));
		addFragmentShader(loadShader("./res/shaders/forward/ambient.fs"));
		compileShader();
		addUniform("MVP");
		addUniform("M");
		addUniform("cameraPos");
		addUniform("diffuseColor");
		addUniform("diffuseTexture");
		addUniform("specularColor");
		addUniform("specularTexture");
		addUniform("ambientOcclussionIntensity");
		addUniform("ambientOcclussionTexture");
		addUniform("normalMapIntensity");
		addUniform("normalMap");
		addUniform("lightColor");
		addUniform("lightIntensity");
		addUniform("skybox");
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light, Camera camera)
	{
		super.updateShader(transformation, material, light, camera);
		Matrix4f MVP = camera.getViewProjectionMatrix().mul(transformation);
		setUniform("MVP", MVP, true);
		setUniform("M", transformation, true);
		setUniform("cameraPos", camera.getGameObject().getTransform().getTranslation());
		setUniform("diffuseColor", material.getDiffuseColor());
		setUniform("diffuseTexture", material.getDiffuseTexture());
		setUniform("specularColor", material.getSpecularColor().mul(ForwardRendering.getSkyboxRenderer().getSpecularIntensity()));
		setUniform("specularTexture", material.getSpecularTexture());
		setUniform("ambientOcclussionIntensity", material.getAmbientOcclussionIntensity());
		setUniform("ambientOcclussionTexture", material.getAmbientOcclussionTexture());
		setUniform("normalMapIntensity", material.getNormalMapIntensity());
		setUniform("normalMap", material.getNormalMap());
		setUniform("lightColor", light.getColor());
		setUniform("lightIntensity", light.getIntensity());
		setUniform("skybox", ForwardRendering.getSkyboxRenderer().getCubemap());
	}
}
