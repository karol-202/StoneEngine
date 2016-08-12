package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Camera;
import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Transform;

public class BasicShader extends Shader
{
	public BasicShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/basic/basic.vs"));
		addFragmentShader(loadShader("./res/shaders/basic/basic.fs"));
		compileShader();
		addUniform("MVP");
		addUniform("color");
	}
	
	@Override
	public void updateShader(Transform transform, Material material)
	{
		material.getTexture().bind();
		
		Matrix4f MVP = Camera.mainCamera.getViewProjectionMatrix().mul(transform.getTransformation());
		setUniform("MVP", MVP);
		setUniform("color", material.getColor());
	}
}
