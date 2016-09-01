package pl.karol202.stoneengine.rendering.postprocess;

import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.shader.Shader;
import pl.karol202.stoneengine.util.Matrix4f;

public abstract class PostEffectShader extends Shader
{
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light, Camera camera)
	{
		super.updateShader(transformation, material, light, camera);
		updateEffect();
	}
	
	protected abstract void updateEffect();
}
