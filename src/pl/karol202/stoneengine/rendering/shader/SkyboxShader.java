package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.util.Matrix4f;

public class SkyboxShader extends Shader
{
	public SkyboxShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/skybox/skybox.vs"));
		addFragmentShader(loadShader("./res/shaders/skybox/skybox.fs"));
		compileShader();
		addUniform("V");
		addUniform("P");
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light, Camera camera)
	{
		setUniform("V", camera.getViewMatrix());
		setUniform("P", camera.getProjectionMatrix());
	}
}
