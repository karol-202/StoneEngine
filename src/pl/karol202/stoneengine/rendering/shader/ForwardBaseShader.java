package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Vector3f;

public class ForwardBaseShader extends Shader
{
	public ForwardBaseShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/forward/base.vs"));
		addFragmentShader(loadShader("./res/shaders/forward/base.fs"));
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
		addUniform("emissiveColor");
		addUniform("emissiveTexture");
		addUniform("lightColor");
		addUniform("lightIntensity");
		addUniform("skybox");
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light, Camera camera)
	{
		super.updateShader(transformation, material, light, camera);
		Matrix4f MVP = camera.getViewProjectionMatrix().mul(transformation);
		Vector3f specularColor = material.getSpecularColor();
		if(ForwardRendering.getSkyboxRenderer() != null)
			specularColor = specularColor.mul(ForwardRendering.getSkyboxRenderer().getSpecularIntensity());
		
		setUniform("MVP", MVP, true);
		setUniform("M", transformation, true);
		setUniform("cameraPos", camera.getGameObject().getTransform().getTranslation());
		setUniform("diffuseColor", material.getDiffuseColor());
		setUniform("diffuseTexture", material.getDiffuseTexture());
		setUniform("specularColor", specularColor);
		setUniform("specularTexture", material.getSpecularTexture());
		setUniform("ambientOcclussionIntensity", material.getAmbientOcclussionIntensity());
		setUniform("ambientOcclussionTexture", material.getAmbientOcclussionTexture());
		setUniform("normalMapIntensity", material.getNormalMapIntensity());
		setUniform("normalMap", material.getNormalMap());
		setUniform("emissiveColor", material.getEmissiveColor());
		setUniform("emissiveTexture", material.getEmissiveTexture());
		setUniform("lightColor", light.getColor());
		setUniform("lightIntensity", light.getIntensity());
		if(ForwardRendering.getSkyboxRenderer() != null)
			setUniform("skybox", ForwardRendering.getSkyboxRenderer().getCubemap());
	}
}