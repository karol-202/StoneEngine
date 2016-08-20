package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.util.Matrix4f;

public class DebugTextureShader extends Shader
{
	public DebugTextureShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/debug/texture.vs"));
		addFragmentShader(loadShader("./res/shaders/debug/texture.fs"));
		compileShader();
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light, Camera camera) { }
}
