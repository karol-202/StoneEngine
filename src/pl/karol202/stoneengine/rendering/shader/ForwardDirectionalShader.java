package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Camera;
import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.light.DirectionalLight;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.util.Matrix4f;

public class ForwardDirectionalShader extends Shader
{
	public ForwardDirectionalShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/forward/directional.vs"));
		addFragmentShader(loadShader("./res/shaders/forward/directional.fs"));
		compileShader();
		addUniform("MVP");
		addUniform("cameraPos");
		addUniform("matColor");
		addUniform("lightColor");
		addUniform("lightIntensity");
		addUniform("lightRotation");
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light)
	{
		if(!(light instanceof DirectionalLight))
			throw new RuntimeException("Error during updating shader's uniforms: light passed to shader is of invalid type.");
		
		material.getTexture().bind();
		Matrix4f MVP = Camera.mainCamera.getViewProjectionMatrix().mul(transformation);
		setUniform("MVP", MVP);
		setUniform("cameraPos", Camera.mainCamera.getGameObject().getTransform().getTranslation());
		setUniform("matColor", material.getColor());
		setUniform("lightColor", light.getColor());
		setUniform("lightIntensity", light.getIntensity());
		setUniform("lightRotation", light.getGameObject().getTransformation());
	}
}
