package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Camera;
import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Transform;

public class ForwardAmbientShader extends Shader
{
	public ForwardAmbientShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/forward/ambient.vs"));
		addFragmentShader(loadShader("./res/shaders/forward/ambient.fs"));
		compileShader();
		addUniform("MVP");
		addUniform("matColor");
		addUniform("lightColor");
		addUniform("lightIntensity");
	}
	
	@Override
	public void updateShader(Transform transform, Material material, Light light)
	{
		material.getTexture().bind();
		
		Matrix4f MVP = Camera.mainCamera.getViewProjectionMatrix().mul(transform.getTransformation());
		setUniform("MVP", MVP);
		setUniform("matColor", material.getColor());
		setUniform("lightColor", light.getColor());
		setUniform("lightIntensity", light.getIntensity());
	}
}
