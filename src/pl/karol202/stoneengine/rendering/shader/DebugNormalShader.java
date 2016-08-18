package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Camera;
import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.util.Matrix4f;

public class DebugNormalShader extends Shader
{
	public DebugNormalShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/debug/normals.vs"));
		addFragmentShader(loadShader("./res/shaders/debug/normals.fs"));
		compileShader();
		addUniform("MVP");
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light, Camera camera)
	{
		Matrix4f MVP = camera.getViewProjectionMatrix().mul(transformation);
		setUniform("MVP", MVP);
	}
}