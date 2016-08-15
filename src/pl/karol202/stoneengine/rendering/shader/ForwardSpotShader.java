package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Camera;
import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.light.SpotLight;
import pl.karol202.stoneengine.util.Matrix4f;

import static org.lwjgl.opengl.GL13.*;

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
		addUniform("lightColor");
		addUniform("lightIntensity");
		addUniform("lightPos");
		addUniform("lightSpotDirectionMat");
		addUniform("lightAttenLinear");
		addUniform("lightAttenQuadratic");
		addUniform("lightRange");
		addUniform("lightInnerAngle");
		addUniform("lightOuterAngle");
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light)
	{
		if(!(light instanceof SpotLight))
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
		
		SpotLight spotLight = (SpotLight) light;
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
		setUniform("lightSpotDirectionMat", light.getGameObject().getTransformation());
		setUniform("lightAttenLinear", spotLight.getAttenLinear());
		setUniform("lightAttenQuadratic", spotLight.getAttenQuadratic());
		setUniform("lightRange", spotLight.getRange());
		setUniform("lightInnerAngle", spotLight.getInnerAngle());
		setUniform("lightOuterAngle", spotLight.getOuterAngle());
	}
}
