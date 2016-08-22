package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.util.Matrix4f;

public class ShadowmapShader extends Shader
{
	public ShadowmapShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/shadowmap/shadowmap.vs"));
		compileShader();
		addUniform("MVP");
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light, Camera camera)
	{
		super.updateShader(transformation, material, light, camera);
		Matrix4f MVP = camera.getViewProjectionMatrix().mul(transformation);
		setUniform("MVP", MVP);
	}
}